package com.deftwun.zombiecopter.components;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Pool.Poolable;

public class JumpComponent extends PooledComponent {
	public float power,
				 coolDown,
				 timeSinceJump;
	public boolean activated;
	
	@Override
	public void reset() {
		power = 0;
		coolDown = 0;
		timeSinceJump = 0;
		activated = false;
	}
}
