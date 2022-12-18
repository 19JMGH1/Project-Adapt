package entities;

import core.Main_Game.Dimensions;

public enum CreatureIDs {
	
	Pig(new int[] {0}, new Dimensions[] {Dimensions.surface}, true, false);
	
	private int[] spawnBiomes;
	private Dimensions[] spawnDimensions = null;
	private boolean daySpawn;
	private boolean nightSpawn;
	
	CreatureIDs(int[] biome, Dimensions[] dims, boolean daySpawn, boolean nightSpawn) {
		setSpawnDimensions(dims);
		setBiomes(biome);
		this.daySpawn = daySpawn;
		this.nightSpawn = nightSpawn;
	}

	public int[] getBiomes() {
		return spawnBiomes;
	}

	public void setBiomes(int[] biome) {
		this.spawnBiomes = biome;
	}

	public boolean isDaySpawn() {
		return daySpawn;
	}

	public boolean isNightSpawn() {
		return nightSpawn;
	}

	public Dimensions[] getSpawnDimensions() {
		return spawnDimensions;
	}

	public void setSpawnDimensions(Dimensions[] spawnDimensions) {
		this.spawnDimensions = spawnDimensions;
	}
}
