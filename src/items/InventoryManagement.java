package items;

import java.util.Arrays;

import core.Main_Game;
import processors.electronics.Electronic;

public class InventoryManagement {

	private Main_Game game;

	public InventoryManagement(Main_Game game) {
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

	public void checkCrafting(int mx, int my, boolean leftClicked)
	{
		for (int j = 0; j < 5; j++)
		{
			for (int i = 0; i < 5; i++)
			{
				if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+(i-6)*game.inventoryhandler.iconSize, game.inventoryhandler.topLeftCornerY+j*game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
				{
					if (game.grabbedItem[0] != 0)
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

	//TODO this method has been shortened to one line so we don't really need it...
	private void setCraftingSlots(int i, int j, boolean leftClicked)
	{
		game.craftingBoxes[i][j] = game.grabbedItem[0];
	}

	public void rightClickManage(int mx, int my) {
		if (game.grabbedItem[0] != 0)
		{
			//Moving one item out of grabbed items and into the inventory slot that was right clicked
			for(int j = 0; j < 6; j++)
			{
				for (int i = 0; i < 5; i++)
				{
					if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+i*game.inventoryhandler.iconSize, (game.inventoryhandler.topLeftCornerY+j*game.inventoryhandler.iconSize)*(1-(j/6))+(j/6)*(Main_Game.HEIGHT-game.inventoryhandler.iconSize), game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
					{
						if (((game.Inventory[i][j][0] == 0) || (game.Inventory[i][j][0] == game.grabbedItem[0])) && (!game.inventoryhandler.unstackable(game.grabbedItem[0])))
						{
							game.Inventory[i][j][1]++;
							game.grabbedItem[1]--;
							game.Inventory[i][j][0] = game.grabbedItem[0];
						}
					}
				}
			}
			//Moving one item from the grabbed items to a container
			for(int j = 0; j < 5; j++)
			{
				for (int i = 0; i < 5; i++)
				{
					if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+(i+6)*game.inventoryhandler.iconSize, game.inventoryhandler.topLeftCornerY+j*game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
					{
						if (showingContainer()) {
							if (game.curentlyOpenedContainer.getValidSlots()[j][i]) {
								if (((game.grabbedItem[0] == game.curentlyOpenedContainer.getContainerSlots()[i][j][0]) || (game.curentlyOpenedContainer.getContainerSlots()[i][j][0] == 0)) && (!game.inventoryhandler.unstackable(game.grabbedItem[0])))
								{
									game.curentlyOpenedContainer.getContainerSlots()[i][j][1]++;
									game.grabbedItem[1]--;
									game.curentlyOpenedContainer.getContainerSlots()[i][j][0] = game.grabbedItem[0];
								}
							}
						}
					}
				}
			}
		}
		else
		{
			//Using items when you right click them
			for(int j = 0; j < 6; j++)
			{
				for (int i = 0; i < 5; i++)
				{
					if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+i*game.inventoryhandler.iconSize, (game.inventoryhandler.topLeftCornerY+j*game.inventoryhandler.iconSize)*(1-(j/6))+(j/6)*(Main_Game.HEIGHT-game.inventoryhandler.iconSize), game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
					{
						game.interactions.useItem(game.Inventory[i][j][0], i, j);
					}
				}
			}
		}
		checkCrafting(mx, my, false);
	}

	public void middleClickManage(int mx, int my) {
		if (game.grabbedItem[0] != 0)
		{
			//Moving half the items in the grabbedItems into an inventory slot
			for(int j = 0; j < 6; j++)
			{
				for (int i = 0; i < 5; i++)
				{
					if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+i*game.inventoryhandler.iconSize, (game.inventoryhandler.topLeftCornerY+j*game.inventoryhandler.iconSize)*(1-(j/6))+(j/6)*(Main_Game.HEIGHT-game.inventoryhandler.iconSize), game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
					{
						if ((game.Inventory[i][j][0] == 0) || (game.Inventory[i][j][0] == game.grabbedItem[0])) {
							short half = (short) (game.grabbedItem[1]/2);
							short addedItemCount = (short) (half+game.Inventory[i][j][1]);
							if (addedItemCount > Main_Game.maxStackSize) {
								game.Inventory[i][j][1] = Main_Game.maxStackSize;
								game.Inventory[i][j][0] = game.grabbedItem[0];
								game.grabbedItem[1] -= half;
								game.grabbedItem[1] += (addedItemCount-Main_Game.maxStackSize);
							}
							else {
								game.Inventory[i][j][1] += half;
								game.Inventory[i][j][0] = game.grabbedItem[0];
								game.grabbedItem[1] -= half;
							}
						}
					}
				}
			}
			//Moving moving half the items from the grabbed items into a container
			for(int j = 0; j < 5; j++)
			{
				for (int i = 0; i < 5; i++)
				{
					if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+(i+6)*game.inventoryhandler.iconSize, game.inventoryhandler.topLeftCornerY+j*game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
					{
						if (showingContainer()) {
							if (game.curentlyOpenedContainer.getValidSlots()[j][i]) {
								if (((game.grabbedItem[0] == game.curentlyOpenedContainer.getContainerSlots()[i][j][0]) || (game.curentlyOpenedContainer.getContainerSlots()[i][j][0] == 0)) && (!game.inventoryhandler.unstackable(game.grabbedItem[0])))
								{
									short half = (short) (game.grabbedItem[1]/2);
									short addedItemCount = (short) (half+game.curentlyOpenedContainer.getContainerSlots()[i][j][1]);
									if (addedItemCount > Main_Game.maxStackSize) {
										game.curentlyOpenedContainer.getContainerSlots()[i][j][1] = Main_Game.maxStackSize;
										game.curentlyOpenedContainer.getContainerSlots()[i][j][0] = game.grabbedItem[0];
										game.grabbedItem[1] -= half;
										game.grabbedItem[1] += (addedItemCount-Main_Game.maxStackSize);
									}
									else {
										game.curentlyOpenedContainer.getContainerSlots()[i][j][1] += half;
										game.curentlyOpenedContainer.getContainerSlots()[i][j][0] = game.grabbedItem[0];
										game.grabbedItem[1] -= half;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void leftClickManage(int mx, int my) {
		//Clicking the trash to delete times from the inventory
		if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX, game.inventoryhandler.topLeftCornerY-game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
		{
			for (int i = 0; i < game.grabbedItem.length; i++) {
				game.grabbedItem[i] = 0;
			}
		}

		//Grabbing and moving items in the inventory
		for(int j = 0; j < 6; j++)
		{
			for (int i = 0; i < 5; i++)
			{
				if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+i*game.inventoryhandler.iconSize, ((game.inventoryhandler.topLeftCornerY+j*game.inventoryhandler.iconSize)*(1-(j/5)))+((j/5)*(Main_Game.HEIGHT-game.inventoryhandler.iconSize)), game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
				{
					if (game.Inventory[i][j][0] != game.grabbedItem[0] || game.inventoryhandler.unstackable(game.grabbedItem[0]) || game.inventoryhandler.unstackable(game.grabbedItem[0])) { //Swap clicked item with grabbed item if they are different
						short[] temp = Arrays.copyOf(game.grabbedItem, game.grabbedItem.length);
						game.grabbedItem = Arrays.copyOf(game.Inventory[i][j], game.Inventory[i][j].length);
						game.Inventory[i][j] = temp;
					}
					else { //If items are the same, then combine the stacks
						short addedItemCount = (short) (game.Inventory[i][j][1] + game.grabbedItem[1]);
						if (addedItemCount > Main_Game.maxStackSize) {
							game.Inventory[i][j][1] = Main_Game.maxStackSize;
							game.grabbedItem[1] = (short) (addedItemCount - Main_Game.maxStackSize);
						}
						else {
							game.Inventory[i][j][1] = addedItemCount;
							game.grabbedItem[1] = 0;
							game.grabbedItem[0] = 0;
						}
					}
				}
			}
		}

		//Moving items in containers
		for(int j = 0; j < 5; j++)
		{
			for (int i = 0; i < 5; i++)
			{
				if (mousePosition(mx, my, game.inventoryhandler.topLeftCornerX+(i+6)*game.inventoryhandler.iconSize, game.inventoryhandler.topLeftCornerY+j*game.inventoryhandler.iconSize, game.inventoryhandler.iconSize, game.inventoryhandler.iconSize))
				{
					if (showingContainer()) {
						if (game.curentlyOpenedContainer.getValidSlots()[j][i]) {
							if ((game.grabbedItem[0] != game.curentlyOpenedContainer.getContainerSlots()[i][j][0]) || (game.grabbedItem[0] == 0) || game.inventoryhandler.unstackable(game.curentlyOpenedContainer.getContainerSlots()[i][j][0]) || game.inventoryhandler.unstackable(game.grabbedItem[0]))
							{
								short[] temp = Arrays.copyOf(game.grabbedItem, game.grabbedItem.length);
								game.grabbedItem = Arrays.copyOf(game.curentlyOpenedContainer.getContainerSlots()[i][j], game.curentlyOpenedContainer.getContainerSlots()[i][j].length);
								game.curentlyOpenedContainer.setContainerSlots(i, j, temp);
							}
							else { //If items are the same, then combine the stacks
								short addedItemCount = (short) (game.curentlyOpenedContainer.getContainerSlots()[i][j][1] + game.grabbedItem[1]);
								if (addedItemCount > Main_Game.maxStackSize) {
									game.curentlyOpenedContainer.getContainerSlots()[i][j][1] = Main_Game.maxStackSize;
									game.grabbedItem[1] = (short) (addedItemCount - Main_Game.maxStackSize);
								}
								else {
									game.curentlyOpenedContainer.getContainerSlots()[i][j][1] = addedItemCount;
									game.grabbedItem[1] = 0;
									game.grabbedItem[0] = 0;
								}
							}
						}
					}
				}
			}
		}

		//Moving items to the crafting window
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