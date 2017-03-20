// io/TextFile18.java
// TIJ4 Chapter IO, Exercise 18, page 940
// Modify TextFile.java so that it passes IOExceptions out to the caller.

/* My solution to one of the exercises in 
* Thinking in Java 4th Edition (by Bruce Eckel).
* It compiles and runs correctly using JDK 1.6.0
* @author Greg Gordon
* @author www.greggordon.org
* March, 2008
*/

// Static functions for reading and writing text files as
// a single string, and treating a file as an ArrayList.
import java.io.*;
import java.util.*;

public class TextFile18 extends ArrayList<String> {
  	// Read a file as a single string:
	public static String read(String fileName) throws IOException {
    		StringBuilder sb = new StringBuilder();
    		BufferedReader in = new BufferedReader(new FileReader(
        		new File(fileName).getAbsoluteFile()));
      		try {
        		String s;
        		while((s = in.readLine()) != null) {
          			sb.append(s);
          			sb.append("\n");
        		}
      		} finally {
        		in.close();
      		}
    		return sb.toString();
  	}
  	// Write a single file in one method call:
  	public static void write(String fileName, String text) throws IOException {
    		PrintWriter out = new PrintWriter(
        		new File(fileName).getAbsoluteFile());
      		try {
        		out.print(text);
      		} finally {
        		out.close();
      		}
    	}
  	// Read a file, split by any regular expression:
  	public TextFile18(String fileName, String splitter) throws IOException {
    		super(Arrays.asList(read(fileName).split(splitter)));
    		// Regular expression split() often leaves an empty
    		// String at the first position:
    		if(get(0).equals("")) remove(0);
  	}
  	// Normally read by lines:
  	public TextFile18(String fileName) throws IOException {
    		this(fileName, "\n");
  	}
  	public void write(String fileName) throws IOException {
    		PrintWriter out = new PrintWriter(
        		new File(fileName).getAbsoluteFile());
      		try {
        		for(String item : this)
          		out.println(item);
      		} finally {
        		out.close();
      		}
    	}
  	// Simple test:
  	public static void main(String[] args) {
		try {
    			String file = read("TextFile18.java");
    			write("test.txt", file);
    			TextFile18 text = new TextFile18("test.txt");
    			text.write("test2.txt");
    			// Break into unique sorted list of words:
    			TreeSet<String> words = new TreeSet<String>(
      				new TextFile18("TextFile18.java", "\\W+"));			
    			// Display the capitalized words:
    			System.out.println(words.headSet("a"));
		} catch(IOException e) {
			System.err.println("Caught " + e);
			e.printStackTrace();
		}
  	}
} 
