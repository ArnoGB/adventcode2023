package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class day5part2 {

	public static class Range {
		long source;
		long length;

		public Range(long source, long length) {
			this.source = source;
			this.length = length;
		}

		public Range(Range range) {
			this(range.source, range.length);
		}

		public long end() {
			return source + length;
		}

		@Override
		public String toString() {
			return String.format("[%d>%d]", source, source + length - 1);
		}

		public Range overlap(Range other) {
			if (this.end() < other.source || other.end() < source) {
				return null;
			} else {
				long overlap_start = max(source, other.source);
				long overlap_end = min(this.end(), other.end());
				return new Range(overlap_start, overlap_end - overlap_start);
			}
		}

		public ArrayList<Range> exclude(Range other) {
			ArrayList<Range> answer = new ArrayList<>();
			if (overlap(other) == null) {
				answer.add(this);
				return answer;
			} else {
				if (source < other.source) { // pre
					answer.add(new Range(source, other.source - source));
				}
				if (other.end() < this.end()) { // post
					answer.add(new Range(other.end(), this.end() - other.end()));
				}
			}
			return answer;
		}

		public Range shift(long amount) {
			return new Range(source + amount, length);
		}
	}

	public static class Constraint {
		Range range;
		long shift;

		public Constraint(Range range, long shift) {
			this.range = range;
			this.shift = shift;
		}

		@Override
		public String toString() {
			return String.format("%s : %+d", range, shift);
		}
	}

	public static class Mapping {
		ArrayList<Constraint> constraints;

		public Mapping() {
			constraints = new ArrayList<>();
		}

		@Override
		public String toString() {
			String s = "";
			for (Constraint constraint : constraints) {
				s += constraint.toString() + "\n";
			}
			return s;
		}

		public ArrayList<Range> processRanges(List<Range> inputs) {
			// We need a buffer layer that will contain the inputs not yet processed
			ArrayList<Range> buffer = new ArrayList<>();
			ArrayList<Range> output = new ArrayList<>();

			for (Range input : inputs) {
				buffer.add(input);
			}

			for (Constraint c : constraints) {
				System.out.println(String.format("Applying %s on %s", c, buffer));
				ArrayList<Range> temp = new ArrayList<>();
				for (int i = 0; i < buffer.size(); i++) {
					temp.addAll(buffer.get(i).exclude(c.range));
					if (buffer.get(i).overlap(c.range) != null) {
						output.add(buffer.get(i).overlap(c.range).shift(c.shift));
					}
				}
				buffer = temp;
			}
			output.addAll(buffer);

			return output;
		}
	}

	public static void main(String[] args) {
		File f = new File("./ressources/day5.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));

			// first line: seeds
			String line = reader.readLine();
			List<Range> seeds = parseSeeds(line);
			ArrayList<Mapping> mappings = new ArrayList<>();

			// rest of the file
			line = reader.readLine();
			while (line != null) {

				// skip empty lines
				if (line.trim().isEmpty()) {
					line = reader.readLine();
					continue;
				}

				// new mapping
				if (line.contains(":")) {
					mappings.add(new Mapping());
					line = reader.readLine();
					continue;
				}

				// final situation (it's a constraint)
				Constraint c = parseConstraint(line);
				mappings.get(mappings.size() - 1).constraints.add(c);

				line = reader.readLine();
			}

			// show mappings
			for (Mapping m : mappings) {
				System.out.println(m);
			}

			// Moulinex the seeds through the mappings
			List<Range> processedSeeds = seeds;
			for (Mapping m : mappings) {
				System.out.println(String.format("seeds are now %s", processedSeeds));
				processedSeeds = m.processRanges(processedSeeds);
			}
			System.out.println(String.format("seeds are in the end %s", processedSeeds));

			long smallest = Long.MAX_VALUE;
			for (Range r : processedSeeds) {
				if (smallest > r.source)
					smallest = r.source;
			}

			System.out.println("Smallest is " + smallest);
			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	public static long min(long a, long b) {
		return (a < b ? a : b);
	}

	public static long max(long a, long b) {
		return (a > b ? a : b);
	}

	private static List<Range> parseSeeds(String line) {
		ArrayList<Range> seedRange = new ArrayList<Range>();
		List<String> values = Arrays.asList(line.replace("seeds: ", "").split(" "));
		for (int i = 0; i < values.size() - 1; i += 2) {
			Range seed = new Range(Long.parseLong(values.get(i)), Long.parseLong(values.get(i + 1)));
			seedRange.add(seed);
		}
		return seedRange;
	}

	private static Constraint parseConstraint(String line) {

		String[] values = line.split(" ");
		// destination THEN source and length ???
		Range r = new Range(Long.parseLong(values[1]), Long.parseLong(values[2]));
		long shift = Long.parseLong(values[0]) - Long.parseLong(values[1]);
		return new Constraint(r, shift);
	}

}
