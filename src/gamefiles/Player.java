package gamefiles;

import java.awt.Graphics;

public class Player extends Creature 
{
	public static final int DEFAULT_HEALTH = 100;
	public static final int DEFAULT_SPEED = 1;
	private Game game;
	
	public Player(Game game, int x, int y, SpriteSheet playerSprite)
	{
		super("Player", playerSprite, x, y, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, Player.DEFAULT_HEALTH, Player.DEFAULT_SPEED);
		this.game = game;
	}
	
	@Override
	protected void update() 
	{
		move();
		game.getGameCamera().centerOnEntity(this);
	}
	
	@Override
	protected void render(Graphics g)
	{
	    g.drawImage(image, entityX - game.getGameCamera().getxOffset(), entityY - game.getGameCamera().getyOffset(), width, height, null);
	}

}
