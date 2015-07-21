package com.deftwun.zombiecopter.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.IntBag;
import com.deftwun.zombiecopter.App;
import com.deftwun.zombiecopter.ComponentMappers;
import com.deftwun.zombiecopter.components.Collectable;
import com.deftwun.zombiecopter.components.Collector;
import com.deftwun.zombiecopter.components.PhysicsComponent;

public class CollectableSystem extends EntityProcessingSystem {

	private Entity collectableFly;

	@SuppressWarnings("unchecked")
	public CollectableSystem() {
		super(Aspect.all(PhysicsComponent.class, Collector.class));
	}

	@Override
	protected void initialize() {
		super.initialize();
		collectableFly = createFlyweightEntity();
}

	@SuppressWarnings("unchecked")
	@Override
	protected void process(Entity entity) {
		ComponentMappers maps =App.engine.mappers;
		PhysicsComponent physics = maps.physics.get(entity);
		Collector collector = maps.collector.get(entity);

		final IntBag collectables = App.engine.getEntitiesFor(Aspect.all(Collectable.class));
		for (int i = 0, s = collectables.size(); i < s; i++) {
			collectableFly.id = collectables.get(i);
			Collectable c = maps.collectable.get(collectableFly);
			PhysicsComponent p = maps.physics.get(collectableFly);
			if (c!= null && p != null){
				if (p.getPosition().sub(physics.getPosition()).len() < c.pickupRange){
					if (c.type == Collectable.ItemType.Civilian && collector.civilians < collector.maxCivilians) {
						collector.civilians += 1;
						App.engine.removeEntity(p.ownerEntity);
					}
				}
			}
		}
	}

}
