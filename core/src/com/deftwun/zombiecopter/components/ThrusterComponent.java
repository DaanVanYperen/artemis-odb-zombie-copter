package com.deftwun.zombiecopter.components;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ThrusterComponent extends PooledComponent {
	public float power,
				 topSpeed,
				 duration,
				 timeThrusting,
				 timeOffGround,
				 delay;
	public Vector2 vector = new Vector2();
	
	@Override
	public void reset() {
		power = 0;
		topSpeed = 0;
		duration = 0;
		timeThrusting = 0;
		timeOffGround = 0;
		delay = 0;
		vector.set(0,0);
	}
}
