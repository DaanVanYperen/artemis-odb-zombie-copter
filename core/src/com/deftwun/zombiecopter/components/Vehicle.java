package com.deftwun.zombiecopter.components;

import com.artemis.PooledComponent;

public class Vehicle extends PooledComponent {

	public static final String NO_OCCUPANT = "";
	public String occupantData = NO_OCCUPANT;
	public float time = 0,
				 coolDown = .5f;
	public boolean eject = false;
	
	
	@Override
	public void reset() {
		occupantData = "";
		coolDown = .5f;
		time = 0;
		eject = false;
	}

	public boolean isOccupied() {
		return occupantData != null && !occupantData.isEmpty();
	}
}
