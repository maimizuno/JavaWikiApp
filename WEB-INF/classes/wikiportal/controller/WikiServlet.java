package wikiportal.controller;

import java.io.IOException; // Import IOException class to handle input-output exceptions
import javax.servlet.ServletException; // Import ServletException class to handle servlet-specific exceptions
import javax.servlet.annotation.WebServlet; // Import WebServlet annotation to define servlet characteristics and mappings
import javax.servlet.http.HttpServlet; // Import HttpServlet class to create HTTP servlets, base class for handling HTTP requests
import javax.servlet.http.HttpServletRequest; // Import HttpServletRequest class to represent client requests, provides request information for HTTP servlets
import javax.servlet.http.HttpServletResponse; // Import HttpServletResponse class to represent servlet responses to clients

import java.sql.SQLException; // Import SQLException class to handle SQL-specific exceptions when interacting with the database
import java.time.LocalDate;
import java.util.List; // Import List interface to handle collections of objects
import javax.servlet.RequestDispatcher; // Import RequestDispatcher class to forward or include responses and resources

import wikiportal.model.bean.Article; // Import Article class, represents article data within the application
import wikiportal.model.bean.Category; // Import Article class, represents article data within the application
import wikiportal.model.bean.User;  // Import User class from the bean package
import wikiportal.model.dao.ArticleDAO; // Import ArticleDAO class, encapsulates all database interactions related to articles
import wikiportal.model.dao.CategoryDAO; // Import CategoryDAO class, encapsulates all database interactions related to categories
import wikiportal.model.dao.UserDAO; // Import UserDAO class, encapsulates all database interactions related to admin

/**
 * Servlet implementation class WikiServlet for handling various actions related to articles.
 * It routes requests to different methods based on the action specified.
 */
@WebServlet("/WikiServlet") // Annotation to declare servlet mapping URL pattern
public class WikiServlet extends HttpServlet {
	private ArticleDAO articleDAO; // Define ArticleDAO as an instance variable to handle all article data operations
	private CategoryDAO categoryDAO; // Handle all category data operations
	private UserDAO userDAO; // Handle all admin data operations

	/**
	 * default constructor
	 */
	public WikiServlet() {
		super(); // Call to the superclass (HttpServlet) constructor for servlet initialization
	}

	/**
	 * init method is called by the servlet container to indicate that this servlet is being placed into service.
	 */
	@Override
	public void init() {
		articleDAO = new ArticleDAO(); // Initialize ArticleDAO to interact with the database for article operations
		categoryDAO = new CategoryDAO();
		userDAO = new UserDAO();
	}

	/**
	 * Handles the HTTP GET requests. Based on the 'action' parameter, it directs the flow to different methods.
	 * @param request  The HttpServletRequest object that contains the client request
	 * @param response The HttpServletResponse object that contains the servlet's response
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action"); // Retrieve the action parameter from the request
		if (action == null) {
	        action = "showHome"; // Default action if none is specified
	    }
	    try {
	        switch (action) { // Switch on the action to determine which method to call
	        case "showHome":
	            showHome(request, response);
	            break;
	        case "listArticles":
	            listArticles(request, response);
	            break;
	        case "listCategories":
	            listCategories(request, response);
	            break;
	        case "adminLogin":
	            adminLogin(request, response);
	            break;
	        case "showArticle":
	            showArticle(request, response);
	            break;
	        case "searchArticles":
	            searchArticles(request, response);
	            break;
	        case "filterByCategory":
	        	filterByCategory(request, response);
	            break;
            case "showEdit":
            	showEdit(request, response);
                break;
            case "showPublish":
            	showPublish(request, response);
            	break;
            case "showHiddenArticles":
            	showHiddenArticles(request, response);
            	break;
            case "showAddCategory":
            	showAddCategory(request, response);
            	break;
            case "showDeleteCategory":
            	showDeleteCategory(request, response);
            	break;
            case "logout":
            	logout(request, response);
                break;
	        default:
	            showHome(request, response); // Default to showHome if action is not recongnised
	            break;
	        }
		} catch (Exception ex) {
			throw new ServletException(ex); // Handle any exceptions by throwing ServletException
		}
	}

	/**
	 * handles HTTP POST requests, which are redirected to doGet method.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    String action = request.getParameter("action");
	    if (action == null) {
	        action = "showHome";
	    }
	    try {
	        switch (action) {
	            case "authenticateUsers":
	                authenticateUsers(request, response);
	                break; 
	            case "registerUsers":
	                registerUsers(request, response);
	                break;
	            case "editArticle":
	            	editArticle(request, response);
	            	break;
	            case "publishArticle":
	            	publishArticle(request, response);
	            	break;
	            case "editCategory":
	            	editCategory(request, response);
	            	break;
	            case "hideArticle":
	            	hideArticle(request, response);
	            	break;
	            case "unhideArticle":
	            	unhideArticle(request, response);
	            	break;
	            case "deleteArticle":
	            	deleteArticle(request, response);
	            	break;
	            case "createCategory":
	            	createCategory(request, response);
	            	break;
	            case "deleteCategory":
	            	deleteCategory(request, response);
	            	break;
	            default:
	                doGet(request, response); // If no specific POST action, delegate to doGet
	                break;
	        }
	    } catch (Exception ex) {
	        throw new ServletException(ex);
	    }
	}

	/**
	 * displays the home page and a limited list of articles based on ID
	 */
	private void showHome(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    List<Article> recentArticles = articleDAO.selectRecentArticles(3); // Get 3 recent articles from the database
	    request.setAttribute("recentArticles", recentArticles); // Set the list of recent articles in the request scope
	    RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp"); // Get the RequestDispatcher for home.jsp
	    dispatcher.forward(request, response); // forward the request and response to home.jsp
	}

	/**
	 * lists all articles for the articles view page
	 */
	private void listArticles(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException, ServletException {
	    List<Article> listArticles = articleDAO.selectAllArticles();
	    request.setAttribute("listArticles", listArticles);

	    // Retrieve isAdmin from the session
	    Boolean isAdmin = (Boolean) request.getSession().getAttribute("isAdmin");

	    // Initialize the resultPage variable
	    String resultPage;

	    // Determine the appropriate page based on isAdmin status
	    if (isAdmin != null && isAdmin) {
	        resultPage = "adminHome.jsp";  // Use admin home page if the user is an admin
	    } else {
	        resultPage = "articles.jsp";   // Use the regular articles page if the user is not an admin
	    }

	    // Forward to the determined result page
	    RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
	    dispatcher.forward(request, response);
	}

	/**
	 * lists all article categories for the categories page
	 */
	private void listCategories(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    List<Category> categories = categoryDAO.selectCategories(); // fetch all categories
	    request.setAttribute("categories", categories);
	    
	    boolean isAdmin = Boolean.TRUE.equals(request.getSession().getAttribute("isAdmin"));
	    String resultPage = isAdmin ? "adminCategories.jsp" : "categories.jsp";
	    
	    RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
	    dispatcher.forward(request, response);
	}

	/**
	 * displays the admin login page
	 */
	private void adminLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("adminLogin.jsp"); // Get the dispatcher for admin.jsp
		dispatcher.forward(request, response); // forward to adminLogin.jsp for admin login
	}

	/**
	 * shows a single article based on its ID
	*/
	private void showArticle(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		
	    String aIdString = request.getParameter("articleId");
	    Integer aId = Integer.parseInt(aIdString);
	    Article article = articleDAO.selectArticleById(aId);
	    int cId = article.getcId();
	    String categoryName = categoryDAO.selectCategoryId(cId);
	    boolean isHidden = article.getIsHidden();
	    String visibility = isHidden ? "Hidden" : "Visible";
	    
	    request.setAttribute("article", article);
	    request.setAttribute("categoryName", categoryName);
	    request.setAttribute("visibility", visibility);
	    
	    String resultPage = "viewArticle.jsp"; // Default
	    if ("true".equals(request.getParameter("admin"))) {
	        resultPage = "adminViewArticle.jsp";
	    }
	    if ("true".equals(request.getParameter("delete"))) {
	        resultPage = "adminDeleteArticle.jsp";
	    }
	    
	    RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
	    dispatcher.forward(request, response); // forward to the determined page
	}

	/**
	 * filters articles by category and displays them on the filtered articles page
	 */
	private void filterByCategory(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		
	    String cIdString = request.getParameter("category"); // get the category from request parameters
	    int categoryId = Integer.parseInt(cIdString);
	 
	    List<Article> articles = articleDAO.selectByCategory(categoryId); // Fetch articles from the database by category
	    request.setAttribute("articles", articles); // set the list of articles in the request scope
        
	    String cName = categoryDAO.selectCategoryId(categoryId); // Fetch the article from the database using its ID	    
	    request.setAttribute("categoryName", cName); // Set the category id in the request scope
	    
	    RequestDispatcher dispatcher = request.getRequestDispatcher("filteredArticles.jsp"); // Get the dispatcher for filteredArticles.jsp
	    dispatcher.forward(request, response); // forward to the specified page
	}

	/**
	 * searches articles based on a keyword and displays them on the search results page
	 */
	private void searchArticles(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		
	    String keyword = request.getParameter("keyword");
	    List<Article> results = articleDAO.searchArticles(keyword); // Search articles in the database using the keyword
	    request.setAttribute("listArticles", results); // set the search results in the request scope

	    // Determine if the search is coming from an admin page
	    boolean isAdminSearch = request.getParameter("adminkeyword") != null;

	    // Choose the appropriate JSP based on whether it's an admin search
	    String resultPage;
	    if (isAdminSearch) {
	        resultPage = "adminSearchResults.jsp";
	    } else {
	        resultPage = "searchResults.jsp";
	    }
	    
	    RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
	    dispatcher.forward(request, response); // Forward to the correct search results page
	}
	
	/**
	 * registers admins with their username and password 
	*/	
	private void registerUsers(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException, ServletException {
		
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");

	    try {
	        if (userDAO.usernameExists(username)) {
	            request.setAttribute("message", "Username exists. Please try a different name.");
	            request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
	        } else {
	            userDAO.registration(username, password);
	            request.setAttribute("message", "Registration successful! You can now log in.");
	            request.getRequestDispatcher("adminLogin.jsp").forward(request, response); 
	        }
	    } catch (SQLException e) {
	        request.setAttribute("errorMessage", "Registration failed: " + e.getMessage());
	        request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
	    }
	}
	
	/**
	 * authenticates admins based on their username and password 
	*/
	private void authenticateUsers(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException, SQLException {
		
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    
	    User user = userDAO.login(username, password);
	    if (user != null) {
	        request.getSession().setAttribute("user", user);
	        request.getSession().setAttribute("isAdmin", true);
	        listArticles(request, response); 
	    } else {
	        request.setAttribute("message", "Invalid username or password");
	        RequestDispatcher dispacher = request.getRequestDispatcher("adminLogin.jsp");
	        dispacher.forward(request, response);
	    }
	}
	
	/**
	 * displays article edit page
	 */
	private void showEdit(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		
	    String articleIdString = request.getParameter("articleId");
	    Integer articleId = Integer.parseInt(articleIdString);  
	    Article article = articleDAO.selectArticleById(articleId);
	    request.setAttribute("article", article);
	    
	    List<Category> categories = categoryDAO.selectCategories();
	    request.setAttribute("categories", categories);
	    
        int categoryId = article.getcId();
        String categoryName = categoryDAO.selectCategoryId(categoryId);
        request.setAttribute("categoryName", categoryName);
        
        boolean isHidden = article.getIsHidden();
	    String visibility = isHidden ? "Hidden" : "Visible"; // convert "Hidden" to "true"
        request.setAttribute("visibility", visibility);
        
	    RequestDispatcher dispatcher = request.getRequestDispatcher("adminEdit.jsp");
	    dispatcher.forward(request, response);
	}

	/**
	 * edit article content
	 */
	private void editArticle(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException {
		
	    String idStr = request.getParameter("id");
	    String cIdStr = request.getParameter("category");
	    Integer id = Integer.parseInt(idStr);
	    Integer cId = Integer.parseInt(cIdStr);
	    String title = request.getParameter("title");
	    String content = request.getParameter("content");
	    String visibility = request.getParameter("visibility");
	    boolean isHidden = visibility.equals("Hidden");
	    
	    Article article = new Article(id, title, content, cId, isHidden);
	    ArticleDAO articleDAO = new ArticleDAO();
	    boolean articleUpdated = articleDAO.updateArticle(article);

	    if (articleUpdated) {
	    	response.sendRedirect(request.getContextPath() + "/WikiServlet?action=showArticle&articleId=" + article.getId() + "&admin=" + request.getSession().getAttribute("isAdmin"));
	    	request.setAttribute("message", "Article updated!");
	    } else {
	        request.setAttribute("errorMessage", "Failed to update the article.");
	        request.getRequestDispatcher("adminEdit.jsp").forward(request, response);
	    }
	}

	/**
	 * display publish artcicle page
	 */
	private void showPublish(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		List<Category> categories = categoryDAO.selectCategories();
	    request.setAttribute("categories", categories);
	    LocalDate today = LocalDate.now(); // Gets today's date
	    request.setAttribute("todayDate", today.toString()); // Convert LocalDate to String and set it as a request attribute
		RequestDispatcher dispatcher = request.getRequestDispatcher("adminPublish.jsp"); // Get the dispatcher for adminPublish.jsp
		dispatcher.forward(request, response); // forward to admin.jsp for admin login
	}
	
	/**
	 * create a new article
	 */
	private void publishArticle(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException, SQLException {
	    String title = request.getParameter("title");
	    String content = request.getParameter("content");
	    String cIdStr = request.getParameter("category"); 
	    String dateStr = request.getParameter("date");
	    String visibility = request.getParameter("visibility");
	    if (title == null || content == null || cIdStr == null || dateStr == null) {
	        request.setAttribute("message", "All fields are required.");
	        request.getRequestDispatcher("adminPublish.jsp").forward(request, response);
	        return;
	    }
	    int cId = Integer.parseInt(cIdStr);
	    LocalDate pDate = LocalDate.parse(dateStr);
	    boolean isHidden = "Hidden".equals(visibility); // convert "Hidden" to true
	    Article article = new Article(title, content, cId, pDate, isHidden);
        articleDAO.insertArticle(article);
        request.getSession().setAttribute("isAdmin", true);
        listArticles(request,response);
	}
	
	/**
	 * edits categories
	 */
	private void editCategory(HttpServletRequest request, HttpServletResponse response) 
	        throws SQLException, IOException, ServletException {
	    int categoryId = Integer.parseInt(request.getParameter("categoryId"));
	    String categoryName = request.getParameter("categoryName");

	    boolean result = categoryDAO.updateCategoryName(categoryId, categoryName);
	    if (result) {
	        request.getSession().setAttribute("isAdmin", true);
	        response.sendRedirect(request.getContextPath() + "/WikiServlet?action=listCategories&admin=true");
	        return; 
	    } else {
	        request.setAttribute("errorMessage", "Failed to update category.");
	        request.getRequestDispatcher("/adminCategories.jsp").forward(request, response);
	    }
	}

	/**
	 * hides articles from article page
	 */
	private void hideArticle(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException, SQLException {
		
	    int articleId = Integer.parseInt(request.getParameter("articleId"));
	    
        if (articleDAO.hideArticle(articleId)) {
            request.setAttribute("message", "Article hidden successfully.");
        } else {
            request.setAttribute("errorMessage", "Failed to hide article.");
        }

	    response.sendRedirect(request.getContextPath() + "/WikiServlet?action=showHiddenArticles");
	}
	
	/**
	 * unhides articles from hidden articles page
	 */
	private void unhideArticle(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException, SQLException {
		
	    int articleId = Integer.parseInt(request.getParameter("articleId"));
        if (articleDAO.unhideArticle(articleId)) {
            request.setAttribute("message", "Article hidden successfully.");
        } else {
            request.setAttribute("errorMessage", "Failed to hide article.");
        }
        
	    request.getSession().setAttribute("isAdmin", true);
	    response.sendRedirect(request.getContextPath() + "/WikiServlet?action=listArticles&admin=true");
	}
	
	/**
	 * shows articles on article page
	 */
	private void showHiddenArticles(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException, ServletException {
		
	    List<Article> hiddenArticles = articleDAO.getHiddenArticles();
	    request.setAttribute("showHiddenArticles", hiddenArticles);
	    
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/adminHidden.jsp");
	    dispatcher.forward(request, response); // Forward to the specified page
	}

	/**
	 * deletes the selected article
	 */
	private void deleteArticle(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		String aIdStr = request.getParameter("id");
		int aId = Integer.parseInt(aIdStr);
		articleDAO.deleteArticle(aId);
		request.getSession().setAttribute("isAdmin", true);
        listArticles(request, response); 
	}
	
	/**
	 * redirects to create category page
	 */
	private void showAddCategory(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	    RequestDispatcher dispatcher = request.getRequestDispatcher("adminCreateCategory.jsp");
	    dispatcher.forward(request, response);
	}

	
	/**
	 * adds a new category
	 */
	protected void createCategory(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, SQLException {
		
	    String cName = request.getParameter("categoryName");
	    CategoryDAO categoryDAO = new CategoryDAO();
        if (categoryDAO.createCategory(cName)) {
            request.setAttribute("message", "Category created successfully.");
        } else {
            request.setAttribute("errorMessage", "Failed to create category.");
        }
        
	    RequestDispatcher dispatcher = request.getRequestDispatcher("adminCreateCategory.jsp");
	    dispatcher.forward(request, response); // forward to the specified page
	}
	
	/**
	 * deletes the selected category
	 */
	private void showDeleteCategory(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		
		String cIdString = request.getParameter("categoryId");
	    Integer cId = Integer.parseInt(cIdString);
        String categoryName = categoryDAO.selectCategoryId(cId);
        request.setAttribute("categoryId", cId);
        request.setAttribute("categoryName", categoryName);
        
		RequestDispatcher dispatcher = request.getRequestDispatcher("adminDeleteCategory.jsp"); 
		dispatcher.forward(request, response); // forward to the specified page
	}
	
	/**
	 * deletes the selected category
	 */
	protected void deleteCategory(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		
        int cId = Integer.parseInt(request.getParameter("categoryId"));
        categoryDAO.deleteCategory(cId);
        
        request.getSession().setAttribute("isAdmin", true); //retrieves the current HttpSession 
        listCategories(request, response); // forward to the specified page
	}

	
	private void logout(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		
	    request.getSession().invalidate(); // Invalidate the session to clear all session data
	    response.sendRedirect(request.getContextPath() +"/WikiServlet?action=showHome"); // Redirect to the showRecentArticles action
	}
}
