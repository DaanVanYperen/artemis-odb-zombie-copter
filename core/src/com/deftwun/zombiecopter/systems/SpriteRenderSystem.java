package com.deftwun.zombiecopter.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.deftwun.zombiecopter.App;
import com.deftwun.zombiecopter.SpriteLayer;
import com.deftwun.zombiecopter.components.Physics;
import com.deftwun.zombiecopter.components.Sprite;

public class SpriteRenderSystem extends EntityProcessingSystem {
	private Logger logger;
	private int LOG_LEVEL = Logger.INFO;
	
	private SpriteBatch batch;
	private ObjectMap<SpriteLayer,ObjectSet<com.badlogic.gdx.graphics.g2d.Sprite>> spriteLayers = new ObjectMap<SpriteLayer,ObjectSet<com.badlogic.gdx.graphics.g2d.Sprite>>();
	//private SpriteLayer[] layerNames = SpriteLayer.values();
	
	@SuppressWarnings("unchecked")
	public SpriteRenderSystem() {		
		super(Aspect.all(Physics.class, Sprite.class));
		logger = new Logger("SpriteRenderSystem",LOG_LEVEL);
		logger.debug("initializing");
		batch = new SpriteBatch();
		for (SpriteLayer l : SpriteLayer.values()){
			spriteLayers.put(l, new ObjectSet<com.badlogic.gdx.graphics.g2d.Sprite>());
		}
	}
	
	@Override
	protected void process(Entity entity) {
		Sprite spriteCom = App.engine.mappers.sprite.get(entity);
		Physics physics = App.engine.mappers.physics.get(entity);
		
		Vector2 pixelPosition;
		float angleDegrees;
				
		for (ObjectMap.Entry<String, com.badlogic.gdx.graphics.g2d.Sprite> entry: spriteCom.spriteMap.entries()){
			Body b = physics.getBody(entry.key);
			if (b == null){
				pixelPosition = physics.getPosition().scl(App.engine.PIXELS_PER_METER);
				angleDegrees = physics.getRotation();
			}
			else {
				pixelPosition = b.getWorldCenter().scl(App.engine.PIXELS_PER_METER);
				angleDegrees = b.getAngle() * MathUtils.radDeg;
			}
	
			com.badlogic.gdx.graphics.g2d.Sprite s = entry.value;
			s.setCenter(pixelPosition.x,pixelPosition.y);
			s.setRotation(angleDegrees);
			//s.setFlip(spriteCom.flipX,spriteCom.flipY);
		}
	}
	
	public void render(){
		logger.debug("Render");
		
		batch.setProjectionMatrix(App.engine.systems.camera.getCamera().combined);
		batch.begin();
		
		for (SpriteLayer l : SpriteLayer.values()){
			for (com.badlogic.gdx.graphics.g2d.Sprite s : spriteLayers.get(l)){
				//Camera Culling
				float spriteRadius = s.getWidth() < s.getHeight() ? s.getHeight() : s.getWidth();
				if (App.engine.systems.camera.getCamera().frustum.sphereInFrustum(s.getX(), s.getY(), 0, spriteRadius))
					{s.draw(batch);}
			}
		}
		batch.end();
	}

	@Override
	public void inserted(Entity entity) {
		Sprite spriteCom = App.engine.mappers.sprite.get(entity);
		if (spriteCom == null) return;
		logger.debug("Sprite added " + entity);
		for (com.badlogic.gdx.graphics.g2d.Sprite s : spriteCom.spriteMap.values()){
			spriteLayers.get(spriteCom.layer).add(s);
		}
	}

	@Override
	public void removed(Entity entity) {
		Sprite spriteCom = App.engine.mappers.sprite.get(entity);
		if (spriteCom == null) return;
		logger.debug("Sprite removed " + entity);
		for (com.badlogic.gdx.graphics.g2d.Sprite s : spriteCom.spriteMap.values()){
			spriteLayers.get(spriteCom.layer).remove(s);
		}
	}
	
}
