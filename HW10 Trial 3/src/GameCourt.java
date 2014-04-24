/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.LinkedList;

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
	//private Fruit fruit;
	
	private LinkedList<Fruit> fruitList = new LinkedList<Fruit>();

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
	
	class Mouse implements MouseListener, MouseMotionListener {
		
		@Override
        public void mousePressed(MouseEvent e) {
            
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
          
        }

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	//iterates through list to move each fruit on screen
	private void fruitIterator() {
		for (int i = 0; i < fruitList.size(); i++) {
			Fruit fruity = fruitList.get(i);
			fruity.bounce(fruity.hitWall());
			fruity.move();
			if (fruity.pos_y > COURT_HEIGHT) {
				fruitList.remove(fruity);
			}
		}
	}

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

		this.status = status;
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {
		
		fruitList = new LinkedList<Fruit>();
		fruitList.add(new Fruit(COURT_WIDTH, COURT_HEIGHT, 
				(int) (Math.random() * 380 - 40)));
		fruitIterator();

		playing = true;
		status.setText("Running...");

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}
	
//	TODO Implement Pause, and play again
	public void pause() {
		
		isPaused = !isPaused;
		int[] vx = new int[fruitList.size()];
		int[] vy = new int[fruitList.size()];
		
		if (isPaused) {
			timer.stop();
			playing = false;
			for (int i = 0; i < fruitList.size(); i++) {
				Fruit fruity = fruitList.get(i);
				final int vlx = fruity.getvx();
				final int vly = fruity.getvy();
				vx[i] = vlx;
				vy[i] = vly;
				fruity.v_x = 0;
				fruity.v_y = 0;
			}
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
			for (int i = 0; i < fruitList.size(); i++) {
				Fruit fruity = fruitList.get(i);
				fruity.v_x = vx[i];
				fruity.v_y = vy[i];
			}
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
			if (fruitList.size() < 3) {
			fruitList.add(new Fruit(COURT_WIDTH, COURT_HEIGHT, 
					(int) (Math.random() * 380 - 40)));
			}
			fruitIterator();

			// make the fruit bounce off walls...
			//fruit.bounce(fruit.hitWall());
			
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
		for (int i = 0; i < fruitList.size(); i++) {
			fruitList.get(i).draw(g);
			Fruit fruit = fruitList.get(i);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
