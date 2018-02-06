import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Tile extends Rectangle{
	
	private final static int tile_width = 32;
	private final static int tile_height = 32;
	
	//constructor for the Tile
	public Tile(int x, int y)
	{
		setBounds(x, y, tile_width, tile_height);
	}
	
	/*
	 * render a individual Tile 
	 * @param g the graphic context where the Tile will be rendered
	 */
	public void render(Graphics g)
	{
		g.setColor(new Color(33,  0,  127));
		g.fillRect(x, y, width, height);
	}
}
