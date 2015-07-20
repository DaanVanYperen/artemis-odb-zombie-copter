package com.deftwun.zombiecopter.components;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Pool.Poolable;

public class VehicleComponent extends PooledComponent {

	public String occupantData = "";
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
	
}
