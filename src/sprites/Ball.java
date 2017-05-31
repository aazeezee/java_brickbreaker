package sprites;

import java.awt.*;
import java.util.*;

import gameWorld.*;

/**
 * Manages the wrecking ball that performs the most complex actions in the game.
 * Uses collision detection to interact with the other components of the game.
 * @author Abdul-Azeez Rabiu
 */
public class Ball extends Sprite{
	/**
	 * X and Y velocity of the ball
	 */
	private int xVeloz, yVeloz;
	
	/**
	 * Will manage the score and lives based on the ball's behavior
	 */
	public Stats stats;
	
	/**
	 * Size of the screen
	 */
	private int winWidth, winHeight;

	/**
	 * Will increment every time the ball collides with the paddle and also simultaneously 
	 * increment the ball's speed, raising the level of difficulty and preventing the game 
	 * from dragging on too long
	 */
	private int difficulty = 0;

	/**
	 * Array of Booleans used for checking if the ball has collided with a brick.
	 * Mirrors the "destroyed" array in BrickMason
	 */
	public boolean[] brickCollide = new boolean[50];

	/**
	 * Diameter of the ball
	 */
	public static final int BALL_SIZE = 16;

	/**
	 * Default constructor automatically calls default constructor of parent
	 */
	public Ball(){
		// Initially, the ball should not have collided with any bricks
		resetBrickCollide();
	}

	/**
	 * Specific constructor that lets the parent class set the attributes
	 * @param someX
	 * @param someY
	 * @param someWidth
	 * @param someColor
	 */
	public Ball(int someX, int someY, int someWidth, int someHeight, Color someColor){
		super(someX, someY, someWidth, someHeight, someColor);
		
		stats = new Stats();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		winWidth = (int)(screenSize.width * .333);
		winHeight = (int)(screenSize.height * .75);

		randomVector();

		// Initially, the ball should not have collided with any bricks
		resetBrickCollide();
	}
	
	/**
	 * Randomly sets the X and Y velocities of the ball every time the game is restarted.
	 * Also makes sure that the X and Y velocities do not equal 0, -1, or 1, as these 
	 * values would either make the ball travel at a right angle or travel very slowly.
	 */
	public void randomVector(){
		Random rand = new Random();
		xVeloz = rand.nextInt(6) - 3;
		if (xVeloz == -1){
			xVeloz = -4;
		}
		else if (xVeloz == 0){
			xVeloz = 3;
		}
		else if (xVeloz == 1){
			xVeloz = 2;
		}
		yVeloz = rand.nextInt(4) + 2;
	}
	
	/**
	 * Resets the "brickCollide" array (which mirrors the "destroyed" array) so that 
	 * it is full of nothing but "false" booleans; necessary when the player advances 
	 * to a new level to ensure that the new bricks are drawn
	 */
	public void resetBrickCollide(){
		for (int i = 0; i < brickCollide.length; i++){
			brickCollide[i] = false;
		}
	}
	
	/**
	 * Governs the ball's movement.
	 * Also checks whether or not the ball has collided into a wall, then manages 
	 * its velocity accordingly.
	 */
	public void move(){

		if(x >= 0 || (x + BALL_SIZE) < winWidth){
			x = x + xVeloz;
		}

		if(y >= 0){
			y = y - yVeloz;
		}

		// Check for left and right wall collision
		if(x <= 0 || (x + BALL_SIZE) >= winWidth){
			xVeloz = xVeloz * -1;
			x = x + xVeloz;
		}

		// Check for top wall collision
		if(y <= 20){
			yVeloz = yVeloz * -1;
			y = y - yVeloz;
		}
	}
	
	/**
	 * Checks to see if ball has fallen through the bottom of the screen, 
	 * in which case the ball should stop moving, the velocity and difficulty
	 * is reset and the game should momentarily stop running
	 * @return isLost Did the ball fall out of the screen?
	 */
	public boolean lose(){
		boolean isLost = false;
		if(y + 3 > winHeight){
			y = 3 + winHeight;
			x = winWidth;
			difficulty = 0;
			randomVector();
			stats.decreaseLife();
			isLost = true;
		}
		return isLost;
	}
	
	/**
	 * Checks whether the ball has struck the paddle or not
	 * @param paddle
	 */
	public void paddleCollision(Paddle paddle){
		// Does the ball hit or "intersect" the paddle?
		if(this.intersects(paddle)){

			// Difficulty is increased every time the ball hits the paddle
			difficulty = difficulty + 1;
			// The ball's speed is increased every time the difficulty reaches a certain point
			// Math.signum() is used to make sure the ball stays in the direction that it was
			// going prior to hitting the paddle.
			if (difficulty == 5){
				xVeloz = xVeloz + ((int)Math.signum(xVeloz) * 1);
				yVeloz = yVeloz + ((int)Math.signum(yVeloz) * 1);
			}
			else if (difficulty == 10){
				xVeloz = xVeloz + ((int)Math.signum(xVeloz) * 2);
				yVeloz = yVeloz + ((int)Math.signum(yVeloz) * 2);
			}
			else if (difficulty == 15){
				xVeloz = xVeloz + ((int)Math.signum(xVeloz) * 3);
				yVeloz = yVeloz + ((int)Math.signum(xVeloz) * 3);
			}
			else if (difficulty == 20){
				xVeloz = xVeloz + ((int)Math.signum(xVeloz) * 4);
				yVeloz = yVeloz + ((int)Math.signum(xVeloz) * 4);
			}
			yVeloz = yVeloz * -1;
			y = y - yVeloz;

			// Checks if the ball has hit one of the corners of the paddle,
			// allowing the player to guide the ball by hitting it in the
			// direction of the of the respective corner.
			if(x + BALL_SIZE/2 <= (paddle.x + Paddle.P_WIDTH / 5)){
				xVeloz = Math.abs(xVeloz) * -1;
			}
			else if (x + BALL_SIZE/2 >= (paddle.x + Paddle.P_WIDTH * 4/5)){
				xVeloz = Math.abs(xVeloz);
			} // end inner if statements
		} // end outer if statement 
	} // end paddleCollision method
	
	/**
	 * Checks whether ball has struck a brick or not
	 * @param brick
	 * @param index The index of the brick in its respective array
	 */
	public void brickCollision(Brick brick, int index){
		// Does the ball hit or "intersect" a brick?
		if(this.intersects(brick) && !brickCollide[index]){

			brickCollide[index] = true;
			stats.increaseScore();

			// This is the Rectangle made by the brief intersection between the ball 
			// and a brick. It will be used to determine whether the ball has hit a 
			// brick vertically or horizontally
			Rectangle intersection = this.getBounds().intersection(brick.getBounds());
			if (intersection.width > intersection.height){
				yVeloz = yVeloz * -1;
				y = y - yVeloz;
			} 
			else if (intersection.height >= intersection.width){
				xVeloz = xVeloz * -1;
				x = x - xVeloz;
			}
			else if (intersection.height == intersection.width){
				xVeloz = xVeloz * -1;
				x = x - xVeloz;
			} // end inner if statements
		} // end outer if statements
	} // end brickCollision method

	/**
	 * Draws the ball
	 * @param pen
	 */
	public void draw(Graphics pen) {
		pen.setColor(spriteColor);
		pen.fillOval(x, y, BALL_SIZE, BALL_SIZE);
		pen.setColor(Color.cyan);
		pen.fillOval(x + 4, y + 4, BALL_SIZE / 4, BALL_SIZE / 4);
	}

}
