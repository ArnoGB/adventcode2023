package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class day12part2 {

	public static HashMap<String, Long> combinationCache = new HashMap<>();

	public static void main(String[] args) {
		File f = new File("./ressources/day12.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			ArrayList<String> records = new ArrayList<String>();

			while (line != null) {
				records.add(line);
				System.out.println(line);
				line = reader.readLine();
			}

			System.out.println();

			long amount = 0;
			long i = 0;
			for (String r : records) {

				System.out.println();

				System.out.println("before: " + r);
				r = unfold(r);
				System.out.println("After: " + r);
				long count = countPossibleArangements(r, false);
				System.out.println("size is " + count);
				i++;
				System.out.println(String.format("(%d/%d)", i, records.size()));
				amount += count;
			}

			System.out.println("Amount is " + amount);

			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	private static String unfold(String r) {
		String sequence = r.split(" ")[0];
		String rule = r.split(" ")[1];
		return sequence + "?" + sequence + "?" + sequence + "?" + sequence + "?" + sequence + " " + rule + "," + rule
				+ "," + rule + "," + rule + "," + rule;
	}

	private static long countPossibleArangements(String sample, boolean onBrokens) {
		// Greedy algorithm won't do it
		// Smarter: a recursive tree

		//System.out.println("Time to treat: "+sample+">"+onBrokens);

		if(sample.length() == 0)
			return 0;
		
		if(onBrokens == false && combinationCache.containsKey(sample)) {
			return combinationCache.get(sample);
		}

		String prompt = sample.split(" ")[0];
		List<Integer> rules = Arrays.asList(sample.split(" ")[1].split(",")).stream().map(s -> Integer.parseInt(s)).toList();
		rules = new ArrayList<Integer>(rules);

		int i = 0;
		while (i < prompt.length()) {
			if (prompt.charAt(i) == '#') {
				onBrokens = true;
				if(rules.isEmpty() || rules.get(0) == 0) {
					//too far, not possible
					//System.out.println("not valid ! (too many #)");
					return 0;
				}
				rules.set(0, rules.get(0) - 1);
				i++;
			}
			else if (prompt.charAt(i) == '.') {
				if(!rules.isEmpty() && rules.get(0) == 0) {
					rules.remove(0);
				} else if(onBrokens && !rules.isEmpty() && rules.get(0) > 0) {
					//System.out.println("not valid ! (not enough #)");
					return 0;
				}
				onBrokens = false;
				i++;
			}
			else if (prompt.charAt(i) == '?') {

				if(rules.isEmpty()) {
					rules.add(0); //fix in case we are done parsing and we encounter a ?
				}
				String ruleSet = String.join(",", rules.stream().map(s -> ""+s).toList());
				
				long value = countPossibleArangements("#" + prompt.substring(i+1)+" "+ruleSet, onBrokens)
						+ countPossibleArangements("." + prompt.substring(i+1)+" "+ruleSet, onBrokens);
				
				combinationCache.put(sample, value);
				
				return value;
			}
		}
		
		if(rules.size() == 1 && rules.get(0) > 0) {
			//System.out.println("not valid ! (There is a last rule to complete:" +rules+")");
			combinationCache.put(sample, 0L);
			return 0;
		}
		
		if(rules.size() > 1) {
			//System.out.println("not valid ! (There are still rules left to fulfill !:" +rules+")");
			combinationCache.put(sample, 0L);
			return 0;
		}
		
		//System.out.println("valid !");
		combinationCache.put(sample, 1L);
		return 1;
	}
}
