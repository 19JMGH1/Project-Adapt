package entities.management;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.Main_Game;
import entities.EntityTypes;
import items.ItemIDs;

public class ItemDrops extends EntityObject{

	Handler handler;
	Main_Game game;

	BufferedImage items;
	private boolean removed = false;
	private int startingY = 0;

	private final int SpriteSheetWidth = 160;
	private final int SpriteSheetHeight = 160;

	private int counter = 5;

	private int[] spriteSheetXY = {0, 0};
	private boolean motionMode = false; //False means it jumped off the tile where it was collected and is bouncing from the tile. true means that it is getting sucked towards the player

	public ItemDrops(int x, int y, byte itemID, EntityTypes id, Main_Game game, Handler handler) {
		super(x, y, id);

		this.game = game;
		this.handler = handler;

		startingY = y;

		try {
			items = ImageIO.read(getClass().getResourceAsStream("/Inventory_Items.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}

		setVelX((int) (((Main_Game.WIDTH/200)*((Math.random())*2-1))));
		setVelY(-Main_Game.HEIGHT/20);

		//spriteSheetXY = Identifications.getInventoryItemsSheet(itemID);
		spriteSheetXY = ItemIDs.getSpriteSheet(itemID);
	}

	public void tick() {
		if (removed) {
			handler.removeObject(this);
		}

		x+=velX;
		y+=velY;

		if (game.WPressed && !game.SPressed) {
			if (game.collision.checkY(game.x, game.y, game.TileX, game.TileY, game.ChunkX, game.ChunkY, (int) game.VelY, game.inBoat)) {
				y += (game.TileHeight/8);
				startingY += (game.TileHeight/8);
			}
		}
		else if (game.SPressed && !game.WPressed) {
			if (game.collision.checkY(game.x, game.y, game.TileX, game.TileY, game.ChunkX, game.ChunkY, (int) game.VelY, game.inBoat)) {
				y += (-game.TileHeight/8);
				startingY += (-game.TileHeight/8);
			}
		}
		if (game.APressed && !game.DPressed) {
			if (game.collision.checkX(game.x, game.y, game.TileX, game.TileY, game.ChunkX, game.ChunkY, (int) game.VelX, game.inBoat)) {
				x += (game.TileWidth/8);
			}
		}
		else if (game.DPressed && !game.APressed) {
			if (game.collision.checkX(game.x, game.y, game.TileX, game.TileY, game.ChunkX, game.ChunkY, (int) game.VelX, game.inBoat)) {
				x += (-game.TileWidth/8);
			}
		}
		if (!motionMode) {
			setVelY(getVelY()+Main_Game.WIDTH/200);
			if (y > startingY) {
				setVelX(0);
				setVelY(0);
				counter--;
				if (counter <= 0) {
					motionMode = true;
				}
			}
		}
		else {
			/*if (aboveChar && (y >= game.characterY+game.CharacterWidth/4+game.TileHeight/8)) {
				removed = true;
			}
			else if (!aboveChar && (y <= game.characterY+game.CharacterWidth/4 && y <= game.characterY+game.CharacterWidth/5-game.TileHeight/8))
			{
				removed  = true;
			}

			if (leftOfChar && (x >= game.characterX+game.CharacterHeight/4 && x <= game.characterX+game.CharacterHeight/5+game.TileWidth/8)) {
				removed = true;
			}
			else if (!leftOfChar && (x <= game.characterX+game.CharacterHeight/4 && x >= game.characterX+game.CharacterHeight/5-game.TileWidth/8))
			{
				removed  = true;
			}
			*/
			if ((x >= game.characterX+game.CharacterWidth/5-5)&&(x <= game.characterX+game.CharacterWidth/5+game.TileWidth/3+5)&&(y >= game.characterY+game.CharacterHeight/5-5)&&(y <= game.characterY+game.CharacterHeight/5+game.TileHeight/3+5)) {
				removed  = true;
			}
			int velXVector = game.characterX+game.CharacterWidth/5-x; //This makes a velocity vector that always points to the player
			int velYVector = game.characterY+game.CharacterHeight/5-y;

			double lengthVector = Math.sqrt(Math.pow(velXVector, 2)+Math.pow(velYVector, 2));

			if ((game.WPressed && !game.SPressed)||(game.SPressed && !game.WPressed)) {
				velYVector = (int)((velYVector/lengthVector)*(game.TileWidth/3));
			}
			else {
				velYVector = (int)((velYVector/lengthVector)*(game.TileWidth/6));
			}
			if ((game.APressed && !game.DPressed)||(game.DPressed && !game.APressed)) {
				velXVector = (int)((velXVector/lengthVector)*(game.TileHeight/3));
			}
			else {
				velXVector = (int)((velXVector/lengthVector)*(game.TileHeight/6));
			}

			setVelX(velXVector);
			setVelY(velYVector);

		}
	}

	public void render(Graphics g) {
		g.drawImage(items, x, y, x+game.TileWidth/2, y+game.TileHeight/2, spriteSheetXY[0]*SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight, spriteSheetXY[0]*SpriteSheetWidth+SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetWidth+SpriteSheetHeight, null);
	}

}
