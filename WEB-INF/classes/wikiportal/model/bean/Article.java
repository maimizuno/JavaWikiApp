package wikiportal.model.bean;

import java.time.LocalDate; // Import LocalDate class

public class Article {  // Start of the Article class definition

    // declaration
    protected int aId;          
    protected String aTitle;    
    protected String aContent;  
    protected LocalDate publishDate; 
    protected int cId; 
    protected boolean isHidden;

    // Default constructor that initializes no values
    public Article() {
    }

    // Constructor for creating an article with a title and content only
    public Article(String aTitle, String aContent) {
        this.aTitle = aTitle;
        this.aContent = aContent;
    }
    
    // Constructor that encapsulates all fields except for article ID
    public Article(String aTitle, String aContent, int cId, LocalDate publishDate, boolean isHidden) {           
        this.aTitle = aTitle;       
        this.aContent = aContent;  
        this.cId = cId;  
        this.publishDate = publishDate;
        this.isHidden = isHidden;
    }
    
    // Constructor that initializes all fields except for publish date
    public Article(int aId, String aTitle, String aContent, int cId, boolean isHidden) {
        this.aId = aId;              
        this.aTitle = aTitle;       
        this.aContent = aContent;  
        this.cId = cId;  
        this.isHidden = isHidden;
    }

    // Constructor that initializes all fields 
    public Article(int aId, String aTitle, String aContent, LocalDate publishDate, int cId, boolean isHidden) {
        this.aId = aId;              
        this.aTitle = aTitle;      
        this.aContent = aContent;   
        this.publishDate = publishDate; 
        this.cId = cId;
        this.isHidden = isHidden;
    }
    
    // Get method for article ID
    public int getId() {
        return aId;
    }

    // Set method for article ID
    public void setId(int aId) {
        this.aId = aId;
    }

    // Get method for article title
    public String getTitle() {
        return aTitle;
    }

    // Set method for article title
    public void setTitle(String aTitle) {
        this.aTitle = aTitle;
    }

    // Get method for article content
    public String getContent() {
        return aContent;
    }

    // Set method for article content
    public void setContent(String aContent) {
        this.aContent = aContent;
    }
    
    // Get method for publish date
    public LocalDate getPublishDate() {
        return publishDate;
    }

    // Set method for publish date with validation
    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }
    
    // Get method for category id
    public int getcId() {
        return cId;
    }
    
    // Set method for category id
    public void setCategory(int cId) {
        this.cId = cId;
    }
    
    // Get method for isHidden
    public boolean getIsHidden() {
    	return isHidden;
    }
    
    // Set method for isHidden
    public void setHidden(boolean hidden) {
    	isHidden = hidden;
    }
    
}
