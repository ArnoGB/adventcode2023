package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class day10part2 {
	
	public static class Point {
		int x, y;
		int distance;
		public Point(int x, int y) {
			this.x = x; this.y = y; this.distance=0;
		}
		
		@Override
		public String toString() {return String.format("[%d,%d]", x+1, y+1);}
		
		@Override 
		public boolean equals(Object o) {
			if (o == this) {
	            return true;
	        }
			if (!(o instanceof Point)) {
	            return false;
	        }
			Point other = (Point) o;
			return other.x == x && other.y == y;
		}
	}
	
	public static void main(String[] args) {
		File f = new File("./ressources/day10.txt");
		

		/*
		 * For part 2:
		 * 
		 * 
		 * Start by expanding the input to replace .F7. by 0 0 -1 -1 -1 0 0
		 * 												   0 0 -1  0 -1 0 0
		 * 
		 * Use flood algorithm to replace 0 by an incrementing int which is index of region
		 * 
		 * Order by size, return 2nd region
		 * 
		 * */
		
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
			
			System.out.println(start);
			
			int[][] table = parseTable(pipes);
			
			for(int i = 0; i<table.length; i++) {
				for(int j = 0; j<table[i].length; j++) {
					System.out.print(table[i][j]+"\t");
				}
				System.out.println();
			}
			
			floodTable(table);
			
			System.out.println("Flooded table:");
			
			for(int i = 0; i<table.length; i++) {
				for(int j = 0; j<table[i].length; j++) {
					System.out.print(table[i][j]+"\t");
				}
				System.out.println();
			}
			
			System.out.println("Shortened flooded table:");
			
			int[][] shortenTable = shortenTable(table,1,3);
			HashMap<Integer,Integer> occurences = new HashMap<>();
			
			for(int i = 0; i<shortenTable.length; i++) {
				for(int j = 0; j<shortenTable[i].length; j++) {
					int key = shortenTable[i][j];
					if(occurences.containsKey(key)) {
						occurences.put(key, occurences.get(key)+1);
					} else {
						occurences.put(key, 1);
					}
					System.out.print(shortenTable[i][j]+"\t");
				}
				System.out.println();
			}
			
			System.out.println(occurences);
			
			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	private static int[][] parseTable(ArrayList<char[]> pipes) {
		int width = pipes.get(0).length;
		int length = pipes.size();
		int[][] table = new int[length*3+3][width*3+3];
		for(int i = 0; i < length*3+3; i++) {
			for(int j = 0; j < width*3+3; j++) {
				table[i][j] = 0;
			}
		}
		for(int j = 0; j < length; j++) {
			for(int i = 0; i < width; i++) {
				char symbol = pipes.get(j)[i];
				int x = i*3+2;
				int y = j*3+2;
				for(int xx = -1; xx < 2; xx++) {
					for(int yy = -1; yy < 2; yy++) {
						table[y+yy][x+xx] = 0;
					}
				}
				table[y][x] = -1;
				switch(symbol) {
					case '.': 
						table[y][x] = 0;
						break;
					//horizontal	
					case 'S':
						table[y-1][x] = -1;
						table[y+1][x] = -1;
					case '-':
						table[y][x-1] = -1;
						table[y][x+1] = -1;
						break;
					case '|':
						table[y-1][x] = -1;
						table[y+1][x] = -1;
						break;
					case 'J':
						table[y-1][x] = -1;
						table[y][x-1] = -1;
						break;	
					case 'L':
						table[y-1][x] = -1;
						table[y][x+1] = -1;
						break;	
					case 'F':
						table[y+1][x] = -1;
						table[y][x+1] = -1;
						break;	
					case '7':
						table[y+1][x] = -1;
						table[y][x-1] = -1;
						break;
					default:
						System.out.println("WTF is "+symbol);
				}
			}
		}
		return table;
	}

	private static void floodTable(int[][] table) {
		int groupIndex = 0;
		for (int i = 0; i<table.length; i++) {
			for(int j = 0; j<table[i].length; j++) {
				if(table[i][j] == 0) {
					groupIndex++;
					System.out.println("Analyzing group: "+groupIndex);
					table[i][j] = groupIndex;
					ArrayList<Point> explored = new ArrayList<>();
					List<Point> toExplore = new ArrayList<Point>();
					toExplore.add(new Point(j,i));
					while(!toExplore.isEmpty()) {
						ArrayList<Point> buffer = new ArrayList<>(toExplore);
						for(Point p : toExplore) {
							explored.add(p);
							table[p.y][p.x] = groupIndex;
							if(p.x > 1 && table[p.y][p.x-1] == 0) {
								buffer.add(new Point(p.x-1,p.y));
							}
							if(p.x < table[i].length-1 
									&& table[p.y][p.x+1] == 0) {
								buffer.add(new Point(p.x+1,p.y));
							}
							if(p.y > 1 && table[p.y-1][p.x] == 0) {
								buffer.add(new Point(p.x,p.y-1));
							}
							if(p.y < table.length-1 && table[p.y+1][p.x] == 0) {
								buffer.add(new Point(p.x,p.y+1));
							}
						}
						toExplore = buffer;
						buffer = new ArrayList<>();
						for(Point p : toExplore) {
							if(!buffer.contains(p) && !explored.contains(p)) {
								buffer.add(p);
							}
						}
						toExplore = buffer;
					}
				}
			}
		}
		
	}
	

	private static int[][] shortenTable(int[][] table, int padding, int factor) {
		int length = (table.length - padding)/factor;
		int width = (table[0].length - padding)/factor;
		int[][] newTable = new int[length][width];
		for (int i = 0; i<length; i++) {
			for(int j = 0; j<width; j++) {
				//stranded pieces of pipes, looking at 4 corners of pipes
				int c1 = table[i*factor+padding][j*factor+padding];
				int c2 = table[i*factor+padding+2][j*factor+padding];
				int c3 = table[i*factor+padding][j*factor+padding+2];
				int c4 = table[i*factor+padding+2][j*factor+padding+2];
				if((c1 == c2) && (c2 == c3) && (c3 == c4)) {
					newTable[i][j] = table[i*factor+padding][j*factor+padding];
				} else {
					newTable[i][j] = table[i*factor+padding+1][j*factor+padding+1];
				}
			}
		}
		return newTable;
	}

	
}
