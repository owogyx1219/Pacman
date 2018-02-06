import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
	private final static Dimension JFRAME_DIM = new Dimension(800, 600);
	
	public static void main(String[] args) throws IOException
	{
		//ask user if they want to start the game
	    String message = "Start game?";
	    int reply = JOptionPane.showConfirmDialog(null, message, "", JOptionPane.YES_NO_OPTION);
	    if(reply == 0)
	    {
		    	Object[] possibilities_of_sizes = {"15x15", "20x15", "30x15"};
		    	String s = (String) JOptionPane.showInputDialog(
		    	                    null,
		    	                    "Choose board size from following options",
		    	                    "Board Size Options",
		    	                    JOptionPane.PLAIN_MESSAGE,
		    	                    null,
		    	                    possibilities_of_sizes,
		    	                    "15x15");
	
		    	//If a string was returned, say so.
		    if(s.equals("15x15"))
		    	{
		    		GameWithUI maingame = new GameWithUI(15, 15);
		    	}
			else if (s.equals("20x15")) {
		    		GameWithUI maingame = new GameWithUI(20, 15);	
		    	}
		    	else if(s.equals("30x15"))
		    	{
		    		GameWithUI maingame = new GameWithUI(30, 15);
		    	}
		    

	    		
	    }
	}
}
