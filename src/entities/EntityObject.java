package entities;

import java.awt.Graphics;

public abstract class EntityObject {

	protected int x;
	protected int y;
	protected EntityIDs id;
	protected int velX, velY;

	public EntityObject(int x, int y, EntityIDs id) {
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
	public void setId(EntityIDs id) {
		this.id = id;
	}
	public EntityIDs getId() {
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
