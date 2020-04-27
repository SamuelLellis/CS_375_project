import java.util.ArrayList; //Import ArrayList
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.lang.String; //Split functionality 

public class backtrack{
	public static int backTrack(ArrayList<Integer> profits, ArrayList<Integer> weights, int numItems, int totWeight){
		//Base case for recursive call; Indicates negative capcity
		//ArrayList<Integer> finalTree = new ArrayList<Integer>();
		if(totWeight < 0){
			return Integer.MIN_VALUE;
		}
		//Base case 2; No items or no more capcity 
		if(numItems < 0 || totWeight == 0){
			return 0;
		}
		//First case: Include the current item in knapsack and recur for the rest of items
		int include = profits.get(numItems) + backTrack(profits,weights,numItems-1, totWeight - weights.get(numItems));
		
		//Second case: Exclude current item and recur for remaining 
		int exclude = backTrack(profits,weights, numItems-1, totWeight);
		 
		return Integer.max(include,exclude);
	}

	public static void main(String[] args){
		//Containers for holding profits and weights 
		ArrayList<Integer> profits = new ArrayList<Integer>();
		ArrayList<Integer> weights = new ArrayList<Integer>();
		ArrayList<Integer> finalTree = new ArrayList<Integer>();
		
		int capacity = 0;
		//Read in profits,weights and capcity from exterior file 
		try{
			File myObj = new File("inputs.txt");
			Scanner reader = new Scanner(myObj);
			capacity = Integer.parseInt(reader.nextLine());
			while(reader.hasNextLine()){
				String data = reader.nextLine();
				String[] vals = data.split(",");
				profits.add(Integer.parseInt(vals[0]));
				weights.add(Integer.parseInt(vals[1]));
			}
			reader.close();
		}
		catch (FileNotFoundException e){
			System.out.println("File not Found...");
			e.printStackTrace();
		}
		System.out.println("Optimal profit: " + backTrack(profits,weights,profits.size() - 1, capacity));
	}
}

