package com.deftwun.zombiecopter.components;

import com.artemis.PooledComponent;

public class Bullet extends PooledComponent {
	public float damage = 1;

	@Override
	public void reset() {
		damage = 1;
	}
	
	
}
