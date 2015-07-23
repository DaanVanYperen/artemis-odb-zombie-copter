package com.deftwun.zombiecopter.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.utils.Logger;
import com.deftwun.zombiecopter.App;
import com.deftwun.zombiecopter.components.PhysicsComponent;
import com.deftwun.zombiecopter.components.TeamComponent;
import com.deftwun.zombiecopter.components.VehicleComponent;
import com.deftwun.zombiecopter.components.VehicleOperatorComponent;
import net.mostlyoriginal.api.system.core.DualEntityProcessingSystem;

/**
 * @author Daan van Yperen
 */
@Wire
public class VehicleMountingSystem extends DualEntityProcessingSystem {

	public static final int MAX_VEHICLE_ENTRY_DISTANCE = 3;

	private Logger logger = new Logger("VehicleMountingSystem", Logger.INFO);

	private ComponentMapper<VehicleOperatorComponent> mVehicleOperator;
	private ComponentMapper<VehicleComponent> mVehicle;
	private ComponentMapper<PhysicsComponent> mPhysics;
	protected ComponentMapper<TeamComponent> mTeam;

	public VehicleMountingSystem() {
		super(Aspect.all(VehicleComponent.class), Aspect.all(VehicleOperatorComponent.class));
	}

	@Override
	protected void process(Entity vehicle, Entity operator) {
	final VehicleOperatorComponent vehicleOperator = this.mVehicleOperator.get(operator);
		if (vehicleOperator.enterVehicle) {
			if (withinRange(vehicle, operator)) {
				enterVehicle(operator, vehicle);
			}
		}
	}

	private boolean withinRange(Entity vehicle, Entity operator) {
		return distance(vehicle, operator) <= MAX_VEHICLE_ENTRY_DISTANCE;
	}

	private float distance(Entity vehicle, Entity operator) {
		final PhysicsComponent operatorPhysics = mPhysics.get(operator);
		final PhysicsComponent vehiclePhysics = mPhysics.get(vehicle);

		return vehiclePhysics.getPosition().dst(operatorPhysics.getPosition());
	}

	private void enterVehicle(Entity o, Entity v) {

		logger.debug("Enter Vehicle");

		joinTeam(v, mTeam.get(o));
		if (App.engine.systems.player.isPlayer(o)) {
			moveControlTo(v);
		}
		stuffOperatorInTrunk(o, v);
	}

	private void moveControlTo(Entity v) {
		App.engine.systems.player.setPlayer(v);
	}

	private void stuffOperatorInTrunk(Entity o, Entity v) {
		VehicleOperatorComponent operator = mVehicleOperator.get(o);
		operator.enterVehicle = false;

		final VehicleComponent vehicle = mVehicle.get(v);
		vehicle.occupantData = App.engine.factory.serialize(o);
		o.deleteFromWorld();
	}

	private void joinTeam(Entity e, TeamComponent team) {
		if (team != null) {
			if (mTeam.has(e)) {
				mTeam.get(e).team = team.team;
			} else {
				e.edit().create(TeamComponent.class).team = team.team;
			}
		}
	}

}
