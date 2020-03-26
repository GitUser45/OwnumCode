package wordEditing;

import java.util.Comparator;


// Creates a Word class for record keeping and counting purposes in main program.
public class Word implements Comparable<Word>{
	private String wordValue;
	private Integer wordCount;


	public Word(String wordValue, Integer wordCount) {
		this.wordValue = wordValue; 
		this.wordCount = wordCount;

	}
	public String getWord() {
		return this.wordValue;
	}

	public void setWord(String word) {
		this.wordValue = word;
	}
	public Integer getCount() {
		return this.wordCount;
	}
	public void setCount(int count) {
		this.wordCount = count;
	}

	// Returns a more neatly formatted representation of the word and how many times it appears.
	@Override
	public String toString() {
		return "Word: " + this.wordValue +  " numberOfTimesSeen " + this.wordCount;
	}

	// Creates a comparator for use in sorting.
	public static Comparator<Word> WORDCOUNT = new Comparator<Word>() {
		@Override
		public int compare(Word o1, Word o2) {
			return o1.wordCount.compareTo(o2.wordCount);
		}
	};

	// Compares words based on how many times they appear, 
	// with higher values sorted to the to and lower
	// values sorted to the bottom. 
	public int compareTo(Word word) {
		if(this.wordCount > word.wordCount) {
			return -1;
		}
		else if(this.wordCount < word.wordCount) {
			return 1;
		}
		else {
			return 0;
		}
	}


}


