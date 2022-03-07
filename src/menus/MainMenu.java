package menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import core.Main_Game;

public class MainMenu {

	private Main_Game game;

	//Colors for all components
	Color backgroundBoxColor = new Color(70, 70, 70, 185);
	Color singleplayerBoxColor = new Color(100, 200, 20);
	Color singleplayerStringColor = new Color(50, 80, 10);
	Color quitGameBoxColor = new Color(255, 40, 40);
	Color quitGameStringColor = new Color(130, 10, 10);
	//Dimensions for all the objects on the title screen
	private int titleFontDims = (Main_Game.WIDTH+Main_Game.HEIGHT)/30;
	private Font titleFont = new Font("Cooper Black", Font.BOLD, titleFontDims);
	private Rectangle2D titleStringSize = titleFont.getStringBounds("Project Adapt", new FontRenderContext(null, true, true));
	private int titleX = Main_Game.WIDTH/2-(int)(titleStringSize.getWidth()/2);
	private int titleY = (int) -titleStringSize.getY();
	
	private int singleplayerFontDims = (Main_Game.WIDTH+Main_Game.HEIGHT)/50;
	private Font singleplayerFont = new Font("Cooper Black", Font.BOLD, singleplayerFontDims);
	private Rectangle2D singleplayerStringSize = singleplayerFont.getStringBounds("SINGLEPLAYER", new FontRenderContext(null, true, true));
	private int singleplayerX = Main_Game.WIDTH/2-(int)(singleplayerStringSize.getWidth()/2);
	private int singleplayerY = (int) (Main_Game.HEIGHT/4+(singleplayerStringSize.getHeight())-singleplayerStringSize.getHeight()/8);
	public int singleplayerBoxWidth = Main_Game.WIDTH/3;
	public int singleplayerBoxHeight = (int)singleplayerStringSize.getHeight();
	public int singleplayerBoxX = Main_Game.WIDTH/2-singleplayerBoxWidth/2;
	public int singleplayerBoxY = Main_Game.HEIGHT/4;
	
	private Rectangle2D quitGameStringSize = singleplayerFont.getStringBounds("QUIT GAME", new FontRenderContext(null, true, true));
	private int quitGameX = Main_Game.WIDTH/2-(int)(quitGameStringSize.getWidth()/2);
	private int quitGameY = (int) (Main_Game.HEIGHT/4+(quitGameStringSize.getHeight())-quitGameStringSize.getHeight()/8+singleplayerBoxHeight*4);
	public int quitGameBoxWidth = Main_Game.WIDTH/3;
	public int quitGameBoxHeight = (int)quitGameStringSize.getHeight();
	public int quitGameBoxX = Main_Game.WIDTH/2-quitGameBoxWidth/2;
	public int quitGameBoxY = Main_Game.HEIGHT/4+singleplayerBoxHeight*4;
	
	private int backgroundBoxWidth = (int) (singleplayerBoxWidth*(1.5));
	private int backgroundBoxHeight = Main_Game.HEIGHT*3/4;
	private int backgroundBoxX = Main_Game.WIDTH/2-backgroundBoxWidth/2;
	private int backgroundBoxY = (int) (titleStringSize.getHeight()/16);

	public boolean singleplayerButton3D = true;
	public boolean quitGameButton3D = true;

	public MainMenu(Main_Game game) {
		this.game = game;
	}

	public void tick() {
		if (game.resized == true) {
			//resets the sizes of the objects on the screen if the screen gets resized
			game.resizeBackground();
			titleFontDims = (Main_Game.WIDTH+Main_Game.HEIGHT)/30;
			titleFont = new Font("Cooper Black", Font.BOLD, titleFontDims);
			titleStringSize = titleFont.getStringBounds("Project Adapt", new FontRenderContext(null, true, true));
			titleX = Main_Game.WIDTH/2-(int)(titleStringSize.getWidth()/2);
			titleY = (int) -titleStringSize.getY();
			singleplayerFontDims = (Main_Game.WIDTH+Main_Game.HEIGHT)/50;
			singleplayerFont = new Font("Cooper Black", Font.BOLD, singleplayerFontDims);
			singleplayerStringSize = singleplayerFont.getStringBounds("SINGLEPLAYER", new FontRenderContext(null, true, true));
			singleplayerX = Main_Game.WIDTH/2-(int)(singleplayerStringSize.getWidth()/2);
			singleplayerY = (int) (Main_Game.HEIGHT/4+(singleplayerStringSize.getHeight())-singleplayerStringSize.getHeight()/8);
			singleplayerBoxWidth = Main_Game.WIDTH/3;
			if (((int) singleplayerStringSize.getWidth()*1.2) > singleplayerBoxWidth) {
				singleplayerBoxWidth = (int) (singleplayerStringSize.getWidth()*1.2);
				//System.out.println(true);
			}
			singleplayerBoxHeight = (int)singleplayerStringSize.getHeight();
			singleplayerBoxX = Main_Game.WIDTH/2-(singleplayerBoxWidth/2);
			singleplayerBoxY = Main_Game.HEIGHT/4;
			backgroundBoxWidth = (int) (singleplayerBoxWidth*(1.5));
			backgroundBoxHeight = Main_Game.HEIGHT*3/4;
			backgroundBoxX = Main_Game.WIDTH/2-backgroundBoxWidth/2;
			backgroundBoxY = (int) (titleStringSize.getHeight()/16);
			
			quitGameStringSize = singleplayerFont.getStringBounds("QUIT GAME", new FontRenderContext(null, true, true));
			quitGameX = Main_Game.WIDTH/2-(int)(quitGameStringSize.getWidth()/2);
			quitGameY = (int) (Main_Game.HEIGHT/4+(quitGameStringSize.getHeight())-quitGameStringSize.getHeight()/8+singleplayerBoxHeight*4);
			quitGameBoxWidth = Main_Game.WIDTH/3;
			quitGameBoxHeight = (int)quitGameStringSize.getHeight();
			quitGameBoxX = Main_Game.WIDTH/2-quitGameBoxWidth/2;
			quitGameBoxY = Main_Game.HEIGHT/4+singleplayerBoxHeight*4;
			if (((int) quitGameStringSize.getWidth()*1.2) > quitGameBoxWidth) {
				quitGameBoxWidth = (int) (quitGameStringSize.getWidth()*1.2);
				//System.out.println(true);
			}
			//System.out.println("resizing main menu");
		}
	}

	public void render(Graphics g) {
		g.drawImage(game.BackgroundArt, 0, 0, game.BackgroundArtWidth, game.BackgroundArtHeight, null);
		g.setColor(backgroundBoxColor);
		g.fillRect(backgroundBoxX, backgroundBoxY, backgroundBoxWidth, backgroundBoxHeight);
		g.setColor(game.titleRedColor);
		g.setFont(titleFont);
		g.drawString("Project Adapt", titleX, titleY);

		g.setColor(singleplayerBoxColor);
		g.fill3DRect(singleplayerBoxX, singleplayerBoxY, singleplayerBoxWidth, singleplayerBoxHeight, singleplayerButton3D);
		g.setColor(singleplayerStringColor);
		g.setFont(singleplayerFont);
		g.drawString("SINGLEPLAYER", singleplayerX, singleplayerY);
		
		g.setColor(quitGameBoxColor);
		g.fill3DRect(quitGameBoxX, quitGameBoxY, quitGameBoxWidth, quitGameBoxHeight, quitGameButton3D);
		g.setColor(quitGameStringColor);
		g.setFont(singleplayerFont);
		g.drawString("QUIT GAME", quitGameX, quitGameY);
	}
}
