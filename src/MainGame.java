import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class MainGame extends Canvas implements Runnable, KeyListener{
	
	public static int WIDTH = 640, HEIGHT = 480;
	
	private boolean gameIsRunning = false;
	private Thread thread;
	
	public static Player gamePlayer;
	public static Background background;
	public static Graphics graphics; 
	
	public static GameWithUI currGame;
	
	public MainGame(GameWithUI fullGame)
	{
		//update teh height and width of canvas based on different board size
		if(fullGame.getBoardWidth() == 15 && fullGame.getBoardHeight() == 15)
		{
			WIDTH = 480;
			HEIGHT = 480;
		}
		else if(fullGame.getBoardWidth() == 20 && fullGame.getBoardHeight() == 15)
		{
			WIDTH = 640;
			HEIGHT = 480;
		}
		else if(fullGame.getBoardWidth() == 30 && fullGame.getBoardHeight() == 15)
		{
			WIDTH = 960;
			HEIGHT = 480;
		}
		
		setPreferredSize(new Dimension(MainGame.WIDTH, MainGame.HEIGHT));
				
		this.setFocusable(true); 
		this.addKeyListener(this);
		
		currGame = fullGame;
		
		//set up player and background(contains coins, enemies and walls) in the game
		gamePlayer = new Player(MainGame.WIDTH/2, MainGame.HEIGHT/2, fullGame);
		
		//load different images based on different board sizes
		if(fullGame.getBoardWidth() == 15 && fullGame.getBoardHeight() == 15)
		{
			background = new Background("/Images/background15_15.png", this);
		}
		else if(fullGame.getBoardWidth() == 20 && fullGame.getBoardHeight() == 15)
		{
			background = new Background("/Images/background.png", this);
		}
		else if(fullGame.getBoardWidth() == 30 && fullGame.getBoardHeight() == 15)
		{
			background = new Background("/Images/background30_15.png", this);
		}
	}
	
	//start the game
	public synchronized void startGame()
	{
		if(gameIsRunning)
		{
			return;
		}
		
		gameIsRunning = true;	
		setThread(new Thread(this));
		getThread().start();
	}
	
	//stop the game
	public synchronized void stopGame()
	{
		if(!gameIsRunning)
		{
			return;
		}
		
		gameIsRunning = false;
		try {
			getThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	//move players and enemies
	private void movePlayerAndEnemies()
	{
		gamePlayer.playerMove();
		background.enemiesMove();
	}
	
	//eliminate the coin when it collides with the player
	public void updateCoins()
	{
		gamePlayer.coinsToBeEliminated();
	}
	
	//render players and background(contains coins, enemies and walls)
	private void render()
	{
		BufferStrategy bs = getBufferStrategy();
		if(bs== null)
		{
			createBufferStrategy(2);
			return;
		}
		
		graphics = bs.getDrawGraphics();
		
		//set up background color
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, MainGame.WIDTH, MainGame.HEIGHT);
		
		//render player and background
		gamePlayer.render(graphics);
		background.render(graphics);
		graphics.dispose();
		bs.show();
	}
	
	
	@Override
	public void run()
	{
		int fps = 0;
		double delta = 0;
		double moveFrequency = 60.0;

		double timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double remainTime = 10;
		
		while(gameIsRunning)
		{
			long currentTime = System.nanoTime();
		
			delta += (currentTime - lastTime) / (1000000000 / moveFrequency);
			lastTime = currentTime;
			
			while(delta >= 30)
			{
				movePlayerAndEnemies();
				updateCoins();
				render();
				fps++;
				delta--;
			}
	
			if(System.currentTimeMillis() - timer >= 2000)
			{
				fps = 0;
				timer += 1000;
			}
		}
		
		startGame();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	//Map keyboard input(right,left, up, down) to player's corresponding move when key is pressed 
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT:
				gamePlayer.RIGHT = true;
				break;
			case KeyEvent.VK_LEFT:
				gamePlayer.LEFT = true;
				break;
			case KeyEvent.VK_UP:
				gamePlayer.UP = true;
				break;
			case KeyEvent.VK_DOWN:
				gamePlayer.DOWN = true;
				break;		
		}
	}

	
	//stop the movement of player when key is released
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT:
				gamePlayer.RIGHT = false;
				break;
			case KeyEvent.VK_LEFT:
				gamePlayer.LEFT = false;
				break;
			case KeyEvent.VK_UP:
				gamePlayer.UP = false;
				break;
			case KeyEvent.VK_DOWN:
				gamePlayer.DOWN = false;
				break;		
		}
	}


	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}
}
