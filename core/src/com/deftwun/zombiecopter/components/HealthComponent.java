package com.deftwun.zombiecopter.components;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Pool.Poolable;

public class HealthComponent extends PooledComponent {
	public float max = 10,
				 value = 10,
				 collisionDamageThreshold = 1f,
				 dropRate = 1; // chance that drop will happen; 1 == 100%, .5 == 50%, etc...
	
	public boolean isHurt = false,
				   isDead = false;
				   
	public String corpse = "",
				  drop = "",
				  damageEffect = "",
				  deathEffect = "",
				  gibbedEffect = "";
	
	@Override
	public void reset() {
		max = 10;
		value = 10;
		collisionDamageThreshold = 1;
		isHurt = false;
		isDead = false;
		corpse = "";
		drop = "";
		damageEffect = "";
		deathEffect = "";
		gibbedEffect = "";
	}
}
