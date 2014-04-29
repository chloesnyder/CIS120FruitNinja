//* CIS 120 HW10
// * (c) University of Pennsylvania
// * @version 2.0, Mar 2013
// */

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A basic game object displayed as a black square, starting in the upper left
 * corner of the game court.
 * 
 */

public class CutFruit extends GameObj {
    public static final int SIZE = 30;
    public static BufferedImage watermelon;
    public static BufferedImage bannana;
    public static BufferedImage strawberry;
    public static BufferedImage apple;
    public static BufferedImage peach;
    public static BufferedImage pineapple;
    public static BufferedImage grapes;
    public static BufferedImage pear;
    public static BufferedImage orange;
    public static BufferedImage cherry;
    public static BufferedImage[] fruits = { watermelon, bannana, strawberry,
            apple, peach, pineapple, grapes, pear, orange, cherry };
    public int i = ((int) (Math.random() * fruits.length));
    public BufferedImage fruit;

    /**
     * Note that, because we don't need to do anything special when constructing
     * a Square, we simply use the superclass constructor called with the
     * correct parameters
     */
    // public CutFruit(int courtWidth, int courtHeight, int x, int init_vel_y) {
    // super(INIT_VEL_X, init_vel_y, x, INIT_Y, SIZE, SIZE, courtWidth,
    // courtHeight);
    public CutFruit(int courtWidth, int courtHeight, int x, int y,
            int init_vel_x, int init_vel_y, String fruit, GameCourt court) {
        super(init_vel_x, init_vel_y, x, y, SIZE, SIZE, courtWidth,
                courtHeight, court);
        // reads in fruit image
        try {
            File w = new File("cut_watermelon.png");
            watermelon = ImageIO.read(w);
            File s = new File("cut_strawberry.png");
            strawberry = ImageIO.read(s);
            File b = new File("cut_bannana.png");
            bannana = ImageIO.read(b);
            File a = new File("cut_apple.png");
            apple = ImageIO.read(a);
            File p = new File("cut_peach.png");
            peach = ImageIO.read(p);
            File pine = new File("cut_pineapple.png");
            pineapple = ImageIO.read(pine);
            File g = new File("cut_grapes.png");
            grapes = ImageIO.read(g);
            File o = new File("cut_orange.png");
            orange = ImageIO.read(o);
            File c = new File("cut_cherry.png");
            cherry = ImageIO.read(c);
            File pe = new File("cut_pear.png");
            pear = ImageIO.read(pe);
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }

        // selects random fruit
        BufferedImage[] fruits = { watermelon, bannana, strawberry, apple,
                peach, pear, pineapple, grapes, orange, cherry };
        // fruit = fruits[i];
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(fruit, pos_x, pos_y, width, height, null);
    }

}
