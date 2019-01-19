package knapsack;

import java.util.ArrayList;
import java.util.Random;

/**
 * Implementation of Genetic Algorithm
 * @author ngadzheva
 */
public class GeneticAlgorithm {
    private ArrayList<Item> allItems;
    private int numOfItems;
    private int napsackCapacity;
    private Population population;
    private Random random;
    private int generationsCount;
    private int noImprovementCount;
    private int maxBenefit;
    
    public GeneticAlgorithm(ArrayList<Item> allItems, int numOfItems, int napsackCapacity){
        this.allItems = allItems;
        this.numOfItems = numOfItems;
        this.napsackCapacity = napsackCapacity;
        this.population = new Population();
        random = new Random();
        generationsCount = 0;
        noImprovementCount = 0;
        maxBenefit = 0;
    }
    
    /**
     * Implementation of genetic algorithm
     *  1. Generate initial population
     *  2. Evaluate the population
     *  3. Crossover
     *  4. Mutate
     */
    public void solve(){
        generatePopulation();
        
        Individual parent1 = new Individual(null, 0, 0);
        int maxIterations = allItems.size() * allItems.size();
        
        for(int i = 0; i < maxIterations; ++i){
            evalPopulation();
            population.sort();
            parent1 = getMostProbable(population.getPopulation());
            crossover(parent1);
            
            if(noImprovementCount > 3 * generationsCount){
                break;
            }
        }

        System.out.println(maxBenefit);
    }
    
    /**
     * Generate initial population
     */
    private void generatePopulation(){
        for (int i = 0; i < 100; ++i) {
            Individual individual = generateIndividual();
            
            if(!population.conatins(individual)){
                while(individual.getTotalWeight() > napsackCapacity) {
                    mutate(individual);
                } 
                
                if(!population.conatins(individual)){
                    population.addIndividual(individual);
                    ++generationsCount;
                    
                    if(individual.getTotalBenefit() > maxBenefit){
                        maxBenefit = individual.getTotalBenefit();
                    }
                    
                    if(generationsCount == 10){
                        System.out.println(maxBenefit);
                    }
                }
            }
        }
    }
    
    /**
     * Generate random individual
     * 
     * @return the generated individual
     */
    private Individual generateIndividual(){
        int totalBenefit = 0;
        int totalWeight = 0;
        
        boolean[] chromosome = new boolean[numOfItems];
        
        for (int i = 0; i < numOfItems; ++i) {
            Item item = allItems.get(i);
            
            chromosome[i] = random.nextBoolean();
            
            if(chromosome[i]){
                totalBenefit += item.getBenefit();
                totalWeight += item.getWeight();
            }
        }
        
        return new Individual(chromosome, totalWeight, totalBenefit);
    }
    
    /**
     * Evaluate the total fitness of the population
     */
    private void evalPopulation(){
        int totalFitness = 0;
        ArrayList<Individual> individuals = population.getPopulation();
        
        for (Individual individual : individuals) {
            int individualFitness = fitnessFunction(individual);
            totalFitness += individualFitness;
            individual.setFitness(individualFitness);
        }
        
        population.setFitness(totalFitness);
    }
    
    /**
     * Fitness function calculates the fitness of every individual - the sum of
     * the total benefit and total weight of each individual
     * 
     * @param individual - individual to calculate the fitness value
     * @return the fitness value
     */
    private int fitnessFunction(Individual individual){
        return individual.getTotalBenefit() + individual.getTotalWeight();
    }
    
    /**
     * Get the most probable individual to crossover
     * 
     * @param individuals - the individuals in the population
     * @return the individual with highest fitness value
     */
    private Individual getMostProbable(ArrayList<Individual> individuals){        
        return individuals.get(individuals.size() - 1);
    }
     
    /**
     * Implementation of one-point crossover
     * Select two parents to crossover. Select random crossover point
     * The child includes the chromosome values, less than the crossover point,
     * of the first parent and the chromosome values, greater than the crossover
     * point, of the second parent
     * @param parent1 
     */
    private void crossover(Individual parent1){       
        Individual parent2 = selectParent();
        
        while(parent1.equals(parent2)){
            parent2 = selectParent();
        }
        
        boolean[] parent1Chromosome = parent1.getChromosome();
        boolean[] parent2Chromosome = parent2.getChromosome();
        boolean[] child1Chromosome = new boolean[numOfItems];
        int child1Weight = 0;
        int child1Benefit = 0;
        boolean[] child2Chromosome = new boolean[numOfItems];
        int child2Weight = 0;
        int child2Benefit = 0;
        int crossoverPoint = random.nextInt(numOfItems);
        
        for (int i = 0; i < numOfItems; ++i) {
            if(i <= crossoverPoint){
                child1Chromosome[i] = parent1Chromosome[i];
                child2Chromosome[i] = parent2Chromosome[i];
            } else {
                child1Chromosome[i] = parent2Chromosome[i];
                child2Chromosome[i] = parent1Chromosome[i];
            }
            
            if(child1Chromosome[i]){
                    child1Benefit += allItems.get(i).getBenefit();
                    child1Weight += allItems.get(i).getWeight();
            }
            
            if(child2Chromosome[i]){
                    child2Benefit += allItems.get(i).getBenefit();
                    child2Weight += allItems.get(i).getWeight();
            }
        }
        
        Individual child1 = new Individual(child1Chromosome, child1Weight, child1Benefit);
        Individual child2 = new Individual(child2Chromosome, child2Weight, child2Benefit);
        
        child1.setFitness(fitnessFunction(child1));
        child2.setFitness(fitnessFunction(child2));
        
        int highestFitness = getMostProbable(population.getPopulation()).getFitness();
        
        if(child1.getFitness() < highestFitness || child2.getFitness() < highestFitness){
            ++noImprovementCount;
        } else {          
            --noImprovementCount;
        }
        
        if(!population.conatins(child1)){
            while(child1.getTotalWeight() > napsackCapacity) {
                mutate(child1);
            } 
            
            if(!population.conatins(child1)){
                population.addIndividual(child1);
                ++generationsCount;

                if(child1Benefit > maxBenefit){
                    maxBenefit = child1Benefit;
                }
                
                if(generationsCount == 150 || generationsCount == 200 ||
                        generationsCount == 250){
                    System.out.println(maxBenefit);
                }
            }
        } 
        
        if(!population.conatins(child2)){
            while(child2.getTotalWeight() > napsackCapacity) {
                mutate(child2);
            } 
            
            if(!population.conatins(child2)){
                population.addIndividual(child2);
                ++generationsCount;
                
                if(child2Benefit > maxBenefit){
                    maxBenefit = child2Benefit;
                }
                
                if(generationsCount == 150 || generationsCount == 200 ||
                        generationsCount == 250){
                    System.out.println(maxBenefit);
                }
            }
        } 
    }
    
    /**
     * Implementation of Roulette-wheel selection
     * Give each individual a slice of a circular roulette wheel equal in 
     * area to the individual’s fitness. The wheel is spun N times, where N is 
     * the number of the individuals in the population.  On each spin, the 
     * individual under wheel’s marker is selected to be in the pool of parents 
     * for the next generation
     * 
     * @return selected parent individual
     */
    private Individual selectParent(){
        ArrayList<Individual> individuals = population.getPopulation();
        int populationFitness = population.getFitness();
        double rouletteWheelPosition = Math.random() * populationFitness;
        int spinWheel = 0;
        
        for (Individual individual : individuals) {
            spinWheel += individual.getFitness();
            
            if(spinWheel >= rouletteWheelPosition){
                return individual;
            }
        }
        
        return getMostProbable(individuals);
    }
    
    /**
     * Implementation of mutation
     * Select randomly indexes from the chromosome until the selected item is included
     * Then exclude it (chromosome[index] = false)
     * 
     * @param individual - individual to mutate
     */
    private void mutate(Individual individual){
        int indexToMutate;
        int benefit = individual.getTotalBenefit();
        int weight = individual.getTotalWeight();
        boolean[] chromosome = individual.getChromosome();
        
        do {
           indexToMutate = random.nextInt(numOfItems);
        } while(!chromosome[indexToMutate]);
        
        chromosome[indexToMutate] = false;
        benefit -= allItems.get(indexToMutate).getBenefit();
        weight -= allItems.get(indexToMutate).getWeight();
        
        individual.setChromosome(chromosome);
        individual.setTotalBenefit(benefit);
        individual.setTotalWeight(weight);
    }
}
