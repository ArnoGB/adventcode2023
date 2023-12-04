package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class day3part2 {

	public static class Part {
		int line;
		int pos;
		int value;

		public int getLength() {
			return Integer.toString(value).length();
		}

		@Override
		public String toString() {
			return String.format("%d: (%d:%d), l=%d", value, line, pos, getLength());
		}
	}

	public static class Gear {
		int line;
		int pos;
		ArrayList<Part> neighbours;
		
		public int getRatio() {
			int ratio = 1;
			for(Part p : neighbours) {
				ratio = ratio * p.value;
			}
			return ratio;
		}
		
		@Override
		public String toString() {
			String ratios="(";
			for(Part p : neighbours) {
				ratios+=p.value+",";
			}
			ratios+="="+getRatio()+")";
			return String.format("Gear: (%d:%d) %s", line, pos, ratios);
		}
	}

	public static void main(String[] args) {
		File f = new File("./ressources/day3.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>();
			while (line != null) {

				// turn into matrix
				ArrayList<Integer> matrixLine = parseSymbols(line);
				matrix.add(matrixLine);

				System.out.println(matrixLine);

				line = reader.readLine();
			}

			// read matrix, put parts in list
			ArrayList<Part> parts = new ArrayList<>();
			ArrayList<Gear> gears = new ArrayList<day3part2.Gear>();
			for (ArrayList<Integer> matrixLine : matrix) {
				int i = 0;
				while (i < matrixLine.size()) {
					if (matrixLine.get(i) < 0) {
						if (matrixLine.get(i) == -3) {
							Gear g = new Gear();
							g.line = matrix.indexOf(matrixLine);
							g.pos = i;
							g.neighbours = new ArrayList<day3part2.Part>();
							gears.add(g);
						}
						i++;
						continue;
					}
					int detectedValue = 0;
					Part part = new Part();
					part.line = matrix.indexOf(matrixLine);
					part.pos = i;
					while (i < matrixLine.size() && matrixLine.get(i) >= 0) {
						detectedValue = detectedValue * 10 + matrixLine.get(i);
						i++;
					}
					if (i < matrixLine.size() && matrixLine.get(i) == -3) {
						Gear g = new Gear();
						g.line = matrix.indexOf(matrixLine);
						g.pos = i;
						g.neighbours = new ArrayList<day3part2.Part>();
						gears.add(g);
					}
					part.value = detectedValue;
					parts.add(part);
					i++;
				}
			}

			// give neighbours to gears
			for (Gear gear : gears) {
				for (Part part : parts) {
					if (areNeighbours(part, gear)) {
						gear.neighbours.add(part);
					}
				}
			}

			// mesure ratios
			int ratioSum = 0;
			for(Gear gear : gears) {
				System.out.println(gear+" has "+gear.neighbours.size()+" neighbours");
				if(gear.neighbours.size() ==2) {
					ratioSum += gear.getRatio();
					System.out.println("added, ratio is "+ratioSum);
				}
			}

			System.out.println("value is " + ratioSum);

			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static boolean areNeighbours(Part part, Gear gear) {
		if (part.pos - 1 <= gear.pos 
				&& part.line - 1 <= gear.line 
				&& gear.pos <= part.pos + part.getLength()
				&& gear.line <= part.line + 1) {
			return true;
		}
		return false;
	}

	private static ArrayList<Integer> parseSymbols(String line) {
		char[] chars = line.toCharArray();
		ArrayList<Integer> ints = new ArrayList<Integer>();
		for (char c : chars) {
			int value = readValue(c);
			ints.add(value);
		}
		return ints;
	}

	private static int readValue(char c) {
		try {
			return Integer.parseInt(String.valueOf(c));
		} catch (Exception e) {
			if (c == '.') {
				return -1; // empty
			} else if (c == '*') {
				return -3;
			} else {
				return -2; // symbol
			}
		}
	}

}
