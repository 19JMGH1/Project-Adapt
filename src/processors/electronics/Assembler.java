package processors.electronics;

import java.awt.Graphics;

import core.Main_Game;
import items.ItemIDs;
import processors.electronics.management.Electronic;

public class Assembler extends Electronic{

	public static final short assemblyTickTimer = 900;

	private int tileAnimationCycle = 0;
	
	public Assembler(Main_Game game, short id, int chunkX, int chunkY, int tileX, int tileY) {
		super(game, id, "/Assembler "+id+".txt", chunkX, chunkY, tileX, tileY);
		for (int i = 0; i < containerID.neededValues; i++) {
			getValues()[i] = 0;
		}
		getValues()[1] = assemblyTickTimer;
		for (int i = 0; i < 4; i++) {
			outputSides[i] = false;
			inputSides[i] = true;
		}
	}

	public void tick() {
		energyTransfer();
		if (getValues()[0] == 1) {
			if (tileAnimationCycle >= 90) {
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
				getValues()[1] = assemblyTickTimer;
				boolean itemsInput = false;
				//TODO put assembler crafting recipes here
				if (solarCell()) {
					itemsInput = true;
				}
				if (!itemsInput) {
					getValues()[0] = 0;
				}
			}
		}
		else {
			getValues()[1] = assemblyTickTimer;
			tileAnimationCycle = 0;
		}
	}
	
	private void decreaseInputSlots() {
		for (int i = 0; i <= 2; i ++) {
			for (int j = 1; j <= 3; j++) {
				containerSlots[i][j][1]--;
				if (containerSlots[i][j][1] == 0) {
					containerSlots[i][j][0] = 0;
				}
			}
		}
	}
	
	private boolean solarCell() {
		if (containerSlots[0][1][0] == ItemIDs.PhosphorusDust.ordinal() && containerSlots[0][1][0] > 0
				&& containerSlots[1][1][0] == ItemIDs.SiliconDust.ordinal() && containerSlots[1][1][0] > 0
				&& containerSlots[2][1][0] == ItemIDs.PhosphorusDust.ordinal() && containerSlots[2][1][0] > 0
				&& containerSlots[0][2][0] == ItemIDs.AluminumBar.ordinal() && containerSlots[0][2][0] > 0
				&& containerSlots[1][2][0] == ItemIDs.SiliconDust.ordinal() && containerSlots[1][2][0] > 0
				&& containerSlots[2][2][0] == ItemIDs.AluminumBar.ordinal() && containerSlots[2][2][0] > 0
				&& containerSlots[0][3][0] == ItemIDs.IronBar.ordinal() && containerSlots[0][3][0] > 0
				&& containerSlots[1][3][0] == ItemIDs.IronBar.ordinal() && containerSlots[1][3][0] > 0
				&& containerSlots[2][3][0] == ItemIDs.IronBar.ordinal() && containerSlots[2][3][0] > 0) {
			if (containerSlots[4][2][0] == ItemIDs.SolarCell.ordinal() || containerSlots[4][2][0] == ItemIDs.Blank.ordinal()) {
				containerSlots[4][2][0] = (short) ItemIDs.SolarCell.ordinal();
				containerSlots[4][2][1]++;
				decreaseInputSlots();
				return true;
			}
		}
		return false;
	}

	public void render(Graphics g) {
		if (getValues()[0] == 1) {
			int x = (Main_Game.WIDTH/2)-game.CharacterWidth/2;
			int y = (Main_Game.HEIGHT/2)-game.CharacterHeight/2;
			if ((tileAnimationCycle >= 1 && tileAnimationCycle <= 10)&&(powerStored >= energyPerTick)) {
				g.drawImage(game.tileanimations.animationImage, x+(game.TileWidth*loc[2])-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*loc[3])+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), x+(game.TileWidth*(loc[2]+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*(loc[3]+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), Main_Game.tileAditionsSpriteDims*(3%Main_Game.tileAditionsSpriteWidth), Main_Game.tileAditionsSpriteDims*(3/Main_Game.tileAditionsSpriteWidth), Main_Game.tileAditionsSpriteDims*(3%Main_Game.tileAditionsSpriteWidth)+Main_Game.tileAditionsSpriteDims, Main_Game.tileAditionsSpriteDims*(3/Main_Game.tileAditionsSpriteWidth)+Main_Game.tileAditionsSpriteDims, null);
			}
			else if ((tileAnimationCycle >= 11 && tileAnimationCycle <= 20)&&(powerStored >= energyPerTick)) {
				g.drawImage(game.tileanimations.animationImage, x+(game.TileWidth*loc[2])-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*loc[3])+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), x+(game.TileWidth*(loc[2]+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*(loc[3]+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), Main_Game.tileAditionsSpriteDims*(4%Main_Game.tileAditionsSpriteWidth), Main_Game.tileAditionsSpriteDims*(4/Main_Game.tileAditionsSpriteWidth), Main_Game.tileAditionsSpriteDims*(4%Main_Game.tileAditionsSpriteWidth)+Main_Game.tileAditionsSpriteDims, Main_Game.tileAditionsSpriteDims*(4/Main_Game.tileAditionsSpriteWidth)+Main_Game.tileAditionsSpriteDims, null);
			}
			else if ((tileAnimationCycle >= 21 && tileAnimationCycle <= 30)&&(powerStored >= energyPerTick)) {
				g.drawImage(game.tileanimations.animationImage, x+(game.TileWidth*loc[2])-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*loc[3])+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), x+(game.TileWidth*(loc[2]+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*(loc[3]+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), Main_Game.tileAditionsSpriteDims*(5%Main_Game.tileAditionsSpriteWidth), Main_Game.tileAditionsSpriteDims*(5/Main_Game.tileAditionsSpriteWidth), Main_Game.tileAditionsSpriteDims*(5%Main_Game.tileAditionsSpriteWidth)+Main_Game.tileAditionsSpriteDims, Main_Game.tileAditionsSpriteDims*(5/Main_Game.tileAditionsSpriteWidth)+Main_Game.tileAditionsSpriteDims, null);
			}
			else if ((tileAnimationCycle >= 31 && tileAnimationCycle <= 40)&&(powerStored >= energyPerTick)) {
				g.drawImage(game.tileanimations.animationImage, x+(game.TileWidth*loc[2])-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*loc[3])+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), x+(game.TileWidth*(loc[2]+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*(loc[3]+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), Main_Game.tileAditionsSpriteDims*(4%Main_Game.tileAditionsSpriteWidth), Main_Game.tileAditionsSpriteDims*(4/Main_Game.tileAditionsSpriteWidth), Main_Game.tileAditionsSpriteDims*(4%Main_Game.tileAditionsSpriteWidth)+Main_Game.tileAditionsSpriteDims, Main_Game.tileAditionsSpriteDims*(4/Main_Game.tileAditionsSpriteWidth)+Main_Game.tileAditionsSpriteDims, null);
			}
			else if ((tileAnimationCycle >= 41 && tileAnimationCycle <= 50)&&(powerStored >= energyPerTick)) {
				g.drawImage(game.tileanimations.animationImage, x+(game.TileWidth*loc[2])-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*loc[3])+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), x+(game.TileWidth*(loc[2]+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*(loc[3]+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), Main_Game.tileAditionsSpriteDims*(3%Main_Game.tileAditionsSpriteWidth), Main_Game.tileAditionsSpriteDims*(3/Main_Game.tileAditionsSpriteWidth), Main_Game.tileAditionsSpriteDims*(3%Main_Game.tileAditionsSpriteWidth)+Main_Game.tileAditionsSpriteDims, Main_Game.tileAditionsSpriteDims*(3/Main_Game.tileAditionsSpriteWidth)+Main_Game.tileAditionsSpriteDims, null);
			}
			else if ((tileAnimationCycle >= 51 && tileAnimationCycle <= 60)&&(powerStored >= energyPerTick)) {
				g.drawImage(game.tileanimations.animationImage, x+(game.TileWidth*loc[2])-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*loc[3])+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), x+(game.TileWidth*(loc[2]+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*(loc[3]+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), Main_Game.tileAditionsSpriteDims*(6%Main_Game.tileAditionsSpriteWidth), Main_Game.tileAditionsSpriteDims*(6/Main_Game.tileAditionsSpriteWidth), Main_Game.tileAditionsSpriteDims*(6%Main_Game.tileAditionsSpriteWidth)+Main_Game.tileAditionsSpriteDims, Main_Game.tileAditionsSpriteDims*(6/Main_Game.tileAditionsSpriteWidth)+Main_Game.tileAditionsSpriteDims, null);
			}
			else if ((tileAnimationCycle >= 61 && tileAnimationCycle <= 70)&&(powerStored >= energyPerTick)) {
				g.drawImage(game.tileanimations.animationImage, x+(game.TileWidth*loc[2])-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*loc[3])+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), x+(game.TileWidth*(loc[2]+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*(loc[3]+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), Main_Game.tileAditionsSpriteDims*(7%Main_Game.tileAditionsSpriteWidth), Main_Game.tileAditionsSpriteDims*(7/Main_Game.tileAditionsSpriteWidth), Main_Game.tileAditionsSpriteDims*(7%Main_Game.tileAditionsSpriteWidth)+Main_Game.tileAditionsSpriteDims, Main_Game.tileAditionsSpriteDims*(7/Main_Game.tileAditionsSpriteWidth)+Main_Game.tileAditionsSpriteDims, null);
			}
			else if ((tileAnimationCycle >= 71 && tileAnimationCycle <= 80)&&(powerStored >= energyPerTick)) {
				g.drawImage(game.tileanimations.animationImage, x+(game.TileWidth*loc[2])-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*loc[3])+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), x+(game.TileWidth*(loc[2]+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*(loc[3]+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), Main_Game.tileAditionsSpriteDims*(6%Main_Game.tileAditionsSpriteWidth), Main_Game.tileAditionsSpriteDims*(6/Main_Game.tileAditionsSpriteWidth), Main_Game.tileAditionsSpriteDims*(6%Main_Game.tileAditionsSpriteWidth)+Main_Game.tileAditionsSpriteDims, Main_Game.tileAditionsSpriteDims*(6/Main_Game.tileAditionsSpriteWidth)+Main_Game.tileAditionsSpriteDims, null);
			}
			else if ((tileAnimationCycle >= 81 && tileAnimationCycle <= 90)&&(powerStored >= energyPerTick)) {
				g.drawImage(game.tileanimations.animationImage, x+(game.TileWidth*loc[2])-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*loc[3])+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), x+(game.TileWidth*(loc[2]+1))-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+((loc[0]+1-game.ChunkX)*game.TileWidth*16), y+(game.TileHeight*(loc[3]+1))+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+((game.ChunkY+1-loc[1])*game.TileHeight*16), Main_Game.tileAditionsSpriteDims*(3%Main_Game.tileAditionsSpriteWidth), Main_Game.tileAditionsSpriteDims*(3/Main_Game.tileAditionsSpriteWidth), Main_Game.tileAditionsSpriteDims*(3%Main_Game.tileAditionsSpriteWidth)+Main_Game.tileAditionsSpriteDims, Main_Game.tileAditionsSpriteDims*(3/Main_Game.tileAditionsSpriteWidth)+Main_Game.tileAditionsSpriteDims, null);
			}
		}
	}

}
