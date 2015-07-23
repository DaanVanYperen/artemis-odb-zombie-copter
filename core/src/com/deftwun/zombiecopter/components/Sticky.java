package com.deftwun.zombiecopter.components;

import com.artemis.PooledComponent;

//Creates a weld joint when the defined fixture collides with another 
public class Sticky extends PooledComponent{
	public String fixtureName = "";
	public boolean enabled = true;
	
	@Override
	public void reset() {
		enabled = true;
		fixtureName = "";
	}
}