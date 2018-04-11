package gamefiles;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class Game implements Runnable
{
	public static final int FPS = 60;
	public static final long maxLoopTime = 1000 / FPS;
	public static final int SCREEN_WIDTH = 640;
	public static final int SCREEN_HEIGHT = 640;
	
	KeyManager keyManager;
	Screen screen;
	BufferStrategy bs;
	Graphics g;
	State gameState, menuState;
	
	public static void main(String args[])
	{
		Game game = new Game();
		new Thread(game).start();
	}
	
	public boolean running = true;
	
	@Override
	public void run() 
	{
		long timestamp;
		long oldTimestamp;
		
		screen = new Screen("Game", SCREEN_WIDTH, SCREEN_HEIGHT);
		keyManager = new KeyManager();
		screen.getFrame().addKeyListener(keyManager);
		
		gameState = new GameState(this);
		menuState = new MenuState(this);
		State.setState(menuState);
		
		while (running)
		{
			oldTimestamp = System.currentTimeMillis();
			update();
			timestamp = System.currentTimeMillis();
			if (timestamp-oldTimestamp > maxLoopTime)
			{
				continue;
			}
			render();
			timestamp = System.currentTimeMillis();
			if (timestamp-oldTimestamp <= maxLoopTime)
			{
				try
				{
					Thread.sleep(maxLoopTime - (timestamp-oldTimestamp));
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void Stop()
	{
		//save
		running = false;
		screen.getFrame().dispatchEvent(new WindowEvent(screen.getFrame(), WindowEvent.WINDOW_CLOSING));
	}
	
	void update()
	{
		if (State.getState() != null)
		{
			State.getState().update();
		}
	}
	
	void render()
	{
		Canvas c = screen.getCanvas();
		bs = c.getBufferStrategy();
		if (bs == null)
		{
			screen.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		State.getState().render(g);
		bs.show();
		g.dispose();
	}
}
