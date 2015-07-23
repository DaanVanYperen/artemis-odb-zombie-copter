package com.deftwun.zombiecopter.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.utils.Logger;
import com.deftwun.zombiecopter.App;
import com.deftwun.zombiecopter.ComponentMappers;
import com.deftwun.zombiecopter.components.Health;
import com.deftwun.zombiecopter.components.Physics;
import com.deftwun.zombiecopter.components.TimeToLive;

public class LifetimeSystem extends EntityProcessingSystem {
	private Logger logger = new Logger("LifeTimeSystem",Logger.INFO);
	
	@SuppressWarnings("unchecked")
	public LifetimeSystem() {
		super(Aspect.all(Physics.class)
				    .one(Health.class,TimeToLive.class));
		logger.debug("initializing");
	}		

	@Override
	protected void process(Entity entity) {
		
		ComponentMappers mappers = App.engine.mappers;
		TimeToLive timeToLive = mappers.timeToLive.get(entity);
	
		//Time to live (expiration)
		if (timeToLive != null){
			timeToLive.time += world.delta;
			if (timeToLive.time >= timeToLive.timeLimit){
				logger.debug("Entity #" + entity.getId() + " has expired: Time to live exceeded");
				App.engine.removeEntity(entity);
			}
		}
	}
}
