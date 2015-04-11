import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

/**
 * Class that represents a queue of rounds to hold five turns for each player in the scorched earth game
 * 
 * @author James Hagan, Matt Haneburger
 */
public class RoundQueue<E> implements Queue
{
	//Instance variable
	private Round[] roundArray;
	private int count, dequeueCount;
	/**
	 * Creates a new instance of RoundQueue and initializes instance variables
	 */
	public RoundQueue()
	{
			roundArray = new Round[10];
			count = dequeueCount = 0;
	}
	public boolean addAll(Collection arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}

	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean containsAll(Collection arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeAll(Collection arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean retainAll(Collection arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public int size() 
	{
		return roundArray.length;
	}

	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] toArray(Object[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean add(Object round) 
	{
		Round newRound = (Round) round;
		if(count < 10)
		{
			roundArray[count] = newRound;
			count++;
			return true;
		}
		else
		{
			return false;
		}
	}

	public Object element() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean offer(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object peek() 
	{
		return roundArray[dequeueCount];
	}

	public Round poll() 
	{
		if(dequeueCount < 10)
		{
			Round round = roundArray[dequeueCount];
			roundArray[dequeueCount] = null;
			dequeueCount++;
			return round;
		}
		else
		{
			return null;
		}
	}

	public Object remove() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Returns the array of rounds
	 * 
	 * @return array the array of rounds
	 */
	public Round[] getRoundArray()
	{
		return roundArray;
	}
}
