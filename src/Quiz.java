import java.io.File;
import java.io.Serializable;
import java.util.*;

public class Quiz implements Serializable{
	private String filename;
	ArrayList<Question> questions = new ArrayList<>();
	private transient Scanner std;
	private int questionIndex = 0;
	private String selected = "E";
	public final static int RIGHTANSWERCREDIT = 20;
	
	//Constructor
	public Quiz(String filename) {
		this.filename = filename;
	}
	
	/**
	 * opens file w/ Scanner
	 */
	public void openFile() {
		try {
			std = new Scanner(new File(filename));
			
		}catch(Exception e) {
			System.out.println("could not find file");
		}
	}
	
	/**
	 * reads file into question and answer variables
	 */
	public void readFile() {
		while(std.hasNext()) {
			String question = std.nextLine();
			
			String[] ans = {std.nextLine(), std.nextLine(), std.nextLine(), std.nextLine()};

			String corr = std.nextLine();
			questions.add(new Question(question, ans, corr));
		}
	}
	
	/**
	 * closes file
	 */
	public void closeFile() {
		std.close();
	}
	
	//Getters & Setters
	
	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public int getQuestionIndex() {
		return questionIndex;
	}

	public void setQuestionIndex(int questionIndex) {
		this.questionIndex = questionIndex;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}	
}
