package entities;

public enum PassiveMobIDs {
	Pig(0);
	
	private int biome;
	
	PassiveMobIDs(int biome) {
		this.setBiome(biome);
	}

	public int getBiome() {
		return biome;
	}

	public void setBiome(int biome) {
		this.biome = biome;
	}
}
