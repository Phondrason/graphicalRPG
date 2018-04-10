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
	Player player;
	Level level;
	
	BufferStrategy bs;
	Graphics g;
	
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
		
		TileSet tileSet = new TileSet("/tiles/rpg.png", 12, 12);
		level = new Level("/level/Level1.txt", tileSet);
		SpriteSheet playerSprite = new SpriteSheet("/sprites/player.png", 3, 4, 64, 64);
		player = new Player(320, 320, playerSprite.getSpriteElement(1, 0));
		
		while (running)
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
		level.renderMap(g);
		player.render(g);
		bs.show();
		g.dispose();
	}
}
