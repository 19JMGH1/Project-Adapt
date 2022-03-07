package containers;

import java.awt.Graphics;
import java.util.LinkedList;

import containers.electronics.CoalGenerator;
import containers.electronics.CopperWire;
import containers.electronics.Electronic;
import containers.electronics.ElectronicHandler;
import containers.electronics.LESU;
import containers.electronics.Refiner;
import core.Main_Game;

public class ContainerProcessHandler implements Runnable{

	public static LinkedList<ContainerProcessors> processors = new LinkedList<ContainerProcessors>();

	private Main_Game game;

	public Thread thread3;
	public boolean running = false;

	public boolean reload = false;
	public boolean reloading = false;

	public ContainerProcessHandler(Main_Game game) {
		this.game = game;
	}

	public synchronized void start() {
		//System.out.println(HEIGHT+", "+WIDTH);
		running = true;
		thread3 = new Thread(this);
		thread3.start();
		System.out.println("Third thread started");
	}

	public void run() {
		running = true;
		long now;
		long updateTime;
		long wait;
		final int TARGET_FPS = 30;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
		while (running) {
			now = System.nanoTime();
			if (reload) {
				reloadProcessors();
				reload = false;
			}
			updateTime = System.nanoTime() - now;
			wait = (OPTIMAL_TIME - updateTime) / 1000000;
			if (wait > 0)
			{
				try {
					Thread.sleep(wait);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//System.out.println("FPS: "+(1000/wait));
			//System.out.println(wait);
		}
		//System.out.println("Third thread is dead");
	}

	public void reloadProcessors() {
		if (!reloading) {
			reloading = true;
			saveAllProcessors();
			removeAllProcessors();
			for (int k = 0; k < 9; k++) {
				for (int j = 0; j < 16; j++) {
					for (int i = 0; i < 16; i++) {
						for (int g = 0; g < ContainerIDs.values().length; g++) {
							if (game.StoredTiles[k][j][i][0] == ContainerIDs.values()[g].tileID.ordinal()) {
								if (ContainerIDs.values()[g] == ContainerIDs.BlastFurnace) { //This list of else ifs initialize the containers
									//processors.add(new BlastFurnace(game, game.StoredTiles[k][j][i][1], k, j, i));
									BlastFurnace bf = (BlastFurnace) CreateContainerFiles.getContainer("Files/File "+game.CurrentFile+"/Tiles/BlastFurnace "+game.StoredTiles[k][j][i][1]+".txt", ContainerIDs.BlastFurnace);
									addNormalContainer(bf);
								}
								else if (ContainerIDs.values()[g] == ContainerIDs.CopperWire) {
									//CopperWire copperwire = new CopperWire(game, game.StoredTiles[k][j][i][1], k, j, i);
									
									CopperWire cw = (CopperWire) CreateContainerFiles.getContainer("Files/File "+game.CurrentFile+"/Tiles/CopperWire "+game.StoredTiles[k][j][i][1]+".txt", ContainerIDs.CopperWire);
									addElectronicContainer(cw);
								}
								else if (ContainerIDs.values()[g] == ContainerIDs.LESU) {
									//LESU lesu = new LESU(game, game.StoredTiles[k][j][i][1], k, j, i);
									
									LESU lesu = (LESU) CreateContainerFiles.getContainer("Files/File "+game.CurrentFile+"/Tiles/LESU "+game.StoredTiles[k][j][i][1]+".txt", ContainerIDs.LESU);
									addElectronicContainer(lesu);
								}
								else if (ContainerIDs.values()[g] == ContainerIDs.CoalGenerator) {
									//CoalGenerator coalgenerator = new CoalGenerator(game, game.StoredTiles[k][j][i][1], k, j, i);
									
									CoalGenerator cg = (CoalGenerator) CreateContainerFiles.getContainer("Files/File "+game.CurrentFile+"/Tiles/CoalGenerator "+game.StoredTiles[k][j][i][1]+".txt", ContainerIDs.CoalGenerator);
									addElectronicContainer(cg);
								}
								else if (ContainerIDs.values()[g] == ContainerIDs.Refiner) {
									//Refiner refiner = new Refiner(game, game.StoredTiles[k][j][i][1], k, j, i);

									Refiner refiner = (Refiner) CreateContainerFiles.getContainer("Files/File "+game.CurrentFile+"/Tiles/Refiner "+game.StoredTiles[k][j][i][1]+".txt", ContainerIDs.Refiner);
									addElectronicContainer(refiner);
								}
								else if (ContainerIDs.values()[g] == ContainerIDs.Cabinet) {
									Cabinet c = (Cabinet) CreateContainerFiles.getContainer("Files/File "+game.CurrentFile+"/Tiles/Cabinet "+game.StoredTiles[k][j][i][1]+".txt", ContainerIDs.Cabinet);
									addNormalContainer(c);
								}
							}
						}
					}
				}
			}
			reloading = false;
		}
	}
	
	private void addNormalContainer(ContainerProcessors c) {
		c.game = game;
		processors.add(c);
	}
	
	private void addElectronicContainer(ContainerProcessors c) {
		c.game = game;
		processors.add(c);
		ElectronicHandler.electronics.add((Electronic) c);
	}

	public void tick() {
		for (int i = 0; i < processors.size(); i++) {
			processors.get(i).tick();
		}
	}
	public void render(Graphics g) {
		for (int i = 0; i < processors.size(); i++) {
			processors.get(i).render(g);
		}
	}

	public static void removeProcessor(ContainerProcessors processor) {
		if (processor.containerID.electronic) {
			ElectronicHandler.electronics.remove(processor);
		}
		processors.remove(processor);
	}

	public ContainerProcessors getProcessor(ContainerIDs containerID, int id) {
		int size = processors.size();
		for (int i = 0; i < size; i++) {
			ContainerProcessors c = processors.get(i);
			if (c.getContainerID() == containerID && c.getID() == id) {
				return c;
			}
		}
		return null;
	}

	public void saveAllProcessors() {
		int size = processors.size();
		for (int i = 0; i < size; i++) {
			processors.get(i).saveProcessor();
		}
	}

	public void removeAllProcessors() {
		saveAllProcessors();
		int CurrentNumberOfProcessors = processors.size();
		int CurrentNumberOfElectronics = ElectronicHandler.electronics.size();
		for (int i = 0; i < CurrentNumberOfProcessors; i++) {
			processors.remove(CurrentNumberOfProcessors-i-1);
		}
		for (int i = 0; i < CurrentNumberOfElectronics; i++) {
			ElectronicHandler.electronics.remove(CurrentNumberOfElectronics-i-1);
		}
	}

	public void removeProcessor(ContainerIDs containerID, int id) {
		int size = processors.size();
		for (int i = 0; i < size; i++) {
			ContainerProcessors c = processors.get(i);
			if (c.removeCheck(containerID, id)) {
				ContainerProcessors Object = processors.get(i);
				Object.saveProcessor();
				processors.remove(Object);
				break;
			}
		}
	}
}
