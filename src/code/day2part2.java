package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class day2part2 {

	public static class Draw {
		public int reds = 0;
		public int greens = 0;
		public int blues = 0;

		@Override
		public String toString() {
			return String.format("red:%d, blue:%d, green:%d;", reds, blues, greens);
		}

		public int power() {
			return (reds == 0 ? 1 : reds) * (greens == 0 ? 1 : greens) * (blues == 0 ? 1 : blues);
		}
	}

	public static class Game {
		public int id;
		public ArrayList<Draw> draws;

		@Override
		public String toString() {
			String str = "";
			for (Draw d : draws) {
				str += d.toString() + "\t";
			}
			return "Game " + id + ":\t" + str;
		}
	}

	public static void main(String[] args) throws IOException {
		File f = new File("./ressources/day2.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			int power = 0;
			while (line != null) {
				Game game = parseGame(line);
				Draw bag = deduceBag(game);

				System.out.println(game);
				System.out.println(String.format("Bag is: %s, power is:%d", bag.toString(), bag.power()));

				power += deduceBag(game).power();
				line = reader.readLine();
			}
			System.out.println("power is " + power);
			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	private static Game parseGame(String line) {
		String[] wordsP1 = line.split(":");
		wordsP1[0] = wordsP1[0].replace("Game ", "");
		int id = Integer.parseInt(wordsP1[0]);

		String[] wordsP2 = wordsP1[1].split(";");

		Game g = new Game();
		g.id = id;
		g.draws = new ArrayList<>();
		for (String drawString : wordsP2) {
			Draw d = parseDraw(drawString);
			g.draws.add(d);
		}
		return g;
	}

	private static Draw parseDraw(String line) {
		Draw d = new Draw();
		String[] wordsP1 = line.trim().split(",");
		for (String dice : wordsP1) {
			String[] wordsP2 = dice.trim().split(" ");
			switch (wordsP2[1]) {
			case "red":
				d.reds = Integer.parseInt(wordsP2[0]);
				break;
			case "green":
				d.greens = Integer.parseInt(wordsP2[0]);
				break;
			case "blue":
				d.blues = Integer.parseInt(wordsP2[0]);
				break;
			default:
				break;
			}
			;
		}
		return d;
	}

	private static Draw deduceBag(Game g) {
		Draw bag = new Draw();
		for (Draw d : g.draws) {
			bag.reds = max(d.reds, bag.reds);
			bag.greens = max(d.greens, bag.greens);
			bag.blues = max(d.blues, bag.blues);
		}
		return bag;
	}

	private static int max(int a, int b) {
		return Math.max(a, b);
	}

}
