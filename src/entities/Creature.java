package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.Main_Game;

public abstract class Creature {

	protected Main_Game game;

	protected EntityTypes type;
	protected int x, y, chunkX, chunkY, tileX, tileY; //Stores the position of the creature
	protected int velX = 0, velY = 0; //velocities that the creature is moving at.
	public int xPos, yPos; //Stores where on the screen to render the Creature using the above position values.
	protected boolean inBoat = false;
	public int width, height; //Stores the size of the creature when it appears on the screen
	protected int spriteSheetResolution[] = new int[2]; //Stores the resolution of the sprite sheet
	protected double aspectRatio; //Is the x resolution divided by the y resolution
	protected BufferedImage body = null; //Buffered image for the body of the creature
	protected BufferedImage[] limbs = null; //Buffered image array for all of the limbs of the creature
	protected int[][] limbLocations = null; //Stores the number of pixels from the left and how many pixels from the top to draw each limb
	protected int numOfLimbs = 0;
	protected Point[] rotationPoints = null; //The relative points in space that the creatures limbs rotate about
	protected int rotations = 0; //int that stores the angle of the limbs of the creature
	protected int maxRotation;
	protected boolean rotationDirection = false; //False is counterclockwise, true is clockwise
	protected boolean facingRight = false; //Makes the creature face the correct direction and it continues to face the direction after movement has stopped
	protected int[] spriteWidthHeight = null; //Stores how many pixels wide and tall the sprite is
	public int HP;
	public int[][] drops;
	public int[] knockbackVel = {0, 0}; //First index is for the x direction, second index is for the y direction.
	protected double sizeRelativeToTiles = 1.0;
	
	protected WanderingBehaviour wanderer = null;
	
	public Creature(Main_Game game, EntityTypes type, int x, int y, int chunkX, int chunkY, int tileX, int tileY, 
			int maxHP, int[][] itemDrops, int[] spriteRes, int numOfLimbs, double sizeRelativeToTiles,
			int[][] limbLocations, String imageFile, int spriteSheetRows, boolean isAWanderer) {
		this.game = game;
		this.type = type;
		this.x = x;
		this.y = y;
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.tileX = tileX;
		this.tileY = tileY;
		HP = maxHP;
		this.sizeRelativeToTiles = sizeRelativeToTiles;
		this.limbLocations = limbLocations;
		this.numOfLimbs = numOfLimbs;
		
		drops = itemDrops;
		spriteWidthHeight = new int[] {spriteRes[0]/10, spriteRes[1]/10};
		maxRotation = 30;
		spriteSheetResolution[0] = spriteRes[0];
		spriteSheetResolution[1] = spriteRes[1];
		rotationPoints = new Point[numOfLimbs];
		setAspectRatio();
		this.width = (int) (game.TileWidth*sizeRelativeToTiles);
		height = (int) (this.width/aspectRatio);
		for (int i = 0; i < numOfLimbs; i++) {
			rotationPoints[i] = new Point((int) (x+(limbLocations[i][0]+0.5)*width/spriteWidthHeight[0]), (int) (y+(limbLocations[i][1]+0.5)*height/spriteWidthHeight[1]));
		}
		BufferedImage SpriteSheet = null;
		try {
			SpriteSheet = ImageIO.read(getClass().getResourceAsStream(imageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		body = SpriteSheet.getSubimage(0, 0, spriteRes[0], spriteRes[1]);
		setupLimbs(SpriteSheet, spriteSheetRows, numOfLimbs);
		
		if (isAWanderer) {
			wanderer = new WanderingBehaviour();
		}
	}
	
	protected void setAspectRatio() {
		aspectRatio = (spriteSheetResolution[0]/(spriteSheetResolution[1]+0.0));
	}
	
	public void tick() {
		
		this.width = (int) (game.TileWidth*sizeRelativeToTiles);
		height = (int) (this.width/aspectRatio);
		if (chunkX-game.ChunkX+1 > 2 || game.ChunkY-chunkY+1 > 2 || chunkX-game.ChunkX+1 < 0 || game.ChunkY-chunkY+1 < 0) {
			game.handler.removeCreature(this);
			return;
		}
		if (HP <= 0) {
			game.handler.removeCreature(this);
		}
		if (knockbackVel[0] != 0) {
			if (knockbackVel[0] > 0) {
				knockbackVel[0]--;
			}
			else {
				knockbackVel[0]++;
			}
		}
		if (knockbackVel[1] != 0) {
			if (knockbackVel[1] > 0) {
				knockbackVel[1]--;
			}
			else {
				knockbackVel[1]++;
			}
		}
		aspectRatio = (spriteSheetResolution[0]/(spriteSheetResolution[1]+0.0));
		
		if ((velX+wanderer.velX) != 0 || (velY+wanderer.velY) != 0) {
			if ((velX+wanderer.velX) > 0) {
				facingRight = true;
			}
			else {
				facingRight = false;
			}
			if (rotationDirection) {
				rotations+=3;
				if (rotations >= maxRotation) {
					rotationDirection = !rotationDirection;
				}
			}
			else {
				rotations-=3;
				if (rotations <= -maxRotation) {
					rotationDirection = !rotationDirection;
				}
			}
		}
		else {
			rotations = 0;
		}
	}
	public void render(Graphics g) {
		if (wanderer != null) {
			wanderer.randomWander();
		}
		if (game.Paused == false) {
			positionHandler(velX+wanderer.velX+knockbackVel[0], velY+wanderer.velY+knockbackVel[1]);
		}
		renderPos();
		//System.out.println(xPos+", "+yPos);
		if (facingRight) {
			for (int i = 0; i < numOfLimbs; i++) {
				rotationPoints[i] = new Point((int) (xPos+width-(limbLocations[i][0]+0.5)*width/spriteWidthHeight[0]), (int) (yPos+limbLocations[i][1]*height/spriteWidthHeight[1]));
			}
		}
		else {
			for (int i = 0; i < numOfLimbs; i++) {
				rotationPoints[i] = new Point((int) (xPos+(limbLocations[i][0]+0.5)*width/spriteWidthHeight[0]), (int) (yPos+limbLocations[i][1]*height/spriteWidthHeight[1]));
			}
		}
		//		g.setColor(Color.black);
		//		g.fillRect(x+width, y, width, height);
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform a = g2d.getTransform();
		if (facingRight) {
			g.drawImage(body, xPos+width, yPos, -width, height, null);
		}
		else {
			g.drawImage(body, xPos, yPos, width, height, null);
		}
		for (int i = 0; i < limbs.length; i++) {
			//BufferedImage im = rotateImage(limbs[i], (((i%2)*-2*rotations)+rotations), (int) rotationPoints[i].getX(), (int) rotationPoints[i].getY());
			g2d.rotate((((i%2)*-2*Math.toRadians(rotations))+Math.toRadians(rotations)), (int) rotationPoints[i].getX(), (int) rotationPoints[i].getY());
			if (facingRight) {
				g2d.drawImage(limbs[i], xPos+width, yPos-height/21, -width, height, null);
			}
			else {
				g2d.drawImage(limbs[i], xPos, yPos-height/21, width, height, null);
			}
			g2d.setTransform(a);
			//g.fillRect((int) rotationPoints[i].getX(), (int) rotationPoints[i].getY(), 2, 2); //For testing where the rotation points were
		}
	}

	protected void renderPos() {
		xPos = (x-game.x)+(chunkX-game.ChunkX)*16*game.TileWidth+(tileX-game.TileX)*game.TileWidth+Main_Game.WIDTH/2-game.CharacterWidth/2;
		yPos = (game.y-y)+(game.ChunkY-chunkY)*16*game.TileHeight+(game.TileY-tileY)*game.TileHeight+Main_Game.HEIGHT/2-game.CharacterHeight/2;
	}

	protected void positionHandler(int velX, int velY) {
		if (game.collision.checkX(x, y, tileX, tileY, chunkX-game.ChunkX+1, game.ChunkY-chunkY+1, (int) velX, inBoat))
		{
			x += velX;
		}
		if (game.collision.checkY(x, y, tileX, tileY, chunkX-game.ChunkX+1, game.ChunkY-chunkY+1, (int) velY, inBoat))
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