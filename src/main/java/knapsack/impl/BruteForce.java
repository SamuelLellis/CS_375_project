package knapsack.impl;

import java.util.ArrayList; // Import ArrayList

import knapsack.Item;
import knapsack.Solution;

public class BruteForce implements Solution {

	private ArrayList<Item> items;
	private int capacity;

	public BruteForce(ArrayList<Item> items, int capacity) {
		this.items = items;
		this.capacity = capacity;
	}

	@Override
	public int solve() {
		return backTrack(items.size() - 1, capacity);
	}

	private int backTrack(int numItems, int totWeight) {
		//Base case for recursive call; Indicates negative capacity
		//ArrayList<Integer> finalTree = new ArrayList<Integer>();
		if (totWeight < 0) {
			return Integer.MIN_VALUE;
		}
		//Base case 2; No items or no more capacity 
		if (numItems < 0 || totWeight == 0) {
			return 0;
		}
		//First case: Include the current item in knapsack and recur for the rest of items
		Item item = items.get(numItems);
		int include = item.getProfit() + backTrack(numItems - 1, totWeight - item.getWeight());

		//Second case: Exclude current item and recur for remaining 
		int exclude = backTrack(numItems - 1, totWeight);

		return Integer.max(include, exclude);
	}

}
