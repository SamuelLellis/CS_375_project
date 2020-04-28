package knapsack;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

	private static final ThreadLocalRandom rand = ThreadLocalRandom.current();

	public static int getTotalWeight(ArrayList<Item> items) {
		return items.stream().mapToInt(Item::getWeight).sum();
	}

	public static ArrayList<Item> itemList(int amount, int maxProfit, int maxWeight) {
		ArrayList<Item> items = new ArrayList<Item>();
		for (int i = 0; i < amount; i++) {
			items.add(item(maxProfit, maxWeight));
		}
		return items;
	}

	public static Item item(int maxProfit, int maxWeight) {
		return new Item(rand.nextInt(1, maxProfit + 1), rand.nextInt(1, maxWeight + 1));
	}

}
