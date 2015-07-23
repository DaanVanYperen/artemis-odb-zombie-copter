package com.deftwun.zombiecopter.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.deftwun.zombiecopter.App;
import com.deftwun.zombiecopter.SpriteLayer;

public class Sprite extends PooledComponent implements Serializable{
	
	private static class Model{
				
		void fromSpriteComponent(Sprite component){
			layer = component.layer;
			for (ObjectMap.Entry<String, com.badlogic.gdx.graphics.g2d.Sprite> entry : component.spriteMap.entries()){
				FileTextureData data = (FileTextureData) entry.value.getTexture().getTextureData();
				spriteMap.put(entry.key,data.getFileHandle().name());
			}
		}
		
		ObjectMap<String,String> spriteMap = new ObjectMap<String,String>();
		SpriteLayer layer;
	}
	
	//TODO Each sprite should have its own layer
	public ObjectMap<String, com.badlogic.gdx.graphics.g2d.Sprite> spriteMap = new ObjectMap<String, com.badlogic.gdx.graphics.g2d.Sprite>();
	public SpriteLayer layer = SpriteLayer.Back;
	/*
	public boolean flipX = false,
			       flipY = false;
	*/
	@Override
	public void reset() {
		spriteMap.clear();
		layer = SpriteLayer.Back;
		/*
		flipX = false;
		flipY = false;
		*/
	}

	private void fromModel(Model model){
		layer = model.layer;
				
		for (ObjectMap.Entry<String,String> entry : model.spriteMap.entries()){
			Texture t = App.assets.getTexture(entry.value);	
			if (t != null){
				com.badlogic.gdx.graphics.g2d.Sprite sprite = new com.badlogic.gdx.graphics.g2d.Sprite(t);
				sprite.setOriginCenter();
				spriteMap.put(entry.key, sprite);
			}
		}
	}
	
	@Override
	public void write(Json json) {
		Model model = new Model();
		model.fromSpriteComponent(this);
		json.writeFields(model);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		Model model = json.fromJson(Model.class, jsonData.toString());
		this.fromModel(model);
	}
	
}

