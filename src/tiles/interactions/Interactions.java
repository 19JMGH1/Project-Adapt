package tiles.interactions;

import java.awt.Graphics;

import core.Files;
import core.Identifications;
import core.Main_Game;
import core.Main_Game.Dimensions;
import items.ItemIDs;
import processors.management.ProcessorIDs;
import tiles.SurfaceTileIDs;

public class Interactions {

	private Main_Game game;
	private Files files;

	public Interactions(Main_Game game, Files files)
	{
		this.game = game;
		this.files = files;
	}

	public void destroyCheck(int location)
	{
		//System.out.println("checking tile..."); //For testing purposes
		int chunk = 4;
		int tileX = game.TileX;
		int tileY = game.TileY;
		if (location == 1)
		{
			tileX--;
			tileY++;
			chunk = chunkXAdjust(chunk, tileX);
			tileX = tileXAdjust(tileX);
			chunk = chunkYAdjust(chunk, tileY);
			tileY = tileYAdjust(tileY);
			synchronized(game.StoredTiles) {
				collect(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
			}
		}
		else if (location == 2)
		{
			tileY++;
			chunk = chunkYAdjust(chunk, tileY);
			tileY = tileYAdjust(tileY);
			synchronized(game.StoredTiles) {
				collect(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
			}
		}
		else if (location == 3)
		{
			tileX++;
			tileY++;
			chunk = chunkXAdjust(chunk, tileX);
			tileX = tileXAdjust(tileX);
			chunk = chunkYAdjust(chunk, tileY);
			tileY = tileYAdjust(tileY);
			synchronized(game.StoredTiles) {
				collect(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
			}
		}
		else if (location == 4)
		{
			tileX--;
			chunk = chunkXAdjust(chunk, tileX);
			tileX = tileXAdjust(tileX);
			synchronized(game.StoredTiles) {
				collect(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
			}
		}
		else if (location == 5)
		{
			synchronized(game.StoredTiles) {
				collect(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
			}
		}
		else if (location == 6)
		{
			tileX++;
			chunk = chunkXAdjust(chunk, tileX);
			tileX = tileXAdjust(tileX);
			synchronized(game.StoredTiles) {
				collect(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
			}
		}
		else if (location == 7)
		{
			tileX--;
			tileY--;
			chunk = chunkXAdjust(chunk, tileX);
			tileX = tileXAdjust(tileX);
			chunk = chunkYAdjust(chunk, tileY);
			tileY = tileYAdjust(tileY);
			synchronized(game.StoredTiles) {
				collect(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
			}
		}
		else if (location == 8)
		{
			tileY--;
			chunk = chunkYAdjust(chunk, tileY);
			tileY = tileYAdjust(tileY);
			synchronized(game.StoredTiles) {
				collect(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
			}
		}
		else if (location == 9)
		{
			tileX++;
			tileY--;
			chunk = chunkXAdjust(chunk, tileX);
			tileX = tileXAdjust(tileX);
			chunk = chunkYAdjust(chunk, tileY);
			tileY = tileYAdjust(tileY);
			synchronized(game.StoredTiles) {
				collect(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
			}
		}
	}

	public void useCheck(short location)
	{
		//System.out.println("checking tile..."); //For testing purposes
		int chunk = 4;
		int tileX = game.TileX;
		int tileY = game.TileY;
		synchronized(game.StoredTiles) {
			if (location == 1)
			{
				tileX--;
				tileY++;
				chunk = chunkXAdjust(chunk, tileX);
				tileX = tileXAdjust(tileX);
				chunk = chunkYAdjust(chunk, tileY);
				tileY = tileYAdjust(tileY);
				use(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY, location);
			}
			else if (location == 2)
			{
				tileY++;
				chunk = chunkYAdjust(chunk, tileY);
				tileY = tileYAdjust(tileY);
				use(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY, location);
			}
			else if (location == 3)
			{
				tileX++;
				tileY++;
				chunk = chunkXAdjust(chunk, tileX);
				tileX = tileXAdjust(tileX);
				chunk = chunkYAdjust(chunk, tileY);
				tileY = tileYAdjust(tileY);
				use(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY, location);
			}
			else if (location == 4)
			{
				tileX--;
				chunk = chunkXAdjust(chunk, tileX);
				tileX = tileXAdjust(tileX);
				use(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY, location);
			}
			else if (location == 5)
			{
				use(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY, location);
			}
			else if (location == 6)
			{
				tileX++;
				chunk = chunkXAdjust(chunk, tileX);
				tileX = tileXAdjust(tileX);
				use(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY, location);
			}
			else if (location == 7)
			{
				tileX--;
				tileY--;
				chunk = chunkXAdjust(chunk, tileX);
				tileX = tileXAdjust(tileX);
				chunk = chunkYAdjust(chunk, tileY);
				tileY = tileYAdjust(tileY);
				use(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY, location);
			}
			else if (location == 8)
			{
				tileY--;
				chunk = chunkYAdjust(chunk, tileY);
				tileY = tileYAdjust(tileY);
				use(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY, location);
			}
			else if (location == 9)
			{
				tileX++;
				tileY--;
				chunk = chunkXAdjust(chunk, tileX);
				tileX = tileXAdjust(tileX);
				chunk = chunkYAdjust(chunk, tileY);
				tileY = tileYAdjust(tileY);
				use(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY, location);
			}
		}
	}

	private int chunkXAdjust(int chunk, int tileX)
	{
		if (tileX < 0)
		{
			chunk--;
		}
		else if (tileX > 15)
		{
			chunk++;
		}
		return chunk;
	}

	private int chunkYAdjust(int chunk, int tileY)
	{
		if (tileY > 0)
		{
			chunk -= 3;
		}
		else if (tileY < -15)
		{
			chunk += 3;
		}
		return chunk;
	}

	private int tileXAdjust(int tileX)
	{
		if (tileX > 15)
		{
			tileX -= 16;
		}
		else if (tileX < 0)
		{
			tileX += 16;
		}
		return tileX;
	}

	private int tileYAdjust(int tileY)
	{
		if (tileY > 0)
		{
			tileY -= 16;
		}
		else if (tileY < -15)
		{
			tileY += 16;
		}
		return tileY;
	}

	private void use(short tileValue, int chunk, int tileX, int tileY, short location)
	{
		if (game.dimension == Dimensions.surface) {
			synchronized(game.StoredTiles) {
				if (tileValue == 0)
				{
					place(chunk, tileX, tileY);
				}
				else if (tileValue == 5) //This is the start of opening and closing doors
				{
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 7;
				}
				else if (tileValue == 6)
				{
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 8;
				}
				else if (tileValue == 7)
				{
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 5;
				}

				else if (tileValue == 8)
				{
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 6;
				} //This is the end of opening and closing doors
				else if (tileValue == 13) //Going into a mine on the surface
				{
					game.handler.removeAllCreatures();
					game.switchDimension(Dimensions.coves);
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 9; //The next lines in this else if set the place you enter the mining dimension to a mine and make some blank tiles around it
					tileY++;
					tileX--;
					chunk = chunkXAdjust(chunk, tileX);
					tileX = tileXAdjust(tileX);
					chunk = chunkYAdjust(chunk, tileY);
					tileY = tileYAdjust(tileY);
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
					tileX++;
					chunk = chunkXAdjust(chunk, tileX);
					tileX = tileXAdjust(tileX);
					chunk = chunkYAdjust(chunk, tileY);
					tileY = tileYAdjust(tileY);
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
					tileX++;
					chunk = chunkXAdjust(chunk, tileX);
					tileX = tileXAdjust(tileX);
					chunk = chunkYAdjust(chunk, tileY);
					tileY = tileYAdjust(tileY);
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
					tileY--;
					tileX -= 2;
					chunk = chunkXAdjust(chunk, tileX);
					tileX = tileXAdjust(tileX);
					chunk = chunkYAdjust(chunk, tileY);
					tileY = tileYAdjust(tileY);
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
					tileX += 2;
					chunk = chunkXAdjust(chunk, tileX);
					tileX = tileXAdjust(tileX);
					chunk = chunkYAdjust(chunk, tileY);
					tileY = tileYAdjust(tileY);
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
					tileY--;
					tileX -= 2;
					chunk = chunkXAdjust(chunk, tileX);
					tileX = tileXAdjust(tileX);
					chunk = chunkYAdjust(chunk, tileY);
					tileY = tileYAdjust(tileY);
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
					tileX++;
					chunk = chunkXAdjust(chunk, tileX);
					tileX = tileXAdjust(tileX);
					chunk = chunkYAdjust(chunk, tileY);
					tileY = tileYAdjust(tileY);
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
					tileX++;
					chunk = chunkXAdjust(chunk, tileX);
					tileX = tileXAdjust(tileX);
					chunk = chunkYAdjust(chunk, tileY);
					tileY = tileYAdjust(tileY);
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = 0;
				}
				else if (tileValue == SurfaceTileIDs.Water.ordinal()) {
					if(game.Inventory[game.SelectedHotbar][5][0] == ItemIDs.Boat.ordinal()) {
						int tempLocation = location-1;
						if (tempLocation/3 == 0) {
							game.TileY++;
							game.y = 0;
						}
						else if (tempLocation/3 == 2) {
							game.TileY--;
							game.y = 0;
						}
						if (tempLocation%3 == 0) {
							game.TileX--;
							game.x = 0;
						}
						else if (tempLocation%3 == 2) {
							game.TileX++;
							game.x = 0;
						}
						game.inBoat = true;
						game.Inventory[game.SelectedHotbar][5][1]--;
					}
					else {
						game.addMessage("Craft a boat to cross the water");
					}
				}
				else if (ProcessorIDs.containerExists(SurfaceTileIDs.values()[tileValue].toString())) { //Opening Containers
					game.curentlyOpenedContainer = game.processhandler.getProcessor(ProcessorIDs.valueOf(SurfaceTileIDs.values()[tileValue].toString()), game.StoredTiles[chunk][tileX][Math.abs(tileY)][1]);
					game.inventoryOpened = true;
				}
			}
		}
		else if (game.dimension == Dimensions.coves) {
			if (tileValue == 9)
			{
				synchronized(game.StoredTiles) {
					game.switchDimension(Dimensions.surface);
				}
			}
		}
	}

	private void place(int chunk, int tileX, int tileY)
	{
		game.placetile.place(chunk, tileX, tileY);
	}
	
	public boolean checkSurroundingsNot(short tileWanted, int chunk, int tileX, int tileY) {
		if (!checkAbove(tileWanted, chunk, tileX, tileY) || !checkBelow(tileWanted, chunk, tileX, tileY) || !checkLeft(tileWanted, chunk, tileX, tileY) || !checkRight(tileWanted, chunk, tileX, tileY)) {
			return true;
		}
		return false;
	}
	
	public boolean checkAbove(short tileWanted, int chunk, int tileX, int tileY)
	{
		tileY++;
		chunk = chunkYAdjust(chunk, tileY);
		tileY = tileYAdjust(tileY);
		if (chunk >= 9 || chunk < 0 || tileX >= 16 || tileX < 0 || tileY <= -16 || tileY > 0) {
			return false;
		}
		if (game.StoredTiles[Math.abs(chunk)][Math.abs(tileX)][Math.abs(tileY)][0] == tileWanted)
		{
			return true;
		}
		return false;
	}

	public boolean checkBelow(short tileWanted, int chunk, int tileX, int tileY)
	{
		tileY--;
		chunk = chunkYAdjust(chunk, tileY);
		tileY = tileYAdjust(tileY);
		if (game.StoredTiles[Math.abs(chunk)][Math.abs(tileX)][Math.abs(tileY)][0] == tileWanted)
		{
			return true;
		}
		return false;
	}

	public boolean checkLeft(short tileWanted, int chunk, int tileX, int tileY)
	{
		tileX--;
		chunk = chunkXAdjust(chunk, tileX);
		tileX = tileXAdjust(tileX);
		if (game.StoredTiles[Math.abs(chunk)][Math.abs(tileX)][Math.abs(tileY)][0] == tileWanted)
		{
			return true;
		}
		return false;
	}

	public boolean checkRight(short tileWanted, int chunk, int tileX, int tileY)
	{
		tileX++;
		chunk = chunkXAdjust(chunk, tileX);
		tileX = tileXAdjust(tileX);
		if (game.StoredTiles[Math.abs(chunk)][Math.abs(tileX)][Math.abs(tileY)][0] == tileWanted)
		{
			return true;
		}
		return false;
	}

	public boolean checkAboveLeft(short tileWanted, int chunk, int tileX, int tileY)
	{
		tileY++;
		tileX--;
		chunk = chunkYAdjust(chunk, tileY);
		chunk = chunkXAdjust(chunk, tileX);
		tileY = tileYAdjust(tileY);
		tileX = tileXAdjust(tileX);
		if (chunk >= 9 || chunk < 0 || tileX >= 16 || tileX < 0 || tileY <= -16 || tileY > 0) {
			return false;
		}
		if (game.StoredTiles[Math.abs(chunk)][Math.abs(tileX)][Math.abs(tileY)][0] == tileWanted)
		{
			return true;
		}
		return false;
	}

	public boolean checkAboveRight(short tileWanted, int chunk, int tileX, int tileY)
	{
		tileY++;
		tileX++;
		chunk = chunkYAdjust(chunk, tileY);
		chunk = chunkXAdjust(chunk, tileX);
		tileY = tileYAdjust(tileY);
		tileX = tileXAdjust(tileX);
		if (chunk >= 9 || chunk < 0 || tileX >= 16 || tileX < 0 || tileY <= -16 || tileY > 0) {
			return false;
		}
		if (game.StoredTiles[Math.abs(chunk)][Math.abs(tileX)][Math.abs(tileY)][0] == tileWanted)
		{
			return true;
		}
		return false;
	}

	public boolean checkBelowLeft(short tileWanted, int chunk, int tileX, int tileY)
	{
		tileY--;
		tileX--;
		chunk = chunkYAdjust(chunk, tileY);
		chunk = chunkXAdjust(chunk, tileX);
		tileY = tileYAdjust(tileY);
		tileX = tileXAdjust(tileX);
		if (chunk >= 9 || chunk < 0 || tileX >= 16 || tileX < 0 || tileY <= -16 || tileY > 0) {
			return false;
		}
		if (game.StoredTiles[Math.abs(chunk)][Math.abs(tileX)][Math.abs(tileY)][0] == tileWanted)
		{
			return true;
		}
		return false;
	}

	public boolean checkBelowRight(short tileWanted, int chunk, int tileX, int tileY)
	{
		tileY--;
		tileX++;
		chunk = chunkYAdjust(chunk, tileY);
		chunk = chunkXAdjust(chunk, tileX);
		tileY = tileYAdjust(tileY);
		tileX = tileXAdjust(tileX);
		if (chunk >= 9 || chunk < 0 || tileX >= 16 || tileX < 0 || tileY <= -16 || tileY > 0) {
			return false;
		}
		if (game.StoredTiles[Math.abs(chunk)][Math.abs(tileX)][Math.abs(tileY)][0] == tileWanted)
		{
			return true;
		}
		return false;
	}

	public boolean checkNearbyTiles(short tileWanted, int chunk, int tileX, int tileY) {
		if (checkAbove(tileWanted, chunk, tileX, tileY)||
				checkBelow(tileWanted, chunk, tileX, tileY)||
				checkLeft(tileWanted, chunk, tileX, tileY)||
				checkRight(tileWanted, chunk, tileX, tileY)||
				checkAboveLeft(tileWanted, chunk, tileX, tileY)||
				checkAboveRight(tileWanted, chunk, tileX, tileY)||
				checkBelowLeft(tileWanted, chunk, tileX, tileY)||
				checkBelowRight(tileWanted, chunk, tileX, tileY)) {
			return true;
		}
		return false;
	}

	private void collect(short tileValue, int chunk, int tileX, int tileY)
	{
		game.harvesttile.collect(tileValue, chunk, tileX, tileY);
	}

	public void itemBroken(short itemID, int x, int y) {
		if (Identifications.isDurabilityItem(itemID)) {
			String fileNameIdentifier = Identifications.getDurabilityFileName(itemID);
			int durability = Integer.parseInt(files.ReadLine("Files/File "+game.CurrentFile+"/Inventory/"+fileNameIdentifier+" "+game.Inventory[x][y][1]+".txt", 1));
			durability--;
			if (durability == 0)
			{
				files.deleteTextFile("Files/File "+game.CurrentFile+"/Inventory/"+fileNameIdentifier+" "+game.Inventory[x][y][1]+".txt");
				game.Inventory[x][y][0] = 0;
				game.Inventory[x][y][1] = 0;
			}
			else
			{
				files.RewriteLine("Files/File "+game.CurrentFile+"/Inventory/"+fileNameIdentifier+" "+game.Inventory[x][y][1]+".txt", 1, ""+durability);
			}
		}
	}

	public void useHarvestingItem(short itemID, int i, int j)
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

	public void useItem(short itemID, int i, int j)
	{
		if (itemID == 5)
		{
			if (game.craftingLevel == 0)
			{
				game.craftingLevel = 1;
				game.SaveFile();
			}
			game.Inventory[i][j][1]--;
		}
		if (itemID == 28)
		{
			if (game.craftingLevel == 1)
			{
				game.craftingLevel = 2;
				game.SaveFile();
				game.Inventory[i][j][1]--;
			}
		}
	}

	public void tick()
	{
		if (game.resized)
		{

		}
		//These statements check if there is a stone table or anvil around to increase the players crafting level
		//It check the anvil and if there is none, it checks for the stone table afterwards
		if (checkNearbyTiles((short) SurfaceTileIDs.Anvil.ordinal(), 4, game.TileX, game.TileY))
		{
			//System.out.println("we got here"); //Just for testing purposes
			game.tempCraftingLevel = 3;
		}
		else if (checkNearbyTiles((short) SurfaceTileIDs.StoneTable.ordinal(), 4, game.TileX, game.TileY))
		{
			game.tempCraftingLevel = 2;
		}
		else
		{
			game.tempCraftingLevel = 0;
		}
	}

	public void render(Graphics g)
	{

	}
}
