package naivebayes;

/**
 * Instance of the data set
 * @author ngadzheva
 */
public class Instance {
    
    private String className;
    private String[] attributes;

    public Instance(String className, String[] attributes) {
        this.attributes = new String[16];
        setClassName(className);
        setAttributes(attributes);
    }

    /**
     * Get the value of attributes
     *
     * @return the value of attributes
     */
    public String[] getAttributes() {
        return attributes;
    }

    /**
     * Set the value of attributes
     *
     * @param attributes new value of attributes
     */
    public void setAttributes(String[] attributes) {
        this.attributes = attributes;
    }


    /**
     * Get the value of className
     *
     * @return the value of className
     */
    public String getClassName() {
        return className;
    }

    /**
     * Set the value of className
     *
     * @param className new value of className
     */
    public void setClassName(String className) {
        this.className = className;
    }

}
