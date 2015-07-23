package com.deftwun.zombiecopter.components;

import com.artemis.PooledComponent;

public class VehicleOperator extends PooledComponent {

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
