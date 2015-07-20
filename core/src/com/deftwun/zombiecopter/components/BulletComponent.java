package com.deftwun.zombiecopter.components;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Pool.Poolable;

public class BulletComponent  extends PooledComponent {
	public float damage = 1;

	@Override
	public void reset() {
		damage = 1;
	}
	
	
}
