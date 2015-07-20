package com.deftwun.zombiecopter.components;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TimeToLiveComponent extends PooledComponent {
	public float time,timeLimit;
	

	@Override
	public void reset() {
		time = 0;
		timeLimit = 0;
	}
	
}
