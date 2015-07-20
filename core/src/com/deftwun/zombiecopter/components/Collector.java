package com.deftwun.zombiecopter.components;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Collector extends PooledComponent {
	public int civilians = 0;
	public int maxCivilians = 10;

	@Override
	public void reset() {
		civilians = 0;
	}
	
}
