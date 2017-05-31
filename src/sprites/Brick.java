package sprites;

import java.awt.*;

/**
 * The objects whose "destruction" is the goal of the game.
 * Unlike the Ball or the Paddle these objects do not move, and 
 * will be instantiate many times to make a wall of bricks.
 * @author Abdul-Azeez Rabiu
 */
public class Brick extends Sprite{

	/**
	 * Size of a brick
	 */
	public static final int B_WIDTH = 80, B_HEIGHT = 20;
	
	/**
	 * Default constructor automatically calls default constructor of parent
	 */
	public Brick(){}

	/**
	 * Specific constructor that lets the parent class set the attributes
	 * @param someX
	 * @param someY
	 * @param someWidth
	 * @param someHeight
	 * @param someColor
	 */
	public Brick(int someX, int someY, int someWidth, int someHeight, Color someColor){
		super(someX, someY, someWidth, someHeight, Color.gray);
	}
	
	/**
	 * Draws the bricks
	 * @param pen
	 */
	public void draw(Graphics pen){
		pen.setColor(spriteColor);
		pen.fill3DRect(x, y, B_WIDTH, B_HEIGHT, true);
	}
}
