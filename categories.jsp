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
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
		<title>Wiki Categories</title>
		<style>
			/* import google fonts*/
			@import url('https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@300..700&display=swap');
			@import url('https://fonts.googleapis.com/css2?family=Libre+Franklin:ital,wght@0,100..900;1,100..900&family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Source+Sans+3:ital,wght@0,200..900;1,200..900&family=Space+Grotesk:wght@300..700&display=swap');

			/* basic styling for body elements */
			body {
				font-family: "Source Sans 3", sans-serif;
				font-weight: 400;
				font-style: normal;
				font-size: 15px;
				width: 100%;
				margin: 0;
				color: #333;
				background-color: #F7F6EB;
			}
			
			/* header */
			.header { 
			    display: flex;
			    justify-content: space-between;  /* Ensures maximum space between items */
			    align-items: center;
			    position: fixed;
			    top: 0;
			    left: 0;
			    width: 100%;
			    height: 80px;
			    padding: 0 30px;
			    box-sizing: border-box;
			    background-color: #F7F6EB;
			}
			
			/* logo part */
			.app_name {
				max-width: 15%;
				padding-right: 30px;
				font-family: "Space Grotesk", sans-serif;
				font-optical-sizing: auto;
				font-weight: 700;
				font-style: normal;
				font-size: 36px;
				letter-spacing: 0.1rem;
				color: black;
			}
			
			/* navigation part */
			.header ul {
			    display: flex;
			    align-items: center;
			    justify-content: flex-end;
			    text-align: right;
			    list-style: none;
			    padding: 0; 
			    margin: 0;
			    font-size: 16px;
			    font-family: "Space Grotesk", sans-serif;
			    box-sizing: border-box;
			    letter-spacing: 0.05rem;
			}
			/* each list item */
			.header li {
				padding-right: 30px;
			}
			/* first list item */
			.header li:first-child {
				padding-right: 40px;
			}
			/* last list item */
			.header li:last-child {
				padding-right: 0;
			}
			/* links within the header */
			a {
				color: blue;
				text-decoration: none;
				cursor: pointer;
				position: relative;
				color: #222;
				font-weight: 500;
			}
			a::after {
			  position: absolute;
			  left: 0;
			  bottom: 0;
			  display: block;
			  content: "";
			  width: 0%;
			  height: 2px;
			  background: #000;
			  transition: all 0.2s cubic-bezier(0.455, 0.03, 0.515, 0.955);
			}
			a:hover {
			  opacity: 80%;
			}
			a:hover::after {
			  width: 100%;
			}
			
			/* search input field for the header */
			.header form {
				margin: 0;
				min-width: 600px;
			}		
			.header input[type="text"] {
				width: 50%;
				height: 40px;
				padding: 20px;
				margin: 0;
				display: inline-block;
				border: 1px solid #666;
				box-sizing: border-box;
				border-radius: 50px;
				margin-right: -40px;
			}
			
			/* search button (icon) */
			.header button { 
			    background: white;
			    cursor: pointer;
			    border: none;
			}
			.header button:hover {
				opacity: 0.8;
			}
			.fa-search { 
			    color: #333;
			}

			/* page heading */
			h1 {
				margin: 0;
				margin-bottom: 20px;
				color: #F7F6EB;
				lign-height: 1.75;
				letter-spacing: 0.2rem;
				text-transform: uppercase;
				font-family: "Space Grotesk", sans-serif;
				font-size: 20px;
				padding: 10px;
				padding-left: 20px;
				background-color: #333;
			}
			
			/* container - all categories */
			.categories {
				width: 100%;
				padding: 150px 30px 0px;
				box-sizing: border-box;
			}
			
			/* each category */
			.category_list {
				margin-bottom: 10px;
			}
			
			
		</style>
</head>
<body>
	<!-- header section -->
	<header class="header">
			<p class="app_name">wiki</p>
			<nav>
				<ul>
					<li>
						<form action="${pageContext.request.contextPath}/WikiServlet?action=searchArticles" method="post">
							<input type="text" name="keyword" placeholder="search by keyword"/> 
							<button type="submit"><i class="fa fa-search"></i></button>
						</form>
					</li>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=showHome">home</a></li>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=listArticles">articles</a></li>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=listCategories">categories</a></li>
					<li><a href="${pageContext.request.contextPath}/WikiServlet?action=adminLogin">admin</a></li>
				</ul>
			</nav>
		</header>
		
	<!-- main content (articles) section -->
	<div class="categories">
		<!-- page title -->
		<h1>All Categories</h1>
		<!-- loop through each article if any articles exist in the database -->
		<c:if test="${not empty categories}">
			<ul>
				<c:forEach var="category" items="${categories}">
					<li class="category_list">
			            <!-- Link to filteredArticle.jsp passing the category name -->
						<a href="${pageContext.request.contextPath}/WikiServlet?action=filterByCategory&category=${category.getcId()}">${category.getcName()}</a>
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
