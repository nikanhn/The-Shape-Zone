/**
 * Name: Nicholas Hong, Nikan Hojatnia
 * Course: CS170-01
 * Submission Date: 10:00 PM, Wednesday(5/12)
 * Group Project: The Shape Zone
 * Validator class that does the exception handling for name of the player.
 * Uses regex to make sure that the user does not enter in whitespaces
 * 
 */
import javax.swing.JOptionPane;
public class Validator {
	//method used to validate the user's name at the beginning
	public String nameValidator(String prompt) {
		boolean valid = false;
		String name = "";
		while (!valid) {//run until the user enters in a valid name
			try {//asking the user for the name
				name = JOptionPane.showInputDialog("What is your name?");
				//throwing and exception if the user didn't enter anything
				if(!name.matches("[A-Za-z1-9_./!@#$%^&*()_+=-?]+"))
					throw new Exception();

				valid = true;
			}//catching the exception if the user did not enter in valid name
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Please enter your name. No spaces.");
			}
			
		}
		//returning the name at the end
		return name;
	}
	
}
