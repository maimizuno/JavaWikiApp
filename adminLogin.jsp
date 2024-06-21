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
	    <title>Wiki -Admin Login</title>
	</head>
	<body>
		<!-- header section -->
		<header class="general_header">
			<p class="app_name">wiki</p>
			<nav>
				<ul>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=showHome">home</a></li>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=listArticles">articles</a></li>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=listCategories">categories</a></li>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=adminLogin">admin</a></li>
				</ul>
			</nav>
		</header>
		<!-- main content (login & register) section -->
		<div class="login_container">
			<!-- page title -->
			<h1>Admin Login</h1>
			<!-- javaScript code to display a pop up if login / registration fails -->
			<script type="text/javascript">
       			window.onload = function() {
            		// Check if there is a message to display
            		var message = "<%=request.getAttribute("message") != null ? request.getAttribute("message") : "" %>";
            		if (message.length > 0) {alert(message);
           			}
       			}
   			</script>
   			<!-- Login & Registration form -->
			<form action="${pageContext.request.contextPath}/WikiServlet?" method="post">
				<!-- User input -->
			    <p><input type="text" placeholder="Username" name="username" required></p>
			    <p><input type="password" placeholder="Password" name="password" required></p>
			    <!-- buttons -->
			    <button type="submit" name="action" value="authenticateUsers">Login</button>
			    <button type="submit" name="action" value="registerUsers" class="btn-register">Register</button>
			</form>
		</div>
	</body>
</html>