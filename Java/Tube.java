import java.awt.Graphics; 
import java.awt.image.BufferedImage;
class Tube extends Sprite
{
	int x;
    int y;
    //Perhaps redundant, but it's not hurting anyone
    String type;
    final static String tubeType = "Tube";
    final static int TubeWidth = 55;
    final static int TubeHeight = 400;
    //I was a tad confused about this, but I think I got it now!
	static BufferedImage tube_image;
	Tube()
	{
        alive = true;
        type = tubeType;
        this.x = 0;
        this.y = 0;
        if(tube_image == null)
        tube_image = loadImage("tube.png");
    }
    Tube(int x, int y)
	{
        alive = true;
        this.x = x;
        this.y = y;
        if(tube_image == null)
        tube_image = loadImage("tube.png");
    }
    Tube(Json ob) {
        alive = true;
        this.x = (int) ob.getLong("x");
        this.y = (int) ob.getLong("y");
        if(tube_image == null)
        tube_image = loadImage("tube.png");
    }
    public boolean intersect(int x, int y)
    {
        if((x>=this.x && x<=(this.x+TubeWidth)) && (y>=this.y && y<=(this.y+TubeHeight)))
        {
            return true;
        }
        return false;
    }
    Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", this.x);
        ob.add("y", this.y);
        return ob;  
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
        this.x = x;
    }
    void draw(Graphics g, int marioPos)
    {
        g.drawImage(tube_image, x - marioPos, y, null);
    }
    public void update()
    {

    }
    public void update(Sprite collidedSprite)
    {

    }
    int getWidth() {
        return TubeWidth;
    }
    int getHeight() {
        return TubeHeight;
    }
    String getType()
    {
        return tubeType;
    }
}