package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class day2 {
	
	public static class Draw {
		public int reds = 0;
		public int greens = 0;
		public int blues = 0;
		
		@Override
		public String toString() {
			return String.format("red:%d, blue:%d, green:%d;", reds, blues, greens);
		}
	}
	
	public static class Game {
		public int id;
		public ArrayList<Draw> draws;
		
		@Override
		public String toString() {
			String str = "";
			for(Draw d : draws) {
				str += d.toString()+"\t";
			}
			return "Game "+id+":\t"+str;
		}
	}
	
	public static void main(String[] args) throws IOException {
		File f = new File("./ressources/day2.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			ArrayList<Game> games = new ArrayList<>();
			ArrayList<Game> winningGames = new ArrayList<day2.Game>();
			Draw bag = new Draw();
			bag.reds = 12;
			bag.greens = 13;
			bag.blues = 14;
			while(line != null) {
				Game game = parseGame(line);
				if(isPossible(game, bag)) {
					winningGames.add(game);
				}

				Boolean isPossible = isPossible(game, bag);
				System.out.println(game);
				System.out.println("possible: "+isPossible);
				games.add(game);
				line = reader.readLine();
			} 

			System.out.println("Winning games are:");
			int somme=0;
			for(Game g : winningGames) {
				System.out.print(g.id+",");
				somme+=g.id;
			}
			System.out.println("\nsomme is "+somme);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Boolean isPossible(Game game, Draw bag) {
		for(Draw d : game.draws) {
			if(bag.reds < d.reds || bag.greens < d.greens || bag.blues < d.blues) {
				return false;
			}
		}
		return true;
	}

	private static Game parseGame(String line) {
		String[] wordsP1 = line.split(":");
		wordsP1[0] = wordsP1[0].replace("Game ", "");
		int id = Integer.parseInt(wordsP1[0]);
		
		String[] wordsP2 = wordsP1[1].split(";");

		Game g = new Game();
		g.id = id;
		g.draws = new ArrayList<day2.Draw>();
		for(String drawString : wordsP2) {
			Draw d = parseDraw(drawString);
			g.draws.add(d);
		}
		return g;
	}

	private static Draw parseDraw(String line) {
		Draw d = new Draw();
		String[] wordsP1 = line.trim().split(",");
		for(String dice : wordsP1) {
			String[] wordsP2 = dice.trim().split(" ");
			switch(wordsP2[1]) {
				case "red":
					d.reds = Integer.parseInt(wordsP2[0]);
					break;
				case "green":
					d.greens = Integer.parseInt(wordsP2[0]);
					break;
				case "blue":
					d.blues = Integer.parseInt(wordsP2[0]);
					break;
				default: break;
			};
		}
		return d;
	}
	
}
