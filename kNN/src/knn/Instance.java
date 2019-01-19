package knn;

/**
 * Instance of the data set
 * @author ngadzheva
 */
public class Instance {
    
    private double[] attributes;
    private String instanceClass;

    public Instance(double[] attributes, String className) {
        setAttributes(attributes);
        setClassName(className);
    }

    public Instance(String[] lineInfo) {
        this.attributes = new double[4];
        
        this.attributes[0] = Double.parseDouble(lineInfo[0]);
        this.attributes[1] = Double.parseDouble(lineInfo[1]);
        this.attributes[2] = Double.parseDouble(lineInfo[2]);
        this.attributes[3] = Double.parseDouble(lineInfo[3]);
        
        this.instanceClass = lineInfo[4];
    }

    /**
     * Get the value of className
     *
     * @return the value of className
     */
    public String getClassName() {
        return instanceClass;
    }

    /**
     * Set the value of className
     *
     * @param className new value of className
     */
    public void setClassName(String className) {
        this.instanceClass = className;
    }


    /**
     * Get the value of attributes
     *
     * @return the value of attributes
     */
    public double[] getAttributes() {
        return attributes;
    }

    /**
     * Set the value of attributes
     *
     * @param attributes new value of attributes
     */
    public void setAttributes(double[] attributes) {
        this.attributes = new double[4];
        
        this.attributes = attributes;
    }

}
