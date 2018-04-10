package gamefiles;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game implements Runnable 
{
	public static final int FPS = 60;
	public static final long maxLoopTime = 1000 / FPS;
	public static final int SCREEN_WIDTH = 640;
	public static final int SCREEN_HEIGHT = 640;
	
	public Screen screen;
	
	public static void main(String args[])
	{
		Game game = new Game();
		new Thread(game).start();
	}
	
	Level level;
	
	@Override
	public void run() 
	{
		long timestamp;
		long oldTimestamp;
		BufferedImage playerImages;
		screen = new Screen("Game", SCREEN_WIDTH, SCREEN_HEIGHT);
		
		TileSet tileSet = new TileSet("/tiles/rpg.png", 12, 12);
		level = new Level("/level/Level1.txt", tileSet);
		
		while (true)
		{
			oldTimestamp = System.currentTimeMillis();
			update();
			timestamp = System.currentTimeMillis();
			if (timestamp-oldTimestamp > maxLoopTime)
			{
				System.out.println("Wir sind zu spät!");
				continue;
			}
			render();
			timestamp = System.currentTimeMillis();
			System.out.println(maxLoopTime + " : " + (timestamp-oldTimestamp));
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
	
	void update()
	{
		try
		{
			Thread.sleep(15);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	BufferStrategy bs;
	Graphics g;
	
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
		level.renderMap(g);
		bs.show();
		g.dispose();
	}
}
