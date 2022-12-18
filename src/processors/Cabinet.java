package processors;

import java.awt.Graphics;

import core.Main_Game;
import processors.management.Processors;

public class Cabinet extends Processors{

	public Cabinet(Main_Game game, short id, int chunkX, int chunkY, int tileX, int tileY) {
		super(game, id, "/Cabinet "+id+".txt", chunkX, chunkY, tileX, tileY);
		this.game = game;
		for (int i = 0; i < containerID.neededValues; i++) {
			getValues()[i] = 0;
		}
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
		
	}

}
