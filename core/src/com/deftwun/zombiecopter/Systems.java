package com.deftwun.zombiecopter;

import com.artemis.WorldConfiguration;
import com.deftwun.zombiecopter.managers.CameraManager;
import com.deftwun.zombiecopter.managers.TeamManager;
import com.deftwun.zombiecopter.systems.*;
import com.deftwun.zombiecopter.systems.action.VehicleEjectSystem;
import com.deftwun.zombiecopter.systems.action.VehicleMountingSystem;
import com.deftwun.zombiecopter.systems.logic.LifetimeSystem;

public class Systems {

	public CameraManager camera = new CameraManager();
	public MoveSystem move = new MoveSystem();
	public PhysicsSystem physics = new PhysicsSystem();
	public PlayerSystem player = new PlayerSystem();
	public WeaponSystem weapon = new WeaponSystem();
	public LifetimeSystem lifetime = new LifetimeSystem();
	public SpriteRenderSystem spriteRender = new SpriteRenderSystem();
	public VisionSystem vision = new VisionSystem();
	public TeamManager team = new TeamManager();
	public CollectableSystem collectable = new CollectableSystem();
	public SpawnSystem spawn = new SpawnSystem();
	public CivilianDropOffSystem dropoff = new CivilianDropOffSystem();
	public DamageSystem damage = new DamageSystem();
	public ParticleSystem particle = new ParticleSystem();
	public VehicleEjectSystem vehicle = new VehicleEjectSystem();
	public VehicleMountingSystem vehicleMounting = new VehicleMountingSystem();
	public AgentSystem agent = new AgentSystem();
	
	public Systems(WorldConfiguration config){

		config.setSystem(physics);
		config.setSystem(agent);
		config.setManager(camera);
		config.setSystem(move);
		config.setSystem(player);
		config.setSystem(weapon);
		config.setSystem(lifetime);
		config.setSystem(vision);
		config.setSystem(spriteRender);
		config.setManager(team);
		config.setSystem(collectable);
		config.setSystem(spawn);
		config.setSystem(damage);
		config.setSystem(particle);
		config.setSystem(vehicle);
		config.setSystem(vehicleMounting);
	}
}
