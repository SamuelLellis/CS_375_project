package knapsack;

public class Item implements Comparable<Item> {

	private final int profit; //The profit/value of the item
	private final int weight; //The weight of the item
	private final double ratio; //The profit to weight ratio of the item

	/**
	 * Creates a new Item with the specified profit and weight values
	 *
	 * @param profit
	 *            the profit/value of the item
	 * @param weight
	 *            the weight of the item
	 */
	public Item(int profit, int weight) {
		this.profit = profit;
		this.weight = weight;
		this.ratio = (double) profit / weight;
	}

	/**
	 * Comparator implementation for easy sorting of items by their profit
	 * to weight ratio. Item is another Item to
	 * compare to this one
	 */
	@Override
	public int compareTo(Item o) {
		return Double.compare(o.ratio, ratio);
	}

	/**
	 * Returns a string representation of the Item for debugging purposes
	 */
	@Override
	public String toString() {
		return "[" + profit + "/" + weight + "=" + String.format("%.2f", ratio) + "]";
	}

	public int getProfit() {
		return profit;
	}

	public int getWeight() {
		return weight;
	}

	public double getRatio() {
		return ratio;
	}

}
