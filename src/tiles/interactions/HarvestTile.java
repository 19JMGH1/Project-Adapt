package tiles.interactions;

import core.Identifications;
import core.InventoryHandler;
import core.Main_Game;
import processors.ProcessHandler;
import processors.ProcessorIDs;
import processors.Processors;
import processors.electronics.ElectronicHandler;
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
		if (game.dimension == 0) { //This else-if runs through all the methods needed to harvest a given tile
			SurfaceTileIDs tile = SurfaceTileIDs.values()[tileValue]; //Save a variable of the tile that was broken for ease of access
			if (!tile.getTool().equals("unbreakable")) //Harvesting any tile
			{
				if (tileValue == 13) { //Removes the mine tile in the mining dimension if its surface counterpart is removed
					game.files.RewriteLine("Files/File "+game.CurrentFile+"/Mine Chunks/Chunk "+((game.ChunkX-1)+(chunk%3))+" "+((game.ChunkY-1)+(chunk/3))+".txt", (Math.abs(tileY)+1+(tileX*16)), "0 0 0");
				}
				if (tile.getTool().equals("pickaxe")) { //Makes sure you are holding a pickaxe is the tile needs a pickaxe to be mined
					if (inventoryhandler.isPick(game.Inventory[game.SelectedHotbar][5][0])) {
						useHarvestingItem(game.Inventory[game.SelectedHotbar][5][0], game.SelectedHotbar, 5);
					}
					else {
						return;
					}
				}
				if (tile.getTool().equals("axe")) { //Makes sure you are holding a axe is the tile needs a axe to be mined
					if (inventoryhandler.isAxe(game.Inventory[game.SelectedHotbar][5][0])) {
						useHarvestingItem(game.Inventory[game.SelectedHotbar][5][0], game.SelectedHotbar, 5);
					}
					else {
						return;
					}
				}
				if (!tile.isContainer()) { //Makes sure the text file for the container is removed if a container is what was removed.
					for (int j = 0; j < tile.getItems().length; j++) {
						basicHarvest((byte) tile.getItems()[j][0], tile.getItems()[j][1], chunk, tileX, tileY);
					}
				}
				else {
					basicContainerHarvest((byte) tile.getItems()[0][0], chunk, tileX, tileY, tile);
				}
			}
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

	private void basicHarvest(byte invValue, int num, int chunk, int tileX, int tileY) {
		if (inventoryhandler.addToInv(invValue, num))
		{
			game.addDropedItem(invValue, num);
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
		}
	}

	private void basicContainerHarvest(byte invValue, int chunk, int tileX, int tileY, SurfaceTileIDs tile) {
		Processors container = game.processhandler.getProcessor(ProcessorIDs.valueOf(SurfaceTileIDs.values()[game.StoredTiles[chunk][tileX][Math.abs(tileY)][0]].toString()), game.StoredTiles[chunk][tileX][Math.abs(tileY)][1]);
		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5; i++) {
				if (container.getValidSlots()[j][i]) {
					inventoryhandler.addToInv(container.getContainerSlots()[i][j][0], container.getContainerSlots()[i][j][1]);
				}
			}
		}
		if (inventoryhandler.addToInv(invValue, 1)) {
			game.addDropedItem(invValue, 1);
		}
		game.files.deleteTextFile("Files/File "+game.CurrentFile+"/Tiles/"+tile.getName()+" "+game.StoredTiles[chunk][tileX][Math.abs(tileY)][1]+".txt");
		game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
		game.StoredTiles[chunk][tileX][Math.abs(tileY)][1] = 0;
		ProcessHandler.processors.remove(container);
		if (tile.isElectronic()) {
			ElectronicHandler.electronics.remove(container);
		}
		//game.processhandler.reloadProcessors();
		game.processhandler.reload = true;
		game.daytimecycle.removeLightSource((byte) chunk, (byte) tileX, (byte) tileY);
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
