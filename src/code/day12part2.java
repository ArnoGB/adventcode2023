package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class day12part2 {

	
	public static void main(String[] args) {
		File f = new File("./ressources/day12.txt");	
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			ArrayList<String> records = new ArrayList<String>();

			while (line != null) {
				records.add(line);
				System.out.println(line);
				line = reader.readLine();
			}
			
			System.out.println();
			
			int amount = 0;
			int i=0;
			for(String r : records) {

				System.out.println();

				System.out.println("before: "+r);
				String unfoleded = unfold(r);
				ArrayList<String> corrected = possibleArangements(unfoleded);
				System.out.println("size is "+corrected.size());
				i++;
				System.out.println(String.format("(%d/%d)", i, records.size()));
				amount += corrected.size();
			}
			
			System.out.println("Amount is "+amount);
			
			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	private static String unfold(String r) {
		String sequence = r.split(" ")[0];
		String rule = r.split(" ")[1];
		return sequence+"?"+sequence+"?"+sequence+"?"+sequence+"?"+sequence+" "+rule+","+rule+","+rule+","+rule+","+rule;
	}

	private static ArrayList<String> possibleArangements(String sample) {
		//Greedy algorithm: test all permutations of ?, test which ones are valid
		
		System.out.println(sample);
		ArrayList<String> arangements = new ArrayList<String>();
		
		int amountOfU = countOccurences(sample, '?');

		return arangements;
	}
	
	private static boolean isValid(String arangement) {
		String[] numbers = arangement.split(" ")[1].split(",");
		int searchIndex = 0;
		int counter = 0;
		int expected = Integer.valueOf(numbers[0]) ;
		for(char c : (arangement.split(" ")[0] + ".").toCharArray()) {
			if(c == '#') {
				counter++;
				if(counter > expected)
					return false;
			} else if (counter != 0){
				if(counter != expected) {
					return false;
				}
				counter = 0;
				searchIndex++;
				if(searchIndex < numbers.length) {
					expected = Integer.valueOf(numbers[searchIndex]);
				} else if(searchIndex == numbers.length) {
					expected = 0;
				} else  {
					return false;
				}
			}
		}
		if(searchIndex < numbers.length) {
			return false;
		}
		return true;
	}

	private static int countOccurences(String s, char character) {
		int o = 0;
		for(char c : s.toCharArray()) {
			if(c == character) o++;
		}
		return o;
	}
}
