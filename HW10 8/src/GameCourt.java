/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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

    public boolean playing = false; // whether the game is running
    private JLabel status; // Current status text (i.e. Running...)
    private JLabel level_label;
    private JLabel score_label;
    private JLabel gravity_label;
    public boolean isPaused = false;

    // mouse stuff
    int fx;
    int fy;

    // Game constants
    public static final int COURT_WIDTH = 400;
    public static final int COURT_HEIGHT = 300;
    public double FRUIT_VELOCITY;
    public int level = 1;
    public int score = 0;
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 20;
    public boolean zeroGravityMode = false;

    // timer
    Timer timer;
    int timePassed = 0;
    int genBomb;

    // Mouse controls
    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        fx = e.getX();
        fy = e.getY();
    }

    public void mouseDragged(MouseEvent e) {
        // if (playing) {
        int x = e.getX();
        int y = e.getY();
        for (int i = 0; i < fruitList.size(); i++) {
            GameObj obj = fruitList.get(i);
            if (!isPaused) {
                if (obj.getClass().equals(Bomb.class)) {
                    if ((obj.pos_x - 20 <= x && obj.pos_x + 20 >= x)
                            && (obj.pos_y - 20 <= y && obj.pos_y + 20 >= y)) {
                        timer.stop();
                        playing = false;
                        status.setText("GAME OVER!");
                    }
                } else {
                    if (playing && (obj.pos_x - 30 <= x && obj.pos_x + 30 >= x)
                            && (obj.pos_y - 30 <= y && obj.pos_y + 30 >= y)) {
                        if (zeroGravityMode) {
                            score += level;
                        } else {
                            score += 10 * level;
                        }
                        score_label.setText("Score: " + score);
                        // imageSplit(x, y, fruit);
                        fruitList.remove(obj);
                        // fruitFall(fruit);
                    }
                }
            }
        }
    }

    // }

    // public void fruitFall(Fruit fruit) {
    // String fruitType = fruit.fruitType();
    // CutFruit cutFruit = new CutFruit(COURT_WIDTH, COURT_HEIGHT, fruit.pos_x,
    // fruit.pos_y, fruit.INIT_VEL_X, -fruit.INIT_VEL_Y, fruitType);
    // }

    public void mouseMoved(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {

    }

    // TODO: delete or use
    // //Split the fruit in half
    // public void imageSplit(int x, int y, GameObj obj) {
    // int width = Math.abs(fx - x);
    // int height = Math.abs(fy - y);
    // double dy = obj.height / height;
    // double dx = obj.width / width;
    // BufferedImage slice[] = new BufferedImage[2];
    //
    //
    // // int a = width * width;
    // // int b = height * height;
    // // double h = Math.sqrt(a + b);
    //
    //
    // }

    // iterates through list to move each fruit or bomb on screen
    private void fruitIterator() {
        if (playing) {
            for (int i = 0; i < fruitList.size(); i++) {
                GameObj fruity = fruitList.get(i);
             // bouncing off of walls enabled with zero gravity mode
                if (zeroGravityMode == true) {
                    for (int j = 0; j < fruitList.size(); j++) {
                        fruity.bounce(fruity.hitWall());
                        if (i != j) {
                            fruity.bounce(fruity.hitObj(fruitList.get(j)));
                            if (i != j - 1 && j - 1 >= 0) {
                                fruity.bounce(fruity.hitObj(fruitList
                                        .get(j - 1)));
                            }
                            if (i != j + 1 && j + 1 < fruitList.size()) {
                                fruity.bounce(fruity.hitObj(fruitList
                                        .get(j + 1)));
                            }
                        }
                    }
                }
                
                // updates velocity and position of each fruit
                fruity.updateVelocity(FRUIT_VELOCITY);
                fruity.move();

                if ((!zeroGravityMode && fruity.pos_y >= 330)
                        || (zeroGravityMode && fruity.pos_y <= 0)) {
                    fruitList.remove(fruity);
                    
                }
            }
        }
    }

    public GameCourt(JLabel status, JLabel level_label, JLabel score_label,
            JLabel gravity_label) {
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
        this.level_label = level_label;
        this.score_label = score_label;
        this.gravity_label = gravity_label;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        playing = true;
        timePassed = 0;
        genBomb = 10;
        zeroGravityMode = false;
        gravity_label.setText("Gravity is on");
        score = 0;
        level = 1;
        score_label.setText("Score: " + score);
        level_label.setText("Level: " + level);
        FRUIT_VELOCITY = -.4;
        fruitList = new LinkedList<GameObj>();
        fruitList.add(new Fruit(COURT_WIDTH, COURT_HEIGHT, (int) (Math.random()
                * COURT_WIDTH - 40), FRUIT_VELOCITY, this));
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
                                "Welcome to Fruit Ninja. \nTo play, click and "
                                        + "drag across"
                                        + " a falling fruit to \"slice\" it"
                                        + " apart. The game ends"
                                        + "if you cut open a bomb!"
                                        + "\n Features include:"
                                        + "\n- Ball physics! Gravity is "
                                        + "implemented for accurate parabolic "
                                        + "motion. Gravity makes the game harder"
                                        + "so you get more points on this mode!"
                                        + " One fruit = 10 points * "
                                        + "your current level"
                                        + "\n- Zero gravity mode! This mode is"
                                        + " easier so you get fewer points. One"
                                        + " fruit = 1 * your current level"
                                        + " \n- Multiple levels of difficulty. "
                                        + "Higher levels have"
                                        + "faster moving fruits, and "
                                        + "more of them");
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

            while (fruitList.size() < (4 + level)) {
                if (timePassed % genBomb == 0) {
                    fruitList.add(new Bomb(COURT_WIDTH, COURT_HEIGHT,
                            (int) (Math.random() * COURT_WIDTH - 40),
                            FRUIT_VELOCITY, this));
                }
                fruitList.add(new Fruit(COURT_WIDTH, COURT_HEIGHT, (int) (Math
                        .random() * COURT_WIDTH - 40), FRUIT_VELOCITY, this));

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

    // level controls
    public void level_up() {
        if (level <= 11 && playing) {
            level += 1;
            FRUIT_VELOCITY -= .01;
            level_label.setText("Level: " + level);
        }
    }

    public void level_down() {
        if (level > 1 && playing) {
            level -= 1;
            FRUIT_VELOCITY += .01;
            level_label.setText("Level: " + level);
        }
    }

    // toggle gravity
    public void zeroGravity() {
        this.zeroGravityMode = !this.zeroGravityMode;
        if (zeroGravityMode) {
            gravity_label.setText("Gravity is off");
        } else {
            gravity_label.setText("Gravity is on");
        }
    }

}
