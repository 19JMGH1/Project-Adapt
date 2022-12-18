package processors.electronics;

import java.awt.Graphics;

import core.Main_Game;
import items.ItemIDs;
import processors.electronics.management.Electronic;

public class Refiner extends Electronic{

	public static final short refineTickTimer = 300;
	
	private int tileAnimationCycle = 0;

	public Refiner(Main_Game game, short id, int chunkX, int chunkY, int tileX, int tileY) {
		super(game, id, "/Refiner "+id+".txt", chunkX, chunkY, tileX, tileY);
		for (int i = 0; i < containerID.neededValues; i++) {
			getValues()[i] = 0;
		}
		getValues()[1] = refineTickTimer;
		for (int i = 0; i < 4; i++) {
			outputSides[i] = false;
			inputSides[i] = true;
		}
	}
	
//	@Override
//	public void saveProcessor() {
//		CreateContainerFiles.createFile(fileName, this);
//	}

	public void tick() {
		energyTransfer();
		if (getValues()[0] == 1) {

			if (tileAnimationCycle >= 30) {
				tileAnimationCycle = 1;
			}
			else {
				tileAnimationCycle++;
			}

			if (powerStored >= energyPerTick) {
				getValues()[1]--;
				powerStored -= energyPerTick;
			}
			if (getValues()[1] <= 0 && powerStored >= energyPerTick) {
				getValues()[1] = refineTickTimer;
				boolean itemsInput = false;
				for (int j = 0; j < 2; j++) {
					for (int i = 0; i < 2; i++) {
						short inputSlot[] = containerSlots[i][j];
						short outputSlot[] = containerSlots[i+3][j];
						if (inputSlot[0] == 13) {
							itemsInput = true;
							if (outputSlot[0] == 0 || outputSlot[0] == 34) { //This is the list of items that can be refined
								if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 1) {
									inputSlot[1] -= 1;
									if (inputSlot[1] == 0) {
										inputSlot[0] = 0;
									}
									outputSlot[0] = 34;
									outputSlot[1]++;
								}
							}
						}
						if (inputSlot[0] == 14) {
							itemsInput = true;
							if (outputSlot[0] == 0 || outputSlot[0] == 35) {
								if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 1) {
									inputSlot[1] -= 1;
									if (inputSlot[1] == 0) {
										inputSlot[0] = 0;
									}
									outputSlot[0] = 35;
									outputSlot[1]++;
								}
							}
						}
						else if (inputSlot[0] == 15) {
							itemsInput = true;
							if (outputSlot[0] == 0 || outputSlot[0] == 36) {
								if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 1) {
									inputSlot[1] -= 1;
									if (inputSlot[1] == 0) {
										inputSlot[0] = 0;
									}
									outputSlot[0] = 36;
									outputSlot[1]++;
								}
							}
						}
						else if (inputSlot[0] == 16) {
							itemsInput = true;
							if (outputSlot[0] == 0 || outputSlot[0] == 37) {
								if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 1) {
									inputSlot[1] -= 1;
									if (inputSlot[1] == 0) {
										inputSlot[0] = 0;
									}
									outputSlot[0] = 37;
									outputSlot[1]++;
								}
							}
						}
						else if (inputSlot[0] == 17) {
							itemsInput = true;
							if (outputSlot[0] == 0 || outputSlot[0] == 38) {
								if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 1) {
									inputSlot[1] -= 1;
									if (inputSlot[1] == 0) {
										inputSlot[0] = 0;
									}
									outputSlot[0] = 38;
									outputSlot[1]++;
								}
							}
						}
						else if (inputSlot[0] == 18) {
							itemsInput = true;
							if (outputSlot[0] == 0 || outputSlot[0] == 39) {
								if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 1) {
									inputSlot[1] -= 1;
									if (inputSlot[1] == 0) {
										inputSlot[0] = 0;
									}
									outputSlot[0] = 39;
									outputSlot[1]++;
								}
							}
						}
						else if (inputSlot[0] == ItemIDs.Stone.ordinal()) {
							itemsInput = true;
							if (outputSlot[0] == 0 || outputSlot[0] == ItemIDs.SiliconDust.ordinal()) {
								if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 1) {
									inputSlot[1] -= 1;
									if (inputSlot[1] == 0) {
										inputSlot[0] = 0;
									}
									outputSlot[0] = (short) ItemIDs.SiliconDust.ordinal();
									outputSlot[1]++;
								}
							}
						}
						else if (inputSlot[0] == ItemIDs.Meat.ordinal()) {
							itemsInput = true;
							if (outputSlot[0] == 0 || outputSlot[0] == ItemIDs.PhosphorusDust.ordinal()) {
								if (outputSlot[1] < Main_Game.maxStackSize && inputSlot[1] >= 1) {
									inputSlot[1] -= 1;
									if (inputSlot[1] == 0) {
										inputSlot[0] = 0;
									}
									outputSlot[0] = (short) ItemIDs.PhosphorusDust.ordinal();
									outputSlot[1]++;
								}
							}
						}
					}
				}
				if (!itemsInput) {
					getValues()[0] = 0;
				}
			}
		}
		else {
			getValues()[1] = refineTickTimer;
			tileAnimationCycle = 0;
		}

	}

	public void render(Graphics g) {
		if (getValues()[0] == 1) {
			int x = (Main_Game.WIDTH/2)-game.CharacterWidth/2;
			int y = (Main_Game.HEIGHT/2)-game.CharacterHeight/2;
			if ((tileAnimationCycle >= 1 && tileAnimationCycle < 15)&&(powerStored >= energyPerTick)) {
				g.drawImage(game.tileanimations.animationImage, x+(game.TileWidth*loc[2])-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*loc[3])+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), x+(game.TileWidth*(loc[2]+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*(loc[3]+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), Main_Game.tileAditionsSpriteDims, 0, Main_Game.tileAditionsSpriteDims*2, Main_Game.tileAditionsSpriteDims, null);
			}
			else if ((tileAnimationCycle >= 15)&&(powerStored >= energyPerTick)) {
				g.drawImage(game.tileanimations.animationImage, x+(game.TileWidth*loc[2])-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*loc[3])+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), x+(game.TileWidth*(loc[2]+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*(loc[3]+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), Main_Game.tileAditionsSpriteDims*2, 0, Main_Game.tileAditionsSpriteDims*3, Main_Game.tileAditionsSpriteDims, null);
			}
		}
	}
}
