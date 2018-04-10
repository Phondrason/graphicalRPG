package gamefiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Entity 
{
	public static final int DEFAULT_WIDTH = 64;
	public static final int DEFAULT_HEIGHT= 64;
	
	protected String name;
	protected int entityX, entityY;
	protected int width, height;
	protected BufferedImage image;
	
	public Entity(String name, BufferedImage image, int x, int y, int width, int height)
	{
		this.name = name;
		this.image = image;
		this.entityX = x;
		this.entityY = y;
		this.width = width;
		this.height = height;
	}
	
	protected abstract void update();
	protected void render(Graphics g)
	{
		g.drawImage(image, entityX, entityY, null);
	}
}
