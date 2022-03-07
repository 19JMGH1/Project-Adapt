package menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import core.Files;
import core.Main_Game;

public class FileMenu {

	Main_Game game;
	Files files;
	//some variables needed for file menu
	public short SelectedFile = 0;
	public int FilesOnScreen = 1;
	private String File1Name = null;
	private String File2Name = null;
	private String File3Name = null;
	private String File4Name = null;
	public boolean File1Selected = false;
	public boolean File2Selected = false;
	public boolean File3Selected = false;
	public boolean File4Selected = false;
	//colors for the file menu
	private Color BackgroundBoxColor = new Color(70, 70, 70, 185);
	private Color ButtonBoxColor = new Color(50, 150, 50);
	private Color GreenStringColor = new Color(50, 80, 10);
	private Color EraseStringColor = new Color(100,0,0);
	//dims for stuff in the file menu
	private Font FileNamesFont = new Font("Cooper Black", Font.PLAIN, Main_Game.WIDTH/50);

	private int BackgroundBoxX = Main_Game.WIDTH/2-19*Main_Game.WIDTH/40;
	public int BackgroundBoxY = Main_Game.HEIGHT/2-(19*Main_Game.HEIGHT/40);
	private int BackgroundBoxWidth = 19*Main_Game.WIDTH/20;
	private int BackgroundBoxHeight = 19*Main_Game.HEIGHT/20;

	public int CancelBoxWidth = BackgroundBoxWidth/5;
	public int WorldBoxWidth = (int)((1.52)*BackgroundBoxWidth/5);
	public int CancelCreateBoxHeight = BackgroundBoxHeight/20;
	public int CancelBoxX = Main_Game.WIDTH-CancelBoxWidth-Main_Game.WIDTH/20;
	public int WorldBoxX = Main_Game.WIDTH/20;
	public int CancelCreateBoxY = Main_Game.HEIGHT-CancelCreateBoxHeight-Main_Game.HEIGHT/20;
	private Font CancelCreateFont = new Font("Cooper Black", Font.BOLD, CancelCreateBoxHeight);
	private Rectangle2D CancelStringSize = CancelCreateFont.getStringBounds("Cancel", new FontRenderContext(null, true, true));
	private Rectangle2D WorldStringSize = CancelCreateFont.getStringBounds("New World", new FontRenderContext(null, true, true));
	private int CancelStringX = CancelBoxX+(CancelBoxWidth-(int)(CancelStringSize.getWidth()))/2;
	private int CancelCreateStringY = CancelCreateBoxY+(int)(0.875*CancelCreateBoxHeight);
	private int WorldStringX = WorldBoxX+(WorldBoxWidth-(int)(WorldStringSize.getWidth()))/2;

	public int EraseBoxWidth = BackgroundBoxWidth/5;
	public int EraseBoxX = (CancelBoxX+WorldBoxX)/2;
	private Rectangle2D EraseStringSize = CancelCreateFont.getStringBounds("Erase", new FontRenderContext(null, true, true));
	private int EraseStringX = EraseBoxX+(EraseBoxWidth-(int)(EraseStringSize.getWidth()))/2;

	public int PlayBoxY = CancelCreateBoxY-CancelCreateBoxHeight;
	private Rectangle2D PlayStringSize = CancelCreateFont.getStringBounds("PLAY", new FontRenderContext(null, true, true));
	private int PlayStringX = WorldBoxX+(WorldBoxWidth-(int)(PlayStringSize.getWidth()))/2;
	private int PlayStringY = PlayBoxY+(int)(0.875*CancelCreateBoxHeight);
	//buttons going in or out of the screen
	public boolean CancelButton3D = true;
	public boolean NewWorldButton3D = true;
	public boolean CreateButton3D = true;
	public boolean EraseButton3D = true;
	public boolean PlayButton3D = true;
	//file naming text area
	public boolean FileNaming = false;
	public String FileNamingText = "";
	private Font FileNamingFont = new Font("Cooper Black", Font.PLAIN, (Main_Game.WIDTH+Main_Game.HEIGHT)/100);
	private Rectangle2D FileNamingStringSize = FileNamingFont.getStringBounds(FileNamingText, new FontRenderContext(null, true, true));
	public int FileNamingBoxX = (int) (Main_Game.WIDTH/2-Main_Game.WIDTH*(0.45));
	public int FileNamingBoxY = Main_Game.HEIGHT/2-Main_Game.HEIGHT/40;
	public int FileNamingBoxWidth = (int)(Main_Game.WIDTH*(0.9));
	public int FileNamingBoxHeight = Main_Game.HEIGHT/20;
	private int FileNamingStringX = (int) (Main_Game.WIDTH/2-0.96875*(FileNamingStringSize.getWidth()/2));
	private int FileNamingStringY = (int)(1.03125*(Main_Game.HEIGHT/2));
	private Rectangle2D NameWorldStringSize = CancelCreateFont.getStringBounds("Name Your World", new FontRenderContext(null, true, true));
	private int ChoseWorldNameX = (int) (Main_Game.WIDTH/2-NameWorldStringSize.getWidth()/2);
	public int CreateBoxX = Main_Game.WIDTH/2-CancelBoxWidth/2;
	public int CreateBoxY = 8*Main_Game.HEIGHT/12;
	private Rectangle2D CreateStringSize = CancelCreateFont.getStringBounds("Create", new FontRenderContext(null, true, true));
	private int CreateStringX = (int) (Main_Game.WIDTH/2-(1.03125*FileNamingStringSize.getWidth()/2));
	private int CreateStringY = (int) (Main_Game.HEIGHT/2+(FileNamingBoxHeight-FileNamingStringSize.getHeight())/2);
	private int FileBoxDrawX = Main_Game.WIDTH/2-7*Main_Game.WIDTH/16-1;
	public int FileBoxFillX = Main_Game.WIDTH/2-7*Main_Game.WIDTH/16;
	public int FileBoxHeightChanges = Main_Game.HEIGHT/5+1;
	public int FileBoxWidths = 7*Main_Game.WIDTH/8+1;
	public int FileBoxHeights = Main_Game.HEIGHT/5+1;

	private int blinkCycle = 0; //Controls the blinking of the '|' that appears to show that you are editing the file
	private boolean lineExists = false;
	public boolean selectedTextField = false; //False corresponds to fileNaming, true corresponds to seed
	public Integer seed = null;

	public FileMenu(Main_Game game, Files files) {
		this.game = game;
		this.files = files;
	}

	public void ScrollFiles() {
		//System.out.println("I got run");
		if (FilesOnScreen < 1) {
			FilesOnScreen = 1;
		}
		ReadFileNames();
		while (File1Name == null) {
			FilesOnScreen--;
			ReadFileNames();
		}
		UnselectFiles();
	}

	public void ReadFileNames() {
		File1Name = files.ReadLine("Files/File "+FilesOnScreen+"/Data.txt", 1);
		File2Name = files.ReadLine("Files/File "+(FilesOnScreen+1)+"/Data.txt", 1);
		File3Name = files.ReadLine("Files/File "+(FilesOnScreen+2)+"/Data.txt", 1);
		File4Name = files.ReadLine("Files/File "+(FilesOnScreen+3)+"/Data.txt", 1);
	}

	public void UnselectFiles() {
		File1Selected = false;
		File2Selected = false;
		File3Selected = false;
		File4Selected = false;
		SelectedFile = 0;
	}

	public void RedrawFiles() {
		if (files.ReadLine("Files/File 1/Data.txt", 1) == null) {
			FileNaming = true;
			FileNamingText = "";
		}
		else {
			ScrollFiles();
		}
	}

	public void NoMore3D() {
		CancelButton3D = true;
		NewWorldButton3D = true;
		CreateButton3D = true;
		EraseButton3D = true;
		PlayButton3D = true;
	}

	public void tick() {
		//System.out.println(File1Name);
		if (game.resized == true) {
			//change main screen stuff sizes
			game.resizeBackground();
			FileNamesFont = new Font("Cooper Black", Font.PLAIN, Main_Game.WIDTH/50);
			BackgroundBoxX = Main_Game.WIDTH/2-19*Main_Game.WIDTH/40;
			BackgroundBoxY = Main_Game.HEIGHT/2-(19*Main_Game.HEIGHT/40);
			BackgroundBoxWidth = 19*Main_Game.WIDTH/20;
			BackgroundBoxHeight = 19*Main_Game.HEIGHT/20;
			CancelBoxWidth = BackgroundBoxWidth/5;
			WorldBoxWidth = (int)((1.52)*BackgroundBoxWidth/5);
			CancelCreateBoxHeight = BackgroundBoxHeight/20;
			CancelBoxX = Main_Game.WIDTH-CancelBoxWidth-Main_Game.WIDTH/20;
			WorldBoxX = Main_Game.WIDTH/20;
			CancelCreateBoxY = Main_Game.HEIGHT-CancelCreateBoxHeight-Main_Game.HEIGHT/20;
			CancelCreateFont = new Font("Cooper Black", Font.BOLD, CancelCreateBoxHeight);
			CancelStringSize = CancelCreateFont.getStringBounds("Cancel", new FontRenderContext(null, true, true));
			WorldStringSize = CancelCreateFont.getStringBounds("New World", new FontRenderContext(null, true, true));
			CancelStringX = CancelBoxX+(CancelBoxWidth-(int)((1.0625)*CancelStringSize.getWidth()))/2;
			CancelCreateStringY = CancelCreateBoxY+(int)(0.875*CancelCreateBoxHeight);
			WorldStringX = (WorldBoxX+WorldBoxWidth/2)-((int)((1.0625)*WorldStringSize.getWidth())/2);
			FileBoxDrawX = Main_Game.WIDTH/2-7*Main_Game.WIDTH/16-1;
			FileBoxFillX = Main_Game.WIDTH/2-7*Main_Game.WIDTH/16;
			FileBoxHeightChanges = Main_Game.HEIGHT/5+1;
			FileBoxWidths = 7*Main_Game.WIDTH/8+1;
			FileBoxHeights = Main_Game.HEIGHT/5+1;
			EraseBoxWidth = BackgroundBoxWidth/5;
			EraseBoxX = (CancelBoxX+WorldBoxX)/2;
			EraseStringSize = CancelCreateFont.getStringBounds("Erase", new FontRenderContext(null, true, true));
			EraseStringX = EraseBoxX+(EraseBoxWidth-(int)(EraseStringSize.getWidth()))/2;
			PlayBoxY = CancelCreateBoxY-CancelCreateBoxHeight;
			PlayStringSize = CancelCreateFont.getStringBounds("PLAY", new FontRenderContext(null, true, true));
			PlayStringX = WorldBoxX+(WorldBoxWidth-(int)(PlayStringSize.getWidth()))/2;
			PlayStringY = PlayBoxY+(int)(0.875*CancelCreateBoxHeight);
		}
		//change file naming variables
		if (FileNaming == true) {
			FileNamingFont = new Font("Cooper Black", Font.PLAIN, (Main_Game.WIDTH+Main_Game.HEIGHT)/100);
			FileNamingStringSize = FileNamingFont.getStringBounds(FileNamingText, new FontRenderContext(null, true, true));
			NameWorldStringSize = CancelCreateFont.getStringBounds("Name Your World", new FontRenderContext(null, true, true));
			ChoseWorldNameX = (int) (Main_Game.WIDTH/2-NameWorldStringSize.getWidth()/2);
			FileNamingBoxX = (int) (Main_Game.WIDTH/2-Main_Game.WIDTH*(0.45));
			FileNamingBoxY = Main_Game.HEIGHT/2-Main_Game.HEIGHT/40;
			FileNamingBoxWidth = (int)(Main_Game.WIDTH*(0.9));
			FileNamingBoxHeight = Main_Game.HEIGHT/20;
			FileNamingStringX = (int) (Main_Game.WIDTH/2-(1.03125*FileNamingStringSize.getWidth()/2));
			FileNamingStringY = (int) (Main_Game.HEIGHT/2+(FileNamingBoxHeight-FileNamingStringSize.getHeight())/2);
			CreateBoxX = Main_Game.WIDTH/2-CancelBoxWidth/2;
			CreateBoxY = 8*Main_Game.HEIGHT/12;
			CreateStringSize = CancelCreateFont.getStringBounds("Create", new FontRenderContext(null, true, true));
			CreateStringX = (int) (Main_Game.WIDTH/2-(1.03125*CreateStringSize.getWidth()/2));
			CreateStringY = (int) (CreateBoxY+CancelCreateBoxHeight+(CancelCreateBoxHeight-CreateStringSize.getHeight())/2);
			UnselectFiles();

			//This makes the line that blinks on and off as the user is typing the name of their world
			FileNamingStringSize = FileNamingFont.getStringBounds(FileNamingText, new FontRenderContext(null, true, true));
			if (blinkCycle <= 20) {
				lineExists = true;
				blinkCycle++;
			}
			else if (blinkCycle > 40) {
				blinkCycle = 0;
			}
			else {
				lineExists = false;
				blinkCycle++;
			}
		}
		else {
			selectedTextField = false;
		}
	}

	public void render(Graphics g) {
		//static object rendering
		g.drawImage(game.BackgroundArt, 0, 0, game.BackgroundArtWidth, game.BackgroundArtHeight, null);
		g.setColor(BackgroundBoxColor);
		g.fillRect(BackgroundBoxX, BackgroundBoxY, BackgroundBoxWidth, BackgroundBoxHeight);

		g.setColor(ButtonBoxColor);
		g.fill3DRect(CancelBoxX, CancelCreateBoxY, CancelBoxWidth, CancelCreateBoxHeight, CancelButton3D);
		g.setColor(GreenStringColor);
		g.setFont(CancelCreateFont);
		g.drawString("Cancel", CancelStringX, CancelCreateStringY);
		if (FileNaming == false) {
			//rendering things when the file naming screen is not up

			//new world button
			g.setColor(ButtonBoxColor);
			g.fill3DRect(WorldBoxX, CancelCreateBoxY, WorldBoxWidth, CancelCreateBoxHeight, NewWorldButton3D);
			g.setColor(GreenStringColor);
			g.setFont(CancelCreateFont);
			g.drawString("New World", WorldStringX, CancelCreateStringY);
			//play button
			g.setColor(ButtonBoxColor);
			g.fill3DRect(WorldBoxX, PlayBoxY, WorldBoxWidth, CancelCreateBoxHeight, PlayButton3D);
			g.setColor(GreenStringColor);
			g.drawString("PLAY!", PlayStringX, PlayStringY);
			//erase world button
			g.setColor(Color.red);
			g.setColor(game.titleRedColor);
			g.fill3DRect(EraseBoxX, CancelCreateBoxY, EraseBoxWidth, CancelCreateBoxHeight, EraseButton3D);
			g.setColor(EraseStringColor);
			g.drawString("Erase", EraseStringX, CancelCreateStringY);

			g.setColor(new Color(0, 0, 0));
			if (File1Name != null)
				g.drawRect(FileBoxDrawX, BackgroundBoxY, FileBoxWidths, FileBoxHeights);
			if (File2Name != null)
				g.drawRect(FileBoxDrawX, BackgroundBoxY+(FileBoxHeightChanges-1), FileBoxWidths, FileBoxHeights);
			if (File3Name != null)
				g.drawRect(FileBoxDrawX, BackgroundBoxY+2*(FileBoxHeightChanges-1), FileBoxWidths, FileBoxHeights);
			if (File4Name != null)
				g.drawRect(FileBoxDrawX, BackgroundBoxY+3*(FileBoxHeightChanges-1), FileBoxWidths, FileBoxHeights);
			g.setColor(new Color(70, 70, 70, 150));
			if (File1Name != null && File1Selected == false)
				g.fillRect(FileBoxFillX, BackgroundBoxY+1, FileBoxWidths-1, FileBoxHeights-2);
			if (File2Name != null && File2Selected == false)
				g.fillRect(FileBoxFillX, BackgroundBoxY+(1*FileBoxHeightChanges)+1, FileBoxWidths-1, FileBoxHeights-3);
			if (File3Name != null && File3Selected == false)
				g.fillRect(FileBoxFillX, BackgroundBoxY+(2*FileBoxHeightChanges), FileBoxWidths-1, FileBoxHeights-3);
			if (File4Name != null && File4Selected == false)
				g.fillRect(FileBoxFillX, BackgroundBoxY+(3*FileBoxHeightChanges)-1, FileBoxWidths-1, FileBoxHeights-2);
			g.setColor(new Color(70, 70, 70, 250));
			if (File1Name != null && File1Selected == true)
				g.fillRect(FileBoxFillX, BackgroundBoxY+1, FileBoxWidths-1, FileBoxHeights-2);
			if (File2Name != null && File2Selected == true)
				g.fillRect(FileBoxFillX, BackgroundBoxY+(1*FileBoxHeightChanges)+1, FileBoxWidths-1, FileBoxHeights-3);
			if (File3Name != null && File3Selected == true)
				g.fillRect(FileBoxFillX, BackgroundBoxY+(2*FileBoxHeightChanges), FileBoxWidths-1, FileBoxHeights-3);
			if (File4Name != null && File4Selected == true)
				g.fillRect(FileBoxFillX, BackgroundBoxY+(3*FileBoxHeightChanges)-1, FileBoxWidths-1, FileBoxHeights-2);
			g.setColor(new Color(0, 0, 0));
			g.setFont(FileNamesFont);
			if (File1Name != null)
				g.drawString(File1Name, FileBoxFillX, BackgroundBoxY+FileBoxHeights-1);
			if (File2Name != null)
				g.drawString(File2Name, FileBoxFillX, BackgroundBoxY+2*FileBoxHeights-1);
			if (File3Name != null)
				g.drawString(File3Name, FileBoxFillX, BackgroundBoxY+3*FileBoxHeights-1);
			if (File4Name != null)
				g.drawString(File4Name, FileBoxFillX, BackgroundBoxY+4*FileBoxHeights-1);
		}
		else {
			//rendering the file naming screen
			g.setColor(Color.white);
			g.fillRect(FileNamingBoxX, FileNamingBoxY, FileNamingBoxWidth, FileNamingBoxHeight);
			g.fillRect(FileNamingBoxX, 7*Main_Game.HEIGHT/12+Main_Game.HEIGHT/100, FileNamingBoxWidth, FileNamingBoxHeight);
			g.setColor(Color.black);
			g.drawString("Name Your World", ChoseWorldNameX, (int)(FileNamingBoxY*0.96875));
			g.setFont(FileNamingFont);
			g.drawString(FileNamingText, FileNamingStringX, FileNamingStringY);
			if (lineExists) {
				if (!selectedTextField) {
					g.drawString("|", (int) (FileNamingStringX+FileNamingStringSize.getWidth()), FileNamingStringY);
				}
				else {
					if (seed == null) {
						g.drawString("|", (int) (Main_Game.WIDTH/2+MenuStrings.getStringSize("", Font.PLAIN, (Main_Game.WIDTH+Main_Game.HEIGHT)/100).getWidth()), 7*Main_Game.HEIGHT/12+FileNamingBoxHeight);
					}
					else {
						g.drawString("|", (int) (Main_Game.WIDTH/2+(MenuStrings.getStringSize(""+seed, Font.PLAIN, (Main_Game.WIDTH+Main_Game.HEIGHT)/100).getWidth())/2), 7*Main_Game.HEIGHT/12+FileNamingBoxHeight);
					}
				}
			}
			g.setColor(ButtonBoxColor);
			g.fill3DRect(CreateBoxX, CreateBoxY, CancelBoxWidth, CancelCreateBoxHeight, CreateButton3D);
			g.setColor(GreenStringColor);
			g.setFont(CancelCreateFont);
			g.drawString("Create", CreateStringX, CreateStringY);
			MenuStrings.renderXCentered(g, 7*Main_Game.HEIGHT/12, Font.BOLD, Main_Game.WIDTH/40, "Enter a seed (optional)", Color.BLACK);
			if (seed != null) {
				MenuStrings.renderXCentered(g, 7*Main_Game.HEIGHT/12+FileNamingBoxHeight, Font.PLAIN, (Main_Game.WIDTH+Main_Game.HEIGHT)/100, ""+seed, Color.BLACK);
			}
		}
	}
}
