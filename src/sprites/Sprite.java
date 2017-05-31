package sprites;		

import java.awt.*;

/**
 * This class is the parent of all of the sprites used in the game, 
 * including the ball, the paddle, and the bricks. It contains all of
 * the information needed to animate the sprites.
 * @author Abdul-Azeez Rabiu
 */
public abstract class Sprite extends Rectangle{
	
	/**
	 * Color of the sprite
	 */
	protected Color spriteColor;

	/**
	 * Default constructor
	 */
	public Sprite(){
	}

	/**
	 * Specific constructor that sets all the attributes of the sprite
	 * @param someX
	 * @param someY
	 * @param someWidth
	 * @param someHeight
	 * @param someColor
	 */
	public Sprite(int someX, int someY, int someWidth, int someHeight, Color someColor){
		
		// Setting the size and location of the sprite to the parameters passed
		this.setLocation(someX, someY);
		this.setSize(someWidth, someHeight);
		
		spriteColor = someColor;
	}
	
	/**
	 * Handles the drawing of the Sprite. It is up to a child class what goes in here.
	 * @param pen
	 */
	public abstract void draw(Graphics pen);

}

