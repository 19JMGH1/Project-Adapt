package entities.management;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import core.Main_Game;
import entities.EntityTypes;

public class DimensionName extends EntityObject{
	
	private boolean removed = false;
	private int cycle = 60;
	
	private int startingVel = Main_Game.WIDTH/40;
	private int yPos = Main_Game.HEIGHT/10;
	
	private String dimension = null;
	private Font dimsFont = new Font("Cooper Black", Font.PLAIN, (Main_Game.HEIGHT)/15);
	
	private Main_Game game;
	private Handler handler;

	public DimensionName(int x, int y, EntityTypes id, Main_Game game, Handler handler, int dimension) {
		super(x, y, id);
		
		this.game = game;
		this.handler = handler;
		
		this.x = -Main_Game.WIDTH/2;
		this.y = yPos;
		
		setVelX(startingVel);
		
		if (dimension == 0) { //The Surface
			this.dimension = "The Surface";
		}
		else if (dimension == 1) { //The Cove
			this.dimension = "The Cove";
		}
	}
	
	public void tick() {
		if (removed) {
			handler.removeObject(this);
		}
		
		if (game.resized) {
			dimsFont = new Font("Cooper Black", Font.PLAIN, (Main_Game.HEIGHT)/15);
			startingVel = Main_Game.WIDTH/40;
			y = yPos;
		}
		
		if (cycle > 0) {
			if (x > 0 && getVelX() >= Main_Game.WIDTH/200) {
				setVelX(getVelX()-Main_Game.WIDTH/500-1);
			}
			else {
				cycle--;
			}
		}
		else {
			if (getVelX() <= startingVel) {
				setVelX(getVelX()+Main_Game.WIDTH/500+1);
			}
			if (getX() >= Main_Game.WIDTH) {
				removed = true;
			}
		}
		
		x+=velX;
	}

	public void render(Graphics g) {
		g.setFont(dimsFont);
		g.setColor(Color.white);
		g.drawString(dimension, x+Main_Game.WIDTH/200, y);
		g.setColor(Color.black);
		g.drawString(dimension, x, y);
	}

}
