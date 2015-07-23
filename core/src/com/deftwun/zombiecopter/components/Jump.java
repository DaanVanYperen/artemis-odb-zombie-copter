package com.deftwun.zombiecopter.components;

import com.artemis.PooledComponent;

public class Jump extends PooledComponent {
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
