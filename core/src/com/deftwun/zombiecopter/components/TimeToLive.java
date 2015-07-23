package com.deftwun.zombiecopter.components;

import com.artemis.PooledComponent;

public class TimeToLive extends PooledComponent {
	public float time;

	@Override
	public void reset() {
		time = 0;
	}
	
}
