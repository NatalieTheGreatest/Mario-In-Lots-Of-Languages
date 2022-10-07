import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener {
	View view;
	Model model;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean save;
	boolean fire;
	boolean fireCooldown;
	boolean load;
	boolean keyUpReleased;
	Controller(Model m)
	{
		model = m;
	}
	void setView(final View v)
	{
		view = v;
	}
	public void actionPerformed(final ActionEvent e)
	{
	}
	public void mousePressed(final MouseEvent e)
	{
		//model.addTube(e.getX() + view.model.sprites.get(0).getX(), e.getY());
	}

	public void mouseReleased(final MouseEvent e) {    }
	public void mouseEntered(final MouseEvent e) {    }
	public void mouseExited(final MouseEvent e) {    }
	public void mouseClicked(final MouseEvent e) {    }
	public void keyPressed(final KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_UP: keyUp = true; keyUpReleased = false; break;
			case KeyEvent.VK_SPACE: keyUp = true; keyUpReleased = false; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			case KeyEvent.VK_S: save = true; break;
			case KeyEvent.VK_L: load = true; break;
		}
	}

	public void keyReleased(final KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_UP: keyUp = false; keyUpReleased = true; break;
			case KeyEvent.VK_SPACE: keyUp = false; keyUpReleased = true; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			case KeyEvent.VK_S: save = false; break;
			case KeyEvent.VK_L: load = false; break;
			case KeyEvent.VK_CONTROL: fire = true;
		}
	}

	public void keyTyped(final KeyEvent e)
	{
	}

	void update()
	{
		//former turtle control
		if(keyRight) 
		{
			model.marioRight();
		}
		if(keyLeft){
			model.marioLeft();
		}
		if(keyUp) model.attemptJump();
		if (keyUpReleased) model.setJumpCount(5);
		if(load) 
		{
			view.loadSave();
			// I forgot to do this
			// so I couldn't add pipes after I loaded
			// Not fun
			model = view.model;
		}
		if(fire)
		{
			model.fire();
			fire = false;
	}
		if (save) view.save();
		// if(keyDown) model.dest_y++;
		// if(keyUp) model.dest_y--;
	}
}
