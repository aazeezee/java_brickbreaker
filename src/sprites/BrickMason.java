package sprites;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/**
 * This class is for managing the arrangement of the bricks to create a level
 * @author Abdul-Azeez Rabiu
 */
public class BrickMason {
	
	/**
	 * Size of a brick; based on the variables of the same name in the Brick class
	 */
	public static final int B_WIDTH = Brick.B_WIDTH, B_HEIGHT = Brick.B_HEIGHT;
	
	/**
	 * Coordinates of starting point for the bricks in each level
	 */
	private static final int BASE_X1 = 120, BASE_Y = 200, BASE_X2 = 80;
	
	/**
	 * Arrays for the brick coordinates of each level; the X coordinates have not been
	 * instantiated yet because they are much more complex than the Y coordinates and 
	 * will be instantiated later 
	 */
	public int[] xBox1 = new int[16], 
			yBox1 = {BASE_Y, BASE_Y, BASE_Y, BASE_Y, BASE_Y, 
			BASE_Y + B_HEIGHT, BASE_Y + B_HEIGHT, BASE_Y + B_HEIGHT, BASE_Y + B_HEIGHT, BASE_Y + B_HEIGHT, BASE_Y + B_HEIGHT,
			BASE_Y + (B_HEIGHT*2), BASE_Y + (B_HEIGHT*2), BASE_Y + (B_HEIGHT*2), BASE_Y + (B_HEIGHT*2), BASE_Y + (B_HEIGHT*2)},
			
			xBox2 = new int[20],
			yBox2 = {BASE_Y, BASE_Y, BASE_Y, BASE_Y, 
					BASE_Y + B_HEIGHT, BASE_Y + B_HEIGHT, 
					BASE_Y + (B_HEIGHT*2), BASE_Y + (B_HEIGHT*2), BASE_Y + (B_HEIGHT*2), BASE_Y + (B_HEIGHT*2), 
					BASE_Y + (B_HEIGHT*7), BASE_Y + (B_HEIGHT*7), BASE_Y + (B_HEIGHT*7), BASE_Y + (B_HEIGHT*7), 
					BASE_Y + (B_HEIGHT*8), BASE_Y + (B_HEIGHT*8),
					BASE_Y + (B_HEIGHT*9), BASE_Y + (B_HEIGHT*9), BASE_Y + (B_HEIGHT*9), BASE_Y + (B_HEIGHT*9)},
			
			xBox3 = new int[24], 
			yBox3 = {BASE_Y, BASE_Y, BASE_Y, BASE_Y, BASE_Y, BASE_Y, 
			BASE_Y + B_HEIGHT, BASE_Y + B_HEIGHT, 
			BASE_Y + (B_HEIGHT*2), BASE_Y + (B_HEIGHT*2), 
			BASE_Y + (B_HEIGHT*5), BASE_Y + (B_HEIGHT*5), BASE_Y + (B_HEIGHT*6), BASE_Y + (B_HEIGHT*6),
			BASE_Y + (B_HEIGHT*9), BASE_Y + (B_HEIGHT*9), 
			BASE_Y * 2, BASE_Y * 2, 
			BASE_Y * 2 + B_HEIGHT, BASE_Y * 2 + B_HEIGHT, BASE_Y * 2 + B_HEIGHT, 
			BASE_Y * 2 + B_HEIGHT, BASE_Y * 2 + B_HEIGHT, BASE_Y * 2 + B_HEIGHT,};
	
	/**
	 * These arrays will be used for holding the bricks and the 
	 * coordinates of where the bricks will go in each level
	 */
	private Brick[] brickArray1 = new Brick[xBox1.length], 
			brickArray2 = new Brick[xBox2.length], 
			brickArray3 = new Brick[xBox3.length];
	
	/**
	 * Used for keeping track of whether each brick has been hit or not. This array 
	 * can be unnecessarily long just to make sure it's long enough for each level. 
	 * It's length is otherwise irrelevant because that value is never checked for
	 * the logic of the game
	 */
	private boolean[] destroyed = new boolean[50];
	
	/**
	 * Default constructor
	 */
	public BrickMason(){
		// Initially, no bricks should be destroyed
		resetDestroyed();
	}
	
	/**
	 * Specific constructor
	 * @param someLevel What level are we on?
	 */
	public BrickMason(int level){
		
		// Initially, no bricks should be destroyed
		resetDestroyed();
		
		// Set those bricks!
		brickMason(level);
		
	}
	
	/**
	 * "Lays down the bricks" according to level by arranging the necessary coordinates
	 * into an array of Point2D objects, then instantiates the bricks in each array 
	 * with the organized coordinates.
	 * @param level What level are we on?
	 */
	public Brick[] brickMason(int level){
		// An array for holding Point2D objects for the bricks' X and Y coordinates 
		Point2D.Double[] plots = new Point2D.Double[xBox1.length];
		// The array of bricks that will be returned; it is empty for now
		Brick[] brickArray = new Brick[0];
		
		/* These if statements and loops are for first checking what level it currently
		* is, then looping through the corresponding "xBox" array to set the X coordinates, 
		* then looping through the "plots" array to set up the complete coordinates.
		*/
		// For the first level
		if (level == 1){
			for(int i = 0; i < xBox1.length; i++){
				if (i < 5){
					xBox1[i] = BASE_X1 + (i * B_WIDTH);
				}
				else if (i >= 5 && i <= 10){
					xBox1[i] = (BASE_X1 - B_WIDTH / 2) + (i * B_WIDTH) - (B_WIDTH * 5);
				}
				else if (i >= 11){
					xBox1[i] = BASE_X1 + (i * B_WIDTH) - (B_WIDTH * 11);
				}
			} // end coordinate setting loop
			for(int i = 0; i < xBox1.length; i++){
				plots[i] = new Point2D.Double();
				plots[i].setLocation(xBox1[i], yBox1[i]);
			} // end brick-laying loop
		} 
		
		// For the second level
		else if (level == 2){
			plots = new Point2D.Double[xBox2.length];
			for(int i = 0; i < xBox2.length; i++){
				if (i == 0 || i == 6 || i == 10 || i == 16){
					xBox2[i] = BASE_X2;
				}
				else if (i == 1 || i == 7 || i == 11 || i == 17){
					xBox2[i] = BASE_X2 * 2;
				}
				else if (i == 2 || i == 8 || i == 12 || i == 18){
					xBox2[i] = BASE_X2 * 5;
				}
				else if (i == 3 || i == 9 || i == 13 || i == 19){
					xBox2[i] = BASE_X2 * 6;
				}
				else if (i == 4 || i == 14){
					xBox2[i] = (BASE_X2 * 2) - 40;
				}
				else if (i == 5 || i == 15){
					xBox2[i] = (BASE_X2 * 6) - 40;
				}
			} // end coordinate setting loop
			for(int i = 0; i < xBox2.length; i++){
				plots[i] = new Point2D.Double();
				plots[i].setLocation(xBox2[i], yBox2[i]);
			} // end brick-laying loop
		} 
		
		// For the third level
		else if (level == 3){
			plots = new Point2D.Double[xBox3.length];
			for(int i = 0; i < xBox3.length; i++){
				if (i == 0 || i == 6 || i == 8 || i == 14 || i == 16 || i == 18){
					xBox3[i] = BASE_X2;
				}
				else if (i == 1 || i == 19){
					xBox3[i] = BASE_X2 * 2;
				}
				else if (i == 2 || i == 10 || i == 12 || i == 20){
					xBox3[i] = BASE_X2 * 3;
				}
				else if (i == 3 || i == 11 || i == 13 || i == 21){
					xBox3[i] = BASE_X2 * 4;
				}
				else if (i == 4 || i == 22){
					xBox3[i] = BASE_X2 * 5;
				}
				else if (i == 5 || i == 7 || i == 9 || i == 15 || i == 17 || i == 23){
					xBox3[i] = BASE_X2 * 6;
				}
			} // end coordinate setting loop
			for(int i = 0; i < xBox3.length; i++){
				plots[i] = new Point2D.Double();
				plots[i].setLocation(xBox3[i], yBox3[i]);
			} // end brick-laying loop
		} // end level-checking if statements
		
		
		// Now the actual bricks can be created with the proper coordinates
		if (level == 1){
			for(int i = 0; i < plots.length; i++){
				brickArray1[i] = new Brick((int)plots[i].getX(), (int)plots[i].getY(), B_WIDTH, B_HEIGHT, Color.gray);
			}
		}
		else if (level == 2){
			for(int i = 0; i < plots.length; i++){
				brickArray2[i] = new Brick((int)plots[i].getX(), (int)plots[i].getY(), B_WIDTH, B_HEIGHT, Color.gray);
			}
		}
		else if (level == 3){
			for(int i = 0; i < plots.length; i++){
				brickArray3[i] = new Brick((int)plots[i].getX(), (int)plots[i].getY(), B_WIDTH, B_HEIGHT, Color.gray);
			}
		} // end if statements
		
		// We must check by level again because arrays are stupid and immutable
		if (level == 1){
			brickArray = Arrays.copyOf(brickArray1, brickArray1.length);
		}
		else if (level == 2){
			brickArray = Arrays.copyOf(brickArray2, brickArray2.length);
		}
		else if (level == 3){
			brickArray = Arrays.copyOf(brickArray3, brickArray3.length);
		} // end if statements
		
		return brickArray;
	} // end brickMason method
	
	/**
	 * Resets the "destroyed" array so that it is full of nothing but "false" booleans;
	 * necessary when the player advances to a new level to ensure that the new bricks
	 * are drawn
	 */
	public void resetDestroyed(){
		for (int i = 0; i < destroyed.length; i++){
			destroyed[i] = false;
		}
	}
	
	/**
	 * Checks whether brick has been struck by the ball or not, then updates "destroyed" array
	 * @param ball
	 * @param level
	 */
	public void ballCollision(Ball ball, int level){
		// Set the destroyed boolean to true
		for (int i = 0; i < brickMason(level).length; i++){
			if(brickMason(level)[i].intersects(ball) && !destroyed[i]){
				destroyed[i] = true;
			}
		} // end for loop
	} // end ballCollision method
	
	/**
	 * Draws the bricks
	 * @param pen
	 * @param level
	 */
	public void draw(Graphics pen, int level){
		if (level == 1){
			for(int i = 0; i < brickMason(1).length; i++){
				if (!destroyed[i]){
					brickMason(1)[i].draw(pen);
				}
			} // end level one loop
		}
		else if (level == 2){
			for(int i = 0; i < brickMason(2).length; i++){
				if (!destroyed[i]){
					brickMason(2)[i].draw(pen);
				}
			} // end level two loop
		}
		else if (level == 3){
			for(int i = 0; i < brickMason(3).length; i++){
				if (!destroyed[i]){
					brickMason(3)[i].draw(pen);
				}
			} // end level three loop
		} // // end level-checking if statements
	} // end draw method
}