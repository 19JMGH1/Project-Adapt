package entities.management;

import java.awt.Graphics;

import entities.EntityTypes;

public abstract class EntityObject {

	protected int x;
	protected int y;
	protected EntityTypes id;
	protected int velX, velY;

	public EntityObject(int x, int y, EntityTypes id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public abstract void tick();
	public abstract void render(Graphics g);

	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getY() {
		return y;
	}
	public int getX() {
		return x;
	}
	public void setId(EntityTypes id) {
		this.id = id;
	}
	public EntityTypes getId() {
		return id;
	}
	public void setVelX(int velX) {
		this.velX = velX;
	}
	public int getVelX() {
		return velX;
	}
	public void setVelY(int velY) {
		this.velY = velY;
	}
	public int getVelY() {
		return velY;
	}
}
