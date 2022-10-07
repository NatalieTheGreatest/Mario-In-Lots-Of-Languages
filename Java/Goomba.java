import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Goomba extends Sprite {

    int x, y, width, height;
    boolean right;
    static String GoombaType="Goomba";
    final static int GoombaWidth = 99;
    final static int GoombaHeight = 118;
    //I was a tad confused about this, but I think I got it now!
    static BufferedImage goomba_image;
    static BufferedImage goomba_death_image;
    BufferedImage ownImage;
    //So you can watch him burn for a second
    int deathFrames = 5;
    Goomba()
	{
        right = true;
        alive = true;
        type = GoombaType;
        this.x = 0;
        this.y = 0;
        if(goomba_image == null)
        goomba_image = loadImage("goomba.png");
        if(goomba_death_image == null);
        goomba_death_image = loadImage("goomba_fire.png");
        ownImage = goomba_image;
    }
    Goomba(int x, int y)
	{
        right = true;
        alive = true;
        this.x = x;
        this.y = y;
        if(goomba_image == null)
        goomba_image = loadImage("goomba.png");
        if(goomba_death_image == null);
        goomba_death_image = loadImage("goomba_fire.png");
        ownImage = goomba_image;
    }
    Goomba(Json ob) {
        right = true;
        alive = true;
        this.x = (int) ob.getLong("x");
        this.y = (int) ob.getLong("y");
        if(goomba_image == null)
        goomba_image = loadImage("goomba.png");
        if(goomba_death_image == null);
        goomba_death_image = loadImage("goomba_fire.png");
        ownImage = goomba_image;
    }
    int getX() {
        return x;
    };

    int getY() {
        return y;
    };

    int getWidth() {
        return width;
    };

    int getHeight() {
        return height;
    };

    Json marshal() {
        Json ob = Json.newObject();
        ob.add("x", this.x);
        ob.add("y", this.y);
        return ob;
    };

    void update(Sprite collidedSprite) {
        if(ownImage == goomba_death_image)
        deathFrames--;
        if(deathFrames < 1)
        alive = false;
        //He switches direction. Since he's never on top of a tube or anything
        if(collidedSprite.getType() == Tube.tubeType)
            right = !right;
        else if(collidedSprite.getType() == "Fireball")
            ownImage = goomba_death_image;
        update();
    };

    void update() {
        if(ownImage == goomba_death_image)
        deathFrames--;
        if(deathFrames < 1)
        alive = false;
        if(right)
        {
           x+=5;
        }
        else {
            x-=5;
        }
    };

    String getType() {
        return GoombaType;
    };

    void draw(Graphics g, int marioPos) {
        g.drawImage(ownImage, x - marioPos, y, null);
    }

}