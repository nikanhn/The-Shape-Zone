/**
 * Name: Nicholas Hong, Nikan Hojatnia
 * Course: CS170-01
 * Submission Date: 10:00 PM, Wednesday(5/12)
 * Group Project: The Shape Zone
 * ShapeList class that adds in all shapes to an ArrayList of type String. There
 * are two functions. The first shuffles the array to produce a randomly assorted 
 * mix of the shapes. The second returns the array in its current state. 
 */
import java.util.*;

public class ShapeList {
	//creating an arrayList of strings to hold all shapes
	private ArrayList<String> shapeList;
	public ShapeList() {
		//allocating memory to object
		shapeList = new ArrayList<String>();
		//adding all 10 shapes to list
		shapeList.add("Square");
		shapeList.add("Triangle");
		shapeList.add("Rectangle");
		shapeList.add("Star");
		shapeList.add("Heart");
		shapeList.add("Circle");
		shapeList.add("Hexagon");
		shapeList.add("Pentagon");
		shapeList.add("Semi-Circle");
		shapeList.add("Trapezoid");
	}
	//shuffling and retruning the arraylist
	public ArrayList<String> shuffleArrayList() {
		Collections.shuffle(shapeList);
		return shapeList;
	}
	//retrieving list in current state
	public ArrayList<String> getArray(){
		return shapeList;
	}
}
