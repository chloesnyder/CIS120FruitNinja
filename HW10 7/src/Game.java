/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes inmutability
        // even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Fruit Ninja");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // set cursor to knife
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("Kitchen-Knife-2-icon.png");
        Point p = new Point(0, 0);
        Cursor c = toolkit.createCustomCursor(image, p, "img");
        frame.setCursor(c);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        JLabel status = new JLabel("Running...");
        status_panel.add(status);

        // level/score panel
        final JPanel level_score_panel = new JPanel();
        final JLabel level_label = new JLabel("Level: 1");
        final JLabel score_label = new JLabel("Score: 0");
        level_score_panel.add(level_label);
        level_score_panel.add(score_label);
        level_score_panel.setLayout(new GridLayout(3, 3));
        frame.add(level_score_panel, BorderLayout.LINE_START);

        // status info, main playing area
        final GameCourt court = new GameCourt(status, level_label, score_label);
        frame.add(court, BorderLayout.CENTER);

        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset
        // button, we define it as an anonymous inner class that is
        // an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed,
        // actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        control_panel.add(reset);

        final JButton pause = new JButton("Pause/Unpause + Instructions");
        pause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.pause();
            }
        });
        control_panel.add(pause);

        // up/down arrows
        ImageIcon up = new ImageIcon("arrow_up.png");
        ImageIcon down = new ImageIcon("arrow_down.png");

        // level up and down buttons
        final JButton level_up = new JButton(up);
        level_up.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.level_up();
            }
        });
        level_score_panel.add(level_up);

        final JButton level_down = new JButton(down);
        level_down.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.level_down();
            }
        });
        level_score_panel.add(level_down);

        // Put the frame on the screen
        frame.repaint();
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }

    /*
     * Main method run to start and run the game Initializes the GUI elements
     * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
     * this in the final submission of your game.
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Game());
    }
}
