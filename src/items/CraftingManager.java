package items;

import core.Identifications;
import core.InventoryHandler;
import core.Main_Game;

public class CraftingManager {

	private Main_Game game;
	private InventoryHandler inventoryhandler;

	public CraftingManager(Main_Game game, InventoryHandler inventoryhandler)
	{
		this.game = game;
		this.inventoryhandler = inventoryhandler;
	}

	public void craft()
	{
		CraftingRecipes recipe = null;
		boolean recipeFound = false;
		for (int i = 0; i < CraftingRecipes.values().length; i++) {
			recipe = CraftingRecipes.values()[i];
			if (!recipe.isShapeless()) {
				recipeFound = shapedCrafting(recipe.getItems(), recipe.getNumOfItems(), recipe.getOutput(), recipe.getNumOutput());
			}
			else {
				recipeFound = shapelessCrafting(recipe.getNumOfItems(), recipe.getOutput(), recipe.getNumOutput());
			}
			if (recipeFound) {
				return;
			}
		}
		System.out.println("No craft"); //Used for testing purposes
		
		//Shaped crafting recipes go at the top to take priority
//		if (stonePick()) {}
//		else if (woodenWall()) {}
//		else if (woodenDoor()) {}
//		else if (cabinet()) {}
//		else if (stoneAxe()) {}
//		else if (stoneTable()) {}
//		else if (mine()) {}
//		else if (blastFurnace()) {}
//		else if (tier2Crafting()) {}
//		else if (anvil()) {}
//		else if (ironPick()) {}
//		else if (copperWires()) {}
//		else if (LESU()) {}
//		else if (coalGenerator()) {}
//		else if (refiner()) {}
//		else if (heatingCoil()) {}
//		else if (electricFurnace()) {}
//		else if (assembler()) {}
//		else if (solarPanel()) {}
//		else if (boat()) {}
//
//		//Shapeless crafting recipes go at the bottom to take less priority
//		else if (sharpStone()) {}
//		else if (tier1Crafting()) {}
	}

	//Shaped crafting recipes start here
//	public boolean stonePick()
//	{
//		int IDs[][] = {{1, 4},
//				{2, 0}};
//		int numOfItems[][] = {{1, 1}, {2, 1}, {4, 1}};
//		return shapedCrafting(IDs, numOfItems, 6, 1);
//	}
//
//	public boolean stoneAxe()
//	{
//		int IDs[][] = {{2, 4},
//				{2, 0}};
//		int numOfItems[][] = {{4, 1}, {2, 2}};
//		return shapedCrafting(IDs, numOfItems, 7, 1);
//	}
//
//	public boolean woodenWall()
//	{
//		int IDs[][] = {{2, 2},
//				{2, 2}};
//		int numOfItems[][] = {{2, 4}};
//		return shapedCrafting(IDs, numOfItems, 8, 4);
//	}
//
//	public boolean woodenDoor()
//	{
//		int IDs[][] = {{2},
//				{2}};
//		int numOfItems[][] = {{2, 2}};
//		return shapedCrafting(IDs, numOfItems, 9, 1);
//	}
//
//	public boolean cabinet()
//	{
//		int IDs[][] =  {{2, 2, 2},
//				{2, 0, 2},
//				{2, 2, 2}};
//		int numOfItems[][] = {{2, 8}};
//		return shapedCrafting(IDs, numOfItems, 40, 1);
//	}
//
//	public boolean stoneTable()
//	{
//		int IDs[][] = {{1, 1},
//				{2, 2}};
//		int numOfItems[][] = {{1, 2}, {2, 2}};
//		return shapedCrafting(IDs, numOfItems, 10, 1);
//	}
//
//	public boolean mine()
//	{
//		int IDs[][] = {{1, 1, 1}, 
//				{1, 0, 1},
//				{1, 6, 1}};
//		int numOfItems[][] = {{1, 7}, {6, 1}};
//		return shapedCrafting(IDs, numOfItems, 11, 1);
//	}
//	public boolean blastFurnace()
//	{
//		int IDs[][] = {{1, 0, 1}, 
//				{1, 0, 1},
//				{1, 12, 1}};
//		int numOfItems[][] = {{12, 1}, {1, 7}};
//		return shapedCrafting(IDs, numOfItems, 21, 1);
//	}
//
//	public boolean tier2Crafting()
//	{
//		int IDs[][] = {{22, 22, 22}, 
//				{22, 23, 22},
//				{22, 22, 22}};
//		int numOfItems[][] = {{23, 1}, {22, 8}};
//		return shapedCrafting(IDs, numOfItems, 28, 1);
//	}
//
//	public boolean ironPick()
//	{
//		int IDs[][] = {{22, 22, 22, 0}, 
//				{0, 2, 0, 22},
//				{0, 2, 0, 0},
//				{0, 2, 0, 0}};
//		int numOfItems[][] = {{2, 3}, {22, 4}};
//		return shapedCrafting(IDs, numOfItems, 20, 1);
//	}
//
//	public boolean anvil()
//	{
//		int IDs[][] = {{22, 22, 22}, 
//				{0, 22, 0},
//				{22, 22, 22}};
//		int numOfItems[][] = {{22, 7}};
//		return shapedCrafting(IDs, numOfItems, 29, 1);
//	}
//
//	public boolean copperWires()
//	{
//		int IDs[][] = {{23, 23, 23}};
//		int numOfItems[][] = {{23, 3}};
//		return shapedCrafting(IDs, numOfItems, 31, 10); 
//	}
//
//	public boolean LESU() //LESU stands for lithium energy storage unit
//	{
//		int IDs[][] = {{22, 31, 31, 22}, 
//				{31, 25, 25, 31},
//				{31, 25, 25, 31},
//				{22, 31, 31, 22}};
//		int numOfItems[][] = {{22, 4}, {31, 8}, {25, 4}};
//		return shapedCrafting(IDs, numOfItems, 30, 1);
//	}
//
//	public boolean coalGenerator()
//	{
//		int IDs[][] =  {{22, 31, 31, 22}, 
//				{31, 22, 22, 31},
//				{31, 22, 22, 31},
//				{22, 31, 31, 22}};
//		int numOfItems[][] = {{22, 8}, {31, 8}};
//		return shapedCrafting(IDs, numOfItems, 32, 1);
//	}
//
//	public boolean refiner()
//	{
//		int IDs[][] =  {{22, 31, 31, 22}, 
//				{31, 24, 24, 31},
//				{31, 24, 24, 31},
//				{22, 31, 31, 22}};
//		int numOfItems[][] = {{22, 4}, {24, 4}, {31, 8}};
//		return shapedCrafting(IDs, numOfItems, 33, 1);
//	}
//
//	private boolean heatingCoil() {
//		int IDs[][] =  {{31, 22, 22, 0}, 
//				{0, 22, 0, 22},
//				{22, 0, 0, 22},
//				{0, 22, 22, 0}};
//		int numOfItems[][] = {{22, 8}, {31, 1}};
//		return shapedCrafting(IDs, numOfItems, 42, 4);
//	}
//
//	private boolean electricFurnace() {
//		int IDs[][] =  {{22, 31, 31, 22}, 
//				{31, 42, 42, 31},
//				{31, 42, 42, 31},
//				{22, 31, 31, 22}};
//		int numOfItems[][] = {{22, 4}, {31, 8}, {42, 4}};
//		return shapedCrafting(IDs, numOfItems, 41, 1);
//	}
//	
//	private boolean assembler() {
//		int IDs[][] =  {{24, 31, 31, 24}, 
//				{31, 27, 27, 31},
//				{31, 27, 27, 31},
//				{24, 31, 31, 24}};
//		int numOfItems[][] = {{24, 4}, {31, 8}, {27, 4}};
//		return shapedCrafting(IDs, numOfItems, 44, 1);
//	}
//	
//	private boolean solarPanel() {
//		int IDs[][] =  {{22, 31, 31, 22}, 
//				{31, 47, 47, 31},
//				{31, 47, 47, 31},
//				{22, 31, 31, 22}};
//		int numOfItems[][] = {{22, 4}, {31, 8}, {47, 4}};
//		return shapedCrafting(IDs, numOfItems, ItemIDs.SolarPanel.ordinal(), 1);
//	}
//	
//	private boolean boat() {
//		int IDs[][] =  {{2, 0, 2},
//						{2, 2, 2}};
//		int numOfItems[][] = {{2, 6}};
//		return shapedCrafting(IDs, numOfItems, ItemIDs.Boat.ordinal(), 1);
//	}
//
//	//Shapeless crafting recipes start here
//	public boolean sharpStone()
//	{
//		int IDs[] = {1};
//		int numPerID[] = {2};
//		return shapelessCrafting(IDs, numPerID, 4, 1);
//	}
//
//	public boolean tier1Crafting()
//	{
//		int IDs[] = {1, 2};
//		int numPerID[] = {1, 1};
//		return shapelessCrafting(IDs, numPerID, 5, 1);
//	}

	public boolean shapedCrafting(ItemIDs itemIDs[][], int numPerID[][], ItemIDs IDToCraft, int numToCraft)
	{
		//The "firstItem" variable and the for loop below makes it possible
		//for the top left slot of any crafting recipe to be an empty slot.
		byte firstItem = 0;
		for (byte i = 0; i < itemIDs[0].length; i++) {
			if (itemIDs[0][i] != ItemIDs.Blank) {
				firstItem = i;
				break;
			}
		}
		
		boolean correctRecipe = true;
		boolean enoughItems = true;
		//Checks for the correct recipe
		byte numOfRecipes = 0;
		boolean itemSlotUsed[][] = {{false, false, false, false, false}, {false, false, false, false, false}, {false, false, false, false, false}, {false, false, false, false, false}, {false, false, false, false, false}};
		for (int j = 0; j < 5; j++)
		{
			for (int i = 0; i < 5; i++)
			{
				if (game.craftingBoxes[i][j] == itemIDs[0][0+firstItem].ordinal() && !itemSlotUsed[i][j])
				{
					//System.out.println(itemIDs.length+", "+itemIDs[0].length); //For testing purposes
					for (int y = 0; y < itemIDs.length; y++)
					{
						for (int x = 0; x < itemIDs[0].length; x++)
						{
							if (((i+x-firstItem) > 4)||(j+y > 4))
							{
								correctRecipe = false;
								return false;
							}
							if (!(game.craftingBoxes[i+x-firstItem][j+y] == itemIDs[y][x].ordinal()))
							{
								y = itemIDs.length;
								x = itemIDs[0].length;
								numOfRecipes--;
								correctRecipe = false;
							}
							else
							{
								itemSlotUsed[i+x-firstItem][j+y] = true;
							}
						}
					}
					numOfRecipes++;
				}
				else if (game.craftingBoxes[i][j] != 0)
				{
					if (!itemSlotUsed[i][j])
					{
						correctRecipe = false;
						return false;
					}
				}
			}
		}
		if (numOfRecipes != 1)
		{
			return false;
		}
		//Checks if the player has enough items to make the craft
		if (correctRecipe)
		{
			for (int y = 0; y < numPerID.length; y++)
			{
				int itemID = numPerID[y][0];
				int numNeeded = numPerID[y][1];
				int numInInv = 0;
				for (int j = 0; j < 6; j++)
				{
					for (int i = 0; i < 5; i++)
					{
						if (game.Inventory[i][j][0] == itemID)
						{
							numInInv += game.Inventory[i][j][1];
						}
					}
				}
				if (numInInv < numNeeded)
				{
					enoughItems = false;
				}
			}
			if (enoughItems)
			{
				if (inventoryhandler.addToInv((byte) IDToCraft.ordinal(), numToCraft))
				{
					for (int k  = 0; k < numPerID.length; k++)
					{
						int itemID = numPerID[k][0];
						int numNeeded = numPerID[k][1];
						int numUsed = 0;
						for (int j = 0; j < 6; j++)
						{
							for (int i = 0; i < 5; i++)
							{
								if (numUsed != numNeeded)
								{
									if (game.Inventory[i][j][0] == itemID)
									{
										if (!inventoryhandler.unstackable((short) itemID))
										{
											if (game.Inventory[i][j][1] > numNeeded)
											{
												numUsed = numNeeded;
												game.Inventory[i][j][1] -= numNeeded;
											}
											else
											{
												numUsed += game.Inventory[i][j][1];
												game.Inventory[i][j][1] = 0;
											}
										}
										else
										{
											String fileNameIdentifier = Identifications.getDurabilityFileName((short) itemID);
											game.files.deleteTextFile("Files/File "+game.CurrentFile+"/Inventory/"+fileNameIdentifier+" "+game.Inventory[i][j][1]+".txt");
											game.Inventory[i][j][0] = 0;
											game.Inventory[i][j][1] = 0;
											numUsed++;
										}
									}
								}
							}
						}
					}
				}
				else
				{
					game.addMessage("Please make room in your inventory to craft this item");
					return false;
				}
			}
			else
			{
				game.addMessage("You don't have enough items to complete this craft");
				return false;
			}
		}
		return true;
	}

	public boolean shapelessCrafting(int itemIDs[][], ItemIDs output, int numToCraft)
	{
		boolean correctRecipe = true;
		boolean enoughItems = true;
		for (int k = 0; k < itemIDs.length; k++)
		{
			int ID = itemIDs[k][0];
			int num = 0;
			for (int j = 0; j < 5; j++)
			{
				for (int i = 0; i < 5; i++)
				{
					if (game.craftingBoxes[i][j] == ID)
					{
						num++;
					}
					else if (game.craftingBoxes[i][j] != 0)
					{
						boolean neededItem = false;
						for (int p = 0; p < itemIDs.length; p++)
						{
							if (game.craftingBoxes[i][j] == itemIDs[p][0])
							{
								neededItem = true;
							}
						}
						if (!neededItem)
						{
							return false;
						}
					}
				}
			}
			if (num != itemIDs[k][1])
			{
				correctRecipe = false;
				return false;
			}
		}
		if (correctRecipe)
		{
			for (int k  = 0; k < itemIDs.length; k++)
			{
				int numNeeded = itemIDs[k][1];
				int numInInv = 0;
				for (int j = 0; j < 6; j++)
				{
					for (int i = 0; i < 5; i++)
					{
						if (game.Inventory[i][j][0] == itemIDs[k][0])
						{
							numInInv += game.Inventory[i][j][1];
						}
					}
				}
				if (numInInv < numNeeded)
				{
					enoughItems = false;
				}
			}
			if (enoughItems)
			{
				if (inventoryhandler.addToInv((byte) output.ordinal(), numToCraft))
				{
					for (int k  = 0; k < itemIDs.length; k++)
					{
						int numNeeded = itemIDs[k][1];
						int numUsed = 0;
						for (int j = 0; j < 6; j++)
						{
							for (int i = 0; i < 5; i++)
							{
								if (numUsed != numNeeded)
								{
									if (game.Inventory[i][j][0] == itemIDs[k][0])
									{
										if (game.Inventory[i][j][1] > numNeeded)
										{
											numUsed = numNeeded;
											game.Inventory[i][j][1] -= numNeeded;
										}
										else
										{
											numUsed += game.Inventory[i][j][1];
											game.Inventory[i][j][1] = 0;
										}
									}
								}
							}
						}
					}
				}
				else
				{
					game.addMessage("Please make room in your inventory to craft this item");
					return false;
				}
			}
			else
			{
				game.addMessage("You don't have enough of a certain item to complete this craft");
				return false;
			}
		}
		return true;
	}
}
