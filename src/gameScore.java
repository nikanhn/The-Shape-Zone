/**
 * Name: Nicholas Hong, Nikan Hojatnia
 * Course: CS170-01
 * Submission Date: 10:00 PM, Wednesday(5/12)
 * Group Project: The Shape Zone
 * gameScore class holds the name and score of the player. It has a constructor that sets the name 
 * and score and methods to return them. It has an overridden method called compareTo from Comparable
 *  interface that compares the scores and return an integer. This is to use Collections.sort().
 */
import java.util.*;

//class used to store user data
//implement comparable to override functions
public class gameScore implements Comparable<gameScore>
{
	//name of user/player
	String userName;
	//score of that player
	int userScore;
	//Constructor that sets the name and score
	public gameScore(String name, int score)
	{
		userName = name;
		userScore = score;
	}
	
	
	/**
	 * Method to return the user's name
	 * @return string
	 */
	public String getName() 
	{
		return userName;
	}

	
	/**
	 * Method to return the user's score
	 * @param none
	 * @return int
	 */
	public int getScore()
	{
		return userScore;
	}
	
	//overriding compareTo function to use Collection.sort
	//from highest score to lowest
	@Override
	public int compareTo(gameScore player) {
		return player.getScore()-this.userScore;
	}
	
	
}
