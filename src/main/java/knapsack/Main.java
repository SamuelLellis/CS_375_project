package knapsack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException {
		//		double[] capacityFractions = new double[] { 0.01, 0.99, 0.50 };
		//		//		double[] capacityFractions = new double[] { 0.01 };
		//		for (Type type : Type.values()) {
		//			for (double capacityFraction : capacityFractions) {
		//				run(type, capacityFraction);
		//			}
		//		}
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

			int v = Type.BEST_FIRST.getCreator().create(items, capacity).solve();
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

	public static void run(Type type, double capacityFraction) {
		System.out.println("TYPE: " + type);
		StringBuilder sb = new StringBuilder();

		for (int i = 1; i < 1000; i++) {
			ArrayList<Item> items = Util.itemList(i, 100, 100);
			int capacity = (int) (Util.getTotalWeight(items) * capacityFraction);

			long start = System.nanoTime();
			type.getCreator().create(items, capacity).solve();
			long end = System.nanoTime();
			if (end - start > 6863546900L) {
				System.out.println(capacity + "," + items);
			}
			System.out.println(i + ") " + (end - start) + "ns");
			sb.append(i + "," + (end - start) + "\n");
			try {
				Files.write(Paths.get("data", type + "-" + capacityFraction + ".txt"), sb.toString().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
