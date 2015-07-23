package com.deftwun.zombiecopter.components;

import com.artemis.PooledComponent;

public class LedgeHang extends PooledComponent {
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
