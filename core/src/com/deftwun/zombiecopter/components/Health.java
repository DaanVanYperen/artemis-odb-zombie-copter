package com.deftwun.zombiecopter.components;

import com.artemis.PooledComponent;

public class Health extends PooledComponent {
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
