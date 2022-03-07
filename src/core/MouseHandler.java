package core;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import containers.electronics.Electronic;
import core.Main_Game.State;
import items.CraftingRecipes;
import menus.FileMenu;
import menus.MainMenu;

public class MouseHandler extends MouseAdapter implements MouseMotionListener, MouseWheelListener{

	public static boolean MouseMovedEnabled = false;

	public static int mouseX = 0;
	public static int mouseY = 0;

	private Main_Game game;
	private MainMenu mainmenu;
	private FileMenu filemenu;
	private Files files;
	private InGameHud ingamehud;
	private Interactions interactions;
	private InventoryHandler inventoryhandler;

	private boolean Unpressed = true;

	private boolean justCrafted = false;

	public MouseHandler(Main_Game game, MainMenu mainmenu, FileMenu filemenu, Files files, InGameHud ingamehud, Interactions interactions, InventoryHandler inventoryhandler, CraftingRecipes craftingrecipes) {
		this.game = game;
		this.mainmenu = mainmenu;
		this.filemenu = filemenu;
		this.files = files;
		this.ingamehud = ingamehud;
		this.interactions = interactions;
		this.inventoryhandler = inventoryhandler;
	}

	public void mousePressed(MouseEvent e) {
		if (Unpressed) {
			int mx = e.getX();
			int my = e.getY();
			if (e.getButton() == 1) //Left Click
			{
				//controls all the buttons for when the player is in the main menu
				if (game.AdaptState == State.MainMenu) {
					//singleplayer button on the title menu
					if (mousePosition (mx, my, mainmenu.singleplayerBoxX, mainmenu.singleplayerBoxY, mainmenu.singleplayerBoxWidth, mainmenu.singleplayerBoxHeight)) {
						mainmenu.singleplayerButton3D = true;
						filemenu.FilesOnScreen = 1;
						filemenu.RedrawFiles();
						game.AdaptState = State.FileMenu;
					}
					else if (mousePosition (mx, my, mainmenu.quitGameBoxX, mainmenu.quitGameBoxY, mainmenu.quitGameBoxWidth, mainmenu.quitGameBoxHeight)) {
						game.close();
					}
				}
				//controls all the buttons for when a player is in the filemenu
				else if (game.AdaptState == State.FileMenu) {
					//cancel button on the file menu
					if (mousePosition (mx, my, filemenu.CancelBoxX, filemenu.CancelCreateBoxY, filemenu.CancelBoxWidth, filemenu.CancelCreateBoxHeight)) {
						filemenu.CancelButton3D = true;
						filemenu.FileNaming = false;
						game.AdaptState = State.MainMenu;
					}

					if (filemenu.FileNaming == false) {

						//buttons that work if the file naming screen is not up:
						//create new world button
						if (mousePosition (mx, my, filemenu.WorldBoxX, filemenu.CancelCreateBoxY, filemenu.WorldBoxWidth, filemenu.CancelCreateBoxHeight)) {
							if (filemenu.FileNaming == false) {
								filemenu.FileNamingText = "";
								filemenu.FileNaming = true;
							}
						}
						//selecting the first file
						else if (mousePosition(mx, my, filemenu.FileBoxFillX, filemenu.BackgroundBoxY+1, filemenu.FileBoxWidths-1, filemenu.FileBoxHeights-2)) {
							filemenu.File1Selected = true;
							filemenu.File2Selected = false;
							filemenu.File3Selected = false;
							filemenu.File4Selected = false;
							filemenu.SelectedFile = 1;
						}
						//selecting the second file
						else if (mousePosition(mx, my, filemenu.FileBoxFillX, filemenu.BackgroundBoxY+(1*filemenu.FileBoxHeightChanges)+1, filemenu.FileBoxWidths-1, filemenu.FileBoxHeights-3)) {
							filemenu.File1Selected = false;
							filemenu.File2Selected = true;
							filemenu.File3Selected = false;
							filemenu.File4Selected = false;
							filemenu.SelectedFile = 2;
						}
						//selecting the third file
						else if (mousePosition(mx, my, filemenu.FileBoxFillX, filemenu.BackgroundBoxY+(2*filemenu.FileBoxHeightChanges), filemenu.FileBoxWidths-1, filemenu.FileBoxHeights-3)) {
							filemenu.File1Selected = false;
							filemenu.File2Selected = false;
							filemenu.File3Selected = true;
							filemenu.File4Selected = false;
							filemenu.SelectedFile = 3;
						}
						//selecting the fourth file
						else if (mousePosition(mx, my, filemenu.FileBoxFillX, filemenu.BackgroundBoxY+(3*filemenu.FileBoxHeightChanges)-1, filemenu.FileBoxWidths-1, filemenu.FileBoxHeights-2)) {
							filemenu.File1Selected = false;
							filemenu.File2Selected = false;
							filemenu.File3Selected = false;
							filemenu.File4Selected = true;
							filemenu.SelectedFile = 4;
						}
						//the erase file button
						else if (mousePosition (mx, my, filemenu.EraseBoxX, filemenu.CancelCreateBoxY, filemenu.EraseBoxWidth, filemenu.CancelCreateBoxHeight)) {
							if (filemenu.SelectedFile != 0) {
								files.DeleteFile(filemenu.FilesOnScreen+filemenu.SelectedFile-1);
								filemenu.RedrawFiles();
							}
						}
						else if (mousePosition (mx, my, filemenu.WorldBoxX, filemenu.PlayBoxY, filemenu.WorldBoxWidth, filemenu.CancelCreateBoxHeight)) {
							game.LoadFile();
						}
					}
					else {
						if (mousePosition (mx, my, filemenu.CreateBoxX, filemenu.CreateBoxY, filemenu.CancelBoxWidth, filemenu.CancelCreateBoxHeight)) {
							if (filemenu.FileNaming) {
								files.CreateFile(filemenu.FileNamingText);
								filemenu.FileNaming = false;
								filemenu.ScrollFiles();
							}
						}
						else if (mousePosition(mx, my, filemenu.FileNamingBoxX, filemenu.FileNamingBoxY, filemenu.FileNamingBoxWidth, filemenu.FileNamingBoxHeight)) {
							filemenu.selectedTextField = false;
						}
						else if (mousePosition(mx, my, filemenu.FileNamingBoxX, 7*Main_Game.HEIGHT/12+Main_Game.HEIGHT/100, filemenu.FileNamingBoxWidth, filemenu.FileNamingBoxHeight)) {
							filemenu.selectedTextField = true;
						}
					}
				}
				//controls all mouse buttons for when the player is in a world
				//The first if statement checks if the game is paused, and if it is, controls the buttons in the pause menu
				else if (game.AdaptState == State.InWorld) {
					if (game.Paused) {
						if (mousePosition(mx, my, ingamehud.ButtonX, ingamehud.ButtonY, ingamehud.ButtonWidths, ingamehud.ButtonHeights)) {
							game.Paused = false;
						}
						else if (mousePosition(mx, my, ingamehud.ButtonX, ingamehud.ButtonY+ingamehud.ButtonHeights+ingamehud.ButtonSeparation, ingamehud.ButtonWidths, ingamehud.ButtonHeights)) {
							game.SaveFile();
							game.Paused = false;
						}
						else if (mousePosition(mx, my, ingamehud.ButtonX, ingamehud.ButtonY+2*(ingamehud.ButtonHeights+ingamehud.ButtonSeparation), ingamehud.ButtonWidths, ingamehud.ButtonHeights)) {
							game.CloseFile();
							game.Paused = false;
							game.AdaptState = State.FileMenu;
						}
					}
					else
					{
						if (game.inventoryOpened)
						{
							if (((game.HighlightedItem != 0) || (game.highlightedContainerItem != 0)) && (!justCrafted))
							{
								//Click the trash to delete times from the inventory
								if (mousePosition(mx, my, inventoryhandler.topLeftCornerX, inventoryhandler.topLeftCornerY-inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize))
								{
									int x1 = (game.HighlightedItem-1) % 5;
									int y1 = (game.HighlightedItem-1) / 5;
									interactions.itemBroken(game.Inventory[x1][y1][0], x1, y1);
									game.Inventory[x1][y1][1] = 0;
									game.HighlightedItem = 0;
									game.highlightedContainerItem = 0;
								}
								//Highlighting and moving items in the inventory
								for(int j = 0; j < 5; j++)
								{
									for (int i = 0; i < 5; i++)
									{
										if (mousePosition(mx, my, inventoryhandler.topLeftCornerX+i*inventoryhandler.iconSize, inventoryhandler.topLeftCornerY+j*inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize))
										{
											if (game.HighlightedItem != 0) {
												int x = (game.HighlightedItem-1) % 5;
												int y = (game.HighlightedItem-1) / 5;
												if (!(x == i&& y == j))
												{
													if ((game.Inventory[x][y][0] == game.Inventory[i][j][0]) && !inventoryhandler.unstackable(game.Inventory[i][j][0]))
													{
														short addedSlots = (short) (game.Inventory[x][y][1]+game.Inventory[i][j][1]);
														if (addedSlots > Main_Game.maxStackSize)
														{
															game.Inventory[i][j][1] = Main_Game.maxStackSize;
															game.Inventory[x][y][1] = (short) (addedSlots-Main_Game.maxStackSize);
														}
														else
														{
															game.Inventory[i][j][1] = addedSlots;
															game.Inventory[x][y][0] = 0;
															game.Inventory[x][y][1] = 0;
														}
													}
													else
													{
														short tempSlot[] = game.Inventory[i][j];
														game.Inventory[i][j] = game.Inventory[x][y];
														game.Inventory[x][y] = tempSlot;
													}
												}
												game.HighlightedItem = 0;
												game.highlightedContainerItem = 0;
											}
											else if (game.highlightedContainerItem != 0) { //Moving items from container to the inventory
												int x = (game.highlightedContainerItem-1) % 5;
												int y = (game.highlightedContainerItem-1) / 5;
												if ((game.curentlyOpenedContainer.getContainerSlots()[x][y][0] == game.Inventory[i][j][0]) && !inventoryhandler.unstackable(game.Inventory[i][j][0]))
												{
													short addedSlots = (short) (game.curentlyOpenedContainer.getContainerSlots()[x][y][1]+game.Inventory[i][j][1]);
													if (addedSlots > Main_Game.maxStackSize)
													{
														game.Inventory[i][j][1] = Main_Game.maxStackSize;
														game.curentlyOpenedContainer.getContainerSlots()[x][y][1] = (short) (addedSlots-Main_Game.maxStackSize);
														//files.saveTileInfo();
													}
													else
													{
														game.Inventory[i][j][1] = addedSlots;
														game.curentlyOpenedContainer.getContainerSlots()[x][y][0] = 0;
														game.curentlyOpenedContainer.getContainerSlots()[x][y][1] = 0;
														//files.saveTileInfo();
													}
												}
												else
												{
													short tempSlot[] = game.Inventory[i][j];
													game.Inventory[i][j] = game.curentlyOpenedContainer.getContainerSlots()[x][y];
													game.curentlyOpenedContainer.getContainerSlots()[x][y] = tempSlot;
													//files.saveTileInfo();
												}
												game.HighlightedItem = 0;
												game.highlightedContainerItem = 0;
											}
										}
									}
								}
								//Highlighting and moving items in the hotbar
								for (int k = 0; k < 5; k++)
								{
									if (mousePosition(mx, my, inventoryhandler.topLeftCornerX+k*inventoryhandler.iconSize, Main_Game.HEIGHT-inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize))
									{
										if (game.HighlightedItem != 0) {
											int x = (game.HighlightedItem-1) % 5;
											int y = (game.HighlightedItem-1) / 5;
											if (!(x == k && y == 5))
											{
												if ((game.Inventory[x][y][0] == game.Inventory[k][5][0]) && !inventoryhandler.unstackable(game.Inventory[k][5][0]))
												{
													short addedSlots = (short) (game.Inventory[x][y][1]+game.Inventory[k][5][1]);
													if (addedSlots > Main_Game.maxStackSize)
													{
														game.Inventory[k][5][1] = Main_Game.maxStackSize;
														game.Inventory[x][y][1] = (short) (addedSlots-Main_Game.maxStackSize);
													}
													else
													{
														game.Inventory[k][5][1] = addedSlots;
														game.Inventory[x][y][0] = 0;
														game.Inventory[x][y][1] = 0;
													}
												}
												else
												{
													short tempSlot[] = game.Inventory[k][5];
													game.Inventory[k][5] = game.Inventory[x][y];
													game.Inventory[x][y] = tempSlot;
												}
											}
											game.HighlightedItem = 0;
											game.highlightedContainerItem = 0;
										}
										else if (game.highlightedContainerItem != 0) { //Moving items from container to the inventory
											int x = (game.highlightedContainerItem-1) % 5;
											int y = (game.highlightedContainerItem-1) / 5;
											if ((game.curentlyOpenedContainer.getContainerSlots()[x][y][0] == game.Inventory[k][5][0]) && !inventoryhandler.unstackable(game.Inventory[k][5][0]))
											{
												short addedSlots = (short) (game.curentlyOpenedContainer.getContainerSlots()[x][y][1]+game.Inventory[k][5][1]);
												if (addedSlots > Main_Game.maxStackSize)
												{
													game.Inventory[k][5][1] = Main_Game.maxStackSize;
													game.curentlyOpenedContainer.getContainerSlots()[x][y][1] = (short) (addedSlots-Main_Game.maxStackSize);
													//files.saveTileInfo();
												}
												else
												{
													game.Inventory[k][5][1] = addedSlots;
													game.curentlyOpenedContainer.getContainerSlots()[x][y][0] = 0;
													game.curentlyOpenedContainer.getContainerSlots()[x][y][1] = 0;
													//files.saveTileInfo();
												}
											}
											else
											{
												short tempSlot[] = game.Inventory[k][5];
												game.Inventory[k][5] = game.curentlyOpenedContainer.getContainerSlots()[x][y];
												game.curentlyOpenedContainer.getContainerSlots()[x][y] = tempSlot;
												//files.saveTileInfo();
											}
											game.HighlightedItem = 0;
											game.highlightedContainerItem = 0;
										}
									}
								}

								//Moving items into containers
								if (game.HighlightedItem != 0)
								{
									for(int j = 0; j < 5; j++)
									{
										for (int i = 0; i < 5; i++)
										{
											if (mousePosition(mx, my, inventoryhandler.topLeftCornerX+(i+6)*inventoryhandler.iconSize, inventoryhandler.topLeftCornerY+j*inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize))
											{
												if (showingContainer()) {
													if (game.curentlyOpenedContainer.getValidSlots()[j][i]) {
														int x = (game.HighlightedItem-1) % 5;
														int y = (game.HighlightedItem-1) / 5;
														if ((game.Inventory[x][y][0] == game.curentlyOpenedContainer.getContainerSlots()[i][j][0]) && !inventoryhandler.unstackable(game.Inventory[i][j][0]))
														{
															short addedSlots = (short) (game.Inventory[x][y][1]+game.curentlyOpenedContainer.getContainerSlots()[i][j][1]);
															if (addedSlots > Main_Game.maxStackSize)
															{
																game.curentlyOpenedContainer.getContainerSlots()[i][j][1] = Main_Game.maxStackSize;
																game.Inventory[x][y][1] = (short) (addedSlots-Main_Game.maxStackSize);
																//files.saveTileInfo();
															}
															else
															{
																game.curentlyOpenedContainer.getContainerSlots()[i][j][1] = addedSlots;
																game.Inventory[x][y][0] = 0;
																game.Inventory[x][y][1] = 0;
																//files.saveTileInfo();
															}
														}
														else
														{
															short tempSlot[] = game.curentlyOpenedContainer.getContainerSlots()[i][j];
															game.curentlyOpenedContainer.getContainerSlots()[i][j] = game.Inventory[x][y];
															game.Inventory[x][y] = tempSlot;
															//files.saveTileInfo();
														}
														game.HighlightedItem = 0;
														game.highlightedContainerItem = 0;
													}
												}
											}
										}
									}
								}
								//Moving items from container to the inventory
								else if (game.highlightedContainerItem != 0) {
									for (int j = 0; j < 5; j++) {
										for  (int i = 0; i < 5; i++) {
											if (mousePosition(mx, my, inventoryhandler.topLeftCornerX+(i+6)*inventoryhandler.iconSize, inventoryhandler.topLeftCornerY+j*inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize))
											{
												if (game.curentlyOpenedContainer.getValidSlots()[j][i]) {
													int x = (game.highlightedContainerItem-1) % 5;
													int y = (game.highlightedContainerItem-1) / 5;
													if (!(x == i&& y == j))
													{
														if ((game.curentlyOpenedContainer.getContainerSlots()[x][y][0] == game.curentlyOpenedContainer.getContainerSlots()[i][j][0]) && !inventoryhandler.unstackable(game.curentlyOpenedContainer.getContainerSlots()[i][j][0]))
														{
															short addedSlots = (short) (game.curentlyOpenedContainer.getContainerSlots()[x][y][1]+game.curentlyOpenedContainer.getContainerSlots()[i][j][1]);
															if (addedSlots > Main_Game.maxStackSize)
															{
																game.curentlyOpenedContainer.getContainerSlots()[i][j][1] = Main_Game.maxStackSize;
																game.curentlyOpenedContainer.getContainerSlots()[x][y][1] = (short) (addedSlots-Main_Game.maxStackSize);
																//files.saveTileInfo();
															}
															else
															{
																game.curentlyOpenedContainer.getContainerSlots()[i][j][1] = addedSlots;
																game.curentlyOpenedContainer.getContainerSlots()[x][y][0] = 0;
																game.curentlyOpenedContainer.getContainerSlots()[x][y][1] = 0;
																//files.saveTileInfo();
															}
														}
														else
														{
															short tempSlot[] = game.curentlyOpenedContainer.getContainerSlots()[i][j];
															game.curentlyOpenedContainer.getContainerSlots()[i][j] = game.curentlyOpenedContainer.getContainerSlots()[x][y];
															game.curentlyOpenedContainer.getContainerSlots()[x][y] = tempSlot;
															//files.saveTileInfo();
														}
														game.HighlightedItem = 0;
														game.highlightedContainerItem = 0;
													}
												}
											}
										}
									}
								}
							}
							else
							{ //Highlighting items in the inventory, hotbar, or containers
								for(int j = 0; j < 5; j++)
								{
									for (int i = 0; i < 5; i++)
									{
										if (mousePosition(mx, my, inventoryhandler.topLeftCornerX+i*inventoryhandler.iconSize, inventoryhandler.topLeftCornerY+j*inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize))
										{
											game.HighlightedItem = (byte) (1*i+5*j+1);
											game.highlightedContainerItem = 0;
										}
										else if (mousePosition(mx, my, inventoryhandler.topLeftCornerX+(i+6)*inventoryhandler.iconSize, inventoryhandler.topLeftCornerY+j*inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize))
										{
											if (showingContainer()) {
												if (game.curentlyOpenedContainer.getValidSlots()[j][i]) {
													game.highlightedContainerItem = (byte) (1*i+5*j+1);
													game.HighlightedItem = 0;
												}
											}
										}
									}
								}
								for (int k = 0; k < 5; k++)
								{
									if (mousePosition(mx, my, inventoryhandler.topLeftCornerX+k*inventoryhandler.iconSize, Main_Game.HEIGHT-inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize))
									{
										game.HighlightedItem = (byte) (1*k+25+1);
										game.highlightedContainerItem = 0;
									}
								}
							}
							//Moving items to the crafting window
							justCrafted = false;
							checkCrafting(mx, my, true);
							if (game.curentlyOpenedContainer != null) {
								if (game.curentlyOpenedContainer.getContainerID().electronic) {
									//Show/hide the IO screen
									if (mousePosition(mx, my, inventoryhandler.topLeftCornerX+(8)*inventoryhandler.iconSize, inventoryhandler.topLeftCornerY+6*inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize)) {
										game.configuringIO = !game.configuringIO;
										game.highlightedContainerItem = 0;
									}
									//Switch the up IO configuration
									else if (mousePosition(mx, my, inventoryhandler.topLeftCornerX+8*inventoryhandler.iconSize, inventoryhandler.topLeftCornerY+1*inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize)) {
										swapIO(0);
									}
									else if (mousePosition(mx, my, inventoryhandler.topLeftCornerX+8*inventoryhandler.iconSize, inventoryhandler.topLeftCornerY+3*inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize)) {
										swapIO(1);
									}
									else if (mousePosition(mx, my, inventoryhandler.topLeftCornerX+7*inventoryhandler.iconSize, inventoryhandler.topLeftCornerY+2*inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize)) {
										swapIO(2);
									}
									else if (mousePosition(mx, my, inventoryhandler.topLeftCornerX+9*inventoryhandler.iconSize, inventoryhandler.topLeftCornerY+2*inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize)) {
										swapIO(3);
									}
								}
							}
						}
						else
						{
							if (mousePosition(mx, my, game.characterX-game.x-game.TileWidth, game.characterY+game.y-game.TileHeight, game.TileWidth, game.TileHeight)) {
								interactions.destroyCheck(1);
							}
							else if (mousePosition(mx, my, game.characterX-game.x, game.characterY+game.y-game.TileHeight, game.TileWidth, game.TileHeight)) {
								interactions.destroyCheck(2);
							}
							else if (mousePosition(mx, my, game.characterX-game.x+game.TileWidth, game.characterY+game.y-game.TileHeight, game.TileWidth, game.TileHeight)) {
								interactions.destroyCheck(3);
							}
							else if (mousePosition(mx, my, game.characterX-game.x-game.TileWidth, game.characterY+game.y, game.TileWidth, game.TileHeight)) {
								interactions.destroyCheck(4);
							}
							else if (mousePosition(mx, my, game.characterX-game.x, game.characterY+game.y, game.TileWidth, game.TileHeight)) {
								interactions.destroyCheck(5);
							}
							else if (mousePosition(mx, my, game.characterX-game.x+game.TileWidth, game.characterY+game.y, game.TileWidth, game.TileHeight)) {
								interactions.destroyCheck(6);
							}
							else if (mousePosition(mx, my, game.characterX-game.x-game.TileWidth, game.characterY+game.y+game.TileHeight, game.TileWidth, game.TileHeight)) {
								interactions.destroyCheck(7);
							}
							else if (mousePosition(mx, my, game.characterX-game.x, game.characterY+game.y+game.TileHeight, game.TileWidth, game.TileHeight)) {
								interactions.destroyCheck(8);
							}
							else if (mousePosition(mx, my, game.characterX-game.x+game.TileWidth, game.characterY+game.y+game.TileHeight, game.TileWidth, game.TileHeight)) {
								interactions.destroyCheck(9);
							}
						}
					}
				}
			}
			else if (e.getButton() == 2) //Middle Click
			{
				if (!game.Paused) {
					if (game.inventoryOpened) //Splitting stacks of items in half in the inventory
					{
						if (game.HighlightedItem != 0)
						{
							for(int j = 0; j < 5; j++)
							{
								for (int i = 0; i < 5; i++)
								{
									if (mousePosition(mx, my, inventoryhandler.topLeftCornerX+i*inventoryhandler.iconSize, inventoryhandler.topLeftCornerY+j*inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize))
									{
										int x = (game.HighlightedItem-1) % 5;
										int y = (game.HighlightedItem-1) / 5;
										if (game.Inventory[x][y][1] > 0 && game.Inventory[i][j][0] == 0)
										{
											int half = game.Inventory[x][y][1]/2;
											game.Inventory[i][j][1] = (short) half;
											game.Inventory[i][j][0] = game.Inventory[x][y][0];
											game.Inventory[x][y][1] -= half;
											game.HighlightedItem = 0;
										}
									}
								}
							}
						}
					}
				}
			}

			else if (e.getButton() == 3) //Right Click
			{
				if (!game.Paused) {
					if (game.AdaptState == State.InWorld)
					{
						if (game.inventoryOpened)
						{
							if (game.HighlightedItem != 0)
							{
								for(int j = 0; j < 5; j++)
								{
									for (int i = 0; i < 5; i++)
									{
										if (mousePosition(mx, my, inventoryhandler.topLeftCornerX+i*inventoryhandler.iconSize, inventoryhandler.topLeftCornerY+j*inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize))
										{
											if (game.Inventory[i][j][0] == 0)
											{
												int x = (game.HighlightedItem-1) % 5;
												int y = (game.HighlightedItem-1) / 5;
												game.Inventory[i][j][1]++;
												game.Inventory[x][y][1]--;
												game.Inventory[i][j][0] = game.Inventory[x][y][0];
											}
										}
									}
								}
								for (int k = 0; k < 5; k++)
								{
									if (mousePosition(mx, my, inventoryhandler.topLeftCornerX+k*inventoryhandler.iconSize, Main_Game.HEIGHT-inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize))
									{
										if (game.Inventory[k][5][0] == 0)
										{
											int x = (game.HighlightedItem-1) % 5;
											int y = (game.HighlightedItem-1) / 5;
											game.Inventory[k][5][1]++;
											game.Inventory[k][5][0] = game.Inventory[x][y][0];
											game.Inventory[x][y][1]--;
											game.HighlightedItem = 0;
										}
									}
								}
							}
							else
							{
								//Using items when you right click them
								for(int j = 0; j < 5; j++)
								{
									for (int i = 0; i < 5; i++)
									{
										if (mousePosition(mx, my, inventoryhandler.topLeftCornerX+i*inventoryhandler.iconSize, inventoryhandler.topLeftCornerY+j*inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize))
										{
											interactions.useItem(game.Inventory[i][j][0], i, j);
										}
									}
								}
								for (int k = 0; k < 5; k++)
								{
									if (mousePosition(mx, my, inventoryhandler.topLeftCornerX+k*inventoryhandler.iconSize, Main_Game.HEIGHT-inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize))
									{
										interactions.useItem(game.Inventory[k][5][0], k, 5);
									}
								}
							}
							justCrafted = false;
							checkCrafting(mx, my, false);
						}
						else
						{ //Right clicking tiles around the player
							if (mousePosition(mx, my, game.characterX-game.x-game.TileWidth, game.characterY+game.y-game.TileHeight, game.TileWidth, game.TileHeight)) {
								interactions.useCheck(1);
							}
							else if (mousePosition(mx, my, game.characterX-game.x, game.characterY+game.y-game.TileHeight, game.TileWidth, game.TileHeight)) {
								interactions.useCheck(2);
							}
							else if (mousePosition(mx, my, game.characterX-game.x+game.TileWidth, game.characterY+game.y-game.TileHeight, game.TileWidth, game.TileHeight)) {
								interactions.useCheck(3);
							}
							else if (mousePosition(mx, my, game.characterX-game.x-game.TileWidth, game.characterY+game.y, game.TileWidth, game.TileHeight)) {
								interactions.useCheck(4);
							}
							else if (mousePosition(mx, my, game.characterX-game.x, game.characterY+game.y, game.TileWidth, game.TileHeight)) {
								interactions.useCheck(5);
							}
							else if (mousePosition(mx, my, game.characterX-game.x+game.TileWidth, game.characterY+game.y, game.TileWidth, game.TileHeight)) {
								interactions.useCheck(6);
							}
							else if (mousePosition(mx, my, game.characterX-game.x-game.TileWidth, game.characterY+game.y+game.TileHeight, game.TileWidth, game.TileHeight)) {
								interactions.useCheck(7);
							}
							else if (mousePosition(mx, my, game.characterX-game.x, game.characterY+game.y+game.TileHeight, game.TileWidth, game.TileHeight)) {
								interactions.useCheck(8);
							}
							else if (mousePosition(mx, my, game.characterX-game.x+game.TileWidth, game.characterY+game.y+game.TileHeight, game.TileWidth, game.TileHeight)) {
								interactions.useCheck(9);
							}
						}
					}
				}
			}
			Unpressed = false;
		}
	}
	
	private void swapIO(int i) {
		Electronic e = (Electronic) game.curentlyOpenedContainer;
		if (e.getInputSides()[i] && e.getOutputSides()[i]) {
			e.getInputSides()[i] = true;
			e.getOutputSides()[i] = false;
		}
		else if (e.getInputSides()[i] == true) {
			e.getInputSides()[i] = false;
			e.getOutputSides()[i] = true;
		}
		else if (e.getOutputSides()[i]) {
			e.getInputSides()[i] = true;
			e.getOutputSides()[i] = true;
		}
	}

	private void setCraftingSlots(int i, int j, boolean leftClicked)
	{
		int x = (game.HighlightedItem-1) % 5;
		int y = (game.HighlightedItem-1) / 5;
		game.craftingBoxes[i][j] = game.Inventory[x][y][0];
		if (leftClicked)
		{
			game.HighlightedItem = 0;
		}
		justCrafted = true;
	}

	private void checkCrafting(int mx, int my, boolean leftClicked)
	{
		for (int j = 0; j < 5; j++)
		{
			for (int i = 0; i < 5; i++)
			{
				if (mousePosition(mx, my, inventoryhandler.topLeftCornerX+(i-6)*inventoryhandler.iconSize, inventoryhandler.topLeftCornerY+j*inventoryhandler.iconSize, inventoryhandler.iconSize, inventoryhandler.iconSize))
				{
					if (game.HighlightedItem != 0)
					{
						if (game.craftingLevel == 0 && game.tempCraftingLevel == 0)
						{
							if ((i == 2 && j == 3)||(i == 3 && j == 3))
							{
								setCraftingSlots(i, j, leftClicked);
							}
							else
							{
								justCrafted = false;
							}
						}
						else if (game.craftingLevel == 1 && game.tempCraftingLevel == 0)
						{
							if ((i == 2 && j == 2)||(i == 3 && j == 2)||(i == 2 && j == 3)||(i == 3 && j == 3))
							{
								setCraftingSlots(i, j, leftClicked);
							}
							else
							{
								justCrafted = false;
							}
						}
						else if ((game.craftingLevel == 2 && game.tempCraftingLevel == 0) || (game.tempCraftingLevel == 2 && game.craftingLevel <= 2))
						{
							if (i >= 1 && i <= 3 && j >= 1 && j <= 3)
							{
								setCraftingSlots(i, j, leftClicked);
							}
							else
							{
								justCrafted = false;
							}
						}
						else if (game.craftingLevel == 3 || (game.tempCraftingLevel == 3 && game.craftingLevel <= 3))
						{
							if (i >= 1 && i <= 4 && j >= 1 && j <= 4)
							{
								setCraftingSlots(i, j, leftClicked);
							}
							else
							{
								justCrafted = false;
							}
						}
						else if (game.craftingLevel == 4 && game.tempCraftingLevel == 0)
						{
							setCraftingSlots(i, j, leftClicked);
						}
						else
						{
							justCrafted = false;
						}
					}
					else
					{
						game.craftingBoxes[i][j] = 0;
					}
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		//System.out.println(true);
		if (e.getButton() == 1) //Left Click
		{
			if (game.AdaptState == State.FileMenu) {
				if (e.getClickCount() == 2) {
					if (filemenu.SelectedFile != 0) {
						if (mousePosition(mx, my, filemenu.FileBoxFillX, filemenu.BackgroundBoxY+1, filemenu.FileBoxWidths-1, filemenu.FileBoxHeights-2) || mousePosition(mx, my, filemenu.FileBoxFillX, filemenu.BackgroundBoxY+(1*filemenu.FileBoxHeightChanges)+1, filemenu.FileBoxWidths-1, filemenu.FileBoxHeights-3) || mousePosition(mx, my, filemenu.FileBoxFillX, filemenu.BackgroundBoxY+(2*filemenu.FileBoxHeightChanges), filemenu.FileBoxWidths-1, filemenu.FileBoxHeights-3) || mousePosition(mx, my, filemenu.FileBoxFillX, filemenu.BackgroundBoxY+(3*filemenu.FileBoxHeightChanges)-1, filemenu.FileBoxWidths-1, filemenu.FileBoxHeights-2)) {
							game.LoadFile();
						}
					}
				}
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
		if (MouseMovedEnabled) {
			int mx = e.getX();
			int my = e.getY();
			mouseX = mx;
			mouseY = my;
			if (game.AdaptState == State.MainMenu) {
				if (mousePosition (mx, my, mainmenu.singleplayerBoxX, mainmenu.singleplayerBoxY, mainmenu.singleplayerBoxWidth, mainmenu.singleplayerBoxHeight)) {
					mainmenu.singleplayerButton3D = false;
				}
				else if (mousePosition (mx, my, mainmenu.quitGameBoxX, mainmenu.quitGameBoxY, mainmenu.quitGameBoxWidth, mainmenu.quitGameBoxHeight)) {
					mainmenu.quitGameButton3D = false;
				}
				else {
					mainmenu.singleplayerButton3D = true;
					mainmenu.quitGameButton3D = true;
				}
			}
			else if (game.AdaptState == State.FileMenu) {
				if (mousePosition (mx, my, filemenu.CancelBoxX, filemenu.CancelCreateBoxY, filemenu.CancelBoxWidth, filemenu.CancelCreateBoxHeight)) {
					filemenu.NoMore3D();
					filemenu.CancelButton3D = false;
				}
				else if (mousePosition (mx, my, filemenu.WorldBoxX, filemenu.CancelCreateBoxY, filemenu.WorldBoxWidth, filemenu.CancelCreateBoxHeight)) {
					filemenu.NoMore3D();
					filemenu.NewWorldButton3D = false;
				}
				else if (mousePosition (mx, my, filemenu.CreateBoxX, filemenu.CreateBoxY, filemenu.CancelBoxWidth, filemenu.CancelCreateBoxHeight)) {
					filemenu.NoMore3D();
					filemenu.CreateButton3D = false;
				}
				else if (mousePosition (mx, my, filemenu.EraseBoxX, filemenu.CancelCreateBoxY, filemenu.EraseBoxWidth, filemenu.CancelCreateBoxHeight)) {
					filemenu.NoMore3D();
					filemenu.EraseButton3D = false;
				}
				else if (mousePosition (mx, my, filemenu.WorldBoxX, filemenu.PlayBoxY, filemenu.WorldBoxWidth, filemenu.CancelCreateBoxHeight)) {
					filemenu.NoMore3D();
					filemenu.PlayButton3D = false;
				}
				else {
					filemenu.NoMore3D();
				}
			}
			else if (game.AdaptState == State.InWorld) {
				//Controls buttons on the pause menu being 3D
				if (game.Paused) {
					if (mousePosition(mx, my, ingamehud.ButtonX, ingamehud.ButtonY, ingamehud.ButtonWidths, ingamehud.ButtonHeights)){
						ingamehud.ResumeButton3D = false;
					}
					else if (mousePosition(mx, my, ingamehud.ButtonX, ingamehud.ButtonY+ingamehud.ButtonHeights+ingamehud.ButtonSeparation, ingamehud.ButtonWidths, ingamehud.ButtonHeights)) {
						ingamehud.SaveButton3D = false;
					}
					else if (mousePosition(mx, my, ingamehud.ButtonX, ingamehud.ButtonY+2*(ingamehud.ButtonHeights+ingamehud.ButtonSeparation), ingamehud.ButtonWidths, ingamehud.ButtonHeights)) {
						ingamehud.QuitButton3D = false;
					}
					else {
						ingamehud.NoMore3D();
					}
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		Unpressed = true;
	}

	public void mouseDragged(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if (game.AdaptState == State.FileMenu) {
			//System.out.println(e.getPreciseWheelRotation());
			if (e.getPreciseWheelRotation() > 0) {
				filemenu.FilesOnScreen += 1;
				filemenu.ScrollFiles();
			}
			else if (e.getPreciseWheelRotation() < 0) {
				filemenu.FilesOnScreen -= 1;
				filemenu.ScrollFiles();
			}
		}
		else if (game.AdaptState == State.InWorld) {
			if (e.getPreciseWheelRotation() > 0) {
				game.SelectedHotbar++;
				if (game.SelectedHotbar > 4)
				{
					game.SelectedHotbar = 0;
				}
			}
			else if (e.getPreciseWheelRotation() < 0) {
				game.SelectedHotbar--;
				if (game.SelectedHotbar < 0)
				{
					game.SelectedHotbar = 4;
				}
			}
		}
	}
	
	private boolean showingContainer() {
		if ((game.curentlyOpenedContainer != null) && (!game.configuringIO)) {
			return true;
		}
		return false;
	}

	public boolean mousePosition(int mx, int my, int xcheck, int ycheck, int width, int height) {
		if (mx >= xcheck && mx <= xcheck+width) {
			if (my >= ycheck && my <= ycheck+height) {
				return true;
			}
			else 
				return false;
		}
		else
			return false;
	}
}
