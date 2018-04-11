package gamefiles;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashSet;

public class GameState extends State
{
	Screen screen;
	Camera gameCamera;
	Player player;
	//AnimEntity fire;
	Level level;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GameState(Game game)
	{
		super(game);
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
		
		//SpriteSheet fireSprite = new SpriteSheet("/sprites/fire_big.png", 3, 1, 64, 128);
		//fire = new AnimEntity(this, "Fire", fireSprite, 280, 280, 64, 128);
		
		gameCamera = new Camera(level.getSizeX(), level.getSizeY());
	}

	public Camera getGameCamera()
	{
		return gameCamera;
	}
	
	public void saveGame()
	{
		try
		{
			FileOutputStream fos = new FileOutputStream("player.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(player);
			oos.close();
			fos = new FileOutputStream("level.ser");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(level);
			oos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadGame()
	{
		try
		{
			FileInputStream fis = new FileInputStream("player.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			player = (Player) ois.readObject();
			ois.close();
			fis = new FileInputStream("level.ser");
			ois = new ObjectInputStream(fis);
			level = (Level) ois.readObject();
			ois.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void update() 
	{
		Point p = game.keyManager.getInput();
		if (p.x == -99)
		{
			if (p.y == KeyEvent.VK_ESCAPE)
			{
				State.setState(game.menuState);
			}
			return;
		}
		player.setMove(p);
		player.update();
		//fire.update();
	}

	@Override
	public void render(Graphics g) 
	{
		level.render(g);
		player.render(g);
		//fire.render(g);
		level.renderZ(g);
	}

}
