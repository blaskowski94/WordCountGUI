package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class FileProcessing {
	
	private File file;
	private HashMap<String, Integer> wordMap;
	private boolean fileProcessed;
	
	/* Implement check to see if .txt file */
	
	public FileProcessing(File f){
		file = f;
		wordMap = new HashMap<>();
		fileProcessed = false;
	}
	
	public HashMap<String, Integer> processFile() throws FileNotFoundException{
		Scanner scan = new Scanner(file);
		while(scan.hasNext()){
			String word = scan.next().replaceAll("[^a-zA-Z ']", "").toLowerCase();
			if(wordMap.containsKey(word)){
				int count = wordMap.get(word);
				wordMap.put(word, ++count);
			}
			else
				wordMap.put(word, 1);
		}
		fileProcessed = true;
		scan.close();
		return wordMap;
	}
	
	public int countWords() throws FileNotFoundException{
		Scanner scan = new Scanner(file);
		int count = 0;
		while(scan.hasNext()){
			scan.next();
			count++;
		}
		scan.close();
		return count;
	}
	
	public int countDiffWords() throws FileNotFoundException{
		if(!fileProcessed){
			processFile();
		}
		
		return wordMap.size();
	}
	
	public HashMap<String,Integer> countByWord() throws FileNotFoundException{
		if(!fileProcessed){
			processFile();
		}
		return wordMap;
	}
	
}
