import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.ObjectInputStream.GetField;

import javax.swing.JFrame;

public class GameWithUI {
	
	private final JFrame gameFrame;
	public Statistics playerStats;
	public MainGame game;
	private final static Dimension JFRAME_DIM = new Dimension(800, 650);
	private int board_width;
	private int board_height;
	
	public GameWithUI(int width, int height)
	{
		//set up the game frame 
		board_width = width;
		board_height = height;
		this.gameFrame = new JFrame();
		this.gameFrame.setTitle("Pac-Man");
		this.gameFrame.setSize(JFRAME_DIM);
		
		game = new MainGame(this);
		playerStats = new Statistics();

		this.gameFrame.add(playerStats, BorderLayout.NORTH);
		this.gameFrame.add(game, BorderLayout.CENTER);
		 
		this.gameFrame.pack();
		this.gameFrame.setResizable(false);
		this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameFrame.setLocationRelativeTo(null);
		this.gameFrame.setVisible(true);
		
		//start the pacman game
		game.startGame();
	}
	
	public int getBoardWidth()
	{
		return this.board_width;
	}
	
	public int getBoardHeight()
	{
		return this.board_height;
	}
}
