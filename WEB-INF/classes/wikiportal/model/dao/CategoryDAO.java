package wikiportal.model.dao;  // Package declaration

import java.sql.*;  // Import SQL classes
import java.util.*;  // Import utility classes like List and ArrayList

import wikiportal.model.bean.Category;  // Import Category class from the bean package
public class CategoryDAO {  // Category Data Access Object class

    // Database connection details
	private String DBURL = "jdbc:mysql://localhost:3306/wikidb?serverTimezone=Australia/Melbourne";
	private String DBUsername = "root";
	private String DBPassword = "bit235mysql";

    // SQL query strings
	private String SELECTCATEGORIES = "SELECT c_id, c_name FROM categories ORDER BY c_name ASC";
	private String SELECTCATEGORYNAME = "SELECT c_name FROM categories WHERE c_id = ?";
	private String UPDATECATEGORYNAME = "UPDATE categories SET c_name = ? WHERE c_id = ?";
	private String DELETECATEGORY = "DELETE FROM categories WHERE c_id = ?;";
	private String UPDATECID = "UPDATE articles SET c_id = (SELECT c_id FROM categories WHERE c_name = 'Unknown') WHERE c_id = ?;";
	private String INSERTCATEGORY = "INSERT INTO categories (c_name) VALUES (?);";


    // Default constructor
	public CategoryDAO() {
	}

    // Establishes database connection
	protected Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  // Load JDBC driver
			conn = DriverManager.getConnection(DBURL, DBUsername, DBPassword);  // Establish connection
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();  // Handle SQL and ClassNotFound exceptions
		}
		return conn;
	}
	
    // Retrieves a single category name by ID
	public String selectCategoryId(int categoryId) {
		String cName = null;
		try (Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(SELECTCATEGORYNAME)) {
			pstm.setInt(1, categoryId);
			System.out.println(pstm);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				cName = rs.getString("c_name");
			} else {
				System.out.println("No category found with ID: " + categoryId);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return cName;
	}


    // list all unique categories
	public List<Category> selectCategories() {
	    List<Category> categories = new ArrayList<>();
	    try (Connection conn = getConnection();
	         PreparedStatement preparedStatement = conn.prepareStatement(SELECTCATEGORIES);
	         ResultSet rs = preparedStatement.executeQuery()) {
	        while (rs.next()) {
	        	int cId = rs.getInt("c_id");
	            String categoryName = rs.getString("c_name");
	            categories.add(new Category(cId, categoryName));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return categories;
	}
	
	// update category name in the database
	public boolean updateCategoryName(int cId, String newCName) throws SQLException {
	    boolean categoryUpdated = false;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	    	conn = getConnection();
	    	pstmt = conn.prepareStatement(UPDATECATEGORYNAME);
	        pstmt.setString(1, newCName);
	        pstmt.setInt(2, cId);

	        int affectedRows = pstmt.executeUpdate();
	        categoryUpdated = affectedRows > 0;
	    } catch (SQLException e) {
	        System.err.println("SQLException: " + e.getMessage());
	        throw e;
	    }
	    return categoryUpdated;
	}
	
	// create category name in the database
	public boolean createCategory(String cName) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERTCATEGORY)) {
            pstmt.setString(1, cName);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new SQLException("Error creating category: " + e.getMessage(), e);
        }
    }
	
	
	// delete category name in the database
	public void deleteCategory(int cId) throws SQLException {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	        conn = getConnection(); // method to establish a database connection
	        // First, reassign articles
	        pstmt = conn.prepareStatement(UPDATECID);
	        pstmt.setInt(1, cId);
	        pstmt.executeUpdate();
	        
	        // Then, delete the category
	        pstmt = conn.prepareStatement(DELETECATEGORY);
	        pstmt.setInt(1, cId);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.err.println("SQLException: " + e.getMessage());
	        throw e;
	    }
	}


    // prints details of SQL exceptions
	private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    // helper method to close SQL connections and handles
	private void finallySQLException(Connection c, PreparedStatement p, ResultSet r){
		if (r != null)	{
            try {
               r.close();
            } catch (Exception e) {}
               r = null;
         }
	
        if (p != null) {
            try {
               p.close();
            } catch (Exception e) {}
               p = null;
        }
	
        if (c != null) {
            try {
               c.close();
            } catch (Exception e) {
          	  c = null;
            }
        }
   }
}
