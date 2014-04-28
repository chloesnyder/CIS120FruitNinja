import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bomb extends GameObj {

    public static final int SIZE = 60;
    public static final int INIT_Y = 300;
    public static final int INIT_VEL_X = (int) (Math.random() * 3) + 1;
    public BufferedImage bomb;

    public Bomb(int courtWidth, int courtHeight, int x, double init_vel_y, 
            GameCourt court) {
        super(INIT_VEL_X, init_vel_y, x, INIT_Y, SIZE, SIZE, courtWidth,
                courtHeight, court);

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
