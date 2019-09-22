import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class PersonalityTestSurvey {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(System.in);
		//First deliver the test
		deliverTest(console);
		//Then you need this information to analyze the results
		System.out.println("The input file here is the output file you initially made.");
		System.out.println("Please type it exactly as you did before.");
		System.out.println("Also, please use a new name for this output file");
		System.out.println("Do NOT use the name of a file that already exists." );
		//This is where we actually analyze the test results
		File results = PersonalityTest.analyzeFileContents(console);
		Scanner fileReader = new Scanner(results);
		//Print the results to the console
		while(fileReader.hasNextLine()){
			System.out.println(fileReader.nextLine());
		}
		fileReader.close();
		console.close();
	}

	public static void deliverTest(Scanner console) throws FileNotFoundException {
		// Get an input file & an output file name
		System.out.print("Please provide a .txt file with the multiple choice questions");
		Scanner fileReader = new Scanner(checkFileValidity(console));
		System.out.print("Output file name: ");
		PrintStream fileToWrite = new PrintStream(new File(console.nextLine()));
		//Having a user's name was part of the format requirements for the output file
		System.out.print("What is your name? ");
		String name = console.nextLine();
		fileToWrite.println(name);
		System.out.println("Please answer the following multiple choice questions.");
		System.out.println("If the answer is neither, please type a '-' character.");
		// Line-based processing of the input file
		while (fileReader.hasNextLine()){
			for (int i = 1; i <= 4; i++) {
				//This corresponds to the structure of the survey.txt file provided
				//Each question had the question number as a header, then the question, and to answer options
				System.out.println(fileReader.nextLine());
			}
			String answer;
			do {
				//Get the user's response
				answer = console.next();
				//Check if it's a valid answer - prompt the user for a new response if it's not
				if (!answer.equalsIgnoreCase("A") && !answer.equalsIgnoreCase("B") && !answer.equalsIgnoreCase("-")) {
					System.out.println("That is not a valid answer. Please try again: ");
				}
			} while (!answer.equalsIgnoreCase("A") && !answer.equalsIgnoreCase("B") && !answer.equalsIgnoreCase("-"));
			//Record the answer
			fileToWrite.print(answer);
			//Consume the empty line that separates questions
			if(fileReader.hasNextLine()){
				fileReader.nextLine();
			}
		} 
		fileReader.close();
	}

	// This method asks for an input file name and checks if it can be used.
	// It returns the file
	public static File checkFileValidity(Scanner console) {
		File userFile;
		//Prompt for a file name
		System.out.print("Input file name: ");
		do {
			userFile = new File(console.nextLine());
			//Check that the file is useable; if it's not, prompt for a new file name
			if (!userFile.exists() || !userFile.canRead()) {
				System.out.print("File not found. Please try again: ");
			}
		} while (!userFile.exists() || !userFile.canRead());
		return userFile;
	}
}