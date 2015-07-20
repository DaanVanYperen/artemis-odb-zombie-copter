package com.deftwun.zombiecopter.components;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CarComponent extends PooledComponent {

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
