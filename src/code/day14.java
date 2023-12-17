package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class day14 {

	public static void main(String[] args) {
		File f = new File("./ressources/day14.txt");
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			ArrayList<String> matrix = new ArrayList<String>();
			while (line != null) {
				
				matrix.add(line);
				
				System.out.println(line);
				line = reader.readLine();
			}

			System.out.println();
			System.out.println();
			System.out.println();
			
			matrix = rollNorth(matrix);

			for(String m : matrix) {
				System.out.println(m);
			}
			
			int amount = computeWeight(matrix);
			
			System.out.println("Amount is " + amount);

			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	private static ArrayList<String> rollNorth(ArrayList<String> matrix) {
		
		ArrayList<String> newMatrix = new ArrayList<String>(matrix);
		
		int length = matrix.size();
		int width = matrix.get(0).length();
		
		for(int i = 0; i < length -1; i++) { //i start point going down
			for(int j = 0; j < length-1; j++) { //j inside loop, both going down
				StringBuilder line = new StringBuilder(newMatrix.get(j)); 
				StringBuilder nextLine = new StringBuilder(newMatrix.get(j+1)); 
				for(int c = 0; c < width; c++) {
					if(line.charAt(c) == '.' && nextLine.charAt(c) == 'O') {
						line.setCharAt(c, 'O');
						nextLine.setCharAt(c, '.');
					}
				}
				newMatrix.set(j, line.toString());
				newMatrix.set(j+1, nextLine.toString());
			}
		}
		
		return newMatrix;
	}
	
	private static int computeWeight(ArrayList<String> matrix) {
		int weight = 0;
		for(int i = 0; i<matrix.size(); i++) {
			String line = matrix.get(i);
			int rowW = matrix.size() - i;
			for(char c : line.toCharArray()) {
				if(c == 'O') {
					weight += rowW;
				}
			}
		}
		return weight;
	}

}
