package processors.electronics.management;

import java.util.Arrays;

import core.Main_Game;
import processors.management.Processors;

public abstract class Electronic extends Processors{ //The energy units that appear in the game will be Joules and Watts (Jules for power stored, watts for power transfer)

	protected int powerStored; //How much power is currently stored in the electronic. This is always stored after the on/off boolean or if one doesn't exist, then its stored on the first line
	protected boolean[] sendableDirections = {true, true, true, true}; //Up then down then left then right
	protected boolean[] inputSides = {true, true, true, true}; //Both of these I/O sides go up, down, left, right
	protected boolean[] outputSides = {true, true, true, true};
	protected int energyPerTick = 0;

	protected Electronic(Main_Game game, short id, String fileName, int chunkX, int chunkY, int tileX, int tileY) {
		super(game, id, fileName, chunkX, chunkY, tileX, tileY); //Needed values does not count the current power stored.
		powerStored = 0;
		energyPerTick = containerID.energyPerTick;
	}

	protected Electronic(String fileName) {
		super(fileName);
		energyPerTick = containerID.energyPerTick;
	}

	public void energyTransfer() {
		if (containerID.powerTransfer > 0) {
			for (int d = 0; d < ElectronicHandler.electronics.size(); d++) { //Loops through all the processors and if its electronic and if it's next to the current electronic, then transfer power
				Electronic otherElectronic = ElectronicHandler.electronics.get(d);
				if (otherElectronic.getContainerID().electronic) {
					int[][] adjacents = getAdjacents();
					for (int p = 0; p < 4; p++) {
						if (sendableDirections[p]) {
							if (checkTransferable(adjacents, p, otherElectronic)) {
								if (otherElectronic.getPowerStored() <= otherElectronic.getMaxPower()-containerID.powerTransfer) {
									if (powerStored <= containerID.powerTransfer) {
										otherElectronic.setPowerStored(otherElectronic.getPowerStored()+powerStored);
										powerStored = 0;
										otherElectronic.setSendableDirections(false, p);
									}
									else if (powerStored > containerID.powerTransfer) {
										otherElectronic.setPowerStored(otherElectronic.getPowerStored()+containerID.powerTransfer);
										powerStored -= containerID.powerTransfer;
										otherElectronic.setSendableDirections(false, p);
									}
								}
								else if (otherElectronic.getPowerStored() < otherElectronic.getMaxPower() && (powerStored >= containerID.powerTransfer)) {
									int energyDifference = otherElectronic.getMaxPower()-otherElectronic.getPowerStored();
									otherElectronic.setPowerStored(otherElectronic.getMaxPower());
									powerStored -= energyDifference;
									otherElectronic.setSendableDirections(false, p);
								}
							}
						}
					}
					for (int i = 0; i < 4; i++) {
						sendableDirections[i] = true;
					}
				}
			}
		}
		energyUse();
	}

	private void energyUse() {
		if (getValues().length != 0) {
			if (getValues()[0] == 1) {
				if (powerStored >= energyPerTick) {
					getValues()[1]--;
					powerStored -= energyPerTick;
				}
			}
		}
	}
	
	protected void energyGen(int gen) {
		if (getPowerStored() > (getMaxPower()+gen)) {
			setPowerStored(getMaxPower());
			setValues(0, (short) 0);
		}
		else {
			setPowerStored(getPowerStored()-gen);
		}
	}

	private int[][] getAdjacents() { //Returns adjacents in the order of one above, one below, one to the left, and one to the right
		//int[][] adjacents = new int[4][3];
		int[][] adjacents = {
				{loc[0], ElectronicHandler.bigChunkYAdjust(loc[3]-1, loc[1]), loc[2], ElectronicHandler.tileYAdjust(loc[3]-1)},
				{loc[0], ElectronicHandler.bigChunkYAdjust(loc[3]+1, loc[1]), loc[2], ElectronicHandler.tileYAdjust(loc[3]+1)},
				{ElectronicHandler.bigChunkXAdjust(loc[2]-1, loc[0]), loc[1], ElectronicHandler.tileXAdjust(loc[2]-1), loc[3]},
				{ElectronicHandler.bigChunkXAdjust(loc[2]+1, loc[0]), loc[1], ElectronicHandler.tileXAdjust(loc[2]+1), loc[3]}
		};
		return adjacents;
	}

	private boolean checkTransferable(int[][] adjacents, int p, Electronic otherElectronic) { //Checks to see if energy is allowed to be transfered between two electronics
		if (Arrays.equals(otherElectronic.getLoc(), adjacents[p])) {
			if (outputSides[p]) {
				int i = 0; //Variable to find which side of the other electronic that the energy is trying to enter through
				if (p == 0) {
					i = 1;
				}
				else if (p == 1) {
					i = 0;
				}
				else if (p == 2) {
					i = 3;
				}
				else if (p == 3) {
					i = 2;
				}
				if ((otherElectronic.inputSides[i])) {
					if ((!inputSides[p])) {
						return true;
					}
					else if (otherElectronic.inputSides[i] && (!otherElectronic.outputSides[i])) {
						return true;
					}
					else if (otherElectronic.inputSides[i] && otherElectronic.outputSides[i]) {
						if (((otherElectronic.powerStored)/(otherElectronic.containerID.baseMaxPower+0.0)) < ((powerStored)/(containerID.baseMaxPower+0.0))) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public int getPowerStored() {
		return powerStored;
	}

	public void setPowerStored(int newPower) {
		//System.out.println(newPower);
		powerStored = newPower;
	}

	public int getMaxPower() {
		return containerID.baseMaxPower;
	}

	public void setSendableDirections(boolean value, int p) {
		int i = 0;
		if (p == 0) {
			i = 1;
		}
		else if (p == 1) {
			i = 0;
		}
		else if (p == 2) {
			i = 3;
		}
		else if (p == 3) {
			i = 2;
		}
		sendableDirections[i] = value;
	}

	public boolean[] getInputSides() {
		return inputSides;
	}

	public boolean[] getOutputSides() {
		return outputSides;
	}

}
