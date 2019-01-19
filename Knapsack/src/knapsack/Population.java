package knapsack;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Representation of a population
 * @author ngadzheva
 */
public class Population {
    private ArrayList<Individual> population;
    private int fitness;
    
    public Population(){
        population = new ArrayList<>();
        fitness = 0;
    }
    
    /**
     * Get the population
     * 
     * @return the population
     */
    public ArrayList<Individual> getPopulation(){
        return population;
    }
    
    /**
     * Set fitness value
     * 
     * @param populationFitness - fitness value to set
     */
    public void setFitness(int populationFitness){
        this.fitness = populationFitness;
    }
    
    /**
     * Get the fitness value
     * 
     * @return fitness
     */
    public int getFitness(){
        return fitness;
    }
    
    /**
     * Add new individual to the population
     * 
     * @param individual - individual to add
     */
    public void addIndividual(Individual individual){
        population.add(individual);
        //sort();
    }
    
    /**
     * Check whether individual is already in the population
     * 
     * @param individual - individual to be checked
     * @return whether individual is already in the population
     */
    public boolean conatins(Individual individual){
        return population.contains(individual);
    }
    
    /**
     * Sort the individuals in the population by their fitness value
     */
    public void sort(){
        population.sort(new Comparator<Individual>(){
            @Override
            public int compare(Individual individual1, Individual individual2) {
                return individual1.getFitness() - individual2.getFitness();
            }
            
        });
    }
}
