package tiles;

import core.Main_Game;

public enum SurfaceTileIDs { //The short array tells the game what inventory items you get when you break the tile. Its an array of arrays that stores it as {item id, num of that id to harvest}
	Blank(null, "Grass", "unbreakable"), //To get the ID number, look at the line of code for the tile, and subtract 6
	Bulder(new short[][] {{1, 3}},"Bulder", "pickaxe"),
	Pebble(new short[][] {{1, 1}}),
	Tree(new short[][] {{2, 3}, {3, 2}}, "Tree", "axe"),
	WoodenWall(new short[][] {{8, 1}}, "WoodenWall"),
	HorizontalClosedDoor(new short[][] {{9, 1}}, "Door"),
	VerticalClosedDoor(new short[][] {{9, 1}}, "Door"),
	HorizontalOpenedDoor(new short[][] {{9, 1}}, "Door"),
	VerticalOpenedDoor(new short[][] {{9, 1}}, "Door"),
	StoneTable(new short[][] {{10, 1}}, "StoneTable"),
	Dirt(null, "Dirt", "unbreakable"),
	Sand(null, "Sand", "unbreakable"),
	Water(null, "Water", "unbreakable"),
	mine(new short[][] {{11, 1}}),
	BlastFurnace(new short[][] {{21, 1}}, "BlastFurnace", "none", true, true),
	Anvil(new short[][] {{29, 1}}),
	LESU(new short[][] {{30, 1}}, "LESU", "none", true, true),
	CopperWire(new short[][] {{31, 1}}, "CopperWire", "none", true, true),
	CoalGenerator(new short[][] {{32, 1}}, "CoalGenerator", "none", true, true),
	Refiner(new short[][] {{33, 1}}, "Refiner", "none", true, true),
	Cabinet(new short[][] {{40, 1}}, "Cabinet", "none", true, false),
	ElectricFurnace(new short[][] {{41, 1}}, "ElectricFurnace", "none", true, true),
	Acorn(new short[][] {{3, 1}}, "Acorn", "none", true, false),
	ENDOFLIST;
	
	private String name;
	private short[][] items; //The items that are dropped from breaking the tile
	private boolean processor;
	private boolean electronic;
	private String tool;
	
	/**
	 * 
	 * @param processor whether or not the tile is a container
	 * @param electronic whether or not the tile is an electronic container
	 */
	SurfaceTileIDs(short[][] items, boolean processor, boolean electronic) {
		this.items = items;
		name = this.toString();
		setTool("none");
		this.setProcessor(processor);
		this.setElectronic(electronic);
	}
	
	/**
	 * Constructor when there is nothing special about the tile
	 */
	SurfaceTileIDs() {
		name = this.toString();
		setTool("none");
		setProcessor(false);
		setElectronic(false);
	}
	
	SurfaceTileIDs(short[][] items) {
		this.items = items;
		name = this.toString();
		setTool("none");
		setProcessor(false);
		setElectronic(false);
	}
	
	/**
	 * 
	 * @param name the name of the tile
	 */
	SurfaceTileIDs(short[][] items, String name) {
		this.items = items;
		this.name = name;
		this.setTool("none");
		setProcessor(false);
		setElectronic(false);
	}
	
	/**
	 * 
	 * @param name the name of the tile
	 * @param tool the type of tool needed to break the tile
	 */
	SurfaceTileIDs(short[][] items, String name, String tool) {
		this.items = items;
		this.name = name;
		this.setTool(tool);
		setProcessor(false);
		setElectronic(false);
	}
	
	/**
	 * 
	 * @param name the string for the name of the tile
	 * @param processor whether or not the tile is a container
	 * @param electronic whether or not the tile is an electronic
	 */
	SurfaceTileIDs(short[][] items, String name, String tool, boolean processor, boolean electronic){
		this.items = items;
		this.name = name;
		this.setTool(tool);
		this.setProcessor(processor);
		this.setElectronic(electronic);
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

	public boolean isProcessor() {
		return processor;
	}

	public void setProcessor(boolean processor) {
		this.processor = processor;
	}

	public boolean isElectronic() {
		return electronic;
	}

	public void setElectronic(boolean electronic) {
		this.electronic = electronic;
	}

	public String getTool() {
		return tool;
	}

	public void setTool(String tool) {
		this.tool = tool;
	}

	public short[][] getItems() {
		return items;
	}

	public void setItems(short[][] items) {
		this.items = items;
	}
}
