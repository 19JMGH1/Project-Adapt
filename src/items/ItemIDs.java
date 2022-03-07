package items;

import core.Main_Game;

public enum ItemIDs { //An items invID can be obtained by looking at the line of code it's on, and subtracting 4.
	Blank(),
	Stone(),
	Wood(),
	Accorn(),
	SharpStone("Sharp Stone"),
	Tier1CraftingUpgrade("Tier 1 Crafting Upgrade"),
	StonePickaxe("Stone Pickaxe"),
	StoneAxe("Stone Axe"),
	WoodenWall("Wooden Wall"),
	WoodenDoor("Wooden Door"),
	StoneTable("Stone Table"),
	Mine(),
	Coal(),
	IronOre("Iron Ore"),
	CopperOre("Copper Ore"),
	GoldOre("Gold Ore"),
	LithiumOre("Lithium Ore"),
	UraniumOre("Uranium Ore"),
	AluminumOre("Aluminum Ore"),
	Topaz(),
	IronPickaxe("Iron Pickaxe"),
	BlastFurnace("Blast Furnace"),
	IronBar("Iron Bar"),
	CopperBar("Cooper Bar"),
	GoldBar("Gold Bar"),
	LithiumShard("Lithium Shard"),
	UraniumNugget("Uranium Nugget"),
	AluminumBar("Aluminum Bar"),
	Tier2CraftingUpgrade("Tier 2 Crafting Upgrade"),
	Anvil(),
	LESU(), //LESU stands for Lithium Energy Storage Unit
	CopperWire("Copper Wire"),
	CoalGenerator("Coal Generator"),
	Refiner(),
	RefinedIronOre("Refined Iron Ore"),
	RefinedCopperOre("Refined Copper Ore"),
	RefinedGoldOre("Refined Gold Ore"),
	RefinedLithiumOre("Refined Lithium Ore"),
	RefinedUraniumOre("Refined Uranium Ore"),
	RefinedAluminumOre("Refined Aluminum Ore"),
	Cabinet(),
	ENDOFLIST();
	
	private String name;
	
	ItemIDs() {
		name = this.toString();
	}
	
	ItemIDs(String name){
		this.name = name;
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
}
