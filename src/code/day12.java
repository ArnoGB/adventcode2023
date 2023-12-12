package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class day12 {

	
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
			for(String r : records) {

				System.out.println();
				ArrayList<String> corrected = possibleArangements(r);
				System.out.println("size is "+corrected.size());
				amount += corrected.size();
			}
			
			System.out.println("Amount is "+amount);
			
			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	private static ArrayList<String> possibleArangements(String r) {
		//Greedy algorithm: test all permutations of ?, test which ones are valid
		
		System.out.println(r);
		
		ArrayList<String> permutabilities = new ArrayList<String>();
		int amountOfU = countOccurences(r, '?');
		for(int perm = 0; perm<Math.pow(2, amountOfU); perm++) {
			String permutability = "";
			for(int i = 1; i<Math.pow(2, amountOfU); i*=2) {
				if((perm%(i*2) - (perm%(i))) == 0) {
					permutability += "#";
				} else {
					permutability += ".";
				}
			}
			permutabilities.add(permutability);
		}
		
		ArrayList<String> arangements = new ArrayList<String>();
		for(String perm : permutabilities) {
			 String arangement = r;
			 int i = 0;
			 for(char c : r.toCharArray()) {
				 if(c == '?') {
					 arangement = arangement.replaceFirst("\\?", String.valueOf(perm.charAt(i)));
					 i++;
				 }
			 }
			 if(isValid(arangement)) {
				 System.out.println(arangement + " valid");
				 arangements.add(arangement);
			 }
			
		}
		
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
