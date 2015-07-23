package com.deftwun.zombiecopter.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;

public class DropOffPoint extends PooledComponent {

	public Vector2 	position = new Vector2();
	public float 	range = 1;

	public DropOffPoint() {
	}

	@Override
	protected void reset() {
		position.set(0,0);
		range=1;
	}
}
