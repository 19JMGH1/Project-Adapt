package containers.electronics;

import java.util.LinkedList;

public class ElectronicHandler {
	
	public static LinkedList<Electronic> electronics = new LinkedList<Electronic>();
	
	public static int baseChunkAdjust(int x, int y, int k) {
		int chunk = k;
		if (x < 0) {
			chunk--;
		}
		else if (x > 15) {
			chunk++;
		}
		if (y < 0) {
			chunk -= 3;
		}
		else if (y > 15) {
			chunk += 3;
		}
		return chunk;
	}
	
	public static int bigChunkXAdjust(int tileX, int chunkX) {
		if (tileX < 0) {
			chunkX--;
		}
		else if (tileX > 15) {
			chunkX++;
		}
		return chunkX;
	}
	
	public static int bigChunkYAdjust(int tileY, int chunkY) {
		if (tileY < 0) {
			chunkY--;
		}
		else if (tileY > 15) {
			chunkY++;
		}
		return chunkY;
	}
	
	public static int tileXAdjust(int x) {
		if (x < 0) {
			x = 15;
		}
		else if (x > 15) {
			x = 0;
		}
		return x;
	}
	
	public static int tileYAdjust(int y) {
		if (y < 0) {
			y = 15;
		}
		else if (y > 15) {
			y = 0;
		}
		return y;
	}
}
