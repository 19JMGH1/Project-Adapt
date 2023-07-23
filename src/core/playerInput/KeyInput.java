package core.playerInput;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import core.Main_Game;
import core.Main_Game.Dimensions;
import core.Main_Game.State;
import core.lighting.DayTimeCycle;
import items.CraftingManager;
import items.ItemIDs;
import menus.FileMenu;
import tiles.SurfaceTileIDs;

public class KeyInput extends KeyAdapter {

	private Main_Game game;
	private FileMenu filemenu;
	private CraftingManager craftingrecipes;

	public KeyInput(Main_Game game, FileMenu filemenu, CraftingManager craftingrecipes) {
		this.game = game;
		this.filemenu = filemenu;
		this.craftingrecipes = craftingrecipes;
	}

	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		int k = e.getKeyCode();
		if (k == KeyEvent.VK_F11) {
			//Go FullScreen when you press F11
			Main_Game.FULLSCREEN = !Main_Game.FULLSCREEN;
			if (Main_Game.FULLSCREEN) {
				try {
					Thread.sleep(250); //Sleeping stops a weird rendering big related to going in and out of fullscreen too quickly
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				game.window.setFullScreen(true);
			}
			else {
				try {
					Thread.sleep(250); //Sleeping stops a weird rendering big related to going in and out of fullscreen too quickly
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				game.window.setFullScreen(false);
			}
		}

		//Key input for if a player is in the File Menu
		if (game.AdaptState == State.FileMenu) {
			if (filemenu.FileNaming) {
				if (c != KeyEvent.CHAR_UNDEFINED && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_ENTER && filemenu.FileNamingText.length() < 40) {
					if (!filemenu.selectedTextField) {
						filemenu.FileNamingText += c;
					}
					else {
						if (k >= KeyEvent.VK_0 && k <= KeyEvent.VK_9) { //Adding a number onto the seed is contained in this if statement
							if (filemenu.seed == null) {
								filemenu.seed = k%KeyEvent.VK_0;
							}
							else {
								long newInt = Long.valueOf(String.valueOf(filemenu.seed) + String.valueOf(k%48));
								if (newInt <= Integer.MAX_VALUE) {
									filemenu.seed = Integer.valueOf(String.valueOf(filemenu.seed) + String.valueOf(k%48));
								}
							}
						}
						else if (k >= KeyEvent.VK_NUMPAD0 && k <= KeyEvent.VK_NUMPAD9) {
							if (filemenu.seed == null) {
								filemenu.seed = k%KeyEvent.VK_NUMPAD0;
							}
							else {
								long newInt = Long.valueOf(String.valueOf(filemenu.seed) + String.valueOf(k%KeyEvent.VK_NUMPAD0));
								if (newInt <= Integer.MAX_VALUE) {
									filemenu.seed = Integer.valueOf(String.valueOf(filemenu.seed) + String.valueOf(k%KeyEvent.VK_NUMPAD0));
								}
							}
						}
					}
				}
				else if (c == KeyEvent.VK_BACK_SPACE) {
					if (!filemenu.selectedTextField) {
						if (filemenu.FileNamingText.length() > 0)
							filemenu.FileNamingText = filemenu.FileNamingText.substring(0, filemenu.FileNamingText.length()-1);
					}
					else {
						if (filemenu.seed != null) {
							String oldSeed = ""+filemenu.seed;
							String newSeed = "";
							newSeed = oldSeed.substring(0, oldSeed.length()-1);
							if (newSeed.equals("")) {
								filemenu.seed = null;
							}
							else {
								filemenu.seed = Integer.parseInt(newSeed);
							}
						}
					}
				}
				else if (c == KeyEvent.VK_ENTER) {
					game.files.CreateFile(filemenu.FileNamingText);
					filemenu.FileNaming = false;
					filemenu.ScrollFiles();
				}
			}
		}
		
		//Key input for if the player is in a world
		if (game.AdaptState == State.InWorld) {
			if (k == KeyEvent.VK_ESCAPE) {
				if (game.inventoryOpened)
				{
					game.inventoryOpened = false;
					game.configuringIO = false;
					for (int j = 0; j < 5; j++)
					{
						for (int i = 0; i < 5; i++)
						{
							game.craftingBoxes[i][j] = 0;
						}
					}
					if (game.curentlyOpenedContainer != null) {
						synchronized(game.curentlyOpenedContainer) {
							game.curentlyOpenedContainer = null;
						}
					}
				}
				else
				{
					game.Paused = !game.Paused;
				}
			}
			if (!game.Paused) {
				if (k == KeyEvent.VK_SHIFT) {
					if (game.inBoat && game.dimension == Dimensions.surface) {
						if (!game.interactions.checkAbove((short) SurfaceTileIDs.Water.ordinal(), 4, game.TileX, game.TileY) && game.collision.checkY(game.x, game.y, game.TileX, game.TileY, 1, 1, game.TileHeight, false)) {
							game.TileY++;
							game.y = 0;
							game.inBoat = false;
							game.inventoryhandler.addToInv((short) ItemIDs.Boat.ordinal(), 1);
						}
						else if (!game.interactions.checkBelow((short) SurfaceTileIDs.Water.ordinal(), 4, game.TileX, game.TileY) && game.collision.checkY(game.x, game.y, game.TileX, game.TileY, 1, 1, -game.TileHeight, false)) {
							game.TileY--;
							game.y = 0;
							game.inBoat = false;
							game.inventoryhandler.addToInv((short) ItemIDs.Boat.ordinal(), 1);
						}
						else if (!game.interactions.checkLeft((short) SurfaceTileIDs.Water.ordinal(), 4, game.TileX, game.TileY) && game.collision.checkX(game.x, game.y, game.TileX, game.TileY, 1, 1, -game.TileHeight, false)) {
							game.TileX--;
							game.x = 0;
							game.inBoat = false;
							game.inventoryhandler.addToInv((short) ItemIDs.Boat.ordinal(), 1);
						}
						else if (!game.interactions.checkRight((short) SurfaceTileIDs.Water.ordinal(), 4, game.TileX, game.TileY) && game.collision.checkX(game.x, game.y, game.TileX, game.TileY, 1, 1, game.TileHeight, false)) {
							game.TileX++;
							game.x = 0;
							game.inBoat = false;
							game.inventoryhandler.addToInv((short) ItemIDs.Boat.ordinal(), 1);
						}
					}
				}
				else if (k >= KeyEvent.VK_1 && k <=KeyEvent.VK_5) {
					game.SelectedHotbar = (byte) (k%KeyEvent.VK_1);
				}
				//Movement
				else if (k == KeyEvent.VK_W) {
					game.WPressed = true;
				}
				else if (k == KeyEvent.VK_A) {
					game.APressed = true;
				}
				else if (k == KeyEvent.VK_S) {
					game.SPressed = true;
				}
				else if (k == KeyEvent.VK_D) {
					game.DPressed = true;
				}
				else if (k == KeyEvent.VK_E) {

					game.inventoryOpened = !game.inventoryOpened;
					game.configuringIO = false;
					for (int j = 0; j < 5; j++)
					{
						for (int i = 0; i < 5; i++)
						{
							game.craftingBoxes[i][j] = 0;
						}
					}
					if (game.curentlyOpenedContainer != null) {
						synchronized(game.curentlyOpenedContainer) {
							game.curentlyOpenedContainer = null;
						}
					}
				}
				else if (k == KeyEvent.VK_C) { //For now, pressing C tries to craft with the items currently in the crafting window
					craftingrecipes.craft();
				}
				else if (k == KeyEvent.VK_O) { //For now, pressing O turns on any machines
					if (game.curentlyOpenedContainer != null) {
						if (game.curentlyOpenedContainer.getContainerID().onOff && game.inventoryOpened) {
							short onOffSwitch[] = game.curentlyOpenedContainer.getValues();
							if (onOffSwitch[0] == 1) {
								onOffSwitch[0] = 0;
								game.curentlyOpenedContainer.removeLight();
								DayTimeCycle.reloadNeeded = true;
							}
							else {
								onOffSwitch[0] = 1;
							}
							game.curentlyOpenedContainer.setValues(onOffSwitch);
						}
					}
				}
			}
		}
	}
	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		if (game.AdaptState == State.InWorld) {
			if (k == KeyEvent.VK_W) {
				game.WPressed = false;
			}
			else if (k == KeyEvent.VK_A) {
				game.APressed = false;
			}
			else if (k == KeyEvent.VK_S) {
				game.SPressed = false;
			}
			else if (k == KeyEvent.VK_D) {
				game.DPressed = false;
			}
		}
	}
}
