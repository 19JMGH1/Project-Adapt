package core.playerInput;

import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import core.Files;
import core.InGameHud;
import core.Main_Game;
import core.Main_Game.State;
import entities.Creature;
import items.CraftingRecipes;
import items.InventoryManagement;
import items.ItemIDs;
import menus.FileMenu;
import menus.MainMenu;
import tiles.interactions.Interactions;

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

	private boolean Unpressed = true;

	public MouseHandler(Main_Game game, MainMenu mainmenu, FileMenu filemenu, Files files, InGameHud ingamehud, Interactions interactions, CraftingRecipes craftingrecipes) {
		this.game = game;
		this.mainmenu = mainmenu;
		this.filemenu = filemenu;
		this.files = files;
		this.ingamehud = ingamehud;
		this.interactions = interactions;
	}

	public void mousePressed(MouseEvent e) {
		if (Unpressed) {
			while (Main_Game.rendering) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
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
						if (!filemenu.FileNaming || files.ReadLine("Files/File 1/Data.txt", 1) == null) {
							game.AdaptState = State.MainMenu;
						}
						filemenu.FileNaming = false;
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
						Creature c = game.handler.creatureDamaged(mx, my); //Get the creature that may have been hit for use later
						if (game.inventoryOpened)
						{
							game.inventorymanagment.leftClickManage(mx, my);
						}
						else if (c != null) {
							double x = (c.xPos+c.width/2)-(game.characterX+game.CharacterWidth/2);
							double y = (game.characterY+game.CharacterHeight/2)-(c.yPos+c.height/2);
							//System.out.println(x+", "+y); //Testing
							c.knockbackVel[0] = (int) Math.ceil((game.TileWidth/10)*(x)/(Math.sqrt((Math.pow(x, 2))+(Math.pow(y, 2)))));
							c.knockbackVel[1] = (int) Math.ceil((game.TileHeight/10)*(y)/(Math.sqrt((Math.pow(x, 2))+(Math.pow(y, 2)))));
							//System.out.println(c.knockbackVel[0]+", "+c.knockbackVel[1]); //Testing
							ItemIDs item = ItemIDs.values()[game.Inventory[game.SelectedHotbar][5][0]];
							if ((c.HP-item.damage) <= 0) {
								for (int i = 0 ; i < c.drops.length; i++) {
									if (game.inventoryhandler.addToInv((short) c.drops[i][0], c.drops[i][1])) {
										game.addDropedItem((byte) c.drops[i][0], c.drops[i][1]);
									}
								}
							}
							c.HP -= item.damage;
							game.harvesttile.itemBroken(game.Inventory[game.SelectedHotbar][5][0], game.SelectedHotbar, 5);
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
						game.inventorymanagment.middleClickManage(mx, my);
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
							game.inventorymanagment.rightClickManage(mx, my);
						}
						else
						{ //Right clicking tiles around the player
							if (mousePosition(mx, my, game.characterX-game.x-game.TileWidth, game.characterY+game.y-game.TileHeight, game.TileWidth, game.TileHeight)) {
								interactions.useCheck((short) 1);
							}
							else if (mousePosition(mx, my, game.characterX-game.x, game.characterY+game.y-game.TileHeight, game.TileWidth, game.TileHeight)) {
								interactions.useCheck((short) 2);
							}
							else if (mousePosition(mx, my, game.characterX-game.x+game.TileWidth, game.characterY+game.y-game.TileHeight, game.TileWidth, game.TileHeight)) {
								interactions.useCheck((short) 3);
							}
							else if (mousePosition(mx, my, game.characterX-game.x-game.TileWidth, game.characterY+game.y, game.TileWidth, game.TileHeight)) {
								interactions.useCheck((short) 4);
							}
							else if (mousePosition(mx, my, game.characterX-game.x, game.characterY+game.y, game.TileWidth, game.TileHeight)) {
								interactions.useCheck((short) 5);
							}
							else if (mousePosition(mx, my, game.characterX-game.x+game.TileWidth, game.characterY+game.y, game.TileWidth, game.TileHeight)) {
								interactions.useCheck((short) 6);
							}
							else if (mousePosition(mx, my, game.characterX-game.x-game.TileWidth, game.characterY+game.y+game.TileHeight, game.TileWidth, game.TileHeight)) {
								interactions.useCheck((short) 7);
							}
							else if (mousePosition(mx, my, game.characterX-game.x, game.characterY+game.y+game.TileHeight, game.TileWidth, game.TileHeight)) {
								interactions.useCheck((short) 8);
							}
							else if (mousePosition(mx, my, game.characterX-game.x+game.TileWidth, game.characterY+game.y+game.TileHeight, game.TileWidth, game.TileHeight)) {
								interactions.useCheck((short) 9);
							}
						}
					}
				}
			}
			Unpressed = false;
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
		if (e.getButton() == 2) { //Middle Click

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
				else {
					if (game.inventoryOpened) {
						if (((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) == InputEvent.BUTTON3_DOWN_MASK)) {
							game.inventorymanagment.checkCrafting(mx, my, true);
							game.inventorymanagment.rightClickManage(mx, my);
						}
					}
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		Unpressed = true;

		//This sets up the variables for right clicking and dragging to place 1 item in multiple slots when the right click is no longer pressed
		if (e.getButton() == 3) {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					InventoryManagement.inventoryRightDrag[i][j] = false;
					InventoryManagement.containerRightDrag[i][j] = false;
				}
				InventoryManagement.inventoryRightDrag[5][i] = false;
			}
		}
	}

	public void mouseDragged(MouseEvent e) {
		//When a button is pressed, this method runs instead of the 
		//mouse moved method, but I still want the mouse moved event to run
		//whenever it's moved even while a button is being pressed.
		mouseMoved(e); //So I added this line of code to mouseDragged
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

	private boolean mousePosition(int mx, int my, int xcheck, int ycheck, int width, int height) {
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
