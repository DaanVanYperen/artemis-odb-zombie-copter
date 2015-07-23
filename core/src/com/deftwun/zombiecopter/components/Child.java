package com.deftwun.zombiecopter.components;

import com.artemis.Entity;
import com.artemis.PooledComponent;

public class Child extends PooledComponent {
	public Entity parentEntity;

	@Override
	public void reset() {
		parentEntity = null;
	}
}