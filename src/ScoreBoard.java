import java.io.Serializable;

public class ScoreBoard implements Serializable{
	public final static int LENGTH = 250;
	public final static int WIDTH = 60 ;
	public final static int X = 20;
	public final static int Y = 20;
	private int score = 0;
	
	//getters & setters
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
}
