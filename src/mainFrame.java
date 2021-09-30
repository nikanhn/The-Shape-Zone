/**
 * Name: Nicholas Hong, Nikan Hojatnia
 * Course: CS170-01
 * Submission Date: 10:00 PM, Wednesday(5/12)
 * Group Project: The Shape Zone
 * mainFrame class inherits from JFrame and uses the ActionListener interface. 
 * The default constructor will establish the frame size based on monitor size. It play a background audio. 
 * It will then   user a container that has border layout to separate the menu panel and the jlayered panel. 
 * The menu will consist of three buttons: help, top 5, and exit. When clicked the actionPerformed
 * method is called and 'help' will display some text, 'top5' will open the textfile and printout
 * the top 5 players on a JOptionPane, and the 'exit' will exit the program. 
 */
import java.awt.*;
import java.awt.event.*;//for actionlistener
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.*;
import java.io.File;
import javafx.scene.media.AudioClip;
//mainfFrame class that inherits JFrame and implements ActionListener
public class mainFrame extends JFrame implements ActionListener{
	//buttons for the top menu
	private JButton help, top5, exit;
	//establishing the jframe to have it open in the center of the screen
	public mainFrame() {
		//trying to run the background track
		try {
			File file = new File("sound/song.wav");//finding the file
			AudioClip audio = new AudioClip(file.toURI().toString());//creating the audioclip
			audio.setCycleCount(audio.INDEFINITE);//having the song loop
			audio.play();//playing the audio
		}catch(Exception e) {//if there is an error print out to the terminal
			System.out.print("Audio Error");
		}
		try {
			//setting the title of the game
			setTitle("TheShapeZone");
			//setting the dimensions of the jframe
			setSize(widthDimension(),heightDimension());
			centerWindow(this);//when the application runs it sets it in the center of the screen
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//making sure the X button closes properly
			
			//instantiating the buttons for the menu
			help = new JButton("Help");
			top5 = new JButton("Top 5");
			exit = new JButton("Exit");
			//adding the respecive actions fo the menu
			help.addActionListener(this);
			top5.addActionListener(this);
			exit.addActionListener(this);
			
			//creating the menu label and doing custom setup
			JLabel menuLabel = new JLabel("Menu:");
			menuLabel.setFont(new Font("Verdana", Font.BOLD, 30));
			
			//The entire main frame pane uses a border layout container to hold all panels
			Container contain = this.getContentPane();
			contain.setLayout(new BorderLayout(4,4));
			contain.setBackground(Color.BLACK);
			
			//creating the menu panel
			JPanel menu = new JPanel();
			//uses GridLayout with 1 row 4 cols
			menu.setLayout(new GridLayout(1,4,100,0));
			//customizing and adding elements to panel
			menu.setBackground(new Color(196,179,255));
			menu.add(menuLabel);
			menu.add(help);
			menu.add(top5);
			menu.add(exit);
			//setting this menu to the north of the panel
			contain.add(menu, BorderLayout.NORTH);
			//the remaining space is for the layeredpane of questions
			JLayeredPane panel = new mainPanels();
			contain.add(panel, BorderLayout.CENTER);
		}
		catch(Exception e) {//catching error if frame was not made properly
			System.out.println("Error creating frame");
		}
		
	}
	
	//event handling function used for menu
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == exit) {//exit button that exits program
			System.exit(0);
		}//top five that displays the current top 5 players
		else if(source == top5) {
			String topfivePlayers="";
			//creating a new fileInputOuput object to retrieve data from text file
			fileInputOutput output = new fileInputOutput();
			//store data in array
			ArrayList<gameScore> playerList = output.returnScoreList();
			//print out players 
			for(int i = 1; i < 6; i++) {
				if((i-1)<playerList.size()) {
					topfivePlayers+= i+". "+playerList.get(i-1).getName()+" "
								+playerList.get(i-1).getScore() + "\n";
				}
				else {//if less than 5 players in array then fill rest with "n/a"
					topfivePlayers +=i+". " + "n/a\n";
				}
			}//print out the top string of players
			JOptionPane.showMessageDialog(null,"Top 5:\n"+topfivePlayers);
		}
		else if(source == help) {//displaying what the players should expect
			JOptionPane.showMessageDialog(null,"Welcome to The Shape Zone\n Try to guess the shapes!!!:)");
		}
	}
	//method that centers the panel on the screen
	private void centerWindow(Window w) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		setLocation((d.width-w.getWidth())/2, (d.height-w.getHeight())/2);
	}
	//function to get half the screen width + 100 px
	private int widthDimension() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		return d.width/2+100;
	}
	//function to get half the screen height + 50 px
	private int heightDimension() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		return d.height/2+50;
	}
}
