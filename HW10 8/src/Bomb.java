import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bomb extends GameObj {

    public static final int SIZE = 60;
    public static final int INIT_Y = 330;
    public static double INIT_VEL_X;
    public BufferedImage bomb;

    public Bomb(int courtWidth, int courtHeight, int x, double init_vel_y, 
            GameCourt court) {
        super(INIT_VEL_X, init_vel_y, x, INIT_Y, SIZE, SIZE, courtWidth,
                courtHeight, court);
        //determine direction
        if (x < 150) INIT_VEL_X = .4;
        else INIT_VEL_X = -.4;
        // read in bomb image
        try {
            File b = new File("bomby.png");
            bomb = ImageIO.read(b);
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(bomb, pos_x, pos_y, width, height, null);
    }

}
