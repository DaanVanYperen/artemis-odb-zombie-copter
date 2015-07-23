package com.deftwun.zombiecopter.components;

import com.artemis.PooledComponent;

public class Team extends PooledComponent {
	public enum TeamType {
		PLAYER,
		NEUTRAL,
		ENEMY, 
		BULLET, 
		WILD
	}
	
	public TeamType teamType = TeamType.NEUTRAL;

	@Override
    public void reset() {
		teamType = TeamType.NEUTRAL;
	}
}
