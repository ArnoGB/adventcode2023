package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class day15 {

	public static void main(String[] args) {
		File f = new File("./ressources/day15.txt");
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			String[] values = line.split(",");

			int value = 0;
			for(String s : values) {
				System.out.println(s);
				System.out.println(hash(s));
				value += hash(s);
			}
			
			System.out.println("Final value is "+value);
			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	private static int hash(String s) {
		int v = 0;
		for(char c : s.toCharArray()) {
			v += (int) c;
			v = (v*17)%256;
		}
		return v;
	}

}
