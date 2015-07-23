package com.deftwun.zombiecopter.systems;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.deftwun.zombiecopter.App;
import com.deftwun.zombiecopter.CollisionBits;
import com.deftwun.zombiecopter.ComponentMappers;
import com.deftwun.zombiecopter.components.*;


public class DamageSystem extends EntityProcessingSystem{

	private Logger logger = new Logger("DamageSystem", Logger.INFO);
	private final float deadEntityTimeLimit = 5;

	private Bag<Component> componentsTmp = new Bag<Component>();

	@SuppressWarnings("unchecked")
	public DamageSystem() {
		super(Aspect.all(Bullet.class));
		logger.debug("initializing");
	}

	public void death(Entity entity){
	
        ComponentMappers mappers = App.engine.mappers;
        Physics physics = mappers.physics.get(entity);
        Health health = mappers.health.get(entity);

		if (!health.isDead){
			health.isDead = true;
			logger.debug("Entity #" + entity.getId() + " has died.");
			 
			App.engine.systems.particle.addEffect(health.deathEffect, physics.getPosition(), 0);
			 
			//Item Drop
			if (!health.drop.equals("") && MathUtils.randomBoolean(health.dropRate)){
				logger.debug("Dropping item: " + health.drop);
				App.engine.factory.build(health.drop, physics.getPosition(), physics.getLinearVelocity());
			}
			
			//Corpse
			if (!health.corpse.equals("")){
				logger.debug("Creating corpse: " + health.corpse);
				Entity corpse = App.engine.factory.build(health.corpse,physics.getPosition(), physics.getLinearVelocity());
				if (App.engine.systems.player.isPlayer(entity)){
					App.engine.systems.player.setPlayer(corpse);
				}
				App.engine.removeEntity(entity);
			}
			
			//No corpse?.... Well I guess I'll just wing it and just make the entity basically a corpse until it gets removed (time to live)
			else{
				
				logger.debug("Entity has no corpse. Will now attempt to disable entity by removing key components.");
				
				physics.getPrimaryBody().setFixedRotation(false);
			 
				//Remove all components except these ones
				final Bag<Component> components = entity.getComponents(componentsTmp);
				for (int i = components.size()-1; i > 0; i--) {
					final Component c = components.get(i);
					if (!(c instanceof Physics) &&
						!(c instanceof TimeToLive) &&
						!(c instanceof Sprite) &&
						!(c instanceof Health) &&
						!(c instanceof Player) &&
						!(c instanceof Look))
					{ 
						entity.edit().remove(c);
					}
				}
				
				//Set Time to live
				TimeToLive ttl = mappers.timeToLive.get(entity);
				if (ttl == null) ttl = App.engine.createComponent(entity,TimeToLive.class);
				ttl.time = 0;
				ttl.timeLimit = deadEntityTimeLimit;
				logger.debug("  ..countdown to removal (timeToLive) = " + ttl.timeLimit + " seconds.");
			}
		}
		 
		//Intense death (Gibbed)
		else if (health.value < -health.max / 3){
			logger.debug("Entity #" + entity.getId() + " has been gibbed: Intense Death");
			App.engine.systems.particle.addEffect(health.gibbedEffect, physics.getPosition(), 0);
			App.engine.removeEntity(entity);
		}
	}
	
	public void dealDamage(Entity e, float dmg){
		dealDamage(e,dmg,0);
	}
	
	public void dealDamage(Entity target, float dmg, float angle){
		dealDamage(null,target,dmg,angle);
	}
	
	public void dealDamage(Entity dealer, Entity target, float dmg, float angle){
		Health h = App.engine.mappers.health.get(target);
		Physics p = App.engine.mappers.physics.get(target);
		
		if (h == null) return;
		if (p != null) App.engine.systems.particle.addEffect(h.damageEffect, p.getPosition(),angle);
		
		logger.debug(String.format("%f damage dealt to Entity#%d",dmg,target.getId()));
		
		h.value -= dmg;
		h.isHurt = true;
		if (h.value <= 0 && !h.isDead){
			death(target);
			if (dealer != null){
				logger.debug(String.format("Entity %d has killed Entity %d",dealer.getId(),target.getId()));
			}
		}
	}	
	
	@Override
	protected void process(Entity entity) {
		
		Physics physics = App.engine.mappers.physics.get(entity);
		Bullet bullet = App.engine.mappers.bullet.get(entity);
		Child child = App.engine.mappers.child.get(entity);
		
		//Iterate over all touching fixtures, checking to see if they have 
		// health, and deal damage as necessary
		Array<Fixture> fixturesTouching = App.engine.systems.physics.getFixturesTouching(physics);
		for (Fixture f : fixturesTouching){
			//Should have no effect on sensors
			if (f.isSensor()) continue;
			Physics p = (Physics) f.getBody().getUserData();
			if (p == null) continue;
			Entity dealer = (child == null) ? entity : child.parentEntity;
			dealDamage(dealer,p.ownerEntity,bullet.damage,physics.getRotation());
		}
		
		//disable bullet after colliding with anything
		if (physics.getCollisionNormal() > 0) {
			physics.setFilter(CollisionBits.Effects, CollisionBits.Mask_Effects);
			entity.edit().remove(Bullet.class);
			//logger.debug("Entity #" + entity.getId() + ": damage disabled");
		}	
	}
}