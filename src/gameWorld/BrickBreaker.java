package gameWorld;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import sprites.*;

/**
 * The arena where all the action in the game takes place
 * @author Abdul-Azeez Rabiu
 */
public class BrickBreaker extends JFrame implements KeyListener, Runnable{

	/**
	 *  Graphics for buffer
	 */
	private Graphics pen;

	/**
	 *  For double buffering
	 */
	private Image buffer;
	
	/**
	 * The thread that will be used to animate the game
	 */
	private Thread thread;

	/**
	 * Game sprites
	 */
	private Ball ball;
	private Paddle paddle;
	private BrickMason brick;
	
	/**
	 * What level are we on?
	 */
	private int level;
	
	/**
	 * Is the game running?
	 * Initially, this should be false until the player manually starts the game
	 */
	private boolean running = false;
	
	/**
	 * Booleans for checking whether or not an arrow key is being pressed
	 */
	private boolean leftArrowPressed, rightArrowPressed;
	
	/**
	 * The size of the game window
	 */
	private int winWidth, winHeight;
	
	/**
	 * For message boxes to communicate with the player
	 */
	private JFrame dialog;
	
	/**
	 * This array will be used for holding the bricks and the 
	 * coordinates of where the bricks will go in each level. 
	 * Each sub-array represents a different level. 
	 */
	private Brick[][] masterBrickArray = new Brick[3][];

	public BrickBreaker(){
		// Setting the size and location of the window based on the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		winWidth = (int)(screenSize.width * .333);
		winHeight = (int)(screenSize.height * .75);
		int winY = (screenSize.height - winHeight)/2;
		setSize(winWidth, winHeight);
		setLocation(winWidth, winY);

		// For handling keyboard input
		addKeyListener(this);
		
		// Initially on Level 1
		level = 1;
		// Instantiate the sprites
		ball = new Ball(this.getWidth() / 2, this.getHeight() * 3/4, Ball.BALL_SIZE, Ball.BALL_SIZE, Color.blue);
		paddle = new Paddle(this.getWidth() / 2, this.getHeight() - Paddle.P_HEIGHT, 
				Paddle.P_WIDTH, Paddle.P_HEIGHT, Color.red);
		brick = new BrickMason();
		for(int i = 0; i < masterBrickArray.length; i++){
			masterBrickArray[i] = Arrays.copyOf(brick.brickMason(i + 1), brick.brickMason(i + 1).length);
		}
		
		dialog = new JFrame("");
		if(!running){
			JOptionPane.showMessageDialog(dialog, "Break some Bricks! \nLevel 1");
			running = true;

		}
		
		thread = new Thread(this);
		thread.start();
		// Makes the window appear and prevents it from being re-sized
		setVisible(true);
		setResizable(false);

	}
	
	/**
	 * Draws every frame after the components get updated
	 * @param pane
	 */
	public void paint(Graphics pane){

		// Double buffer
		if(buffer == null){
			buffer = createImage(this.getSize().width, this.getSize().height);
			pen = buffer.getGraphics();
		}
		pane.drawImage(buffer, 0, 0, null);		

		// Paint the background
		pen.setColor(Color.black);
		pen.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// Painting the sprites
		brick.draw(pane, level);
		ball.draw(pane);
		paddle.draw(pane);
		
		// The HUD (heads-up display) of the game
		pane.setColor(Color.WHITE);
		pane.drawString("Lives: " + ball.stats.getLife(), 450, 80);
		pane.drawString("Score: " + ball.stats.getScore(), 50, 80);
	}
	
	/**
	 * Listens for when an arrow key is pressed then 
	 * sets the respective boolean to true
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			leftArrowPressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			rightArrowPressed = true;
	
		}
	}
	
	/**
	 * Listens for when an arrow key is released then 
	 * sets the respective boolean to false
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			leftArrowPressed = false;
			
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			rightArrowPressed = false;
		}
	}
	
	/**
	 * The for each loop in this method checks to see if the boolean value in question
	 * is inside of an array of booleans. Will be used to search the "destroyed" array
	 * for a "false" boolean; if there exists a "false", then the game is not yet won, if
	 * there is no "false", then all of the bricks must have been destroyed. 
	 * @param array The array of booleans that is being searched through, which will
	 * be the "destroyed" array in this case
	 * @param target The boolean we are looking for
	 * @return - Boolean for whether the player has won or not
	 */
	public boolean won(boolean[] array, boolean target) {
		// A sub-array from the original one based on the length of the sub-array
		// of bricks for the current level
		boolean[] subArray = Arrays.copyOfRange(array, 0, masterBrickArray[level - 1].length);
		for(boolean b: subArray){
			if(b == target)
				return false;
		}
		return true;
	}
	
	/**
	 * Called continuously by the program to animate the sprites
	 */
	public void run() {
		
		while(running){
			
			// Used to check which key is being pressed and also if the paddle 
			// has reached the edge of the window, in which case it will stop moving
			if(leftArrowPressed && paddle.x >= 0){
				paddle.x -= Paddle.P_SPEED;
			} else if (rightArrowPressed && (paddle.x + Paddle.P_WIDTH) < this.getWidth()) {
				paddle.x += Paddle.P_SPEED;
			}
			
			// Moves the ball and checks for a collision with the paddle
			ball.move();
			ball.paddleCollision(paddle);
			
			// Checks the masterBrickArray for a ball and brick collision
			for (int i = 0; i < masterBrickArray[level - 1].length; i++){
				ball.brickCollision(masterBrickArray[level - 1][i], i);
				brick.ballCollision(ball, level);
			}
			
			repaint();
			
			// Did the ball fall out of the screen?
			if (ball.lose()){
				running = false;
				if (ball.stats.getLife() == 0){
					JOptionPane.showMessageDialog(dialog, "GAME OVER! \nTry again! Score: " + ball.stats.getScore());
				}
				else {
					JOptionPane.showMessageDialog(dialog, "ALL YOUR BALL ARE BELONG TO US NOW \nLives: " + ball.stats.getLife());
					ball.setLocation(this.getWidth() / 2, this.getHeight() * 3/4);
					ball.randomVector();
					running = true;
				} // end inner if statements
			} // end outer if statement
			
			// Checks to see if all of the bricks have been destroyed, 
			// then moves onto the next level and prompts a dialog box 
			// that congratulates the player
			if (won(ball.brickCollide, false) && level != masterBrickArray.length){
				running = false;
				level = level + 1;
				JOptionPane.showMessageDialog(dialog, "You're a regular wrecking ball! \nLevel: " + level);
				ball.setLocation(this.getWidth() / 2, this.getHeight() * 3/4);
				ball.randomVector();
				brick.resetDestroyed();
				ball.resetBrickCollide();
				running = true;
			}
			else if (won(ball.brickCollide, false) && level == masterBrickArray.length){
				running = false;
				JOptionPane.showMessageDialog(dialog, "A WINNER IS YOU! \nScore: " + ball.stats.getScore());
			} // end if statements
			
			// Used for controlling how fast the game is animated
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} // end try and catch block
		} // end while loop
	} // end run method
	
	public void keyTyped(KeyEvent e) {
	}
	
	/**
	 * Starts the program
	 * @param args
	 */	
	public static void main(String args[]) {
		new BrickBreaker();
	}
}