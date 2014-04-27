/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

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

    private LinkedList<GameObj> fruitList = new LinkedList<GameObj>();

    // timer
    Timer timer;
    int timePassed = 0;
    int genBomb = 1 + ((int) (Math.random() * 5 + 1));

    public boolean playing = false; // whether the game is running
    private JLabel status; // Current status text (i.e. Running...)
    //TODO: get level and score labels to work
    private JLabel level_label;
    private JLabel score_label;
    public boolean isPaused = false;

    // mouse stuff
    Point pointStart = null;
    Point pointEnd = null;

    // Game constants
    public static final int COURT_WIDTH = 400;
    public static final int COURT_HEIGHT = 300;
    public int FRUIT_VELOCITY = -2;
    public int level = 1;
    public int score = 0;
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 30;

    // TODO: Implement mouse controls
    // Mouse controls
    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        if (playing) {
            int x = e.getX();
            int y = e.getY();
            for (int i = 0; i < fruitList.size(); i++) {
                GameObj fruit = fruitList.get(i);
                if (!isPaused) {
                    if (fruit.getClass().equals(Bomb.class)) {
                        if ((fruit.pos_x - 50 <= x && fruit.pos_x + 50 >= x)
                                && (fruit.pos_y - 30 <= y && fruit.pos_y + 30 >= y)) {
                            // timer.stop();
                            playing = false;
                            status.setText("GAME OVER!");
                        }
                    } else {

                        if ((fruit.pos_x - 30 <= x && fruit.pos_x + 30 >= x)
                                && (fruit.pos_y - 30 <= y && fruit.pos_y + 30 >= y)) {
                            score += 1 * level;
                            // score_label.setText("Score: " + score);
                            System.out.println("Score is " + score);
                            fruitList.remove(fruit);
                        }
                    }
                }
            }
        }
    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {

    }

    // iterates through list to move each fruit or bomb on screen
    private void fruitIterator() {
        if (playing) {
            for (int i = 0; i < fruitList.size(); i++) {
                GameObj fruity = fruitList.get(i);
                fruity.bounce(fruity.hitWall());
                for (int j = 0; j < fruitList.size(); j++) {
                    if (i != j) {
                        fruity.bounce(fruity.hitObj(fruitList.get(j)));
                        if (i != j - 1 && j - 1 >= 0) {
                            fruity.bounce(fruity.hitObj(fruitList.get(j - 1)));
                        }
                        if (i != j + 1 && j + 1 < fruitList.size()) {
                            fruity.bounce(fruity.hitObj(fruitList.get(j + 1)));
                        }
                    }
                }
                
                // TODO: make velocity updates work
                // update velocity so that it is realistic
                // fruity.v_y = (int) Math.sqrt((fruity.v_y * fruity.v_y) +
                // 2*10*fruity.pos_y);
                fruity.move();
                if (fruity.pos_y < 0) {
                    fruitList.remove(fruity);
                }
            }
        } else {
            //TODO: get death animations to work
            for (int i = 0; i < fruitList.size(); i++) {
                GameObj shake = fruitList.get(i);
                if (timePassed % 0 == 2) {
                    shake.pos_x += 1;
                } else {
                    shake.pos_x -=1;
                }
            }
        }
    }

    public GameCourt(JLabel status, JLabel level_label, JLabel score_label) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.WHITE);

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
        // addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        this.status = status;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        playing = true;
        timePassed = 0;
        genBomb = (int) (Math.random() * 7);
        score = 0;
        level = 1;
        FRUIT_VELOCITY = -2;
        fruitList = new LinkedList<GameObj>();
        fruitList.add(new Fruit(COURT_WIDTH, COURT_HEIGHT, (int) (Math.random()
                * COURT_WIDTH - 40), FRUIT_VELOCITY));
        fruitIterator();
        if (isPaused) {
            status.setText("Will reset after game is unpaused");
        } else {
            status.setText("Running...");
        }
        timer.start();

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    // Implement Pause, and play again
    public void pause() {
        if (playing) {

            isPaused = !isPaused;

            if (isPaused) {
                timer.stop();
                JOptionPane
                        .showMessageDialog(
                                null,
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
                timer.start();
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
        timePassed += 1;
        if (playing && !isPaused) {

            while (fruitList.size() < 4) {
                if (timePassed % genBomb == 0) {
                    fruitList.add(new Bomb(COURT_WIDTH, COURT_HEIGHT,
                            (int) (Math.random() * COURT_WIDTH - 40),
                            FRUIT_VELOCITY));
                }

                fruitList.add(new Fruit(COURT_WIDTH, COURT_HEIGHT, (int) (Math
                        .random() * COURT_WIDTH - 40), FRUIT_VELOCITY));

            }
            fruitIterator();
            // animation for bomb
        } else {
            while (!playing && !isPaused) {
                if (timePassed % 2 == 0) {
                    setBackground(Color.YELLOW);
                } else {
                    setBackground(Color.RED);
                }
            }
            fruitIterator();
        }
        repaint();
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

    public void level_up() {
        if (level <= 12 && playing) {
            level += 1;
            FRUIT_VELOCITY -= 1;
            System.out.println("I'm level " + level);
            level_label.setText("Level " + level);
        }
    }

    public void level_down() {
        if (level > 0 && playing) {
            level -= 1;
            FRUIT_VELOCITY += 1;
            level_label.setText("Level " + level);
            System.out.println("I'm level " + level);
        }
    }

}
