/**
 * Name: Nicholas Hong, Nikan Hojatnia
 * Course: CS170-01
 * Submission Date: 10:00 PM, Wednesday(5/12)
 * Group Project: The Shape Zone
 *This is the Questions class that holds all questions that will be shown 
 *in the game. There are two types of question: one asking "What shape is this?"
 *and the other asking "What shape is a...?" where the "..." is a randomly picked shape.
 *There are 5 of each type of question. The stores all questions in an ArrayList of type AnswerInfo.
 *There is a shuffleArray function that is to produce the question in a random order. 
 */
import java.util.*;

import java.util.Collections;
public class Questions {
	//arraylist of AnswerInfo type to hold questions, answers, and question type
	private ArrayList<AnswerInfo> quest;
	//default constructor that initializes the questions and creates the list of questions
	public Questions(){
		quest = new ArrayList<AnswerInfo>();
		createQuestionList();
	}
	//function that is used to add 5 of each question type to the list
	public void createQuestionList() {
		//creating a new ShapeList object that holds all shapes
		ShapeList s = new ShapeList();
		//shapelist will be shuffled to have random order
		ArrayList<String> shapes = s.shuffleArrayList();
		//different types of questions
		String firstQ = "What shape is this?";
		String secondQ = "What shape is a ";
		//for loop that loops for 10 questions
		for(int i = 0; i < 10; i++) {
			AnswerInfo temp = new AnswerInfo();
			temp.answer = shapes.get(i);//entering the answer to questions
			//entering question and question type
			if(i<5) {
				temp.question = firstQ;
				temp.qtype=1;
			}
			else {
				temp.question = secondQ+shapes.get(i)+"?";
				temp.qtype=2;
			}
			quest.add(temp);//adding the AnswerInfo type to the arraylist
		}
	}
	//method used to shuffle the arraylist of AnswerInfo
	public ArrayList<AnswerInfo> shuffleArray(){
		Collections.shuffle(quest);
		return quest;
	}
}
