package items;

import core.Main_Game;

public enum ItemIDs { //An items invID can be obtained by looking at the line of code it's on, and subtracting 4.
	Blank(), //The parameters are string for a name different than the enum name, and what tile is placed if you right click with this item (leave blank if it doesn't place an item).
	Stone(),
	Wood(),
	Accorn(),
	SharpStone("Sharp Stone"),
	Tier1CraftingUpgrade("Tier 1 Crafting Upgrade"),
	StonePickaxe("Stone Pickaxe"),
	StoneAxe("Stone Axe"),
	WoodenWall("Wooden Wall", (short) 4),
	WoodenDoor("Wooden Door", (short) 5),
	StoneTable("Stone Table", (short) 9),
	Mine((short) 13),
	Coal(),
	IronOre("Iron Ore"),
	CopperOre("Copper Ore"),
	GoldOre("Gold Ore"),
	LithiumOre("Lithium Ore"),
	UraniumOre("Uranium Ore"),
	AluminumOre("Aluminum Ore"),
	Topaz(),
	IronPickaxe("Iron Pickaxe"),
	BlastFurnace("Blast Furnace", (short) 14),
	IronBar("Iron Bar"),
	CopperBar("Cooper Bar"),
	GoldBar("Gold Bar"),
	LithiumShard("Lithium Shard"),
	UraniumNugget("Uranium Nugget"),
	AluminumBar("Aluminum Bar"),
	Tier2CraftingUpgrade("Tier 2 Crafting Upgrade"),
	Anvil((short) 15),
	LESU((short) 16), //LESU stands for Lithium Energy Storage Unit
	CopperWire("Copper Wire", (short) 17),
	CoalGenerator("Coal Generator", (short) 18),
	Refiner((short) 19),
	RefinedIronOre("Refined Iron Ore"),
	RefinedCopperOre("Refined Copper Ore"),
	RefinedGoldOre("Refined Gold Ore"),
	RefinedLithiumOre("Refined Lithium Ore"),
	RefinedUraniumOre("Refined Uranium Ore"),
	RefinedAluminumOre("Refined Aluminum Ore"),
	Cabinet((short) 20),
	ElectricFurnace("Electric Furnace", (short) 21),
	HeatingCoil("Heating Coil"),
	ENDOFLIST();
	
	private String name;
	private short tile = 0; //This variable controls what tile gets placed when you right click this item
	
	ItemIDs() {
		name = this.toString();
	}
	
	ItemIDs(short tile) {
		name = this.toString();
		this.setTile(tile);
	}
	
	ItemIDs(String name) {
		this.name = name;
	}
	
	ItemIDs(String name, short tile) {
		this.name = name;
		this.setTile(tile);
	}
	
	public String getName() {
		return name;
	}
	
	public static int[] getSpriteSheet(short invValues) {
		int[] spriteXY = {invValues, 0};
		while (spriteXY[0] >= Main_Game.invItemsSpriteWidth) {
			spriteXY[0] -= Main_Game.invItemsSpriteWidth;
			spriteXY[1]++;
		}
		return spriteXY;
	}

	public short getTile() {
		return tile;
	}

	public void setTile(short tile) {
		this.tile = tile;
	}
}
