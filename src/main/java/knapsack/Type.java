package knapsack;

import knapsack.Solution.SolutionCreator;
import knapsack.impl.Backtracking;
import knapsack.impl.BestFirstSearch;
import knapsack.impl.BruteForce;

public enum Type {

	BRUTE_FORCE(BruteForce::new),
	BACKTRACKING(Backtracking::new),
	//	BACKTRACKING_ITERATIVE(BestFirstSearch::new),
	BEST_FIRST(BestFirstSearch::new),

	;

	private SolutionCreator creator;

	private Type(SolutionCreator creator) { this.creator = creator; }

	public SolutionCreator getCreator() {
		return creator;
	}

}
