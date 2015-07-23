package com.deftwun.zombiecopter.components;

import com.artemis.PooledComponent;

public class Car extends PooledComponent {

	public float speed = 0,
				 downForce = 0;
	public boolean frontWheelDrive = true,
				   rearWheelDrive = true;
	//public Vector2 moveVector = new Vector2();
	
	@Override
	public void reset() {
		speed = 0;
		downForce=0;
		frontWheelDrive = true;
		rearWheelDrive = true;
		//moveVector.set(0,0);
	}

}
