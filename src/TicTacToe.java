/**
 * Class that presents and runs a tic-tac-toe game
 * 
 * @author James Hagan, Matt Haneburger
 * @version 1.0
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class TicTacToe extends JPanel implements ActionListener, KeyListener 
{
	//Instance variables
	private JPanel panel, gamePanel, scorePanel;
	private JButton button1, button2, button3, button4, button5, button6, button7, button8, button9;
	private JOptionPane endGame = new JOptionPane();
	private String letter;
	private boolean win = false;
	private ImageIcon xIcon, oIcon;
	private JLabel winsLabel, xLabel, oLabel, tieLabel, turnLabel;
	private int count, xCount, oCount, tieCount;
	private JButton resetBoardButton, resetScoresButton, mainMenuButton;
	private Stack<String> moves;

	/**
	 * Creates a new instance of the tic-tac-toe game and presents the game to the user
	 */
	public TicTacToe() 
	{
		//initialize buttons
		button1 = new JButton("");
		button2 = new JButton("");
		button3 = new JButton("");
		button4 = new JButton("");
		button5 = new JButton("");
		button6 = new JButton("");
		button7 = new JButton("");
		button8 = new JButton("");
		button9 = new JButton("");
		
		//Create panel
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(1000, 1000));
		FlowLayout fLayout = new FlowLayout();
		fLayout.setHgap(100);
		panel.setLayout(fLayout);
		panel.setBackground(Color.black);
		
		//Set up gamePanel
		gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(3, 3));
		gamePanel.setPreferredSize(new Dimension(450, 450));
		
		//Add buttons to gamePanel
		gamePanel.add(button1);
		gamePanel.add(button2);
		gamePanel.add(button3);
		gamePanel.add(button4);
		gamePanel.add(button5);
		gamePanel.add(button6);
		gamePanel.add(button7);
		gamePanel.add(button8);
		gamePanel.add(button9);
		moves = new Stack<String>();
		
		//Set images, count, and letter
		oIcon = new ImageIcon("oIcon.png");
		xIcon = new ImageIcon("xIcon.png");
		count = 0;
		letter = "X";
		
		//Set up scorePanel
		scorePanel = new JPanel();
		GridLayout sPanelLayout = new GridLayout(2, 1, 100, 250);
		scorePanel.setLayout(sPanelLayout);
		scorePanel.setPreferredSize(new Dimension(250, 700));
		scorePanel.setBackground(Color.red);
		scorePanel.setLocation(750, 125);
		xCount = oCount = tieCount = 0;
		winsLabel = new JLabel("Number of Wins", SwingConstants.CENTER);
		winsLabel.setForeground(Color.black);
		xLabel = new JLabel("Player X: " + xCount);
		xLabel.setForeground(Color.black);
		oLabel = new JLabel("Player O: " + oCount);
		oLabel.setForeground(Color.black);
		tieLabel = new JLabel("Draws: " + tieCount);
		tieLabel.setForeground(Color.black);
		turnLabel = new JLabel("Player " + letter + "'s Turn", SwingConstants.CENTER);
		turnLabel.setForeground(Color.black);
		turnLabel.setFont(new Font("myFont", Font.BOLD, 24));
		resetBoardButton = new JButton("Reset Board");
		resetBoardButton.setPreferredSize(new Dimension(150, 25));
		resetScoresButton = new JButton("Reset Scores");
		resetScoresButton.setPreferredSize(new Dimension(150, 25));
		mainMenuButton = new JButton("Main Menu");
		mainMenuButton.setPreferredSize(new Dimension(150, 25));
		FlowLayout scoreFlow = new FlowLayout();
		scoreFlow.setVgap(15);
		JPanel scoreButtonPanel = new JPanel();
		JPanel scoreLabelPanel = new JPanel();
		scoreLabelPanel.setLayout(new GridLayout(5, 1));
		scoreButtonPanel.setLayout(scoreFlow);
		scoreLabelPanel.add(turnLabel);
		scoreLabelPanel.add(winsLabel);
		scoreLabelPanel.add(xLabel);
		scoreLabelPanel.add(oLabel);
		scoreLabelPanel.add(tieLabel);
		scoreButtonPanel.add(resetBoardButton);
		scoreButtonPanel.add(resetScoresButton);
		scoreButtonPanel.add(mainMenuButton);
		scoreLabelPanel.setBackground(Color.red);
		scoreButtonPanel.setBackground(Color.red);
		scorePanel.add(scoreLabelPanel);
		scorePanel.add(scoreButtonPanel);
				
		// Add The Action Listener To The Buttons
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);
		button7.addActionListener(this);
		button8.addActionListener(this);
		button9.addActionListener(this);
		resetBoardButton.addActionListener(this);
		resetScoresButton.addActionListener(this);
		mainMenuButton.addActionListener(this);
		
		panel.add(gamePanel);
		panel.add(scorePanel);
		panel.setVisible(false);
		panel.addKeyListener(this);
		gamePanel.addKeyListener(this);
	}
	
	/**
	 * Checks to see if the key combination is CTRL + Z. If so, then performs the undo action
	 * 
	 * @param event the key event to be processed
	 */
	public void keyPressed(KeyEvent event)
	{
		if(event.isControlDown() && event.getKeyCode() == KeyEvent.VK_Z)
		{
			undoMove();
		}
	}
	
	//provide empty methods
	public void keyTyped(KeyEvent event) {}
	public void keyReleased(KeyEvent event) {}

	/**
	 * Detects a button has been pressed and performs the according action
	 * 
	 * @param a the action event that is fired
	 */
	public void actionPerformed(ActionEvent a) 
	{
		count++;
		JButton tempButton = (JButton) a.getSource();
		
		//Check to see if the tile has already been selected
		if(tempButton.getText().equalsIgnoreCase("X") || tempButton.getText().equalsIgnoreCase("O"))
		{
			JOptionPane.showMessageDialog(null, "This tile has already been selected. Please select another tile.");
			count--;
			gamePanel.requestFocus();
			return;
		}
		
		// Calculate Who's Turn It Is
		ImageIcon icon = calculateTurn();
	
		// Display X's or O's on the buttons
		if (a.getSource() == button1) 
		{
			button1.setIcon(icon);
			button1.setText(letter);
			moves.push("button1");
		} 
		else if (a.getSource() == button2) 
		{
			button2.setIcon(icon);
			button2.setText(letter);
			moves.push("button2");
		} 
		else if (a.getSource() == button3) 
		{
			button3.setIcon(icon);
			button3.setText(letter);
			moves.push("button3");
		}
		else if (a.getSource() == button4) 
		{
			button4.setIcon(icon);
			button4.setText(letter);
			moves.push("button4");
		} 
		else if (a.getSource() == button5) 
		{
			button5.setIcon(icon);
			button5.setText(letter);
			moves.push("button5");
		} 
		else if (a.getSource() == button6) 
		{
			button6.setIcon(icon);
			button6.setText(letter);
			moves.push("button6");
		}
		else if (a.getSource() == button7) 
		{
			button7.setIcon(icon);
			button7.setText(letter);
			moves.push("button7");
		}
		else if (a.getSource() == button8) 
		{
			button8.setIcon(icon);
			button8.setText(letter);
			moves.push("button8");
		} 
		else if (a.getSource() == button9) 
		{
			button9.setIcon(icon);
			button9.setText(letter);
			moves.push("button9");
		}
		else if (a.getSource() == resetBoardButton)
		{
			resetPanel();
		}
		else if (a.getSource() == resetScoresButton)
		{
			count--;
			resetScores();
			if(letter.equalsIgnoreCase("o"))
			{
				turnLabel.setText("Player O's Turn");
			}
			else
			{
				turnLabel.setText("Player X's Turn");
			}
		}
		else if (a.getSource() == mainMenuButton)
		{
			panel.setVisible(false);
			resetPanel();
			MainMenu.setVisibility();
		}
		

		// Determine who wins
		// horizontal wins
		if (button1.getText() == button2.getText() && button2.getText() == button3.getText() && button1.getText() != "") 
		{
			win = true;
		} 
		else if (button4.getText() == button5.getText() && button5.getText() == button6.getText() && button4.getText() != "") 
		{
			win = true;
		} 
		else if (button7.getText() == button8.getText()	&& button8.getText() == button9.getText() && button7.getText() != "") 
		{
			win = true;
		}

		// vertical wins
		else if (button1.getText() == button4.getText()	&& button4.getText() == button7.getText() && button1.getText() != "") 
		{
			win = true;
		} 
		else if (button2.getText() == button5.getText()	&& button5.getText() == button8.getText() && button2.getText() != "") 
		{
			win = true;
		} 
		else if (button3.getText() == button6.getText()	&& button6.getText() == button9.getText() && button3.getText() != "") 
		{
			win = true;
		}

		// diagonal wins
		else if (button1.getText() == button5.getText()	&& button5.getText() == button9.getText() && button1.getText() != "") 
		{
			win = true;
		} else if (button3.getText() == button5.getText()	&& button5.getText() == button7.getText() && button3.getText() != "") 
		{
			win = true;
		} 
		else 
		{
			win = false;
		}

		// Show a dialog if someone wins or the game is tie
		if (win == true) 
		{
			if(letter.equalsIgnoreCase("x"))
			{
				xCount++;
				setXLabel(xCount);
			}
			else
			{
				oCount++;
				setOLabel(oCount);
			}
			JOptionPane.showMessageDialog(null, letter + " WINS!");
			endGame.setOptionType(JOptionPane.YES_NO_OPTION);
			int reply = JOptionPane.showConfirmDialog(null, "Would you like to play again?", "Play Again", JOptionPane.YES_OPTION);
			if (reply == JOptionPane.YES_OPTION) 
			{
				resetPanel();
			} 
			else if (reply == JOptionPane.NO_OPTION) 
			{
				JOptionPane.showMessageDialog(null, "Thank you for playing!");
				panel.setVisible(false);
				resetPanel();
				MainMenu.setVisibility();
			}
		} 
		else if (count == 9 && win == false) 
		{
			tieCount++;
			setTieLabel(tieCount);
			JOptionPane.showMessageDialog(null, "Tie Game!");
			endGame.setOptionType(JOptionPane.YES_NO_OPTION);
			int reply = JOptionPane.showConfirmDialog(null, "Would you like to play again?", "Play Again", JOptionPane.YES_OPTION);
			if (reply == JOptionPane.YES_OPTION) 
			{
				resetPanel();
			} 
			else if (reply == JOptionPane.NO_OPTION) 
			{
				JOptionPane.showMessageDialog(null, "Thank you for playing!");
				panel.setVisible(false);
				resetPanel();
				MainMenu.setVisibility();
			}
		}
		panel.setFocusable(true);
		panel.requestFocus();
	}

	/**
	 * Resets the game panel to create a new game of tic-tac-toe
	 */
	public void resetPanel() 
	{
		button1.setText("");
		button1.setEnabled(true);
		button1.setIcon(null);
		button2.setText("");
		button2.setEnabled(true);
		button2.setIcon(null);
		button3.setText("");
		button3.setEnabled(true);
		button3.setIcon(null);
		button4.setText("");
		button4.setEnabled(true);
		button4.setIcon(null);
		button5.setText("");
		button5.setEnabled(true);
		button5.setIcon(null);
		button6.setText("");
		button6.setEnabled(true);
		button6.setIcon(null);
		button7.setText("");
		button7.setEnabled(true);
		button7.setIcon(null);
		button8.setText("");
		button8.setEnabled(true);
		button8.setIcon(null);
		button9.setText("");
		button9.setEnabled(true);
		button9.setIcon(null);
		letter = "";
		count = 0;
		win = false;
		turnLabel.setText("Player X's Turn");
		while(!moves.empty())
		{
			
			moves.pop();
		}
	}
	
	/**
	 * Sets the panel visibility to true
	 */
	public void setPanelVisibility()
	{
		panel.setVisible(true);
	}
	
	/**
	 * Returns the JPanel
	 * 
	 * @return panel the panel that contains the game panel and score panel
	 */
	public JPanel getPanel()
	{
		return panel;
	}
	
	/**
	 * Sets the text of the x label
	 * 
	 * @param numWins the number of wins of the x player
	 */
	private void setXLabel(int numWins)
	{
		xLabel.setText("Player X: " + xCount);
	}
	
	/**
	 * Sets the text of the o label
	 * 
	 * @param numWins the number of wins of the o player
	 */
	private void setOLabel(int numWins)
	{
		oLabel.setText("Player O: " + oCount);
	}
	
	/**
	 * Sets the text of the tie label
	 * 
	 * @param numDraws the number of draws
	 */
	private void setTieLabel(int numDraws)
	{
		tieLabel.setText("Draws: " + tieCount);
	}
	 
	/**
	 * Resets the board and the scores
	 */
	private void resetScores()
	{
		xCount = oCount = tieCount = 0;
		setXLabel(xCount);
		setOLabel(oCount);
		setTieLabel(tieCount);
	}
	
	/**
	 * Undoes the most recent move and updates the board
	 */
	public void undoMove()
	{
		if(count == 0)
		{
			JOptionPane.showMessageDialog(null, "There are no more moves to undo. Please select a tile.");
			return;
		}
		String button = moves.pop();
		if(button.equals("button1"))
		{
			button1.setIcon(null);
			button1.setText("");
		}
		else if(button.equals("button2"))
		{
			button2.setIcon(null);
			button2.setText("");
		}
		else if(button.equals("button3"))
		{
			button3.setIcon(null);
			button3.setText("");
		}
		else if(button.equals("button4"))
		{
			button4.setIcon(null);
			button4.setText("");
		}
		else if(button.equals("button5"))
		{
			button5.setIcon(null);
			button5.setText("");
		}
		else if(button.equals("button6"))
		{
			button6.setIcon(null);
			button6.setText("");
		}
		else if(button.equals("button7"))
		{
			button7.setIcon(null);
			button7.setText("");
		}
		else if(button.equals("button8"))
		{
			button8.setIcon(null);
			button8.setText("");
		}
		else if(button.equals("button9"))
		{
			button9.setIcon(null);
			button9.setText("");
		}
		count--;
		
		//Check to see who's turn it is
		calculateTurn();
	}
	
	/**
	 * Calculates who's turn it is and sets the turn label to the correct string
	 */
	public ImageIcon calculateTurn()
	{
		ImageIcon icon = new ImageIcon();
		if (count%2 != 0) 
		{
			icon = xIcon;
			letter = "X";
			turnLabel.setText("Player O's Turn");
		} 
		else
		{
			icon = oIcon;
			letter = "O";
			turnLabel.setText("Player X's Turn");
		}
		return icon;
	}
}