package tiles;

import core.Main_Game;

public enum CoveTileIDs {
	Blank(),
	CoalOre("Coal Ore", (byte) 2),
	IronOre("Iron Ore", (byte) 3),
	CopperOre("Copper Ore", (byte) 2),
	GoldOre("Gold Ore", (byte) 4),
	LithiumOre("Lithium Ore", (byte) 1),
	UraniumOre("Uranium Ore", (byte) 6),
	AluminumOre("Aluminum Ore", (byte) 4),
	Topaz(),
	Mine(),
	ENDOFLIST;
	
	private String name;
	public byte neededHardness = 0;
	
	CoveTileIDs() {
		name = this.toString();
	}
	
	CoveTileIDs(String name, byte neededHardness){
		this.name = name;
		this.neededHardness = neededHardness;
	}
	
	public String getName() {
		return name;
	}
	
	public static int[] getSpriteSheet(short invValues) {
		int[] spriteXY = {invValues, 0};
		while (spriteXY[0] >= Main_Game.coveTileSpriteWidth) {
			spriteXY[0] -= Main_Game.coveTileSpriteWidth;
			spriteXY[1]++;
		}
		return spriteXY;
	}
}
