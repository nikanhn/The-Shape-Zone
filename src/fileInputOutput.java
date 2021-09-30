/**
 * Name: Nicholas Hong, Nikan Hojatnia
 * Course: CS170-01
 * Submission Date: 10:00 PM, Wednesday(5/12)
 * Group Project: The Shape Zone
 * fileInputOutput class does the file input and output of the project. It has several methods for 
 * writing to file(only the top 5 will be written to file), clearing the file when needed, and to 
 * parse the file and return the player list
 */


import java.util.ArrayList;
import java.util.Collections;
import java.io.*;


public class fileInputOutput 
{

	/**
	 * Method to write to the file
	 * @param name
	 * @param score
	 * @return none
	 */
	public void writetoFile(String name, int score)
	{
		//finding the file for topfive
		File textFile = new File("Scoreboard/topfive.txt");
		//retrieving data of textfile and storing into arraylist
		ArrayList<gameScore> playerList = returnScoreList();
		try {
			clearFile();//clearing the text file
			playerList.add(new gameScore(name, score));//adding the new player to the array list
			Collections.sort(playerList);//sorting all the players based on their score
		}catch(Exception e) {//catching an error if the sort is messed up
			System.out.println("Error with sorting");
		}
		//If file does not exist
		if (!textFile.exists())
		{
			//Create new file in location
			try
			{
				//making sure that the text file can read and write
				textFile.setReadable(true);
				textFile.setWritable(true);	
				textFile.createNewFile();//creating a new file if there is not one
				
			}
			
			//Catch exception in case unable to create new file
			catch (IOException e)
			{
				System.out.println("Error with file creation");
			}
			
		}
		
		try 
		{
			
			
			//FileWrite class is used to write streams of characters 
			//getAbsoluteFile method returns the absolute file object of pathname
			//absolute file points to same file/directory(where it's saved) as the given file object
			//the path should be complete from the root directories
			FileWriter writer = new FileWriter(textFile.getAbsoluteFile(), true);
			
			//write structure's data into the file(buffer)
			//BufferedWriter writes text to character output stream, buffering characters
			BufferedWriter bufferWriter = new BufferedWriter(writer);
			//write() method writes a single character to buffer of writer
			//we need to convert it to string
			//for loop that loops through all the players in the array list and inserts into the text file
			//format: "John 8\n"
			for(int i = 0; i < 5; i ++) {
				if(i<playerList.size())
					bufferWriter.write(playerList.get(i).getName()+" "+playerList.get(i).getScore()+"\n");
			}
			bufferWriter.close(); //close the file
			
		}
		
		//IOException occurs when an IO operation fails 
		catch(IOException e) //using FileWrite throws an IOException
		{
			System.out.printf("Error: %s\n",e); //to see it on the console
		}
		catch(Exception e) {
			System.out.println("Error inputting to Textfile");
		}
		
		
	}
	
	
	/**
	 * Method to clear the file
	 * @param none
	 * @return none
	 */
	public void clearFile()	
	{	
		//get the scoreboard file
		try 
		{
			File textFile = new File("Scoreboard/topfive.txt");
			
			//Replace the file's content with an empty space
			PrintWriter writer = new PrintWriter(textFile.getAbsoluteFile());
			writer.print(""); //empty space
			writer.close();
		}
		
		catch (Exception e) //file not found
		{
			//printStackTrace method handles exceptions/errors 
			//it prints the throwable with other details like class name and line number where the exception happened
			e.printStackTrace(); 
		}	
	}
	
	
	
	/**
	 * method that is used to open the topfive textfile and retrieve
	 * all information and store into arraylist that will be returned
	 * @param none
	 * @return ArrayList<gameScore>
	 */
	public ArrayList<gameScore> returnScoreList(){
		//creating a new arraylist of strings
		ArrayList<String> stringList = new ArrayList<String>();
		//creating a new arraylist of gameScore
		ArrayList<gameScore> playerList = new ArrayList<gameScore>();
		try {
			//trying to open the file for reading
			File file = new File("Scoreboard/topfive.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			//while there is still something to read
			while(reader.ready()) {
				//read the entire line and insert to string arraylist
				stringList.add(reader.readLine());
			}
			
			//loop through the string arraylist and parse to enter to arraylist
			//0123456
			//jake 10
			for(String s : stringList) {
				String name="";
				int score = 0;
				int i = 0;
				while(s.charAt(i) != ' ') {
					name+=s.charAt(i);
					i++;
				}
				score = Integer.parseInt(s.substring(i+1));
				//inserting player into gameScore arraylist
				playerList.add(new gameScore(name, score));
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		//returning the gameScore arraylist
		return playerList;
	}
}