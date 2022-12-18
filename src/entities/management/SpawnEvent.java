package entities.management;

import java.lang.reflect.InvocationTargetException;

import core.Main_Game;
import core.Main_Game.Dimensions;
import entities.Creature;
import entities.EntityTypes;
import entities.CreatureIDs;

public class SpawnEvent {

	private Main_Game game;

	private static final int spawnsPerSpawnEvent = 2; //This variable tells us how many creatures the spawn event will try to spawn.
	private static final int maxCreatures = 5;
	
	public SpawnEvent(Main_Game game) {
		this.game = game;
	}

	private boolean validBiome(int currBiome, int[] validBiomes) {
		for (int i : validBiomes) {
			if (i == currBiome) {
				return true;
			}
		}
		return false;
	}
	
	private boolean validDimension(Dimensions currentDim, Dimensions[] validDims) {
		for (Dimensions i : validDims) {
			if (i == currentDim) {
				return true;
			}
		}
		return false;
	}
	
	public void tick() {
		if ((game.daytimecycle.time%(10*Main_Game.Target_TPS) == 0)) {
			//System.out.println("Spawning...");
			for (int y = 0; y < spawnsPerSpawnEvent; y++) {
				Class<?> cla = null;
				int k = Main_Game.randomNum(0, 8);
				int i = Main_Game.randomNum(0, 15);
				int j = Main_Game.randomNum(-15, 0);
				if ((game.daytimecycle.transparency <= game.daytimecycle.getMaxDarkness()/2 || game.lightLevels[k][i][Math.abs(j)] >= 5) && (game.handler.creatures.size() <= maxCreatures)) { //Spawns mobs that spawn at day time
					CreatureIDs creature = CreatureIDs.values()[Main_Game.randomNum(0, CreatureIDs.values().length-1)];
					if (creature.isDaySpawn() && validBiome(game.StoredTiles[k][i][Math.abs(j)][0], creature.getBiomes()) && validDimension(game.dimension, creature.getSpawnDimensions())) {
						try {
							//System.out.println(pm.toString());
							cla = Class.forName("entities."+creature.toString());
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						Creature c = null;
						try {	
							//c = (Creature) new Pig(game, EntityTypes.Passive, 0, 0, k%3+game.ChunkX-1, k/3+game.ChunkY-1, i, j);
							c = (Creature) cla.getConstructors()[0].newInstance(game, EntityTypes.Passive, 0, 0, k%3+game.ChunkX-1, k/3+game.ChunkY-1, i, j);
							//System.out.println("He is spawned at:");
							//System.out.println(k%3+", "+k/3+", "+i+", "+j);
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException | SecurityException e1) {
							e1.printStackTrace();
						}
						game.handler.addCreature(c);
					}
				}
				if ((game.daytimecycle.transparency > game.daytimecycle.getMaxDarkness()/2 && game.lightLevels[k][i][Math.abs(j)] < 5) && (game.handler.creatures.size() <= maxCreatures)) { //Spawns mobs that spawn at night
					CreatureIDs creature = CreatureIDs.values()[Main_Game.randomNum(0, CreatureIDs.values().length-1)];
					if (creature.isNightSpawn() && validBiome(game.StoredTiles[k][i][Math.abs(j)][0],creature.getBiomes())) {
						try {
							//System.out.println(pm.toString());
							cla = Class.forName("entities."+creature.toString());
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						Creature c = null;
						try {	
							//c = (Creature) new Pig(game, EntityTypes.Passive, 0, 0, k%3+game.ChunkX-1, k/3+game.ChunkY-1, i, j);
							c = (Creature) cla.getConstructors()[0].newInstance(game, EntityTypes.Passive, 0, 0, k%3+game.ChunkX-1, k/3+game.ChunkY-1, i, j);
							//System.out.println("He is spawned at:");
							//System.out.println(k%3+", "+k/3+", "+i+", "+j);
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException | SecurityException e1) {
							e1.printStackTrace();
						}
						game.handler.addCreature(c);
					}
				}
			}
		}
	}
}
