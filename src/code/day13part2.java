package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class day13part2 {

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
				int existing = readVSymmetryStrict(table);
				int vSymmetry = readDifferentVSymmetryWithSmudge(table, existing);
				symmetriesV.add(vSymmetry);
				existing = readVSymmetryStrict(flip(table));
				int hSymmetry = readDifferentVSymmetryWithSmudge(flip(table), existing);
				symmetriesH.add(hSymmetry);
				
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
	private static int readDifferentVSymmetryWithSmudge(ArrayList<String> table, int existing) {
		for(String line : table) {
			System.out.println(line);
		}
		for(int charIndex = 1; charIndex < table.get(0).length(); charIndex++) {
			if(existing == charIndex)
				continue;
			System.out.println("Symmetry at "+charIndex+"?");
			boolean smudge = false;
			boolean symmetryHolds = true;
			int lineIndex = 0;
			for(String line : table) {
				//System.out.println(line);
				if(symmetricAt(charIndex, line) == 1 && !smudge) { 
					System.out.println(String.format("Smudge at %d:%d", table.indexOf(line), charIndex) );
					smudge = true;
				} else if (symmetricAt(charIndex, line) > 0) {
					System.out.println(String.format("No, at %d:%d", table.indexOf(line), charIndex));
					symmetryHolds = false;
					break;
				}
				//System.out.println("ok");
			}
			if(symmetryHolds && smudge) {
				System.out.println("yeeees");
				return charIndex;
			}
		}
		return -1;
	}
	
	//return -1 if no symmetry
		private static int readVSymmetryStrict(ArrayList<String> table) {
			for(String line : table) {
				System.out.println(line);
			}
			for(int charIndex = 1; charIndex < table.get(0).length(); charIndex++) {
				System.out.println("Symmetry at "+charIndex+"?");
				boolean symmetryHolds = true;
				for(String line : table) {
					//System.out.println(line);
					if (symmetricAt(charIndex, line) > 0) {
						System.out.println(String.format("No, at %d:%d", table.indexOf(line), charIndex));
						symmetryHolds = false;
						break;
					}
					//System.out.println("ok");
				}
				if(symmetryHolds) {
					System.out.println("yeeees");
					return charIndex;
				}
			}
			return -1;
		}

	//return amount of incorrectitude from symmetry, 0 = symmetric
	private static int symmetricAt(int charIndex, String line) {
		int length = line.length();
		int symmetry = 0;
		for(int i = charIndex; i<length; i++) {
			int j = charIndex + (charIndex - 1 - i);
			if(j < 0) {
				return symmetry;
			}
			if(line.charAt(i) != line.charAt(j)) {
				symmetry++;
			}
		}
		return symmetry;
	}
	
	//return -1 if no symmetry
	private static ArrayList<String> flip(ArrayList<String> table) {
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
		
		return flippedTable;
	}

}
