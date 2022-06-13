package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class InGameHud {

	private Main_Game game;
	
	private Font XYPositionFont = new Font("Cooper Black", Font.BOLD, Main_Game.HEIGHT/24);
	
	//variables that control dark background on the pause menu
	private int BackgroundBoxX = Main_Game.WIDTH/4;
	private int BackgroundBoxY = Main_Game.HEIGHT/4;
	private int BackgroundBoxWidth = Main_Game.WIDTH/2;
	private int BackgroundBoxHeight = Main_Game.HEIGHT/2;
	private Color BackgroundBoxColor = new Color(70, 70, 70, 185);
	//Varibles controling the 3D nature of the buttons
	public boolean ResumeButton3D = true;
	public boolean SaveButton3D = true;
	public boolean QuitButton3D = true;
	//variables that control the buttons in the pause menu
	private Color PauseButtonColor = new Color(80, 80, 255);
	private Color PauseStringColor = new Color(30, 30, 100);
	private Rectangle2D ResumeStringSize;
	private Rectangle2D SaveStringSize;
	private Rectangle2D QuitStringSize;
	public int ButtonWidths = BackgroundBoxWidth;
	public int ButtonHeights = BackgroundBoxHeight/5;
	public int ButtonSeparation = ButtonHeights;
	public int ButtonX = BackgroundBoxX;
	public int ButtonY = BackgroundBoxY;
	private int ResumeStringX;
	private int ResumeStringY;
	private int SaveStringX;
	private int SaveStringY;
	private int QuitStringX;
	private int QuitStringY;
	private Font PauseFont = new Font("Cooper Black", Font.BOLD, ButtonHeights);
	
	//variable to control where the position appears in the top left corner
	private Rectangle2D ShowXYPosition;
	
	public InGameHud(Main_Game game) {
		this.game = game;
		XYPositionFont = new Font("Cooper Black", Font.BOLD, Main_Game.HEIGHT/24);
		ShowXYPosition = XYPositionFont.getStringBounds("Position: "+(game.TileX+game.ChunkX*16)+", "+(game.TileY+game.ChunkY*16), new FontRenderContext(null, true, true));
		ResumeStringSize = PauseFont.getStringBounds("Resume", new FontRenderContext(null, true, true));
		SaveStringSize = PauseFont.getStringBounds("Save", new FontRenderContext(null, true, true));
		QuitStringSize = PauseFont.getStringBounds("Quit", new FontRenderContext(null, true, true));
		ResumeStringX = ButtonX+(ButtonWidths-(int)(ResumeStringSize.getWidth()))/2;
		ResumeStringY = ButtonY+(int)(0.875*ButtonHeights);
		SaveStringX = ButtonX+(ButtonWidths-(int)(SaveStringSize.getWidth()))/2;
		SaveStringY = ButtonY+ButtonHeights+ButtonSeparation+(int)(0.875*ButtonHeights);
		QuitStringX = ButtonX+(ButtonWidths-(int)(QuitStringSize.getWidth()))/2;
		QuitStringY = ButtonY+2*(ButtonHeights+ButtonSeparation)+(int)(0.875*ButtonHeights);
	}
	
	public void NoMore3D() {
		ResumeButton3D = true;
		SaveButton3D = true;
		QuitButton3D = true;
	}
	
	
	
	public void tick() {
//		game.Inventory[0][5][1] = 50; //This is used by the programmer to add items to my inventory so that I can test new items that I add
//		game.Inventory[0][5][0] = 12;
			if (game.resized == true) {
			XYPositionFont = new Font("Cooper Black", Font.BOLD, Main_Game.HEIGHT/24);
			ShowXYPosition = XYPositionFont.getStringBounds("Position: "+(game.TileX+game.ChunkX*16)+", "+(game.TileY+game.ChunkY*16), new FontRenderContext(null, true, true));
			BackgroundBoxX = Main_Game.WIDTH/4;
			BackgroundBoxY = Main_Game.HEIGHT/4;
			BackgroundBoxWidth = Main_Game.WIDTH/2;
			BackgroundBoxHeight = Main_Game.HEIGHT/2;
			ButtonWidths = BackgroundBoxWidth;
			ButtonHeights = BackgroundBoxHeight/5;
			PauseFont = new Font("Cooper Black", Font.BOLD, ButtonHeights);
			ResumeStringSize = PauseFont.getStringBounds("Resume", new FontRenderContext(null, true, true));
			SaveStringSize = PauseFont.getStringBounds("Save", new FontRenderContext(null, true, true));
			QuitStringSize = PauseFont.getStringBounds("Quit", new FontRenderContext(null, true, true));
			ButtonSeparation = ButtonHeights;
			ButtonX = BackgroundBoxX;
			ButtonY = BackgroundBoxY;
			PauseFont = new Font("Cooper Black", Font.BOLD, ButtonHeights);
			ResumeStringX = ButtonX+(ButtonWidths-(int)(ResumeStringSize.getWidth()))/2;
			ResumeStringY = ButtonY+(int)(0.875*ButtonHeights);
			SaveStringX = ButtonX+(ButtonWidths-(int)(SaveStringSize.getWidth()))/2;
			SaveStringY = ButtonY+ButtonHeights+ButtonSeparation+(int)(0.875*ButtonHeights);
			QuitStringX = ButtonX+(ButtonWidths-(int)(QuitStringSize.getWidth()))/2;
			QuitStringY = ButtonY+2*(ButtonHeights+ButtonSeparation)+(int)(0.875*ButtonHeights);
		}
		
		if (!game.Paused)
		{
			NoMore3D();
		}
	}
	
	public void render(Graphics g) {
		g.setFont(XYPositionFont);
		g.setColor(Color.black);
		g.drawString("Position: "+(game.TileX+game.ChunkX*16)+", "+(game.TileY+game.ChunkY*16), 0, (int) ShowXYPosition.getHeight());
		Color col = new Color(0, 0, 0, 100);
		g.setColor(col);
		
		if (game.Paused) {
			g.setColor(BackgroundBoxColor);
			g.fillRect(BackgroundBoxX, BackgroundBoxY, BackgroundBoxWidth, BackgroundBoxHeight);
			g.setColor(PauseButtonColor);
			g.fill3DRect(ButtonX, ButtonY, ButtonWidths, ButtonHeights, ResumeButton3D);
			g.fill3DRect(ButtonX, ButtonY+ButtonHeights+ButtonSeparation, ButtonWidths, ButtonHeights, SaveButton3D);
			g.fill3DRect(ButtonX, ButtonY+2*(ButtonHeights+ButtonSeparation), ButtonWidths, ButtonHeights, QuitButton3D);
			g.setFont(PauseFont);
			g.setColor(PauseStringColor);
			g.drawString("Resume", ResumeStringX, ResumeStringY);
			g.drawString("Save", SaveStringX, SaveStringY);
			g.drawString("Quit", QuitStringX, QuitStringY);
		}
	}
}
