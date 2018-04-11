package gamefiles;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.util.Arrays;
import java.util.HashSet;

public class Game implements Runnable 
{
	public static final int FPS = 60;
	public static final long maxLoopTime = 1000 / FPS;
	public static final int SCREEN_WIDTH = 640;
	public static final int SCREEN_HEIGHT = 640;
	
	public Screen screen;
	private Camera gameCamera;
	Player player;
	AnimEntity fire;
	Level level;
	KeyManager keyManager;
	
	BufferStrategy bs;
	Graphics g;
	
	public static void main(String args[])
	{
		Game game = new Game();
		new Thread(game).start();
	}
	
	public boolean running = true;
	
	public Camera getGameCamera()
	{
		return gameCamera;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void run() 
	{
		long timestamp;
		long oldTimestamp;
		
		screen = new Screen("Game", SCREEN_WIDTH, SCREEN_HEIGHT);
		keyManager = new KeyManager();
		screen.getFrame().addKeyListener(keyManager);
		
		TileSet[] tileSet = new TileSet[3];
		HashSet hs = new HashSet(Arrays.asList(0, 1, 2, 12, 14, 24, 25, 26));
		tileSet[0] = new TileSet("/tiles/rpg.png", 12 , 12, 3, hs);
		hs = new HashSet(Arrays.asList(160, 161));
		tileSet[1] = new TileSet("/tiles/tileb.png", 16, 16, 0, hs);
		tileSet[2] = new TileSet("/tiles/tileb.png", 16, 16, 0, hs);
		
		String[] paths = new String[3];
		paths[0] = "/level/Level1.txt";
		paths[1] = "/level/Level1a.txt";
		paths[2] = "/level/Level1b.txt";
		level = new Level(this, paths, tileSet);
		
		SpriteSheet playerSprite = new SpriteSheet("/sprites/player.png", 3, 4, 64, 64);
		player = new Player(this, level, 320, 320, playerSprite);
		
		SpriteSheet fireSprite = new SpriteSheet("/sprites/fire_big.png", 3, 1, 64, 128);
		fire = new AnimEntity(this, "Fire", fireSprite, 280, 280, 64, 128);
		
		gameCamera = new Camera(level.getSizeX(), level.getSizeY());
		
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
	
	void update()
	{
		keyManager.update();
		player.setMove(getInput());
		player.update();
		fire.update();
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
		level.render(g);
		player.render(g);
		fire.render(g);
		level.renderZ(g);
		bs.show();
		g.dispose();
	}
	
	private Point getInput()
	{
		int xMove = 0;
		int yMove = 0;
		if (keyManager.up) yMove = -1;
		if (keyManager.down) yMove = 1;
		if (keyManager.left) xMove = -1;
		if (keyManager.right) xMove = 1;
		return new Point(xMove, yMove);
	}
}
