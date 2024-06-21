<!-- JSP directive to set the content type and character encoding for the page -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- JSP directive to include JSTL core library -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- JSP directive to include JSTL functions library -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"> <!-- sets the character encoding for the HTML document -->
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
		<title>Wiki - Categories</title>
	</head>
	<body>
	<!-- header section -->
	<header class="header">
			<div class="top">
				<p class="app_name">wiki admin</p>
				<ul>
					<li>
						<form action="${pageContext.request.contextPath}/WikiServlet?action=searchArticles" method="post">
						    <input type="text" name="keyword" placeholder="search by keyword"/>  <!-- This is the actual search keyword -->
						    <input type="hidden" name="adminkeyword" value="true"/>  <!-- Flag to indicate admin search -->
						    <button type="submit"><i class="fa fa-search"></i></button>
						</form>
					</li>
					<li class="user_name"><c:out value="${user.getuName()}" /><i class="fas fa-user"></i></li>
				</ul>
			</div>
			<nav class="menu">
				<ul>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=showPublish">Publish Articles</a></li>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=listArticles&admin=true">Manage Articles</a></li>
					<li class="menu_current"><a href="${pageContext.request.contextPath}/WikiServlet?action=listCategories&admin=true">Manage categories</a></li>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=showHiddenArticles">View Hidden Articles</a></li>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=logout">logout</a></li>
				</ul>
			</nav>
		</header>	
	<!-- main content (articles) section -->
	<div class="categories">
		<!-- add New category -->
		<div class="read_more">
			<a href="${pageContext.request.contextPath}/WikiServlet?action=showAddCategory">Create a New Category</a>
			<i class="fas fa-chevron-right"></i>
		</div>
		<!-- Edit form -->
		<!-- loop through each category if any articles exist in the database -->
		<c:if test="${not empty categories}">
			<ul class="category_list">
				<c:forEach var="category" items="${categories}">
				    <li>
				        <form action="${pageContext.request.contextPath}/WikiServlet" method="post">
				            <c:if test="${category.cName ne 'Unknown'}">
				            	<input type="hidden" name="categoryId" value="${category.cId}"/> <!-- Hidden field for category ID -->
				            	<input type="text" name="categoryName" value="${category.cName}"/> <!-- Input field for category name -->
				            	<button class="save" type="submit" name="action" value="editCategory">Save</button>
				            </c:if>
				        </form>
				        <form action="${pageContext.request.contextPath}/WikiServlet" method="post">
						 	<c:if test="${category.cName ne 'Unknown'}"> 	
						    	<input type="hidden" name="action" value="showDeleteCategory">
						    	<input type="hidden" name="categoryId" value="${category.cId}">
						    	<button class="delete" type="submit">Delete</button>
						    </c:if>
						</form>
				    </li>
				</c:forEach>
			</ul>
		</c:if>
		<!-- display error message if no records -->
		<c:if test="${empty categories}">
			<p>No categories found.</p>
		</c:if>

	</div>


</body>
</html>
