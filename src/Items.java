import java.awt.Rectangle;
import java.io.Serializable;

import javax.imageio.ImageIO;

public abstract class Items implements Serializable{
	private int width;
	private int length;
	private int x;
	private int y;
	private int x_vel = -8;
	public static final int Y_VEL = 0;
	public static int plane_Xvel = -10;
	public static int car_Xvel = -9;
	public static int fly_Xvel = -8;
	public static int snail_Xvel = -6;
	private ItemsID itemID;
	
	//Constructor
	public Items(int x, int y, int width, int length, int x_vel, ItemsID itemID) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.length = length;
		this.x_vel = x_vel;
		this.itemID = itemID;
	}
	
	
	//getters and setters
	
	public ItemsID getItemID() {
		return itemID;
	}

	public int getX_vel() {
		return x_vel;
	}


	public void setX_vel(int x_vel) {
		this.x_vel = x_vel;
	}


	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	/**
	 *  created rectangle around the item (hitbox)
	 */
	public Rectangle bounds() {
		return (new Rectangle(x,y,getLength(),getWidth()));
	}

	//abstract methods to be implemented by subclasses
	public abstract boolean beHit();
	public abstract void exit();
}
