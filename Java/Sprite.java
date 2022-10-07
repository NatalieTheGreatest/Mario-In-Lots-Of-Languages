import java.awt.Graphics; 
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
public abstract class Sprite {
    int x, y;
    boolean alive;
    String type;
    abstract int getX();
    abstract int getY();
    abstract int getWidth();
    abstract int getHeight();
    abstract Json marshal();
    abstract void update(Sprite collidedSprite);
    abstract void update();
    abstract String getType();
    abstract void draw(Graphics g, int marioPos);
    BufferedImage loadImage(final String filename){
        try{
            return ImageIO.read(new File(filename));
            }
            catch(IOException e)
            {
                System.out.println("It did not like " + filename);
                e.printStackTrace(System.err);
			    System.exit(1);
            }
            return null;
        }
	public boolean intersect(int x2, int y2) {
		return false;
	}

} 