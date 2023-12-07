package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class day6part2 {

	public static void main(String[] args) {
		File f = new File("./ressources/day6.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String timeStr = reader.readLine();
			String distanceStr = reader.readLine();

			long time = Long.parseLong(timeStr.replace("Time:", " ").replaceAll("\\s+", ""));
			long distance = Long.parseLong(distanceStr.replace("Distance:", " ").replaceAll("\\s+", ""));

			System.out.println(time);
			System.out.println(distance);

			long winnings = computeNumberOfWin(time, distance);

			System.out.println("Winnings: " + winnings);

			reader.close();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	public static long computeNumberOfWin(long time, long distance) {
		long win = 0;
		for (long i : computeAllChrono(time)) {
			if (i > distance)
				win++;
		}
		System.out.println(String.format("[%d on %d time] : %d wins", time, distance, win));
		return win;
	}

	public static ArrayList<Long> computeAllChrono(long time) {
		ArrayList<Long> times = new ArrayList<Long>();
		for (long i = 0; i < time; i++) {
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
