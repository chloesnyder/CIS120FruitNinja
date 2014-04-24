/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	// the state of the game logic
//	private Square square; // the Black Square, keyboard control
//	private Square circle; // the Golden Snitch, bounces
	//private Poison poison; // the Poison Mushroom, doesn't move
	
	
	//A single block to fall
	private Fruit fruit;

	//timer
	Timer timer;
	
	public boolean playing = false; // whether the game is running
	private JLabel status; // Current status text (i.e. Running...)
	public boolean isPaused = false;
	

	// Game constants
	public static final int COURT_WIDTH = 300;
	public static final int COURT_HEIGHT = 500;
	public static final int FRUIT_VELOCITY = 3;
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;

	public GameCourt(JLabel status) {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		// This key listener allows the square to move as long
		// as an arrow key is pressed, by changing the square's
		// velocity accordingly. (The tick method below actually
		// moves the square.)
		
//		addKeyListener(new KeyAdapter() {
//			public void keyPressed(KeyEvent e) {
//				if (!fruit.isStopped) {
//					if (e.getKeyCode() == KeyEvent.VK_LEFT)
//						fruit.v_x = -FRUIT_VELOCITY;
//					else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
//						fruit.v_x = FRUIT_VELOCITY;
//					else if (e.getKeyCode() == KeyEvent.VK_DOWN)
//						fruit.v_y = FRUIT_VELOCITY * 2;
//				}
//
//				// TODO: make it rotate
//				// else if (e.getKeyCode() == KeyEvent.VK_UP)
//				// fruit.v_y = -SQUARE_VELOCITY;
//			}
//
//			public void keyReleased(KeyEvent e) {
//				if (!fruit.isStopped) {
//					// falls straight down if no key
//					fruit.v_x = 0;
//					fruit.v_y = FRUIT_VELOCITY;
//				}
//			}
//		});

		this.status = status;
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {
		
		fruit = new Fruit((int) 
				(Math.random() * COURT_WIDTH), COURT_HEIGHT);

		playing = true;
		status.setText("Running...");

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}
	
//	TODO Implement Pause, and play again
	public void pause() {
		
		isPaused = !isPaused;
		
		if (isPaused) {
			timer.stop();
			playing = false;
			fruit.v_x = 0;
			fruit.v_y = 0;
			JOptionPane.showMessageDialog(null, 
					"Welcome to Fruit Ninja. \nTo play, click and drag across"
					+ "a falling fruit to \"slice\" it apart. The game ends"
					+ "when the timer runs out. Unsliced fruit piles up at the"
					+ "bottom of the board."
					+ "\n Features include:"
					+ "\n-slicing fruits that have already been sliced for"
					+ "extra points. These slices fall faster"
					+ "\n-multiple levels of difficulty. Higher levels have"
					+ "more fruit falling faster"
					+ "\n-garbage mode. The fruit piles up on the ground"
					+ "and the game ends when it piles up to the top of the"
					+ "board");
			status.setText("Paused...");
		} else {
			playing = true;
			timer.start();
			fruit.move();
		}

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}
	

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (playing) {
			// advance the block in current direction.
			fruit.move();
			
			//generates a new fruit when one goes off screen
			if (fruit.pos_y < 0) {
				fruit = new Fruit(COURT_WIDTH, COURT_HEIGHT);
				fruit.move();
			}

			// make the fruit bounce off walls...
			fruit.bounce(fruit.hitWall());
			
			//TODO: modify so that it collides with knife?
			// ...and the mushroom
//			fruit.bounce(fruit.hitObj(poison));

			
			//TODO: Check for game end conditions, i.e piled up
			
			// check for the game end conditions
//			if (square.intersects(poison)) {
//				playing = false;
//				status.setText("You lose!");
//
//			} else if (square.intersects(snitch)) {
//				playing = false;
//				status.setText("You win!");
//			}

			// update the display
			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		fruit.draw(g);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
