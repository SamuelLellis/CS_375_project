package knapsack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(args[0])); //A list of the lines in the input file
		String[] parts = lines.get(0).split(","); //The first line, split on commas

		final int itemCount = Integer.parseInt(parts[0]); //The n value from the input files
		ArrayList<Item> items = new ArrayList<>(itemCount); //The list of items to build
		for (int i = 1; i <= itemCount; i++) {
			String[] line = lines.get(i).split(","); //The current line, split on commas
			items.add(new Item(Integer.parseInt(line[1]), Integer.parseInt(line[0])));
		}
		//		int best = new BestFirstSearch(items, Integer.parseInt(parts[1])).search(Paths.get(args[1]));
	}

}
