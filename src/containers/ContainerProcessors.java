package containers;

import java.awt.Graphics;

import core.Main_Game;

public abstract class ContainerProcessors {

	public abstract void tick();
	public abstract void render(Graphics g);

	transient public Main_Game game;

	protected ContainerIDs containerID; //The id that tells the type of container
	protected short ID; //The id that makes the container unique from the others of its kind
	protected String fileName;
	protected int[] loc = new int[4]; //Stored the tile Location in the order of (chunkX, chunkY, tileX, tileY
	protected short[] values; //Used to store various values that the container needs, the first value is always the On/Off state of the container
	protected boolean[][] validSlots; //Which slots the container actually uses
	protected short[][][] containerSlots = new short[5][5][2]; //The slots of the container

	protected ContainerProcessors(Main_Game game, short id, ContainerIDs containerID, boolean[][] validSlots, String fileName, byte neededValues, int chunkX, int chunkY, int tileX, int tileY) {
		this.game = game;
		setID(id);
		this.setContainerID(containerID);
		this.setValidSlots(validSlots);
		this.setFileName(fileName);
		setValues(new short[neededValues]);
		loc[0] = chunkX;
		loc[1] = chunkY;
		loc[2] = tileX;
		loc[3] = tileY;
		for (int k = 0; k < 2; k++) { //When a container is placed, all its slots will contain nothing.
			for  (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					containerSlots[i][j][k] = 0;
				}
			}
		}
		saveProcessor();
	}
	
	protected ContainerProcessors(String fileName) {
		this.fileName = fileName;
	}

	public boolean removeCheck(ContainerIDs containerID, int id) {
		if (this.getContainerID() == containerID && getID() == id) {
			return true;
		}
		return false;
	}

	public void saveProcessor() {
		//		for (int i = 1; i <= getValues().length; i++) {
		//			game.files.RewriteLine(getFileName(), i, ""+values[i-1]);
		//		}
		
		//game.files.saveContainer(this);
		
		CreateContainerFiles.createFile(fileName, this);
	}

	public void remove() {
		ContainerProcessHandler.removeProcessor(this);
	}

	public ContainerIDs getContainerID() {
		return containerID;
	}

	public void setContainerID(ContainerIDs containerID) {
		this.containerID = containerID;
	}

	public int getID() {
		return ID;
	}

	public void setID(short ID) {
		this.ID = ID;
	}

	public short[][][] getContainerSlots() {
		return containerSlots;
	}

	public void setContainerSlots(short[][][] containerSlots) {
		this.containerSlots = containerSlots;
	}

	public boolean[][] getValidSlots() {
		return validSlots;
	}

	public void setValidSlots(boolean[][] validSlots) {
		this.validSlots = validSlots;
	}

	public short[] getValues() {
		return values;
	}

	public void setValues(short[] values) {
		this.values = values;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int[] getLoc() { //This is a getter, but there is not setter cause the location never has to change.
		return loc;
	}
}
