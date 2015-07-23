package com.deftwun.zombiecopter.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.utils.Logger;
import com.deftwun.zombiecopter.App;
import com.deftwun.zombiecopter.ComponentMappers;
import com.deftwun.zombiecopter.GameEngine;
import com.deftwun.zombiecopter.components.*;

public class VehicleSystem extends EntitySystem {

	private Logger logger = new Logger("VehicleSystem", Logger.INFO);

	public static final Aspect.Builder VEHICLE_ASPECTS = Aspect.all(VehicleComponent.class);
	public static final Aspect.Builder VEHICLE_OPERATOR_ASPECTS = Aspect.all(VehicleOperatorComponent.class);

	private Entity vehicleFly;
	private Entity occupantFly;

	public VehicleSystem() {
		// ideally this would be a BaseSystem, but can't access flyweights from there (yet).
		super(Aspect.all(WorkaroundComponent.class));
		logger.debug("Initializing");
	}

	@Override
	protected void initialize() {
		super.initialize();
		vehicleFly = createFlyweightEntity();
		occupantFly = createFlyweightEntity();
	}

	private void ejectOccupant(Entity e) {
		logger.debug("Eject occupant");

		GameEngine engine = App.engine;
		ComponentMappers mappers = engine.mappers;

		PhysicsComponent physics = mappers.physics.get(e);
		ControllerComponent controller = mappers.controller.get(e);
		VehicleComponent vehicle = mappers.vehicle.get(e);
		TeamComponent team = mappers.team.get(e);

		if (vehicle.occupantData.equals("")) return;
		vehicle.eject = false;

		//Remove team
		if (team != null) e.edit().remove(TeamComponent.class); // remove team

		//Reset entity controller
		controller.reset();

		//Recreate occupant
		Entity occupant = engine.factory.deserialize(vehicle.occupantData);
		if (occupant == null) {
			logger.error("Could not deserialize : " + vehicle.occupantData);
			return;
		}
		PhysicsComponent occupantPhys = mappers.physics.get(occupant);
		if (occupantPhys != null) {
			occupantPhys.setPosition(physics.getPosition());
			occupantPhys.setLinearVelocity(physics.getLinearVelocity());
		}
		engine.addEntity(occupant);
		App.engine.systems.player.setPlayer(occupant);

		vehicle.occupantData = "";
	}

	public void processSystem() {

		IntBag vehicles = App.engine.getEntitiesFor(VEHICLE_ASPECTS);

		ejectOccupantsFromExplodingCars(vehicles);
	}

	private void ejectOccupantsFromExplodingCars(IntBag vehicles) {
		ComponentMappers mappers = App.engine.mappers;
		//Vehicles
		for (int i = 0, s = vehicles.size(); i < s; i++) {
			vehicleFly.id = vehicles.get(i);

			VehicleComponent vehicle = mappers.vehicle.get(vehicleFly);
			HealthComponent health = mappers.health.get(vehicleFly);

			if ((vehicle.eject && !vehicle.occupantData.equals("")) || (health != null && health.value <= 0)) {
				ejectOccupant(vehicleFly);
			}
		}
	}
}
