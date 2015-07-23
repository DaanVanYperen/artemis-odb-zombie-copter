package com.deftwun.zombiecopter.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;

public class Controller extends PooledComponent {
	
	public Vector2 moveVector = new Vector2(),
				   lookVector = new Vector2();
	public boolean attack,
				   action;
	
	@Override
	public void reset() {
		moveVector.set(0,0);
		lookVector.set(0,0);
		attack = false;
		action = false;
		
	}
	
}
