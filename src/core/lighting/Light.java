package core.lighting;

public interface Light { //Any light source will implement this interface.
	
	default void addLight(String lightName, byte lightLevel, byte existingLightLevel, byte chunkX, byte chunkY, byte tileX, byte tileY) {
		//The parameter "existingLightLevel" is the light level that already exists on the tile that we are trying to add a light source to
		//System.out.println("Trying to add light");
		LightsData l = new LightsData(lightName, tileX, tileY, chunkX, chunkY, lightLevel);
		for (LightsData ld : DayTimeCycle.lightSources) {
			if (ld.lightName.equals(l.lightName)) {
				return;
			}
		}
		DayTimeCycle.lightSources.add(l);
		DayTimeCycle.reloadNeeded = true;
		//System.out.println("Light added");
	}
	
}
