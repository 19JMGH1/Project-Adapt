package tiles.interactions;

import java.lang.reflect.InvocationTargetException;

import core.Main_Game;
import items.ItemIDs;
import processors.electronics.management.Electronic;
import processors.electronics.management.ElectronicHandler;
import processors.management.ProcessHandler;
import processors.management.ProcessorIDs;
import processors.management.Processors;
import tiles.SurfaceTileIDs;

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
		ItemIDs itemID = ItemIDs.values()[invValue];
		if (itemID.getTile() == 0) {
			return;
		}

		if (invValue == 9) //Places the door tile in its correct orientation
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
		else {
			SurfaceTileIDs tile = SurfaceTileIDs.values()[itemID.getTile()];
			game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = (short) itemID.getTile();
			if (tile.isProcessor()) {
				ProcessorIDs processor = ProcessorIDs.NoContainer;
				for (int i = 0; i < ProcessorIDs.values().length; i++) {
					if (ProcessorIDs.values()[i].tileID.ordinal() == tile.ordinal()) {
						processor = ProcessorIDs.values()[i];
						break;
					}
				}
				Class<?> cla = null;
				if (processor.electronic) {
					try {
						cla = Class.forName("processors.electronics."+processor.toString());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = (short) tile.ordinal();
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][1] = game.files.getTileFileID((short) tile.ordinal());
					int chunkX = game.ChunkX-1+(chunk%3);
					int chunkY = (game.ChunkY+1)-(chunk/3);
					Electronic e = null;
					try {
						e = (Electronic) cla.getConstructors()[0].newInstance(game, game.StoredTiles[chunk][tileX][Math.abs(tileY)][1], chunkX, chunkY, tileX, Math.abs(tileY));
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | SecurityException e1) {
						e1.printStackTrace();
					}
					ProcessHandler.processors.add(e);
					ElectronicHandler.electronics.add(e);
				}
				else {
					try {
						cla = Class.forName("processors."+processor.toString());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = (short) tile.ordinal();
					game.StoredTiles[chunk][tileX][Math.abs(tileY)][1] = game.files.getTileFileID((short) tile.ordinal());
					int chunkX = game.ChunkX-1+(chunk%3);
					int chunkY = (game.ChunkY+1)-(chunk/3);
					Processors p = null;

					try {
						p = (Processors) cla.getConstructors()[0].newInstance(game, game.StoredTiles[chunk][tileX][Math.abs(tileY)][1], chunkX, chunkY, tileX, Math.abs(tileY));
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | SecurityException e) {
						e.printStackTrace();
					}
					ProcessHandler.processors.add(p);
				}
			}
		}
		//Takes the item from the hotbar slot if it was placed
		game.addMessage("Removed 1X "+ItemIDs.values()[invValue].getName());
		game.Inventory[x][y][1]--;
	}

	//	private void placeContainer(int chunk, int tileX, int tileY, short tileID, byte neededValues, byte inventorySlots) { //Remember that the neededValues byte does not include the On/Off boolean of the container
	//		game.StoredTiles[chunk][tileX][Math.abs(tileY)][0] = tileID;
	//		game.files.createTileInfo((short) tileID, chunk, tileX, Math.abs(tileY), neededValues, inventorySlots);
	//	}

}
