package processors.electronics;

import java.awt.Graphics;

import core.Main_Game;
import processors.electronics.management.Electronic;
import processors.management.ProcessorIDs;

public class CopperWire extends Electronic{

	public static final boolean[][] validSlots =	{{false, false, false, false, false},
			{false, false, false, false, false},
			{false, false, false, false, false},
			{false, false, false, false, false},
			{false, false, false, false, false}};
	public static final byte neededValues = 0; //This is the number of values that the copper wire needs
	public static final int baseMaxPower = 1000;
	public static final short powerTransfer = 200;

	public CopperWire(Main_Game game, short id, int chunkX, int chunkY, int tileX, int tileY) {
		super(game, id, ProcessorIDs.CopperWire, validSlots, "/CopperWire "+id+".txt", neededValues, chunkX, chunkY, tileX, tileY, baseMaxPower, powerTransfer);
	}

	public void tick() {
		energyTransfer();
	}

	public void render(Graphics g) {

	}

}
