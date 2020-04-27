package knapsack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import knapsack.bfs.BestFirstSearch;
import knapsack.bfs.backtrack.Backtrack;

public class Main {

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("inputs.txt")); //A list of the lines in the input file
		int capacity = Integer.parseInt(lines.get(0).trim());

		ArrayList<Item> items = new ArrayList<>(); //The list of items to build
		for (int i = 1; i < lines.size(); i++) {
			String[] parts = lines.get(i).split(","); //The current line, split on commas
			items.add(new Item(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
		}
		System.out.println(new BestFirstSearch(items, capacity).search());
		System.out.println(new Backtrack(items, capacity).search());
	}

}
