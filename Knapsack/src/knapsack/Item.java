package knapsack;

/**
 * Representation of one item
 * @author ngadzheva
 */
public class Item {
    
    private int weight;
    private int benefit;

    public Item(int weight, int benefit) {
        setWeight(weight);
        setBenefit(benefit);
    }

    /**
     * Get the value of benefit
     *
     * @return the value of benefit
     */
    public int getBenefit() {
        return benefit;
    }

    /**
     * Set the value of benefit
     *
     * @param benefit new value of benefit
     */
    public void setBenefit(int benefit) {
        this.benefit = benefit;
    }


    /**
     * Get the value of weight
     *
     * @return the value of weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Set the value of weight
     *
     * @param weight new value of weight
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

}
