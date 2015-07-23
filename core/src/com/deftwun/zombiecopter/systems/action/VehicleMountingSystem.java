package com.deftwun.zombiecopter.systems.action;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.utils.Logger;
import com.deftwun.zombiecopter.App;
import com.deftwun.zombiecopter.components.Physics;
import com.deftwun.zombiecopter.components.Team;
import com.deftwun.zombiecopter.components.Vehicle;
import com.deftwun.zombiecopter.components.VehicleOperator;
import net.mostlyoriginal.api.system.core.DualEntityProcessingSystem;

/**
 * @author Daan van Yperen
 */
@Wire
public class VehicleMountingSystem extends DualEntityProcessingSystem {

	public static final int MAX_VEHICLE_ENTRY_DISTANCE = 3;

	private Logger logger = new Logger("VehicleMountingSystem", Logger.INFO);

	private ComponentMapper<VehicleOperator> mVehicleOperator;
	private ComponentMapper<Vehicle> mVehicle;
	private ComponentMapper<Physics> mPhysics;
	protected ComponentMapper<Team> mTeam;

	public VehicleMountingSystem() {
		super(Aspect.all(Vehicle.class), Aspect.all(VehicleOperator.class));
	}

	@Override
	protected void process(Entity vehicle, Entity operator) {
		final VehicleOperator vehicleOperator = this.mVehicleOperator.get(operator);
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
		final Physics operatorPhysics = mPhysics.get(operator);
		final Physics vehiclePhysics = mPhysics.get(vehicle);

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
		VehicleOperator operator = mVehicleOperator.get(o);
		operator.enterVehicle = false;

		final Vehicle vehicle = mVehicle.get(v);
		vehicle.occupantData = App.engine.factory.serialize(o);
		o.deleteFromWorld();
	}

	private void joinTeam(Entity e, Team team) {
		if (team != null) {
			if (mTeam.has(e)) {
				mTeam.get(e).teamType = team.teamType;
			} else {
				e.edit().create(Team.class).teamType = team.teamType;
			}
		}
	}

}
