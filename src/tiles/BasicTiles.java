package tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import containers.ContainerIDs;
import containers.electronics.ElectronicHandler;
import entities.EntityIDs;
import entities.EntityObject;
import entities.Handler;
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

	public BasicTiles(int x, int y, EntityIDs id, Main_Game game, Handler handler) {
		super(x, y, id);

		removed = false;

		this.game = game;
		this.handler = handler;

		game.TileWidth = Main_Game.WIDTH/18;
		game.TileHeight = game.TileWidth;

		//previously used to create a random world for testing
		/*for (int i = 0; i <= 15; i++) {
			for (int j = 0; j <= 15; j++) {
				//Math.floor(Math.random()*(max-min+1)+min)
				byte random = (byte) Math.floor(Math.random()*(20+1));
				if (random == 1) {
					game.StoredTiles5[i][j] = 1;
				}
				else if (random == 2) {
					game.StoredTiles5[i][j] = 2;
				}
				else if (random == 3) {
					game.StoredTiles5[i][j] = 3;
				}
				else {
					game.StoredTiles5[i][j] = 0;
				}
			}
		}*/

		try {
			WireConnections = ImageIO.read(getClass().getResourceAsStream("/WireConnections.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		if (game.dimension != 0) {
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

		//		for (int k = 0; k < 9; k++) { //Loops through each tile to see it the tile needs processing done with it
		//			for (int i = 0; i <= 15; i++) {
		//				for (int j = 0; j <= 15; j++) {
		//					//Hanlding processes for each type of tile
		//					if (game.StoredTiles[k][i][j][0] == 14) {
		//						blastFurnace(k, i, j);
		//					}
		//				}
		//			}
		//		}
	}

	//	private void blastFurnace(int chunk, int tileX, int tileY) { //Handling the blast furnace
	//		String fileName = "Files/File "+game.CurrentFile+"/Tiles/BlastFurnace "+game.StoredTiles[chunk][tileX][tileY][1]+".txt";
	//		int timer = 0;
	//		try {
	//			Integer.parseInt(game.files.ReadLine(fileName, 2)); //An error catching for random times when it can't get the value for some reason
	//		} catch(NumberFormatException e) {
	//			return;
	//		}
	//		int burnTime = Integer.parseInt(game.files.ReadLine(fileName, 3));
	//		int burning = 0;
	//		try {
	//			burning = Integer.parseInt(game.files.ReadLine(fileName, 1));
	//			if (Integer.parseInt(game.files.ReadLine(fileName, 1)) == 1) { //If the blast furnace is running
	//				if (burnTime <= 0) {
	//					short fuelSlot[] = game.files.getInventorySlot(fileName, 12);
	//					if (fuelSlot[0] == 12) { //Only coal can be used to smelt
	//						//System.out.println("That looks like coal to me!"); //Used for testing
	//						fuelSlot[1]--;
	//						burnTime = Main_Game.coalBurnTime;
	//						if (fuelSlot[1] <= 0) {
	//							fuelSlot[0] = 0;
	//						}
	//						game.files.RewriteLine(fileName, 12, fuelSlot[0]+" "+fuelSlot[1]);
	//
	//						if (game.containerID == game.StoredTiles[chunk][tileX][tileY][1]) {
	//							boolean[][] containerSlots = {{true, true, false, true, true},
	//									{true, true, false, true, true},
	//									{false, false, false, false, false},
	//									{false, false, true, false, false},
	//									{false, false, false, false, false}};
	//							game.containerSlots = game.files.openContainer(fileName, 3, containerSlots);
	//						}
	//					}
	//					else {
	//						burning = 0;
	//						game.files.RewriteLine(fileName, 1, ""+burning);
	//						timer = Main_Game.BlastFurnaceCookTime;
	//						game.files.RewriteLine(fileName, 2, ""+timer);
	//					}
	//				}
	//				else {
	//					timer = Integer.parseInt(game.files.ReadLine(fileName, 2));
	//					if (timer > 0) {
	//						timer--;
	//					}
	//					else {
	//						if (burnTime > 0)
	//						{
	//							burnTime--;
	//							timer = Main_Game.BlastFurnaceCookTime;
	//							System.out.println("Now processing ores");
	//							for (int j = 0; j < 2; j++) {
	//								for (int i = 0; i < 2; i++) {
	//									short inputSlot[] = game.files.getInventorySlot(fileName, i+(j*4)+4);
	//									short outputSlot[] = game.files.getInventorySlot(fileName, i+(j*4)+6);
	//									if (inputSlot[0] == 13) {
	//										if (outputSlot[0] == 0 || outputSlot[0] == 22) { //This is the list of items that can be smelted in a blast furnace
	//											if (outputSlot[1] < 30 && inputSlot[1] >= 3) {
	//												inputSlot[1] -= 3;
	//												if (inputSlot[1] == 0) {
	//													inputSlot[0] = 0;
	//												}
	//												outputSlot[0] = 22;
	//												outputSlot[1]++;
	//											}
	//										}
	//									}
	//									if (inputSlot[0] == 14) {
	//										if (outputSlot[0] == 0 || outputSlot[0] == 23) {
	//											if (outputSlot[1] < 30 && inputSlot[1] >= 3) {
	//												inputSlot[1] -= 3;
	//												if (inputSlot[1] == 0) {
	//													inputSlot[0] = 0;
	//												}
	//												outputSlot[0] = 23;
	//												outputSlot[1]++;
	//											}
	//										}
	//									}
	//									else if (inputSlot[0] == 15) {
	//										if (outputSlot[0] == 0 || outputSlot[0] == 24) {
	//											if (outputSlot[1] < 30 && inputSlot[1] >= 3) {
	//												inputSlot[1] -= 3;
	//												if (inputSlot[1] == 0) {
	//													inputSlot[0] = 0;
	//												}
	//												outputSlot[0] = 24;
	//												outputSlot[1]++;
	//											}
	//										}
	//									}
	//									else if (inputSlot[0] == 16) {
	//										if (outputSlot[0] == 0 || outputSlot[0] == 25) {
	//											if (outputSlot[1] < 30 && inputSlot[1] >= 3) {
	//												inputSlot[1] -= 3;
	//												if (inputSlot[1] == 0) {
	//													inputSlot[0] = 0;
	//												}
	//												outputSlot[0] = 25;
	//												outputSlot[1]++;
	//											}
	//										}
	//									}
	//									else if (inputSlot[0] == 17) {
	//										if (outputSlot[0] == 0 || outputSlot[0] == 26) {
	//											if (outputSlot[1] < 30 && inputSlot[1] >= 3) {
	//												inputSlot[1] -= 3;
	//												if (inputSlot[1] == 0) {
	//													inputSlot[0] = 0;
	//												}
	//												outputSlot[0] = 26;
	//												outputSlot[1]++;
	//											}
	//										}
	//									}
	//									else if (inputSlot[0] == 18) {
	//										if (outputSlot[0] == 0 || outputSlot[0] == 27) {
	//											if (outputSlot[1] < 30 && inputSlot[1] >= 3) {
	//												inputSlot[1] -= 3;
	//												if (inputSlot[1] == 0) {
	//													inputSlot[0] = 0;
	//												}
	//												outputSlot[0] = 27;
	//												outputSlot[1]++;
	//											}
	//										}
	//									}
	//									game.files.RewriteLine(fileName, i+(j*4)+4, inputSlot[0]+" "+inputSlot[1]);
	//									game.files.RewriteLine(fileName, i+(j*4)+6, outputSlot[0]+" "+outputSlot[1]);
	//								}
	//							}
	//
	//							if (game.containerID == game.StoredTiles[chunk][tileX][tileY][1]) {
	//								game.containerSlots = game.files.openContainer(fileName, 3, Identifications.getValidContainerSlots(game.containerType));
	//							}
	//						}
	//					}
	//				}
	//				//System.out.println(""+timer);
	//				//System.out.println(""+burnTime);
	//				game.files.RewriteLine(fileName, 2, ""+timer);
	//				game.files.RewriteLine(fileName, 3, ""+burnTime);
	//			}
	//		}catch (NumberFormatException e) {
	//
	//		}
	//	}

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
					if (game.dimension == 0) { //This if statement stops the tile rendering while the dimension is changing
						//SpriteSheetLocs = Identifications.getSurfaceTileSheet(game.StoredTiles[k][i][j][0]); //OLD way of getting sprites
						SpriteSheetLocs = SurfaceTileIDs.getSpriteSheet(game.StoredTiles[k][i][j][0]);
						g.drawImage(game.TilesSprite, x+(game.TileWidth*i)-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+(RelativeChunkX*game.TileWidth*16), y+(game.TileHeight*j)+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+(RelativeChunkY*game.TileHeight*16), x+(game.TileWidth*(i+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+(RelativeChunkX*game.TileWidth*16), y+(game.TileHeight*(j+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+(RelativeChunkY*game.TileHeight*16), SpriteSheetLocs[0]*SpriteSheetWidth, SpriteSheetLocs[1]*SpriteSheetHeight, SpriteSheetLocs[0]*SpriteSheetWidth+SpriteSheetWidth, SpriteSheetLocs[1]*SpriteSheetHeight+SpriteSheetHeight, null);
						if (game.StoredTiles[k][i][j][0] == 17) { //Wire connections for the copper wires
							int[][] adjacents = {	{i, ElectronicHandler.tileYAdjust(j-1), ElectronicHandler.baseChunkAdjust(i, j-1, k)}, //One above
									{ElectronicHandler.tileXAdjust(i+1), j, ElectronicHandler.baseChunkAdjust(i+1, j, k)}, //One the right
									{i, ElectronicHandler.tileYAdjust(j+1), ElectronicHandler.baseChunkAdjust(i, j+1, k)}, //One below
									{ElectronicHandler.tileXAdjust(i-1), j, ElectronicHandler.baseChunkAdjust(i-1, j, k)}}; //One to the right
							for (int d = 0; d < 4; d++) {
								if (adjacents[d][2] >= 0 && adjacents[d][2] <= 8) {
									if (ContainerIDs.containerExists(SurfaceTileIDs.values()[game.StoredTiles[adjacents[d][2]][adjacents[d][0]][adjacents[d][1]][0]].toString())) {
										ContainerIDs c = ContainerIDs.valueOf(SurfaceTileIDs.values()[game.StoredTiles[adjacents[d][2]][adjacents[d][0]][adjacents[d][1]][0]].toString());
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
