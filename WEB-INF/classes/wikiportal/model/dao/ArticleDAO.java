package wikiportal.model.dao;  // Package declaration

import java.sql.*;  // Import SQL classes
import java.util.*;  // Import utility classes like List and ArrayList

import java.time.LocalDate;  // Import LocalDate for handling date operations

import wikiportal.model.bean.Article;  // Import Article class from the bean package


public class ArticleDAO {  // Article Data Access Object class

    // Database connection details
	private String DBURL = "jdbc:mysql://localhost:3306/wikidb?serverTimezone=Australia/Melbourne";
	private String DBUsername = "root";
	private String DBPassword = "bit235mysql";

    // SQL query strings
	private String SELECTBYID = "SELECT * FROM articles WHERE a_id = ?";
	private String SELECTBYCATEGORY = "SELECT * FROM articles WHERE c_id = ? AND is_hidden = FALSE ORDER BY publish_date DESC";
	private String SEARCHARTICLES = "SELECT * FROM articles WHERE (a_title LIKE ? OR a_content LIKE ?) AND is_hidden = FALSE";
	private String SELECTALLARTICLES = "SELECT * FROM articles WHERE is_hidden = FALSE ORDER BY publish_date DESC";
	private String SELECTRECENTARTICLES = "SELECT * FROM articles WHERE is_hidden = FALSE ORDER BY publish_date DESC LIMIT ?";
	private String UPDATEARTICLES = "UPDATE articles SET a_title = ?, c_id = ?, is_hidden = ?, a_content = ? WHERE a_id = ?";
	private String UPDATEHIDDENTRUE = "UPDATE articles SET is_hidden = TRUE WHERE a_id = ?;";
	private String UPDATEHIDDENFALSE = "UPDATE articles SET is_hidden = FALSE WHERE a_id = ?;";
	private String SELECTHIDDENARTICLES = "SELECT * FROM articles WHERE is_hidden = TRUE";
	private String INSERTARTICLE = "INSERT INTO articles (a_title, c_id, a_content, publish_date, is_hidden) VALUES (?, ?, ?, ?, ?);";
	private String DELETEARTICLE = "DELETE FROM articles where a_id = ?;";

    // Default constructor
	public ArticleDAO() {
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

    // Retrieves a limited list of the most recently published articles
	public List<Article> selectRecentArticles(int limit) {
		List<Article> articles = new ArrayList<>();
		try (Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(SELECTRECENTARTICLES)) {
			pstm.setInt(1, limit);
			try (ResultSet rs = pstm.executeQuery()) {
				while (rs.next()) {
					articles.add(extractArticleFromResultSet(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articles;
	}

    // Retrieves a single article by ID
	public Article selectArticleById(int articleId) {
		Article article = null;
		try (Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(SELECTBYID)) {
			pstmt.setInt(1, articleId);
			System.out.println(pstmt);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				article = extractArticleFromResultSet(rs);
			} else {
				System.out.println("No article found with ID: " + articleId);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return article;
	}
	
    // Retrieves articles by category
	public List<Article> selectByCategory(int cId) {
	    List<Article> articles = new ArrayList<>();
	    try (Connection conn = getConnection();
	    	PreparedStatement preparedStatement = conn.prepareStatement(SELECTBYCATEGORY)) {
	        preparedStatement.setInt(1, cId);
	        try (ResultSet rs = preparedStatement.executeQuery()) {
	            while (rs.next()) {
	                articles.add(extractArticleFromResultSet(rs));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return articles;
	}

    // list all articles
	public List<Article> selectAllArticles() {
		List<Article> articles = new ArrayList<>();
		Connection conn = null;
	    PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SELECTALLARTICLES);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				articles.add(extractArticleFromResultSet(rs));
			}
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
            finallySQLException(conn, pstmt, null);
        }
		return articles;
	}

    // search for articles by keyword in title or content
	public List<Article> searchArticles(String keyword) {
		List<Article> articles = new ArrayList<>();
		Connection conn = null;
	    PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SEARCHARTICLES);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					articles.add(extractArticleFromResultSet(rs));
				}
			}
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
            finallySQLException(conn, pstmt, null);
        }
		return articles;
	}
	
	// update an article on admin page
	public boolean updateArticle(Article article) {
	    boolean articleUpdated = false;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
            conn = getConnection();
            pstmt = conn.prepareStatement(UPDATEARTICLES);
            pstmt.setString(1, article.getTitle());
            pstmt.setInt(2, article.getcId());
            pstmt.setBoolean(3, article.getIsHidden());
            pstmt.setString(4, article.getContent());
            pstmt.setInt(5, article.getId());
            articleUpdated = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            finallySQLException(conn, pstmt, null);
        }
        return articleUpdated;
    }
	
	// set hidden true for an article by articleId
	public boolean hideArticle(int articleId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	        conn = getConnection();
	        pstmt = conn.prepareStatement(UPDATEHIDDENTRUE);
	        pstmt.setInt(1, articleId);
	        int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        printSQLException(e);  
	        return false;           
	    } finally {
	        finallySQLException(conn, pstmt, null);
	    }
	}
	
	// set hidden false
	public boolean unhideArticle(int articleId) throws SQLException {
	    try (Connection conn = getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(UPDATEHIDDENFALSE)) {
	        pstmt.setInt(1, articleId);
	        int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        System.err.println("SQLException: " + e.getMessage());
	        throw e;
	    }
	}
	
	// list hidden articles
	public List<Article> getHiddenArticles(){
	    List<Article> hiddenArticles = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = getConnection();
	        pstmt = conn.prepareStatement(SELECTHIDDENARTICLES);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            hiddenArticles.add(extractArticleFromResultSet(rs));
	        }
	    } catch (SQLException e) {
	        printSQLException(e);  
	    } finally {
	        finallySQLException(conn, pstmt, rs); 
	    }
	    return hiddenArticles;
	}

	// insert articles
	public void insertArticle(Article article) throws SQLException {
	    System.out.println(INSERTARTICLE);
	    PreparedStatement pstmt = null;
	    Connection conn = null;
	    try {
	        conn = getConnection(); 
	        pstmt = conn.prepareStatement(INSERTARTICLE);
	        pstmt.setString(1, article.getTitle());
	        pstmt.setInt(2, article.getcId());
	        pstmt.setString(3, article.getContent());
	        // set localDate
	        LocalDate publishDate = article.getPublishDate(); // returns a java.time.LocalDate
	        java.sql.Date sqlDate = java.sql.Date.valueOf(publishDate);
	        pstmt.setDate(4, sqlDate);
	        pstmt.setBoolean(5, article.getIsHidden());
	        
	        System.out.println(pstmt);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        printSQLException(e);
	    } finally {
	        finallySQLException(conn, pstmt, null);
	    }
	}
	
	// delete articles on admin page
    public boolean deleteArticle(int id) throws SQLException {
        boolean articleDeleted = false;
        Connection conn = null; 
      	PreparedStatement pstmt = null;
      	try {
        	 conn = getConnection(); 
        	 pstmt = conn.prepareStatement(DELETEARTICLE);
        	 pstmt.setInt(1, id);
             articleDeleted = pstmt.executeUpdate() > 0 ? true:false;
      	} catch (SQLException e) {
	        printSQLException(e);
	    }
        finally {
        	finallySQLException(conn, pstmt,null);
        }
        return articleDeleted;
    }

    // helper method to extract article data from ResultSet
    private Article extractArticleFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("a_id");
        String title = rs.getString("a_title");
        String content = rs.getString("a_content");
        LocalDate publishDate = rs.getDate("publish_date") != null ? rs.getDate("publish_date").toLocalDate() : null;
        int cId = rs.getInt("c_id");
        boolean isHidden = rs.getBoolean("is_hidden"); // Assuming you have this column in your result set
        return new Article(id, title, content, publishDate, cId, isHidden);
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
