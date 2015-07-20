package com.deftwun.zombiecopter;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.deftwun.zombiecopter.components.*;

public class ComponentMappers{

	public final ComponentMapper<GunComponent> gun;
	public final ComponentMapper<JumpComponent> jump;
	public final ComponentMapper<LedgeHangComponent> ledge;
	public final ComponentMapper<LookComponent> look;
	public final ComponentMapper<PhysicsComponent> physics;
	public final ComponentMapper<PlayerComponent> player;
	public final ComponentMapper<ThrusterComponent> thrust;
	public final ComponentMapper<WalkComponent> walk;
	public final ComponentMapper<HealthComponent> health;
	public final ComponentMapper<TimeToLiveComponent> timeToLive;
	public final ComponentMapper<BulletComponent> bullet;
	public final ComponentMapper<HelicopterComponent> helicopter;
	public final ComponentMapper<SpriteComponent> sprite;
	public final ComponentMapper<TeamComponent> team;
	public final ComponentMapper<BrainComponent> brain;
	public final ComponentMapper<Collector> collector;
	public final ComponentMapper<Collectable> collectable;
	public final ComponentMapper<VehicleComponent> vehicle;
	public final ComponentMapper<VehicleOperatorComponent> vehicleOperator;
	public final ComponentMapper<CarComponent> car;
	public final ComponentMapper<ControllerComponent> controller;
	public final ComponentMapper<LeaderComponent> leader;
	public final ComponentMapper<ChildComponent> child;
	public final ComponentMapper<StickyComponent> sticky;
	public final ComponentMapper<MeleeComponent> melee;

	public ComponentMappers(World world) {
		gun = ComponentMapper.getFor(GunComponent.class, world);
		jump = ComponentMapper.getFor(JumpComponent.class, world);
		ledge = ComponentMapper.getFor(LedgeHangComponent.class, world);
		look = ComponentMapper.getFor(LookComponent.class, world);
		physics = ComponentMapper.getFor(PhysicsComponent.class, world);
		player = ComponentMapper.getFor(PlayerComponent.class, world);
		thrust = ComponentMapper.getFor(ThrusterComponent.class, world);
		walk = ComponentMapper.getFor(WalkComponent.class, world);
		health = ComponentMapper.getFor(HealthComponent.class, world);
		timeToLive = ComponentMapper.getFor(TimeToLiveComponent.class, world);
		bullet = ComponentMapper.getFor(BulletComponent.class, world);
		helicopter = ComponentMapper.getFor(HelicopterComponent.class, world);
		sprite = ComponentMapper.getFor(SpriteComponent.class, world);
		team = ComponentMapper.getFor(TeamComponent.class, world);
		brain = ComponentMapper.getFor(BrainComponent.class, world);
		collector = ComponentMapper.getFor(Collector.class, world);
		collectable = ComponentMapper.getFor(Collectable.class, world);
		vehicle = ComponentMapper.getFor(VehicleComponent.class, world);
		vehicleOperator = ComponentMapper.getFor(VehicleOperatorComponent.class, world);
		car = ComponentMapper.getFor(CarComponent.class, world);
		controller = ComponentMapper.getFor(ControllerComponent.class, world);
		leader = ComponentMapper.getFor(LeaderComponent.class, world);
		child = ComponentMapper.getFor(ChildComponent.class, world);
		sticky = ComponentMapper.getFor(StickyComponent.class, world);
		melee = ComponentMapper.getFor(MeleeComponent.class, world);
	}
}

