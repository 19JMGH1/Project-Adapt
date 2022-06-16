package tiles.interactions;

import core.Main_Game;
import items.ItemIDs;
import processors.BlastFurnace;
import processors.Cabinet;
import processors.ProcessHandler;
import processors.electronics.CoalGenerator;
import processors.electronics.CopperWire;
import processors.electronics.ElectricFurnace;
import processors.electronics.ElectronicHandler;
import processors.electronics.LESU;
import processors.electronics.Refiner;

public class PlaceTile {
	
	private Main_Game game;
	private Interactions interactions;
	
	public PlaceTile(Main_Game game, Interactions interactions){
		this.game = game;
		this.interactions = interactions;
	}
	
	public void place(int chunk, int tileX, int tileY) {
		int x = game.SelectedHotbar;
		int y = 5;
		short invValue = game.Inventory[x][y][0];
		boolean itemUsed = true;
		
		if (invValue == 8) //Placing a wall tile
		{
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 4;
		}
		else if (invValue == 9) //Placing a door tile
		{
			if (interactions.checkAbove((byte) 4, chunk, tileX, tileY)||interactions.checkBelow((byte) 4, chunk, tileX, tileY))
			{
				game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 6;
			}
			else
			{
				game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 5;
			}
		}
		else if (invValue == 10) //Placing a stone table
		{
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 9;
		}
		else if (invValue == 11) //Placing a mine
		{
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 13;
		}
		else if (invValue == 21) //Placing a blast furnace
		{
			//placeContainer(chunk, tileX, tileY, (short) 14, (byte) 2, (byte) 9); //Remember that the neededValues byte does not include the On/Off boolean of the container
			
			short tileID = 14;
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = tileID;
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][1] = game.files.getTileFileID(tileID);
			int chunkX = game.ChunkX+(chunk%3);
			int chunkY = (game.ChunkY)+(chunk/3);
			if (chunk <= 2) {
				chunkY+=2;
			}
			BlastFurnace bf = new BlastFurnace(game, game.StoredTiles[chunk][tileX][Math.abs(tileY)][1], chunkX, chunkY, tileX, Math.abs(tileY));
			ProcessHandler.processors.add(bf);
		}
		else if (invValue == 29) //Placing an anvil
		{
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 15;
		}
		else if (invValue == 30) //Placing an LESU
		{
			//placeContainer(chunk, tileX, tileY, (short) 16, (byte) 0, (byte) 0); //Remember that the neededValues byte does not include the On/Off boolean of the container
			
			short tileID = 16;
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = tileID;
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][1] = game.files.getTileFileID(tileID);
			int chunkX = game.ChunkX+(chunk%3);
			int chunkY = game.ChunkY+(chunk/3);
			LESU lesu = new LESU(game, game.StoredTiles[chunk][tileX][Math.abs(tileY)][1], chunkX, chunkY, tileX, Math.abs(tileY));
			ProcessHandler.processors.add(lesu);
			ElectronicHandler.electronics.add(lesu);
		}
		else if (invValue == 31) //Placing a copper wire
		{
			//placeContainer(chunk, tileX, tileY, (short) 17, (byte) 0, (byte) 0); //Remember that the neededValues byte does not include the On/Off boolean of the container
			
			short tileID = 17;
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = tileID;
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][1] = game.files.getTileFileID(tileID);
			int chunkX = game.ChunkX+(chunk%3);
			int chunkY = game.ChunkY+(chunk/3);
			CopperWire cw = new CopperWire(game, game.StoredTiles[chunk][tileX][Math.abs(tileY)][1], chunkX, chunkY,  tileX, Math.abs(tileY));
			ProcessHandler.processors.add(cw);
			ElectronicHandler.electronics.add(cw);
		}
		else if (invValue == 32) //Placing a coal generator
		{
			//placeContainer(chunk, tileX, tileY, (short) 18, (byte) 2, (byte) 1); //Remember that the neededValues byte does not include the On/Off boolean of the container
			
			short tileID = 18;
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = tileID;
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][1] = game.files.getTileFileID(tileID);
			int chunkX = game.ChunkX+(chunk%3);
			int chunkY = game.ChunkY+(chunk/3);
			CoalGenerator cg = new CoalGenerator(game, game.StoredTiles[chunk][tileX][Math.abs(tileY)][1], chunkX, chunkY, tileX, Math.abs(tileY));
			ProcessHandler.processors.add(cg);
			ElectronicHandler.electronics.add(cg);
		}
		else if (invValue == 33) //Placing a refiner
		{
			//placeContainer(chunk, tileX, tileY, (short) 19, (byte) 1, (byte) 8); //Remember that the neededValues byte does not include the On/Off boolean of the container
			short tileID = 19;
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = tileID;
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][1] = game.files.getTileFileID(tileID);
			int chunkX = game.ChunkX+(chunk%3);
			int chunkY = game.ChunkY+(chunk/3);
			Refiner refiner = new Refiner(game, game.StoredTiles[chunk][tileX][Math.abs(tileY)][1], chunkX, chunkY, tileX, Math.abs(tileY));
			ProcessHandler.processors.add(refiner);
			ElectronicHandler.electronics.add(refiner);
		}
		else if (invValue == 40) //Placing a cabinet
		{
			short tileID = 20;
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = tileID;
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][1] = game.files.getTileFileID(tileID);
			int chunkX = game.ChunkX+(chunk%3);
			int chunkY = game.ChunkY+(chunk/3);
			Cabinet c = new Cabinet(game, game.StoredTiles[chunk][tileX][Math.abs(tileY)][1], chunkX, chunkY, tileX, Math.abs(tileY));
			ProcessHandler.processors.add(c);
		}
		else if (invValue == 41) //Placing an electric furnace
		{
			System.out.println(ProcessHandler.processors.size());
			short tileID = 21;
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = tileID;
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][1] = game.files.getTileFileID(tileID);
			int chunkX = game.ChunkX+(chunk%3);
			int chunkY = game.ChunkY+(chunk/3);
			ElectricFurnace c = new ElectricFurnace(game, game.StoredTiles[chunk][tileX][Math.abs(tileY)][1], chunkX, chunkY, tileX, Math.abs(tileY));
			ProcessHandler.processors.add(c);
			ElectronicHandler.electronics.add(c);
		}
		else
		{
			itemUsed = false;
		}

		if (itemUsed) //Takes an item from the hotbar slot if it was used
		{
			game.addMessage("Removed 1X "+ItemIDs.values()[invValue].getName());
			game.Inventory[x][y][1]--;
		}
	}
	
//	private void placeContainer(int chunk, int tileX, int tileY, short tileID, byte neededValues, byte inventorySlots) { //Remember that the neededValues byte does not include the On/Off boolean of the container
//		game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = tileID;
//		game.files.createTileInfo((short) tileID, chunk, tileX, Math.abs(tileY), neededValues, inventorySlots);
//	}
	
}
