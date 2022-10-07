import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ListIterator;
import java.awt.Color;

class View extends JPanel {
	Model model;
	View(final Controller c, final Model m) {
		model = m;
		c.setView(this);
	}

	public BufferedImage loadImage(final String filename)
	{
		try{
		return ImageIO.read(new File(filename));
		}
		catch(IOException e)
		{
			System.out.println("It did not like " + filename);
		}
		return null;
	}
	public void paintComponent(final Graphics g) {
		g.setColor(new Color(107, 178, 238));//official mario sky color because the cyan was hurting me
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(new Color(255,204,153));
		g.fillRect(0, 596, 2000, 596);
		//draw tubes
		for(Sprite s: model.sprites){
			s.draw(g, model.mario.getX());
		}
		
	}
	void setModel(final Model m)
	{
		this.model = m;
	}
	public void loadSave() {
		Json ob = Json.newObject();
		Model newModel;
		ob = Json.load("map.json");
		newModel = new Model(ob.get("Model"));
		setModel(newModel);
	}
	public void save() {
		final Json ob = Json.newObject();
		//Data heirarchy: 
		/*Ob: {
			
			model:
			{
				{tube},{tube},{tube}
			}
			Mario: {x,y}
		}
		*/
		ob.add("Model", model.marshal());
		ob.save("map.json");
	}

}
