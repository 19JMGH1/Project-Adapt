package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import processors.electronics.management.Electronic;
import processors.management.ProcessorIDs;
import processors.management.Processors;
import tiles.SurfaceTileIDs;

public class Files {

	private Main_Game game;

	public int NumberOfFiles = 0;

	public Files(Main_Game game) {
		this.game = game;
		CreateSettingsFile();
	}

	public void CreateSettingsFile() {
		File settingsDir = new File("Settings.txt");
		try {
			if (!settingsDir.exists()) {
				PrintWriter settingsFile = new PrintWriter(settingsDir);
				settingsFile.println(0);
				settingsFile.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void CreateFile(String fileName) {
		int i = 1;
		File WorldFile = new File("Files/File "+i);
		File ChunkFile = new File("Files/File "+i+"/Chunks");
		File InventoryFile = new File("Files/File "+i+"/Inventory");
		File Entities = new File(WorldFile+"/Entities");
		File tilesFile = new File(WorldFile+"/Tiles");
		File DataTxt = new File(WorldFile+"/Data.txt");
		File InvTxt = new File(WorldFile+"/Inventory/Inv.txt");
		while (WorldFile.exists()) {
			i++;
			WorldFile = new File("Files/File "+i);
			InventoryFile = new File("Files/File "+i+"/Inventory");
			tilesFile = new File(WorldFile+"/Tiles");
			DataTxt = new File(WorldFile+"/Data.txt");
			Entities = new File(WorldFile+"/Entities");
			InvTxt = new File(WorldFile+"/Inventory/Inv.txt");
		}
		game.CurrentFile = i;
		try {
			CreateDirs(WorldFile);
			CreateDirs(ChunkFile);
			CreateDirs(InventoryFile);
			CreateDirs(tilesFile);
			CreateDirs(Entities);
			PrintWriter DataTxtWriter = new PrintWriter(DataTxt);
			PrintWriter InvTxtWriter = new PrintWriter(InvTxt);
			DataTxtWriter.println(fileName); //File Name
			if (game.filemenu.seed == null) {
				Random rand = new Random();
				game.seed = rand.nextInt();
			}
			else {
				game.seed = game.filemenu.seed;
				game.filemenu.seed = null;
			}
			System.out.println(game.seed);
			DataTxtWriter.println(game.seed); //Seed
			DataTxtWriter.println(8); //Tile X
			DataTxtWriter.println(-8); //Tile Y
			DataTxtWriter.println(0); //Chunk X
			DataTxtWriter.println(0); //Chunk Y
			DataTxtWriter.println(0); //Crafting Level
			DataTxtWriter.println(0); //Starting on the surface dimension
			
			for (int j = 0; j < 30; j++) //This for loop prints the inventory and makes sure nothing is in it
			{
				DataTxtWriter.println(0); //Extra slots in the data file for future needed data
				InvTxtWriter.println(0+" "+0); //Prints an empty inventory
			}
			DataTxtWriter.close();
			InvTxtWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		File directory = new File("Files/File "+game.CurrentFile+"/Chunks");
		if (directory.exists() == false) {
			directory.mkdirs();
		}
		directory = new File("Files/File "+game.CurrentFile+"/Mine Chunks");
		if (directory.exists() == false) {
			directory.mkdirs();
		}
		game.GenWorld(); //Generates the first 9 chunks of the world
	}

	public void CreateDirs(File dir) {
		dir.mkdirs();
	}

	public void DeleteFile(int fileNumber) {
		try {
			//deletes the chunk file and everything inside it
			File ChunkIndex = new File("Files/File "+fileNumber+"/Chunks");
			String[]ChunkEntries = ChunkIndex.list();
			for(String s: ChunkEntries) {
				File currentFile = new File(ChunkIndex.getPath(),s);
				currentFile.delete();
			}
			ChunkIndex.delete();

			File mineChunkIndex = new File("Files/File "+fileNumber+"/Mine Chunks");
			String[]mineChunkEntries = mineChunkIndex.list();
			for(String s: mineChunkEntries) {
				File currentFile = new File(mineChunkIndex.getPath(),s);
				currentFile.delete();
			}
			mineChunkIndex.delete();

			File InventoryIndex = new File("Files/File "+fileNumber+"/Inventory");
			String[]InventoryEntries = InventoryIndex.list();
			for(String s: InventoryEntries) {
				File currentFile = new File(InventoryIndex.getPath(),s);
				currentFile.delete();
			}
			InventoryIndex.delete();

			File TilesIndex = new File("Files/File "+fileNumber+"/Tiles");
			String[]TileEntries = TilesIndex.list();
			for(String s: TileEntries) {
				File currentFile = new File(TilesIndex.getPath(),s);
				currentFile.delete();
			}
			TilesIndex.delete();

			//deletes all the other text files in the file
			File TextIndex = new File("Files/File "+fileNumber);
			String[]TextEntries = TextIndex.list();
			for(String s: TextEntries) {
				File currentFile = new File(TextIndex.getPath(),s);
				currentFile.delete();
			}
			TextIndex.delete();

			File renamed = new File("Files/File "+(fileNumber+1));
			//System.out.println(renamed);
			int i = 0;
			File renamedTo = new File("Files/File "+(fileNumber+i));
			while(renamed.exists()) {
				renamedTo = new File("Files/File "+(fileNumber+i));
				//System.out.println(renamedTo);
				if(renamed.renameTo(renamedTo)) {
					//System.out.println("renamed!");
				}
				else {
					System.out.println("unable to rename");
				}
				i++;
				renamed = new File("Files/File "+(fileNumber+i+1));
				//System.out.println(renamed);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteTextFile(String fileDirectory) {
		File del = new File(fileDirectory);
		if (del.exists()) {
			del.delete();
		}
		else {
			System.out.println("A file could not be deleted because it doesn't exist");
		}
	}

	public void RewriteLine(String fileName, int lineNumber, String newLine) {
		try {
			BufferedReader file = new BufferedReader(new FileReader(fileName));
			StringBuffer inputBuffer = new StringBuffer(512);
			String line;
			int i = 0;
			while ((line = file.readLine()) != null) {
				i++;
				if (i == lineNumber) {
					inputBuffer.append(newLine);
				}
				else {
					inputBuffer.append(line);
				}
				inputBuffer.append('\n');
			}
			while (i < lineNumber) {
				i++;
				if (i == lineNumber) {
					inputBuffer.append(newLine);
				}
				else {
					inputBuffer.append("0");
				}
				inputBuffer.append('\n');
			}
			file.close();
			String inputStr = inputBuffer.toString();
			FileOutputStream fileOut = new FileOutputStream(fileName);
			fileOut.write(inputStr.getBytes());
			fileOut.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String ReadLine(String fileName, int lineNumber) {
		String returnedRead = null;
		try {
			BufferedReader file = new BufferedReader(new FileReader(fileName));
			while (!file.ready()) {
				System.out.println("This file isn't this ready yet");
				Thread.sleep(50);
			}
			for (int i = 1; i <= lineNumber; i++) {
				//System.out.println("The for loop ran");
				if (i == lineNumber) {
					returnedRead = file.readLine();
					//System.out.println("returnedRead was set");
				}
				else {
					file.readLine();
				}
			}
			file.close();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		//		if (returnedRead == null) {  //This if statement, combined with the while loop at the top that checks if the file is ready, should be enough to stop any null pointer exceptions
		//			returnedRead = ReadLine(fileName, lineNumber);
		//		}
		return returnedRead;
	}

	public boolean Exist(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			return true;
		}
		else {
			return false;
		}
	}

	public void WriteChunk(int fileNumber, int chunkX, int chunkY, short[][][] ChunkbyteToWrite, byte dimension) {
		File directory = null;
		if (dimension == 0) {
			directory = new File("Files/File "+fileNumber+"/Chunks");
			if (directory.exists() == false) {
				directory.mkdirs();
			}
		}
		else if (dimension == 1) {
			directory = new File("Files/File "+fileNumber+"/Mine Chunks");
			if (directory.exists() == false) {
				directory.mkdirs();
			}
		}
		//Files and variables to make sure the biome of the chunk doesn't change
		String ChunkFileString = directory+"/Chunk "+chunkX+" "+chunkY+".txt";
		File ChunkFile = new File(ChunkFileString);

		try {
			FileWriter Write = new FileWriter(ChunkFile);
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
					Write.write(ChunkbyteToWrite[i][j][0]+"");
					Write.write(" ");
					Write.write(ChunkbyteToWrite[i][j][1]+"");
					Write.write(" ");
					Write.write(ChunkbyteToWrite[i][j][2]+"");
					Write.write("\n");
				}
			}
			//Writes the chunks biome to the second line of the file
			Write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public short[][][] ReadChunk(int fileNumber, int chunkX, int chunkY, byte dimension) {
		short[][][] TempByte = new short[16][16][3];
		File directory = null;
		if (dimension == 0) {
			directory = new File("Files/File "+fileNumber+"/Chunks"+"/Chunk "+chunkX+" "+chunkY+".txt");
			if (!directory.exists()) {
				game.GenAboveChunk(chunkX, chunkY);
			}
		}
		else if (dimension == 1) {
			directory = new File("Files/File "+fileNumber+"/Mine Chunks"+"/Chunk "+chunkX+" "+chunkY+".txt");
			if (!directory.exists()) {
				game.GenMiningChunk(chunkX, chunkY);
			}
		}
		try {
			FileReader Read = new FileReader(directory);
			BufferedReader br = new BufferedReader(Read);
			while (!br.ready()) {
				System.out.println("The file is not really ready yet");
				Thread.sleep(50);
			}
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
					String line = br.readLine();
					String[] separatedLine = line.split("\\s+");
					int TempReadInt1 = Integer.parseInt(separatedLine[0]);
					int TempReadInt2 = Integer.parseInt(separatedLine[1]);
					int TempReadInt3 = Integer.parseInt(separatedLine[2]);
					TempByte[i][j][0] = (short) TempReadInt1;
					TempByte[i][j][1] = (short) TempReadInt2;
					TempByte[i][j][2] = (short) TempReadInt3;
					//System.out.println(TempByte[i][j]); //For testing purposes
				}
			}
			br.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println(directory);
		return TempByte;
	}

	public void WriteInventory(int fileNumber) {
		File directory = new File("Files/File "+fileNumber+"/Inventory/Inv.txt");
		String inv = ""; //String that will store each value of the inventory

		for (int j = 0; j < 6; j++)
		{
			for (int i = 0; i < 5; i++)
			{
				inv = game.Inventory[i][j][0]+" "+game.Inventory[i][j][1]; //inv stores the whole line containing what inventory slot stores
				RewriteLine(directory+"", i+j*5+1, inv);
			}
		}
	}

	public short[][][] ReadInventory(int fileNumber) {
		short[][][] TempByte = new short[5][6][2];
		File directory = new File("Files/File "+fileNumber+"/Inventory/Inv.txt");
		try {
			FileReader Read = new FileReader(directory);
			BufferedReader br = new BufferedReader(Read);
			for (int j = 0; j < 6; j++) {
				for (int i = 0; i < 5; i++) {
					String line = br.readLine();
					String[] separatedLine = line.split("\\s+");
					int TempReadInt1 = Integer.parseInt(separatedLine[0]);
					int TempReadInt2 = Integer.parseInt(separatedLine[1]);
					TempByte[i][j][0] = (short) TempReadInt1;
					TempByte[i][j][1] = (short) TempReadInt2;
					//System.out.println(TempByte[i][j]); //For testing purposes
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(directory);
		return TempByte;
	}

	public short[][][] openContainer(String fileName, int startingLine, boolean[][] containerSize) {
		short[][][] TempByte = new short[5][5][2];
		File directory = new File(fileName);
		try {
			FileReader Read = new FileReader(directory);
			BufferedReader br = new BufferedReader(Read);
			while (!br.ready()) {
				System.out.println("The files isn't quite ready yet");
				Thread.sleep(50);
			}
			for (int r = 1; r < startingLine; r++) { //Skips lines that contain data that is not related to the items in the container
				br.readLine();
			}
			for (int j = 0; j < 5; j++) {
				for (int i = 0; i < 5; i++) {
					if (containerSize[j][i]) {
						String line = br.readLine();
						String[] separatedLine = line.split("\\s+");
						int TempReadInt1 = Integer.parseInt(separatedLine[0]);
						int TempReadInt2 = Integer.parseInt(separatedLine[1]);
						TempByte[i][j][0] = (short) TempReadInt1;
						TempByte[i][j][1] = (short) TempReadInt2;
						//System.out.println(TempByte[i][j]); //For testing purposes
					}
				}
			}
			br.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println(directory);
		return TempByte;
	}

	public short[] getInventorySlot(String fileName, int lineNum) {
		short[] TempByte = new short[2];
		File directory = new File(fileName);
		String line;
		try {
			FileReader Read = new FileReader(directory);
			BufferedReader br = new BufferedReader(Read);
			while (!br.ready()) {
				System.out.println("The file isn't ready yet");
				Thread.sleep(50);
			}
			int i = 0;
			while ((line = br.readLine()) != null) {
				i++;
				if (i == lineNum) {
					String[] separatedLine = line.split("\\s+");
					int TempReadInt1 = Integer.parseInt(separatedLine[0]);
					int TempReadInt2 = Integer.parseInt(separatedLine[1]);
					TempByte[0] = (short) TempReadInt1;
					TempByte[1] = (short) TempReadInt2;
				}
			}
			//System.out.println(TempByte[i][j]); //For testing purposes
			br.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println(directory);
		return TempByte;
	}

	public void createUnstackable(short invValue, int i, int j)
	{
		if (invValue == 6)
		{
			int durability = 20;
			int count = 1;
			File PickFile = new File("Files/File "+game.CurrentFile+"/Inventory/StonePick "+count+".txt");
			while (PickFile.exists()) {
				count++;
				PickFile = new File("Files/File "+game.CurrentFile+"/Inventory/StonePick "+count+".txt");
			}
			try {
				PrintWriter PickFileWriter = new PrintWriter(PickFile);
				PickFileWriter.println(""+durability);
				game.Inventory[i][j][1] = (byte) count;
				game.Inventory[i][j][0] = invValue;
				PickFileWriter.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else if (invValue == 7)
		{
			int durability = 20;
			int count = 1;
			File File = new File("Files/File "+game.CurrentFile+"/Inventory/StoneAxe "+count+".txt");
			while (File.exists()) {
				count++;
				File = new File("Files/File "+game.CurrentFile+"/Inventory/StoneAxe "+count+".txt");
			}
			try {
				PrintWriter FileWriter = new PrintWriter(File);
				FileWriter.println(""+durability);
				game.Inventory[i][j][1] = (byte) count;
				game.Inventory[i][j][0] = invValue;
				FileWriter.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else if (invValue == 20)
		{
			int durability = 150;
			int count = 1;
			File file = new File("Files/File "+game.CurrentFile+"/Inventory/IronPick "+count+".txt");
			while (file.exists()) {
				count++;
				file = new File("Files/File "+game.CurrentFile+"/Inventory/IronPick "+count+".txt");
			}
			try {
				PrintWriter FileWriter = new PrintWriter(file);
				FileWriter.println(""+durability);
				game.Inventory[i][j][1] = (byte) count;
				game.Inventory[i][j][0] = invValue;
				FileWriter.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void createTileInfo(short tileValue, int chunk, int i, int j, byte neededValues, byte inventorySlots)
	{
		int count = 1;
		File file = new File("Files/File "+game.CurrentFile+"/Tiles/"+SurfaceTileIDs.values()[tileValue].toString()+" "+count+".txt");
		while (file.exists()) {
			count++;
			file = new File("Files/File "+game.CurrentFile+"/Tiles/"+SurfaceTileIDs.values()[tileValue].toString()+" "+count+".txt");
		}
		try {
			PrintWriter FileWriter = new PrintWriter(file);
			if (ProcessorIDs.valueOf(SurfaceTileIDs.values()[tileValue].toString()).onOff) { //Creates the On/Off boolean on the first line of the text file if that applies to the given container
				FileWriter.println("0");
			}
			if (ProcessorIDs.valueOf(SurfaceTileIDs.values()[tileValue].toString()).electronic) {
				FileWriter.println("0");
				for (int m = 0; m < 4; m++) { //used to store the configuration of input sides
					FileWriter.println("true");
				}
				for (int m = 0; m < 4; m++) { //used to store the configuration of output sides
					FileWriter.println("true");
				}
			}
			for (int g = 0; g < neededValues; g++) {
				FileWriter.println("0");
			}
			for (int p = 0; p < inventorySlots; p++) { //These store the values of the inventory slots of the container
				FileWriter.println("0 0");
			}

			game.StoredTiles[chunk][i][j][1] = (short) count;
			game.StoredTiles[chunk][i][j][0] = tileValue;
			FileWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		game.processhandler.reloadProcessors(); //Add the container to the processor list when it's created
	}
	
	public short getTileFileID(short tileValue) { //Gets the unique ID that makes this tile different from all the other tiles of its type
		short count = 1;
		File file = new File("Files/File "+game.CurrentFile+"/Tiles/"+SurfaceTileIDs.values()[tileValue].toString()+" "+count+".txt");
		while (file.exists()) {
			count++;
			file = new File("Files/File "+game.CurrentFile+"/Tiles/"+SurfaceTileIDs.values()[tileValue].toString()+" "+count+".txt");
		}
		return count;
	}

	public void saveContainer(Processors c)
	{
		File file = new File(c.getFileName());
		try {
			PrintWriter FileWriter = new PrintWriter(file);
			if (c.getContainerID().onOff) { //Creates the On/Off boolean on the first line of the text file if that applies to the given container
				FileWriter.println(c.getValues()[0]);
				if (c.getContainerID().electronic) {
					Electronic elec = (Electronic) c;
					FileWriter.println(elec.getPowerStored());
					for (int m = 0; m < 4; m++) { //prints the input sides to the files
						FileWriter.println(""+elec.getInputSides()[m]);
					}
					for (int m = 0; m < 4; m++) { //prints the output sides to the files
						FileWriter.println(""+elec.getOutputSides()[m]);
					}
				}
				for (int i = 1; i < c.getValues().length; i++) {
					FileWriter.println(""+c.getValues()[i]);
				}
			}
			else {
				if (c.getContainerID().electronic) {
					Electronic elec = (Electronic) c;
					FileWriter.println(elec.getPowerStored());
					for (int m = 0; m < 4; m++) { //prints the input sides to the files
						FileWriter.println(""+elec.getInputSides()[m]);
					}
					for (int m = 0; m < 4; m++) { //prints the output sides to the files
						FileWriter.println(""+elec.getOutputSides()[m]);
					}
				}
				for (int i = 1; i <= c.getValues().length; i++) {
					FileWriter.println(""+c.getValues()[i-1]);
				}
			}
			for (int j = 0; j < 5; j++) {
				for (int i = 0; i < 5; i++) {
					if (c.getValidSlots()[j][i]) {
						FileWriter.println(c.getContainerSlots()[i][j][0]+" "+c.getContainerSlots()[i][j][1]);
					}
				}
			}
			FileWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/* This was a method just to test writing to files
	public void Write(String FileName, String words) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(FileName+".txt");
			pw.println(""+words);
			pw.println("more stuff for fun testing");
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 */
}
