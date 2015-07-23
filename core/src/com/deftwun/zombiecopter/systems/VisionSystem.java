package com.deftwun.zombiecopter.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Logger;
import com.deftwun.zombiecopter.App;
import com.deftwun.zombiecopter.components.Controller;
import com.deftwun.zombiecopter.components.Look;
import com.deftwun.zombiecopter.components.Physics;
import com.deftwun.zombiecopter.components.Sprite;

public class VisionSystem extends EntityProcessingSystem{
     
    private Logger logger;
    private int LOG_LEVEL = Logger.INFO;
	
    @SuppressWarnings("unchecked")
    public VisionSystem(){
        super(Aspect.all(Physics.class, Look.class));
        logger = new Logger("VisionSystem",LOG_LEVEL);
        logger.debug("initializing");
    }
     
    @Override
    protected void process(Entity entity) {
        Physics phys = App.engine.mappers.physics.get(entity);
		Controller controller = App.engine.mappers.controller.get(entity);
        Look look = App.engine.mappers.look.get(entity);
        Sprite sprite = App.engine.mappers.sprite.get(entity);
				
        if (look.isSweeping){
        	if (look.sweepAcc >= look.sweepAngle){
        		look.reverseSweep = !look.reverseSweep;
        		look.sweepAcc = 0;
        	}
        	float rate = look.sweepRate * world.delta;
        	if (look.reverseSweep) look.direction.rotate(rate * -1);
        	else look.direction.rotate(rate);
        	look.sweepAcc += rate;
        }
		else {
			if (controller != null)	look.direction.set(controller.lookVector).nor();
		}
        
        //Sprite flipping
        //TODO: Make sure no other systems flip the sprite after this
        if (look.controlSpriteFlip && sprite != null){
        	com.badlogic.gdx.graphics.g2d.Sprite visionSprite = sprite.spriteMap.get("visionBody");
        	for (com.badlogic.gdx.graphics.g2d.Sprite s : sprite.spriteMap.values()){
        		if (s == visionSprite){
        			if (look.direction.angle() > 90 && look.direction.angle() <  270)
        				visionSprite.setFlip(false, true);
        			else visionSprite.setFlip(false, false);
        		}
        		else {
        			if (look.direction.angle() > 90 && look.direction.angle() <  270)
        				s.setFlip(true,false);
        			else s.setFlip(false,false);
        		}
        	}
        }
        
        look.position.set(phys.getPosition());
		Body visionCone = phys.getBody("visionBody");
		if (visionCone != null){
			visionCone.setTransform(phys.getPosition(), look.direction.angleRad());
		}
    }
}