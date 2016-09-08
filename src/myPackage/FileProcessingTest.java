package myPackage;

import java.io.File;
import java.io.FileNotFoundException;

public class FileProcessingTest {
	
	public static void main(String[] args) throws FileNotFoundException{
		FileProcessing test = new FileProcessing(new File("\\input.txt"));
		
		test.processFile();
	}
	
}
