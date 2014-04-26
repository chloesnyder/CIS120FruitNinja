/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

/**
 * A basic game object displayed as a black square, starting in the upper left
 * corner of the game court.
 * 
 */


public class Fruit extends GameObj {
	public static final int SIZE = 30;
	public static final int INIT_Y = 300;
	public static final int INIT_VEL_X = (int) (Math.random() * 3) + 1;
	public static final int INIT_VEL_Y = -(int) (Math.random() * 3) - 1;
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
	public static BufferedImage[] fruits = {watermelon, bannana, strawberry, 
		apple, peach, pineapple, grapes, pear, orange, cherry};
	public int i = ((int) (Math.random() * fruits.length));
	public BufferedImage fruit;
	
	

	/**
	 * Note that, because we don't need to do anything special when constructing
	 * a Square, we simply use the superclass constructor called with the
	 * correct parameters
	 */
	public Fruit(int courtWidth, int courtHeight, int x) {
		super(INIT_VEL_X, INIT_VEL_Y, x, INIT_Y, SIZE, SIZE, courtWidth,
				courtHeight);
		//reads in fruit image
		try {
			File w = new File("watermelon.png");
			watermelon = ImageIO.read(w);
			File s = new File("strawberry.png");
			strawberry = ImageIO.read(s);
			File b = new File("bannana.png");
			bannana = ImageIO.read(b);
			File a = new File("apple.png");
			apple = ImageIO.read(a);
			File p = new File("peach.png");
			peach = ImageIO.read(p);
			File pine = new File("pineapple.png");
			pineapple = ImageIO.read(pine);
			File g = new File("grapes.png");
			grapes = ImageIO.read(g);
			File o = new File("orange.png");
			orange = ImageIO.read(o);
			File c = new File("cherry.png");
			cherry = ImageIO.read(c);
		} catch (IOException e) {
			System.out.println("Something went wrong");
		}
		
		//selects random fruit
		BufferedImage[] fruits = {watermelon, bannana, strawberry, apple,
				peach, pineapple, grapes, orange, cherry};
		int i = ((int) (Math.random() * fruits.length));
		fruit = fruits[i];
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(fruit, pos_x, pos_y, width, height, null);
	}

}
