package core;

public final class Identifications {
	
//	public static int[] getSurfaceTileSheet (short tileID) { //OLD CODE used to get the sprite sheets for the surface and cove dimensions
//		int[] tileSprite = new int[2];
//		if (tileID == 0) { //Blank tile
//			tileSprite[0] = 0;
//			tileSprite[1] = 0;
//		}
//		else if (tileID == 1) { //Boulder
//			tileSprite[0] = 1;
//			tileSprite[1] = 0;
//		}
//		else if (tileID == 2) { //Pebble
//			tileSprite[0] = 2;
//			tileSprite[1] = 0;
//		}
//		else if (tileID == 3) { //Tree
//			tileSprite[0] = 3;
//			tileSprite[1] = 0;
//		}
//		else if (tileID == 4) { //Wooden Wall
//			tileSprite[0] = 0;
//			tileSprite[1] = 1;
//		}
//		else if (tileID == 5) { //Different orientations of doors start here
//			tileSprite[0] = 1;
//			tileSprite[1] = 1;
//		}
//		else if (tileID == 6) {
//			tileSprite[0] = 2;
//			tileSprite[1] = 1;
//		}
//		else if (tileID == 7) {
//			tileSprite[0] = 3;
//			tileSprite[1] = 1;
//		}
//		else if (tileID == 8) { //Different orientations of doors ends here
//			tileSprite[0] = 0;
//			tileSprite[1] = 2;
//		}
//		else if (tileID == 9) { //Stone Table
//			tileSprite[0] = 1;
//			tileSprite[1] = 2;
//		}
//		else if (tileID == 10) { //Dirt
//			tileSprite[0] = 2;
//			tileSprite[1] = 2;
//		}
//		else if (tileID == 11) { //Sand
//			tileSprite[0] = 3;
//			tileSprite[1] = 2;
//		}
//		else if (tileID == 12) { //Water
//			tileSprite[0] = 0;
//			tileSprite[1] = 3;
//		}
//		else if (tileID == 13) { //Mine
//			tileSprite[0] = 1;
//			tileSprite[1] = 3;
//		}
//		else if (tileID == 14) { //Blast Furnace
//			tileSprite[0] = 2;
//			tileSprite[1] = 3;
//		}
//		else if (tileID == 15) { //Anvil
//			tileSprite[0] = 3;
//			tileSprite[1] = 3;
//		}
//		return tileSprite;
//	}
//	
//	public static int[] getCoveTileSheet (short tileID) {
//		int[] tileSprite = new int[2];
//		if (tileID == 0) { //Blank with no rock
//			tileSprite[0] = 0;
//			tileSprite[1] = 0;
//		}
//		else if (tileID == 1) { //Coal Rock
//			tileSprite[0] = 1;
//			tileSprite[1] = 0;
//		}
//		else if (tileID == 2) { //Iron Rock
//			tileSprite[0] = 2;
//			tileSprite[1] = 0;
//		}
//		else if (tileID == 3) { //Copper Rock
//			tileSprite[0] = 0;
//			tileSprite[1] = 1;
//		}
//		else if (tileID == 4) { //Gold Rock
//			tileSprite[0] = 1;
//			tileSprite[1] = 1;
//		}
//		else if (tileID == 5) { //Lithium Rock
//			tileSprite[0] = 2;
//			tileSprite[1] = 1;
//		}
//		else if (tileID == 6) { //Uranium Rock
//			tileSprite[0] = 0;
//			tileSprite[1] = 2;
//		}
//		else if (tileID == 7) { //Aluminum Rock
//			tileSprite[0] = 1;
//			tileSprite[1] = 2;
//		}
//		else if (tileID == 8) { //Topaz Gem
//			tileSprite[0] = 2;
//			tileSprite[1] = 2;
//		}
//		else if (tileID == 9) { //Mine
//			tileSprite[0] = 0;
//			tileSprite[1] = 3;
//		}
//		return tileSprite;
//	}
//	
//	public static boolean[][] getValidContainerSlots(int containerID) {
//		boolean[][] validSlots = new boolean[5][5];
//		
//		if (containerID == 1) {
//			boolean[][] slots = {{true, true, false, true, true},
//				{true, true, false, true, true},
//				{false, false, false, false, false},
//				{false, false, true, false, false},
//				{false, false, false, false, false}};
//			validSlots = slots;
//		}
//		return validSlots;
//	}
	
	public static boolean isDurabilityItem(short itemID) {
		if (itemID == 6 || itemID == 7 || itemID == 20) {
			return true;
		}
		return false;
	}
	
	public static String getDurabilityFileName(short itemID) {
		if (itemID == 6) {
			return "StonePick";
		}
		else if (itemID == 7) {
			return "StoneAxe";
		}
		else if (itemID == 20) {
			return "IronPick";
		}
		return null;
	}
	
}
