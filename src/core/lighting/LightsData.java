package core.lighting;

public class LightsData {
	
	public String lightName = "";
	public byte tileX; //Which tile in the x direction the light is located
	public byte tileY; //Which tile in the y direction the light is located
	public byte chunk; //This value ranges from 0 to 8. 0 is the top left loaded chunk, 8 is the bottom right loaded chunk.
	public byte lightLevel; //The light level produced at the tile that the light emitter is placed on.
	
	public LightsData(String lightName, byte tileX, byte tileY, byte chunkX, byte chunkY, byte lightLevel) {
		this.lightName = lightName;
		this.tileX = tileX;
		this.tileY = tileY;
		chunk = (byte) (chunkX + 3*chunkY);
		this.lightLevel = lightLevel;
	}
}
