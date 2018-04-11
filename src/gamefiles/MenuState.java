package gamefiles;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class MenuState extends State 
{
	public int menuItem = 1;
	private int counter = 8;
	TileSet tileSet;
	Font font;
	BufferedImage menuItemFrame;
	
	public MenuState(Game game){
		super(game);
		this.game = game;
		@SuppressWarnings({ "rawtypes", "unchecked" })
		HashSet hs = new HashSet(Arrays.asList(0));
		tileSet = new TileSet("/tiles/menu.png", 6, 3, 0, hs);
		try 
		{
			menuItemFrame = ImageIO.read(TileSet.class.getResource("/tiles/menuitemframe.png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		font = new Font("Matura MT Script Capitals", Font.BOLD, 40);
		}
	
	@Override
	public void update() 
	{
		Point p = game.keyManager.getInput();
		if (counter > 0) counter--;
		if(p.y == KeyEvent.VK_DOWN && counter <= 0)
		{
			counter = 8;
			if(menuItem < 2) menuItem++;
		} 
		else if(p.y == KeyEvent.VK_UP && counter <= 0)
		{
			counter = 8;
			if(menuItem > 0) menuItem--;
		}
		else if(p.y == KeyEvent.VK_ENTER && counter <= 0)
		{
			counter = 8;
			if (menuItem == 0) 
			{
				game.gameState = new GameState(game);
				State.setState(game.gameState);
			} 
			else if (menuItem == 1) 
			{
				State.setState(game.gameState);
			}
			else if (menuItem == 2)
			{
				game.Stop();
			}
		}
	}

	@Override
	public void render(Graphics g) 
	{
		Canvas canvas = game.screen.getCanvas();
		Color color = new Color(0x900000);
		canvas.setBackground(color);
		/*
		* First line of main menu screen
		*/
		renderMenu(g, 0 /* tilenum */, 0 /* x */, 0 /* y */);
		for(int x = 1; x <= Game.SCREEN_WIDTH / TileSet.TILEWIDTH - 2; x++) 
		{
			renderMenu(g, 2, x, 0);
		}
		renderMenu(g, 1, Game.SCREEN_WIDTH / TileSet.TILEWIDTH - 1, 0);
		/*
		* Line 2 to sizeY of main menu screen
		*/
		int y;
		for(y = 1; y <= Game.SCREEN_HEIGHT / TileSet.TILEHEIGHT - 2; y++) 
		{
			renderMenu(g, 8, 0, y);
			int i;
			for(i = 1; i <= Game.SCREEN_WIDTH / TileSet.TILEWIDTH - 2; i++) 
			{
				renderMenu(g, 12, i, y);
			}
			renderMenu(g, 14, i, y);
		}
		/*
		* Last line of main menu screen
		*/
		renderMenu(g, 6 /* tilenum */, 0 /* x */, y /* y */);
		int x;
		for(x = 1; x <= Game.SCREEN_WIDTH / TileSet.TILEWIDTH - 2; x++)
		{
			renderMenu(g, 13, x, y);
		}
		renderMenu(g, 7, x, y);
		g.setFont(font);
		color = new Color(0xEEEEEE);
		g.setColor(color);
		g.drawString("New Game", Game.SCREEN_WIDTH/2-120, 200);
		g.drawString("Resume", Game.SCREEN_WIDTH/2-120, 280);
		g.drawString("Quit", Game.SCREEN_WIDTH/2-120, 360);
		/*if(!playerSerFile.exists()) 
		{
			color = new Color(0x600000);
			g.setColor(color);
			g.drawString("Resume", Game.SCREEN_WIDTH/2-120, 280);
		}*/
		g.drawImage(menuItemFrame, Game.SCREEN_WIDTH/2-190, 140 + menuItem * 80, 384, 96, null);
	}
	
	private void renderMenu(Graphics g, int tile, int x, int y) 
	{
		tileSet.renderTile(g, tile, x * TileSet.TILEWIDTH, y * TileSet.TILEHEIGHT);
	}

}
