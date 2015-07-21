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
	public static final Aspect.Builder VEHICLE_ASPECTS = Aspect.all(VehicleComponent.class);
	public static final Aspect.Builder VEHICLE_OPERATOR_ASPECTS = Aspect.all(VehicleOperatorComponent.class);
	private Logger logger = new Logger("VehicleSystem",Logger.INFO);

	private Entity vehicleFly;
	private Entity occupantFly;

	public VehicleSystem() {
		// ideally this would be a BaseSystem, but can't access flyweights from there (yet).
		super(null);
		logger.debug("Initializing");
	}

	@Override
	protected void initialize() {
		super.initialize();
		vehicleFly = createFlyweightEntity();
		occupantFly = createFlyweightEntity();
	}

	private void enterVehicle(Entity o, Entity v){
		logger.debug("Enter Vehicle");
		GameEngine engine = App.engine;
		ComponentMappers mappers = engine.mappers;
		
		VehicleComponent vehicle = mappers.vehicle.get(v);		
		VehicleOperatorComponent operator = mappers.vehicleOperator.get(o);
		
		//Vehicle takes on operator team
		TeamComponent operatorTeam = mappers.team.get(o),
		              vehicleTeam = mappers.team.get(v);
		if (operatorTeam != null) {
			if (vehicleTeam == null){
				vehicleTeam = App.engine.createComponent(TeamComponent.class);
				vehicleTeam.team = operatorTeam.team;
				v.edit().add(vehicleTeam);
			}
			else {
				vehicleTeam.team = operatorTeam.team;
			}
		}
		
		operator.enterVehicle = false;
		vehicle.occupantData = App.engine.factory.serialize(o);
		if (App.engine.systems.player.getPlayer() == o){
			PlayerComponent p = engine.createComponent(PlayerComponent.class);
			v.edit().add(p);
			engine.systems.player.setPlayer(v);
		}
		engine.removeEntity(o);
	}
	
	private void ejectOccupant(Entity e){
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
		if (occupantPhys != null){
			occupantPhys.setPosition(physics.getPosition());
			occupantPhys.setLinearVelocity(physics.getLinearVelocity());
		}
		engine.addEntity(occupant);
		App.engine.systems.player.setPlayer(occupant);
		
		vehicle.occupantData = "";
	}
	
	public void processSystem(){

		IntBag vehicles = App.engine.getEntitiesFor(VEHICLE_ASPECTS);

		ejectOccupantsFromExplodingCars(vehicles);
		enterVehicles(vehicles);
	}

	private void ejectOccupantsFromExplodingCars(IntBag vehicles) {
		ComponentMappers mappers = App.engine.mappers;
		//Vehicles
		for (int vid : vehicles.getData()){
			vehicleFly.id=vid;

			VehicleComponent vehicle = mappers.vehicle.get(vehicleFly);
			HealthComponent health = mappers.health.get(vehicleFly);

			if ((vehicle.eject && !vehicle.occupantData.equals("")) || (health != null && health.value <= 0))
			{
				ejectOccupant(vehicleFly);
			}
		}
	}

	private void enterVehicles(IntBag vehicles) {
		ComponentMappers mappers = App.engine.mappers;
		IntBag operators = App.engine.getEntitiesFor(VEHICLE_OPERATOR_ASPECTS);
		//Operators
		for (int oid : operators.getData()){
			occupantFly.id = oid;

			final VehicleOperatorComponent operator = mappers.vehicleOperator.get(occupantFly);
			final PhysicsComponent operatorPhysics = mappers.physics.get(occupantFly);

			for (int vid : vehicles.getData()){
				this.vehicleFly.id=vid;

				final PhysicsComponent vehiclePhysics = mappers.physics.get(vehicleFly);
				float vehicleRange = vehiclePhysics.getPosition().dst(operatorPhysics.getPosition());
				if (vehicleRange <= 3 && operator.enterVehicle){
					enterVehicle(occupantFly,vehicleFly);
					break;
				}
			}
		}
	}
}
