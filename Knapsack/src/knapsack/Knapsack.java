package knapsack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Solution of Knapsack problem
 * @author ngadzheva
 */
public class Knapsack {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {            
            Scanner input = new Scanner(new File("KP long test data.txt"));
            
            String[] line = input.nextLine().split(" ");
            int totalWeight = Integer.parseInt(line[0]);
            int numOfItems = Integer.parseInt(line[1]);
            ArrayList<Item> allItems = new ArrayList<>();
            
            while(input.hasNext()){
                line = input.nextLine().split(" ");
                allItems.add(new Item(Integer.parseInt(line[0]), Integer.parseInt(line[1])));
            }
            
            GeneticAlgorithm ga = new GeneticAlgorithm(allItems, numOfItems, totalWeight);
            ga.solve();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        }
    }
    
}
