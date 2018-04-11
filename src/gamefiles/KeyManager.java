package gamefiles;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

	private boolean[] keys;
	
	public KeyManager()
	{
		keys = new boolean[256];
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{

	}
	
	public Point getInput()
	{
		int xMove = 0;
		int yMove = 0;
		if (keys[KeyEvent.VK_W]) yMove = -1;
		if (keys[KeyEvent.VK_S]) yMove = 1;
		if (keys[KeyEvent.VK_A]) xMove = -1;
		if (keys[KeyEvent.VK_D]) xMove = 1;
		if (keys[KeyEvent.VK_UP])
		{
			yMove = KeyEvent.VK_UP;
			xMove = -99;
		}
		if (keys[KeyEvent.VK_DOWN])
		{
			yMove = KeyEvent.VK_DOWN;
			xMove = -99;
		}
		if (keys[KeyEvent.VK_ENTER])
		{
			yMove = KeyEvent.VK_ENTER;
			xMove = -99;
		}
		if (keys[KeyEvent.VK_ESCAPE])
		{
			yMove = KeyEvent.VK_ESCAPE;
			xMove = -99;
		}
		return new Point(xMove, yMove);
	}

}
