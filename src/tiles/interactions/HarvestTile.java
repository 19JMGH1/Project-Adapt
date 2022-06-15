package tiles.interactions;

import core.Identifications;
import core.InventoryHandler;
import core.Main_Game;
import processors.ProcessorIDs;
import processors.Processors;
import tiles.SurfaceTileIDs;

public class HarvestTile {
	
	private Main_Game game;
	private InventoryHandler inventoryhandler;
	
	public HarvestTile(Main_Game game, InventoryHandler inventoryhandler){
		this.game = game;
		this.inventoryhandler = inventoryhandler;
	}
	
	public void collect(short tileValue, int chunk, int tileX, int tileY)
	{
		boolean collected = true;
		if (game.dimension == 0) { //This else-if runs through all the methods needed to harvest a given tile
			
			if (tileValue == 1) //Harvesting boulders
			{
				if (inventoryhandler.isPick(game.Inventory[game.SelectedHotbar][5][0]))
				{
					byte invValue = 1;
					if (inventoryhandler.addToInv(invValue, 3))
					{
						game.addDropedItem(invValue, 3);
						useHarvestingItem(game.Inventory[game.SelectedHotbar][5][0], game.SelectedHotbar, 5);
						game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
					}
				}
			}
			else if (tileValue == 2) //Harvesting pebbles
			{
				byte invValue = 1;
				if (inventoryhandler.addToInv(invValue, 1))
				{
					game.addDropedItem(invValue, 1);
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
				}
			}
			else if (tileValue == 3) //Harvesting trees
			{
				if (inventoryhandler.isAxe(game.Inventory[game.SelectedHotbar][5][0]))
				{
					byte invValue = 2;
					collected = inventoryhandler.addToInv(invValue, 2);
					game.addDropedItem(invValue, 2);
					invValue = 3;
					int random = (int)Math.floor(Math.random()*(2)+1); //Gives either 1 or 2 acorns by chance.
					inventoryhandler.addToInv(invValue, random);
					game.addDropedItem(invValue, random);
					if (collected)
					{
						useHarvestingItem(game.Inventory[game.SelectedHotbar][5][0], game.SelectedHotbar, 5);
						game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
					}
				}
			}
			else if (tileValue == 4) //Harvesting Walls
			{
				basicHarvest((byte) 8, chunk, tileX, tileY);
			}
			else if (tileValue == 5) //Harvesting Different kinds of door starts here
			{
				basicHarvest((byte) 9, chunk, tileX, tileY);
			}
			else if (tileValue == 6)
			{
				basicHarvest((byte) 9, chunk, tileX, tileY);
			}
			else if (tileValue == 7)
			{
				basicHarvest((byte) 9, chunk, tileX, tileY);
			}
			else if (tileValue == 8) //Harvesting Different kinds of doors ends here
			{
				basicHarvest((byte) 9, chunk, tileX, tileY);
			}
			else if (tileValue == 9) //Harvesting a stone table
			{
				basicHarvest((byte) 10, chunk, tileX, tileY);
			}
			else if (tileValue == 13) //Harvesting a mine
			{
				byte invValue = 11;
				if (inventoryhandler.addToInv(invValue, 1))
				{
					game.addDropedItem(invValue, 1);
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
					int tempChunkX = chunk%3 - 1; //These 5 lines remove the mine that this mine leads to in the cove dimension.
					int tempChunkY = chunk/3 - 1;
					short tempChunk[][][] = game.files.ReadChunk(game.CurrentFile, game.ChunkX+tempChunkX, game.ChunkY+tempChunkY, (byte) 1);
					tempChunk[tileX][Math.abs(tileY)][0] = 0;
					game.files.WriteChunk(game.CurrentFile, game.ChunkX+tempChunkX, game.ChunkY+tempChunkY, tempChunk, (byte) 1);
				}
			}
			else if (tileValue == 15) //Harvesting an anvil
			{
				basicHarvest((byte) 29, chunk, tileX, tileY);
			}
			else if (tileValue == 14) //Harvesting a blast furnace
			{
				basicContainerHarvest((byte) 21, chunk, tileX, tileY, "BlastFurnace");
			}
			else if (tileValue == 16) { //Harvesting an LESU
				basicContainerHarvest((byte) 30, chunk, tileX, tileY, "LESU");
			}
			else if (tileValue == 17) { //Harvesting a copper wire
				basicContainerHarvest((byte) 31, chunk, tileX, tileY, "CopperWire");
			}
			else if (tileValue == 18) {
				basicContainerHarvest((byte) 32, chunk, tileX, tileY, "CoalGenerator");
			}
			else if (tileValue == 19) {
				basicContainerHarvest((byte) 33, chunk, tileX, tileY, "Refiner");
			}
			else if (tileValue == 20) {
				basicContainerHarvest((byte) 40, chunk, tileX, tileY, "Cabinet");
			}
			else if (tileValue == 21) {
				basicContainerHarvest((byte) 41, chunk, tileX, tileY, "ElectricFurnace");
			}
			//The lines of code found below are not done in the basicContainerHarvest method in a slightly different way.
//			if (ProcessorIDs.containerExists(SurfaceTileIDs.values()[tileValue].toString())) { //Removing containers from the processor linked list
//				Processors c = game.processhandler.getProcessor(ProcessorIDs.valueOf(SurfaceTileIDs.values()[tileValue].toString()), game.StoredTiles[chunk][tileX][Math.abs(tileY)][1]);
//				c.remove();
//				
//			}
		}
		else if (game.dimension == 1) { //Harvesting items in the mining dimension
			if (tileValue == 1) //Harvesting coal
			{
				if (inventoryhandler.comparePickHardness(game.Inventory[game.SelectedHotbar][5][0], 2))
				{
					byte invValue = 12;
					if (inventoryhandler.addToInv(invValue, 2))
					{
						harvestBulder();
						game.addDropedItem(invValue, 2);
						useHarvestingItem(game.Inventory[game.SelectedHotbar][5][0], game.SelectedHotbar, 5);
						game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
					}
				}
			}
			else if (tileValue == 2) //Harvesting iron
			{
				if (inventoryhandler.comparePickHardness(game.Inventory[game.SelectedHotbar][5][0], 3))
				{
					byte invValue = 13;
					if (inventoryhandler.addToInv(invValue, 2))
					{
						harvestBulder();
						game.addDropedItem(invValue, 2);
						useHarvestingItem(game.Inventory[game.SelectedHotbar][5][0], game.SelectedHotbar, 5);
						game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
					}
				}
			}
			else if (tileValue == 3) //Harvesting copper
			{
				if (inventoryhandler.comparePickHardness(game.Inventory[game.SelectedHotbar][5][0], 2))
				{
					byte invValue = 14;
					if (inventoryhandler.addToInv(invValue, 2))
					{
						harvestBulder();
						game.addDropedItem(invValue, 2);
						useHarvestingItem(game.Inventory[game.SelectedHotbar][5][0], game.SelectedHotbar, 5);
						game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
					}
				}
			}
			else if (tileValue == 4) //Harvesting gold
			{
				if (inventoryhandler.comparePickHardness(game.Inventory[game.SelectedHotbar][5][0], 4))
				{
					byte invValue = 15;
					if (inventoryhandler.addToInv(invValue, 2))
					{
						harvestBulder();
						game.addDropedItem(invValue, 2);
						useHarvestingItem(game.Inventory[game.SelectedHotbar][5][0], game.SelectedHotbar, 5);
						game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
					}
				}
			}
			else if (tileValue == 5) //Harvesting lithium
			{
				if (inventoryhandler.comparePickHardness(game.Inventory[game.SelectedHotbar][5][0], 1))
				{
					byte invValue = 16;
					if (inventoryhandler.addToInv(invValue, 2))
					{
						harvestBulder();
						game.addDropedItem(invValue, 2);
						useHarvestingItem(game.Inventory[game.SelectedHotbar][5][0], game.SelectedHotbar, 5);
						game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
					}
				}
			}
			else if (tileValue == 6) //Harvesting uranium
			{
				if (inventoryhandler.comparePickHardness(game.Inventory[game.SelectedHotbar][5][0], 6))
				{
					byte invValue = 17;
					if (inventoryhandler.addToInv(invValue, 1))
					{
						harvestBulder();
						game.addDropedItem(invValue, 1);
						useHarvestingItem(game.Inventory[game.SelectedHotbar][5][0], game.SelectedHotbar, 5);
						game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
					}
				}
			}
			else if (tileValue == 7) //Harvesting aluminum
			{
				if (inventoryhandler.comparePickHardness(game.Inventory[game.SelectedHotbar][5][0], 4))
				{
					byte invValue = 18;
					if (inventoryhandler.addToInv(invValue, 2))
					{
						harvestBulder();
						game.addDropedItem(invValue, 2);
						useHarvestingItem(game.Inventory[game.SelectedHotbar][5][0], game.SelectedHotbar, 5);
						game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
					}
				}
			}
			else if (tileValue == 8) //Harvesting topaz gems
			{
				byte invValue = 19;
				if (inventoryhandler.addToInv(invValue, 1))
				{
					game.addDropedItem(invValue, 1);
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
				}
			}
		}
	}
	
	private void basicHarvest(byte invValue, int chunk, int tileX, int tileY) {
		if (inventoryhandler.addToInv(invValue, 1))
		{
			game.addDropedItem(invValue, 1);
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
		}
	}
	
	private void basicContainerHarvest(byte invValue, int chunk, int tileX, int tileY, String filePrefix) {
		if (inventoryhandler.addToInv(invValue, 1))
		{
			Processors container = game.processhandler.getProcessor(ProcessorIDs.valueOf(SurfaceTileIDs.values()[game.StoredTiles[chunk][tileX][Math.abs(tileY)][0]].toString()), game.StoredTiles[chunk][tileX][Math.abs(tileY)][1]);
			for (int j = 0; j < 5; j++) {
				for (int i = 0; i < 5; i++) {
					if (container.getValidSlots()[j][i]) {
						inventoryhandler.addToInv(container.getContainerSlots()[i][j][0], container.getContainerSlots()[i][j][1]);
					}
				}
			}
			game.addDropedItem(invValue, 1);
			game.files.deleteTextFile("Files/File "+game.CurrentFile+"/Tiles/"+filePrefix+" "+game.StoredTiles[chunk][tileX][Math.abs(tileY)][1]+".txt");
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][1] = 0;
			game.processhandler.reloadProcessors();
			game.daytimecycle.removeLightSource((byte) chunk, (byte) tileX, (byte) tileY);
		}
	}

	private void harvestBulder() {
		if (Math.random() > 0.5) {
			byte invValue = 1;
			if (inventoryhandler.addToInv(invValue, 1))
			{
				game.addDropedItem(invValue, 1);
			}
		}
	}
	
	private void itemBroken(short itemID, int x, int y) {
		if (Identifications.isDurabilityItem(itemID)) {
			String fileNameIdentifier = Identifications.getDurabilityFileName(itemID);
			int durability = Integer.parseInt(game.files.ReadLine("Files/File "+game.CurrentFile+"/Inventory/"+fileNameIdentifier+" "+game.Inventory[x][y][1]+".txt", 1));
			durability--;
			if (durability == 0)
			{
				game.files.deleteTextFile("Files/File "+game.CurrentFile+"/Inventory/"+fileNameIdentifier+" "+game.Inventory[x][y][1]+".txt");
				game.Inventory[x][y][0] = 0;
				game.Inventory[x][y][1] = 0;
			}
			else
			{
				game.files.RewriteLine("Files/File "+game.CurrentFile+"/Inventory/"+fileNameIdentifier+" "+game.Inventory[x][y][1]+".txt", 1, ""+durability);
			}
		}
	}
	
	private void useHarvestingItem(short itemID, int i, int j)
	{
		if (itemID == 4)
		{
			game.Inventory[i][j][1]--;
		}
		else
		{
			itemBroken(itemID, i, j);
		}
	}

	
}
