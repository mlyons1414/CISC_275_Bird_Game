public class Obstacle extends Items{

	//constructor
	public Obstacle(int x, int y, int width, int length, int x_vel, ItemsID itemID) {
		super(x, y, width, length, x_vel, itemID);
	}

	/**
	 * 
	 * Description: This method detects if the bird is get hit or not by the obstacle
	 * 				returns TRUE if hits,
	 * 						else FALSE
	 */
	public boolean beHit() {
		return false;
	}

	
	
	/**
	 * Description: THe method will be called if beHit() == TRUE;
	 * Effects: Obstacles disappear from the screen
	 */
	public void exit() {}
	
}
