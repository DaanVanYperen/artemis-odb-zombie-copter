package com.deftwun.zombiecopter.components;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Pool.Poolable;

public class LedgeHangComponent extends PooledComponent {
	public boolean hanging,
				   climbing;
	public float climbSpeed;
	
	@Override
	public void reset() {
		hanging = false;
		climbing = false;
		climbSpeed = 1;
	}

}
