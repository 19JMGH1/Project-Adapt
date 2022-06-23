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
	protected int velX = 0, velY = 0; //velocities that the creature is moving at.
	public int xPos, yPos; //Stores where on the screen to render the Creature using the above position values.
	public int width, height; //Stores the size of the creature when it appears on the screen
	protected int spriteSheetResolution[] = new int[2]; //Stores the resolution of the sprite sheet
	protected double aspectRatio; //Is the x resolution divided by the y resolution
	protected BufferedImage body = null; //Buffered image for the body of the creature
	protected BufferedImage[] limbs = null; //Buffered image array for all of the limbs of the creature
	protected Point[] rotationPoints = null; //The relative points in space that the creatures limbs rotate about
	protected int rotations = 0; //int that stores the angle of the limbs of the creature
	protected int maxRotation;
	protected boolean rotationDirection = false; //False is counterclockwise, true is clockwise
	protected boolean facingRight = false; //Makes the creature face the correct direction and it continues to face the direction after movement has stopped
	public int HP;

	public Creature(Main_Game game, EntityTypes type, int x, int y, int chunkX, int chunkY, int tileX, int tileY, int maxHP) {
		this.game = game;
		this.type = type;
		this.x = x;
		this.y = y;
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.tileX = tileX;
		this.tileY = tileY;
		HP = maxHP;
	}
	public void tick() {
		if (HP <= 0) {
			game.handler.removeCreature(this);
		}
	}
	public abstract void render(Graphics g);

	protected void renderPos() {
		xPos = (x-game.x)+(chunkX-game.ChunkX)*16*game.TileWidth+(tileX-game.TileX)*game.TileWidth+Main_Game.WIDTH/2-game.CharacterWidth/2;
		yPos = (game.y-y)+(game.ChunkY-chunkY)*16*game.TileHeight+(game.TileY-tileY)*game.TileHeight+Main_Game.HEIGHT/2-game.CharacterHeight/2;
	}

	protected void positionHandler(int velX, int velY) {
		if (game.collision.checkX(x, y, tileX, tileY, chunkX-game.ChunkX+1, game.ChunkY-chunkY+1, (int) velX))
		{
			x += velX;
		}
		if (game.collision.checkY(x, y, tileX, tileY, chunkX-game.ChunkX+1, game.ChunkY-chunkY+1, (int) velY))
		{
			y += velY;
		}
		//Changes the player's tile position every time it crosses a tile border
		if (x < 0) {
			tileX--;
			x += game.TileWidth;
			//System.out.println(TileX);
		}
		else if (x >= game.TileWidth) {
			tileX++;
			x -= game.TileWidth;
			//System.out.println(TileX);
		}

		if (y < 0) {
			tileY--;
			y += game.TileHeight;
			//System.out.println(TileY);
		}
		else if (y >= game.TileHeight) {
			tileY++;
			y -= game.TileHeight;
			//System.out.println(TileY);
		}
		if (tileY <= -16) {
			chunkY--;
			tileY += 16;
		}
		else if (tileY > 0) {
			chunkY++;
			tileY -= 16;
		}
		if (tileX >= 16) {
			chunkX++;
			tileX -= 16;
		}
		else if (tileX < 0) {
			chunkX--;
			tileX += 16;
		}
	}

	protected void setupLimbs(BufferedImage img, int rows, int numOfLimbs) {
		limbs = new BufferedImage[numOfLimbs];
		for (int i = 1; i <= numOfLimbs; i++) {
			limbs[i-1] = img.getSubimage(spriteSheetResolution[0]*(i%rows), spriteSheetResolution[1]*(i/rows), spriteSheetResolution[0], spriteSheetResolution[1]);
		}
		//		for (int i = 0; i < numOfLimbs; i++) {
		//			try {
		//				limbs[i] = ImageIO.read(getClass().getResourceAsStream("/PigLeg.png"));
		//			} catch (IOException e) {
		//				e.printStackTrace();
		//			}
		//		}
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
		//		double locationX = image.getWidth() / 2;
		//		double locationY = image.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, rotationX, rotationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(image, null);
	}
}

class WanderingBehaviour {
	public boolean wandering = false;
	public int velX = 0;
	public int velY = 0;
	public short wanderingTime = 0; //This time is measured in ticks
	
	public void randomWander() {
		if (!wandering) {
			int rand = Main_Game.randomNum(0, 125);
			if (rand == 75) {
				wandering = true;
				wanderingTime = (short) Main_Game.randomNum(3*Main_Game.Target_TPS, 6*Main_Game.Target_TPS);
				velX = Main_Game.randomNum(-Main_Game.WIDTH/400, Main_Game.WIDTH/400);
				velY = Main_Game.randomNum(-Main_Game.HEIGHT/400, Main_Game.HEIGHT/400);
			}
		}
		if (wanderingTime > 0) {
			wanderingTime--;
			if (wanderingTime == 0) {
				velX = 0;
				velY = 0;
				wandering = false;
			}
		}
	}
}