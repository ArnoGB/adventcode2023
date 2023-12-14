package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class day13part2 {

	public static HashMap<String, Long> combinationCache = new HashMap<>();

	public static void main(String[] args) {
		File f = new File("./ressources/day13.txt");
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			ArrayList<ArrayList<String>> tables = new ArrayList<>();
			
			ArrayList<String> currentTable = new ArrayList<String>();

			while (line != null) {
				
				if(line.isEmpty()) {
					tables.add(currentTable);
					currentTable = new ArrayList<String>();
				} else {
					currentTable.add(line);
				}
				
				System.out.println(line);
				line = reader.readLine();
			}

			tables.add(currentTable);

			int amount = 0;

			System.out.println("tables are :"+tables);

			ArrayList<Integer> symmetriesV = new ArrayList<Integer>();
			ArrayList<Integer> symmetriesH = new ArrayList<Integer>();
			
			for(ArrayList<String> table : tables) {
				int hSymmetry = readHSymmetry(table);
				int vSymmetry = readVSymmetry(table);
				symmetriesH.add(hSymmetry);
				symmetriesV.add(vSymmetry);
			}

			System.out.println("hsym are :"+symmetriesH);

			System.out.println("vsym are :"+symmetriesV);
			
			for(int h : symmetriesH) {
				amount += h==-1?0:100*h;
			}
			for(int v : symmetriesV) {
				amount += v==-1?0:v;
			}
			
			System.out.println("Amount is " + amount);

			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	//return -1 if no symmetry
	private static int readVSymmetry(ArrayList<String> table) {

		for(int charIndex = 1; charIndex < table.get(0).length(); charIndex++) {
			System.out.println("Symmetry at "+charIndex+"?");
			boolean symmetryHolds = true;
			for(String line : table) {
				if(!isSymmetricAt(charIndex, line)) {
					System.out.println("no, at "+line);
					symmetryHolds = false;
				}
			}
			if(symmetryHolds) {return charIndex;}
		}
		return -1;
	}

	private static boolean isSymmetricAt(int charIndex, String line) {
		int length = line.length();
		for(int i = charIndex; i<length; i++) {
			int j = charIndex + (charIndex - 1 - i);
			if(j < 0) {
				return true;
			}
			if(line.charAt(i) != line.charAt(j)) {
				return false;
			}
		}
		return true;
	}

	//return -1 if no symmetry
	private static int readHSymmetry(ArrayList<String> table) {
		ArrayList<String> flippedTable = new ArrayList<String>();
		for(int i = 0; i < table.size(); i++) {
			for(int j = 0; j < table.get(0).length(); j++) {
				if(flippedTable.size() <= j) {
					flippedTable.add(table.get(i).substring(j,j+1));
				} else {
					flippedTable.set(j, flippedTable.get(j) + table.get(i).substring(j,j+1));
				}
			}
		}
		
		System.out.println("flipped table is "+flippedTable);
		
		return readVSymmetry(flippedTable);
	}

}
