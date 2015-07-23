package com.deftwun.zombiecopter;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.deftwun.zombiecopter.components.*;

public class ComponentMappers{

	public final ComponentMapper<Gun> gun;
	public final ComponentMapper<Jump> jump;
	public final ComponentMapper<LedgeHang> ledge;
	public final ComponentMapper<Look> look;
	public final ComponentMapper<Physics> physics;
	public final ComponentMapper<Player> player;
	public final ComponentMapper<Thruster> thrust;
	public final ComponentMapper<Walk> walk;
	public final ComponentMapper<Health> health;
	public final ComponentMapper<TimeToLive> timeToLive;
	public final ComponentMapper<Bullet> bullet;
	public final ComponentMapper<Helicopter> helicopter;
	public final ComponentMapper<Sprite> sprite;
	public final ComponentMapper<Team> team;
	public final ComponentMapper<Brain> brain;
	public final ComponentMapper<Collector> collector;
	public final ComponentMapper<Collectable> collectable;
	public final ComponentMapper<Vehicle> vehicle;
	public final ComponentMapper<VehicleOperator> vehicleOperator;
	public final ComponentMapper<Car> car;
	public final ComponentMapper<Controller> controller;
	public final ComponentMapper<Leader> leader;
	public final ComponentMapper<Child> child;
	public final ComponentMapper<Sticky> sticky;
	public final ComponentMapper<Melee> melee;

	public ComponentMappers(World world) {
		gun = ComponentMapper.getFor(Gun.class, world);
		jump = ComponentMapper.getFor(Jump.class, world);
		ledge = ComponentMapper.getFor(LedgeHang.class, world);
		look = ComponentMapper.getFor(Look.class, world);
		physics = ComponentMapper.getFor(Physics.class, world);
		player = ComponentMapper.getFor(Player.class, world);
		thrust = ComponentMapper.getFor(Thruster.class, world);
		walk = ComponentMapper.getFor(Walk.class, world);
		health = ComponentMapper.getFor(Health.class, world);
		timeToLive = ComponentMapper.getFor(TimeToLive.class, world);
		bullet = ComponentMapper.getFor(Bullet.class, world);
		helicopter = ComponentMapper.getFor(Helicopter.class, world);
		sprite = ComponentMapper.getFor(Sprite.class, world);
		team = ComponentMapper.getFor(Team.class, world);
		brain = ComponentMapper.getFor(Brain.class, world);
		collector = ComponentMapper.getFor(Collector.class, world);
		collectable = ComponentMapper.getFor(Collectable.class, world);
		vehicle = ComponentMapper.getFor(Vehicle.class, world);
		vehicleOperator = ComponentMapper.getFor(VehicleOperator.class, world);
		car = ComponentMapper.getFor(Car.class, world);
		controller = ComponentMapper.getFor(Controller.class, world);
		leader = ComponentMapper.getFor(Leader.class, world);
		child = ComponentMapper.getFor(Child.class, world);
		sticky = ComponentMapper.getFor(Sticky.class, world);
		melee = ComponentMapper.getFor(Melee.class, world);
	}
}

