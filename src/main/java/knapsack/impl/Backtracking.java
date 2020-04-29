package knapsack.impl;

import java.util.ArrayList; // Import ArrayList

import knapsack.Item;
import knapsack.Solution;

public class Backtracking implements Solution {

	private ArrayList<Item> items;
	private int capacity;

	public int best = -1; //KEVIN NEW LINE HERE

	public Backtracking(ArrayList<Item> items, int capacity) {
		this.items = items;
		this.capacity = capacity;
	}

	@Override
	public int solve() {
		return backTrack(items.size() - 1, capacity, 0);
	}

	public int backTrack(int numItems, int totWeight, int currProf) {
		//Base case for recursive call; Indicates negative capcity
		//ArrayList<Integer> finalTree = new ArrayList<Integer>();
		if (totWeight < 0) {
			return Integer.MIN_VALUE;
		}
		//Base case 2; No items or no more capcity 
		if (numItems < 0 || totWeight == 0) {
			return 0;
		}

		//If it is impossible for the current path to produce a higher val, then forget it 
		if (best < currProf) {//KEVIN NEW LINE HERE
			best = currProf;//KEVIN NEW LINE HERE
		}
		if(currProf > capacity) {
			return 0;
		}
		int accum = currProf;//KEVIN NEW LINE HERE
		for (int i = numItems; i >= 0; i--) {//KEVIN NEW LINE HERE
			accum += items.get(i).getProfit();//KEVIN NEW LINE HERE
		} //KEVIN NEW LINE HERE
		if (accum < best) {//KEVIN NEW LINE HERE
			return 0;//KEVIN NEW LINE HERE
		} //KEVIN NEW LINE HERE

		//First case: Include the current item in knapsack and recur for the rest of items
		int include = items.get(numItems).getProfit() + backTrack(numItems - 1, totWeight - items.get(numItems).getWeight(), currProf + items.get(numItems).getProfit());//KEVIN MODIFIED LINE HERE

		//Second case: Exclude current item and recur for remaining 
		int exclude = backTrack(numItems - 1, totWeight, currProf);//KEVIN MODIFIED LINE HERE

		return Integer.max(include, exclude);
	}

}
