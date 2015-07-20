package com.deftwun.zombiecopter.components;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ChildComponent  extends PooledComponent {
	public Entity parentEntity;

	@Override
	public void reset() {
		parentEntity = null;
	}
}