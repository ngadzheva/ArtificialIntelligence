package knn;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * K Nearest Neighbors
 * @author ngadzheva
 */
public class KNN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter k: ");
        int k = input.nextInt();
        
        Classifier classifier = new Classifier();
        classifier.solve(k);
    }
    
}
