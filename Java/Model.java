import java.util.ArrayList;
import java.util.ListIterator;

class Model
{
	ArrayList<Sprite> sprites;
	Sprite collidedSprite;
	final int marioSpeed = 5;
	Mario mario;
	Model()
	{
		mario = new Mario();
		sprites = new ArrayList<Sprite>();
		sprites.add(mario);
	}

public void update()
	{
		ArrayList<Sprite> deadbois = new ArrayList<Sprite>();
		for(Sprite s: sprites){
		if(colliding(s))
		{
			s.update(collidedSprite);
		} else
			s.update();
		if(!s.alive){
			deadbois.add(s);
		}
		}
		for(Sprite s: deadbois){
			sprites.remove(s);
		}
}
	public void fire()
	{
		int firex = mario.getX() + 25;
		if(mario.isRight())
		firex+=(mario.getWidth() + 10);
		sprites.add(new Fireball(firex, mario.getY(), mario.isRight()));
	}
	public void addTube(final int x, final int y)
	{
		boolean found = false;
		Tube tube = new Tube();
		for(final Sprite t: sprites)
		{
			if(t.type == "Tube") {
			if(t.intersect(x, y)){
				tube = (Tube) t;
				found = true;
				break;
			}
		}
		}
		if(found){
			this.sprites.remove(tube);
		}
		else {
			tube = new Tube(x, y);
			this.sprites.add(tube);
		}

	}
	public void attemptJump()
	{
		mario.attemptJump();
	}
	public void setJumpCount(int i)
	{
		mario.jumpCount = i;
	}
	Model (final Json ob) {
		this.sprites = new ArrayList<Sprite>();
		mario = new Mario();
		this.sprites.add(mario);
		Json tmpList = ob.get("tubes");
		for(int i = 0; i < tmpList.size(); i++)
		{
			final Tube t = new Tube(tmpList.get(i));
			this.sprites.add(t);
		}
		tmpList = ob.get("goombas");
		for(int i = 0; i < tmpList.size(); i++)
		{
			final Goomba g = new Goomba(tmpList.get(i));
			this.sprites.add(g);
		}
	}
	Json marshal()
    {
        final Json ob = Json.newObject();
		final Json tmpList = Json.newList();
        ob.add("tubes", tmpList);
        for(int i = 0; i < sprites.size(); i++){
			String spriteType = sprites.get(i).getType();
			if(spriteType == Tube.tubeType || spriteType == "Goomba")
			tmpList.add(this.sprites.get(i).marshal());
		}
			
		//ob.add("Mario", mario.marshal());
        return ob;
	}
	public void marioRight(){
		mario.setX(mario.getX() + marioSpeed);
		
		if(colliding(mario))
		{
		if(collidedSprite.getType() == Tube.tubeType){
			if(mario.getY() != (collidedSprite.getY() - Mario.MarioHeight + 1))
			{
			mario.setX(collidedSprite.getX() - Mario.MarioDistance - collidedSprite.getWidth());
		}
			// System.out.println(collidedSprite.getX() - collidedSprite.getWidth() - Mario.MarioDistance);
			// mario.setX(collidedSprite.getX() - collidedSprite.getWidth() - Mario.MarioDistance);
		}
		}
	}
	public void marioLeft()
	{
		mario.setX(mario.getX() - marioSpeed);
		
		if(colliding(mario))
		{
			if(collidedSprite.getType() == Tube.tubeType){
			if(mario.getY() != (collidedSprite.getY() - Mario.MarioHeight + 1))
			{
			mario.setX(collidedSprite.getX());
			// System.out.println(collidedSprite.getX());
			// mario.setX(collidedSprite.getX());
			}
		}
		}
	}
	boolean colliding(final Sprite s)
    {
       final ListIterator<Sprite> spriteList = this.sprites.listIterator();
       while(spriteList.hasNext())
       {
        final Sprite sprite = spriteList.next();
        final int spriteX = sprite.getX();
		final int spriteY = sprite.getY();
		final int spriteCheck = s.getX();
		final int spriteCheckY = s.getY();
		//It doesn't collide with itself
		if(sprite != s) {
        if((spriteX < (spriteCheck + s.getWidth() + Mario.MarioDistance - 1)) && (spriteX + sprite.getWidth() > (spriteCheck + Mario.MarioDistance + 1)) && spriteY < spriteCheckY + s.getHeight() && (spriteY + sprite.getHeight() > spriteCheckY))
        {
			collidedSprite = sprite;
            return true;
		}
						}
       }
       return false; 
	}
}