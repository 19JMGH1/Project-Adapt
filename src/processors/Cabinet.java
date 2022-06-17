package processors;

import java.awt.Graphics;

import core.Main_Game;
import processors.management.ProcessorIDs;
import processors.management.Processors;

public class Cabinet extends Processors{
	
	public static final boolean[][] validSlots ={{true, true, true, true, true},
			{true, true, true, true, true},
			{true, true, true, true, true},
			{true, true, true, true, true},
			{true, true, true, true, true}};
	public static final byte neededValues = 0;

	public Cabinet(Main_Game game, short id, int chunkX, int chunkY, int tileX, int tileY) {
		super(game, id, ProcessorIDs.Cabinet, validSlots, "/Cabinet "+id+".txt", neededValues, chunkX, chunkY, tileX, tileY);
		this.game = game;
		for (int i = 0; i < neededValues; i++) {
			getValues()[i] = 0;
		}
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
		
	}

}
