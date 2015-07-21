package com.deftwun.zombiecopter.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.utils.Array;
import com.deftwun.zombiecopter.App;
import com.deftwun.zombiecopter.ComponentMappers;
import com.deftwun.zombiecopter.DropOffPoint;
import com.deftwun.zombiecopter.components.Collector;
import com.deftwun.zombiecopter.components.PhysicsComponent;


//Drop off points are areas that an entity with a 'collector' component can drop off civilians when within range. 
//TODO: Could be made to include different types of collectables ..
public class CivilianDropOffSystem extends EntityProcessingSystem {
	private Array<DropOffPoint> points = new Array<DropOffPoint>();
	private int totalCivsReturned = 0;
	
	public int getTotalCiviliansDroppedOff(){
		return totalCivsReturned;
	}
	
	public void add(DropOffPoint point){
		points.add(point);
	}

	public CivilianDropOffSystem() {
		super(Aspect.all(Collector.class));
	}

	@Override
	protected void process(Entity e) {
		final ComponentMappers maps = App.engine.mappers;
		for (DropOffPoint point : points){
			Collector c = maps.collector.get(e);
			PhysicsComponent phys = maps.physics.get(e);
			if (phys.getPosition().dst(point.position) < point.range){
				totalCivsReturned += c.civilians;
				c.civilians = 0;
			}

		}
	}
}
