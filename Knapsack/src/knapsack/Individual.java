package knapsack;

import java.util.Arrays;

/**
 * Representation of an individual of the population
 * @author ngadzheva
 */
public class Individual {
    
    private boolean[] chromosome;
    private int totalWeight;
    private int totalBenefit;
    private int fitness;


    public Individual(boolean[] chromosome, int totalWeight, int totalBenefit) {
        setChromosome(chromosome);
        setTotalWeight(totalWeight);
        setTotalBenefit(totalBenefit);
    }

    /**
     * Get the value of totalBenefit
     *
     * @return the value of totalBenefit
     */
    public int getTotalBenefit() {
        return totalBenefit;
    }

    /**
     * Set the value of totalBenefit
     *
     * @param totalBenefit new value of totalBenefit
     */
    public void setTotalBenefit(int totalBenefit) {
        this.totalBenefit = totalBenefit;
    }


    /**
     * Get the value of totalWeight
     *
     * @return the value of totalWeight
     */
    public int getTotalWeight() {
        return totalWeight;
    }

    /**
     * Set the value of totalWeight
     *
     * @param totalWeight new value of totalWeight
     */
    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }


    /**
     * Get the value of chromosome
     *
     * @return the value of chromosome
     */
    public boolean[] getChromosome() {
        return chromosome;
    }

    /**
     * Set the value of chromosome
     *
     * @param chromosome new value of chromosome
     */
    public void setChromosome(boolean[] chromosome) {
        this.chromosome = chromosome;
    }
    
    /**
     * Get the value of fitness
     *
     * @return the value of fitness
     */
    public int getFitness() {
        return fitness;
    }

    /**
     * Set the value of fitness
     *
     * @param fitness new value of fitness
     */
    public void setFitness(int fitness) {
        this.fitness = fitness;
    }


    /**
     * Compare two Individual objects
     * 
     * @param obj - individual object to compare to current individual object
     * @return true if the two objects are equal,
     * else return false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Individual other = (Individual) obj;
        if (!Arrays.equals(this.chromosome, other.chromosome)) {
            return false;
        }
        return true;
    }
}
