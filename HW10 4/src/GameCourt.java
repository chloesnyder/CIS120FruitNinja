/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.LinkedList;
import java.awt.Color;  
import java.awt.Graphics;  
import java.awt.Point; 

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel implements MouseMotionListener {
	
	private LinkedList<Fruit> fruitList = new LinkedList<Fruit>();

	//timer
	Timer timer;
	
	public boolean playing = false; // whether the game is running
	private JLabel status; // Current status text (i.e. Running...)
	public boolean isPaused = false;
	
	//mouse stuff
	Point pointStart = null;
	Point pointEnd = null;
	
//	public int distance(Point one, Point two) {
//		int a = Math.abs(one.x - two.x);
//		int b = Math.abs(one.y - two.y);
//		return (int) (Math.sqrt((a*a) + (b*b)));
//	}
	

	// Game constants
	public static final int COURT_WIDTH = 400;
	public static final int COURT_HEIGHT = 300;
	public static final int FRUIT_VELOCITY = (int) (Math.random() * 6);
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 30;
	
	
	//TODO: Implement mouse controls
	//Mouse controls
    public void mousePressed(MouseEvent e) {
        
    }


    public void mouseReleased(MouseEvent e) {
    }


	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		for (int i = 0; i < fruitList.size(); i++) {
			Fruit fruit = fruitList.get(i);
			if (!isPaused) {
				if ((fruit.pos_x - 30 <= x  && fruit.pos_x + 30 >= x )
						&& (fruit.pos_y - 30 <= y  && fruit.pos_y + 30 >= y)) {
					fruitList.remove(fruit);
				}
			}
		}
	}



	public void mouseMoved(MouseEvent arg0) {
		
	}


	public void mouseClicked(MouseEvent arg0) {
	}


	public void mouseEntered(MouseEvent arg0) {	
	}


	public void mouseExited(MouseEvent arg0) {
		
	}
	
	
	//iterates through list to move each fruit on screen
	private void fruitIterator() {
		for (int i = 0; i < fruitList.size(); i++) {
			Fruit fruity = fruitList.get(i);
			fruity.bounce(fruity.hitWall());
			for (int j = 0; j < fruitList.size(); j ++) {
				if (i != j) {
					fruity.bounce(fruity.hitObj(fruitList.get(j)));
					if (i != j-1 && j-1 >= 0) {
						fruity.bounce(fruity.hitObj(fruitList.get(j - 1)));
					}
					if (i != j+1 && j+1 < fruitList.size()) {
						fruity.bounce(fruity.hitObj(fruitList.get(j + 1)));
					}
				}
			}
			//TODO: make velocity updates work
			//update velocity so that it is realistic
//			fruity.v_y = (int) Math.sqrt((fruity.v_y * fruity.v_y) + 2*10*fruity.pos_y);
			fruity.move();
			if (fruity.pos_y < 0) {
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
//		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		this.status = status;
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {
		
		fruitList = new LinkedList<Fruit>();
		fruitList.add(new Fruit(COURT_WIDTH, COURT_HEIGHT, 
				(int) (Math.random() * COURT_WIDTH - 40)));
		fruitIterator();

		playing = true;
		if (isPaused) {
		    status.setText("Will reset after game is unpaused");
		} else {
		    status.setText("Running...");
		}

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}
	
//	TODO Implement Pause, and play again
	public void pause() {
		
		isPaused = !isPaused;
		
		if (isPaused) {
			timer.stop();
			JOptionPane.showMessageDialog(null, 
					"Welcome to Fruit Ninja. \nTo play, click and drag across"
					+ "a falling fruit to \"slice\" it apart. The game ends"
					+ "if you cut open a bomb!"
					+ "\n Features include:"
					+ "\n-ball physics. The fruits collide with each other,"
					+ " walls, and the knife. Gravity in place for accurate"
					+ "projectile motion."
					+ "\n-multiple levels of difficulty. Higher levels have"
					+ "more fruit falling faster");
			status.setText("Paused...");
		} else {
		    status.setText("Running...");
			playing = true;
			timer.start();
		}

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}
	

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (playing && !isPaused) {
			//int numFruits = (int) (Math.random() * 3);
			// advance the block in current direction.
			if (fruitList.size() <= 3) {
			fruitList.add(new Fruit(COURT_WIDTH, COURT_HEIGHT, 
					(int) (Math.random() * COURT_WIDTH - 40)));
			}
			fruitIterator();
			
			//TODO: Check for game end conditions, i.e piled up
			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < fruitList.size(); i++) {
			fruitList.get(i).draw(g);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}

}
