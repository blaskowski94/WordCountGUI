package application;

import java.io.File;

/**
 * This class is used for testing purposes with the FileProcessing.java class.
 * It only contains a main method and prints results to console.
 * 
 * @author Bob Laskowski
 *
 */
public class FileProcessingTest {
	
	/**
	 * Main method creates four FileProcessing objects. The first three use
	 * different .txt files included in src folder. The fourth contains an
	 * invalid file path to ensure error handling works correctly.
	 * 
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		// Small sample input I created
		FileProcessing test1 = new FileProcessing(new File(
				"C:\\Users\\Bob\\OneDrive\\Documents\\Augsburg\\Compilers I\\LaskowskiProjectOne\\src\\application\\input.txt"));
		
		System.out.println("input.txt");
		System.out.println(test1.countByWord());
		System.out.println(test1.countWords());
		System.out.println(test1.countDiffWords());
		System.out.println();
		
		// Hamlet by Shakespeare
		FileProcessing test2 = new FileProcessing(new File(
				"C:\\Users\\Bob\\OneDrive\\Documents\\Augsburg\\Compilers I\\LaskowskiProjectOne\\src\\application\\Hamlet.txt"));
		
		System.out.println("Hamlet");
		System.out.println(test2.countByWord());
		System.out.println(test2.countWords());
		System.out.println(test2.countDiffWords());
		System.out.println();
		
		// Sherlock Holmes
		FileProcessing test3 = new FileProcessing(new File(
				"C:\\Users\\Bob\\OneDrive\\Documents\\Augsburg\\Compilers I\\LaskowskiProjectOne\\src\\application\\Sherlock_Holmes.txt"));
		
		System.out.println("Sherlock Holmes");
		System.out.println(test3.countByWord());
		System.out.println(test3.countWords());
		System.out.println(test3.countDiffWords());
		System.out.println();
		
		// Invalid file path
		FileProcessing test4 = new FileProcessing(new File("C:\\invalidFilePath"));
		
		System.out.println("Invalid file path");
		System.out.println(test4.countByWord());
		System.out.println(test4.countWords());
		System.out.println(test4.countDiffWords());
		System.out.println();
	}
	
}