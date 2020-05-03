package knapsack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import knapsack.impl.BestFirstSearch;

public class Main {

	public static void main(String[] args) throws IOException {
		ArrayList<Item> items = Util.itemList(10, 100, 100);
		double[] capacityFractions = new double[] { 0.01, 0.50, 0.99 };
		for (int i = 0; i < capacityFractions.length; i++) {
			double omega = capacityFractions[i];
			for (Type type : Type.values()) {
				int capacity = (int) (Util.getTotalWeight(items) * omega);
				int solution = type.getCreator().create(items, capacity).solve();
				System.out.println(type + " " + omega + " = " + solution);
			}
			System.out.println();
		}

		//		double[] capacityFractions = new double[] { 0.01, 0.99, 0.50 };
		//		for (Type type : Type.values()) {
		//			for (double capacityFraction : capacityFractions) {
		//				run(type, capacityFraction);
		//			}
		//		}
	}

	public static void run(Type type, double capacityFraction) {
		System.out.println("TYPE: " + type + " " + capacityFraction);
		StringBuilder sb = new StringBuilder();

		for (int i = 1; i < 10; i++) {
			ArrayList<Item> items = Util.itemList(i, 100, 100);
			int capacity = (int) (Util.getTotalWeight(items) * capacityFraction);

			long start = System.nanoTime();
			type.getCreator().create(items, capacity).solve();
			long end = System.nanoTime();
			System.out.println(i + ") " + (end - start) + "ns");
			sb.append(i + "," + (end - start) + "\n");
			try {
				Files.write(Paths.get("data", type + "-" + capacityFraction + ".txt"), sb.toString().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void testBFSWorstCase() {
		int max = 0;
		int maxCapacity = 0;
		ArrayList<Item> maxItems = null;
		int itemCount = 4;
		int theoreticalMax = (int) Math.pow(2, itemCount + 1) - 1;
		int count = 0;
		int checks = 30_000_000;
		for (int i = 0; i < checks; i++) {
			ArrayList<Item> items = Util.itemList(itemCount, 10, 10);
			int capacity = (int) (Util.getTotalWeight(items) * 0.5);

			BestFirstSearch bfs = (BestFirstSearch) Type.BEST_FIRST.getCreator().create(items, capacity);
			bfs.solve();
			int v = bfs.getVisited();
			if (v == theoreticalMax) {
				count++;
			}

			if (max == 0 || v > max) {
				maxItems = items;
				max = v;
				maxCapacity = capacity;
			}
		}
		System.out.println(max);
		System.out.println(maxCapacity + ") " + maxItems);
		System.out.println(count + "/" + checks);
		System.out.println("PERCENT: " + 100D * count / checks);
	}

}
