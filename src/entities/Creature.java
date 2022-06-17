package entities;

import java.awt.image.BufferedImage;

public abstract class Creature {
	
	protected int x, y, chunkX, chunkY, tileX, tileY; //Stores the position of the creature
	protected BufferedImage body; //Buffered image for the body of the creature
	protected BufferedImage[] limbs; //Buffered image array for all of the limbs of the creature
	protected int[] rotations; //int that stores the angle of the limbs of the creature
	
	public abstract void tick();
	public abstract void render();
	
}
