import java.awt.Graphics; 
import java.awt.image.BufferedImage;
import java.util.ListIterator;
import java.lang.Math; 
class Mario extends Sprite
{

    boolean right;
    int x = 0;
    int y;
    String type;
    final static int MarioHeight = 95;
    final static int MarioWidth = 60;
    //How far he is from start of screen
    final static int MarioDistance = 50;
    double vert_vel;
    BufferedImage[] mario_images;
    int imagePos;
    int jumpCount;
    boolean grounded;
    Mario()
    {
        alive = true;
        type = "Mario";
        right = true;
        x = 0;
        y = 500;
        grounded = true;
        jumpCount = 0;
        if(mario_images == null) {
        mario_images = new BufferedImage[5];
		mario_images[0] = loadImage("mario1.png");
		mario_images[1] = loadImage("mario2.png");
		mario_images[2] = loadImage("mario3.png");
		mario_images[3] = loadImage("mario4.png");
        mario_images[4] = loadImage("mario5.png");
    }
    }
    void attemptJump()
    {
        grounded = false;
        if(jumpCount < 5 && vert_vel > -19)
        {
            vert_vel -= 5;
            jumpCount++;
        }
        
    }
    public void update()
    {
        if(y >= 500 && vert_vel >= 0)
		{
			vert_vel = 0.0;
            y = 500; // snap back to the ground
            jumpCount = 0;
            grounded = true;
        }
        else
        {
            vert_vel += 1.2;
            y += vert_vel;
            grounded = false;
        }
    }
    public void update(Sprite collidedSprite)
    {
        if(y != 500)
        {
       if(collidedSprite.getType() == Tube.tubeType){
            //Head hitting doesn't seem to be a thing
        // if(y >= collidedTube.getY() && y < collidedTube.getY()+collidedTube.getHeight())
        // {
        //     // System.out.println(y + "He hit head I think" + (collidedTube.getY()+collidedTube.getHeight()));
        //     if(vert_vel < 0)
        //     vert_vel = 1.2;
        //     else
        //     vert_vel += 1.2;
        //     jumpCount = 5;
        // } else
         if(y < collidedSprite.getY())
        {
            if(vert_vel > 0)
                vert_vel = 0.0;
            y = collidedSprite.getY() - MarioHeight + 1;
            y+= vert_vel;
            jumpCount = 0;
            grounded = true;
         }
        }
        else {
            update();
        }
    }
    else {
        jumpCount = 0;
    }
    }
    int getX()
    {
        return x;
    }
    int getY()
    {
        return y;
    }
    void setX(int x)
    {
        if(this.x > x)
        {
            right = false;
            imagePos--;
        } 
        else if(this.x < x)
        {
            right = true;
            imagePos++;
        }
        this.x = x;
    }
    void draw(Graphics g)
    {
        int image = Math.abs(imagePos % 5);
        g.drawImage(mario_images[image], 50, y, null);
    }
    void draw(Graphics g, int marioPos)
    {
        int image = Math.abs(imagePos % 5);
        g.drawImage(mario_images[image], 50, y, null);
    }
    Json marshal()
    {
        return null;  
    }
    int getWidth() {
        return MarioWidth;
    }
    int getHeight() {
        return MarioHeight;
    }
    String getType()
    {
        return type;
    }
    boolean isRight()
    {
        return this.right;
    }
}
