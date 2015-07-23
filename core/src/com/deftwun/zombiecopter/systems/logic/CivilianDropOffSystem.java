package com.deftwun.zombiecopter.systems.logic;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.deftwun.zombiecopter.components.Collector;
import com.deftwun.zombiecopter.components.DropOffPoint;
import com.deftwun.zombiecopter.components.Physics;
import net.mostlyoriginal.api.system.core.DualEntityProcessingSystem;


//Drop off points are areas that an entity with a 'collector' component can drop off civilians when within range. 
//TODO: Could be made to include different types of collectables ..
public class CivilianDropOffSystem extends DualEntityProcessingSystem {
	private int totalCivsReturned = 0;

	public CivilianDropOffSystem() {
		super(Aspect.all(Collector.class), Aspect.all(DropOffPoint.class));
	}

	protected ComponentMapper<Collector> mCollector;
	protected ComponentMapper<DropOffPoint> mDropOffPoint;
	protected ComponentMapper<Physics> mPhysics;

	@Override
	protected void process(Entity collector, Entity dropOff) {
		if (withinRange(collector, dropOff)) {
			returnCivilians(collector);
		}
	}

	private void returnCivilians(Entity actor) {
		final Collector collector = mCollector.get(actor);
		totalCivsReturned += collector.civilians;
		collector.civilians = 0;
	}

	private boolean withinRange(Entity actor, Entity dropOff) {
		final Physics actorPhysics = mPhysics.get(actor);
		final DropOffPoint dropOffPoint = mDropOffPoint.get(dropOff);
		return actorPhysics.getPosition().dst(dropOffPoint.position) < dropOffPoint.range;
	}

	public int getTotalCiviliansDroppedOff() {
		return totalCivsReturned;
	}
}
