package processors;

import java.awt.Graphics;
import java.util.Random;

import core.Main_Game;
import processors.management.Processors;

public class Acorn extends Processors{
	
	public static final short baseSeconds = 240; //How many seconds the acorn takes to grow
	
	public Acorn(Main_Game game, short id, int chunkX, int chunkY, int tileX, int tileY) {
		super(game, id, "/Acorn "+id+".txt", chunkX, chunkY, tileX, tileY);
		this.game = game;
		Random r = new Random();
		getValues()[0] = (short) ((baseSeconds+r.nextInt(128))*Main_Game.Target_TPS);
		getValues()[1] = 0;
	}

	public void tick() {
		setValues(1, (short) (getValues()[1]+1));
		if (getValues()[1] == getValues()[0]) {
			game.StoredTiles[(loc[0]-game.ChunkX+1)+(3*(game.ChunkY-loc[1]+1))][loc[2]][loc[3]][0] = 3;
			game.StoredTiles[(loc[0]-game.ChunkX+1)+(3*(game.ChunkY-loc[1]+1))][loc[2]][loc[3]][1] = 0;
			game.processhandler.removeProcessor(this);
			//System.out.println("Files/File "+game.CurrentFile+"/Tiles"+fileName); //Was for testing
			game.files.deleteTextFile("Files/File "+game.CurrentFile+"/Tiles"+fileName);
			game.processhandler.reload = true;
		}
	}

	public void render(Graphics g) {
		
	}
}
