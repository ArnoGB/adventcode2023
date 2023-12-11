package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class day11 {
	
	public static class Star{
		int x,y;
		
		public Star(int x, int y) {this.x=x; this.y=y;}
		
		@Override public String toString() {return String.format("[%d,%d]", x,y);}
		
		public int distanceWith(Star other) {
			return Math.abs(other.x - x) + Math.abs(other.y - y);
		}
	}
	
	public static void main(String[] args) {
		File f = new File("./ressources/day11.txt");	
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			ArrayList<Star> stars = new ArrayList<>();
			
			int x = 0;
			int y = 0;
			while (line != null) {
				x = 0;
				for(char c : line.toCharArray()) {
					if(c == '#') {
						stars.add(new Star(x,y));
					}
					x++;
				}
				System.out.println(line);
				line = reader.readLine();
				y++;
			}
			
			doubleEmptyLines(stars);
			
			System.out.println(stars);
			
			System.out.println(stars.get(4)+""+stars.get(8) +" d: "+stars.get(4).distanceWith(stars.get(8)));
			System.out.println(stars.get(0)+""+stars.get(6) +" d: "+stars.get(0).distanceWith(stars.get(6)));
			System.out.println(stars.get(2)+""+stars.get(5) +" d: "+stars.get(2).distanceWith(stars.get(5)));
			System.out.println(stars.get(7)+""+stars.get(8) +" d: "+stars.get(7).distanceWith(stars.get(8)));
			
			int distance = calculateSumOfAllDistances(stars);
			
			System.out.println("Distance is "+distance);
			
			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	private static int calculateSumOfAllDistances(ArrayList<Star> stars) {
		ArrayList<Star> checkedStars = new ArrayList<>();
		int distance = 0;
		for(Star s: stars) {
			checkedStars.add(s);
			for(Star other: stars) {
				if(checkedStars.contains(other)) continue;
				distance += s.distanceWith(other);
			}
		}
		return distance;
	}

	private static void doubleEmptyLines(ArrayList<Star> stars) {
		int width = 0;
		int length = 0;
		for(Star s : stars) {
			if(s.x > width) width = s.x;
			if(s.y > length) length = s.y;
		}
		ArrayList<Boolean> emptyCols = new ArrayList<>();
		for(int i=0; i<width; i++) {
			boolean isEmpty = true;
			for(Star s : stars) {
				if(s.x == i) isEmpty = false;
			}
			emptyCols.add(isEmpty);
		}
		ArrayList<Boolean> emptyLines = new ArrayList<>();
		for(int i=0; i<length; i++) {
			boolean isEmpty = true;
			for(Star s : stars) {
				if(s.y == i) isEmpty = false;
			}
			emptyLines.add(isEmpty);
		}
		for(Star s : stars) {
			int shift = 0;
			for(int i=0; i<s.x; i++) {
				if(emptyCols.get(i)) {
					shift++;
				}
			}
			s.x += shift;
			shift = 0;
			for(int i=0; i<s.y; i++) {
				if(emptyLines.get(i)) {
					shift++;
				}
			}
			s.y += shift;
		}
		return;
	}
/*
	private static Star getClosestStar(Star star, ArrayList<Star> stars) {
		Star closest = null;
		int shortestD = Integer.MAX_VALUE;
		for(Star s : stars) {
			if(star == s) continue;
			int distance = s.distanceWith(star);
			if(distance < shortestD) {
				closest = s;
				shortestD = distance;
			}
		}
		return closest;
	}*/
}
