/*
 * (C) Copyright 2022 Joystick_Justin. All Rights Reserved.
 * author: Justin
 * start date: ~July, 2020 (The exact start date has been lost)
 */

package core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import core.lighting.DayTimeCycle;
import core.playerInput.KeyInput;
import core.playerInput.MouseHandler;
import tiles.BasicTiles;
import tiles.MiningTiles;
import tiles.interactions.HarvestTile;
import tiles.interactions.Interactions;
import tiles.interactions.PlaceTile;
import entities.Handler;
import entities.ItemDrops;
import items.CraftingRecipes;
import items.InventoryManagement;
import menus.FileMenu;
import menus.MainMenu;
import processors.ProcessHandler;
import processors.Processors;
import processors.TileAnimations;
import entities.Character;
import entities.Collision;
import entities.DimensionName;
import entities.EntityIDs;

public class Main_Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 4980508040812834274L;

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int HEIGHT = screenSize.height-screenSize.height/20, WIDTH = screenSize.width-screenSize.width/20;

	public static final int Target_TPS = 30;
	public static final int Target_FPS = 30;
	
	public static boolean FULLSCREEN = false;

	public static final int maxStackSize = 50; //The max number of items allowed in an inventory slot

	public static final int FurnaceCookTime = 300; //This number is measured in tick, where there are the value of Target_TPS ticks in a second
	public static final int coalBurnTime = 5; //How many times the flame ticks down with coal

	public static final int invItemsSpriteWidth = 7; //This is how many items fit horizontally across one row of the items sprite sheet
	public static final int surfaceTileSpriteWidth = 5; //This is how many items fit horizontally across one of the rows of the surface tile sprite sheet
	public static final int coveTileSpriteWidth = 3; //This is how many items fit horizontally across one of the rows of the cove tile sprite sheet

	public Thread thread1; //The render thread
	private boolean running = false; //Is the game running?

	public BufferedImage BackgroundArt; //The picture that appears in the background of the menus
	public BufferedImage TilesSprite; //This variable stores the surface tile sprite sprite sheet

	public int BackgroundArtWidth = WIDTH; //Used in defining the background art size when it renders to the screen
	public int BackgroundArtHeight = (int) (WIDTH*1.8125);

	public int CurrentFile = 0; //variable that stores which file is opened

	//Variable that store the part of the world that is currently loaded
	//A 3 by 3 grid of chunks is always loaded
	//the last 2 dimensions are the size of each loaded chunk, the 9 is because 9 chunks are loaded at one time
	public short[] [] [] [] StoredTiles = new short [9][16][16][3]; //Stores what kind of tile is in each spot and this array allows for some tiles to have extra data in them.
	public byte[] [] [] lightLevels = new byte[9][16][16]; //This array stores the light level on each tile

	public static final byte MAX_LIGHT_LEVEL = 11; //This number is the max light level -1 since 0 is a light level that is included in this list
	public Integer seed = null; //this integer stores the seed for the world
	//this array stores all the information about the players inventory
	public short[] [] []Inventory = new short [5][6][2]; //It's a 5 by 5 inventory, and two values, one is the ID, other is amount of items in the slot.
	//The 6th value in the second component of the inventory stores the hotbar
	public short[] grabbedItem = new short [2]; //Stores the data about the item that is attached to the mouse

	public Processors curentlyOpenedContainer = null; //Points to the object that is the currently opened container
	public byte highlightedContainerItem = 0;
	public boolean configuringIO = false; //Stores whether or not an electrical machine's input/output control is being edited

	public byte SelectedHotbar = 0; //Tracks which hotbar slot is currently selected
	public byte HighlightedItem = 0; //Controls the item that has been selected by the player
	public boolean inventoryOpened = false; //Stores whether or not the inventory is opened
	public short craftingBoxes[][] = new short[5][5]; //Stores the items that are in each slot of the crafting area
	public byte craftingLevel = 0; //0 for a 2 by 1, 1 for a 2 by 2, 2 for a 3 by 3, 3 for a 4 by 4, 4 for a 5 by 5 
	public byte tempCraftingLevel = 0; //Variable that controls an increased crafting level due to standing near a table or anvil or ect.

	//Aspect Ratio is WIDTH = 17 and HEIGHT = 21
	public int CharacterWidth = Main_Game.WIDTH/20;
	public int CharacterHeight = CharacterWidth*(21/17);
	public short VelX = 0;
	public short VelY = 0;
	//All these variables store the position of the character in the world
	public int x = 0;
	public int y = 0;
	public int TileX = 0;
	public int TileY = 0;
	public int ChunkX = 0;
	public int ChunkY = 0;
	//Aspect Ratio is WIDTH = 320 and HEIGHT = 320
	public int TileWidth = Main_Game.WIDTH/18;
	public int TileHeight = TileWidth;

	//These are the locations on the screen where the character is always located
	public int characterX = (Main_Game.WIDTH/2)-CharacterWidth/2;
	public int characterY = (Main_Game.HEIGHT/2)-CharacterHeight/2;

	//The red color used for buttons in the menus
	public Color titleRedColor = new Color(240, 0, 0);

	public boolean resized = true;
	public boolean Paused = false;

	private Handler handler;
	public Window window;
	private MouseHandler mousehandler;
	private KeyInput keyinput;
	private MainMenu mainmenu;
	public FileMenu filemenu;
	public Files files;
	private WorldGeneration worldgen;
	private InGameHud ingamehud;
	public InventoryHandler inventoryhandler;
	public Collision collision;
	private CraftingRecipes craftingrecipes;
	public Interactions interactions;
	public ProcessHandler processhandler;
	public HarvestTile harvesttile;
	public PlaceTile placetile;
	public TileAnimations tileanimations;
	public DayTimeCycle daytimecycle;
	public InventoryManagement inventorymanagment;

	public TickThread tickthread;

	public LinkedList<String> messageString = new LinkedList<String>();
	public LinkedList<Integer> messageCounter = new LinkedList<Integer>();
	public LinkedList<Integer> messageFade = new LinkedList<Integer>();

	//Buttons pressed correspond to movement and velocity
	public boolean WPressed = false;
	public boolean APressed = false;
	public boolean SPressed = false;
	public boolean DPressed = false;

	//This is stuff for different dimensions
	public byte dimension = 0; //0 corresponds to the surface, 1 corresponds to the cove
	
	public boolean justCrafted = false;

	public Main_Game() { //Initialize all the classes
		handler = new Handler(this);
		mainmenu = new MainMenu(this);
		files = new Files(this);
		worldgen = new WorldGeneration(this, files);
		filemenu = new FileMenu(this, files);
		ingamehud = new InGameHud(this);
		inventoryhandler = new InventoryHandler(this, files);
		collision = new Collision(this);
		craftingrecipes = new CraftingRecipes(this, inventoryhandler);
		interactions = new Interactions(this, files);
		processhandler = new ProcessHandler(this);
		harvesttile = new HarvestTile(this, inventoryhandler);
		placetile = new PlaceTile(this, interactions);
		daytimecycle = new DayTimeCycle(this);
		inventorymanagment = new InventoryManagement(this);
		

		tickthread = new TickThread(this);

		mousehandler = new MouseHandler(this, mainmenu, filemenu, files, ingamehud, interactions, craftingrecipes);
		keyinput = new KeyInput(this, filemenu, craftingrecipes);

		this.addMouseListener(mousehandler);
		this.addMouseMotionListener(mousehandler);
		this.addMouseWheelListener(mousehandler);
		this.addKeyListener(keyinput);

		try {
			BackgroundArt = ImageIO.read(getClass().getResourceAsStream("/First_Background.png"));
			TilesSprite = ImageIO.read(getClass().getResourceAsStream("/Tiles.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		window = new Window(WIDTH, HEIGHT, "Project Adapt", this);
	}

	public enum State {
		MainMenu,
		FileMenu,
		InWorld;
	}

	public State AdaptState = State.MainMenu;
	private State StoredState = State.MainMenu;

	public synchronized void start() {
		//System.out.println(HEIGHT+", "+WIDTH);
		running = true;
		thread1 = new Thread(this);
		thread1.start();
		System.out.println("First thread started");
		tickthread.start();
		processhandler.start();
	}

	public void close() {
		running = false;
		tickthread.running = false;
		processhandler.running = false;
		window.frame.dispose();
	}

	public void run() {
		running = true;
		long now;
		long updateTime;
		long wait;
		final long OPTIMAL_TIME = 1000000000 / Target_FPS;

		while (running) {
			now = System.nanoTime();
			//tick();
			render();
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
			//System.out.println("Render: "+wait);
		}
	}

	public void switchDimension(byte dim) {
		SaveFile();
		dimension = dim;
		loadTiles();
		processhandler.reload = true;
	}

	public void LoadFile() {
		CurrentFile = filemenu.FilesOnScreen+filemenu.SelectedFile-1;
		tileanimations = new TileAnimations();
		if (files.Exist("Files/File "+CurrentFile)) {
			AdaptState = State.InWorld;
			seed = Integer.parseInt(files.ReadLine("Files/File "+CurrentFile+"/Data.txt", 2));
			TileX = Integer.parseInt(files.ReadLine("Files/File "+CurrentFile+"/Data.txt", 3));
			TileY = Integer.parseInt(files.ReadLine("Files/File "+CurrentFile+"/Data.txt", 4));
			ChunkX = Integer.parseInt(files.ReadLine("Files/File "+CurrentFile+"/Data.txt", 5));
			ChunkY = Integer.parseInt(files.ReadLine("Files/File "+CurrentFile+"/Data.txt", 6));
			craftingLevel = (byte) Integer.parseInt(files.ReadLine("Files/File "+CurrentFile+"/Data.txt", 7));
			dimension = (byte) Integer.parseInt(files.ReadLine("Files/File "+CurrentFile+"/Data.txt", 8));
			daytimecycle.time = Integer.parseInt(files.ReadLine("Files/File "+CurrentFile+"/Data.txt", 9));
			//dimension = 0; //Was used for leaving the mining dimension before leaving it was coded
			loadTiles();
			Inventory = files.ReadInventory(CurrentFile);
			inventoryhandler.setupCrafting();
			//Inventory[0][0][0] = 11; //Used for putting a mine in the inventory for initial debugging of the mining dimension on new worlds.
			//Inventory[0][0][1] = 1;
			AddTiles();
			AddCharacter();
			AddDims();
			processhandler.reload = true;
			for (int i = 0; i < grabbedItem.length; i++) {
				grabbedItem[i] = 0;
			}
		}
	}

	public void loadTiles() {
		daytimecycle.clearLights();
		StoredTiles[0] = files.ReadChunk(CurrentFile, ChunkX-1, ChunkY+1, dimension);
		StoredTiles[1] = files.ReadChunk(CurrentFile, ChunkX, ChunkY+1, dimension);
		StoredTiles[2] = files.ReadChunk(CurrentFile, ChunkX+1, ChunkY+1, dimension);
		StoredTiles[3] = files.ReadChunk(CurrentFile, ChunkX-1, ChunkY, dimension);
		StoredTiles[4] = files.ReadChunk(CurrentFile, ChunkX, ChunkY, dimension);
		StoredTiles[5] = files.ReadChunk(CurrentFile, ChunkX+1, ChunkY, dimension);
		StoredTiles[6] = files.ReadChunk(CurrentFile, ChunkX-1, ChunkY-1, dimension);
		StoredTiles[7] = files.ReadChunk(CurrentFile, ChunkX, ChunkY-1, dimension);
		StoredTiles[8] = files.ReadChunk(CurrentFile, ChunkX+1, ChunkY-1, dimension);
	}

	public void CloseFile() {
		SaveFile();
		messageString.clear();
		messageCounter.clear();
		messageFade.clear();
		x = 0;
		y = 0;
		RemoveObjects();
		processhandler.removeAllProcessors();
		seed = null;
		//daytimecycle.resetLists();
		//This has to stay at the very end
		tileanimations = null;
	}

	public void SaveFile() {
		files.WriteChunk(CurrentFile, ChunkX-1, ChunkY+1, StoredTiles[0], dimension);
		files.WriteChunk(CurrentFile, ChunkX, ChunkY+1, StoredTiles[1], dimension);
		files.WriteChunk(CurrentFile, ChunkX+1, ChunkY+1, StoredTiles[2], dimension);
		files.WriteChunk(CurrentFile, ChunkX-1, ChunkY, StoredTiles[3], dimension);
		files.WriteChunk(CurrentFile, ChunkX, ChunkY, StoredTiles[4], dimension);
		files.WriteChunk(CurrentFile, ChunkX+1, ChunkY, StoredTiles[5], dimension);
		files.WriteChunk(CurrentFile, ChunkX-1, ChunkY-1, StoredTiles[6], dimension);
		files.WriteChunk(CurrentFile, ChunkX, ChunkY-1, StoredTiles[7], dimension);
		files.WriteChunk(CurrentFile, ChunkX+1, ChunkY-1, StoredTiles[8], dimension);
		files.RewriteLine("Files/File "+CurrentFile+"/Data.txt", 2, ""+seed);
		files.RewriteLine("Files/File "+CurrentFile+"/Data.txt", 3, ""+TileX);
		files.RewriteLine("Files/File "+CurrentFile+"/Data.txt", 4, ""+TileY);
		files.RewriteLine("Files/File "+CurrentFile+"/Data.txt", 5, ""+ChunkX);
		files.RewriteLine("Files/File "+CurrentFile+"/Data.txt", 6, ""+ChunkY);
		files.WriteInventory(CurrentFile);
		files.RewriteLine("Files/File "+CurrentFile+"/Data.txt", 7, ""+craftingLevel);
		files.RewriteLine("Files/File "+CurrentFile+"/Data.txt", 8, ""+dimension);
		files.RewriteLine("Files/File "+CurrentFile+"/Data.txt", 9, ""+daytimecycle.getTime());
		processhandler.saveAllProcessors();
	}

	public void addMessage(String message)
	{
		if (messageString.size() >= 10)
		{
			messageString.removeLast();
			messageCounter.removeLast();
			messageFade.removeLast();
		}
		messageString.addFirst(message);
		messageCounter.addFirst(150);
		messageFade.addFirst(250);
	}

	public void GenWorld() {
		worldgen.GenerateWorld();
	}

	public void GenAboveChunk(int ChunkX, int ChunkY) {
		worldgen.GenerateAboveChunk(ChunkX, ChunkY);
	}

	public void GenMiningChunk(int ChunkX, int ChunkY) {
		worldgen.GenerateMiningChunk(ChunkX, ChunkY);
	}

	public void AddCharacter() {
		handler.addObject(new Character(100, 100, EntityIDs.Player, this, handler, keyinput));
	}
	public void AddTiles() {
		if (dimension == 0) {
			handler.addObject(new BasicTiles(0, 0, EntityIDs.Tiles, this, handler));
		}
		else if (dimension == 1) {
			handler.addObject(new MiningTiles(0, 0, EntityIDs.Tiles, this, handler));
		}
	}
	public void AddDims() {
		handler.addObject(new DimensionName(0, 0, EntityIDs.Other, this, handler, dimension));
	}

	public void addDropedItem(byte itemID, int numToDrop) {
		for (int  i = 0; i < numToDrop; i++) {
			handler.addObject(new ItemDrops(MouseHandler.mouseX, MouseHandler.mouseY, itemID, EntityIDs.ItemDrop, this, handler));
		}
	}

	public void RemoveObjects() {
		handler.removeAllObjects();
	}

	private void PositionHandler() {
		//Sets the velocities based on which buttons are pressed
		if (WPressed && !SPressed) {
			VelY = (short) (TileHeight/8);
		}
		else if (SPressed && !WPressed) {
			VelY = (short) (-TileHeight/8);
		}
		else {
			VelY = 0;
		}
		if (APressed && !DPressed) {
			VelX = (short) (-TileWidth/8);
		}
		else if (DPressed && !APressed) {
			VelX = (short) (TileWidth/8);
		}
		else {
			VelX = 0;
		}

		//System.out.println(VelX+", "+VelY+", "+x+", "+y+", "+TileX+", "+TileY); //For testing purposes
		//System.out.println(TileWidth/10);
		if (!inventoryOpened)
		{
			if (collision.checkX(x, y, TileX, TileY, ChunkX, ChunkY, (int) VelX, (int) VelY))
			{
				x += VelX;
			}
			if (collision.checkY(x, y, TileX, TileY, ChunkX, ChunkY, (int) VelX, (int) VelY))
			{
				y += VelY;
			}
		}
		//Changes the player's tile position on the every time crosses a tile border
		if (x < 0) {
			TileX--;
			x += TileWidth;
			//System.out.println(TileX);
		}
		else if (x >= TileWidth) {
			TileX++;
			x -= TileWidth;
			//System.out.println(TileX);
		}

		if (y < 0) {
			TileY--;
			y += TileHeight;
			//System.out.println(TileY);
		}
		else if (y >= TileHeight) {
			TileY++;
			y -= TileHeight;
			//System.out.println(TileY);
		}

		//Makes the players chunk position change and rearranges the tiles that are in view as the player crosses a chunk border
		if (TileY > 0) {
			TileY -= 16;
			ChunkY++;
			lightLevels[6] = lightLevels[3];
			lightLevels[7] = lightLevels[4];
			lightLevels[8] = lightLevels[5];
			lightLevels[3] = lightLevels[0];
			lightLevels[4] = lightLevels[1];
			lightLevels[5] = lightLevels[2];
			daytimecycle.removeLightSources();
			files.WriteChunk(CurrentFile, ChunkX-1, ChunkY-2, StoredTiles[6], dimension);
			files.WriteChunk(CurrentFile, ChunkX, ChunkY-2, StoredTiles[7], dimension);
			files.WriteChunk(CurrentFile, ChunkX+1, ChunkY-2, StoredTiles[8], dimension);
			StoredTiles[6] = StoredTiles[3];
			StoredTiles[7] = StoredTiles[4];
			StoredTiles[8] = StoredTiles[5];
			StoredTiles[3] = StoredTiles[0];
			StoredTiles[4] = StoredTiles[1];
			StoredTiles[5] = StoredTiles[2];
			StoredTiles[0] = files.ReadChunk(CurrentFile, ChunkX-1, ChunkY+1, dimension);
			StoredTiles[1] = files.ReadChunk(CurrentFile, ChunkX, ChunkY+1, dimension);
			StoredTiles[2] = files.ReadChunk(CurrentFile, ChunkX+1, ChunkY+1, dimension);
			processhandler.reload = true;
		}
		else if (TileY < -15) {
			TileY += 16;
			ChunkY--;
			lightLevels[0] = lightLevels[3];
			lightLevels[1] = lightLevels[4];
			lightLevels[2] = lightLevels[5];
			lightLevels[3] = lightLevels[6];
			lightLevels[4] = lightLevels[7];
			lightLevels[5] = lightLevels[8];
			daytimecycle.removeLightSources();
			files.WriteChunk(CurrentFile, ChunkX-1, ChunkY+2, StoredTiles[0], dimension);
			files.WriteChunk(CurrentFile, ChunkX, ChunkY+2, StoredTiles[1], dimension);
			files.WriteChunk(CurrentFile, ChunkX+1, ChunkY+2, StoredTiles[2], dimension);
			StoredTiles[0] = StoredTiles[3];
			StoredTiles[1] = StoredTiles[4];
			StoredTiles[2] = StoredTiles[5];
			StoredTiles[3] = StoredTiles[6];
			StoredTiles[4] = StoredTiles[7];
			StoredTiles[5] = StoredTiles[8];
			StoredTiles[6] = files.ReadChunk(CurrentFile, ChunkX-1, ChunkY-1, dimension);
			StoredTiles[7] = files.ReadChunk(CurrentFile, ChunkX, ChunkY-1, dimension);
			StoredTiles[8] = files.ReadChunk(CurrentFile, ChunkX+1, ChunkY-1, dimension);
			processhandler.reload = true;
		}
		if (TileX < 0) {
			TileX += 16;
			ChunkX--;
			lightLevels[2] = lightLevels[1];
			lightLevels[5] = lightLevels[4];
			lightLevels[8] = lightLevels[7];
			lightLevels[1] = lightLevels[0];
			lightLevels[4] = lightLevels[3];
			lightLevels[7] = lightLevels[6];
			daytimecycle.removeLightSources();
			files.WriteChunk(CurrentFile, ChunkX+2, ChunkY+1, StoredTiles[2], dimension);
			files.WriteChunk(CurrentFile, ChunkX+2, ChunkY, StoredTiles[5], dimension);
			files.WriteChunk(CurrentFile, ChunkX+2, ChunkY-1, StoredTiles[8], dimension);
			StoredTiles[2] = StoredTiles[1];
			StoredTiles[5] = StoredTiles[4];
			StoredTiles[8] = StoredTiles[7];
			StoredTiles[1] = StoredTiles[0];
			StoredTiles[4] = StoredTiles[3];
			StoredTiles[7] = StoredTiles[6];
			StoredTiles[0] = files.ReadChunk(CurrentFile, ChunkX-1, ChunkY+1, dimension);
			StoredTiles[3] = files.ReadChunk(CurrentFile, ChunkX-1, ChunkY, dimension);
			StoredTiles[6] = files.ReadChunk(CurrentFile, ChunkX-1, ChunkY-1, dimension);
			processhandler.reload = true;
		}
		else if (TileX > 15) {
			TileX -= 16;
			ChunkX++;
			lightLevels[0] = lightLevels[1];
			lightLevels[3] = lightLevels[4];
			lightLevels[6] = lightLevels[7];
			lightLevels[1] = lightLevels[2];
			lightLevels[4] = lightLevels[5];
			lightLevels[7] = lightLevels[8];
			daytimecycle.removeLightSources();
			files.WriteChunk(CurrentFile, ChunkX-2, ChunkY+1, StoredTiles[0], dimension);
			files.WriteChunk(CurrentFile, ChunkX-2, ChunkY, StoredTiles[3], dimension);
			files.WriteChunk(CurrentFile, ChunkX-2, ChunkY-1, StoredTiles[6], dimension);
			StoredTiles[0] = StoredTiles[1];
			StoredTiles[3] = StoredTiles[4];
			StoredTiles[6] = StoredTiles[7];
			StoredTiles[1] = StoredTiles[2];
			StoredTiles[4] = StoredTiles[5];
			StoredTiles[7] = StoredTiles[8];
			StoredTiles[2] = files.ReadChunk(CurrentFile, ChunkX+1, ChunkY+1, dimension);
			StoredTiles[5] = files.ReadChunk(CurrentFile, ChunkX+1, ChunkY, dimension);
			StoredTiles[8] = files.ReadChunk(CurrentFile, ChunkX+1, ChunkY-1, dimension);
			processhandler.reload = true;
		}
	}

	private void showMessages()
	{
		for (int i = 0; i < messageString.size(); i++)
		{
			if (messageCounter.get(i) >= 0)
			{
				messageCounter.set(i, messageCounter.get(i)-1);
			}
			else
			{
				if (messageFade.get(i) >= 5)
				{
					messageFade.set(i, messageFade.get(i)-5);
				}
				else
				{
					messageString.removeLast();
					messageCounter.removeLast();
					messageFade.removeLast();
				}
			}
		}
	}

	private Font collectedFont = new Font("Cooper Black", Font.BOLD, Main_Game.WIDTH/50);
	private int separation = Main_Game.HEIGHT/20;
	private void renderMessages(Graphics g)
	{
		g.setFont(collectedFont);
		for (int i = 0; i < messageString.size(); i++)
		{
			Color col = new Color(0, 0, 0, messageFade.get(i));
			g.setColor(col);
			g.drawString(messageString.get(i), 0, Main_Game.HEIGHT-(i+1)*separation);
		}
	}

	//Declaring variables for timing of autosaving
	long currentTime = System.currentTimeMillis();
	long prevTime = currentTime;
	int savePeriod = 60000;

	public void tick() {
		//System.out.println(AdaptState);
		if ((window.frame.getWidth() < window.frame.getHeight())) {
			//System.out.println("here");
			window.resize(this);
		}
		if (window.frame.getHeight() < (window.frame.getWidth()/3)) {
			//System.out.println("here");
			window.resize(this);
		}

		if (StoredState != (StoredState = AdaptState)) {
			resized = true;
		}

		if (resized)
		{
			characterX = (Main_Game.WIDTH/2)-CharacterWidth/2;
			characterY = (Main_Game.HEIGHT/2)-CharacterHeight/2;
			TileWidth = Main_Game.WIDTH/18;
			TileHeight = TileWidth;
			collectedFont = new Font("Cooper Black", Font.BOLD, Main_Game.WIDTH/50);
			separation = Main_Game.HEIGHT/20;
		}

		if (AdaptState == State.MainMenu) {
			MouseHandler.MouseMovedEnabled = true;
			mainmenu.tick();
		}
		else if (AdaptState == State.FileMenu) {
			MouseHandler.MouseMovedEnabled = true;
			filemenu.tick();
		}
		else if (AdaptState == State.InWorld) {
			//Autosave every 60 seconds
			currentTime = System.currentTimeMillis();
			if (currentTime > prevTime+savePeriod)
			{
				prevTime = currentTime;
				SaveFile();
			}
			processhandler.tick();
			inventoryhandler.tick();
			ingamehud.tick();
			interactions.tick();
			daytimecycle.tick();
		}
		showMessages();
		handler.tick();
		resized = false;
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		if (isShowing()) paint(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, Main_Game.WIDTH, Main_Game.HEIGHT);

		if (AdaptState == State.MainMenu) {
			mainmenu.render(g);
		}
		else if (AdaptState == State.FileMenu) {
			filemenu.render(g);
		}
		handler.render(g);

		if (AdaptState == State.InWorld) {
			processhandler.render(g);
			PositionHandler(); //This is not in the tick() method since being in the tick() method caused rendering issues and flashes related to the multi-threading.
			daytimecycle.render(g);
			ingamehud.render(g);
			inventoryhandler.render(g);
			interactions.render(g);
		}
		//Light.render(g); //Use this light class to make small lights once the day/night cycle is introduced
		renderMessages(g);
		
		//This makes a light source in the corner to represent a sun
//		Graphics2D g2d = (Graphics2D) g;
//		Point2D center = new Point2D.Double(Main_Game.WIDTH, 0);
//	     float radius = 1000;
//	     float[] dist = {0.02f, 0.8f};
//	     Color[] colors = {new Color(150, 150, 220), new Color(0,0,0,0)};
//	     RadialGradientPaint p =
//	         new RadialGradientPaint(center, radius, dist, colors);
//	    g2d.setPaint(p);
//	    g2d.fillRect((int) (center.getX()-radius), (int) (center.getY()-radius), (int) (radius*4), (int) (radius*4));
		
		g.dispose();
		bs.show();
	}

	public void resizeBackground() {
		if (HEIGHT > (WIDTH*(1/1.8125))) {
			BackgroundArtHeight = HEIGHT;
			BackgroundArtWidth = (int) (HEIGHT*1.8125);
		}
		else {
			BackgroundArtWidth = WIDTH;
			BackgroundArtHeight = (int) (WIDTH*(1/1.8125));
		}
		//System.out.println("Resizing background");
	}

	public static void main(String args[]) {
		System.setProperty("sun.awt.noerasebackground", "True");
		System.setProperty("sun.java2d.opengl", "True");
		new Main_Game();
	}
}
