package com.deftwun.zombiecopter.systems.logic;

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

	protected ComponentMapper<HealthComponent> mHealthComponent;
	protected ComponentMapper<VehicleComponent> mVehicleComponent;
	protected ComponentMapper<ControllerComponent> mControllerComponent;
	protected ComponentMapper<PhysicsComponent> mPhysicsComponent;
	protected ComponentMapper<TeamComponent> mTeamComponent;
	
	public VehicleEjectSystem() {
		// ideally this would be a BaseSystem, but can't access flyweights from there (yet).
		super(Aspect.all(VehicleComponent.class));
		logger.debug("Initializing");
	}

	@Override
	protected void process(Entity e) {
		if (hasEjectableOccupant(e) || isDestroyed(e)) {
			ejectOccupant(e);
		}
	}

	private boolean isDestroyed(Entity e) {
		final HealthComponent health =  mHealthComponent.get(e);
		return health != null && health.value <= 0;
	}

	private boolean hasEjectableOccupant(Entity e) {
		final VehicleComponent vehicle = mVehicleComponent.get(e);
		return vehicle.eject && vehicle.isOccupied();
	}

	private void ejectOccupant(Entity e) {
		logger.debug("Eject occupant");

		VehicleComponent vehicle = mVehicleComponent.get(e);
		if (!vehicle.isOccupied()) return;
		vehicle.eject = false;

		removeTeam(e);
		resetEntityController(e);
		ejectTrunkedOccupant(e);
	}

	private void resetEntityController(Entity e) {
		ControllerComponent controller = mControllerComponent.get(e);
		controller.reset();
	}

	private void removeTeam(Entity e) {
		TeamComponent team = mTeamComponent.get(e);
		if (team != null) e.edit().remove(TeamComponent.class); // remove team
	}

	private void ejectTrunkedOccupant(Entity e) {
		VehicleComponent vehicle = mVehicleComponent.get(e);
		PhysicsComponent physics = mPhysicsComponent.get(e);

		//Recreate occupant
		final Entity occupant = App.engine.factory.deserialize(vehicle.occupantData);
		if (occupant == null) {
			logger.error("Could not deserialize : " + vehicle.occupantData);
			return;
		}

		final PhysicsComponent occupantPhys = mPhysicsComponent.get(occupant);
		if (occupantPhys != null) {
			occupantPhys.setPosition(physics.getPosition());
			occupantPhys.setLinearVelocity(physics.getLinearVelocity());
		}
		App.engine.systems.player.setPlayer(occupant);

		vehicle.occupantData = VehicleComponent.NO_OCCUPANT;
	}
}
