package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class day1part2 {
	public static void main(String[] args) throws IOException {
		File f = new File("./ressources/day1.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			int somme = 0;
			while (line != null) {

				somme += Integer.parseInt(extractNoumbers(line));

				System.out.println(line + " / " + brewNoumbers(line)

						+ " / " + brewNoumbersButLikeTenet(line) + " / " + extractNoumbers(line) + " / " + somme);

				line = reader.readLine();
			}
			System.out.println("somme is " + somme);
			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	private static String extractNoumbers(String line) {
		String forth = brewNoumbers(line);
		String back = brewNoumbersButLikeTenet(line);
		return extractFirstNoumber(forth) + extractLastNoumber(back);
	}

	private static String extractFirstNoumber(String line) {
		String[] noummeurs = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		int s = -1;
		for (int i = 0; i < line.length(); i++) {
			if (Arrays.asList(noummeurs).contains(String.valueOf(line.charAt(i)))) {
				if (s == -1) {
					s = i;
				}
			}
		}
		return String.valueOf(line.charAt(s));
	}

	private static String extractLastNoumber(String line) {
		String[] noummeurs = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		int s = -1;
		for (int i = 0; i < line.length(); i++) {
			if (Arrays.asList(noummeurs).contains(String.valueOf(line.charAt(i)))) {
				s = i;
			}
		}
		return String.valueOf(line.charAt(s));
	}

	private static String brewNoumbers(String line) {
		String[] noummeurs = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] noummeursL = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

		if (line.length() < 2)
			return line;
		String lastChar = line.substring(line.length() - 1);
		line = brewNoumbers(line.substring(0, line.length() - 1)) + lastChar;

		for (int i = 0; i < 9; i++) {
			while (line.contains(noummeursL[i])) {
				line = line.replace(noummeursL[i], noummeurs[i]);
			}
		}
		return line;
	}

	private static String brewNoumbersButLikeTenet(String line) {
		String[] noummeurs = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] noummeursL = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

		if (line.length() < 2)
			return line;
		String firstChar = line.substring(0, 1);
		line = firstChar + brewNoumbersButLikeTenet(line.substring(1, line.length()));

		for (int i = 0; i < 9; i++) {
			while (line.contains(noummeursL[i])) {
				line = line.replace(noummeursL[i], noummeurs[i]);
			}
		}
		return line;
	}
}
