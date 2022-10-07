import java.awt.Graphics; 
import java.awt.image.BufferedImage;
class Fireball extends Sprite
{
	int x;
    int y;
    boolean right = true;
    int vert_vel;
    int scrollPos;
    final static int FireballWidth = 47;
    String type = "Fireball";
    final static int FireballHeight = 47;
    static BufferedImage ball_image;
    final int ground = 596 - FireballHeight;
	Fireball()
	{
        alive = true;
        vert_vel = 0;
        this.x = 0;
        this.y = 0;
        if(ball_image == null)
        ball_image = loadImage("fireball.png");
    }
    Fireball(int x, int y, boolean right)
	{
        this.right = right;
        alive = true;
        vert_vel = 0;
        this.x = x;
        this.y = y;
        if(ball_image == null)
        ball_image = loadImage("fireball.png");
    }
    int getX()
    {
        return x;
    }
    Json marshal()
    {
        return null;
    }
    int getY()
    {
        return y;
    }
    void setX(int x)
    {
        this.x = x;
        if(x - scrollPos > 1450){
            alive = false;}
        
    }
    void draw(Graphics g, int marioPos)
    {
        scrollPos = marioPos;
        g.drawImage(ball_image, x - marioPos, y, null);
    }
    public void update()
    {
        if(right)
            setX(x + 6);
        else
            setX(x-6);
        if(y < ground+Mario.MarioHeight)
        {
            vert_vel += 10;
        } else {
            vert_vel -= 10;
        }
        y += vert_vel;
        if(y >= ground)
        {
            vert_vel= -50;
            y = ground;
        }
    }
    public void update(Sprite collidedSprite)
    {
        //They die on touch
        if(collidedSprite.getType() != "Mario" && collidedSprite.getType() != this.getType())
        {
            alive = false;
        }
        
        update();
      
    }
    int getWidth() {
        return FireballWidth;
    }
    int getHeight() {
        return FireballHeight;
    }
    String getType()
    {
        return type;
    }
}
