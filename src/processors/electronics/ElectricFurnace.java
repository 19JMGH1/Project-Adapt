package processors.electronics;

import java.awt.Graphics;

import core.Main_Game;
import core.lighting.DayTimeCycle;
import core.lighting.Light;
import processors.BlastFurnace;
import processors.ProcessorIDs;

public class ElectricFurnace extends Electronic implements Light{

	public static final boolean[][] validSlots ={{true, true, false, true, true},
			{true, true, false, true, true},
			{false, false, false, false, false},
			{false, false, false, false, false},
			{false, false, false, false, false}};
	public static final byte neededValues = 2; //One for On/Off, one value for the flame burn time, another for the amount of time that the flame stays at its level
	public static final int baseMaxPower = 30000;
	public static final short powerTransfer = 200;
	public static final short refineTickTimer = 300;
	public static final short energyUsePerTick = 20;
	public static final short energyPerOperation = Main_Game.FurnaceCookTime*energyUsePerTick;
	private static final byte lightLevelProduced = 8; //This is the light level that the furnace makes while running

	public ElectricFurnace(Main_Game game, short id, int chunkX, int chunkY, int tileX, int tileY) {
		super(game, id, ProcessorIDs.ElectricFurnace, validSlots, "/ElectricFurnace "+id+".txt", neededValues, chunkX, chunkY, tileX, tileY, baseMaxPower, powerTransfer);
		this.game = game;
		for (int i = 0; i < neededValues; i++) {
			getValues()[i] = 0;
			outputSides[i] = false;
			inputSides[i] = true;
		}
		getValues()[1] = Main_Game.FurnaceCookTime;
	}

	public void tick() {
		energyTransfer();
		if (getValues()[0] == 1) {
			if (powerStored >= energyUsePerTick) {
				getValues()[1]--;
				powerStored -= energyUsePerTick;
			}
			if (getValues()[1] <= 0 && powerStored >= energyUsePerTick) {
				getValues()[1] = Main_Game.FurnaceCookTime;
				boolean itemsToSmelt = BlastFurnace.smelt(this);
				if (!itemsToSmelt) {
					getValues()[0] = 0;
					removeLight();
					DayTimeCycle.reloadNeeded = true;
				}
			}
		}
	}

	public void render(Graphics g) {
		if (getValues()[0] == 1) {
			int x = (Main_Game.WIDTH/2)-game.CharacterWidth/2;
			int y = (Main_Game.HEIGHT/2)-game.CharacterHeight/2;
			//g.drawImage(TilesSprite, x+(game.TileWidth*i)-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+(RelativeChunkX*game.TileWidth*16), y+(game.TileHeight*j)+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+(RelativeChunkY*game.TileHeight*16), x+(game.TileWidth*(i+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+(RelativeChunkX*game.TileWidth*16), y+(game.TileHeight*(j+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+(RelativeChunkY*game.TileHeight*16), SpriteSheetLocs[0]*SpriteSheetWidth, SpriteSheetLocs[1]*SpriteSheetHeight, SpriteSheetLocs[0]*SpriteSheetWidth+SpriteSheetWidth, SpriteSheetLocs[1]*SpriteSheetHeight+SpriteSheetHeight, null);
			//System.out.println((x+(game.TileWidth*loc[2])-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16))+" "+(y+(game.TileHeight*loc[3])+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16)));
			g.drawImage(game.tileanimations.animationImage, x+(game.TileWidth*loc[2])-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*loc[3])+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), x+(game.TileWidth*(loc[2]+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*(loc[3]+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), 0, 0, 320, 320, null);
			//System.out.println(game.ChunkY-loc[1]+2);
			//System.out.println(loc[0]-game.ChunkX);
			byte chunkX = (byte) (loc[0]-game.ChunkX+1);
			byte chunkY = (byte) (game.ChunkY-loc[1]+1);
			addLight(fileName, lightLevelProduced, game.lightLevels[(chunkX)+(3*(chunkY))][loc[2]][loc[3]+1], chunkX, chunkY, (byte) loc[2], (byte) (-loc[3]-1));
		}
	}

}
