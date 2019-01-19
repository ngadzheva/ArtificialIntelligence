package naivebayes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of Naive Bayes
 * @author ngadzheva
 */
public class Classifier {
    private final int DATA_SET_LENGTH;
    private final int LINE_LENGTH;
    private final int ATTRIBUTES_COUNT;
    private List<Instance> instances;
    private List<Instance> testSet;
    private List<List<Instance>> validationSet;
    private Map<String, Map<String, Integer>> model;
    
    public Classifier() {
        this.DATA_SET_LENGTH = 435;
        this.LINE_LENGTH = 17;
        this.ATTRIBUTES_COUNT = 16;
        this.instances = new ArrayList<>();
        this.testSet = new ArrayList<>();
        this.validationSet = new ArrayList<>();
        this.model = new HashMap<>();
        
        initializeData();
    }
    
    /**
     * Classify the instances from the dataset, using Naive Bayes Classifier
     */
    public void classify(){initializeData();
        
        double accuracySum = 0.0;
        
        for (int i = 0; i < 10; i++) {
            initializeData();
            separateData();
            makeModel(instances);
            double accuracy = train();
            System.out.printf("Accuracy: %.2f%s%n", 100 * accuracy, "%");
            accuracySum += accuracy;
        }
      
        System.out.printf("Total: %.2f%s%n", 100 * (accuracySum / 10.0), "%");
    }
    
    /**
     * Read data from file house-votes-84.data.txt 
     */
    private void initializeData(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("house-votes-84.data.txt"));
            String line;
             while ((line = reader.readLine()) != null){
                String[] lineInfo = line.split(",");
                String[] attributes = new String[LINE_LENGTH - 1];
                String className = lineInfo[0];
                
                 for (int i = 1; i < LINE_LENGTH; ++i) {
                     if(lineInfo[i].equals("y")){
                        attributes[i - 1] = "y";
                     } else if(lineInfo[i].equals("n")){
                        attributes[i - 1] = "n";
                     } else {
                        attributes[i - 1] = "?";
                     }
                 }
                 
                 Instance instance = new Instance(className, attributes);
                 instances.add(instance);
                
                line = reader.readLine();
            };
        } catch (FileNotFoundException ex) { 
            Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Classifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Split the data set to ten subsets, including:
     *      - a test set
     *      - a validation set
     * using 10-fold cross validation
     * For every k from 0 to 9
     * the first 9 instances are put to the validation set
     * the last instance is put to the test set
     */
    private void separateData(){
        Collections.shuffle(instances);
        int oneTenth = DATA_SET_LENGTH / 10;
        int k = 0;
        
        for (int i = 0; i < 10; ++i) {
            if(i == 9){
                for (int j = k; j < oneTenth; ++j) {
                    testSet.add(instances.get(j));
                }
            } else {
                validationSet.add(new ArrayList<>());
                
                for (int j = k; j < oneTenth; ++j) {
                    validationSet.get(i).add(instances.get(j));
                }
                
                k = oneTenth;
                oneTenth += DATA_SET_LENGTH / 10;
            }
        }
    }
    
    /**
     * Make data model for the validation set
     * For every instance from the validation set
     * count the votes for every feature of the instance
     * 
     * @param validationData - the validation set
     */
    private void makeModel(List<Instance> validationData){
        for (Instance instance : validationData) {
            String className = instance.getClassName();
            Map<String, Integer> votes;
            
            if(!model.containsKey(className)){
                model.put(className, new HashMap<>());
                votes = model.get(className);
                for (int i = 0; i < LINE_LENGTH - 1; i++) {
                    votes.put(instance.getAttributes()[i], 1);
                }
            } else {
                votes = model.get(className);
            }
            
            String[] attributes = instance.getAttributes();
            for (int i = 0; i < ATTRIBUTES_COUNT; i++) {
                if(votes.get(attributes[i]) == null){
                    votes.put(attributes[i], 1);
                } else {
                    int votesCount = votes.get(attributes[i]) + 1;
                    votes.put(attributes[i], votesCount);
                }  
            }
        }
    }
    
    /**
     * Train the algorithm
     * For every instance from the test set
     *      classify the instance from the test set
     *      If the predicted class is correct
     *      increment the correctPredictions counter
     * Compute the average number of correct predictions
     * 
     * @return the average number of correct predictions
     */
    private double train(){
        int correctPredictions = 0;
        
        for (Instance instance : testSet) {
            String predictedClass = classification(instance);
            if(instance.getClassName().equals(predictedClass)){
                correctPredictions++;
            }
        }
        
        return correctPredictions * 1.0 / testSet.size();
    }
    
    /**
     * Classify an instance, using the data model
     * 
     * @param instance
     * 
     * @return the predicted class
     */
    private String classification(Instance instance){
        String bestName = "";
        double bestProb = Double.MIN_VALUE;
        
        for (Map.Entry<String, Map<String, Integer>> entry : model.entrySet()) {
            String key = entry.getKey();
            Map<String, Integer> value = entry.getValue();
            
            double prob = calculateProbability(instance, value);
            
            if(bestProb < prob){
                bestProb = prob;
                bestName = key;
            }
        }
        
        return bestName;
    }
    
    /**
     * Compute the probability
     * 
     * @param instance
     * @param value
     * 
     * @return probability
     */
    private double calculateProbability(Instance instance, Map<String, Integer> value){
        String[] attributes = instance.getAttributes();
        double probability = 1.0;
        int total = value.get("y") + value.get("n") + value.get("?");
        for (int i = 0; i < ATTRIBUTES_COUNT; ++i) {
            probability *= (1.0 * value.get(attributes[i])) / total;
        }
        
        return probability;
    }
}
