package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class day9part2 {
	
	public static void main(String[] args) {
		File f = new File("./ressources/day9.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			Long endValue = 0L;

			while (line != null) {
				List<Long> numbers = Arrays.asList(line.trim().split(" ")).stream().map(Long::parseLong).toList();
				System.out.println(numbers);
				
				//Turn List of n into List of accelerations
				numbers = turnIntoAccelerations(numbers);
				
				//Add 0 to List
				numbers.add(0L);
				Collections.reverse(numbers);
				System.out.println(numbers);
				
				//Reverse generate list of accelerations
				numbers = generateFromAccelerations(numbers, true);
				System.out.println(numbers);
				
				//Add first number to end value
				long newValue = numbers.get(0);
				endValue += newValue;
				System.out.println("New Value is "+newValue);
				System.out.println();
				line = reader.readLine();
			}
			
			//print end value
			System.out.println(endValue);
			
			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	private static List<Long> turnIntoAccelerations(List<Long> inputs) {
		ArrayList<Long> accelerations = new ArrayList<>();
		ArrayList<Long> buffer = new ArrayList<>(inputs);
		while(buffer.size() > 0) {
			ArrayList<Long> temp = new ArrayList<>();
			accelerations.add(buffer.get(0));
			for (int i=0; i<buffer.size()-1; i++) {
				temp.add(buffer.get(i+1) - buffer.get(i));
			}
			buffer = temp;
		}
		return accelerations;
	}
	
	private static List<Long> generateFromAccelerations(List<Long> accelerations, boolean backward) {
		ArrayList<Long> positions = new ArrayList<>();
		positions.add(0L);
		for(Long a : accelerations) {
			
			ArrayList<Long> temp = new ArrayList<>();
			if(backward) {
				
				temp.add(a - positions.get(0));
				temp.add(a);
				
				for (int i=2; i<positions.size()+1; i++) {
					temp.add(temp.get(i-1) + positions.get(i-1));
				}
			} else {
				temp.add(a);
				for (int i=1; i<positions.size()+1; i++) {
					temp.add(temp.get(i-1) + positions.get(i-1));
				}
			}
			
			positions = temp;
		}

		return positions;
	}
	
}
