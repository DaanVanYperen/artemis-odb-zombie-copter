package com.deftwun.zombiecopter.components;

import com.artemis.Entity;
import com.artemis.PooledComponent;

//Creates a weld joint when the defined fixture collides with another 
public class Melee extends PooledComponent {
	public float damage, 
				 range,
				 time,
				 coolDown;
	
	public boolean triggerPulled = false;
	public Entity target;
	
	@Override
	public void reset() {
		damage = 0;
		range = 0;
		time = 0;
		coolDown = 0;
		triggerPulled = false;	
		target = null;
	}
}