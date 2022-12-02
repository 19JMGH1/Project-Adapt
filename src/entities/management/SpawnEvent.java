package entities.management;

import java.lang.reflect.InvocationTargetException;

import core.Main_Game;
import entities.Creature;
import entities.EntityTypes;
import entities.PassiveMobIDs;

public class SpawnEvent {

	private Main_Game game;

	private static final int spawnsPerSpawnEvent = 2; //This variable tells us how many creatures the spawn event will try to spawn.

	public SpawnEvent(Main_Game game) {
		this.game = game;
	}

	public void tick() {
		if ((game.daytimecycle.time%(10*Main_Game.Target_TPS) == 0)) {
			//System.out.println("Spawning...");
			for (int y = 0; y < spawnsPerSpawnEvent; y++) {
				Class<?> cla = null;
				int k = Main_Game.randomNum(0, 8);
				int i = Main_Game.randomNum(0, 15);
				int j = Main_Game.randomNum(-15, 0);
				if ((game.daytimecycle.transparency == 0 || game.lightLevels[k][i][Math.abs(j)] >= 5) && (game.handler.creatures.size() <= 3)) { //Spawns a passive mob at day time
					PassiveMobIDs pm = PassiveMobIDs.values()[Main_Game.randomNum(0, PassiveMobIDs.values().length-1)];
					if (game.StoredTiles[k][i][Math.abs(j)][0] == pm.getBiome()) {
						try {
							//System.out.println(pm.toString());
							cla = Class.forName("entities."+pm.toString());
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
