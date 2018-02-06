import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.*;
import java.util.Random;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Statistics extends JPanel{

	JLabel scoreOfPlayer1;
	JButton forfeit;
	public static int score;
	
	//constructor
	public Statistics()
	{
		score = 0;
		setUpScore();		
		this.add(scoreOfPlayer1);
	}
	
	//set up player score
	private void setUpScore() 
	{
		scoreOfPlayer1  = new JLabel(" Scores of Player" + ": " + score);
		scoreOfPlayer1.setFont(new Font("Serif", Font.BOLD, 20));
		this.setLayout(new GridLayout(1, 1));
	}

	public void setScoreForPlayer(int currScore)
	{
		score = currScore;
		scoreOfPlayer1.setText(" Scores of Player" + ": " + currScore);
	}

}
