package com.deftwun.zombiecopter.components;

import com.artemis.Component;
import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TeamComponent extends PooledComponent {
	public enum Team{
		PLAYER,
		NEUTRAL,
		ENEMY, 
		BULLET, 
		WILD
	}
	
	public Team team = Team.NEUTRAL;

	@Override
    public void reset() {
		team = Team.NEUTRAL;
	}
}
