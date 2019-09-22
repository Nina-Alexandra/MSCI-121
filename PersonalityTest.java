/*Nina DeSouza – 20666255
 * March 20 - 25, 2017
 * Assignment 8, Problem 3
 * This file analyzes data from a personality test file
 * Input: Name of file to read, and a name for the output file
 * Output: A file summarizing the personality test data from the file
 * Note that the method analyzeFileContents returns a file so that the program
 * PersonalityTestSurvey (which allows you to take the test and then immediately
 * analyzes the results) can print that file to the console */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class PersonalityTest {
	public static final int SIZE = 4;

	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(System.in);
		analyzeFileContents(console);
		console.close();
	}

	// This method takes an input file, analyzes the contents,
	// and writes a summary to an output file
	public static File analyzeFileContents(Scanner console) throws FileNotFoundException {
		// Get an input file & an output file name
		Scanner fileReader = new Scanner(checkFileValidity(console));
		System.out.print("Output file name: ");
		File outputFile = new File(console.nextLine());
		PrintStream fileToWrite = new PrintStream(outputFile);
		// Line-based processing of the input file
		while (fileReader.hasNextLine()) {
			// Collect the data – name, number of A's & B's per dimension,
			// percent which are B's, and personality type
			String name = fileReader.nextLine();
			String answers = fileReader.nextLine();
			double[] aCounts = countALetter(answers, 'a');
			double[] bCounts = countALetter(answers, 'b');
			int[] bPercents = computePercent(aCounts, bCounts);
			String personalityType = convertToType(bPercents);
			// Print the data to the output file
			fileToWrite.println(name + " :");
			// Combine and format the A & B counts
			String[] countedPairs = new String[SIZE];
			for (int i = 0; i < SIZE; i++) {
				countedPairs[i] = (int) aCounts[i] + "A-" + (int) bCounts[i] + "B";
				fileToWrite.print(countedPairs[i] + " ");
			}
			// Format the percent of B's
			fileToWrite.println();
			String[] bPercentsToPrint = new String[SIZE];
			for (int i = 0; i < SIZE; i++) {
				bPercentsToPrint[i] = bPercents[i] + "%";
			}
			fileToWrite.print(Arrays.toString(bPercentsToPrint));
			fileToWrite.println(" = " + personalityType);
			fileToWrite.println();
		}
		fileReader.close();
		fileToWrite.close();
		return outputFile;
	}

	// This method asks for an input file name and checks if it can be used.
	// It returns the file
	public static File checkFileValidity(Scanner console) {
		File userFile;
		System.out.print("Input file name: ");
		do {
			userFile = new File(console.nextLine());
			if (!userFile.exists() || !userFile.canRead()) {
				System.out.print("File not found. Please try again: ");
			}
		} while (!userFile.exists() || !userFile.canRead());
		return userFile;
	}

	// This method tallies the occurence of a given letter according to the
	// category to
	// which it belongs, which is determined based on its position in the string
	public static double[] countALetter(String stringOfLetters, char letterToCount) {
		double[] countsofLetter = new double[SIZE];
		stringOfLetters = stringOfLetters.toLowerCase();
		int dimension;
		for (int i = 0; i < stringOfLetters.length(); i++) {
			// Figure out to which dimension the answer belongs
			int dimensionDeterminingNumber = i % 7;
			if (dimensionDeterminingNumber == 0) {
				dimension = 0;
			} else if (dimensionDeterminingNumber == 1 || dimensionDeterminingNumber == 2) {
				dimension = 1;
			} else if (dimensionDeterminingNumber == 3 || dimensionDeterminingNumber == 4) {
				dimension = 2;
			} else {
				dimension = 3;
			}
			// Check if the answer is the one we're looking for
			// Update the array if it is
			if (stringOfLetters.charAt(i) == letterToCount) {
				countsofLetter[dimension]++;
			}
		}
		return countsofLetter;
	}

	// This method computes the percentage of b answers for each dimension
	public static int[] computePercent(double[] aCount, double[] bCount) {
		int[] bPercents = new int[SIZE];
		for (int i = 0; i < SIZE; i++) {
			bPercents[i] = (int) (bCount[i] / (aCount[i] + bCount[i]) * 100);
		}
		return bPercents;
	}

	// This method takes the percentages of B answers
	// and converts them to their corresponding letters
	public static String convertToType(int[] percentB) {
		String[] aLetters = { "E", "S", "T", "J" };
		String[] bLetters = { "I", "N", "F", "P" };
		String personalityType = "";
		for (int i = 0; i < SIZE; i++) {
			if (percentB[i] > 50) {
				personalityType += bLetters[i];
			} else if (percentB[i] < 50) {
				personalityType += aLetters[i];
			} else {
				personalityType += "X";
			}
		}
		return personalityType;
	}
}
