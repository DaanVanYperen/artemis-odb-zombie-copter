package com.deftwun.zombiecopter.components;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Pool.Poolable;

public class VehicleOperatorComponent  extends PooledComponent {

	public boolean enterVehicle = false;
	public float time = 0,
				 coolDown = .5f;
	
	@Override
	public void reset() {
		enterVehicle = false;
		time = 0;
		coolDown = .5f;
	}

}
