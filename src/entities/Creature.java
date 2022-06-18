package entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import core.Main_Game;

public abstract class Creature {
	
	protected Main_Game game;
	
	protected EntityTypes type;
	protected int x, y, chunkX, chunkY, tileX, tileY; //Stores the position of the creature
	protected int width, height; //Stores the size of the creature when it appears on the screen
	protected int spriteSheetResolution[] = new int[2]; //Stores the resolution of the sprite sheet
	protected double aspectRatio; //Is the x resolution divided by the y resolution
	protected BufferedImage body = null; //Buffered image for the body of the creature
	protected BufferedImage[] limbs = null; //Buffered image array for all of the limbs of the creature
	protected Point[] rotationPoints = null; //The relative points in space that the creatures limbs rotate about
	protected int rotations = 0; //int that stores the angle of the limbs of the creature
	protected int maxRotation;
	protected boolean rotationDirection = false; //False is counterclockwise, true is clockwise
	
	
	
	public Creature(Main_Game game, EntityTypes type, int x, int y, int chunkX, int chunkY, int tileX, int tileY) {
		this.game = game;
		this.type = type;
		this.x = x;
		this.y = y;
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.tileX = tileX;
		this.tileY = tileY;
	}
	public abstract void tick();
	public abstract void render(Graphics g);
	
	protected void setupLimbs(BufferedImage img, int rows, int numOfLimbs) {
		limbs = new BufferedImage[numOfLimbs];
		for (int i = 1; i <= numOfLimbs; i++) { //This if statement is weird because the first img in a spritesheet is always the body
			limbs[i-1] = img.getSubimage(spriteSheetResolution[0]*(i%rows), spriteSheetResolution[1]*(i/rows), spriteSheetResolution[0], spriteSheetResolution[1]);
		}
	}
	
	/**
	 * Mirrors an image and returns an image as if the mirror was vertical
	 * @param image the buffered image to be mirrored
	 * @return the mirrored image
	 */
	protected BufferedImage mirrorImage(BufferedImage image) {
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-image.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return op.filter(image, null);
	}
	
	/**
	 * Rotates an image and returns a buffered image that is the rotated image
	 * @param image the image to be rotated
	 * @param rotationDegrees the rotation in degrees for the image to be rotated by
	 * @return the rotated image
	 */
	protected BufferedImage rotateImage(BufferedImage image, int rotationDegrees, int rotationX, int rotationY) {
		double rotationRequired = Math.toRadians (rotationDegrees);
		double locationX = image.getWidth() / 2;
		double locationY = image.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(image, null);
	}
}
