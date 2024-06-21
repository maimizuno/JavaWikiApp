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
		<title>Wiki - Delete Article</title>
	</head>
	<body>
		<!-- header section -->
		<header class="header">
			<div class="top">
				<p class="app_name">wiki admin</p>
				<ul>
					<li class="user_name"><c:out value="${user.getuName()}" /><i class="fas fa-user"></i></li>
				</ul>
			</div>
			<nav class="menu">
				<ul>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=showPublish">Publish Articles</a></li>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=listArticles&admin=true">Manage Articles</a></li>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=listCategories&admin=true">Manage categories</a></li>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=showHiddenArticles">View Hidden Articles</a></li>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=logout">logout</a></li>
				</ul>
			</nav>
		</header>
		<!-- main content (articles) section -->
		<div class="articles">
			<div class="delete_container">
				<h3>Are you sure you want to delete the category: <strong><c:out value="${categoryName}" /></strong> ?</h3>
				<h3><strong>This action cannot be undone.</strong></h3>
				<div class="delete_buttons">
					<form action="${pageContext.request.contextPath}/WikiServlet?" method="post">
						<input type="hidden"  name="categoryId" value="${categoryId}"/> 
						<button class="delete_button" type="submit" name="action" value="deleteCategory">Delete</button>
						<input type="hidden"  name="admin" value="true"/> 
						<button class="cancel_button" type="submit" name="action" value="listCategories">Cancel</button>
					</form>
				</div>
			</div>
		</div>	
	</body>
</html>