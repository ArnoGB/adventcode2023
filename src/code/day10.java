package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class day10 {
	
	public static class Point {
		int x, y;
		int distance;
		public Point(int x, int y) {
			this.x = x; this.y = y; this.distance=0;
		}
		
		@Override
		public String toString() {return String.format("[%d,%d]", x+1, y+1);}
	}
	
	public static void main(String[] args) {
		File f = new File("./ressources/day10.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			ArrayList<char[]> pipes = new ArrayList<>();
			Point start = new Point(0,0);
			int y = 0;
			
			while (line != null) {
				
				//Parse as matrix
				char[] pipeLine = line.toCharArray();
				pipes.add(pipeLine);
				
				//Save S as a special position
				if (String.valueOf(pipeLine).contains("S")) {
					start = new Point(String.valueOf(pipeLine).indexOf("S"), y);
				}
				
				System.out.println(line);
				line = reader.readLine();
				y++;
			}
			
			int width = pipes.get(0).length;
			int tableLength = pipes.size();
			
			System.out.println(start);
			
			ArrayList<Point> lookupQueue = new ArrayList<>();
			ArrayList<Point> explored = new ArrayList<>();
			HashMap<Point, Integer> path = new HashMap<>();
			
			lookupQueue.add(start);
			
			int length = 0;
			while(lookupQueue.size() > 0) {
				ArrayList<Point> toCheck = new ArrayList<>(lookupQueue);
				for(Point p : lookupQueue) {
					explored.add(p);
					char symbol = pipes.get(p.y)[p.x];
					switch(symbol) {
						case 'S':
							if(pipes.get(p.y)[p.x-1] != '|' && pipes.get(p.y)[p.x-1] != 'J' && pipes.get(p.y)[p.x-1] != '7')
								toCheck.add(new Point(p.x-1,p.y));
							if(pipes.get(p.y)[p.x+1] != '|' && pipes.get(p.y)[p.x+1] != 'F' && pipes.get(p.y)[p.x+1] != 'L')
								toCheck.add(new Point(p.x+1,p.y));
							if(pipes.get(p.y-1)[p.x] != '-' && pipes.get(p.y-1)[p.x] != 'L' && pipes.get(p.y-1)[p.x] != 'J')
								toCheck.add(new Point(p.x,p.y-1));
							if(pipes.get(p.y+1)[p.x] != '-' && pipes.get(p.y+1)[p.x] != 'F' && pipes.get(p.y+1)[p.x] != '7')
								toCheck.add(new Point(p.x,p.y+1));
							break;
						case '|':
							toCheck.add(new Point(p.x,p.y-1));
							toCheck.add(new Point(p.x,p.y+1));
							break;
						case '-':
							toCheck.add(new Point(p.x-1,p.y));
							toCheck.add(new Point(p.x+1,p.y));
							break;
						case 'L':
							toCheck.add(new Point(p.x,p.y-1));
							toCheck.add(new Point(p.x+1,p.y));							
							break;
						case 'J':
							toCheck.add(new Point(p.x,p.y-1));
							toCheck.add(new Point(p.x-1,p.y));		
							break;
						case '7':
							toCheck.add(new Point(p.x,p.y+1));
							toCheck.add(new Point(p.x-1,p.y));		
							break;
						case 'F':
							toCheck.add(new Point(p.x,p.y+1));
							toCheck.add(new Point(p.x+1,p.y));		
							break;
						case '.':
							break;
						default:
							System.out.println("WTF is "+symbol);
					}
					if(symbol != '.') {
						path.put(p, length);
					}
				}
				lookupQueue = new ArrayList<>();
				//cleanup doubles
				for (Point point : toCheck) {
					if(!lookupQueue.contains(point) && !explored.contains(point)) {
						lookupQueue.add(point);
					}
				}
				toCheck = new ArrayList<>(lookupQueue);
				for(Point explor : explored) {
					for(Point lookup : lookupQueue) {
						if(explor.x == lookup.x && explor.y == lookup.y) {
							int index = toCheck.indexOf(lookup);
							if(index != -1)
								toCheck.remove(index);
						}
						if(lookup.x < 0 || lookup.y < 0 || width <= lookup.x || tableLength <= lookup.y) {
							int index = toCheck.indexOf(lookup);
							if(index != -1)
								toCheck.remove(index);
						}
					}
				}
				lookupQueue = toCheck;
				length++;
				System.out.println(length +""+ lookupQueue);
				
			}
			
			System.out.println(length-1 + " is final");
			
			printPath(path, width, tableLength);
			
			
			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	public static void printPath(HashMap<Point, Integer> path, int width, int height) {
		
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				boolean empty = true;
				for(Entry<Point, Integer> p : path.entrySet()) {
					if(p.getKey().x == j && p.getKey().y == i) {
						System.out.print(p.getValue()+"\t");
						//System.out.print("X");
						empty = false;
					}
				}
				if(empty)
					System.out.print(".\t");
			}
			
			System.out.println();
		}
		
	}
	
}
