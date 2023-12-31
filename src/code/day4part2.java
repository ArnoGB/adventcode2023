package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class day4part2 {

	public static class Card {
		int id;
		ArrayList<Integer> winningNumbers;
		ArrayList<Integer> playedNumbers;

		@Override
		public String toString() {
			String winnings = "";
			for (int w : winningNumbers) {
				winnings += w + " ";
			}
			String played = "";
			for (int p : playedNumbers) {
				played += p + " ";
			}
			return String.format("Card %d: %s| %s => %d points", id, winnings, played, countPoints());
		}

		public int countGood() {
			int points = 0;
			for (int n : playedNumbers) {
				if (winningNumbers.contains(n)) {
					points++;
				}
			}
			return points;
		}

		public int countPoints() {
			int points = 0;
			for (int n : playedNumbers) {
				if (winningNumbers.contains(n)) {
					points = (points == 0) ? 1 : 2 * points;
				}
			}
			return points;
		}
	}

	public static void main(String[] args) {
		File f = new File("./ressources/day4.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			ArrayList<Card> cards = new ArrayList<day4part2.Card>();
			HashMap<Integer, Integer> distribution = new HashMap<>();

			while (line != null) {

				Card c = parseCard(line);
				cards.add(c);
				distribution.put(c.id, 1);
				System.out.println(c);
				line = reader.readLine();
			}

			int total = 0;
			for (Card c : cards) {
				int score = c.countGood();
				for (int i = c.id + 1; i <= c.id + score; i++) {
					distribution.put(i, distribution.get(c.id) + distribution.get(i));
				}
				total += distribution.get(c.id);
				System.out.println(String.format("Card %d: %d", c.id, distribution.get(c.id)));
			}

			System.out.println("Total is " + total);
			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	private static Card parseCard(String line) {
		String[] wordsP1 = line.replace("  ", " ").split(":");
		wordsP1[0] = wordsP1[0].replace("Card ", "");
		int id = Integer.parseInt(wordsP1[0].trim());

		String[] wordsP2 = wordsP1[1].trim().split("\\|");

		Card c = new Card();
		c.id = id;
		c.playedNumbers = new ArrayList<Integer>();
		c.winningNumbers = new ArrayList<Integer>();
		for (String winning : wordsP2[0].trim().split(" ")) {
			c.winningNumbers.add(Integer.parseInt(winning.trim()));
		}
		for (String played : wordsP2[1].trim().split(" ")) {
			c.playedNumbers.add(Integer.parseInt(played.trim()));
		}
		return c;
	}

}
