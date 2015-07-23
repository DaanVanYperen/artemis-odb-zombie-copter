package com.deftwun.zombiecopter.systems;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.deftwun.zombiecopter.App;
import com.deftwun.zombiecopter.ComponentMappers;
import com.deftwun.zombiecopter.UserInterface;
import com.deftwun.zombiecopter.components.*;

@Wire
public class PlayerSystem extends BaseSystem implements InputProcessor {
	private Logger logger = new Logger("PlayerSystem",Logger.INFO);
	private Entity currentPlayer = null;

	protected ComponentMapper<Player> mPlayer;

	public PlayerSystem(){
		logger.debug("initializing");
	}

	public boolean isPlayer(Entity o) {
		return currentPlayer != null && currentPlayer.equals(o);
	}

	public void setPlayer(Entity e){

		if ( !mPlayer.has(e) ) {
			e.edit().create(Player.class);
		}

		currentPlayer = e;
		if (e != null)
			App.engine.systems.camera.setFollow(e);
	}
	
	public Entity getPlayer(){
		if (currentPlayer == null) logger.debug("currentPlayer == null");
		return currentPlayer;
	}
	
	@Override
	public void processSystem() {

		if (currentPlayer == null) return;
		ComponentMappers mappers = App.engine.mappers;
		Controller controller = mappers.controller.get(currentPlayer);
		Physics physics = mappers.physics.get(currentPlayer);

		//Set entity controller inputs
		if (controller == null) return;
		
		UserInterface ui = App.engine.ui;		
		controller.moveVector.set(ui.getMoveVector());
		controller.attack = ui.isFiring();
		
		if (ui.isTouchScreen) controller.lookVector.set(ui.getFireVector());
		else {
			if (physics == null) return;
			Vector2 worldCoords = App.engine.systems.camera.unproject(ui.getTouchPosition());
			worldCoords.scl(1/App.engine.PIXELS_PER_METER);
			controller.lookVector.set(worldCoords.sub(physics.getPosition()).nor());
		}
		
		//Set entity boundary around player
		if (physics == null) return;
		App.engine.entityBounds.setCenter(physics.getPosition());
		
	}

	//@todo migrate
	public void removed(Entity entity) {
		//If the current player is removed then current player = null;
		if (currentPlayer == entity){
			logger.debug("Current player has been removed: Entity#" + currentPlayer.getId());
			currentPlayer = null;
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (currentPlayer == null) return false;
		if (keycode == Keys.E){
			//TODO: this shoudl be driven by tghe 'action' switch in the controller component
			logger.debug("Vehicle Key pressed");
			Vehicle vehicle = App.engine.mappers.vehicle.get(currentPlayer);
			VehicleOperator operator = App.engine.mappers.vehicleOperator.get(currentPlayer);
			if (vehicle != null) vehicle.eject = true;
			if (operator != null) operator.enterVehicle = true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (currentPlayer == null) return false;
		if (keycode == Keys.E){
			logger.debug("Vehicle Key depressed");
			Vehicle vehicle = App.engine.mappers.vehicle.get(currentPlayer);
			VehicleOperator operator = App.engine.mappers.vehicleOperator.get(currentPlayer);
			if (vehicle != null) vehicle.eject = false;
			if (operator != null) operator.enterVehicle = false;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
