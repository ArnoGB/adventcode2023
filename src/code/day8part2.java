package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day8part2 {
	
	//Stolen from https://stackoverflow.com/questions/14586131/lcm-lowest-common-multiple-in-java
	/**
	* Calculate Lowest Common Multiplier
	*/
	public static long LCM(long a, long b) {
	    return (a * b) / GCF(a, b);
	}

	/**
	* Calculate Greatest Common Factor
	*/
	public static long GCF(long a, long b) {
	    if (b == 0) {
	        return a;
	    } else {
	        return (GCF(b, a % b));
	    }
	} 
	
	//Home-made
	public static long LCM(ArrayList<Long> array) {
		long a=0, b=0;
		if(array.size() > 2) {
			a = array.get(0);
			ArrayList<Long> workingArray = new ArrayList<Long>(array);
			workingArray.remove(0);
			b = LCM(workingArray);
		} else {
			a = array.get(0);
			b = array.get(1);
		}
		return LCM(a,b);
		
	} 
	
	public static class Node {
		String left;
		String right;
	}
	
	public static class Graph {
		HashMap<String, Node> graph;
		
		public Graph() {
			graph = new HashMap<>();
		}
		
		public String walk(String start, String direction) {
			if(!"R".equals(direction) && !"L".equals(direction)) {
				System.err.println("unexpected direction: -"+direction+"-");
			}
			Node node = graph.get(start);
			if("L".equals(direction)) {
				return node.left;
			}
			if("R".equals(direction)) {
				return node.right;
			}
			return null;
		}
		
		public ArrayList<String> getStarts() {
			//return nodes ending with A
			ArrayList<String> starts = new ArrayList<String>();
			for(Entry<String, Node> n : graph.entrySet()) {
				if(n.getKey().endsWith("A")) {
					starts.add(n.getKey());
				}
			}
			return starts;
		}
		
		@Override
		public String toString() {
			String str = "";
			for(Entry<String, Node> n : graph.entrySet()) {
				str += String.format("%s: (%s, %s)\n", n.getKey(), n.getValue().left, n.getValue().right);
			}
			return str;
		}
	}

	public static void main(String[] args) {
		File f = new File("./ressources/day8.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			//Read move
			String move = reader.readLine();
			//Empty line
			reader.readLine();
			Graph g = new Graph();
			String line = reader.readLine();

			while (line != null) {
				
				//Build regex
				Pattern p = Pattern.compile("([A-Z0-9]+) = \\(([A-Z0-9]+), ([A-Z0-9]+)\\)");
				System.out.println(line);
				Matcher m = p.matcher(line);
				if(!m.matches()) {
					System.err.println("it dont match, wtf");
				} else {
					//Build Node
					Node n = new Node();
					n.left = m.group(2);
					n.right = m.group(3);
					//Build graph
					g.graph.put(m.group(1), n);
				}
				
				line = reader.readLine();
			}
			System.out.println();
			System.out.println(g);
			
			//Solve individually all the amounts
			ArrayList<String> paths = g.getStarts();
			ArrayList<Long> lengths = new ArrayList<>();
			for (String p : paths) {
				lengths.add(0L);
				p.hashCode(); //to remove warnings
			}
			long time = System.currentTimeMillis();
			System.out.println("time is "+time);
			//while not going back on a known node
			int inProgress = paths.size();
			while(inProgress > 0) {
				inProgress = paths.size();
				for(int i=0; i<paths.size(); i++) {
					String path = paths.get(i);
					if(!path.endsWith("Z")) {
						for(char c : move.toCharArray()) {
							String action = String.valueOf(c);
							path = g.walk(path, action);
							lengths.set(i, lengths.get(i) + 1);
							paths.set(i, path);
						}
					} else {
						inProgress--;
					}
				}
				System.out.println(paths +" : "+ lengths);
			}
			
			//Perform a PPMC (https://fr.wikipedia.org/wiki/Plus_petit_commun_multiple)
			System.out.println("Optimal length is: " + LCM(lengths));
			

			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
}
