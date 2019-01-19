package knn;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of K Nearest Neighbors
 * @author ngadzheva
 */
public class Classifier {
    private final int TEST_SET_LENGTH;
    private final int ATTRIBUTES_COUNT;
    private List<Instance> instances;
    private List<Instance> testSet;
    private List<Instance> trainingSet;

    public Classifier() {
        this.TEST_SET_LENGTH = 20;
        this.ATTRIBUTES_COUNT = 4;
        this.instances = new ArrayList<>();
        this.testSet = new ArrayList<>();
        this.trainingSet = new ArrayList<>();
        
        initializeData();
    }
    
    /**
     * Read data from file iris.data.txt and create test set and training set
     * The test set is 20 randomly chosen instances from the data set
     * The other instances are put in the training set
     */
    private void initializeData(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("iris.data.txt"));
            String line;
             while ((line = reader.readLine()) != null){
                String[] lineInfo = line.split(",");
                Instance instance = new Instance(lineInfo);
                instances.add(instance);
                line = reader.readLine();
            };
        } catch (FileNotFoundException ex) { 
            Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Collections.shuffle(instances);
        
        for (int i = 0; i < TEST_SET_LENGTH; i++) {
            testSet.add(instances.get(i));
        }
        
        int size = instances.size();
        
        for (int i = TEST_SET_LENGTH; i < size; i++) {
            trainingSet.add(instances.get(i));
        }
    }
    
    /**
     * Implementation of KNN
     * For every instance in the test set
     *  get its k nearest neighbors 
     *  classify it
     *  check whether the predicted class is correct
     *  print the predicted class and the real class
     * Finally, calculate the accuracy of the algorithm and print it
     * 
     * @param k - the number of neighbors
     */
    public void solve(int k){
        int correctPredictions = 0;
        
        for (Instance instance : testSet) {
            List<Instance> neighbours = getNeighbours(trainingSet, instance, k);
            String className = classify(neighbours);
            
            if(className.equals(instance.getClassName())){
                correctPredictions++;
            }
            
            System.out.printf("Prediction: %s Expected: %s%n", className, instance.getClassName());
        }
        
        System.out.println("Accuracy: " + getAccuracy(correctPredictions) + "%");
    }
    
    /**
     * Find k nearest neighbors of the current instance from the test set
     * For every instance from the training set
     *  calculate the Euclidean distance between the current instance from the
     *      training set and the current instance from the test set
     *  put the calculated distance in the distances list
     *  put the calculated distance and the current instance from the training
     *      set in the dictionary
     * Then sort the calculated distances
     * Put the first k instances with min distance from the dictionary to the 
     * list of neighbors of the current instance from the test set
     * 
     * @param trainingSet
     * @param testInstance
     * @param k - the number of neighbors to find
     * 
     * @return list with the k nearest neighbors
     */
    private List<Instance> getNeighbours(List<Instance> trainingSet, Instance testInstance, int k){
        List<Instance> neighbours = new ArrayList<>();
        List<Double> distances = new ArrayList<>();
        HashMap<Double, Instance> dictionary = new HashMap<>();
        int size = trainingSet.size();
        
        for (int i = 0; i < size; i++) {
            double distance = getEuclideanDistance(testInstance, trainingSet.get(i));
            distances.add(distance);
            dictionary.put(distance, trainingSet.get(i));
        }
        
        Collections.sort(distances);
        
        for (int i = 0; i < k; i++) {
            neighbours.add(dictionary.get(distances.get(i)));
        }
        
        return neighbours;
    }
    
    /**
     * Compute Euclidean distance between two instances
     * 
     * @param instance1
     * @param instance2
     * @return the Euclidean distance
     */
    private double getEuclideanDistance(Instance instance1, Instance instance2){
        double distance = 0.0;
        
        for (int i = 0; i < ATTRIBUTES_COUNT; i++) {
            distance += Math.pow((instance1.getAttributes()[i] - instance2.getAttributes()[i]), 2);
        }
        
        return Math.sqrt(distance);
    }
    
    /**
     * Get the most common class in the k shorter distances
     * For every neighbor
     *  get its class and increment the number of predictions for this class
     * Find the class with the greatest predictions count
     * Return its name
     * 
     * @param neighbours
     * @return the class name
     */
    private String classify(List<Instance> neighbours){
        HashMap<String, Integer> predictions = new HashMap<>();
        
        for (Instance neighbour : neighbours) {
            Integer currentCount = predictions.get(neighbour.getClassName());
            predictions.put(neighbour.getClassName(), (currentCount == null) ? 1 : currentCount++);
        }
        
        int max  = 0;
        Collection<Integer> predictionsCount = predictions.values();

        for (Integer prediction : predictionsCount) {
            if(prediction > max){
                max = prediction;
            }
        }
        
        for (Entry<String, Integer> prediction : predictions.entrySet()) {
            String className = prediction.getKey();
            Integer classVotes = prediction.getValue();
            
            if(classVotes == max){
                return className;
            }
        }
        
        return "";
    }
    
    /**
     * Calculate the accuracy of the algorithm
     * 
     * @param predictionsCount
     * @return the accuracy
     */
    private double getAccuracy(double predictionsCount){
        return ((predictionsCount * 1.0) / (double) TEST_SET_LENGTH) * 100.0;
    }
}
