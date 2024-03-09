package processors.management;

import java.awt.Graphics;
import java.util.LinkedList;

import core.Main_Game;
import core.lighting.DayTimeCycle;
import processors.electronics.management.Electronic;
import processors.electronics.management.ElectronicHandler;

public class ProcessHandler implements Runnable {

	public static LinkedList<Processors> processors = new LinkedList<Processors>(); //TODO change this to ArrayList

	private Main_Game game;

	public Thread thread3;
	public boolean running = false;

	public boolean reload = false;
	public boolean reloading = false;

	public ProcessHandler(Main_Game game) {
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
		try {
			Thread.sleep(250); //Sleep before starting the game loop to give the window time to get situated.
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			//System.out.println("Processors: "+wait);
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
						for (int g = 1; g < ProcessorIDs.values().length; g++) { //g starts at 1 to skip the NoContainer in the ContainerIDs enum
							if (game.StoredTiles[k][j][i][0] == ProcessorIDs.values()[g].tileID.ordinal()) {
								Class<?> cla = null;
								try {
									if (ProcessorIDs.values()[g].electronic) {
										cla = Class.forName("processors.electronics."+ProcessorIDs.values()[g].toString());
									}
									else {
										cla = Class.forName("processors."+ProcessorIDs.values()[g].toString());
									}
								} catch (ClassNotFoundException e) {
									e.printStackTrace();
								}
								Processors cp = (Processors) cla.cast(CreateProcessorsFiles.getContainer("Files/File "+game.CurrentFile+"/Tiles/"+ProcessorIDs.values()[g].toString()+" "+game.StoredTiles[k][j][i][1]+".txt", ProcessorIDs.values()[g]));
								if (!ProcessorIDs.values()[g].electronic) {
									addNormalContainer(cp);
								}
								else {
									addElectronicContainer(cp);
								}
	//Below is the old system for adding objects to the container processors linked list
//								if (ContainerIDs.values()[g] == ContainerIDs.BlastFurnace) { //This list of else ifs initialize the containers
//									//processors.add(new BlastFurnace(game, game.StoredTiles[k][j][i][1], k, j, i));
//									BlastFurnace bf = (BlastFurnace) CreateContainerFiles.getContainer("Files/File "+game.CurrentFile+"/Tiles/BlastFurnace "+game.StoredTiles[k][j][i][1]+".txt", ContainerIDs.BlastFurnace);
//									addNormalContainer(bf);
//								}
//								else if (ContainerIDs.values()[g] == ContainerIDs.CopperWire) {
//									//CopperWire copperwire = new CopperWire(game, game.StoredTiles[k][j][i][1], k, j, i);
//									
//									CopperWire cw = (CopperWire) CreateContainerFiles.getContainer("Files/File "+game.CurrentFile+"/Tiles/CopperWire "+game.StoredTiles[k][j][i][1]+".txt", ContainerIDs.CopperWire);
//									addElectronicContainer(cw);
//								}
//								else if (ContainerIDs.values()[g] == ContainerIDs.LESU) {
//									//LESU lesu = new LESU(game, game.StoredTiles[k][j][i][1], k, j, i);
//									
//									LESU lesu = (LESU) CreateContainerFiles.getContainer("Files/File "+game.CurrentFile+"/Tiles/LESU "+game.StoredTiles[k][j][i][1]+".txt", ContainerIDs.LESU);
//									addElectronicContainer(lesu);
//								}
//								else if (ContainerIDs.values()[g] == ContainerIDs.CoalGenerator) {
//									//CoalGenerator coalgenerator = new CoalGenerator(game, game.StoredTiles[k][j][i][1], k, j, i);
//									
//									CoalGenerator cg = (CoalGenerator) CreateContainerFiles.getContainer("Files/File "+game.CurrentFile+"/Tiles/CoalGenerator "+game.StoredTiles[k][j][i][1]+".txt", ContainerIDs.CoalGenerator);
//									addElectronicContainer(cg);
//								}
//								else if (ContainerIDs.values()[g] == ContainerIDs.Refiner) {
//									//Refiner refiner = new Refiner(game, game.StoredTiles[k][j][i][1], k, j, i);
//
//									Refiner refiner = (Refiner) CreateContainerFiles.getContainer("Files/File "+game.CurrentFile+"/Tiles/Refiner "+game.StoredTiles[k][j][i][1]+".txt", ContainerIDs.Refiner);
//									addElectronicContainer(refiner);
//								}
//								else if (ContainerIDs.values()[g] == ContainerIDs.Cabinet) {
//									Cabinet c = (Cabinet) CreateContainerFiles.getContainer("Files/File "+game.CurrentFile+"/Tiles/Cabinet "+game.StoredTiles[k][j][i][1]+".txt", ContainerIDs.Cabinet);
//									addNormalContainer(c);
//								}
//								else if (ContainerIDs.values()[g] == ContainerIDs.ElectricFurnace) {
//									ElectricFurnace ef = (ElectricFurnace) CreateContainerFiles.getContainer("Files/File "+game.CurrentFile+"/Tiles/ElectricFurnace "+game.StoredTiles[k][j][i][1]+".txt", ContainerIDs.ElectricFurnace);
//									addElectronicContainer(ef);
//								}
							}
						}
					}
				}
			}
			if (game.curentlyOpenedContainer != null) {
				System.out.println("Testing");
				game.curentlyOpenedContainer = game.processhandler.getProcessor(game.curentlyOpenedContainer.containerID, game.StoredTiles[4+((game.curentlyOpenedContainer.loc[0]-game.ChunkX)+(game.ChunkY-game.curentlyOpenedContainer.loc[1])*3)][game.curentlyOpenedContainer.loc[2]][Math.abs(game.curentlyOpenedContainer.loc[3])][1]);
			}
			DayTimeCycle.reloadNeeded = true;
			reloading = false;
		}
		
	}
	
	private void addNormalContainer(Processors c) {
		c.game = game;
		processors.add(c);
	}
	
	private void addElectronicContainer(Processors c) {
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

	public void removeProcessor(Processors processor) {
		//game.daytimecycle.removeLight(processor.getContainerID().toString()+processor.ID);
		if (processor.containerID.electronic) {
			ElectronicHandler.electronics.remove(processor);
		}
		processors.remove(processor);
	}

	public Processors getProcessor(ProcessorIDs containerID, int id) {
		int size = processors.size();
		for (int i = 0; i < size; i++) {
			Processors c = processors.get(i);
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

	public void removeProcessor(ProcessorIDs containerID, int id) {
		int size = processors.size();
		for (int i = 0; i < size; i++) {
			Processors c = processors.get(i);
			if (c.removeCheck(containerID, id)) {
				Processors Object = processors.get(i);
				Object.saveProcessor();
				processors.remove(Object);
				break;
			}
		}
	}
}
