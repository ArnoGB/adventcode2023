package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class day1 {
	public static void main(String[] args) throws IOException {
		File f = new File("./ressources/day1.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			int somme = 0;
			while (line != null) {
				System.out.println(extractNoumbers(line));
				somme += Integer.parseInt(extractNoumbers(line));
				line = reader.readLine();
			}
			System.out.println("somme is " + somme);
			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	private static String extractNoumbers(String line) {
		String[] noummeurs = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		int s = -1;
		int e = -1;
		for (int i = 0; i < line.length(); i++) {
			if (Arrays.asList(noummeurs).contains(String.valueOf(line.charAt(i)))) {
				if (s == -1) {
					s = i;
				}
				e = i;
			}
		}

		return String.valueOf(line.charAt(s)) + String.valueOf(line.charAt(e));
	}
}
