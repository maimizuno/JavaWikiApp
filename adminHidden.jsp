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
	    <title>Wiki - Hidden Articles</title>
	</head>
	<body>
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
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=listCategories&admin=true">Manage categories</a></li>
					<li class="menu_current"><a href="${pageContext.request.contextPath}/WikiServlet?action=showHiddenArticles">View Hidden Articles</a></li>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=logout">logout</a></li>
				</ul>
			</nav>
		</header>
		<!-- main content (articles) section -->
		<div class="articles">
			<!-- loop through each article if any articles exist in the database -->
		    <c:if test="${not empty showHiddenArticles}">
		        <c:forEach var="article" items="${showHiddenArticles}">
		            <div class="article_container">
		                <div class = sub_container>
			                <h2 class="title"><c:out value="${article.getTitle()}" /></h2>
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
								<form action="${pageContext.request.contextPath}/WikiServlet" method="POST">
								    <input type="hidden" name="action" value="unhideArticle">
								    <input type="hidden" name="articleId" value="${article.getId()}">
								    <button class="list_hide" type="submit">Unhide</button>
								</form>
							</div>
						</div>
		                <p class="publishdate"><c:out value="${article.getPublishDate()}" /></p>
		                <p class="content"><c:out value="${fn:substring(article.getContent(), 0, 300)}" />...</p> 
		                <!-- link to read more -->
						<div class="read_more">
							<a href="${pageContext.request.contextPath}/WikiServlet?action=showArticle&articleId=${article.getId()}&admin=true">Read more</a>
							<i class="fas fa-chevron-right"></i>
						</div>
		            </div>
		        </c:forEach>
		    </c:if>
		    <!-- display error message if no records -->
			<c:if test="${empty showHiddenArticles}">
				<p>No articles found.</p>
			</c:if>
		</div>
	</body>
</html>
