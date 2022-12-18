package processors.electronics;

import java.awt.Graphics;

import core.Main_Game;
import processors.electronics.management.Electronic;

public class CoalGenerator extends Electronic{

	public static final short basePowerGenerated = 30;
	public static final short flameTickTimer = 100;

	public CoalGenerator(Main_Game game, short id, int chunkX, int chunkY, int tileX, int tileY) {
		super(game, id, "/CoalGenerator "+id+".txt", chunkX, chunkY, tileX, tileY);
		for (int i = 0; i < containerID.neededValues; i++) {
			getValues()[i] = 0;
		}
		getValues()[1] = flameTickTimer;
		for (int i = 0; i < 4; i++) {
			outputSides[i] = true;
			inputSides[i] = false;
		}
	}

	public void tick() {
		energyTransfer();
		if (getValues()[0] == 1) {
			if (getValues()[2] <= 0) {
				if (getContainerSlots()[2][3][0] == 12) {
					if (getContainerSlots()[2][3][1] >= 1) {
						getContainerSlots()[2][3][1]--;
						getValues()[2] = Main_Game.coalBurnTime;
						getValues()[1] = flameTickTimer;
						if (getContainerSlots()[2][3][1] <= 0) {
							getContainerSlots()[2][3][0] = 0;
						}
					}
				}
				else {
					getValues()[0] = 0;
					game.files.RewriteLine("Files/File "+game.CurrentFile+"/Tiles"+getFileName(), 1, ""+getValues()[0]);
					getValues()[1] = flameTickTimer;
					game.files.RewriteLine("Files/File "+game.CurrentFile+"/Tiles"+getFileName(), 2, ""+getValues()[1]);
				}
			}
			else {
				getValues()[1]--;
				energyGen(energyPerTick);
				if (getValues()[1] <= 0) {
					getValues()[2]--;
					getValues()[1] = flameTickTimer;
				}
			}
		}
	}
	public void render(Graphics g) {
		
	}

}
