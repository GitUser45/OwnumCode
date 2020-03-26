package wordEditing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
public class wordStatistics {

	// Reads and computes the total word count,
	// displays the top 10 words used and display them in sorted order,
	// and pulls the last sentence where the most used word is used. 

	// Code assumes that the desired reading file has been inserted
	// into the base directory of the project. This reduces code 
	// that would be needed to deal with absolute file pathing
	// and would merely create more opportunities for failed
	// application runs and sources of error when debugging. 
	public static <T> void main(String[] args) {
		int wordCount = countWords(new String("passage.txt"));
		HashMap<String, Word> checkHash = getWordCollection(new String("passage.txt"));
		String highestWord = getTopN(checkHash, 10);
		System.out.println(getLastTargetLine(new String("passage.txt"), highestWord));
	}



	// Given a String representing the file path of a given text file,
	// iterate over the text file, count how many words are in the text
	// file, and print the result for the user. 
	public static int countWords(String targetFile) {
		try {	
			File baseFile = new File(targetFile);
			Scanner fileReader = new Scanner(baseFile);
			int numberOfWords = 0; 
			while (fileReader.hasNextLine()) {
				String currentLine = fileReader.nextLine();
				// The currentLine is split by spaces in between words in a line,
				// allowing them to then be counted by getting the length of the resulting 
				// String array. 
				if(!currentLine.equals("")) {
					String[] collectWords = currentLine.split("\\s+");
					numberOfWords += collectWords.length;
				}
			}
			System.out.println("The number of words in the given document is " + numberOfWords + ".");
			return numberOfWords;
		}
		// Handles the case when the requested file is not found and provides error logging. 
		catch (FileNotFoundException noFileException) {
			System.out.println("There was an issue locating the desired reading file. "
					+ "Please look at the error messages and try again.");
			noFileException.printStackTrace();
		}
		return 0;
	}

	// Given a String representing a file name in the base directory,
	// replace punctuation, place the line into a container, and add
	// values to a HashMap to adequately store and organize the words
	// and their frequency. 
	// Hyphenated words(ex. ascending-bid) are assumed to be treated as one word.
	public static HashMap<String, Word> getWordCollection(String targetFile) {
		HashMap <String, Word> wordMap = new HashMap<String, Word>();
		try {	
			File baseFile = new File(targetFile);
			Scanner fileReader = new Scanner(baseFile);
			int numberOfWords = 0; 
			while (fileReader.hasNextLine()) {
				String currentLine = fileReader.nextLine();
				// The currentLine replaces all punctuation with regular expressions and 
				// splits on spaces in between words in a line,
				// allowing them to then be counted by getting the length of the resulting 
				// String array. 
				if(!currentLine.equals("")) {
					currentLine = currentLine.replaceAll("\\p{Punct}", "");
					String[] collectWords = currentLine.split("\\s+");

					//	Places all words to lower-case for proper counting purposes.
					for(int j = 0; j < collectWords.length; j++) {
						if(!collectWords[j].contentEquals(collectWords[j].toLowerCase())) {
						collectWords[j] = collectWords[j].toLowerCase();
					}
					}

					// Inserts the new key-value pair into the HashMap if it exists
					// and increments the Word count for that word if it doesn't.
					for(int i = 0; i < collectWords.length; i++) {
						if(!wordMap.containsKey(collectWords[i])) {
							wordMap.put(collectWords[i], new Word(collectWords[i], 1));
						}
						else {
							wordMap.get(collectWords[i]).setCount(wordMap.get(collectWords[i]).getCount()+1);
						}

					}
				}
			}
			return wordMap;
		}
		// Handles the case when the requested file is not found and provides error logging. 
		catch (FileNotFoundException noFileException) {
			System.out.println("There was an issue locating the desired reading file. "
					+ "Please look at the error messages and try again.");
			noFileException.printStackTrace();
		}
		return null;
	}



	// Prints the top N argument specified number of Word values from a HashMap.
	// Returns the most frequently used word for determining the last line
	// it was used in. 
	public static String getTopN(HashMap<String, Word> checkMap, int N) {
		List<Map.Entry<String, Word>> list = 
				new LinkedList<Map.Entry<String, Word>>(checkMap.entrySet());	
		Collections.sort(list, wordStatistics.WORDCOUNT);
		for(int i = 0; i < N; i++) {
			System.out.println(list.get(i).getValue());
		}
		return list.get(0).getValue().getWord();
	}

	// Allows for the HashMap to be sorted based on the Word values. 
	public static Comparator<Map.Entry<String, Word>> WORDCOUNT = new Comparator<Map.Entry<String, Word>>() {
		@Override
		public int compare(Map.Entry<String, Word> o1, Map.Entry<String, Word> o2){
			return o1.getValue().compareTo(o2.getValue()); // for example
		}
	};


	//	Retrieves the last line where the most used word is used. 
	public static String getLastTargetLine(String targetFile, String mostUsedWord) {
		String lastLine = "";
		try {	
			File baseFile = new File(targetFile);
			Scanner fileReader = new Scanner(baseFile);
			int numberOfWords = 0; 
			while (fileReader.hasNextLine()) {
				String currentLine = fileReader.nextLine();
				// If the line contains the mostUsedWord, split on 
				// the period. If one of the split sentences contains
				// the word, it is set to the lastLine with a period added, 
				// as the period was removed by the split.
				if(currentLine.contains(mostUsedWord)) {
					String[] collectWords = currentLine.split("\\.");
					for(int i = 0; i < collectWords.length; i++) {
						if(collectWords[i].contains(mostUsedWord)) {
							lastLine = collectWords[i] + ".";
						}
					}

				}
			}
			return lastLine;
		}
		// Handles the case when the requested file is not found and provides error logging. 
		catch (FileNotFoundException noFileException) {
			System.out.println("There was an issue locating the desired reading file. "
					+ "Please look at the error messages and try again.");
			noFileException.printStackTrace();
		}
		return null;
	}


}




