package sprites;

import java.awt.*;

/**
 * Manages the paddle that the player uses to interact with the game
 * @author Abdul-Azeez Rabiu
 */
public class Paddle extends Sprite{
	
	/**
	 * Size of the paddle
	 */
	public static final int P_WIDTH = 80, P_HEIGHT = 20;
	
	/**
	 * Speed of the paddle
	 */
	public static final int P_SPEED = 8;
	
	/**
	 * Default constructor automatically calls default constructor of parent
	 */
	public Paddle(){}
	
	/**
	 * Specific constructor that lets the parent class set the attributes
	 * @param someX
	 * @param someY
	 * @param someWidth
	 * @param someHeight
	 * @param someColor
	 */
	public Paddle(int someX, int someY, int someWidth, int someHeight, Color someColor){
		super(someX, someY, someWidth, someHeight, someColor);
	}
	
	/**
	 * Draws the paddle
	 * @param pen
	 */
	public void draw(Graphics pen) {
		pen.setColor(spriteColor);
		pen.fillRect(x, y, P_WIDTH, P_HEIGHT);
	}
	
}
