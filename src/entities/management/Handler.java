package entities.management;

import java.awt.Graphics;
import java.util.LinkedList;

import core.Main_Game;
import entities.Creature;
import entities.EntityTypes;
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
	}
	public void removeAllCreatures() {
		int CurrentNumberOfObjects = creatures.size();
		for (int i = 0; i < CurrentNumberOfObjects; i++) {
			Creature tempCreature = creatures.get(CurrentNumberOfObjects-i-1);
			removeCreature(tempCreature);
		}
	}
	
	public Creature creatureDamaged(int mx, int my) {
		int topLeftP[] = {game.characterX-game.TileWidth, game.characterY-game.TileHeight};
		int bottomRightP[] = {game.characterX+2*game.TileWidth, game.characterY+2*game.TileHeight};
		if (mx >= topLeftP[0] && mx <= bottomRightP[0] && my >= topLeftP[1] && my <= bottomRightP[1]) {
			for (Creature c : game.handler.creatures) {
				int creatureTopLeftP[] = {c.xPos, c.yPos};
				int creatureBottomRightP[] = {c.xPos+c.width, c.yPos+c.height};
				if (mx >= creatureTopLeftP[0] && mx <= creatureBottomRightP[0] && my >= creatureTopLeftP[1] && my <= creatureBottomRightP[1]) {
					return c;
				}
			}
		}
		return null;
	}
}
