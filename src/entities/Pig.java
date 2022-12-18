package entities;

import java.awt.Graphics;

import core.Main_Game;
import items.ItemIDs;

public class Pig extends Creature {

	private static final int numOfLimbs = 4;
	private static final int[][] limbLocations = {{6,13}, {8, 15}, {20, 13}, {22, 15}};
	private static final int maxHP = 20;
	
	private static final int[] spriteRes = {280, 210}; //Sprite resolution
	private static final double sizeRelativeToTiles = 1.0; //This number is multiplied by the size of a tile to get the size of the creature
	
	private static final String imageFile = "/Pig.png";
	private static final int spriteSheetRows = 2;
	
	private static final int[][] itemDrops = {{ItemIDs.Meat.ordinal(), 2}};
	
	private static final boolean isAWanderer = true;

	public Pig(Main_Game game, EntityTypes type, int x, int y, int chunkX, int chunkY, int tileX, int tileY) {
		super(game, type, x, y, chunkX, chunkY, tileX, tileY, maxHP, 
				itemDrops, spriteRes, numOfLimbs, sizeRelativeToTiles,
				limbLocations, imageFile, spriteSheetRows, isAWanderer);
	}

	public void tick() {
		super.tick();
	}

	public void render(Graphics g) {
		super.render(g);
		//System.out.println(knockbackVel[0]+", "+knockbackVel[1]);
	}
}
