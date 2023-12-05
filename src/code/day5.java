package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class day5 {

	public static class Constraint {
		long source;
		long dest;
		long length;
		
		public Constraint(long source, long dest, long length) {
			this.source = source;
			this.dest = dest;
			this.length = length;
		}
		
		public boolean appliesTo(long n) {return source <= n && n < source + length;}
		
		long processNumber(long n) {
			if(appliesTo(n)) {
				n += (dest-source);
			}
			return n;
		}
		
		@Override
		public String toString() {
			return String.format("(%d>%d):%d", source, dest, length);
		}
	}
	
	public static class Mapping {
		ArrayList<Constraint> constraints;
		
		public Mapping() {constraints = new ArrayList<>();}
		
		long processNumber(long n) {
			for (Constraint constraint : constraints) {
				if(constraint.appliesTo(n)) {
					n = constraint.processNumber(n);
					System.out.println(String.format("Through %s, I have become %d.",constraint,n));
					break;
				}
			}
			return n;
		}
		
		@Override
		public String toString() {
			String s = "";
			for (Constraint constraint : constraints) {
				s += constraint.toString() + "\n";
			}
			return s;
		}
	}
	
	public static void main(String[] args) {
		File f = new File("./ressources/day5.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			
			//first line: seeds
			String line = reader.readLine();
			List<String> seeds = Arrays.asList(line.replace("seeds: ", "").split(" "));
			ArrayList<Mapping> mappings = new ArrayList<>();
			
			//rest of the file
			line = reader.readLine();
			while (line != null) {
				
				//skip empty lines
				if(line.trim().isEmpty()) {
					line = reader.readLine();
					continue;
				}
				
				//new mapping
				if(line.contains(":")) {
					String mappingName = line.split(" ")[0];
					mappings.add(new Mapping());
					line = reader.readLine();
					continue;
				}
				
				//final situation (it's a constraint)
				String[] values = line.split(" ");
				//destination THEN source and length ???
				Constraint c = new Constraint(Long.parseLong(values[1]), Long.parseLong(values[0]), Long.parseLong(values[2]));
				mappings.get(mappings.size()-1).constraints.add(c);
				
				line = reader.readLine();
			}
			
			//show mappings
			for(Mapping m : mappings) {
				System.out.println(m);
			}
			
			//Moulinex the seeds through the mappings
			ArrayList<Long> processedSeeds = new ArrayList<Long>();
			for(String seedS : seeds) {
				long seed = Long.parseLong(seedS);
				for(Mapping m : mappings) {
					System.out.print(seed+">");
					seed = m.processNumber(seed);
				}
				System.out.println(seed);
				processedSeeds.add(seed);
			}
			
			long smallest = min(processedSeeds);

			System.out.println("Smallest is "+smallest);
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static long min(ArrayList<Long> processedSeeds) {
		long min = Long.MAX_VALUE;
		for(long i : processedSeeds) {
			if(i < min) min = i;
		}
		return min;
	}

}
