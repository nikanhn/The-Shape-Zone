/**
 * Name: Nicholas Hong, Nikan Hojatnia
 * Course: CS170-01
 * Submission Date: 10:00 PM, Wednesday(5/12)
 * Group Project: The Shape Zone
 * AnswerInfo class holds public information such as answer and question. It only has a default constructor
 * that sets qtype variable to zero
 */
public class AnswerInfo {
	//holds the answer
	public String answer;
	//holds the question
	public String question;
	//two types of question types; what shape is this? ; what shape is a ...?
	public int qtype;
	//default constructor to initialize int value
	public AnswerInfo(){
		qtype = 0;
	}
}
