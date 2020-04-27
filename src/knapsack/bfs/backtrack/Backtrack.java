package knapsack.bfs.backtrack;

import java.util.ArrayList; // Import ArrayList

import knapsack.Item;

public class Backtrack {

	public static int backTrack(ArrayList<Item> items, int capacity) {
		return backTrack(items, items.size() - 1, capacity);
	}

	private static int backTrack(ArrayList<Item> items, int numItems, int totWeight) {
		//Base case for recursive call; Indicates negative capcity
		//ArrayList<Integer> finalTree = new ArrayList<Integer>();
		if (totWeight < 0) {
			return Integer.MIN_VALUE;
		}
		//Base case 2; No items or no more capcity 
		if (numItems < 0 || totWeight == 0) {
			return 0;
		}
		//First case: Include the current item in knapsack and recur for the rest of items
		Item item = items.get(numItems);
		int include = item.getProfit() + backTrack(items, numItems - 1, totWeight - item.getWeight());

		//Second case: Exclude current item and recur for remaining 
		int exclude = backTrack(items, numItems - 1, totWeight);

		return Integer.max(include, exclude);
	}

}
