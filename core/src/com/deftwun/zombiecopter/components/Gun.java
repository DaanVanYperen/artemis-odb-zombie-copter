package com.deftwun.zombiecopter.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;

public class Gun extends PooledComponent {
	
	//public ObjectMap<String,Gun> availableGuns = new ObjectMap<String,Gun>();
	//public String selectedGun;	
	public float bulletSpeed = 5,
				 range = 5,
				 spreadAngle = 0,
				 time = 0,
		     	 cooldown = 2;	
	public String projectileName = "bullet";
	public Vector2 offset = new Vector2();
	public boolean triggerPulled = false;
	
	@Override
	public void reset() {
		range = 5;
		bulletSpeed = 5;
		spreadAngle = 0;
		time = 0;
		cooldown = 2;
		projectileName = "bullet";
		offset.set(0,0);
		triggerPulled = false;
	}  
	
}
