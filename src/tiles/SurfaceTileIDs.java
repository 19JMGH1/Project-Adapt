package tiles;

import core.Main_Game;
import items.ItemIDs;

public enum SurfaceTileIDs { //The short array tells the game what inventory items you get when you break the tile. Its an array of arrays that stores it as {item id, num of that id to harvest}
	Blank(null, "Grass", "unbreakable", false), //To get the ID number, look at the line of code for the tile, and subtract 6
	Bulder(new short[][] {{(short) ItemIDs.Stone.ordinal(), 3}},"Bulder", "pickaxe", true),
	Pebble(new short[][] {{(short) ItemIDs.Stone.ordinal(), 1}}, false),
	Tree(new short[][] {{(short) ItemIDs.Wood.ordinal(), 3}, {(short) ItemIDs.Acorn.ordinal(), 2}}, "Tree", "axe", true),
	WoodenWall(new short[][] {{(short) ItemIDs.WoodenWall.ordinal(), 1}}, "WoodenWall", true),
	HorizontalClosedDoor(new short[][] {{(short) ItemIDs.WoodenDoor.ordinal(), 1}}, "Door", true),
	VerticalClosedDoor(new short[][] {{(short) ItemIDs.WoodenDoor.ordinal(), 1}}, "Door", true),
	HorizontalOpenedDoor(new short[][] {{(short) ItemIDs.WoodenDoor.ordinal(), 1}}, "Door", false),
	VerticalOpenedDoor(new short[][] {{(short) ItemIDs.WoodenDoor.ordinal(), 1}}, "Door", false),
	StoneTable(new short[][] {{(short) ItemIDs.StoneTable.ordinal(), 1}}, "StoneTable", true),
	Dirt(null, "Dirt", "unbreakable", false),
	Sand(null, "Sand", "unbreakable", false),
	Water(null, "Water", "unbreakable", true),
	mine(new short[][] {{(short) ItemIDs.Mine.ordinal(), 1}}, false),
	BlastFurnace(new short[][] {{(short) ItemIDs.BlastFurnace.ordinal(), 1}}, "BlastFurnace", "none", true, true, true),
	Anvil(new short[][] {{(short) ItemIDs.Anvil.ordinal(), 1}}, true),
	LESU(new short[][] {{(short) ItemIDs.LESU.ordinal(), 1}}, "LESU", "none", true, true, true),
	CopperWire(new short[][] {{(short) ItemIDs.CopperWire.ordinal(), 1}}, "CopperWire", "none", true, true, false),
	CoalGenerator(new short[][] {{(short) ItemIDs.CoalGenerator.ordinal(), 1}}, "CoalGenerator", "none", true, true, true),
	Refiner(new short[][] {{(short) ItemIDs.Refiner.ordinal(), 1}}, "Refiner", "none", true, true, true),
	Cabinet(new short[][] {{(short) ItemIDs.Cabinet.ordinal(), 1}}, "Cabinet", "none", true, false, true),
	ElectricFurnace(new short[][] {{(short) ItemIDs.ElectricFurnace.ordinal(), 1}}, "ElectricFurnace", "none", true, true, true),
	Acorn(new short[][] {{(short) ItemIDs.Acorn.ordinal(), 1}}, "Acorn", "none", true, false, false),
	Assembler(new short[][] {{(short) ItemIDs.Assembler.ordinal()}}, "Assembler", "none", true, true, true),
	SolarPanel(new short[][] {{(short) ItemIDs.SolarPanel.ordinal()}}, "SolarPanel", "none", true, true, true),
	ENDOFLIST;
	
	private String name;
	private short[][] items; //The items that are dropped from breaking the tile
	private boolean processor;
	private boolean electronic;
	private String tool;
	private boolean collides; //This variable tells if the tile can be collided with.
	
	/**
	 * 
	 * @param processor whether or not the tile is a container
	 * @param electronic whether or not the tile is an electronic container
	 */
	SurfaceTileIDs(short[][] items, boolean processor, boolean electronic, boolean collides) {
		this.setCollides(collides);
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
		this.setCollides(false);
		name = this.toString();
		setTool("none");
		setProcessor(false);
		setElectronic(false);
	}
	
	SurfaceTileIDs(short[][] items, boolean collides) {
		this.setCollides(collides);
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
	SurfaceTileIDs(short[][] items, String name, boolean collides) {
		this.setCollides(collides);
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
	SurfaceTileIDs(short[][] items, String name, String tool, boolean collides) {
		this.setCollides(collides);
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
	SurfaceTileIDs(short[][] items, String name, String tool, boolean processor, boolean electronic, boolean collides){
		this.setCollides(collides);
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

	public boolean isCollides() {
		return collides;
	}

	public void setCollides(boolean collides) {
		this.collides = collides;
	}
}
