/**
 * Class that represents the main menu of the arcade. Gives the user the choice to play tic-tac-toe or scorched earth.
 * 
 * @author James Hagan, Matt Haneburger
 * @version 1.0
 */
import java.awt.*;
import java.awt.event.*;
import java.util.Queue;

import javax.swing.*;

public class MainMenu extends JPanel implements KeyListener
{
	//Instance Variables
	private JButton tictactoe, scorch;
	private static JPanel buttonPanel;
	private JLabel label;
	private TicTacToe ttt;
	private ScorchedEarth scorchedEarth;
	private String[] konamiArray, konamiCode;
	private int konamiCount;

	/**
	 * Creates a new instance of the main menu
	 */
	public MainMenu() 
	{
		// Tictactoe button
		tictactoe = new JButton("Tic-Tac-Toe");
		
		// Scorched Earth button
		scorch = new JButton("Scorched Earth");
		tictactoe.setBounds(0,0,250,250);
		
		// This block waits for a button to be clicked and implements action accordingly
		ButtonListener listener = new ButtonListener();
		tictactoe.addActionListener(listener);
		scorch.addActionListener(listener);

		label = new JLabel("Which game would you like to play?", SwingConstants.CENTER);
		label.setForeground(Color.BLACK);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		JPanel choicePanel = new JPanel();
		choicePanel.setBackground(Color.red);
		choicePanel.add(tictactoe);
		choicePanel.add(scorch);
		buttonPanel.setPreferredSize(new Dimension(400, 400));
		buttonPanel.setBackground(Color.red);
		buttonPanel.add(label, BorderLayout.NORTH);
		buttonPanel.add(choicePanel, BorderLayout.CENTER);
		ttt = new TicTacToe();
		scorchedEarth = new ScorchedEarth();
		konamiArray = new String[10];
		konamiCode = new String[10];
		konamiCode[0] = "38";
		konamiCode[1] = "38";
		konamiCode[2] = "40";
		konamiCode[3] = "40";
		konamiCode[4] = "37";
		konamiCode[5] = "39";
		konamiCode[6] = "37";
		konamiCode[7] = "39";
		konamiCode[8] = "66";
		konamiCode[9] = "65";
		konamiCount = 0;
		
		setPreferredSize(new Dimension(1000, 750));
		setBackground(Color.BLACK);
		add(buttonPanel);
		add(ttt.getPanel());
		add(scorchedEarth);
		ttt.getPanel().setFocusable(true);
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
	}

	/**
	 * Represents a listener for both of the buttons
	 */
	private class ButtonListener implements ActionListener 
	{
		// Determines which button was pressed
		public void actionPerformed(ActionEvent event) 
		{
			if (event.getSource() == tictactoe)
			{
				buttonPanel.setVisible(false);
				ttt.setPanelVisibility();
				ttt.getPanel().setFocusable(true);
				ttt.getPanel().requestFocus();
			}
			else if (event.getSource() == scorch)
			{
				buttonPanel.setVisible(false);
				scorchedEarth.setVisible(true);
			}
		}
	}
	
	/**
	 * Checks to see if the key combination is CTRL + Z. If so, then performs the undo action
	 * 
	 * @param event the key event to be processed
	 */
	public void keyPressed(KeyEvent event)
	{
		konamiCount++;
		konamiArray[konamiCount - 1] = String.valueOf(event.getKeyCode());
		if(konamiCount == 10)
		{
			if(testKonami())
			{
				JOptionPane.showMessageDialog(null, "You've unlocked Konami!");
			}
		}
	}
	
	//provide empty methods
	public void keyTyped(KeyEvent event) {}
	public void keyReleased(KeyEvent event) {}
	
	/**
	 * Sets the visibility for the button panel to true
	 */
	public static void setVisibility()
	{
		buttonPanel.setVisible(true);
	}
	
	/**
	 * Returns the tic-tac-toe object
	 * 
	 * @return TicTacToe the tic-tac-toe object
	 */
	public TicTacToe getTicTacToe()
	{
		return ttt;
	}
	
	/**
	 * Returns the scorched earth object
	 * 
	 * @return ScorchedEarth the scorched earth object
	 */
	public ScorchedEarth getScorchedEarth()
	{
		return scorchedEarth;
	}
	
	/**
	 * Tests the values of the input characters and sees if it matches with the Konami Code
	 */
	public boolean testKonami()
	{
		boolean match = true;
		for(int i = 0; i < konamiArray.length; i++)
		{
			if(konamiArray[i].equals(konamiCode[i]))
			{
				match = true;
			}
			else
			{
				match = false;
				i = 10;
			}
		}
		for(int i = 0; i < konamiArray.length; i++)
		{
			konamiArray[i] = null;
		}
		konamiCount = 0;
		return match;
	}
}
