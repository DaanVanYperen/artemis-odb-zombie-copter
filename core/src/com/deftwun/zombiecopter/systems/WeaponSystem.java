package com.deftwun.zombiecopter.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.deftwun.zombiecopter.App;
import com.deftwun.zombiecopter.ComponentMappers;
import com.deftwun.zombiecopter.components.*;

public class WeaponSystem extends EntityProcessingSystem {
	
	private Logger logger = new Logger("WeaponSystem",Logger.INFO);	
	private Vector2 pos = new Vector2(), 
					vel = new Vector2();
	
	@SuppressWarnings("unchecked")
	public WeaponSystem(){
		super(Aspect.one(GunComponent.class, MeleeComponent.class));
		logger.debug("initializing");
	}
	
	@Override
	protected void process(Entity entity) {
		
		ComponentMappers mappers = App.engine.mappers;
		PhysicsComponent physics = mappers.physics.get(entity);
		GunComponent gun = mappers.gun.get(entity);
		MeleeComponent melee = mappers.melee.get(entity);
		ControllerComponent controller = mappers.controller.get(entity);
		
		//Shoot Gun
		if (gun != null){
			gun.triggerPulled = controller.attack;
			gun.time += world.delta;
			if (gun.triggerPulled && gun.time >= gun.cooldown){
				logger.debug(String.format("Entity %s is firing",entity.getId()));
				
				vel.set(controller.lookVector).nor().scl(gun.bulletSpeed).add(physics.getLinearVelocity());
				vel.rotate(MathUtils.random(gun.spreadAngle)-gun.spreadAngle/2);
				pos.set(gun.offset).rotate(controller.lookVector.angle()).add(physics.getPosition());
				
				Entity bulletEntity = App.engine.factory.build(gun.projectileName,pos,vel,controller.lookVector.angle());
				if (bulletEntity != null){
					//Time to live
					TimeToLiveComponent ttl = App.engine.createComponent(bulletEntity,TimeToLiveComponent.class);
					ttl.timeLimit = gun.range / gun.bulletSpeed;
					
					//Parent 
					ChildComponent child = App.engine.createComponent(bulletEntity,ChildComponent.class);
					child.parentEntity = entity;
				
					gun.time = 0;
				}
			}
		}
		
		//Melee Attack
		if (melee != null){
			melee.triggerPulled = controller.attack; 
			melee.time += world.delta;;
			String status = String.format("Melee.target = %b\n TriggerPulled = %b\n Cooled = %b",
										   melee.target != null,melee.triggerPulled,melee.time >= melee.coolDown);
			logger.debug(status);
			if (melee.target != null && melee.triggerPulled && melee.time >= melee.coolDown){
				logger.debug("Entity " + entity.getId() + " is meleeing");
				Vector2 targetPosition = mappers.physics.get(melee.target).getPosition();
				App.engine.systems.damage.dealDamage(entity,melee.target,melee.damage,targetPosition.sub(physics.getPosition()).angle());
				melee.time = 0;
			}
		}
	}

}
