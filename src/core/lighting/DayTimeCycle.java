package core.lighting;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import core.Main_Game;
import core.Main_Game.Dimensions;

public class DayTimeCycle {

	private Main_Game game;

	public static LinkedList<LightsData> lightSources = new LinkedList<LightsData>(); //Stores all of the light sources that are in the world
	public static boolean reloadNeeded = false;

	private Color[] lightLevelsColors = new Color[Main_Game.MAX_LIGHT_LEVEL]; //Stores the colors to put on the screen for each light level

	private final int dayTime = 300; //how long a day or night lasts in seconds (my chosen default time is 5 minutes)
	private final int darkSpeed = 5; //how quickly the darkness if night comes through (lower numbers mean quicker time)
	private final int maxDarkness = 100; //How dark does it get at night (value can be between 1 and 150)
	public int time = 0; //Determines the current time
	public int transparency = 0; //Holds the value for how transparent the box of darkness is. 0 means its day, higher numbers mean it's darker.

	public DayTimeCycle(Main_Game game) {
		this.game = game;
		//lightSources.add(new LightsData((byte) 7,(byte) -7, (byte) 1, (byte) 1, (byte) 10));
		//lightSources.add(new LightsData((byte) 7,(byte) -9, (byte) 1, (byte) 1, (byte) 5));
		for (int i = 0; i < lightLevelsColors.length; i++) {
			lightLevelsColors[i] = new Color(20, 20, 20, Math.max(0, transparency-(i*(transparency/Main_Game.MAX_LIGHT_LEVEL))));
		}
	}

	/**
	 * Clears the array of light sources but does not empty the game.lightLevels array.
	 */
	public void removeLightSources() {
		lightSources.clear();
	}

	/**
	 * Removes a light source at a specified location in your 3 by 3 chunk space.
	 */
	public void removeLightSource(byte chunk, byte tileX, byte tileY) {
		int i = 0;
		int toBeRemoved = -1;
		for (LightsData light : DayTimeCycle.lightSources) {
			if (light.chunk == chunk && light.tileX == tileX && light.tileY == tileY) {
				toBeRemoved = i;
				break;
			}
			i++;
		}
		if (toBeRemoved >= 0) {
			DayTimeCycle.lightSources.remove(toBeRemoved);
			wipeAndReload();
		}
	}

	/**
	 * Clears the array of light sources and empties the game.lightLevels array.
	 */
	public void clearLights() {
		lightSources.clear();
		clearLightLevels();
	}

	/**
	 * Empties the game.lightLevels array but does not clear the array of light sources.
	 */
	public void clearLightLevels() {
		for (int k = 0; k < 9; k++) {
			for (int j = 0; j < 16; j++) {
				for (int i = 0; i < 16; i++) {
					game.lightLevels[k][i][j] = 0;
				}
			}
		}
	}

	public int getTime() {
		return time;
	}

	public void tick() {
		if (game.resized == true) {

		}

		if (reloadNeeded) {
			//wipeAndReload();
			reloadLights();
			reloadNeeded = false;
		}

		time++;
		transparency = getTransparency(time);
		if (transparency >= getMaxDarkness()) {
			transparency = getMaxDarkness();
		}
		//Adjusts the color for each light level
		for (int i = 0; i < lightLevelsColors.length; i++) {
			lightLevelsColors[i] = new Color(20, 20, 20, Math.max(0, transparency-(i*(transparency/Main_Game.MAX_LIGHT_LEVEL))));
			//The line below was for debugging
			//System.out.println("Transparency for light level "+i+" is: "+(transparency-(i*(transparency/Main_Game.MAX_LIGHT_LEVEL))));
		}
		
//		System.out.println(lightSources.size());
//		for (LightsData ld : lightSources) {
//			System.out.println(ld.lightName);
//		}
		
	}

	private int getTransparency(int time) {
		if (time <= Main_Game.Target_TPS*dayTime) {
			return 0;
		}
		else if (time > Main_Game.Target_TPS*dayTime*2) {
			if (transparency == 0) {
				this.time = 0;
				return 0;
			}
			return (getMaxDarkness()-(time-Main_Game.Target_TPS*dayTime*2)/darkSpeed);
		}
		else if (transparency >= getMaxDarkness()) {
			return getMaxDarkness();
		}
		else if (time > Main_Game.Target_TPS*dayTime) {
			return ((time-Main_Game.Target_TPS*dayTime)/darkSpeed);
		}
		return (getMaxDarkness() - time);
	}

	/**
	 * Wipes the game.lightLevels array, then reloads all the lights.
	 */
	public void wipeAndReload() {
		clearLightLevels();
		reloadLights();
	}

	private void reloadLights() {
		for (LightsData ld : lightSources) {
			reloadIndividualLightSource(ld);
		}
	}

	public void reloadIndividualLightSource(LightsData ld) {
		setLight(ld.lightLevel, ld.tileX, ld.tileY, ld.chunk);
		//		The lines below were for debugging
		//		for (int k = 0; k < 9; k++) {
		//			for (int j = 0; j < 16; j++) {
		//				for (int i = 0; i < 16; i++) {
		//					System.out.println(game.lightLevels[k][i][j]);
		//				}
		//			}
		//		}
	}

	public void setLight(byte lightLevel, byte tileX, byte tileY, byte chunk) {
		if (chunk < 0 || chunk > 8 || lightLevel <= 0) {
			return;
		}
		if (game.lightLevels[chunk][tileX][Math.abs(tileY)] >= lightLevel) {
			return;
		}
		game.lightLevels[chunk][tileX][Math.abs(tileY)] = lightLevel;
		//The line below was just for testing
		//System.out.println("Light level at ("+tileX+", "+tileY+") in chunk "+chunk+" is now: "+lightLevel);
		byte nextTileX = tileXAdjust((byte) (tileX-1));
		byte nextTileY = tileY;
		byte nextChunk = chunk;
		nextChunk = chunkXAdjust(nextChunk, (byte) (tileX-1));
		setLight((byte) (lightLevel-1), nextTileX, nextTileY, nextChunk);
		nextTileX = tileXAdjust((byte) (tileX+1));
		nextTileY = tileY;
		nextChunk = chunk;
		nextChunk = chunkXAdjust(nextChunk, (byte) (tileX+1));
		setLight((byte) (lightLevel-1), nextTileX, nextTileY, nextChunk);
		nextTileX = tileX;
		nextTileY = tileYAdjust((byte) (tileY-1));
		nextChunk = chunk;
		nextChunk = chunkYAdjust(nextChunk, (byte) (tileY-1));
		setLight((byte) (lightLevel-1), nextTileX, nextTileY, nextChunk);
		nextTileX = tileX;
		nextTileY = tileYAdjust((byte) (tileY+1));
		nextChunk = chunk;
		nextChunk = chunkYAdjust(nextChunk, (byte) (tileY+1));
		setLight((byte) (lightLevel-1), nextTileX, nextTileY, nextChunk);
	}

	private byte chunkXAdjust(byte chunk, byte tileX)
	{
		if (tileX < 0)
		{
			chunk--;
		}
		else if (tileX > 15)
		{
			chunk++;
		}
		return chunk;
	}

	private byte chunkYAdjust(byte chunk, byte tileY)
	{
		if (tileY > 0)
		{
			chunk -= 3;
		}
		else if (tileY < -15)
		{
			chunk += 3;
		}
		return chunk;
	}

	private byte tileXAdjust(byte tileX)
	{
		if (tileX > 15)
		{
			tileX -= 16;
		}
		else if (tileX < 0)
		{
			tileX += 16;
		}
		return tileX;
	}

	private byte tileYAdjust(byte tileY)
	{
		if (tileY > 0)
		{
			tileY -= 16;
		}
		else if (tileY < -15)
		{
			tileY += 16;
		}
		return tileY;
	}

	public void render(Graphics g) {
		g.setColor(new Color(20, 20, 20, transparency));

		int x = (Main_Game.WIDTH/2)-game.CharacterWidth/2;
		int y = (Main_Game.HEIGHT/2)-game.CharacterHeight/2;

		if (game.dimension == Dimensions.surface) { //Lower light levels only appear in the surface dimension
			for (int k = 0; k < 9; k++) {
				int relativeChunkX = k%3;
				int relativeChunkY = k/3;
				for (int i = 0; i < 16; i++) {
					for (int j = 0; j < 16; j++) {
						g.setColor(lightLevelsColors[game.lightLevels[k][i][j]]);
						g.fillRect(x+(game.TileWidth*i)-(game.TileX*game.TileWidth)-(game.TileWidth*16)-game.x+(relativeChunkX*game.TileWidth*16), y+(game.TileHeight*j)+(game.TileY*game.TileHeight)-(game.TileHeight*16)+game.y+(relativeChunkY*game.TileHeight*16), game.TileWidth, game.TileHeight);
					}
				}
			}
		}
		//g2d.fill(darkness);
	}

	public int getMaxDarkness() {
		return maxDarkness;
	}

}
