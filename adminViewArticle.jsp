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
		<title>Wiki - View Article</title>
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
					<li class="menu_current"><a href="${pageContext.request.contextPath}/WikiServlet?action=listArticles&admin=true">Manage Articles</a></li>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=listCategories&admin=true">Manage categories</a></li>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=showHiddenArticles">View Hidden Articles</a></li>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=logout">logout</a></li>
				</ul>
			</nav>
		</header>
	<!-- main content (articles) section -->
	<div class="articles">
		<c:if test="${not empty article}">
			<div class="articleTitle_container">
				<h1>${article.title}</h1>
				<div class="modify">
					<form action="${pageContext.request.contextPath}/WikiServlet" method="post" style="display:inline;">
					    <input type="hidden" name="articleId" value="${article.getId()}">
					    <input type="hidden" name="action" value="showEdit">
					    <button class="list_edit" type="submit" class="link-button">Edit</button>
					</form>
					<form action="${pageContext.request.contextPath}/WikiServlet" method="post" style="display:inline;">
					    <input type="hidden" name="articleId" value="${article.getId()}">
					    <input type="hidden" name="action" value="showArticle">
					    <input type="hidden" name="delete" value="true">
					    <button class="list_delete" type="submit">Delete</button>
					</form>
					<c:choose>
						<c:when test="${visibility eq 'Visible'}">
							<form action="${pageContext.request.contextPath}/WikiServlet" method="POST">
							    <input type="hidden" name="action" value="hideArticle">
							    <input type="hidden" name="articleId" value="${article.getId()}">
							    <button class="list_hide" type="submit">Hide</button>
							</form>
						</c:when>
						<c:otherwise>
							<form action="${pageContext.request.contextPath}/WikiServlet" method="POST">
							    <input type="hidden" name="action" value="unhideArticle">
							    <input type="hidden" name="articleId" value="${article.getId()}">
							    <button class="list_hide" type="submit">UnHide</button>
							</form>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="sub_container">
				<div class = "sub">
					<p><strong>ID: </strong><c:out value="${article.getId()}" /></p>
				    <p><strong>Published: </strong><c:out value="${article.getPublishDate()}" /></p>
				    <p><strong>Category: </strong><c:out value="${categoryName}" /></p>
				    <p><strong>Visible: </strong><c:out value="${visibility}" /></p>
			    </div>
			</div>
		    <p class="content"><c:out value= "${article.getContent()}" /></p>
		</c:if>
	</div>
	</body>
</html>