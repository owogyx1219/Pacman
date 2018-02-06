import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Enemy extends Rectangle{
	
	private final static int enemy_width = 16;
	private final static int enemy_height = 16;
	private final static int enemy_offset_x = 10;
	private final static int enemy_offset_y = 10;
	
	private double prevTime;
	private int speed = 32;
	private int renderColor;
	private Thread thread;
	
	//constructor for the Enemy
	public Enemy(int x, int y, int color, Thread currThread)
	{
		//specify the position and size of the Enemy
		setBounds(x, y, 32, 32);
		renderColor = color;
		
		thread = currThread;
		prevTime = System.currentTimeMillis();
	}
	
	//move the enemy in random direction at speed=2 after each 0.2 seconds passed
	public void enemyMove()
	{	
		double currTime = System.currentTimeMillis();

		//make the enemy chase the player at each move
		if(currTime - prevTime >= 600)
		{
			
			if(x < MainGame.gamePlayer.x && enemyValidMove(x+speed, y))
			{
				moveRight();
			}
			if(x > MainGame.gamePlayer.x && enemyValidMove(x-speed, y))
			{
				moveLeft();
			}
			if(y < MainGame.gamePlayer.y && enemyValidMove(x, y+speed))
			{
				moveDown();
			}
			if(y > MainGame.gamePlayer.y && enemyValidMove(x, y-speed))
			{
				moveUp();
			}
			
			prevTime = currTime;
		}
		
		//if the enemy eat the player, then restart the game
		if(this.intersects(MainGame.gamePlayer))
		{
			System.out.println(MainGame.currGame.game.gamePlayer.x);
			MainGame.currGame.game.gamePlayer.x += 1000;
			//ask user if they want to restart the game
		    String message = "              You lost.                            \n\n              Your final score is \n                         " + MainGame.currGame.playerStats.score + "              \n\n              Restart Game?              \n\n\n";
		    int reply = JOptionPane.showConfirmDialog(null, message, "", JOptionPane.YES_NO_OPTION);
		    if(reply == 0)
		    {
		    		//restart the game and asks user to choose a new board size they want to play with
			    	Object[] possibilities_of_sizes = {"15x15", "20x15", "30x15"};
			    	String s = (String) JOptionPane.showInputDialog(
			    	                    null,
			    	                    "Choose board size from following options",
			    	                    "Board Size Options",
			    	                    JOptionPane.PLAIN_MESSAGE,
			    	                    null,
			    	                    possibilities_of_sizes,
			    	                    "15x15");
		
			    	//start the game of different board size based on user's input
		    	    if(s.equals("15x15"))
			    	{
			    		GameWithUI maingame = new GameWithUI(15, 15);
			    		thread.interrupt();
			    	}
		    	    else if (s.equals("20x15")) 
			    	{
			    		GameWithUI maingame = new GameWithUI(20, 15);
			    		thread.interrupt();
			    	}
			    	else if(s.equals("30x15"))
			    	{
			    		GameWithUI maingame = new GameWithUI(30, 15);
			    		thread.interrupt();
			    	}

		    }
		    else 
		    {
		    		//exit the game
		    		System.exit(0);
		    }
		    
		}
		
	}
	
	
	/*
	 * check if the next move(its direction depends on keyboard input) of the Enemy is valid
	 * in order to make sure the Enemy will not collide with the wall
	 * @param nextPos_x  x position of the next move
	 * @param nextPos_y  y position of the next move
	 * @return true if the next move is valid
	 */
	public boolean enemyValidMove(int nextPos_x, int nextPos_y)
	{
		Rectangle nextSquare = new Rectangle(nextPos_x, nextPos_y, enemy_width, enemy_height);
		Background background = MainGame.background;
		int bwidth = background.tiles.length;
		int bheight = background.tiles[0].length;
		
		//loop through the tiles to check if the square where the next move will reach will collides with any tile that is a wall
		for(int pos_x = 0; pos_x < bwidth; pos_x++)
		{
			for(int pos_y = 0; pos_y < bheight; pos_y++)
			{
				if(background.tiles[pos_x][pos_y] != null)
				{
					if(nextSquare.intersects(background.tiles[pos_x][pos_y]))
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public void moveRight()
	{
		x += speed;
	}
	
	public void moveLeft()
	{
		x -= speed;
	}
	
	public void moveUp()
	{
		y -= speed;
	}
	
	public void moveDown()
	{
		y += speed;
	}
	
	
	/*
	 * render the Enemy
	 * @param g the graphic context where the Player will be rendered
	 */
	public void render(Graphics g)
	{		
		BufferedImage enemyImage;
		try {
			//get the corresponding image of the enemy
			String imagePath = "/Images/enemy" + renderColor + ".png";
			enemyImage = ImageIO.read(getClass().getResource(imagePath));
			
			//draw the image on the graphic context
			g.drawImage(enemyImage, x, y, 32, 32, null);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
