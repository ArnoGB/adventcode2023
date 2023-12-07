package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class day6 {

	public static void main(String[] args) {
		File f = new File("./ressources/day6.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String timesStr = reader.readLine();
			String distancesStr = reader.readLine();

			List<Integer> times = Arrays
					.asList(timesStr.replace("Time:", " ").trim().replaceAll("\\s+", " ").split(" ")).stream()
					.map(Integer::parseInt).toList();
			List<Integer> distances = Arrays
					.asList(distancesStr.replace("Distance:", " ").trim().replaceAll("\\s+", " ").split(" ")).stream()
					.map(Integer::parseInt).toList();

			System.out.println(times);
			System.out.println(distances);

			int winnings = 1;
			for (int i = 0; i < times.size(); i++) {
				winnings *= computeNumberOfWin(times.get(i), distances.get(i));
			}

			System.out.println("Winnings multiplied: " + winnings);

			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	public static int computeNumberOfWin(int time, int distance) {
		int win = 0;
		for (int i : computeAllChrono(time)) {
			if (i > distance)
				win++;
		}
		System.out.println(String.format("[%d on %d time] : %d wins", time, distance, win));
		return win;
	}

	public static ArrayList<Integer> computeAllChrono(int time) {
		ArrayList<Integer> times = new ArrayList<Integer>();
		for (int i = 0; i < time; i++) {
			times.add(i * (time - i));
		}
		return times;
	}

	public static long min(long a, long b) {
		return (a < b ? a : b);
	}

	public static long max(long a, long b) {
		return (a > b ? a : b);
	}
}
