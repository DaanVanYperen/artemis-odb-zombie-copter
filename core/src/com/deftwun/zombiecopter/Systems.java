package com.deftwun.zombiecopter;

import com.artemis.Engine;
import com.artemis.WorldConfiguration;
import com.deftwun.zombiecopter.systems.*;

public class Systems {

	public CameraSystem camera = new CameraSystem();
	public MoveSystem move = new MoveSystem();
	public PhysicsSystem physics = new PhysicsSystem();
	public PlayerSystem player = new PlayerSystem();
	public WeaponSystem weapon = new WeaponSystem();
	public LifetimeSystem lifetime = new LifetimeSystem();
	public SpriteRenderSystem spriteRender = new SpriteRenderSystem();
	public VisionSystem vision = new VisionSystem();
	public TeamSystem team = new TeamSystem();
	public CollectableSystem collectable = new CollectableSystem();
	public SpawnSystem spawn = new SpawnSystem();
	public CivilianDropOffSystem dropoff = new CivilianDropOffSystem();
	public DamageSystem damage = new DamageSystem();
	public ParticleSystem particle = new ParticleSystem();
	public VehicleSystem vehicle = new VehicleSystem();
	public AgentSystem agent = new AgentSystem();
	
	public Systems(WorldConfiguration config){

		config.setSystem(physics);
		config.setSystem(agent);
		config.setSystem(camera);
		config.setSystem(move);
		config.setSystem(player);
		config.setSystem(weapon);
		config.setSystem(lifetime);
		config.setSystem(vision);
		config.setSystem(spriteRender);
		config.setSystem(team);
		config.setSystem(collectable);
		config.setSystem(spawn);
		config.setSystem(damage);
		config.setSystem(particle);
		config.setSystem(vehicle);
		
		config.addEntityListener(camera);
		config.addEntityListener(physics);
		config.addEntityListener(player);
		config.addEntityListener(spriteRender);

	}
}
