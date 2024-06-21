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
	    <title>Wiki - Publish Articles</title>
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
				<li class="menu_current"><a href="${pageContext.request.contextPath}/WikiServlet?action=showPublish">Publish Articles</a></li>
				<li><a href="${pageContext.request.contextPath}/WikiServlet?action=listArticles&admin=true">Manage Articles</a></li>
				<li><a href="${pageContext.request.contextPath}/WikiServlet?action=listCategories&admin=true">Manage categories</a></li>
				<li><a href="${pageContext.request.contextPath}/WikiServlet?action=showHiddenArticles">View Hidden Articles</a></li>
				<li><a href="${pageContext.request.contextPath}/WikiServlet?action=logout">logout</a></li>
			</ul>
		</nav>
	</header>
		<!-- main content (create) section -->
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
       		<!-- Creation form -->
	        <form action="${pageContext.request.contextPath}/WikiServlet" method="post">
	            <!-- User input -->
	            <p>Title<input type="text" name="title" required></p>
	            <p>Category
	                <select name="category" required>
	                	<option value=""></option>
	                    <c:forEach items="${categories}" var="category">
	                    	<c:if test="${category.cName ne 'Unknown'}">
	                        	<option value="${category.cId}">${category.cName}</option>
	                        </c:if>
	                    </c:forEach>
	                </select>
	            </p>
	            <p>Visibility
				    <select name="visibility" required>
				        <option value="Visible" ${visibility eq 'Visible' ? 'selected' : ''}>Visible</option>
				        <option value="Hidden" ${visibility eq 'Hidden' ? 'selected' : ''}>Hidden</option>
				    </select>
				</p>
	            <p>Publish Date<input name="date" value="${todayDate}" required/></p>
	            <p>Content<textarea name="content" required></textarea></p>
	            <!-- Submit button -->
	            <button type="submit" name="action" value="publishArticle">Publish</button>
	        </form>
	    </div>
	</body>
</html>