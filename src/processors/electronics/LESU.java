package processors.electronics;

import java.awt.Graphics;

import core.Main_Game;
import processors.electronics.management.Electronic;

public class LESU extends Electronic{

	public LESU(Main_Game game, short id, int chunkX, int chunkY, int tileX, int tileY) {
		super(game, id, "/LESU "+id+".txt", chunkX, chunkY, tileX, tileY);	
	}

	public void tick() {
		energyTransfer();
	}
	public void render(Graphics g) {
		
	}
}
