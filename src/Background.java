import java.awt.Graphics;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;

public class Background {
	public int width;
	public int height;
	
	public Tile[][] tiles;
	public List<Coins> coins;
	public List<Enemy> enemies;
	
	/*
	 * Constructor for the Background, which consists of empty and nonempty tiles 
	 * and nonempty tiles represent walls that both enemies and player cannot walk through
	 * @param g the graphic context where the Tile will be rendered
	 */
	public Background(String path, MainGame currentGame)
	{
		coins = new ArrayList<>();
		enemies = new ArrayList<>();
		
		BufferedImage background;
		try {
			background = ImageIO.read(getClass().getResource(path));
			
			this.width = background.getWidth();
			this.height = background.getHeight();

			tiles = new Tile[width][height];
			
			//compress the RGB value of each pixel in the background image into a 1-D array
			int[] pixels = new int[width*height];
			background.getRGB(0, 0, width, height, pixels, 0, width);
			int enemyIndex = 0;
			
			for(int i = 0; i < width; i++)
			{
				for(int j = 0; j < height; j++)
				{
					int color = pixels[i+j*width];
					
					if(color == 0xFF000000)
					{
						//tile for wall
						tiles[i][j] = new Tile(i*32, j*32);
					}
					else if(color == 0xFF2196F3)
					{
						//tile for player, set up player's initial position
						MainGame.gamePlayer.x = i*32;
						MainGame.gamePlayer.y = j*32;
					}
					else if(color == 0xFFf44336)
					{
						//tile for enemy and coin
						enemies.add(new Enemy(i*32,  j*32, enemyIndex, currentGame.getThread()));
						enemyIndex++;
						
						coins.add(new Coins(i*32, j*32, 0));
					}
					//add coins of different categories 
					else if(color == 0xFFFFEB3B)
					{
						coins.add(new Coins(i*32, j*32, 0));
					}
					else if(color == 0xFFFF9800)
					{
						coins.add(new Coins(i*32, j*32, 1));
					}
					else if(color == 0xFF009688)
					{
						coins.add(new Coins(i*32, j*32, 2));
					}
					else if(color == 0xFF795548)
					{
						coins.add(new Coins(i*32, j*32, 3));
					}
					else if(color == 0xFF9C27B0)
					{
						coins.add(new Coins(i*32, j*32, 4));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	//move all enemies at random direction at the same time
	public void enemiesMove()
	{
		for(int i = 0; i < enemies.size(); i++)
		{
			enemies.get(i).enemyMove();
		}
	}
	
	
	
	/*
	 * render the walls, coins and enemies
	 * @param g the graphic context where the above components will be rendered
	 */
	public void render(Graphics g)
	{
		for(int pos_x = 0; pos_x < width; pos_x++)
		{
			for(int pos_y = 0; pos_y < height; pos_y++)
			{
				if(tiles[pos_x][pos_y] != null)
				{
					tiles[pos_x][pos_y].render(g);
				}
			}
		}
		
		for(int i = 0; i < coins.size(); i++)
		{
			coins.get(i).render(g);
		}
		
		for(int i = 0; i < enemies.size(); i++)
		{
			enemies.get(i).render(g);
		}
	}
}
