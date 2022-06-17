package processors.management;

import tiles.SurfaceTileIDs;

public enum ProcessorIDs {
	NoContainer(SurfaceTileIDs.Blank, false, false, false),
	BlastFurnace(SurfaceTileIDs.BlastFurnace, false, true, true),
	CopperWire(SurfaceTileIDs.CopperWire, true, false, false),
	LESU(SurfaceTileIDs.LESU, true, false, false),
	CoalGenerator(SurfaceTileIDs.CoalGenerator, true, true, true),
	Refiner(SurfaceTileIDs.Refiner, true, true, false),
	Cabinet(SurfaceTileIDs.Cabinet, false, false, false),
	ElectricFurnace(SurfaceTileIDs.ElectricFurnace, true, true, false),
	Acorn(SurfaceTileIDs.Acorn, false, false, false);
	
	public SurfaceTileIDs tileID;
	public boolean electronic;
	public boolean onOff;
	public boolean hasFlame;
	
	ProcessorIDs(SurfaceTileIDs tileID, boolean electronic, boolean onOff, boolean hasFlame) {
		this.tileID = tileID;
		this.electronic = electronic;
		this.onOff = onOff;
		this.hasFlame = hasFlame;
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
