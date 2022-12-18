package processors.electronics;

import java.awt.Graphics;

import core.Main_Game;
import processors.electronics.management.Electronic;

public class SolarPanel extends Electronic{

	public SolarPanel(Main_Game game, short id, int chunkX, int chunkY, int tileX, int tileY) {
		super(game, id, "/SolarPanel "+id+".txt", chunkX, chunkY, tileX, tileY);
		for (int i = 0; i < 4; i++) {
			outputSides[i] = true;
			inputSides[i] = false;
		}
	}

	public void tick() {
		energyTransfer();
		if (game.daytimecycle.transparency == 0) {
			energyGen(energyPerTick);
		}
	}

	public void render(Graphics g) {
		
	}

}
