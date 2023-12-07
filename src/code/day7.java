package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class day7 {
	
	static String[] cardValues = {"2","3","4","5","6","7","8","9","T","J","Q","K","A"};
	static String[] moves = {"nothing","high","pair","dpair","triple","full","quad","fives"};
	
	private static String getMove(Hand h) {
		String hand = h.hand;
		HashMap<String, Integer> values = new HashMap<String, Integer>();
		for(String c : hand.split("(?!^)")) {
			if(values.containsKey(c)) {
				values.put(c, values.get(c)+1);
			} else {
				values.put(c, 1);
			}
		}
		if(values.keySet().size() == 5) { return "high"; }
		int fives = 0;
		int pairs = 0;
		int triples = 0;
		int quads = 0;
		for(String card : values.keySet()) {
			switch(values.get(card)) {
				case 2:
					pairs++; break;
				case 3:
					triples++; break;
				case 4:
					quads++; break;
				case 5:
					fives++; break;
				default:
					break;
			}
		}
		if(fives==1) {return "fives";}
		if(quads==1) {return "quad";}
		if(triples==1 && pairs==0) {return "triple";}
		if(triples==1 && pairs==1) {return "full";}
		if(triples==0 && pairs==2) {return "dpair";}
		if(triples==0 && pairs==1) {return "pair";}
		return "nothing";
	}
	
	private static int compareCard(String card1, String card2) {
		int force1 = Arrays.asList(cardValues).indexOf(card1);
		int force2 = Arrays.asList(cardValues).indexOf(card2);
		return force1 - force2;
	}
	
	public static class Hand implements Comparable<Hand>{
		String hand;
		int bid;
		
		public Hand(String line) {
			hand = line.split(" ")[0];
			bid = Integer.parseInt(line.split(" ")[1]);
		}
		
		@Override
		public String toString() {
			return String.format("%s:%d (m=%s)", hand, bid, getMove(this));
		}

		@Override
		public int compareTo(Hand other) {
			System.out.println(String.format("%s vs %s:",this,other));
			int thisPower = Arrays.asList(moves).indexOf(getMove(this));
			int otherPower = Arrays.asList(moves).indexOf(getMove(other));
			if(thisPower != otherPower) {
				System.out.println("d="+(thisPower - otherPower)*1000);
				return (thisPower - otherPower)*1000;
			} else {
				for(int i=0; i<hand.length() && i<other.hand.length(); i++) {
					if(compareCard(String.valueOf(hand.charAt(i)), String.valueOf(other.hand.charAt(i))) != 0) {
						System.out.println("d'="+compareCard(String.valueOf(hand.charAt(i)), String.valueOf(other.hand.charAt(i))));
						return compareCard(String.valueOf(hand.charAt(i)), String.valueOf(other.hand.charAt(i)));
					}
				}
				return 0;
			}
		}
	}
	
	public static void main(String[] args) {
		File f = new File("./ressources/day7.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			
			String line = reader.readLine();
			ArrayList<Hand> hands = new ArrayList<>(); 
			
			while (line != null) {
				Hand h = new Hand(line);
				System.out.println(h);
				hands.add(h);
				
				line = reader.readLine();
			}
			
			System.out.println(hands);
			
			hands.sort((o1, o2) -> o1.compareTo(o2));
			
			int rank=1;
			int amount =0 ;
			for(Hand hand : hands) {
				System.out.println(hand + " is "+rank);
				amount += rank*hand.bid;
				rank++;
			}
			System.out.println("amount is "+amount);
			
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static long min(long a, long b) {
		return (a < b ? a : b);
	}
	
	public static long max(long a, long b) {
		return (a > b ? a : b);
	}

}
