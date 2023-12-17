package code;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class day14part2 {
	
	static int exportAmount = 0;

	public static void main(String[] args) {
		File f = new File("./ressources/day14sample.txt");
		
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
			exportPng(matrix);
			
			ArrayList<ArrayList<String>> previousMatrices = new ArrayList<ArrayList<String>>();
			
			int rollAmount = 0;
			int lastIdentical = -1;
			do {
				System.out.println("CYCLE "+rollAmount);
				previousMatrices.add(new ArrayList<String>(matrix));
				for(int i=0; i<4; i++) { //teacher said to not do a for loop when not using the index, but I need 4 rotations
					matrix = rollNorth(matrix);
					matrix = rotateMatrix(matrix);
				}
				System.out.println("Rolled");
				exportPng(matrix);
				rollAmount++;
				lastIdentical = findIdentical(previousMatrices, matrix);
			} while(lastIdentical == -1 && rollAmount < 1000); //Did you know 1 billion ms is 12 days ?

			
			int cycle = rollAmount - lastIdentical;
			
			long offset = ((1_000_000_000L - lastIdentical)%cycle);
			

			System.out.println(lastIdentical+" - "+rollAmount+" - "+offset+" - "+cycle);
			
			matrix = previousMatrices.get((int) (lastIdentical+offset));
			
			for(String l : matrix)
				System.out.println(l);
			
			int amount = computeWeight(matrix);
			
			System.out.println("Amount is " + amount);

			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	//return id where was before
	private static int findIdentical(ArrayList<ArrayList<String>> previousMatrices, ArrayList<String> matrix) {
		for(ArrayList<String> previousMatrix : previousMatrices) {
			boolean identical = true;
			for(int i = 0; i<matrix.size(); i++) {
				if(!matrix.get(i).equals(previousMatrix.get(i))) {
					identical = false;
					break;
				}
			}
			if(identical)
				return previousMatrices.indexOf(previousMatrix);
		}
		return -1;
	}

	//rotate clockwise 90 deg, taken from day 13 part 2 and adapted
	private static ArrayList<String> rotateMatrix(ArrayList<String> matrix) {
		ArrayList<String> flippedTable = new ArrayList<String>();
		int length = matrix.size();
		int width = matrix.get(0).length();
		for(int i = 0; i < length ; i++) {
			for(int j = 0; j < width; j++) {
				int x = j;
				int y = width - i - 1;
				if(flippedTable.size() < width) {
					flippedTable.add(matrix.get(y).substring(x,x+1));
				} else {
					flippedTable.set(j, flippedTable.get(j) + matrix.get(y).substring(x,x+1));
				}
			}
		}
		
		return flippedTable;
		
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
	
	private static void exportPng(ArrayList<String> matrix) {
		int length = matrix.size()*16;
		int width = matrix.get(0).length()*16;
		
		BufferedImage image = new BufferedImage(width, length, BufferedImage.TYPE_INT_RGB);
		Color cube = new Color(50,50,50);
		Color boulder = new Color(100,100,150);
		Color empty = new Color(250,250,250);
		

		Graphics2D g = image.createGraphics();
		
		for(int i = 0; i<matrix.size(); i++) {
			String line = matrix.get(i);
			int j = 0;
			for(char c : line.toCharArray()) {
				if(c == 'O') {
					g.setColor(boulder);
					g.fillRect(j*16, i*16, 16, 16);
				} else if(c == '#') {
					g.setColor(cube);
					g.fillRect(j*16, i*16, 16, 16);
				} else {
					g.setColor(empty);
					g.fillRect(j*16, i*16, 16, 16);
				}
				j++;
			}
		}
		
		File f = new File("pictures/day14export_"+exportAmount+".png");
		try {
			ImageIO.write(image, "png", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		exportAmount++;
	}

}
