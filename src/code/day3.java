package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class day3 {

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
			for (ArrayList<Integer> matrixLine : matrix) {
				int i = 0;
				while (i < matrixLine.size()) {
					if (matrixLine.get(i) < 0) {
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
					part.value = detectedValue;
					parts.add(part);
					i++;
				}
			}

			// for each parts, include if good
			ArrayList<Part> goodParts = new ArrayList<day3.Part>();
			for (Part p : parts) {

				System.out.println(p);
				if (hasSymbolNeighbour(p, matrix)) {
					goodParts.add(p);
					System.out.println("is good !");
				}
			}

			// add good parts id, return
			int value = 0;
			for (Part p : goodParts) {
				value += p.value;
			}

			System.out.println("value is " + value);

			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	private static boolean hasSymbolNeighbour(Part p, ArrayList<ArrayList<Integer>> matrix) {
		for (int i = p.line - 1; i <= p.line + 1; i++) {
			if (i < 0 || i >= matrix.size())
				continue;
			for (int j = p.pos - 1; j <= p.pos + p.getLength(); j++) {
				if (j < 0 || j >= matrix.get(i).size())
					continue;
				System.out.println(String.format("testing %d:%d", i, j));
				if (matrix.get(i).get(j) == -2) {
					System.out.println(String.format("symbol at %d:%d", i, j));
					return true;
				}
			}
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
			} else {
				return -2; // symbol
			}
		}
	}

}
