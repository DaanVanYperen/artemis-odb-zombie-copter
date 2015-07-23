package com.deftwun.zombiecopter.managers;

import com.artemis.Manager;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.deftwun.zombiecopter.components.Team;
import com.deftwun.zombiecopter.components.Team.TeamType;


//This class only serves to determine enemies of a given teamType.
public class TeamManager extends Manager {
	private ObjectMap<Team.TeamType,Array<TeamType>> enemyMap;
	
	public Array<TeamType> getEnemies(Team.TeamType t){
		return enemyMap.get(t);
	}
	
	public TeamManager(){
		enemyMap = new ObjectMap<Team.TeamType,Array<TeamType>>();
		for (Team.TeamType t : TeamType.values()){
			enemyMap.put(t, new Array<Team.TeamType>());
		}
		enemyMap.get(Team.TeamType.PLAYER).add(Team.TeamType.ENEMY);
		enemyMap.get(Team.TeamType.PLAYER).add(TeamType.WILD);
		
		enemyMap.get(Team.TeamType.ENEMY).add(TeamType.PLAYER);
		enemyMap.get(TeamType.ENEMY).add(TeamType.WILD);
		
		enemyMap.get(TeamType.WILD).add(TeamType.ENEMY);
		enemyMap.get(Team.TeamType.WILD).add(Team.TeamType.PLAYER);
		enemyMap.get(TeamType.WILD).add(TeamType.NEUTRAL);

	}

}
