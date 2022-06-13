package items;

import core.Main_Game;
import processors.electronics.Electronic;

public class InventoryManagementOLD {
	
	private Main_Game game;
	private boolean justCrafted = false;

	public InventoryManagementOLD(Main_Game game) {
		this.game = game;
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
	
	private boolean showingContainer() {
		if ((game.curentlyOpenedContainer != null) && (!game.configuringIO)) {
			return true;
		}
		return false;
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
	
	private void checkCrafting(int mx, int my, boolean leftClicked)
	{
		for (int j = 0; j < 5; j++)
		{
			for (int i = 0; i < 5; i++)
			{
				if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+(i-6)*game.inventoryhandler.iconSize, game.inventoryhandler.topLeftCornerY+j*game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
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
								game.justCrafted = false;
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
								game.justCrafted = false;
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
								game.justCrafted = false;
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
								game.justCrafted = false;
							}
						}
						else if (game.craftingLevel == 4 && game.tempCraftingLevel == 0)
						{
							setCraftingSlots(i, j, leftClicked);
						}
						else
						{
							game.justCrafted = false;
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
	
	private void setCraftingSlots(int i, int j, boolean leftClicked)
	{
		int x = (game.HighlightedItem-1) % 5;
		int y = (game.HighlightedItem-1) / 5;
		game.craftingBoxes[i][j] = game.Inventory[x][y][0];
		if (leftClicked)
		{
			game.HighlightedItem = 0;
		}
		game.justCrafted = true;
	}
	
	public void rightClickManage(int mx, int my) {
		if (game.HighlightedItem != 0)
		{
			for(int j = 0; j < 5; j++)
			{
				for (int i = 0; i < 5; i++)
				{
					if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+i*game.inventoryhandler.iconSize, game.inventoryhandler.topLeftCornerY+j*game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
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
				if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+k*game.inventoryhandler.iconSize, Main_Game.HEIGHT-game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
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
					if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+i*game.inventoryhandler.iconSize, game.inventoryhandler.topLeftCornerY+j*game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
					{
						game.interactions.useItem(game.Inventory[i][j][0], i, j);
					}
				}
			}
			for (int k = 0; k < 5; k++)
			{
				if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+k*game.inventoryhandler.iconSize, Main_Game.HEIGHT-game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
				{
					game.interactions.useItem(game.Inventory[k][5][0], k, 5);
				}
			}
		}
		game.justCrafted = false;
		checkCrafting(mx, my, false);
	}
	
	public void middleClickManage(int mx, int my) {
		if (game.HighlightedItem != 0)
		{
			for(int j = 0; j < 5; j++)
			{
				for (int i = 0; i < 5; i++)
				{
					if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+i*game.inventoryhandler.iconSize, game.inventoryhandler.topLeftCornerY+j*game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
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
	
	public void leftClickManage(int mx, int my) {
		if (((game.HighlightedItem != 0) || (game.highlightedContainerItem != 0)) && (!justCrafted))
		{
			//Click the trash to delete times from the inventory
			if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX, game.inventoryhandler.topLeftCornerY-game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
			{
				int x1 = (game.HighlightedItem-1) % 5;
				int y1 = (game.HighlightedItem-1) / 5;
				game.interactions.itemBroken(game.Inventory[x1][y1][0], x1, y1);
				game.Inventory[x1][y1][1] = 0;
				game.HighlightedItem = 0;
				game.highlightedContainerItem = 0;
			}
			//Highlighting and moving items in the inventory
			for(int j = 0; j < 5; j++)
			{
				for (int i = 0; i < 5; i++)
				{
					if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+i*game.inventoryhandler.iconSize, game.inventoryhandler.topLeftCornerY+j*game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
					{
						if (game.HighlightedItem != 0) {
							int x = (game.HighlightedItem-1) % 5;
							int y = (game.HighlightedItem-1) / 5;
							if (!(x == i&& y == j))
							{
								if ((game.Inventory[x][y][0] == game.Inventory[i][j][0]) && !game.inventoryhandler.unstackable(game.Inventory[i][j][0]))
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
							if ((game.curentlyOpenedContainer.getContainerSlots()[x][y][0] == game.Inventory[i][j][0]) && !game.inventoryhandler.unstackable(game.Inventory[i][j][0]))
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
				if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+k*game.inventoryhandler.iconSize, Main_Game.HEIGHT-game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
				{
					if (game.HighlightedItem != 0) {
						int x = (game.HighlightedItem-1) % 5;
						int y = (game.HighlightedItem-1) / 5;
						if (!(x == k && y == 5))
						{
							if ((game.Inventory[x][y][0] == game.Inventory[k][5][0]) && !game.inventoryhandler.unstackable(game.Inventory[k][5][0]))
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
						if ((game.curentlyOpenedContainer.getContainerSlots()[x][y][0] == game.Inventory[k][5][0]) && !game.inventoryhandler.unstackable(game.Inventory[k][5][0]))
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
						if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+(i+6)*game.inventoryhandler.iconSize, game.inventoryhandler.topLeftCornerY+j*game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
						{
							if (showingContainer()) {
								if (game.curentlyOpenedContainer.getValidSlots()[j][i]) {
									int x = (game.HighlightedItem-1) % 5;
									int y = (game.HighlightedItem-1) / 5;
									if ((game.Inventory[x][y][0] == game.curentlyOpenedContainer.getContainerSlots()[i][j][0]) && !game.inventoryhandler.unstackable(game.Inventory[i][j][0]))
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
						if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+(i+6)*game.inventoryhandler.iconSize, game.inventoryhandler.topLeftCornerY+j*game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
						{
							if (game.curentlyOpenedContainer.getValidSlots()[j][i]) {
								int x = (game.highlightedContainerItem-1) % 5;
								int y = (game.highlightedContainerItem-1) / 5;
								if (!(x == i&& y == j))
								{
									if ((game.curentlyOpenedContainer.getContainerSlots()[x][y][0] == game.curentlyOpenedContainer.getContainerSlots()[i][j][0]) && !game.inventoryhandler.unstackable(game.curentlyOpenedContainer.getContainerSlots()[i][j][0]))
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
					if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+i*game.inventoryhandler.iconSize, game.inventoryhandler.topLeftCornerY+j*game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
					{
						game.HighlightedItem = (byte) (1*i+5*j+1);
						game.highlightedContainerItem = 0;
					}
					else if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+(i+6)*game.inventoryhandler.iconSize, game.inventoryhandler.topLeftCornerY+j*game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
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
				if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+k*game.inventoryhandler.iconSize, Main_Game.HEIGHT-game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
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
				if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+(8)*game.inventoryhandler.iconSize, game.inventoryhandler.topLeftCornerY+6*game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize)) {
					game.configuringIO = !game.configuringIO;
					game.highlightedContainerItem = 0;
				}
				//Switch the up IO configuration
				else if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+8*game.inventoryhandler.iconSize, game.inventoryhandler.topLeftCornerY+1*game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize)) {
					swapIO(0);
				}
				else if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+8*game.inventoryhandler.iconSize, game.inventoryhandler.topLeftCornerY+3*game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize)) {
					swapIO(1);
				}
				else if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+7*game.inventoryhandler.iconSize, game.inventoryhandler.topLeftCornerY+2*game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize)) {
					swapIO(2);
				}
				else if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+9*game.inventoryhandler.iconSize, game.inventoryhandler.topLeftCornerY+2*game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize)) {
					swapIO(3);
				}
			}
		}
	}
	
}
