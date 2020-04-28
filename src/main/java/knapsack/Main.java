package knapsack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import knapsack.bfs.BestFirstSearch;

public class Main {

	//	private static final int BENCHMARK_RUNS = 1000;

	public static void main(String[] args) throws IOException {
		//		List<String> lines = Files.readAllLines(Paths.get("inputs.txt")); //A list of the lines in the input file
		//		int capacity = Integer.parseInt(lines.get(0).trim());
		//
		//		ArrayList<Item> items = new ArrayList<>(); //The list of items to build
		//		for (int i = 1; i < lines.size(); i++) {
		//			String[] parts = lines.get(i).split(","); //The current line, split on commas
		//			items.add(new Item(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
		//		}
		run();

		//		System.out.println(new Backtrack(items, capacity).search());
		//		Benchmark benchmark = new Benchmark(items, capacity);
		//		benchmark.start();
		//		System.out.println(benchmark.getAverage());
	}

	private static void run() {
		StringBuilder sb = new StringBuilder();

		for (int i = 1; i < 3000; i++) {
			ArrayList<Item> items = Util.itemList(i, 100, 100);
			int capacity = (int) (Util.getTotalWeight(items) * 0.5);

			long start = System.nanoTime();
			//			new Backtrack(items, capacity).search();
			new BestFirstSearch(items, capacity).search();
			long end = System.nanoTime();
			System.out.println(end - start + "ns");
			sb.append(i + "," + (end - start) + "\n");
			try {
				Files.write(Paths.get("result.csv"), sb.toString().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
