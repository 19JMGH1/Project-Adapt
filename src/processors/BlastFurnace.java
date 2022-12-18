package processors;

import java.awt.Graphics;

import core.Main_Game;
import core.lighting.DayTimeCycle;
import core.lighting.Light;
import processors.management.Processors;

public class BlastFurnace extends Processors implements Light{

	private static final byte lightLevelProduced = 8; //This is the light level that the furnace make while running

	public BlastFurnace(Main_Game game, short id, int chunkX, int chunkY, int tileX, int tileY) {
		super(game, id, "/BlastFurnace "+id+".txt", chunkX, chunkY, tileX, tileY);
		this.game = game;
		for (int i = 0; i < containerID.neededValues; i++) {
			getValues()[i] = 0;
		}
		getValues()[1] = Main_Game.FurnaceCookTime;
	}
	
	public static boolean smelt(Processors c) {
		boolean smelted = false;
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 2; i++) {
				short inputSlot[] = c.getContainerSlots()[i][j];
				short outputSlot[] = c.getContainerSlots()[i+3][j];
				//	short inputSlot[] = game.files.getInventorySlot(fileName, i+(j*4)+4); //These were the previous lines used to read the slots, the new way has less file reading
				//	short outputSlot[] = game.files.getInventorySlot(fileName, i+(j*4)+6);
				if (inputSlot[0] == 13) {
					if (outputSlot[0] == 0 || outputSlot[0] == 22) { //This is the list of items that can be smelted in a blast furnace
						if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 2) {
							inputSlot[1] -= 2;
							if (inputSlot[1] == 0) {
								inputSlot[0] = 0;
							}
							outputSlot[0] = 22;
							outputSlot[1]++;
							smelted = true;
						}
					}
				}
				if (inputSlot[0] == 14) {
					if (outputSlot[0] == 0 || outputSlot[0] == 23) {
						if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 2) {
							inputSlot[1] -= 2;
							if (inputSlot[1] == 0) {
								inputSlot[0] = 0;
							}
							outputSlot[0] = 23;
							outputSlot[1]++;
							smelted = true;
						}
					}
				}
				else if (inputSlot[0] == 15) {
					if (outputSlot[0] == 0 || outputSlot[0] == 24) {
						if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 2) {
							inputSlot[1] -= 2;
							if (inputSlot[1] == 0) {
								inputSlot[0] = 0;
							}
							outputSlot[0] = 24;
							outputSlot[1]++;
							smelted = true;
						}
					}
				}
				else if (inputSlot[0] == 16) {
					if (outputSlot[0] == 0 || outputSlot[0] == 25) {
						if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 2) {
							inputSlot[1] -= 2;
							if (inputSlot[1] == 0) {
								inputSlot[0] = 0;
							}
							outputSlot[0] = 25;
							outputSlot[1]++;
							smelted = true;
						}
					}
				}
				else if (inputSlot[0] == 17) {
					if (outputSlot[0] == 0 || outputSlot[0] == 26) {
						if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 2) {
							inputSlot[1] -= 2;
							if (inputSlot[1] == 0) {
								inputSlot[0] = 0;
							}
							outputSlot[0] = 26;
							outputSlot[1]++;
							smelted = true;
						}
					}
				}
				else if (inputSlot[0] == 18) {
					if (outputSlot[0] == 0 || outputSlot[0] == 27) {
						if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 2) {
							inputSlot[1] -= 2;
							if (inputSlot[1] == 0) {
								inputSlot[0] = 0;
							}
							outputSlot[0] = 27;
							outputSlot[1]++;
							smelted = true;
						}
					}
				}
				if (inputSlot[0] == 34) {
					if (outputSlot[0] == 0 || outputSlot[0] == 22) { //This is the list of items that can be smelted in a blast furnace
						if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 1) {
							inputSlot[1] -= 1;
							if (inputSlot[1] == 0) {
								inputSlot[0] = 0;
							}
							outputSlot[0] = 22;
							outputSlot[1]++;
							smelted = true;
						}
					}
				}
				if (inputSlot[0] == 35) {
					if (outputSlot[0] == 0 || outputSlot[0] == 23) {
						if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 1) {
							inputSlot[1] -= 1;
							if (inputSlot[1] == 0) {
								inputSlot[0] = 0;
							}
							outputSlot[0] = 23;
							outputSlot[1]++;
							smelted = true;
						}
					}
				}
				else if (inputSlot[0] == 36) {
					if (outputSlot[0] == 0 || outputSlot[0] == 24) {
						if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 1) {
							inputSlot[1] -= 1;
							if (inputSlot[1] == 0) {
								inputSlot[0] = 0;
							}
							outputSlot[0] = 24;
							outputSlot[1]++;
							smelted = true;
						}
					}
				}
				else if (inputSlot[0] == 37) {
					if (outputSlot[0] == 0 || outputSlot[0] == 25) {
						if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 1) {
							inputSlot[1] -= 1;
							if (inputSlot[1] == 0) {
								inputSlot[0] = 0;
							}
							outputSlot[0] = 25;
							outputSlot[1]++;
							smelted = true;
						}
					}
				}
				else if (inputSlot[0] == 38) {
					if (outputSlot[0] == 0 || outputSlot[0] == 26) {
						if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 1) {
							inputSlot[1] -= 1;
							if (inputSlot[1] == 0) {
								inputSlot[0] = 0;
							}
							outputSlot[0] = 26;
							outputSlot[1]++;
							smelted = true;
						}
					}
				}
				else if (inputSlot[0] == 39) {
					if (outputSlot[0] == 0 || outputSlot[0] == 27) {
						if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 1) {
							inputSlot[1] -= 1;
							if (inputSlot[1] == 0) {
								inputSlot[0] = 0;
							}
							outputSlot[0] = 27;
							outputSlot[1]++;
							smelted = true;
						}
					}
				}
			}
		}
		return smelted;
	}

	public void tick() {
		if (getValues()[0] == 1) { //If the blast furnace is running
			if (getValues()[2] <= 0) {
				if (getContainerSlots()[2][3][0] == 12) { //Only coal can be used to smelt
					getContainerSlots()[2][3][1]--;
					getValues()[2] = Main_Game.coalBurnTime;
					if (getContainerSlots()[2][3][1] <= 0) {
						getContainerSlots()[2][3][0] = 0;
					}
				}
				else {
					getValues()[0] = 0;
					getValues()[1] = Main_Game.FurnaceCookTime;
					game.daytimecycle.clearLights();
				}
			}
			else {
				if (getValues()[1] > 0) {
					getValues()[1]--;
				}
				else {
					if (getValues()[2] > 0)
					{
						getValues()[2]--;
						getValues()[1] = Main_Game.FurnaceCookTime;
						//System.out.println("Now processing ores"); //Was used for testing purposes
						boolean itemsToSmelt = smelt(this);
						if (!itemsToSmelt) {
							getValues()[0] = 0;
							removeLight();
							DayTimeCycle.reloadNeeded = true;
						}
					}
				}
			}
		}
		else {
			values[1] = Main_Game.FurnaceCookTime;
		}
	}

	public void render(Graphics g) {
		if (getValues()[0] == 1) {
			int x = (Main_Game.WIDTH/2)-game.CharacterWidth/2;
			int y = (Main_Game.HEIGHT/2)-game.CharacterHeight/2;
			//g.drawImage(TilesSprite, x+(game.TileWidth*i)-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+(RelativeChunkX*game.TileWidth*16), y+(game.TileHeight*j)+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+(RelativeChunkY*game.TileHeight*16), x+(game.TileWidth*(i+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+(RelativeChunkX*game.TileWidth*16), y+(game.TileHeight*(j+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+(RelativeChunkY*game.TileHeight*16), SpriteSheetLocs[0]*SpriteSheetWidth, SpriteSheetLocs[1]*SpriteSheetHeight, SpriteSheetLocs[0]*SpriteSheetWidth+SpriteSheetWidth, SpriteSheetLocs[1]*SpriteSheetHeight+SpriteSheetHeight, null);
			g.drawImage(game.tileanimations.animationImage, x+(game.TileWidth*loc[2])-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*loc[3])+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), x+(game.TileWidth*(loc[2]+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*(loc[3]+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), 0, 0, 320, 320, null);
			byte chunkX = (byte) (loc[0]-game.ChunkX+1);
			byte chunkY = (byte) (game.ChunkY-loc[1]+1);
			addLight(fileName ,lightLevelProduced, game.lightLevels[(chunkX)+(3*(chunkY))][loc[2]][loc[3]], chunkX, chunkY, (byte) loc[2], (byte) (-loc[3]));
		}
	}
}
