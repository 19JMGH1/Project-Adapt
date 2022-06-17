package tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.EntityTypes;
import entities.EntityObject;
import entities.Handler;
import core.Main_Game;

public class MiningTiles extends EntityObject{

	private Main_Game game;
	private Handler handler;

	private BufferedImage MiningSprite;

	private boolean removed = false;

	private final int SpriteSheetWidth = 320;
	private final int SpriteSheetHeight = 320;

	private int SpriteSheetLocs[] = new int[2];

	public MiningTiles(int x, int y, EntityTypes id, Main_Game game, Handler handler) {
		super(x, y, id);

		this.game = game;
		this.handler = handler;

		removed = false;

		try {
			MiningSprite = ImageIO.read(getClass().getResourceAsStream("/Mining_Tiles.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		if (game.dimension != 1) {
			removed = true;
		}
		if (removed) {
			handler.replaceTiles();
		}

		if (game.resized) {
			game.TileWidth = Main_Game.WIDTH/18;
			game.TileHeight = game.TileWidth;
		}
		this.x = (Main_Game.WIDTH/2)-game.CharacterWidth/2;
		this.y = (Main_Game.HEIGHT/2)-game.CharacterHeight/2;
	}

	public void render(Graphics g) {
		int RelativeChunkX = 0;
		int RelativeChunkY = 0;
		for (int k = 0; k <= 8; k++) {
			if (k == 0) {
				RelativeChunkX = 0;
				RelativeChunkY = 0;
			}
			else if (k == 1) {
				RelativeChunkX = 1;
				RelativeChunkY = 0;
			}
			else if (k == 2) {
				RelativeChunkX = 2;
				RelativeChunkY = 0;
			}
			else if (k == 3) {
				RelativeChunkX = 0;
				RelativeChunkY = 1;
			}
			else if (k == 4) {
				RelativeChunkX = 1;
				RelativeChunkY = 1;
			}
			else if (k == 5) {
				RelativeChunkX = 2;
				RelativeChunkY = 1;
			}
			else if (k == 6) {
				RelativeChunkX = 0;
				RelativeChunkY = 2;
			}
			else if (k == 7) {
				RelativeChunkX = 1;
				RelativeChunkY = 2;
			}
			else if (k == 8) {
				RelativeChunkX = 2;
				RelativeChunkY = 2;
			}
			for (int i = 0; i <= 15; i++) {
				for (int j = 0; j <= 15; j++) {
					//The list of the tileIDs location on the spritesheet
					if (game.dimension == 1) { //This if statement stops the tile rendering while the dimension is changing
						//SpriteSheetLocs = Identifications.getCoveTileSheet(game.StoredTiles[k][i][j][0]); //OLD way of getting sprites
						SpriteSheetLocs = CoveTileIDs.getSpriteSheet(game.StoredTiles[k][i][j][0]);
						g.drawImage(MiningSprite, x+(game.TileWidth*i)-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+(RelativeChunkX*game.TileWidth*16), y+(game.TileHeight*j)+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+(RelativeChunkY*game.TileHeight*16), x+(game.TileWidth*(i+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+(RelativeChunkX*game.TileWidth*16), y+(game.TileHeight*(j+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+(RelativeChunkY*game.TileHeight*16), SpriteSheetLocs[0]*SpriteSheetWidth, SpriteSheetLocs[1]*SpriteSheetHeight, SpriteSheetLocs[0]*SpriteSheetWidth+SpriteSheetWidth, SpriteSheetLocs[1]*SpriteSheetHeight+SpriteSheetHeight, null);
					}
				}
			}
		}
	}

}
