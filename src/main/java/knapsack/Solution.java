package knapsack;

import java.util.ArrayList;

public interface Solution {

	@FunctionalInterface
	public static interface SolutionCreator {

		Solution create(ArrayList<Item> items, int capacity);

	}

	int solve();

}
