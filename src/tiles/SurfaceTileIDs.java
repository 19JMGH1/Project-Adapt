package tiles;

import core.Main_Game;

public enum SurfaceTileIDs {
	Blank(), //To get the ID number, look at the line of code for the tile, and subtract 6
	Bulder(),
	Pebble(),
	Tree(),
	WoodenWall("Wooden Wall"),
	HorizontalClosedDoor("Door"),
	VerticalClosedDoor("Door"),
	HorizontalOpenedDoor("Door"),
	VerticalOpenedDoor("Door"),
	StoneTable("Stone Table"),
	Dirt(),
	Sand(),
	Water(),
	mine(),
	BlastFurnace("Blast Furnace"),
	Anvil(),
	LESU(),
	CopperWire("Copper Wire"),
	CoalGenerator("Coal Generator"),
	Refiner(),
	Cabinet(),
	ElectricFurnace("Electric Furnace"),
	ENDOFLIST;
	
	private String name;
	
	SurfaceTileIDs() {
		name = this.toString();
	}
	
	SurfaceTileIDs(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static int[] getSpriteSheet(short tileValues) {
		int[] spriteXY = {tileValues, 0};
		while (spriteXY[0] >= Main_Game.surfaceTileSpriteWidth) {
			spriteXY[0] -= Main_Game.surfaceTileSpriteWidth;
			spriteXY[1]++;
		}
		return spriteXY;
	}
}
