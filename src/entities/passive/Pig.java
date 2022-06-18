package entities.passive;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.Main_Game;
import entities.Creature;
import entities.EntityTypes;

public class Pig extends Creature{
	
	private static final int numOfLimbs = 4;
	
	public Pig(Main_Game game, EntityTypes type, int x, int y, int chunkX, int chunkY, int tileX, int tileY) {
		super(game, type, x, y, chunkX, chunkY, tileX, tileY);
		maxRotation = 30;
		spriteSheetResolution[0] = 280;
		spriteSheetResolution[1] = 210;
		rotationPoints = new Point[4];
		aspectRatio = (spriteSheetResolution[0]/(spriteSheetResolution[1]+0.0));
		this.width = this.game.TileWidth;
		height = (int) (this.width*aspectRatio);
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
		this.width = this.game.TileWidth/10;
		height = (int) (this.width/aspectRatio);
		rotationPoints[0] = new Point(x+(width/28)*7, y+(height/21)*13);
		rotationPoints[1] = new Point(x+(width/28)*9, y+(height/21)*15);
		rotationPoints[2] = new Point(x+(width/28)*21, y+(height/21)*13);
		rotationPoints[3] = new Point(x+(width/28)*23, y+(height/21)*15);
		if (rotationDirection) {
			rotations++;
			if (rotations >= maxRotation) {
				rotationDirection = !rotationDirection;
			}
		}
		else {
			rotations--;
			if (rotations <= -maxRotation) {
				rotationDirection = !rotationDirection;
			}
		}
	}

	public void render(Graphics g) { //TODO make a separate image for each limb of the creature. This will make rotating the limbs easier and it will look better.
		g.drawImage(body, x, y, x+width, y+height, null);
		for (int i = 0; i < limbs.length; i++) {
			BufferedImage im = rotateImage(limbs[i], (((i%2)*-2*rotations)+rotations), (int) rotationPoints[i].getX(), (int) rotationPoints[i].getY());
			g.drawImage(im, x, y, x+width, y+height, null);
		}
	}

}
