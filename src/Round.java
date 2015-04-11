/**
 * Class that represents a round of play in the scorched earth game
 * 
 * @author James Hagan, Matt Haneburger
 * @version 1.0
 */
public class Round 
{
	//Instance variables
	private int angle, power;
	
	/**
	 * Creates a new instance of the round class and initializes instance variables to default values
	 */
	public Round()
	{
		angle = 45;
		power = 50;
	}
	
	/**
	 * Creates a new instance of the round class and initializes instance variables to specified values
	 */
	public Round(int angle, int power)
	{
		this.angle = angle;
		this.power = power;
	}
	
	/**
	 * Returns the angle of the round
	 * 
	 * @return int the angle of the round
	 */
	public int getAngle()
	{
		return angle;
	}
	
	/**
	 * Returns the power of the round
	 * 
	 * @return int the power of the round
	 */
	public int getPower()
	{
		return power;
	}
}
