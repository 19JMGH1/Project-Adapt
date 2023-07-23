package items;

public enum CraftingRecipes {

	//All the shaped crafting recipes go before the shapeless ones.
	StonePick((byte) 1, false, (byte) 2, (byte) 2, new ItemIDs[][]
			{	{ItemIDs.Stone,	ItemIDs.SharpStone},
				{ItemIDs.Wood,	ItemIDs.Blank}},
			new int[][] {{ItemIDs.Stone.ordinal(), 1}, {ItemIDs.SharpStone.ordinal(), 1}, {ItemIDs.Wood.ordinal(), 1}},
			ItemIDs.StonePickaxe, (byte) 1),
	StoneAxe((byte) 1, false, (byte) 2, (byte) 2, new ItemIDs[][]
			{	{ItemIDs.Wood,	ItemIDs.SharpStone},
				{ItemIDs.Wood,	ItemIDs.Blank}},
			new int[][] {{ItemIDs.SharpStone.ordinal(), 1}, {ItemIDs.Wood.ordinal(), 2}},
			ItemIDs.StoneAxe, (byte) 1),
	WoddenWall((byte) 1, false, (byte) 2, (byte) 2, new ItemIDs[][]
			{	{ItemIDs.Wood,	ItemIDs.Wood},
				{ItemIDs.Wood,	ItemIDs.Wood}},
			new int[][] {{ItemIDs.Wood.ordinal(), 4}},
			ItemIDs.WoodenWall, (byte) 4),
	WoddenDoor((byte) 0, false, (byte) 2, (byte) 2, new ItemIDs[][]
			{	{ItemIDs.Wood},
				{ItemIDs.Wood}},
			new int[][] {{ItemIDs.Wood.ordinal(), 2}},
			ItemIDs.WoodenDoor, (byte) 1),
	Cabinet((byte) 2, false, (byte) 3, (byte) 3, new ItemIDs[][]
			{	{ItemIDs.Wood,	ItemIDs.Wood,	ItemIDs.Wood},
				{ItemIDs.Wood,	ItemIDs.Blank,	ItemIDs.Wood},
				{ItemIDs.Wood,	ItemIDs.Wood,	ItemIDs.Wood}},
			new int[][] {{ItemIDs.Wood.ordinal(), 8}},
			ItemIDs.Cabinet, (byte) 1),
	StoneTable((byte) 1, false, (byte) 2, (byte) 2, new ItemIDs[][]
			{	{ItemIDs.Stone,	ItemIDs.Stone},
				{ItemIDs.Wood,	ItemIDs.Wood}},
			new int[][] {{ItemIDs.Wood.ordinal(), 2}, {ItemIDs.Stone.ordinal(), 2}},
			ItemIDs.StoneTable, (byte) 1),
	Mine((byte) 2, false, (byte) 3, (byte) 3, new ItemIDs[][]
			{	{ItemIDs.Stone,	ItemIDs.Stone,			ItemIDs.Stone},
				{ItemIDs.Stone,	ItemIDs.Blank,			ItemIDs.Stone},
				{ItemIDs.Stone,	ItemIDs.StonePickaxe,	ItemIDs.Stone}},
			new int[][] {{ItemIDs.Stone.ordinal(), 7}, {ItemIDs.StonePickaxe.ordinal(), 1}},
			ItemIDs.Mine, (byte) 1),
	BlastFurnace((byte) 2, false, (byte) 3, (byte) 3, new ItemIDs[][]
			{	{ItemIDs.Stone,	ItemIDs.Blank,	ItemIDs.Stone},
				{ItemIDs.Stone,	ItemIDs.Blank,	ItemIDs.Stone},
				{ItemIDs.Stone,	ItemIDs.Coal,	ItemIDs.Stone}},
			new int[][] {{ItemIDs.Stone.ordinal(), 6}, {ItemIDs.Coal.ordinal(), 1}},
			ItemIDs.BlastFurnace, (byte) 1),
	Tier2Crafting((byte) 2, false, (byte) 3, (byte) 3, new ItemIDs[][]
			{	{ItemIDs.IronBar,	ItemIDs.IronBar,	ItemIDs.IronBar},
				{ItemIDs.IronBar,	ItemIDs.CopperBar,	ItemIDs.IronBar},
				{ItemIDs.IronBar,	ItemIDs.IronBar,	ItemIDs.IronBar}},
			new int[][] {{ItemIDs.IronBar.ordinal(), 8}, {ItemIDs.CopperBar.ordinal(), 1}},
			ItemIDs.Tier2CraftingUpgrade, (byte) 1),
	IronPick((byte) 3, false, (byte) 4, (byte) 4, new ItemIDs[][]
			{	{ItemIDs.IronBar,	ItemIDs.IronBar,	ItemIDs.IronBar,	ItemIDs.Blank},
				{ItemIDs.Blank,		ItemIDs.Wood,		ItemIDs.Blank,			ItemIDs.IronBar},
				{ItemIDs.Blank,		ItemIDs.Wood,		ItemIDs.Blank,			ItemIDs.Blank},
				{ItemIDs.Blank,		ItemIDs.Wood,		ItemIDs.Blank,			ItemIDs.Blank}},
			new int[][] {{ItemIDs.IronBar.ordinal(), 4}, {ItemIDs.Wood.ordinal(), 3}},
			ItemIDs.IronPickaxe, (byte) 1),
	Anvil((byte) 2, false, (byte) 3, (byte) 3, new ItemIDs[][]
			{	{ItemIDs.IronBar,	ItemIDs.IronBar,	ItemIDs.IronBar},
				{ItemIDs.Blank,		ItemIDs.IronBar,	ItemIDs.Blank},
				{ItemIDs.IronBar,	ItemIDs.IronBar,	ItemIDs.IronBar}},
			new int[][] {{ItemIDs.IronBar.ordinal(), 6}},
			ItemIDs.Anvil, (byte) 1),
	CopperWire((byte) 2, false, (byte) 3, (byte) 1, new ItemIDs[][]
			{	{ItemIDs.CopperBar,	ItemIDs.CopperBar,	ItemIDs.CopperBar}},
			new int[][] {{ItemIDs.CopperBar.ordinal(), 3}},
			ItemIDs.CopperWire, (byte) 10),
	LESU((byte) 3, false, (byte) 4, (byte) 4, new ItemIDs[][]
			{	{ItemIDs.IronBar,		ItemIDs.CopperWire,		ItemIDs.CopperWire,		ItemIDs.IronBar},
				{ItemIDs.CopperWire,	ItemIDs.LithiumShard,	ItemIDs.LithiumShard,	ItemIDs.CopperWire},
				{ItemIDs.CopperWire,	ItemIDs.LithiumShard,	ItemIDs.LithiumShard,	ItemIDs.CopperWire},
				{ItemIDs.IronBar,		ItemIDs.CopperWire,		ItemIDs.CopperWire,		ItemIDs.IronBar}},
			new int[][] {{ItemIDs.IronBar.ordinal(), 4}, {ItemIDs.CopperWire.ordinal(), 8}, {ItemIDs.LithiumShard.ordinal(), 4}},
			ItemIDs.LESU, (byte) 1),
	CoalGenerator((byte) 3, false, (byte) 4, (byte) 4, new ItemIDs[][]
			{	{ItemIDs.IronBar,		ItemIDs.CopperWire,	ItemIDs.CopperWire,	ItemIDs.IronBar},
				{ItemIDs.CopperWire,	ItemIDs.IronBar,	ItemIDs.IronBar,		ItemIDs.CopperWire},
				{ItemIDs.CopperWire,	ItemIDs.IronBar,	ItemIDs.IronBar,		ItemIDs.CopperWire},
				{ItemIDs.IronBar,		ItemIDs.CopperWire,	ItemIDs.CopperWire,	ItemIDs.IronBar}},
			new int[][] {{ItemIDs.IronBar.ordinal(), 8}, {ItemIDs.CopperWire.ordinal(), 8}},
			ItemIDs.CoalGenerator, (byte) 1),
	Refiner((byte) 3, false, (byte) 4, (byte) 4, new ItemIDs[][]
			{	{ItemIDs.IronBar,		ItemIDs.CopperWire,	ItemIDs.CopperWire,	ItemIDs.IronBar},
				{ItemIDs.CopperWire,	ItemIDs.GoldBar,	ItemIDs.GoldBar,	ItemIDs.CopperWire},
				{ItemIDs.CopperWire,	ItemIDs.GoldBar,	ItemIDs.GoldBar,	ItemIDs.CopperWire},
				{ItemIDs.IronBar,		ItemIDs.CopperWire,	ItemIDs.CopperWire,	ItemIDs.IronBar}},
			new int[][] {{ItemIDs.IronBar.ordinal(), 4}, {ItemIDs.CopperWire.ordinal(), 8}, {ItemIDs.GoldBar.ordinal(), 4}},
			ItemIDs.Refiner, (byte) 1),
	HeatingCoil((byte) 3, false, (byte) 4, (byte) 4, new ItemIDs[][]
			{	{ItemIDs.CopperWire,	ItemIDs.IronBar,	ItemIDs.IronBar,	ItemIDs.Blank},
				{ItemIDs.Blank,			ItemIDs.IronBar,	ItemIDs.Blank,		ItemIDs.IronBar},
				{ItemIDs.IronBar,		ItemIDs.Blank,		ItemIDs.Blank,		ItemIDs.IronBar},
				{ItemIDs.Blank,			ItemIDs.IronBar,	ItemIDs.IronBar,	ItemIDs.Blank}},
			new int[][] {{ItemIDs.IronBar.ordinal(), 8}, {ItemIDs.CopperWire.ordinal(), 1}},
			ItemIDs.HeatingCoil, (byte) 4),
	ElectricFurnace((byte) 3, false, (byte) 4, (byte) 4, new ItemIDs[][]
			{	{ItemIDs.IronBar,		ItemIDs.CopperWire,	ItemIDs.CopperWire,			ItemIDs.IronBar},
				{ItemIDs.CopperWire,	ItemIDs.HeatingCoil,	ItemIDs.HeatingCoil,	ItemIDs.CopperWire},
				{ItemIDs.CopperWire,	ItemIDs.HeatingCoil,	ItemIDs.HeatingCoil,	ItemIDs.CopperWire},
				{ItemIDs.IronBar,		ItemIDs.CopperWire,	ItemIDs.CopperWire,			ItemIDs.IronBar}},
			new int[][] {{ItemIDs.IronBar.ordinal(), 4}, {ItemIDs.CopperWire.ordinal(), 8}, {ItemIDs.HeatingCoil.ordinal(), 4}},
			ItemIDs.ElectricFurnace, (byte) 1),
	Assembler((byte) 3, false, (byte) 4, (byte) 4, new ItemIDs[][]
			{	{ItemIDs.GoldBar,		ItemIDs.CopperWire,		ItemIDs.CopperWire,		ItemIDs.GoldBar},
				{ItemIDs.CopperWire,	ItemIDs.AluminumBar,	ItemIDs.AluminumBar,	ItemIDs.CopperWire},
				{ItemIDs.CopperWire,	ItemIDs.AluminumBar,	ItemIDs.AluminumBar,	ItemIDs.CopperWire},
				{ItemIDs.GoldBar,		ItemIDs.CopperWire,		ItemIDs.CopperWire,		ItemIDs.GoldBar}},
			new int[][] {{ItemIDs.IronBar.ordinal(), 4}, {ItemIDs.CopperWire.ordinal(), 8}, {ItemIDs.AluminumBar.ordinal(), 4}},
			ItemIDs.Assembler, (byte) 1),
	SolarPanel((byte) 3, false, (byte) 4, (byte) 4, new ItemIDs[][]
			{	{ItemIDs.IronBar,		ItemIDs.CopperWire,	ItemIDs.CopperWire,	ItemIDs.IronBar},
				{ItemIDs.CopperWire,	ItemIDs.SolarCell,	ItemIDs.SolarCell,	ItemIDs.CopperWire},
				{ItemIDs.CopperWire,	ItemIDs.SolarCell,	ItemIDs.SolarCell,	ItemIDs.CopperWire},
				{ItemIDs.IronBar,		ItemIDs.CopperWire,	ItemIDs.CopperWire,	ItemIDs.IronBar}},
			new int[][] {{ItemIDs.IronBar.ordinal(), 4}, {ItemIDs.CopperWire.ordinal(), 8}, {ItemIDs.SolarCell.ordinal(), 4}},
			ItemIDs.SolarPanel, (byte) 1),
	Boat((byte) 2, false, (byte) 3, (byte) 3, new ItemIDs[][]
			{	{ItemIDs.Wood,	ItemIDs.Blank,	ItemIDs.Wood},
				{ItemIDs.Wood,	ItemIDs.Wood,	ItemIDs.Wood}},
			new int[][] {{ItemIDs.Wood.ordinal(), 5}},
			ItemIDs.Boat, (byte) 1),
	
	//All the shapeless crafting recipes must follow (They go at the bottom to be given less priority)
	SharpStone((byte) 0, true,
		new int[][] {{ItemIDs.Stone.ordinal(), 2}},
		ItemIDs.SharpStone, (byte) 1),
	Tier1Crafting((byte) 0, true,
			new int[][] {{ItemIDs.Stone.ordinal(), 1}, {ItemIDs.Wood.ordinal(), 1}},
			ItemIDs.Tier1CraftingUpgrade, (byte) 1);
	
	private byte unlockLevel; //This is the crafting level required for the recipe to appear in the recipe book.
	private boolean isShapeless; //This the recipe shaped or shapeless
	
	private byte recipeWidth; //This is the width of the recipe
	private byte recipeHeight; //This is the height of the recipe (Leave blank for shapeless recipes)
	private ItemIDs[][] items; //The items required for the recipe
	private int[][] numOfItems; //How many of each item ID are needed for the recipe
	
	private ItemIDs output; //What item is made from this recipe
	private byte numOutput; //How many items does this recipe make
	
	CraftingRecipes(byte unlockLevel, boolean isShapeless, byte recipeWidth, byte recipeHeight, ItemIDs[][] items,
			int[][] numOfItems ,ItemIDs output, byte numOutput){
		this.setUnlockLevel(unlockLevel);
		this.setShapeless(isShapeless);
		this.setRecipeWidth(recipeWidth);
		this.setRecipeHeight(recipeHeight);
		this.setItems(items);
		this.setNumOfItems(numOfItems);
		this.setOutput(output);
		this.setNumOutput(numOutput);
	}
	
	CraftingRecipes(byte unlockLevel, boolean isShapeless, int[][] numOfItems ,ItemIDs output, byte numOutput){
		this.setUnlockLevel(unlockLevel);
		this.setShapeless(isShapeless);
		this.setNumOfItems(numOfItems);
		this.setOutput(output);
		this.setNumOutput(numOutput);
	}

	public byte getUnlockLevel() {
		return unlockLevel;
	}

	public void setUnlockLevel(byte unlockLevel) {
		this.unlockLevel = unlockLevel;
	}

	public boolean isShapeless() {
		return isShapeless;
	}

	public void setShapeless(boolean isShapeless) {
		this.isShapeless = isShapeless;
	}

	public byte getRecipeWidth() {
		return recipeWidth;
	}

	public void setRecipeWidth(byte recipeWidth) {
		this.recipeWidth = recipeWidth;
	}

	public byte getRecipeHeight() {
		return recipeHeight;
	}

	public void setRecipeHeight(byte recipeHeight) {
		this.recipeHeight = recipeHeight;
	}

	public ItemIDs[][] getItems() {
		return items;
	}

	public void setItems(ItemIDs[][] items) {
		this.items = items;
	}

	public int[][] getNumOfItems() {
		return numOfItems;
	}

	public void setNumOfItems(int[][] numOfItems) {
		this.numOfItems = numOfItems;
	}
	
	public ItemIDs getOutput() {
		return output;
	}

	public void setOutput(ItemIDs output) {
		this.output = output;
	}

	public byte getNumOutput() {
		return numOutput;
	}

	public void setNumOutput(byte numOutput) {
		this.numOutput = numOutput;
	}
}
