package com.deftwun.zombiecopter.systems.logic;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.utils.Logger;
import com.deftwun.zombiecopter.components.TimeToLive;

/**
 * Expire after a while.
 */
@Wire
public class LifetimeSystem extends EntityProcessingSystem {
	private Logger logger = new Logger("LifeTimeSystem", Logger.INFO);

	protected ComponentMapper<TimeToLive> mTimeToLive;

	@SuppressWarnings("unchecked")
	public LifetimeSystem() {
		super(Aspect.all(TimeToLive.class));
		logger.debug("initializing");
	}

	@Override
	protected void process(Entity entity) {
		TimeToLive timeToLive = mTimeToLive.get(entity);
		timeToLive.time += world.delta;
		if (timeToLive.time >= timeToLive.timeLimit) {
			logger.debug("Entity #" + entity.getId() + " has expired: Time to live exceeded");
			entity.deleteFromWorld();
		}
	}
}
