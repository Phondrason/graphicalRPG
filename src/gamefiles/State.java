package gamefiles;

import java.awt.Graphics;

public abstract class State 
{
	private static State currentState = null;
	protected Game game;
	
	public static void setState(State state)
	{
		currentState = state;
	}
	
	public static State getState()
	{
		return currentState;
	}
	
	public State(Game game)
	{
		this.game = game;
	}
	
	public abstract void update();
	public abstract void render(Graphics g);
}
