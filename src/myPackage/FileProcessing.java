package myPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class FileProcessing {
	
	private File file;
	private HashMap wordMap;
	
	public FileProcessing(File f){
		file = f;
	}
	
	public HashMap processFile() throws FileNotFoundException{
		Scanner scan = new Scanner(file);
		while(scan.hasNext()){
			System.out.println(scan.next());
		}
		
		return wordMap;		
	}
	
}
