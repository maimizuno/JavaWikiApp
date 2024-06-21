package wikiportal.model.dao;  // Package declaration

import java.sql.*;  // Import SQL classes
import java.util.*;  // Import utility classes like List and ArrayList
import java.time.LocalDate;  // Import LocalDate for handling date operations

import wikiportal.model.bean.Category;
import wikiportal.model.bean.User;  // Import User class from the bean package

public class UserDAO {  // Article Data Access Object class

    // Database connection details
	private String DBURL = "jdbc:mysql://localhost:3306/wikidb?serverTimezone=Australia/Melbourne";
	private String DBUsername = "root";
	private String DBPassword = "bit235mysql";

    // SQL query strings
	private String COUNTUSERS = "SELECT COUNT(*) FROM users WHERE username = ?";
	private String INSERTUSER = "INSERT INTO users (username, password) VALUES (?, MD5(?))";
	private String SELECTUSERBYUSERNAMEANDPASSWORD = "SELECT admin_id, username, password FROM users WHERE username = ? AND password = MD5(?)";

    // Default constructor
	public UserDAO() {
	}

    // Establishes database connection
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  // Load JDBC driver
			connection = DriverManager.getConnection(DBURL, DBUsername, DBPassword);  // Establish connection
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();  // Handle SQL and ClassNotFound exceptions
		}
		return connection;
	}

    public User login(String uName, String pwd) {
        User user = null;
        try (Connection conn = getConnection();
             PreparedStatement pstm = conn.prepareStatement(SELECTUSERBYUSERNAMEANDPASSWORD)) {
            pstm.setString(1, uName);
            pstm.setString(2, pwd);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    user = extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public boolean usernameExists(String username) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(COUNTUSERS)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // return true if count is greater than 0
                }
            }
        }
        return false;
    }
    
    public void registration(String uName, String pwd) throws SQLException {
        try (Connection conn = getConnection();
            PreparedStatement pstm = conn.prepareStatement(INSERTUSER)) {
            pstm.setString(1, uName);
            pstm.setString(2, pwd);
            System.out.println(pstm);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    // helper method to extract article data from ResultSet
	private User extractUserFromResultSet(ResultSet rs) throws SQLException {
		int adminId = rs.getInt("admin_id");
		String uName = rs.getString("username");
		String pwd = rs.getString("password");
		return new User(adminId, uName, pwd);
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
