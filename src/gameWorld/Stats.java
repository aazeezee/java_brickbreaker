package gameWorld;

/**
 * This class will be used to manage the player's score and lives
 * @author Abdul-Azeez Rabiu
 */
public class Stats {
	
	/**
	 * The player's score, which is initially 0
	 */
	private int score = 0;
	
	/**
	 * Amount of points the player will get each time they hit a brick
	 */
	private final int POINTS = 50;

	/**
	 * How many lives the player has
	 */
	private int lives;
	
	/**
	 * The number of lives the player has initially
	 */
	private final int LIVES = 3;
	
	/**
	 * Initializes the stats
	 */
	public Stats(){
		lives = LIVES;
		
	}
	
	/**
	 * Increases the score
	 */
	public void increaseScore(){
		score = score + POINTS;
	}
	
	/**
	 * Subtracts one of the player's lives when the ball falls through the bottom of the screen
	 * and displays a message telling the user of this and allowing them to regroup
	 */
	public void decreaseLife(){
		lives = lives - 1;
	}
	
	/**
	 * Returns the number of lives remaining
	 * @return lives
	 */
	public int getLife(){
		return lives;
	}
	
	/**
	 * Returns the current score
	 * @return score
	 */
	public int getScore(){
		return score;
	}
}
