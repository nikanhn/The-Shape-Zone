/**
 * Name: Nicholas Hong, Nikan Hojatnia
 * Course: CS170-01
 * Submission Date: 10:00 PM, Wednesday(5/12)
 * Group Project: The Shape Zone
 * This is the mainPanel class where most of the operations are performed. It extends JLayeredPane
 * to layer panels together and implements the ActionListener for event handling. There are a total 
 * of 4 types of panels each using its of layout manager. The first is the start which is called at 
 * the beginning. This will have the game name and a start button. The start will button will then start the
 * game and based on the questions ArrayList and how it is shuffled, a randomly assorted mix of questions will choose 
 * between two types of panels. One that displays an image and asks for which shape it is and display
 * four different buttons that have the shape's name for the user to choose from. The other panel
 * will ask "What shape is a...?" and display four different buttons with the image of shapes on it. 
 * The last panel, which will happen after 10 question are asked, is the score panel that displays the 
 * current users score as well as the top 5 player results. There will also be a button at the bottom 
 * for the user to retry the program. If clicked they are led back to the start panel to repeat. 
 * There is an actionPerformed method for each button. This method checks if the user started the program,
 * chose the correct answer, and wants to retry. 
 */
import java.awt.*;
import javax.swing.*;
import javafx.scene.media.AudioClip;

import java.awt.event.*;//for actionlistener
import java.io.File;
import java.util.*;

public class mainPanels extends JLayeredPane implements ActionListener{
	//used for validating name
	private Validator valid;
	//buttons for retry and start pages
	private JButton retry, start;
	//buttons for answering questions
	private JButton figure[] = new JButton[4];
	//string that holds name
	private String name;
	//ints that hold the counter(for question #) and user score
	private int counter, score;
	//arraylist of shape names
	private ArrayList <String> shapes;
	//ShapeList object for retrieving arraylist of shapes
	private ShapeList newList;
	//label of current score during gameplay
	private JLabel scoreVal;
	//tk for getting default screen sizes
	private Toolkit tk = Toolkit.getDefaultToolkit();
	//dimensions of screen
	private Dimension d = tk.getScreenSize();
	//Question object that holds values of possible questions
	private Questions q;
	//array list of questions, answers, and questio type
	private ArrayList<AnswerInfo> question;
	//random for displaying buttons in random order
	private Random rand;
	//used for good and bad sounds
	private fileInputOutput playerfile;
	//default constructor
	public mainPanels() {
		//intializing the validator
		valid = new Validator();
		counter = 0;//start the counter at 0
		rand = new Random();//initalize the random
		this.add(firstPane());//adding the start pane to the frame
	}
	
	//all event handling for the buttons
	public void actionPerformed(ActionEvent e) {
		//creating a base object source
		Object source = e.getSource();
		//removing the current panel
		this.removeAll();
		//start button was clicked
		if(source == start) {
			//ask user to enter in name and check through validator
			name = valid.nameValidator("Enter in your name:\n");
		}//if the counter is between 0 and 11 then check if  user entere in correct question
		else if(counter > 0 && counter < 11){//only for question panels, 0 is start panel and 11 is score panel
			boolean correct = false;
			//checking which button the user chose
			for(JButton b: figure) {
				if(source == b) {
					if(b.getName().equals(question.get(counter-1).answer))//if the button pressed is the answer
						correct = true;
				}
			}
			if(correct) {// if the user chose the right answer
				try {//playing the right answer audio file
					File file = new File("sound/good.wav");//finding the file
					AudioClip audio = new AudioClip(file.toURI().toString());//creating the audioclip
					audio.play();//playing the audio
				}
				catch(Exception ex) {//catching the error if audio does not work
					System.out.println("Error with good.wav");
				}
				JOptionPane.showMessageDialog(null, "You got it! :>");//display that the user got the answer right
				score++;
			}
			else {//if the user chose the wrong answer
				try {//playing the wrong answer audio file
					File file = new File("sound/bad.wav");//finding the file
					AudioClip audio = new AudioClip(file.toURI().toString());//creating the audioclip
					audio.play();//playing the audio
				}
				catch(Exception ex) {//catching the error if audio does not work
					System.out.println("Error with bad.wav");
				}//display that the answer chosen was wrong
				JOptionPane.showMessageDialog(null, "Wrong Answer :<");
			}
		}
		//if the user is at the end and clicks on retry button
		if(source == retry) {
			this.add(firstPane());//go back to the first pane
			counter = -1;//setting the counter to -1 
		}
		else if(counter == 10) {//at 10 the player reached the score pane
			this.add(scorePane());
		}//other wise code checks which type of question to display
		else if(question.get(counter).qtype == 1) {
			this.add(changePaneType1());
		}
		else if(question.get(counter).qtype == 2) {
			this.add(changePaneType2());
		}//adding 1 to the counter
		counter++;

		//repainting before switching
		this.revalidate();
		this.repaint();

	}
	//function that produces the first panel with start button
	private JPanel firstPane() {
		score = 0;//resets the score
		q = new Questions();//creates a new list of questions
		question = q.shuffleArray();//shuffles the questions
		//creating the panel and using gridbaglayout
		JPanel first = new JPanel(new GridBagLayout());
		//creating constraints for gridbag
		GridBagConstraints c = new GridBagConstraints();
		//setting the size of the panel and making it visible
		first.setBounds(0,0, d.width/2+100,d.height/2-30);
		first.setOpaque(true);
		first.setVisible(true);
		//setting background color
		first.setBackground(new Color(255,212,212));
		//backing label to display game name
		JLabel gameName = new JLabel("The Shape Zone");
		//changing font
		gameName.setFont(new Font("Verdana", Font.BOLD, 50));
		//placing specific gridbag layout and adding to panel
		c.gridwidth = 1;
		c.weighty = 5;
		c.gridx = 0;
		c.gridy = 1;
		first.add(gameName,c);
		
		//creating start button with specific formatting
		start = new JButton("Start");
		start.setFont(new Font("Verdana", Font.PLAIN , 20));
		start.setPreferredSize(new Dimension(100,75));
		start.setBackground(new Color(255,190,133));
		start.setBorder(BorderFactory.createLineBorder(Color.RED));
		//adding action to button
		start.addActionListener(this);
		//adding button to panel with constraints 
		c.gridwidth = 1;
	    c.gridx = 0;
	    c.gridy = 2;
		first.add(start,c);
		
		//initizliating the list of shapes
		newList = new ShapeList();
		
		return first;//returning the first pane to add to the frame
	}
	
	//panel of buttons with shape name on them
	private JPanel changePaneType1() {
		//creating new panel with gridbaglayout
		JPanel pane = new JPanel(new GridBagLayout());
		//creating gridbag contraints
		GridBagConstraints c = new GridBagConstraints();
		
		//creating the label that holds the user's current score
		scoreVal = new JLabel("Score: "+Integer.toString(score));
		scoreVal.setFont(new Font("Verdana", Font.PLAIN , 20));
		
		//panel size boundaries and set visible
		pane.setBounds(0,0, d.width/2+100,d.height/2-30);
		pane.setOpaque(true);
		pane.setVisible(true);
		pane.setBackground(new Color(255,146,128));
		//displaying name at the top with the score
		//constraints for th ename and then add to panel
		JLabel displayName = new JLabel("Name: " + name);
		displayName.setFont(new Font("Verdana", Font.PLAIN , 20));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth= 0;
		pane.add(displayName, c);
		//contraints of the score label
		c.gridx = 0;
		c.gridy = 1;
		//add to the panel
		pane.add(scoreVal,c);
		
		//adding the question with set constraints
		JLabel questLabel = new JLabel(question.get(counter).question);
		questLabel.setFont(new Font("Verdana", Font.BOLD, 50));
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 0;
		//adding to the panel
		pane.add(questLabel,c);
		
		//adding the image of the shape using a jlabel and image icon
		JLabel shapeImage = new JLabel();
		shapeImage.setIcon(new ImageIcon(getClass().getResource(question.get(counter).answer+".png")));
		//setting the grid constraints
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 0;
		c.weighty = 0.2;
		//adding to panel
		pane.add(shapeImage,c);
		
		//setting the constraints for each button with answers
		c.gridwidth = 1;
		c.gridy = 4;
		c.weightx = 1.5;
		//shuffling the list of shapes to display random shapes
		shapes = newList.shuffleArrayList();
		int randQ = rand.nextInt(4);//geting a random value from 0 to 3 for the right answer button
		for(int i = 0; i < 4; i++) {
			c.gridx = i;
			if(i == randQ) {//a number from 0-3
				//setting the text to the answer
				figure[i]= new JButton(question.get(counter).answer);
				//setting the name of the button
				figure[i].setName(question.get(counter).answer);
			}//checks if the current shape in index is equal to the answer
			else if(shapes.get(i).equals(question.get(counter).answer)) {
				figure[i]= new JButton(shapes.get(9));//getting last value in array list instead
				figure[i].setName(shapes.get(9));
			}//otherwise just insert some shape into the button
			else {
				figure[i]= new JButton(shapes.get(i));
				figure[i].setName(shapes.get(i));
			}
			//add action for each button
			figure[i].addActionListener(this);
			//adding some modifications to the button
			figure[i].setFont(new Font("Verdana", Font.PLAIN , 20));
			figure[i].setPreferredSize(new Dimension(200,75));
			figure[i].setBackground(new Color(255,190,133));
			figure[i].setBorder(BorderFactory.createLineBorder(Color.BLUE));
			//adding to the panel
			pane.add(figure[i],c);
		}
		return pane;//return the pane to add to the frame
	}
	//panel of buttons with images on them
	private JPanel changePaneType2() {
		//creating Jpanel with gridbaglayout
		JPanel pane = new JPanel(new GridBagLayout());
		//creating gridbag constraints
		GridBagConstraints c = new GridBagConstraints();
		
		//creating the label that holds the user's current score
		scoreVal = new JLabel("Score: "+ Integer.toString(score));
		scoreVal.setFont(new Font("Verdana", Font.PLAIN , 20));
		
		//setting the panel boundaries and making it visible
		pane.setBounds(0,0, d.width/2+100,d.height/2-30);
		pane.setOpaque(true);
		pane.setVisible(true);
		//setting the background color
		pane.setBackground(new Color(162,255,144));
		//displaying name at the top with the score
		//constraints for th ename and then add to panel
		JLabel displayName = new JLabel("Name: " + name);
		displayName.setFont(new Font("Verdana", Font.PLAIN , 20));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth= 0;
		pane.add(displayName, c);
		//constraints for the score to be at the top
		c.gridx = 0;
		c.gridwidth = 0;
		c.gridy = 1;
		pane.add(scoreVal,c);
		
		//creating label for the question
		JLabel questLabel = new JLabel(question.get(counter).question);
		questLabel.setFont(new Font("Verdana", Font.BOLD, 50));
		//constraints for quetsion and adding to the panel
		c.weighty = 0.2;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 0;
		pane.add(questLabel,c);
		
		//constraints for the button answers
		c.gridwidth = 1;
		c.gridy = 3;
		c.weightx = 1.5;
		//shuffling the array list
		shapes = newList.shuffleArrayList();
		int randQ = rand.nextInt(4);//getting random values from 0-3
		for(int i = 0; i < 4; i++) {//loop for 4 buttons
			c.gridx = i;
			if(i == randQ) {//create button of correct answer at random index
				figure[i]= new JButton(question.get(counter).answer);
				//setting the immage
				figure[i].setIcon(new ImageIcon(getClass().getResource(question.get(counter).answer+".png")));
				figure[i].setName(question.get(counter).answer);//setting the value of the button
			}
			else if(shapes.get(i).equals(question.get(counter).answer)) {//if the shape in the array matches the answer
				figure[i]= new JButton(shapes.get(9));//getting last value in array list instead
				figure[i].setIcon(new ImageIcon(getClass().getResource(shapes.get(9)+".png")));
				figure[i].setName(shapes.get(9));
			}
			else {//otherwise just set the button to the value in the shapelist array
				figure[i]= new JButton(shapes.get(i));
				figure[i].setIcon(new ImageIcon(getClass().getResource(shapes.get(i)+".png")));
				figure[i].setName(shapes.get(i));
			}
			figure[i].setText("");//set the text to blank to the buttons don't display anything besides the img
			figure[i].setPreferredSize(new Dimension(200,200));
			figure[i].addActionListener(this);//adding its action element
			pane.add(figure[i],c);//add to the panel
		}
		return pane;//return to add the panel to the frame
	}
	//panel that is used to display the top 5 players and a retry button
	private JPanel scorePane() {
		//creating a new pane that uses gridlayout dimensions (0,3) so the rows numbers can be undefined
		JPanel endpane = new JPanel(new GridLayout(0,3));
		//setting the panel size and making it visible
		endpane.setBounds(0,0, d.width/2+100,d.height/2-30);
		endpane.setOpaque(true);
		endpane.setVisible(true);
		endpane.setBackground(new Color(144,201,255));//panel color
		
		//instantiating the fileInputOuput object
		playerfile = new fileInputOutput();
		playerfile.writetoFile(name, score);//writing to file the current user data
		ArrayList<gameScore> playerresult = playerfile.returnScoreList();//returns the scorelist of the top 5 players
		//adding a blank jlabel to fill the grid
		endpane.add(new JLabel());
		//adding the name title to the top
		JLabel nameTitle = new JLabel("Name");
		nameTitle.setFont(new Font("Verdana", Font.BOLD, 50));
		endpane.add(nameTitle);
		//addint the score title to the top
		JLabel scoreTitle = new JLabel("Score");
		scoreTitle.setFont(new Font("Verdana", Font.BOLD, 50));
		endpane.add(scoreTitle);
		
		//adding the user's results to the panel
		JLabel results = new JLabel("Your Results:");
		results.setFont(new Font("Verdana", Font.PLAIN, 25));
		endpane.add(results);
		JLabel yourName = new JLabel(name);
		yourName.setFont(new Font("Verdana", Font.PLAIN, 25));
		endpane.add(yourName);//adding users name
		JLabel yourScore = new JLabel(Integer.toString(score));
		yourScore.setFont(new Font("Verdana", Font.PLAIN, 25));
		endpane.add(yourScore);//adding users score
		
		//adding top 5 lable to the under the current user's score
		JLabel topresults = new JLabel("Top 5: \n");
		topresults.setFont(new Font("Verdana", Font.PLAIN, 25));
		endpane.add(topresults);
		endpane.add(new JLabel());endpane.add(new JLabel());//adding blanks to fill in grid
		
		//printing out the top 5 player's scores
		for(int i =1; i < 6; i++) {
			//printing the number ranking
			JLabel num = new JLabel(i+".");
			num.setFont(new Font("Verdana", Font.PLAIN, 25));
			endpane.add(num);
			//adding new label for the user's name
			JLabel people = new JLabel();
			people.setFont(new Font("Verdana", Font.PLAIN, 25));
			//new label for the user's score
			JLabel peoplescore = new JLabel();
			peoplescore.setFont(new Font("Verdana", Font.PLAIN, 25));
			if(i <= playerresult.size()) {//if the number of players is less than 5 in text file
				people.setText(playerresult.get(i-1).getName());//adding the name to the panel
				endpane.add(people);
				peoplescore.setText(Integer.toString(playerresult.get(i-1).getScore()));//adding the score to the panel
				endpane.add(peoplescore);
			}
			else {//if less then 5 add "n/a" to result list
				people.setText("n/a");
				endpane.add(people);
				endpane.add(new JLabel());
			}
		}
		endpane.add(new JLabel());//adding blank to fill in grid
		retry = new JButton("Retry");//adding retry button that leads back to start panel
		retry.addActionListener(this);
		retry.setFont(new Font("Verdana", Font.PLAIN, 25));
		retry.setBackground(new Color(192,255,188));
		retry.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		endpane.add(retry);
		
		return endpane;//returning the endpanel 
		
	}
	
}
