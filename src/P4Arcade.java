/**
 * Class that creates a window for the user to play tic-tac-toe or scorched earth
 * 
 * @author James Hagan, Matt Haneburger
 * @version 1.0
 */

import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.swing.*;

public class P4Arcade extends JFrame implements ActionListener
{
	//Instance variables
	private static JFrame frame;
	private MainMenu mainMenu;
	
	/**
	 * Main method to run the program
	 * 
	 * @param args an array of command line arguments
	 */
	public static void main(String[] args) 
	{
		if(args.length > 0)
		{
			System.out.println("This program takes zero arguments, and one or more arguments have been entered.");
			System.out.println("Please try running the program again with zero arguments.");
			System.exit(0);
		}
		P4Arcade arcade = new P4Arcade();
		arcade.setFrame();
	}
	
	/**
	 * Represents and performs the action selected in the menu bar
	 * 
	 * @param event the action event
	 */
	public void actionPerformed(ActionEvent event)
	{
		JMenuItem mi = (JMenuItem) event.getSource();
		if(mi.getText().equals("Exit"))
		{
			int status;
			status = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
			if(status == JOptionPane.YES_OPTION)
			{
				System.exit(0);
			}
		}
		else if(mi.getText().equals("Main Menu"))
		{
			mainMenu.getTicTacToe().getPanel().setVisible(false);
			mainMenu.getScorchedEarth().setVisible(false);
			mainMenu.getScorchedEarth().resetGame();
			MainMenu.setVisibility();
		}
		else if(mi.getText().equals("Help"))
		{
			Scanner scanner = null;
			String instructions = "";
			try
			{
				scanner = new Scanner(new File("Instructions.txt"));
			}
			catch(FileNotFoundException e)
			{
				JOptionPane.showMessageDialog(null, "There was an error reading the file.");
				return;
			}
			while(scanner.hasNext())
			{
				instructions += scanner.nextLine() + "\n";
			}
			scanner.close();
			JFrame instructionsFrame = new JFrame("Instructions");	
			instructionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JPanel instructionsPanel = new JPanel();
			JTextArea instructionsText = new JTextArea(instructions);
			instructionsText.setPreferredSize(new Dimension(750, 750));
			instructionsPanel.add(instructionsText);
			instructionsFrame.getContentPane().add(instructionsPanel);
			instructionsFrame.pack();
			instructionsFrame.setVisible(true);
		}
		else if(mi.getText().equals("Undo Move"))
		{
			if(checkTicTacToePanel())
			{
				mainMenu.getTicTacToe().undoMove();
			}
		}
	}
	
	/**
	 * Sets the frame for the P4Arcade
	 */
	public void setFrame()
	{
		frame = new JFrame("Welcome to the Arcade!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		fileMenu.setMnemonic('f');
		editMenu.setMnemonic('d');
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem help = new JMenuItem("Help");
		JMenuItem undo = new JMenuItem("Undo Move");
		JMenuItem main = new JMenuItem("Main Menu");
		help.setMnemonic('h');
		exit.addActionListener(this);
		help.addActionListener(this);
		undo.addActionListener(this);
		main.setMnemonic('m');
		main.addActionListener(this);
		exit.setMnemonic('x');
		undo.setMnemonic('u');
		fileMenu.add(main);
		fileMenu.add(help);
		fileMenu.add(exit);
		editMenu.add(undo);
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		
		mainMenu = new MainMenu();
		frame.getContentPane().add(mainMenu);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	/**
	 * Checks to see if the tic-tac-toe panel is visible
	 */
	public boolean checkTicTacToePanel()
	{		
		if(mainMenu.getTicTacToe().getPanel().isVisible())
		{
			return true;
		}
		else
		{
			JOptionPane.showMessageDialog(null,  "This item is only used while playing Tic-Tac-Toe");
			return false;
		}
	}
}

