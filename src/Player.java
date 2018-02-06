import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SpringLayout.Constraints;

public class Player extends Rectangle{
	
	private final static int player_width = 24;
	private final static int player_height = 24;
	private final static int player_offset_x = 4;
	private final static int player_offset_y = 4;
	private int playerScore;
	
	public boolean RIGHT, LEFT, UP, DOWN;
	private int speed = 2;
	
	public static Graphics g;
	
	public GameWithUI fullGame;
	//constructor for the Player
	public Player(int x, int y, GameWithUI game)
	{
		fullGame = game;
		
		//specify the position and size of the Player
		setBounds(x+player_offset_x, y+player_offset_y, player_width, player_height);
		g = MainGame.graphics;
		playerScore = 0;
	}
	
	
	//move the player in corresponding direction at speed=2 based on Keyboard input(left, right, up or down) 
	public void playerMove()
	{
		if(RIGHT && playerValidMove(x+speed, y)) 
			x += speed;
		if(LEFT && playerValidMove(x-speed, y))  
			x -= speed;
		if(UP && playerValidMove(x, y-speed))  
			y -= speed;
		if(DOWN && playerValidMove(x, y+speed))
			y += speed;			
		
	}
	
	
	//eliminate coin when it collides with the player
	public void coinsToBeEliminated()
	{
		Background background = MainGame.background;
		List<Coins> coins = background.coins;
		
		for (Coins coin : coins) 
		{
			if(this.intersects(coin))
			{
				//move the x position of coin to make it disappear in screen
				coin.x += 1000;
				//increment the score based on different types of coins eaten by the player
				if(coin.getColorIndex() == 2)
				{
					playerScore += 10;
				}
				else if(coin.getColorIndex() == 1)
				{
					playerScore += 20;
				}
				else if(coin.getColorIndex() == 3)
				{
					playerScore += 30;
				}
				else if(coin.getColorIndex() == 0)
				{
					playerScore += 40;
				}
				else {
					playerScore += 50;
				}
				fullGame.playerStats.setScoreForPlayer(playerScore);
			}
		}
		
	}
	
	/*
	 * check if the next move(its direction depends on keyboard input) of the Player is valid
	 * in order to make sure the Player will not collide with the wall
	 * @param nextPos_x  x position of the next move
	 * @param nextPos_y  y position of the next move
	 * @return true if the next move is valid
	 */
	public boolean playerValidMove(int nextPos_x, int nextPos_y)
	{
		Rectangle nextSquare = new Rectangle(nextPos_x+player_offset_x, nextPos_y+player_offset_y, player_width, player_height);
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
	
	
	/*
	 * render the Player
	 * @param g the graphic context where the Player will be rendered
	 */
	public void render(Graphics g)
	{
		
		BufferedImage playerImage;
		try {
			//get the corresponding image of the player
			playerImage = ImageIO.read(getClass().getResource("/Images/player.png"));
			g.drawImage(playerImage, x, y, 30, 30, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//get the current score of the player
	public int getPlayerScore()
	{
		return this.playerScore;
	}
}
