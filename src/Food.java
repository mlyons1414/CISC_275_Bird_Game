public class Food extends Items{
	
	public Food(int x, int y, int width, int length, int x_vel, ItemsID itemID) {
		super(x, y, width, length, x_vel, itemID);

	}
	
	/**
	 * return true if food collides with bird and calls exit();
	 */
	@Override
	public boolean beHit() {
		return false;
	}
	
	/**
	 * makes the food disappear on the screen
	 */
	@Override
	public void exit() {}
}