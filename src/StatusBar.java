
import java.io.Serializable;

public class StatusBar implements Serializable{
	private final int X;
	private final int Y;
	public final static int WIDTH = 300;
	public final static int LENGTH = 32;
	private int status = 300;
	
	public StatusBar(int x, int y) {
		this.X = x;
		this.Y = y;

	}

	//getters & setters
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getX() {
		return X;
	}
	public int getY() {
		return Y;
	}
}
