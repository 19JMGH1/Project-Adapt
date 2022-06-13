package processors.electronics;

import java.awt.Graphics;

import core.Main_Game;
import processors.ProcessorIDs;

public class LESU extends Electronic{

	public static final boolean[][] validSlots =	{{false, false, false, false, false},
			{false, false, false, false, false},
			{false, false, false, false, false},
			{false, false, false, false, false},
			{false, false, false, false, false}};
	public static final byte neededValues = 0;
	public static final int baseMaxPower = 100000; //100,000 so the LESU stores 100,000 joules
	public static final short powerTransfer = 300;

	public LESU(Main_Game game, short id, int chunkX, int chunkY, int tileX, int tileY) {
		super(game, id, ProcessorIDs.LESU, validSlots, "Files/File "+game.CurrentFile+"/Tiles/LESU "+id+".txt", neededValues, chunkX, chunkY, tileX, tileY, baseMaxPower, powerTransfer);
		
	}

	public void tick() {
		energyTransfer();
	}
	public void render(Graphics g) {
		
	}
}
