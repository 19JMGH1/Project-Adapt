package tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.EntityTypes;
import entities.management.EntityObject;
import entities.management.Handler;
import processors.electronics.management.ElectronicHandler;
import processors.management.ProcessorIDs;
import core.Main_Game;

public class BasicTiles extends EntityObject{

	private Main_Game game;
	private Handler handler;

	private boolean removed = false;

	private BufferedImage WireConnections;
	
	private final int SpriteSheetWidth = 320;
	private final int SpriteSheetHeight = 320;
	//Location of where to get the sprite from
	private int SpriteSheetLocs[] = new int[2];

	public BasicTiles(int x, int y, EntityTypes id, Main_Game game, Handler handler) {
		super(x, y, id);

		removed = false;

		this.game = game;
		this.handler = handler;

		game.TileWidth = Main_Game.WIDTH/18;
		game.TileHeight = game.TileWidth;

		try {
			WireConnections = ImageIO.read(getClass().getResourceAsStream("/WireConnections.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		if (game.dimension != core.Main_Game.Dimensions.surface) {
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
		for (int k = 0; k < 9; k++) {
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
					if (game.dimension == core.Main_Game.Dimensions.surface) { //This if statement stops the tile rendering while the dimension is changing
						//SpriteSheetLocs = Identifications.getSurfaceTileSheet(game.StoredTiles[k][i][j][0]); //OLD way of getting sprites
						SpriteSheetLocs = SurfaceTileIDs.getSpriteSheet(game.StoredTiles[k][i][j][0]);
						g.drawImage(game.TilesSprite, x+(game.TileWidth*i)-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+(RelativeChunkX*game.TileWidth*16), y+(game.TileHeight*j)+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+(RelativeChunkY*game.TileHeight*16), x+(game.TileWidth*(i+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+(RelativeChunkX*game.TileWidth*16), y+(game.TileHeight*(j+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+(RelativeChunkY*game.TileHeight*16), SpriteSheetLocs[0]*SpriteSheetWidth, SpriteSheetLocs[1]*SpriteSheetHeight, SpriteSheetLocs[0]*SpriteSheetWidth+SpriteSheetWidth, SpriteSheetLocs[1]*SpriteSheetHeight+SpriteSheetHeight, null);
						//The two lines below were used for testing the early stages of the lighting system
						//g.setFont(new Font("Cooper Black", Font.PLAIN, Main_Game.WIDTH/50));
						//g.drawString(""+game.lightLevels[k][i][j], x+(game.TileWidth*i)-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+(RelativeChunkX*game.TileWidth*16), y+(game.TileHeight*j)+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+(RelativeChunkY*game.TileHeight*16));
						if (game.StoredTiles[k][i][j][0] == 17) { //Wire connections for the copper wires
							int[][] adjacents = {	{i, ElectronicHandler.tileYAdjust(j-1), ElectronicHandler.baseChunkAdjust(i, j-1, k)}, //One above
									{ElectronicHandler.tileXAdjust(i+1), j, ElectronicHandler.baseChunkAdjust(i+1, j, k)}, //One the right
									{i, ElectronicHandler.tileYAdjust(j+1), ElectronicHandler.baseChunkAdjust(i, j+1, k)}, //One below
									{ElectronicHandler.tileXAdjust(i-1), j, ElectronicHandler.baseChunkAdjust(i-1, j, k)}}; //One to the right
							for (int d = 0; d < 4; d++) {
								if (adjacents[d][2] >= 0 && adjacents[d][2] <= 8) {
									if (ProcessorIDs.containerExists(SurfaceTileIDs.values()[game.StoredTiles[adjacents[d][2]][adjacents[d][0]][adjacents[d][1]][0]].toString())) {
										ProcessorIDs c = ProcessorIDs.valueOf(SurfaceTileIDs.values()[game.StoredTiles[adjacents[d][2]][adjacents[d][0]][adjacents[d][1]][0]].toString());
										if (c.electronic) {
											int x1 = d%2;
											int y1 = d/2;
											g.drawImage(WireConnections, x+(game.TileWidth*i)-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+(RelativeChunkX*game.TileWidth*16), y+(game.TileHeight*j)+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+(RelativeChunkY*game.TileHeight*16), x+(game.TileWidth*(i+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+(RelativeChunkX*game.TileWidth*16), y+(game.TileHeight*(j+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+(RelativeChunkY*game.TileHeight*16), x1*SpriteSheetWidth, y1*SpriteSheetHeight, x1*SpriteSheetWidth+SpriteSheetWidth, y1*SpriteSheetHeight+SpriteSheetHeight, null);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
