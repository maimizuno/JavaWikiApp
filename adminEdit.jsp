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
		    <title>Wiki - Edit Article</title>
		</head>
		<body>
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
			<!-- main content (edit) section -->
			<!-- javaScript code to display a pop up if edit fails -->
			<script type="text/javascript">
			    window.onload = function() {
			        var message = "${not empty message ? fn:escapeXml(message) : ''}";
			        if (message.length > 0) {
			            alert(message);
			        }
			    }
			</script>
			<div class="edit_container">
   			<!-- Edit form -->
				<form action="${pageContext.request.contextPath}/WikiServlet?" method="post">
					<!-- get article id -->
					<input type="hidden" name="id" value="${article.getId()}"/>
				    <!-- User input -->
				    <p>Title<input type="text" name="title" value="${article.getTitle()}" required></p>
				    <p>Category 
					    <select class="category" name="category" required>
					        <c:forEach  var="category" items="${categories}">
					            <option value="${category.cId}" ${category.cId == article.getcId() ? 'selected' : ''}>${category.cName}</option>
					        </c:forEach>
					    </select>
					</p>
					<p>Visibility
					    <select name="visibility" required>
					        <option value="Visible" ${visibility == 'Visible' ? 'selected' : ''}>Visible</option>
					        <option value="Hidden" ${visibility == 'Hidden' ? 'selected' : ''}>Hidden</option>
					    </select>
					</p>
				    <p>Content<textarea class="content" name="content" required>${article.getContent()}</textarea></p>
				    <!-- button -->
				    <button type="submit" name="action" value="editArticle">Save</button>
				</form>
			</div>
		</body>
	</html>