package wikiportal.model.bean;

public class Category {

    // declaration
    protected int cId;          
    protected String cName;    
    protected String cDescription;  


    //Default constructor that initializes no values
    public Category() {
    }
    
    // Constructor for creating an category with id and category name only
    public Category(int cId, String cName) {
        this.cId = cId;
        this.cName = cName;
    }
    
    // Constructor that initializes all fields
    public Category(int cId, String cName, String cDescription) {
        this.cId = cId;
        this.cName = cName;
        this.cDescription = cDescription;
        
    }
    
    // Get method for category ID
    public int getcId() {
        return cId;
    }
    
    // Set method for category ID
    public void setId(int cId) {
        this.cId = cId;
    }  
    
    // Get method for category name
    public String getcName() {
        return cName;
    }
    
    // Set method for category name
    public void setCname(String cName) {
        this.cName = cName;
    }  

}
