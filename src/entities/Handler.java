package entities;

import java.awt.Graphics;
import java.util.LinkedList;

import core.Main_Game;
import tiles.BasicTiles;
import tiles.MiningTiles;

public class Handler {
	
	LinkedList<EntityObject> object = new LinkedList<EntityObject>();
	
	private Main_Game game;
	
	public Handler(Main_Game game) {
		this.game = game;
	}
	
	public void tick() {
		for (int i = 0; i < object.size(); i++) {
			EntityObject tempObject = object.get(i);
			tempObject.tick();
		}
	}
	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			EntityObject tempObject = object.get(i);
			tempObject.render(g);
		}
	}
	public void addObject(EntityObject object) {
		this.object.add(object);
	}
	public void removeObject(EntityObject object) {
		this.object.remove(object);
	}
	public void replaceTiles() {
		EntityObject newTiles = null;
		for (int i = 0; i < this.object.size(); i++) {
			EntityObject tempObject = this.object.get(i);
			if (tempObject.getId() == EntityTypes.Tiles) {
				if (game.dimension == 0) {
					newTiles = new BasicTiles(0, 0, EntityTypes.Tiles, game, this);
				}
				else if (game.dimension == 1) {
					newTiles = new MiningTiles(0, 0, EntityTypes.Tiles, game, this);
				}
				this.object.set(i, newTiles);
				game.AddDims();
			}
		}
	}
	public void removeAllObjects() {
		int CurrentNumberOfObjects = object.size();
		for (int i = 0; i < CurrentNumberOfObjects; i++) {
			EntityObject tempObject = object.get(CurrentNumberOfObjects-i-1);
			removeObject(tempObject);
		}
	}
}
