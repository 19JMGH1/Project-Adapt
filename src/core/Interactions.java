package core;

import java.awt.Graphics;

import containers.ContainerIDs;
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
			collect(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
		}
		else if (location == 2)
		{
			tileY++;
			chunk = chunkYAdjust(chunk, tileY);
			tileY = tileYAdjust(tileY);
			collect(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
		}
		else if (location == 3)
		{
			tileX++;
			tileY++;
			chunk = chunkXAdjust(chunk, tileX);
			tileX = tileXAdjust(tileX);
			chunk = chunkYAdjust(chunk, tileY);
			tileY = tileYAdjust(tileY);
			collect(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
		}
		else if (location == 4)
		{
			tileX--;
			chunk = chunkXAdjust(chunk, tileX);
			tileX = tileXAdjust(tileX);
			collect(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
		}
		else if (location == 5)
		{
			collect(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
		}
		else if (location == 6)
		{
			tileX++;
			chunk = chunkXAdjust(chunk, tileX);
			tileX = tileXAdjust(tileX);
			collect(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
		}
		else if (location == 7)
		{
			tileX--;
			tileY--;
			chunk = chunkXAdjust(chunk, tileX);
			tileX = tileXAdjust(tileX);
			chunk = chunkYAdjust(chunk, tileY);
			tileY = tileYAdjust(tileY);
			collect(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
		}
		else if (location == 8)
		{
			tileY--;
			chunk = chunkYAdjust(chunk, tileY);
			tileY = tileYAdjust(tileY);
			collect(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
		}
		else if (location == 9)
		{
			tileX++;
			tileY--;
			chunk = chunkXAdjust(chunk, tileX);
			tileX = tileXAdjust(tileX);
			chunk = chunkYAdjust(chunk, tileY);
			tileY = tileYAdjust(tileY);
			collect(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
		}
	}

	public void useCheck(int location)
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
			use(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
		}
		else if (location == 2)
		{
			tileY++;
			chunk = chunkYAdjust(chunk, tileY);
			tileY = tileYAdjust(tileY);
			use(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
		}
		else if (location == 3)
		{
			tileX++;
			tileY++;
			chunk = chunkXAdjust(chunk, tileX);
			tileX = tileXAdjust(tileX);
			chunk = chunkYAdjust(chunk, tileY);
			tileY = tileYAdjust(tileY);
			use(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
		}
		else if (location == 4)
		{
			tileX--;
			chunk = chunkXAdjust(chunk, tileX);
			tileX = tileXAdjust(tileX);
			use(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
		}
		else if (location == 5)
		{
			use(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
		}
		else if (location == 6)
		{
			tileX++;
			chunk = chunkXAdjust(chunk, tileX);
			tileX = tileXAdjust(tileX);
			use(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
		}
		else if (location == 7)
		{
			tileX--;
			tileY--;
			chunk = chunkXAdjust(chunk, tileX);
			tileX = tileXAdjust(tileX);
			chunk = chunkYAdjust(chunk, tileY);
			tileY = tileYAdjust(tileY);
			use(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
		}
		else if (location == 8)
		{
			tileY--;
			chunk = chunkYAdjust(chunk, tileY);
			tileY = tileYAdjust(tileY);
			use(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
		}
		else if (location == 9)
		{
			tileX++;
			tileY--;
			chunk = chunkXAdjust(chunk, tileX);
			tileX = tileXAdjust(tileX);
			chunk = chunkYAdjust(chunk, tileY);
			tileY = tileYAdjust(tileY);
			use(game.StoredTiles[chunk][tileX][Math.abs(tileY)][0], chunk, tileX, tileY);
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

	private void use(short tileValue, int chunk, int tileX, int tileY)
	{
		if (game.dimension == 0) {
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
				game.switchDimension((byte) 1);
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
			else if (ContainerIDs.containerExists(SurfaceTileIDs.values()[tileValue].toString())) { //Opening Containers
				game.curentlyOpenedContainer = game.containerprocesshandler.getProcessor(ContainerIDs.valueOf(SurfaceTileIDs.values()[tileValue].toString()), game.StoredTiles[chunk][tileX][Math.abs(tileY)][1]);
				game.inventoryOpened = true;
			}
		}
		else if (game.dimension == 1) {
			if (tileValue == 9)
			{
				game.switchDimension((byte) 0);
			}
		}
	}

	private void place(int chunk, int tileX, int tileY)
	{
		game.placetile.place(chunk, tileX, tileY);
	}

	public boolean checkAbove(short tileWanted, int chunk, int tileX, int tileY)
	{
		tileY++;
		chunk = chunkYAdjust(chunk, tileY);
		tileY = tileYAdjust(tileY);
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

	public boolean checkLeft(byte tileWanted, int chunk, int tileX, int tileY)
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
		if (checkAbove((byte) 9, 4, game.TileX, game.TileY)||checkBelow((byte) 9, 4, game.TileX, game.TileY)||checkLeft((byte) 9, 4, game.TileX, game.TileY)||checkRight((byte) 9, 4, game.TileX, game.TileY))
		{
			game.tempCraftingLevel = 2;
		}
		else if (checkAbove((byte) 15, 4, game.TileX, game.TileY)||checkBelow((byte) 15, 4, game.TileX, game.TileY)||checkLeft((byte) 15, 4, game.TileX, game.TileY)||checkRight((byte) 15, 4, game.TileX, game.TileY))
		{
			//System.out.println("we got here"); //Just for testing purposes
			game.tempCraftingLevel = 3;
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
