package entities;

import java.awt.Graphics;
import java.util.LinkedList;

import core.Main_Game;
import tiles.BasicTiles;
import tiles.MiningTiles;

public class Handler {
	
	LinkedList<EntityObject> objects = new LinkedList<EntityObject>();
	
	LinkedList<Creature> creatures = new LinkedList<Creature>();
	
	private Main_Game game;
	
	public Handler(Main_Game game) {
		this.game = game;
	}
	
	public void tick() {
		for (int i = 0; i < objects.size(); i++) {
			EntityObject tempObject = objects.get(i);
			tempObject.tick();
		}
		for (int i = 0; i < creatures.size(); i++) {
			Creature tempObject = creatures.get(i);
			tempObject.tick();
		}
	}
	public void render(Graphics g) {
		for (int i = 0; i < objects.size(); i++) {
			EntityObject tempObject = objects.get(i);
			tempObject.render(g);
		}
		for (int i = 0; i < creatures.size(); i++) {
			Creature tempObject = creatures.get(i);
			tempObject.render(g);
		}
	}
	public void addObject(EntityObject object) {
		this.objects.add(object);
	}
	public void removeObject(EntityObject object) {
		this.objects.remove(object);
	}
	public void addCreature(Creature c) {
		this.creatures.add(c);
	}
	public void removeCreature(Creature c) {
		this.creatures.remove(c);
	}
	public void replaceTiles() {
		EntityObject newTiles = null;
		for (int i = 0; i < this.objects.size(); i++) {
			EntityObject tempObject = this.objects.get(i);
			if (tempObject.getId() == EntityTypes.Tiles) {
				if (game.dimension == 0) {
					newTiles = new BasicTiles(0, 0, EntityTypes.Tiles, game, this);
				}
				else if (game.dimension == 1) {
					newTiles = new MiningTiles(0, 0, EntityTypes.Tiles, game, this);
				}
				this.objects.set(i, newTiles);
				game.AddDims();
			}
		}
	}
	public void removeAllEntities() {
		int CurrentNumberOfObjects = objects.size();
		for (int i = 0; i < CurrentNumberOfObjects; i++) {
			EntityObject tempObject = objects.get(CurrentNumberOfObjects-i-1);
			removeObject(tempObject);
		}
		CurrentNumberOfObjects = creatures.size();
		for (int i = 0; i < creatures.size(); i++) {
			Creature tempCreature = creatures.get(CurrentNumberOfObjects-i-1);
			removeCreature(tempCreature);
		}
	}
}
