import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;


public class WordLadder
{
	private static int MAX_DEPTH = 10;
	
	private static Set<String> dictionary = new HashSet<>();
	
	private static char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

	public static void main(String[] args) throws IOException
	{
		Queue<Word> bfs = new ArrayDeque<>();
		
		if (args.length != 2)
		{
			System.out.println("\nUsage: WordLadder <start_word> <end_word>\n");
			System.exit(0);
		}
		
		Word startWord = new Word(args[0].toLowerCase(), 0, null);
		Word endWord = new Word(args[1].toLowerCase(), 0, null);
		if (startWord.length() != endWord.length())
		{
			System.out.println("\nRules: Start word length must equal end word length.\n");
			System.exit(0);
		}
		
		if (startWord.equals(endWord)) System.out.println("\nDone! 0 Steps: " + startWord + " -->" + endWord + "\n");
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("./corpus.txt"), "UTF-8"))) {
		    String word;
		    while ((word = br.readLine()) != null) {
		       dictionary.add(word);
		    }
		}
		dictionary.remove(startWord.word);
		
		bfs.add(startWord);
		
		Word next;
		while (!bfs.isEmpty() && null != (next = bfs.remove()))
		{
			if (next.depth > MAX_DEPTH)
			{
				System.out.println("\nSystem reached MAX_DEPTH - no solution! " + startWord.word + " --> null \n");
				System.exit(0);
			}
			
			if (next.equals(endWord))
			{
				next.printSolution();
				System.exit(0);
			}

			for (int j=0; j<=next.depth; j++) System.out.print("\t");
			System.out.println("-- " + next.word);
			
			for (int i=0; i<next.word.length(); i++)
			{
				char[] nextArray = next.toCharArray();
				for (char c : alphabet)
				{
					nextArray[i] = c;
					String candidate = new String(nextArray);
					if (dictionary.contains(candidate))
					{
						for (int j=0; j<=next.depth+1; j++) System.out.print("\t");
						System.out.println("-- " + candidate);
						
						Word child = new Word(candidate, next.depth+1, next);
						
						if (child.equals(endWord)) 
						{
							child.printSolution();
							System.out.println();
							System.exit(0);
						}
						
						bfs.add(child);
						dictionary.remove(candidate);
					}
				}
			}
			System.out.println();
		}
	}

	private static class Word
	{
		private String word;
		private int depth;
		private Word parent;
		
		public Word(String word, Integer depth, Word parent)
		{
			this.word = word;
			this.depth = depth;
			this.parent = parent;
		}
		
		public void printSolution()
		{
			if (parent == null)
			{
				System.out.print(this.word);
			}
			else
			{
				parent.printSolution();
				System.out.print(" --> " + this.word);
			}			
		}

		public char[] toCharArray()
		{
			return word.toCharArray();
		}
		
		public boolean equals(Word other)
		{
			return this.word.equals(other.word);
		}
		
		public int length()
		{
			return word.length();
		}
		
		public String toString()
		{
			return "{" + word + ", " + depth + ", " + parent + "}";
		}
	}

}
