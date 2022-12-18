package entities.management;
import java.lang.Math;

import core.Main_Game;
import core.Main_Game.Dimensions;
import tiles.SurfaceTileIDs;

public class Collision {

	private Main_Game game;

	public Collision(Main_Game game)
	{
		this.game = game;
	}

	public boolean checkX(int X, int Y, int tileX, int tileY, int chunkX, int chunkY, int velX, boolean inBoat) //The inBoat parameter controls if we are checking collision for entities in boats or out of boats.
	{
		if (chunkY > 2 || chunkY < 0) {
			return false;
		}
		if (chunkX > 2 || chunkX < 0) {
			return false;
		}
		if (velX == 0)
		{
			return true;
		}
		if (tileX == 16)
			tileX = 15;

		if (X >= game.TileWidth/8 && X <= game.TileWidth/4)
		{
			if (velX > 0) 
			{
				if (!(Y >= 0 && Y <= game.TileHeight/4))
				{
					if (tileX == 15)
					{
						if (checkTile(0, tileY, chunkX+1, chunkY, inBoat))
							return false;
						if (checkTile(0, tileY+1, chunkX+1, chunkY, inBoat))
							return false;
					}
					else
					{
						if (checkTile(tileX+1, tileY, chunkX, chunkY, inBoat))
							return false;
						if (checkTile(tileX+1, tileY+1, chunkX, chunkY, inBoat))
							return false;
					}
				}
				else
				{
					if (tileX == 15)
					{
						if (checkTile(0, tileY, chunkX+1, chunkY, inBoat))
							return false;
					}
					else
					{
						if (checkTile(tileX+1, tileY, chunkX, chunkY, inBoat))
							return false;
					}
				}
			}
		}
		if (X >= 0 && X <= game.TileWidth/8)
		{
			if (velX < 0) 
			{
				if (!(Y >= 0 && Y <= game.TileHeight/4))
				{
					if (tileX == 0)
					{
						if (checkTile(15, tileY, chunkX-1, chunkY, inBoat))
							return false;
						if (checkTile(15, tileY+1, chunkX-1, chunkY, inBoat))
							return false;
					}
					else
					{
						if (checkTile(tileX-1, tileY, chunkX, chunkY, inBoat))
							return false;
						if (checkTile(tileX-1, tileY+1, chunkX, chunkY, inBoat))
							return false;
					}
				}
				else
				{
					if (tileX == 0)
					{
						if (checkTile(15, tileY, chunkX-1, chunkY, inBoat))
							return false;
					}
					else
					{
						if (checkTile(tileX-1, tileY, chunkX, chunkY, inBoat))
							return false;
					}
				}
			}
		}
		return true;
	}

	public boolean checkY(int X, int Y, int tileX, int tileY, int chunkX, int chunkY, int velY, boolean inBoat) //The inBoat parameter controls if we are checking collision for entities in boats or out of boats.
	{
		if (velY == 0)
		{
			return true;
		}
		if (tileY == -16)
			tileY = -15;

		if (Y >= game.TileHeight/8 && Y <= game.TileHeight/4)
		{
			if (velY > 0) 
			{
				if (!(X >= 0 && X <= game.TileWidth/4))
				{
					if (tileY == 0)
					{
						if (checkTile(tileX, -15, chunkX, chunkY-1, inBoat))
							return false;
						if (checkTile(tileX+1, -15, chunkX, chunkY-1, inBoat))
							return false;
					}
					else
					{
						if (checkTile(tileX, tileY+1, chunkX, chunkY, inBoat))
							return false;
						if (checkTile(tileX+1, tileY+1, chunkX, chunkY, inBoat))
							return false;
					}
				}
				else
				{
					if (tileY == 0)
					{
						if (checkTile(tileX, -15, chunkX, chunkY-1, inBoat))
							return false;
					}
					else
					{
						if (checkTile(tileX, tileY+1, chunkX, chunkY, inBoat))
							return false;
					}
				}
			}
		}
		if (Y >= 0 && Y <= game.TileHeight/8)
			if (velY < 0) 
			{
				if (!(X >= 0 && X <= game.TileWidth/4))
				{
					if (tileY == -15)
					{
						if (checkTile(tileX, 0, chunkX, chunkY+1, inBoat))
							return false;
						if (checkTile(tileX+1, 0, chunkX, chunkY+1, inBoat))
							return false;
					}
					else
					{
						if (checkTile(tileX, tileY-1, chunkX, chunkY, inBoat))
							return false;
						if (checkTile(tileX+1, tileY-1, chunkX, chunkY, inBoat))
							return false;
					}
				}
				else
				{
					if (tileY == -15)
					{
						if (checkTile(tileX, 0, chunkX, chunkY+1, inBoat))
							return false;
					}
					else
					{
						if (checkTile(tileX, tileY-1, chunkX, chunkY, inBoat))
							return false;
					}
				}
			}
		return true;
	}

	private boolean checkTile(int tileX, int tileY, int chunkX, int chunkY, boolean inBoat) //The inBoat parameter controls if we are checking collision for entities in boats or out of boats.
	{
		if (tileX == 16)
		{
			tileX -= 16;
			chunkX += 1;
		}
		else if (tileX == -1)
		{
			tileX += 16;
			chunkX -= 1;
		}
		if (tileY == -16)
		{
			tileY += 16;
			chunkY += 1;
		}
		else if (tileY == 1)
		{
			tileY -= 16;
			chunkY -= 1;
		}
		if (chunkX*1+chunkY*3 >= 9 || chunkX*1+chunkY*3 <= 0) {
			return false;
		}
		if (validTile(game.StoredTiles[chunkX*1+chunkY*3][Math.abs(tileX)][Math.abs(tileY)][0], inBoat))
		{
			return false;
		}
		return true;
	}

	private boolean validTile(short tile, boolean inBoat) //The inBoat parameter controls if we are checking collision for entities in boats or out of boats.
	{
		//System.out.println(inBoat);
		if (game.dimension == Dimensions.surface) {
			if ((SurfaceTileIDs.values()[tile].isCollides() && !inBoat) || (inBoat && (SurfaceTileIDs.values()[tile] != SurfaceTileIDs.Water)))
				//The above if statement contains all the numbers corresponding to tiles that need collision detection on the surface.
			{
				return false;
			}
		}
		else if (game.dimension == Dimensions.coves) {
			if ((tile == 1)||(tile == 2)||(tile == 3)||(tile == 4)||(tile == 5)||(tile == 6)||(tile == 7)) {
				//The above if statement contains all the numbers corresponding to tiles that need collision detection in the cove.
				return false;
			}
		}
		return true;
	}

}
