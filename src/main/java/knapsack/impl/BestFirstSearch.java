package knapsack.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

import knapsack.Item;
import knapsack.Solution;

/**
 * A class that takes in an instance of a 0/1 knapsack problem and can find and
 * write the optimal solution to a file
 */
public class BestFirstSearch implements Solution {

	private ArrayList<Item> items; //The list of items to search
	private int capacity; //The maximum total weight for this search

	private int visited;
	private ArrayList<Item> solution;

	/**
	 * Creates a new {@link BestFirstSearch} instance with the corresponding
	 * items and capacity. The constructor sorts
	 * the items to ensure proper functionality of the class and uses the
	 * original list so the input list should not be
	 * modified after calling this method
	 *
	 * @param items
	 *            the list of items to search
	 * @param capacity
	 *            the maximum total weight for this search
	 */
	public BestFirstSearch(ArrayList<Item> items, int capacity) {
		Collections.sort(items);
		this.items = items;
		this.capacity = capacity;
	}

	/**
	 * Searches the classes items to final the optimal solution and write the
	 * search results to the output file
	 *
	 * @param outputFile
	 *            the path of the file to write the search results to
	 * @return the actual maximum profit
	 */
	@Override
	public int solve() {
		Node best = new Node(null, false, 0, 0, 0, computeBound(0, 0)); //A variable representing the node with the best profit found so far, initially set to the root of the tree

		PriorityQueue<Node> queue = new PriorityQueue<>(); //A priority queue for storing every encountered Node
		queue.add(best);

		//		int visited = 0; //A counter for the number of visited nodes
		//		int leaves = 0; //A counter for the number of visited leaf nodes

		while (!queue.isEmpty()) {
			Node parent = queue.poll(); //Extract the highest priority node from the queue and store it in parent
			visited++;

			if (parent.bound < best.profit || parent.weight > capacity) { //NOTE: WE MUST ADD TO THE QUEUE EVEN IF THIS BOUND ONLY EQUALS THE PROFIT
				//				leaves++;
				continue;
			}

			if (parent.index < items.size()) {
				Item item = items.get(parent.index);
				Node include = new Node(parent, true, parent.index + 1, parent.profit + item.getProfit(), parent.weight + item.getWeight(), parent.bound); //A child node of the parent representing the inclusion of the parent's item
				Node exclude = new Node(parent, false, parent.index + 1, parent.profit, parent.weight, parent.profit + computeBound(parent.index + 1, parent.weight)); //A child node of the parent representing the exclusion of the parent's item
				//				if (include.bound >= best.profit && include.weight <= capacity) { //NOTE: WE MUST ADD TO THE QUEUE EVEN IF THIS BOUND ONLY EQUALS THE PROFIT
				//					queue.add(include);
				//				}
				//				if (exclude.bound >= best.profit) { //Don't need to check weight here because it's just the parent's weight
				//					queue.add(exclude);
				//				}
				queue.add(include);
				queue.add(exclude);
			}
			//			else {
			//				leaves++;
			//			}

			if (parent.profit > best.profit) {
				best = parent;
			}
		}

		ArrayList<Item> solution = new ArrayList<>(); //A list of the Items which are included in the optimal solution
		Node current = best; //A temporary Node variable for iterating through the `best` varaible's parents until arriving at the root
		while (current != null) {
			if (current.include) {
				solution.add(0, items.get(current.index - 1));
			}
			current = current.parent;
		}

		//		if (outputFile != null) {
		//			StringBuilder sb = new StringBuilder(); //A string builder for building the output which should be written to the output file
		//			sb.append(items.size() + "," + best.profit + "," + solution.size() + "\n");
		//			sb.append(visited + "," + leaves + "\n");
		//			for (Item item : solution) { //The item variable is each element in the solution list
		//				sb.append(item.getWeight() + "," + item.getProfit() + "\n");
		//			}
		//			try {
		//				Files.write(Paths.get("output.txt"), sb.toString().getBytes());
		//			} catch (IOException e) {
		//				e.printStackTrace();
		//			}
		//		}

		return best.profit;
	}

	public int getVisited() {
		return visited;
	}

	public ArrayList<Item> getSolution() {
		return solution;
	}

	/**
	 * Computes the bound for node by using the starting level/index and weight
	 *
	 * @param startingIndex
	 *            the index into the items array at which to start
	 * @param weight
	 *            the initial weight of the computation
	 * @return the bound value for the node specified by the above index and
	 *         weight parameters
	 */
	private int computeBound(int startingIndex, int weight) {
		int bound = 0; //The current calculated bound so far
		for (int i = startingIndex; i < items.size(); i++) {
			Item item = items.get(i); //The item at index i
			if (weight + item.getWeight() <= capacity) {
				bound += item.getProfit();
				weight += item.getWeight();
			} else {
				bound += item.getProfit() * ((capacity - weight) / (float) item.getWeight());
				break;
			}
		}
		return bound;
	}

	/**
	 * A node class for represenating
	 */
	public static class Node implements Comparable<Node> {

		Node parent; //This Node's parent Node
		boolean include; //Whether or not the parent nodes item was included

		int index; //The index of the item which this node represents
		int profit; //The accumulated profit for this node
		int weight; //The accumulated weight for this node
		int bound; //The maximum possible profit for this node as computed by the computeBound method

		/**
		 * Creates a new Node object
		 *
		 * @param parent
		 *            the parent node of the Node to create
		 * @param include
		 *            whether or not this node represents the inclusion of
		 *            parent node's item
		 * @param index
		 *            the index of the item which this node represents
		 * @param profit
		 *            the accumulated profit for this node
		 * @param weight
		 *            the accumulated weight for this node
		 * @param bound
		 *            the maximum profit for this node as computed by the
		 *            computeBound method
		 */
		public Node(Node parent, boolean include, int index, int profit, int weight, int bound) {
			this.parent = parent;
			this.include = include;
			this.index = index;
			this.profit = profit;
			this.weight = weight;
			this.bound = bound;
		}

		/**
		 * Comparator implementation for easy sorting of Nodes by their bound.
		 * This is used by the priority queue to
		 * determine Node priority. Node is another Node to compare to the
		 * current instance
		 */
		@Override
		public int compareTo(Node node) {
			return node.bound - bound;
		}

		/**
		 * Returns a string representation of the Node for debugging purposes
		 */
		@Override
		public String toString() {
			return "[" + profit + " " + weight + " " + bound + "]";
		}

	}

}
