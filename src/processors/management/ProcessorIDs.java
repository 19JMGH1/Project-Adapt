package processors.management;

import tiles.SurfaceTileIDs;

public enum ProcessorIDs {
	NoContainer(SurfaceTileIDs.Blank, false, false, false, null, 0),
	BlastFurnace(SurfaceTileIDs.BlastFurnace, false, true, true, new boolean[][]
		{{true, true, false, true, true},
		{true, true, false, true, true},
		{false, false, false, false, false},
		{false, false, true, false, false},
		{false, false, false, false, false}},
		3),
	CopperWire(SurfaceTileIDs.CopperWire, true, false, false, new boolean[][]
		{{false, false, false, false, false},
		{false, false, false, false, false},
		{false, false, false, false, false},
		{false, false, false, false, false},
		{false, false, false, false, false}},
		0, 1000, 200, 0),
	LESU(SurfaceTileIDs.LESU, true, false, false, new boolean[][]
		{{false, false, false, false, false},
		{false, false, false, false, false},
		{false, false, false, false, false},
		{false, false, false, false, false},
		{false, false, false, false, false}},
		0, 100000, 300, 0),
	CoalGenerator(SurfaceTileIDs.CoalGenerator, true, true, true, new boolean[][]
		{{false, false, false, false, false},
		{false, false, false, false, false},
		{false, false, false, false, false},
		{false, false, true, false, false},
		{false, false, false, false, false}},
		3, 20000, 200, -30),
	Refiner(SurfaceTileIDs.Refiner, true, true, false, new boolean[][]
		{{true, true, false, true, true},
		{true, true, false, true, true},
		{false, false, false, false, false},
		{false, false, false, false, false},
		{false, false, false, false, false}},
		2, 30000, 200, 20),
	Cabinet(SurfaceTileIDs.Cabinet, false, false, false, new boolean[][]
		{{true, true, true, true, true},
		{true, true, true, true, true},
		{true, true, true, true, true},
		{true, true, true, true, true},
		{true, true, true, true, true}},
		0),
	ElectricFurnace(SurfaceTileIDs.ElectricFurnace, true, true, false, new boolean[][]
		{{true, true, false, true, true},
		{true, true, false, true, true},
		{false, false, false, false, false},
		{false, false, false, false, false},
		{false, false, false, false, false}},
		2, 30000, 200, 20),
	Acorn(SurfaceTileIDs.Acorn, false, false, false, new boolean[][]
		{{false, false, false, false, false},
		{false, false, false, false, false},
		{false, false, false, false, false},
		{false, false, false, false, false},
		{false, false, false, false, false}},
		2),
	Assembler(SurfaceTileIDs.Assembler, true, true, false, new boolean[][]
		{{false, false, false, false, false},
		{true, true, true, false, false},
		{true, true, true, false, true},
		{true, true, true, false, false},
		{false, false, false, false, false}},
		2, 50000, 500, 50),
	SolarPanel(SurfaceTileIDs.SolarPanel, true, true, false, new boolean[][]
			{{false, false, false, false, false},
			{false, false, false, false, false},
			{false, false, false, false, false},
			{false, false, false, false, false},
			{false, false, false, false, false}},
			0, 1000, 50, -1);
	
	public SurfaceTileIDs tileID;
	public boolean electronic;
	public boolean onOff;
	public boolean hasFlame;
	public boolean[][] validSlots;
	public int neededValues;
	public int baseMaxPower = 0;
	public short powerTransfer = 0;
	public short energyPerTick = 0;
	
	ProcessorIDs(SurfaceTileIDs tileID, boolean electronic, boolean onOff, boolean hasFlame, boolean[][] validSlots, int neededValues) {
		if (electronic) {
			System.out.println("Check ProcessorIDs. One of your electronics does not have enough parameters, so the wrong constructor is being used.");
		}
		this.tileID = tileID;
		this.electronic = electronic;
		this.onOff = onOff;
		this.hasFlame = hasFlame;
		this.validSlots = validSlots;
		this.neededValues = neededValues;
	}
	
	ProcessorIDs(SurfaceTileIDs tileID, boolean electronic, boolean onOff, boolean hasFlame, boolean[][] validSlots, int neededValues,
			int baseMaxPower, int powerTransfer, int energyPerTick) {
		this.tileID = tileID;
		this.electronic = electronic;
		this.onOff = onOff;
		this.hasFlame = hasFlame;
		this.validSlots = validSlots;
		this.neededValues = neededValues;
		this.baseMaxPower = baseMaxPower;
		this.powerTransfer = (short) powerTransfer;
		this.energyPerTick = (short) energyPerTick;
	}
	
	public static boolean containerExists(String containerName) {
		for (int i = 0; i < ProcessorIDs.values().length; i++) {
			if (ProcessorIDs.values()[i].toString() == containerName) {
				return true;
			}
		}
		return false;
	}
	
}
