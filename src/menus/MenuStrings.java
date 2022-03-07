package menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import core.Main_Game;

public class MenuStrings {
	
	public static void render(Graphics g, int x, int y, int fontType, int fontSize, String string, Color col) {
		g.setFont(new Font(string, fontType, fontSize));
		g.setColor(col);
	}
	
	public static void renderXCentered(Graphics g, int y, int fontType, int fontSize, String string, Color col) {
		Font font = new Font("Cooper Black", fontType, fontSize);
		g.setFont(font);
		g.setColor(col);
		Rectangle2D stringSize = font.getStringBounds(string, new FontRenderContext(null, true, true));
		int x = (int) (Main_Game.WIDTH/2 - stringSize.getWidth()/2);
		g.drawString(string, x, y);
	}
	
	public static Rectangle2D getStringSize(String string, int fontType, int fontSize) {
		Font font = new Font("Cooper Black", fontType, fontSize);
		Rectangle2D rect = font.getStringBounds(string, new FontRenderContext(null, true, true));
		return rect;
	}
}
