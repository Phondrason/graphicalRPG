package gamefiles;

import java.awt.image.BufferedImage;

public class Player extends Creature 
{
	public static final int DEFAULT_HEALTH = 100;
	public static final int DEFAULT_SPEED = 1;
	
	public Player(int x, int y, BufferedImage image)
	{
		super("Player", image, x, y, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, Player.DEFAULT_HEALTH, Player.DEFAULT_SPEED);
	}
	
	@Override
	protected void update() 
	{
		move();
	}

}
