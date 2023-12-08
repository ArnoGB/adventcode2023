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

public class day8 {
	
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
				Pattern p = Pattern.compile("([A-Z]+) = \\(([A-Z]+), ([A-Z]+)\\)");
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
			
			//create known nodes
			ArrayList<String> knownNodes = new ArrayList<>();
			String path = "AAA";
			int amount = 0;
			long time = System.currentTimeMillis();
			System.out.println("time is "+time);
			//while not going back on a known node
			while(!knownNodes.contains(path) && !"ZZZ".equals(path)) {
				knownNodes.add(path);
				for(char c : move.toCharArray()) {
					String action = String.valueOf(c);
					path = g.walk(path, action);

					amount++;
				}
				System.out.println(amount + " : " + path);
			}
			
			System.out.println("time is "+System.currentTimeMillis());
			System.out.println("Elapsed:" + (System.currentTimeMillis()-time));
			
			System.out.println("Final amount is "+amount);

			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

}
