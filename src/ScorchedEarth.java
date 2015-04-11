/**
 * Class that represents the Scorched Earth game, where tanks shoot missiles at each other and win after a certain number of hits
 * 
 * @author James Hagan, Matt Haneburger
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ScorchedEarth extends JPanel implements ActionListener, ChangeListener
{
	//Instance variables
	private final int WIDTH = 1000, HEIGHT = 750;
	private final int DELAY = 20, MISSILE_SIZE = 10;
	private Timer timer;
	private final int TANK_HEIGHT = 20;
	private final int TANK_WIDTH = 40;
	private int power, angle, roundCount, turn, physicsPower, physicsAngle;
	private double x, y, startTime;
	private final double GRAVITY = 9.8;
	private JButton shoot, addButton;
	private JPanel controlPanel;
	private GamePanel gamePanel;
	private JSlider powerSlider, angleSlider;
	private JLabel powerLabel, angleLabel, scorchedEarthLabel, playerTurn;
	private RoundQueue<Round> roundQueue;
	private Rectangle obstacle, leftTank, rightTank, leftPlatform, rightPlatform;
	
	/**
	 * Creates a new scorched earth game and initializes the instance variables
	 */
	public ScorchedEarth()
	{
		timer = new Timer(DELAY, this);
		x = 90;
		y = 270;
		power = physicsPower = 50;
		angle = physicsAngle = 45;
		roundCount = 0;
		startTime = 0;
		turn = 1;
		
		shoot = new JButton("Shoot!");
		shoot.addActionListener(this);
		addButton = new JButton("Load");
		addButton.addActionListener(this);
		
		//set up graphics
		obstacle = new Rectangle(250, 250, 450, 300);
		leftTank = new Rectangle(50, 280, TANK_WIDTH, TANK_HEIGHT);
		rightTank = new Rectangle(850, 280, TANK_WIDTH, TANK_HEIGHT);
		leftPlatform = new Rectangle(30, 300, 70 , 300);
		rightPlatform = new Rectangle(830, 300, 70 , 300);
		
		//set up control panel
		controlPanel = new JPanel();
		powerSlider = new JSlider(JSlider.VERTICAL, 0, 100, 50);
		angleSlider = new JSlider(JSlider.VERTICAL, 10, 80, 45);
		powerLabel = new JLabel("Power: " + power/20);
		angleLabel = new JLabel("Angle: " + angle/14);
		scorchedEarthLabel = new JLabel("Scorched Earth");
		playerTurn = new JLabel("Player 1 load shot to queue");
		setControlPanel();
		
		//set up the queue
		roundQueue = new RoundQueue<Round>();
		
		//set up game panel
		gamePanel = new GamePanel();
		setGamePanel();
		
		add(gamePanel);
		add(controlPanel);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.black);
		setVisible(false);
	}
	
	/**
	 * Updates the position of the missile every time the timer fires an action event
	 * 
	 * @param event the action event fired by the timer
	 */
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == timer)
		{
			isTurnOver();
			physics();
			gamePanel.repaint();
		}
		else if(event.getSource() == shoot)
		{
			shoot.setEnabled(false);
			calculateTurn(roundCount);
			Round round = new Round();
			round = roundQueue.poll();
			physicsAngle = round.getAngle();
			if(turn == 2)
			{
				int temp = 0;
				physicsAngle += 90;
				if(physicsAngle >= 140)
				{
					temp = physicsAngle - 100;
					physicsAngle -= temp;
				}
				else
				{
					temp = 170 - physicsAngle;
					physicsAngle += temp;
				}
			}
			physicsPower = round.getPower();
			roundCount++;
			timer.start();
			startTime = System.currentTimeMillis();
		}
		else if(event.getSource() == addButton)
		{
			Round round = new Round(angle, power);
			roundQueue.add(round);
			calculateTurn(roundCount);
			roundCount++;
			if(roundCount < 10)
			{
				playerTurn.setText("Player " + turn + " load shot to queue");
			}
			else if(roundCount == 10)
			{
				addButton.setVisible(false);
				shoot.setVisible(true);
				playerTurn.setText("Press 'Shoot!' to play round");
				roundCount = 1 ;
			}
		}
	}
	
	/**
	 * Draws the two tanks on the screen
	 * 
	 * @param tanks the graphics for the tanks
	 */
	public void drawTanks(Graphics tanks)
	{
		tanks.setColor(Color.BLUE);
		tanks.fillRect(leftTank.x, leftTank.y, TANK_WIDTH, TANK_HEIGHT);
		tanks.setColor(Color.pink);
		tanks.fillRect(rightTank.x, rightTank.y, TANK_WIDTH, TANK_HEIGHT);
		tanks.setColor(Color.LIGHT_GRAY);
		tanks.fillRect(leftPlatform.x, leftPlatform.y, leftPlatform.width, leftPlatform.height);
		tanks.fillRect(rightPlatform.x, rightPlatform.y, rightPlatform.width, rightPlatform.height);
	}
	
	/**
	 * Draws the obstacles between the tanks
	 * 
	 * @param obstacles the graphics for the obstacles
	 */
	public void drawObstacles(Graphics obstacles)
	{
		obstacles.setColor(Color.LIGHT_GRAY);
		obstacles.fillRect(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
	}
	
	/**
	 * Draws a missile for the left tank
	 * 
	 * @param missile the graphics for the missile
	 */
	public void drawLeftMissile(Graphics missile)
	{
		missile.setColor(Color.ORANGE);
		missile.fillRect((int) x, (int) y, MISSILE_SIZE, MISSILE_SIZE);
	}
	
	/**
	 * Draws a missile for the right tank
	 * 
	 * @param missile the graphics for the missile
	 */
	public void drawRightMissile(Graphics missile)
	{
		missile.setColor(Color.blue);
		missile.fillRect((int) x + 750, (int) y, MISSILE_SIZE, MISSILE_SIZE);
	}
	
	/**
	 * Resets the game panel to prepare for the next turn
	 */
	public void reset()
	{
		x = 90;
		y = 270;
		calculateTurn(roundCount);
		gamePanel.repaint();
	}
	
	/**
	 * Resets the scorched earth game
	 */
	public void resetGame()
	{
		reset();
		roundCount = 0;
		turn = 1;
		calculateTurn(turn);
		addButton.setVisible(true);
		shoot.setVisible(false);
		x = 90;
		y = 270;
		playerTurn.setText("Player " + turn + " load shot to queue");
		repaint();
		power = physicsPower = 50;
		angle = physicsAngle = 45;
		startTime = 0;
		powerSlider.setValue(power);
		angleSlider.setValue(angle);
		shoot.setEnabled(true);
	}
	
	/**
	 * Sets up the control panel
	 */
	public void setControlPanel()
	{
		controlPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT - 550));
		controlPanel.setBackground(Color.red);	
		powerSlider.setMajorTickSpacing(20);
		powerSlider.setPaintTicks(true);
		angleSlider.setMajorTickSpacing(14);
		angleSlider.setPaintTicks(true);
		powerSlider.addChangeListener(this);
		angleSlider.addChangeListener(this);
		
		scorchedEarthLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 34));
		scorchedEarthLabel.setForeground(Color.black);
		powerLabel.setForeground(Color.black);
		angleLabel.setForeground(Color.black);
		playerTurn.setForeground(Color.black);
		
		//Add components to control panel
		controlPanel.add(scorchedEarthLabel);
		controlPanel.add(powerSlider);
		controlPanel.add(powerLabel);
		controlPanel.add(angleSlider);
		controlPanel.add(angleLabel);
		controlPanel.add(playerTurn);
		controlPanel.add(addButton);
		controlPanel.add(shoot);
		shoot.setVisible(false);
	}
	
	/**
	 * Sets up the game panel
	 */
	public void setGamePanel()
	{
		gamePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT - 210));
		gamePanel.setBackground(Color.DARK_GRAY);
	}
	
	/**
	 * changes the value of the power or angle by listening to the respective sliders
	 */
	public void stateChanged(ChangeEvent event)
	{
		if(event.getSource() == powerSlider)
		{
			power = powerSlider.getValue();
			powerLabel.setText("Power: " + power/20);
		}
		else if(event.getSource() == angleSlider)
		{
			angle = angleSlider.getValue();
			angleLabel.setText("Angle: " + angle/14);
		}
	}
	
	/**
	 * Calculates which player has the current turn and the current round in the queue
	 */
	public void calculateTurn(int count)
	{
		if(count % 2 != 0)
		{
			turn = 1;
		}
		else
		{
			turn = 2;
		}
	}
	
	/**
	 * Calculates the x, y coordinates of the projectile given projectile physics equations and updates
	 * x and y accordingly
	 */
	public void physics()
	{
		double xV;
		double yV;
		double initialVelocity = physicsPower / 3;
		xV = (Math.cos(physicsAngle * Math.PI / 180)) * initialVelocity;
		yV = (Math.sin(physicsAngle * Math.PI / 180)) * initialVelocity;
		double time = System.currentTimeMillis() - startTime;
		time = time / 1000;
		double moveX = xV * time;
		double moveY = (yV * time) - (0.5 * GRAVITY * Math.pow(time, 2));
		x += moveX;
		y -= moveY;
	}
	
	/**
	 * Checks to see if the turn is over due to the projectile colliding with an object on the screen
	 */
	public void isTurnOver()
	{
		if(x >= 0 && x <= WIDTH && y <= HEIGHT - 220)
		{
			if(obstacle.contains(x, y))
			{
				timer.stop();
				isRoundOver();
				shoot.setEnabled(true);
				reset();
			}
			else if(leftPlatform.contains(x, y) || rightPlatform.contains(x, y))
			{
				timer.stop();
				isRoundOver();
				shoot.setEnabled(true);
				reset();
			}
		}
		else if(x <= 0 || x >= WIDTH || y >= HEIGHT - 220)
		{
			timer.stop();
			isRoundOver();
			shoot.setEnabled(true);
			reset();
		}
	}
	
	/**
	 * Checks to see if the round is over due to the depletion of both queues
	 */
	public void isRoundOver()
	{
		if(roundCount == 11)
		{
			calculateTurn(roundCount);
			JOptionPane.showMessageDialog(null, "The Round is Over!");
			resetGame();
			reset();
		}
	}
	
	/**
	 * Checks to see if the game is over due to one player hitting the other player with
	 * the projectile
	 */
	public void isGameOver()
	{
		//check to see if player two wins
		if(leftTank.contains(x, y));
		{
			timer.stop();
			int again = JOptionPane.showConfirmDialog(null, "Player Two Wins! Play Again?", "Game Over", JOptionPane.YES_NO_OPTION);
			if(again == JOptionPane.YES_OPTION)
			{
				resetGame();
			}
			else
			{
				setVisible(false);
				MainMenu.setVisibility();
			}
		}
		//check to see if player one wins
		if(rightTank.contains(x, y))
		{
			timer.stop();
			int again = JOptionPane.showConfirmDialog(null, "Player One Wins! Play Again?", "Game Over", JOptionPane.YES_NO_OPTION);
			if(again == JOptionPane.YES_OPTION)
			{
				resetGame();
			}
			else
			{
				setVisible(false);
				MainMenu.setVisibility();
			}
		}
	}
	
	/**
	 * Class that represents the game panel 
	 */
	private class GamePanel extends JPanel
	{
		/**
		 * Creates a new instance of the game panel
		 */
		public GamePanel()
		{
			//TODO: implement this constructor
		}
		
		/**
		 * Paints all tanks, obstacles, and missiles to the game panel
		 * 
		 * @param page the graphics context, in this case the game panel
		 */
		public void paintComponent(Graphics page)
		{
			super.paintComponent(page);
			drawTanks(page);
			drawObstacles(page);
			if(turn == 1)
			{
				drawLeftMissile(page);
			}
			else if(turn == 2)
			{
				drawRightMissile(page);
			}
		}
	}
}
