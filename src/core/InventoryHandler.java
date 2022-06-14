package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.playerInput.MouseHandler;
import items.ItemIDs;
import processors.electronics.Electronic;
import tiles.SurfaceTileIDs;

public class InventoryHandler {

	private Main_Game game;
	private Files files;

	BufferedImage InventoryBox;
	BufferedImage InventoryItems;
	BufferedImage ChosenHotbarItem;
	BufferedImage Trash;
	BufferedImage Flame;
	BufferedImage Gray_Flame;
	BufferedImage IO_Button;
	BufferedImage IO_Arrows;

	static Color electronicOutputColor = Color.red;
	static Color electronicInputColor = Color.blue;
	static Color electronicIOColor = Color.orange;

	private final int SpriteSheetWidth = 160;
	private final int SpriteSheetHeight = 160;
	//Location of where to get the sprite from
	private int spriteSheetXY[] = {0, 0};

	public int iconSize = Main_Game.WIDTH/20;
	public int topLeftCornerX = (int) (Main_Game.WIDTH/2-iconSize*2.5);
	public int topLeftCornerY = (int) (Main_Game.HEIGHT/2-iconSize*2.5);
	private Font numberFont = new Font("Cooper Black", Font.BOLD, Main_Game.WIDTH/70);
	private Color highilghtedItemColor = (new Color(255, 242, 0, 50));

	public InventoryHandler(Main_Game game, Files files) {
		this.game = game;
		this.files = files;

		try {
			InventoryBox = ImageIO.read(getClass().getResourceAsStream("/Inventory_Box.png"));
			InventoryItems = ImageIO.read(getClass().getResourceAsStream("/Inventory_Items.png"));
			ChosenHotbarItem = ImageIO.read(getClass().getResourceAsStream("/ChosenHotbarItem.png"));
			Trash = ImageIO.read(getClass().getResourceAsStream("/Trash.png"));
			Flame = ImageIO.read(getClass().getResourceAsStream("/Flame.png"));
			Gray_Flame = ImageIO.read(getClass().getResourceAsStream("/Gray_Flame.png"));
			IO_Button = ImageIO.read(getClass().getResourceAsStream("/IO_Button.png"));
			IO_Arrows = ImageIO.read(getClass().getResourceAsStream("/IO_Arrows.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setupCrafting()
	{
		for (int j  = 0; j < 5; j++)
		{
			for (int i = 0; i < 5; i++)
			{
				game.craftingBoxes[i][j] = 0;
			}
		}
	}

	public boolean addToInv(short invValue, int numToAdd)
	{
		int num = numToAdd;
		for (int j = 0; j < 6; j++)
		{
			for (int i = 0; i < 5; i++)
			{
				if (!unstackable(invValue))
				{
					if (game.Inventory[i][j][0] == 0 || (game.Inventory[i][j][0] == invValue && game.Inventory[i][j][1] < Main_Game.maxStackSize))
					{
						if (num > 0)
						{
							game.Inventory[i][j][0] = invValue;
							while (game.Inventory[i][j][1] < Main_Game.maxStackSize && num > 0)
							{
								game.Inventory[i][j][1]++;
								num--;
							}
						}
					}
				}
				else
				{
					if (game.Inventory[i][j][0] == 0)
					{
						if (num > 0)
						{
							while (game.Inventory[i][j][1] < 1 && num > 0)
							{
								num--;
								files.createUnstackable(invValue, i, j);
							}
						}
					}
				}
			}
		}
		if (num == numToAdd)
		{
			return false;
		}
		addToMessages(invValue, numToAdd-num);
		return true;
	}

	public void addToMessages(int itemID, int numberOfItems)
	{
		if (game.messageString.size() >= 10)
		{
			game.messageString.removeLast();
			game.messageCounter.removeLast();
			game.messageFade.removeLast();
		}
		String itemName = ItemIDs.values()[itemID].getName();
		game.messageString.addFirst("Added "+numberOfItems+"X "+itemName);
		game.messageCounter.addFirst(150);
		game.messageFade.addFirst(250);
	}

	public void tick()
	{
		if (game.resized)
		{
			iconSize = Main_Game.WIDTH/20;
			topLeftCornerX = (int) (Main_Game.WIDTH/2-iconSize*2.5);
			topLeftCornerY = (int) (Main_Game.HEIGHT/2-iconSize*2.5);
			numberFont = new Font("Cooper Black", Font.BOLD, Main_Game.WIDTH/70);
		}

		if (game.inventoryOpened)
		{
			spriteSheetXY[0] = 0;
			spriteSheetXY[1] = 0;
			for(int j = 0; j < 6; j++) //If you remove enough items from a slot to make it zero, then remove that item from the slot.
			{
				for (int i = 0; i < 5; i++)
				{
					if (game.Inventory[i][j][1] == 0)
					{
						game.Inventory[i][j][0] = 0;
					}
				}
			}
			if (game.grabbedItem[1] == 0) { //If your grabbed item runs out of items, then remove that item from your grabbed item
				game.grabbedItem[0] = 0;
			}
		}
		else
		{
			if (game.grabbedItem[0] != 0) {
				if (addToInv(game.grabbedItem[0], game.grabbedItem[1])) {
					for (int i = 0; i < game.grabbedItem.length; i++) {
						game.grabbedItem[i] = 0;
					}
				}
			}
		}
	}

	public void render(Graphics g)
	{
		//This chunk of code puts the inventory on the screen if it's currently opened.
		if (game.inventoryOpened)
		{
			if (game.grabbedItem[0] != 0)
			{
				g.drawImage(InventoryBox, topLeftCornerX, topLeftCornerY-iconSize, topLeftCornerX+iconSize, topLeftCornerY, 0, 0, SpriteSheetWidth, SpriteSheetHeight, null);
				g.drawImage(Trash, topLeftCornerX, topLeftCornerY-iconSize, topLeftCornerX+iconSize, topLeftCornerY, 0, 0, SpriteSheetWidth, SpriteSheetHeight, null);
			}
			for(int j = 0; j < 5; j++)
			{
				for (int i = 0; i < 5; i++)
				{
					//spriteSheetXY = Identifications.getInventoryItemsSheet(game.Inventory[i][j][0]);
					spriteSheetXY = ItemIDs.getSpriteSheet(game.Inventory[i][j][0]);
					g.drawImage(InventoryBox, topLeftCornerX+i*iconSize, topLeftCornerY+j*iconSize, topLeftCornerX+iconSize+i*iconSize, topLeftCornerY+iconSize+j*iconSize, 0, 0, SpriteSheetWidth, SpriteSheetHeight, null);
					g.drawImage(InventoryItems, topLeftCornerX+i*iconSize, topLeftCornerY+j*iconSize, topLeftCornerX+iconSize+i*iconSize, topLeftCornerY+iconSize+j*iconSize, spriteSheetXY[0]*SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight, spriteSheetXY[0]*SpriteSheetWidth+SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight+SpriteSheetHeight, null);
					if (game.HighlightedItem == i*1+j*5+1)
					{
						g.setColor(highilghtedItemColor);
						g.fillRect(topLeftCornerX+i*iconSize, topLeftCornerY+j*iconSize, iconSize, iconSize);
					}
				}
			}
			g.setColor(Color.white);
			g.setFont(numberFont);
			for(int j = 0; j < 5; j++)
			{
				for (int i = 0; i < 5; i++)
				{
					if (!unstackable(game.Inventory[i][j][0]))
					{
						if (game.Inventory[i][j][0] != 0)
						{
							g.drawString(""+game.Inventory[i][j][1], topLeftCornerX+i*iconSize+iconSize/16, topLeftCornerY+(j+1)*iconSize-iconSize/16);
						}
					}
				}
			}
			//Puts the crafting area on the screen
			drawCrafting(g);

			//Puts a container on the screen if one is opened
			if (game.curentlyOpenedContainer != null) {
				if (!game.configuringIO) {
					for (int j = 0; j < 5; j++) {
						for (int i = 0; i < 5; i++) {
							if (game.curentlyOpenedContainer.getValidSlots()[j][i]) {
								//spriteSheetXY = Identifications.getInventoryItemsSheet(game.containerSlots[i][j][0]);
								spriteSheetXY = ItemIDs.getSpriteSheet(game.curentlyOpenedContainer.getContainerSlots()[i][j][0]);
								g.drawImage(InventoryBox, topLeftCornerX+(i+6)*iconSize, topLeftCornerY+j*iconSize, topLeftCornerX+iconSize+(i+6)*iconSize, topLeftCornerY+iconSize+j*iconSize, 0, 0, SpriteSheetWidth, SpriteSheetHeight, null);
								g.drawImage(InventoryItems, topLeftCornerX+(i+6)*iconSize, topLeftCornerY+j*iconSize, topLeftCornerX+iconSize+(i+6)*iconSize, topLeftCornerY+iconSize+j*iconSize, spriteSheetXY[0]*SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight, spriteSheetXY[0]*SpriteSheetWidth+SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight+SpriteSheetHeight, null);
								if (!unstackable(game.curentlyOpenedContainer.getContainerSlots()[i][j][0]))
								{
									if (game.curentlyOpenedContainer.getContainerSlots()[i][j][0] != 0)
									{
										g.drawString(""+game.curentlyOpenedContainer.getContainerSlots()[i][j][1], topLeftCornerX+(i+6)*iconSize+iconSize/16, topLeftCornerY+(j+1)*iconSize-iconSize/16);
									}
									if (game.highlightedContainerItem == i*1+j*5+1)
									{
										g.setColor(highilghtedItemColor);
										g.fillRect(topLeftCornerX+(i+6)*iconSize, topLeftCornerY+j*iconSize, iconSize, iconSize);
										g.setColor(Color.white);
									}
								}
							}
						}
					}
					if (game.curentlyOpenedContainer.getContainerID().hasFlame) { //Draws the flame on any container that has the flame to denote fueling
						//Renders the flame to indicate the amount of burn time in a blast furnace
						//g.fillRect(topLeftCornerX+(8)*iconSize+iconSize/6, topLeftCornerY+2*iconSize, 4*iconSize/6, iconSize);
						g.drawImage(Gray_Flame, topLeftCornerX+(8)*iconSize, topLeftCornerY+2*iconSize, iconSize, iconSize, null);
						double height = 0;
						try {
							height = game.curentlyOpenedContainer.getValues()[2]/(Main_Game.coalBurnTime+0.0);
						} catch(NumberFormatException e) {

						}
						//g2d.fill(new Rectangle(topLeftCornerX+(8)*iconSize+iconSize/6, (int) (topLeftCornerY+3*iconSize-height*iconSize), 4*iconSize/6, (int) (iconSize*height)));
						//g.drawImage(Flame, topLeftCornerX+(8)*iconSize, (int) (topLeftCornerY+2*iconSize), iconSize, (int) (iconSize), null);
						g.drawImage(Flame, topLeftCornerX+(8)*iconSize, (int) (topLeftCornerY+2*iconSize+(1-height)*iconSize), topLeftCornerX+(9)*iconSize, (int) (topLeftCornerY+3*iconSize), 0, (int) ((1-height)*SpriteSheetHeight), SpriteSheetWidth, SpriteSheetHeight, null);
					}
				}
				else {
					if (game.curentlyOpenedContainer.getContainerID().electronic) {
						spriteSheetXY = SurfaceTileIDs.getSpriteSheet((short) SurfaceTileIDs.valueOf(game.curentlyOpenedContainer.getContainerID().toString()).ordinal());
						g.setColor(new Color(50, 50, 50, 180));
						g.fillRect(topLeftCornerX+6*iconSize, topLeftCornerY, iconSize*5, iconSize*5);

						g.drawImage(game.TilesSprite, topLeftCornerX+8*iconSize, topLeftCornerY+2*iconSize, topLeftCornerX+iconSize+8*iconSize, topLeftCornerY+iconSize+2*iconSize, spriteSheetXY[0]*320, spriteSheetXY[1]*320, spriteSheetXY[0]*320+320, spriteSheetXY[1]*320+320, null);

						//Adds the arrows for output sides of a machine
						Electronic e = (Electronic) game.curentlyOpenedContainer;
						int spriteX;
						int spriteY;
						int i;
						int x;
						int y;
						//Up
						i = 0;
						x = 8;
						y = 1;
						if (e.getInputSides()[i] && e.getOutputSides()[i]) {
							g.setColor(electronicIOColor);
							g.fillRect(topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, iconSize, iconSize);
							spriteX = 0;
							spriteY = 1;
							g.drawImage(IO_Arrows, topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, topLeftCornerX+iconSize+x*iconSize, topLeftCornerY+iconSize+y*iconSize, spriteX*SpriteSheetWidth, spriteY*SpriteSheetHeight, spriteX*SpriteSheetWidth+SpriteSheetWidth, spriteY*SpriteSheetHeight+SpriteSheetHeight, null);
						}
						else if (e.getInputSides()[i]) {
							g.setColor(electronicInputColor);
							g.fillRect(topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, iconSize, iconSize);
							spriteX = 0;
							spriteY = 0;
							g.drawImage(IO_Arrows, topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, topLeftCornerX+iconSize+x*iconSize, topLeftCornerY+iconSize+y*iconSize, spriteX*SpriteSheetWidth, spriteY*SpriteSheetHeight, spriteX*SpriteSheetWidth+SpriteSheetWidth, spriteY*SpriteSheetHeight+SpriteSheetHeight, null);
						}
						else if (e.getOutputSides()[i]) {
							g.setColor(electronicOutputColor);
							g.fillRect(topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, iconSize, iconSize);
							spriteX = 1;
							spriteY = 0;
							g.drawImage(IO_Arrows, topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, topLeftCornerX+iconSize+x*iconSize, topLeftCornerY+iconSize+y*iconSize, spriteX*SpriteSheetWidth, spriteY*SpriteSheetHeight, spriteX*SpriteSheetWidth+SpriteSheetWidth, spriteY*SpriteSheetHeight+SpriteSheetHeight, null);
						}
						//down
						i = 1;
						x = 8;
						y = 3;
						if (e.getInputSides()[i] && e.getOutputSides()[i]) {
							g.setColor(electronicIOColor);
							g.fillRect(topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, iconSize, iconSize);
							spriteX = 0;
							spriteY = 1;
							g.drawImage(IO_Arrows, topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, topLeftCornerX+iconSize+x*iconSize, topLeftCornerY+iconSize+y*iconSize, spriteX*SpriteSheetWidth, spriteY*SpriteSheetHeight, spriteX*SpriteSheetWidth+SpriteSheetWidth, spriteY*SpriteSheetHeight+SpriteSheetHeight, null);
						}
						else if (e.getInputSides()[i]) {
							g.setColor(electronicInputColor);
							g.fillRect(topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, iconSize, iconSize);
							spriteX = 1;
							spriteY = 0;
							g.drawImage(IO_Arrows, topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, topLeftCornerX+iconSize+x*iconSize, topLeftCornerY+iconSize+y*iconSize, spriteX*SpriteSheetWidth, spriteY*SpriteSheetHeight, spriteX*SpriteSheetWidth+SpriteSheetWidth, spriteY*SpriteSheetHeight+SpriteSheetHeight, null);
						}
						else if (e.getOutputSides()[i]) {
							g.setColor(electronicOutputColor);
							g.fillRect(topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, iconSize, iconSize);
							spriteX = 0;
							spriteY = 0;
							g.drawImage(IO_Arrows, topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, topLeftCornerX+iconSize+x*iconSize, topLeftCornerY+iconSize+y*iconSize, spriteX*SpriteSheetWidth, spriteY*SpriteSheetHeight, spriteX*SpriteSheetWidth+SpriteSheetWidth, spriteY*SpriteSheetHeight+SpriteSheetHeight, null);
						}
						//left
						i = 2;
						x = 7;
						y = 2;
						if (e.getInputSides()[i] && e.getOutputSides()[i]) {
							g.setColor(electronicIOColor);
							g.fillRect(topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, iconSize, iconSize);
							spriteX = 1;
							spriteY = 2;
							g.drawImage(IO_Arrows, topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, topLeftCornerX+iconSize+x*iconSize, topLeftCornerY+iconSize+y*iconSize, spriteX*SpriteSheetWidth, spriteY*SpriteSheetHeight, spriteX*SpriteSheetWidth+SpriteSheetWidth, spriteY*SpriteSheetHeight+SpriteSheetHeight, null);
						}
						else if (e.getInputSides()[i]) {
							g.setColor(electronicInputColor);
							g.fillRect(topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, iconSize, iconSize);
							spriteX = 0;
							spriteY = 2;
							g.drawImage(IO_Arrows, topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, topLeftCornerX+iconSize+x*iconSize, topLeftCornerY+iconSize+y*iconSize, spriteX*SpriteSheetWidth, spriteY*SpriteSheetHeight, spriteX*SpriteSheetWidth+SpriteSheetWidth, spriteY*SpriteSheetHeight+SpriteSheetHeight, null);
						}
						else if (e.getOutputSides()[i]) {
							g.setColor(electronicOutputColor);
							g.fillRect(topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, iconSize, iconSize);
							spriteX = 1;
							spriteY = 1;
							g.drawImage(IO_Arrows, topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, topLeftCornerX+iconSize+x*iconSize, topLeftCornerY+iconSize+y*iconSize, spriteX*SpriteSheetWidth, spriteY*SpriteSheetHeight, spriteX*SpriteSheetWidth+SpriteSheetWidth, spriteY*SpriteSheetHeight+SpriteSheetHeight, null);
						}
						//right
						i = 3;
						x = 9;
						y = 2;
						if (e.getInputSides()[i] && e.getOutputSides()[i]) {
							g.setColor(electronicIOColor);
							g.fillRect(topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, iconSize, iconSize);
							spriteX = 1;
							spriteY = 2;
							g.drawImage(IO_Arrows, topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, topLeftCornerX+iconSize+x*iconSize, topLeftCornerY+iconSize+y*iconSize, spriteX*SpriteSheetWidth, spriteY*SpriteSheetHeight, spriteX*SpriteSheetWidth+SpriteSheetWidth, spriteY*SpriteSheetHeight+SpriteSheetHeight, null);
						}
						else if (e.getInputSides()[i]) {
							g.setColor(electronicInputColor);
							g.fillRect(topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, iconSize, iconSize);
							spriteX = 1;
							spriteY = 1;
							g.drawImage(IO_Arrows, topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, topLeftCornerX+iconSize+x*iconSize, topLeftCornerY+iconSize+y*iconSize, spriteX*SpriteSheetWidth, spriteY*SpriteSheetHeight, spriteX*SpriteSheetWidth+SpriteSheetWidth, spriteY*SpriteSheetHeight+SpriteSheetHeight, null);
						}
						else if (e.getOutputSides()[i]) {
							g.setColor(electronicOutputColor);
							g.fillRect(topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, iconSize, iconSize);
							spriteX = 0;
							spriteY = 2;
							g.drawImage(IO_Arrows, topLeftCornerX+x*iconSize, topLeftCornerY+y*iconSize, topLeftCornerX+iconSize+x*iconSize, topLeftCornerY+iconSize+y*iconSize, spriteX*SpriteSheetWidth, spriteY*SpriteSheetHeight, spriteX*SpriteSheetWidth+SpriteSheetWidth, spriteY*SpriteSheetHeight+SpriteSheetHeight, null);
						}
					}
				}
				if (game.curentlyOpenedContainer.getContainerID().electronic) {
					//Adds the IO button to the inventory screen for electronics that can have their outputs configured
					g.drawImage(IO_Button, topLeftCornerX+(8)*iconSize, topLeftCornerY+6*iconSize, iconSize, iconSize, null);

					//This block but the energy bar on the screen for the electronic
					Electronic elecContainer = (Electronic) game.curentlyOpenedContainer;
					double widthModifier = elecContainer.getPowerStored()/(elecContainer.getMaxPower()+0.0);
					Graphics2D g2d = (Graphics2D) g;
					float x1 = topLeftCornerX+(6)*iconSize;
					float y1 = topLeftCornerY+11*iconSize/2;
					Color col1 = new Color(90, 10, 10);
					float x2 = topLeftCornerX+(11)*iconSize;
					float y2 = y1;
					Color col2 = new Color(255, 30, 30);
					GradientPaint p = new GradientPaint(x1, y1, col1, x2, y2, col2);
					g2d.setColor(Color.LIGHT_GRAY);
					g2d.fillRect((int) (x1), (int) y1-iconSize/2, (int) iconSize*5, (int) iconSize);
					g2d.setPaint(p);
					g2d.fillRect((int) (x1), (int) y1-iconSize/2, (int) (iconSize*5*widthModifier), (int) iconSize);
				}
			}
		}
		//Puts the hotbar on the screen
		g.setColor(Color.white);
		g.setFont(numberFont);
		//System.out.println(p+26);
		//System.out.println(game.SelectedHotbar == (p+26));
		for (int i = 0; i < 5; i++)
		{
			//spriteSheetXY = Identifications.getInventoryItemsSheet(game.Inventory[i][5][0]);
			spriteSheetXY = ItemIDs.getSpriteSheet(game.Inventory[i][5][0]);
			g.drawImage(InventoryBox, topLeftCornerX+i*iconSize, Main_Game.HEIGHT-iconSize, topLeftCornerX+iconSize+i*iconSize, Main_Game.HEIGHT, 0, 0, SpriteSheetWidth, SpriteSheetHeight, null);
			if (game.HighlightedItem == (i+26))
			{
				g.setColor(highilghtedItemColor);
				g.fillRect(topLeftCornerX+i*iconSize, Main_Game.HEIGHT-iconSize, iconSize, iconSize);
				g.setColor(Color.white);
			}
			if (i == game.SelectedHotbar)
			{
				g.drawImage(ChosenHotbarItem ,topLeftCornerX+game.SelectedHotbar*iconSize, Main_Game.HEIGHT-iconSize, iconSize, iconSize, null);
			}
			g.drawImage(InventoryItems, topLeftCornerX+i*iconSize, Main_Game.HEIGHT-iconSize, topLeftCornerX+iconSize+i*iconSize, Main_Game.HEIGHT, spriteSheetXY[0]*SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight, spriteSheetXY[0]*SpriteSheetWidth+SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight+SpriteSheetHeight, null);
			if (!unstackable(game.Inventory[i][5][0]))
			{
				if (game.Inventory[i][5][0] != 0)
				{
					g.drawString(""+game.Inventory[i][5][1], topLeftCornerX+i*iconSize+iconSize/16,Main_Game.HEIGHT-iconSize/16);
				}
			}
		}
		//Draws the grabbed item on the screen
		if (game.inventoryOpened && game.grabbedItem[0] != 0) {
			spriteSheetXY = ItemIDs.getSpriteSheet(game.grabbedItem[0]);
			g.drawImage(InventoryItems, MouseHandler.mouseX-iconSize/2, MouseHandler.mouseY-iconSize/2, MouseHandler.mouseX+iconSize/2, MouseHandler.mouseY+iconSize/2, spriteSheetXY[0]*SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight, spriteSheetXY[0]*SpriteSheetWidth+SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight+SpriteSheetHeight, null);
			if (!unstackable(game.grabbedItem[0])) {
				g.drawString(""+game.grabbedItem[1], MouseHandler.mouseX-iconSize/2+iconSize/16, MouseHandler.mouseY+iconSize/2-iconSize/16);
			}
		}
	}

	private void drawCrafting(Graphics g)
	{
		if (game.craftingLevel == 0 && game.tempCraftingLevel == 0)
		{
			for(int j = 0; j < 5; j++)
			{
				for (int i = 0; i < 5; i++)
				{
					if ((i == 2 && j == 3)||(i == 3 && j == 3))
					{
						//spriteSheetXY = Identifications.getInventoryItemsSheet(game.craftingBoxes[i][j]);
						spriteSheetXY = ItemIDs.getSpriteSheet(game.craftingBoxes[i][j]);
						g.drawImage(InventoryBox, topLeftCornerX+(i-6)*iconSize, topLeftCornerY+j*iconSize, topLeftCornerX+iconSize+(i-6)*iconSize, topLeftCornerY+iconSize+j*iconSize, 0, 0, SpriteSheetWidth, SpriteSheetHeight, null);
						g.drawImage(InventoryItems, topLeftCornerX+(i-6)*iconSize, topLeftCornerY+j*iconSize, topLeftCornerX+iconSize+(i-6)*iconSize, topLeftCornerY+iconSize+j*iconSize, spriteSheetXY[0]*SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight, spriteSheetXY[0]*SpriteSheetWidth+SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight+SpriteSheetHeight, null);
					}
				}
			}
		}
		else if (game.craftingLevel == 1 && game.tempCraftingLevel == 0)
		{
			for(int j = 0; j < 5; j++)
			{
				for (int i = 0; i < 5; i++)
				{
					if ((i == 2 && j == 2)||(i == 3 && j == 2)||(i == 2 && j == 3)||(i == 3 && j == 3))
					{
						//spriteSheetXY = Identifications.getInventoryItemsSheet(game.craftingBoxes[i][j]);
						spriteSheetXY = ItemIDs.getSpriteSheet(game.craftingBoxes[i][j]);
						g.drawImage(InventoryBox, topLeftCornerX+(i-6)*iconSize, topLeftCornerY+j*iconSize, topLeftCornerX+iconSize+(i-6)*iconSize, topLeftCornerY+iconSize+j*iconSize, 0, 0, SpriteSheetWidth, SpriteSheetHeight, null);
						g.drawImage(InventoryItems, topLeftCornerX+(i-6)*iconSize, topLeftCornerY+j*iconSize, topLeftCornerX+iconSize+(i-6)*iconSize, topLeftCornerY+iconSize+j*iconSize, spriteSheetXY[0]*SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight, spriteSheetXY[0]*SpriteSheetWidth+SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight+SpriteSheetHeight, null);
					}
				}
			}
		}
		else if ((game.craftingLevel == 2 && game.tempCraftingLevel == 0) || (game.tempCraftingLevel == 2 && game.craftingLevel <= 2))
		{
			for(int j = 0; j < 5; j++)
			{
				for (int i = 0; i < 5; i++)
				{
					if (i >= 1 && i <= 3 && j >= 1 && j <= 3)
					{
						//spriteSheetXY = Identifications.getInventoryItemsSheet(game.craftingBoxes[i][j]);
						spriteSheetXY = ItemIDs.getSpriteSheet(game.craftingBoxes[i][j]);
						g.drawImage(InventoryBox, topLeftCornerX+(i-6)*iconSize, topLeftCornerY+j*iconSize, topLeftCornerX+iconSize+(i-6)*iconSize, topLeftCornerY+iconSize+j*iconSize, 0, 0, SpriteSheetWidth, SpriteSheetHeight, null);
						g.drawImage(InventoryItems, topLeftCornerX+(i-6)*iconSize, topLeftCornerY+j*iconSize, topLeftCornerX+iconSize+(i-6)*iconSize, topLeftCornerY+iconSize+j*iconSize, spriteSheetXY[0]*SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight, spriteSheetXY[0]*SpriteSheetWidth+SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight+SpriteSheetHeight, null);
					}
				}
			}
		}
		else if (game.craftingLevel == 3 || (game.tempCraftingLevel == 3 && game.craftingLevel <= 3))
		{
			for(int j = 0; j < 5; j++)
			{
				for (int i = 0; i < 5; i++)
				{
					if (i >= 1 && i <= 4 && j >= 1 && j <= 4)
					{
						//spriteSheetXY = Identifications.getInventoryItemsSheet(game.craftingBoxes[i][j]);
						spriteSheetXY = ItemIDs.getSpriteSheet(game.craftingBoxes[i][j]);
						g.drawImage(InventoryBox, topLeftCornerX+(i-6)*iconSize, topLeftCornerY+j*iconSize, topLeftCornerX+iconSize+(i-6)*iconSize, topLeftCornerY+iconSize+j*iconSize, 0, 0, SpriteSheetWidth, SpriteSheetHeight, null);
						g.drawImage(InventoryItems, topLeftCornerX+(i-6)*iconSize, topLeftCornerY+j*iconSize, topLeftCornerX+iconSize+(i-6)*iconSize, topLeftCornerY+iconSize+j*iconSize, spriteSheetXY[0]*SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight, spriteSheetXY[0]*SpriteSheetWidth+SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight+SpriteSheetHeight, null);
					}
				}
			}
		}
		else if (game.craftingLevel == 4 && game.tempCraftingLevel == 0)
		{
			for(int j = 0; j < 5; j++)
			{
				for (int i = 0; i < 5; i++)
				{
					//spriteSheetXY = Identifications.getInventoryItemsSheet(game.craftingBoxes[i][j]);
					spriteSheetXY = ItemIDs.getSpriteSheet(game.craftingBoxes[i][j]);
					g.drawImage(InventoryBox, topLeftCornerX+(i-6)*iconSize, topLeftCornerY+j*iconSize, topLeftCornerX+iconSize+(i-6)*iconSize, topLeftCornerY+iconSize+j*iconSize, 0, 0, SpriteSheetWidth, SpriteSheetHeight, null);
					g.drawImage(InventoryItems, topLeftCornerX+(i-6)*iconSize, topLeftCornerY+j*iconSize, topLeftCornerX+iconSize+(i-6)*iconSize, topLeftCornerY+iconSize+j*iconSize, spriteSheetXY[0]*SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight, spriteSheetXY[0]*SpriteSheetWidth+SpriteSheetWidth, spriteSheetXY[1]*SpriteSheetHeight+SpriteSheetHeight, null);
				}
			}
		}
	}

	public boolean unstackable(short inventory)
	{
		//Here is going to be a list of all the item IDs that will be unstackable in the game. Return true if its unstackable.
		if (inventory == 6)
		{
			return true;
		}
		else if (inventory == 7)
		{
			return true;
		}
		else if (inventory == 20)
		{
			return true;
		}
		return false;
	}

	public byte getItemType(short itemID)
	{
		//Returns the type of item that is in the held item slot, 0 for no type, 1 for sword, 2 for axe, 3 for pickaxe
		if (isSword(itemID))
		{
			return 1;
		}
		else if (isAxe(itemID))
		{
			return 2;
		}
		else if (isPick(itemID))
		{
			return 3;
		}
		else
		{
			return 0;
		}
	}
	public boolean isSword(short itemID) //A bunch of ifs for all the item IDs for swords
	{
		return false;
	}
	public boolean isAxe(short itemID) //A bunch of ifs for all the item IDs for axes
	{
		if (itemID == 4)
		{
			return true;
		}
		else if (itemID == 7)
		{
			return true;
		}
		return false;
	}
	public boolean isPick(short itemID) //A bunch of ifs for all the item IDs for pickaxes
	{
		if (itemID == 6)
		{
			return true;
		}
		else if (itemID == 20)
		{
			return true;
		}
		return false;
	}
	public boolean comparePickHardness(short itemID, int neededHardness) {
		if (!isPick(itemID)) {
			game.addMessage("Use a pickaxe to harvest that");
			return false;
		}
		if (neededHardness <= 3) {
			if (itemID == 6) { //ID for the stone pickaxe
				return true;
			}
		}
		if (neededHardness <= 4) {
			if (itemID == 20) {  //ID for the iron pickaxe
				return true;
			}
		}
		if (neededHardness <= 8) {
			//TODO whenever the topaz pickaxe is added, make this return true when a topaz pick is being used
		}
		game.addMessage("Your pickaxe is not strong enough");
		return false;
	}
}
