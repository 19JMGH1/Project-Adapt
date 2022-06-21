package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.Main_Game;

public class Pig extends Creature{
	
	private static final int numOfLimbs = 4;
	
	private WanderingBehaviour wanderer = new WanderingBehaviour();
	
	public Pig(Main_Game game, EntityTypes type, int x, int y, int chunkX, int chunkY, int tileX, int tileY) {
		super(game, type, x, y, chunkX, chunkY, tileX, tileY);
		maxRotation = 30;
		spriteSheetResolution[0] = 280;
		spriteSheetResolution[1] = 210;
		rotationPoints = new Point[4];
		aspectRatio = (spriteSheetResolution[0]/(spriteSheetResolution[1]+0.0));
		this.width = this.game.TileWidth*2;
		height = (int) (this.width/aspectRatio);
		rotationPoints[0] = new Point((int) (x+6.5*width/28), y+13*height/21);
		rotationPoints[1] = new Point((int) (x+8.5*width/28), y+15*height/21);
		rotationPoints[2] = new Point((int) (x+20.5*width/28), y+13*height/21);
		rotationPoints[3] = new Point((int) (x+22.5*width/28), y+15*height/21);
		BufferedImage PigSheet = null;
		try {
			PigSheet = ImageIO.read(getClass().getResourceAsStream("/Pig.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		body = PigSheet.getSubimage(0, 0, 280, 210);
		setupLimbs(PigSheet, 2, numOfLimbs);
	}
	
	public void tick() {
		aspectRatio = (spriteSheetResolution[0]/(spriteSheetResolution[1]+0.0));
		this.width = game.TileWidth;
		height = (int) (this.width/aspectRatio);
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

	public void render(Graphics g) {
		wanderer.randomWander();
		velX += wanderer.velX;
		velY += wanderer.velY;
		positionHandler();
		renderPos();
		rotationPoints[0] = new Point((int) (xPos+6.5*width/28), yPos+13*height/21);
		rotationPoints[1] = new Point((int) (xPos+8.5*width/28), yPos+15*height/21);
		rotationPoints[2] = new Point((int) (xPos+20.5*width/28), yPos+13*height/21);
		rotationPoints[3] = new Point((int) (xPos+22.5*width/28), yPos+15*height/21);
		g.drawImage(body, xPos, yPos, width, height, null);
//		g.setColor(Color.black);
//		g.fillRect(x+width, y, width, height);
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform a = g2d.getTransform();
		for (int i = 0; i < limbs.length; i++) {
			//BufferedImage im = rotateImage(limbs[i], (((i%2)*-2*rotations)+rotations), (int) rotationPoints[i].getX(), (int) rotationPoints[i].getY());
			g2d.rotate((((i%2)*-2*Math.toRadians(rotations))+Math.toRadians(rotations)), (int) rotationPoints[i].getX(), (int) rotationPoints[i].getY());
			g2d.drawImage(limbs[i], xPos, yPos-height/21, width, height, null);
			g2d.setTransform(a);
			//g.fillRect((int) rotationPoints[i].getX(), (int) rotationPoints[i].getY(), 2, 2);
		}
		velX -= wanderer.velX;
		velY -= wanderer.velY;
	}
}
