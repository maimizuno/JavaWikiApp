package wikiportal.model.bean;

public class User {

    // declaration
    protected int adminId;          
    protected String uName;    
    protected String pwd;  


    //Default constructor that initializes no values
    public User() {
    }
    
    // Constructor for creating an Users with user name and password only
    public User(String uName, String pwd) {
        this.uName = uName;
        this.pwd = pwd;
    }
    
    // Constructor that initializes all fields
    public User(int adminId, String uName, String pwd) {
        this.adminId = adminId;
        this.uName = uName;
        this.pwd = pwd;
    }
    
    // Get method for Users ID
    public int getadminId() {
        return adminId;
    }
    
    // Set method for Users ID
    public void setId(int adminId) {
        this.adminId = adminId;
    }  
    
    // Get method for Users name
    public String getuName() {
        return uName;
    }
    
    // Set method for Users name
    public void setuName(String uName) {
        this.uName = uName;
    }  
    
 // Get method for password
    public String getPwd() {
        return pwd;
    }

    // Set method for password
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }  
}
