package tiles;

import core.Main_Game;

public enum CoveTileIDs {
	Blank(),
	CoalOre("Coal Ore"),
	IronOre("Iron Ore"),
	CopperOre("Copper Ore"),
	GoldOre("Gold Ore"),
	LithiumOre("Lithium Ore"),
	UraniumOre("Uranium Ore"),
	AluminumOre("Aluminum Ore"),
	Topaz(),
	Mine(),
	ENDOFLIST;
	
	private String name;
	
	CoveTileIDs() {
		name = this.toString();
	}
	
	CoveTileIDs(String name){
		this.name = name;
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
