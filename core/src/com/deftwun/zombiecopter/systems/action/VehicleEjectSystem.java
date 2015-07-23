package com.deftwun.zombiecopter.systems.action;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.utils.Logger;
import com.deftwun.zombiecopter.App;
import com.deftwun.zombiecopter.components.*;

@Wire
public class VehicleEjectSystem extends EntityProcessingSystem {

	private Logger logger = new Logger("VehicleEjectSystem", Logger.INFO);

	protected ComponentMapper<Health> mHealthComponent;
	protected ComponentMapper<Vehicle> mVehicleComponent;
	protected ComponentMapper<Controller> mControllerComponent;
	protected ComponentMapper<Physics> mPhysicsComponent;
	protected ComponentMapper<Team> mTeamComponent;
	
	public VehicleEjectSystem() {
		// ideally this would be a BaseSystem, but can't access flyweights from there (yet).
		super(Aspect.all(Vehicle.class));
		logger.debug("Initializing");
	}

	@Override
	protected void process(Entity e) {
		if (hasEjectableOccupant(e) || isDestroyed(e)) {
			ejectOccupant(e);
		}
	}

	private boolean isDestroyed(Entity e) {
		final Health health =  mHealthComponent.get(e);
		return health != null && health.value <= 0;
	}

	private boolean hasEjectableOccupant(Entity e) {
		final Vehicle vehicle = mVehicleComponent.get(e);
		return vehicle.eject && vehicle.isOccupied();
	}

	private void ejectOccupant(Entity e) {
		logger.debug("Eject occupant");

		Vehicle vehicle = mVehicleComponent.get(e);
		if (!vehicle.isOccupied()) return;
		vehicle.eject = false;

		removeTeam(e);
		resetEntityController(e);
		ejectTrunkedOccupant(e);
	}

	private void resetEntityController(Entity e) {
		Controller controller = mControllerComponent.get(e);
		controller.reset();
	}

	private void removeTeam(Entity e) {
		Team team = mTeamComponent.get(e);
		if (team != null) e.edit().remove(Team.class); // remove teamType
	}

	private void ejectTrunkedOccupant(Entity e) {
		Vehicle vehicle = mVehicleComponent.get(e);
		Physics physics = mPhysicsComponent.get(e);

		//Recreate occupant
		final Entity occupant = App.engine.factory.deserialize(vehicle.occupantData);
		if (occupant == null) {
			logger.error("Could not deserialize : " + vehicle.occupantData);
			return;
		}

		final Physics occupantPhys = mPhysicsComponent.get(occupant);
		if (occupantPhys != null) {
			occupantPhys.setPosition(physics.getPosition());
			occupantPhys.setLinearVelocity(physics.getLinearVelocity());
		}
		App.engine.systems.player.setPlayer(occupant);

		vehicle.occupantData = Vehicle.NO_OCCUPANT;
	}
}
