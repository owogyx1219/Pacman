import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class Coins extends Rectangle{
	private final static int coin_width = 8;
	private final static int coin_height = 8;
	private int colorIndex = 0;
	
	//constructor for the Coins
	public Coins(int x, int y, int designatedColorIndex)
	{
		colorIndex = designatedColorIndex;
		setBounds(x+12, y+12, coin_width, coin_height);
	}
	
	
	/*
	 * render a individual Coin 
	 * @param g the graphic context where the Tile will be rendered
	 */
	public void render(Graphics g)
	{
		if(colorIndex == 0)
		{
			g.setColor(Color.yellow);
		}
		else if(colorIndex == 1)
		{
			g.setColor(Color.GRAY);
		}
		else if(colorIndex == 2)
		{
			g.setColor(Color.cyan);
		}
		else if(colorIndex == 3)
		{
			g.setColor(Color.BLUE);
		}
		
		else if(colorIndex == 4)
		{
			g.setColor(Color.white);
		}
		
		g.fillRect(x, y, width, height);
	}
	
	
	
	//get the index that represent the corresponding color of a certain type of coin
	//white-0  gray-1  cyan-2  blue-3  white-4
	public int getColorIndex()
	{
		return this.colorIndex;
	}
}
