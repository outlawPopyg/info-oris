<%@ page import="models.Entity" %>
<%@ page import="java.util.List" %>
<%@ page import="models.User" %>
<%@ page import="models.Product" %>
<%@ page import="java.util.NoSuchElementException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Product list</title>
</head>
<% List<Product> products = (List<Product>) request.getSession().getAttribute("products"); %>
<body>
	<form action="" method="post">
		<% for (Product product : products) { %>
			<hr />
				<div> <%= product.getName() %></div>
				<div> <%= product.getPrice() %></div>
				<input name="added" type="checkbox" value="<%= product.getId() %>" >

			<hr />
		<% } %>
		<button type="submit">add to cart</button>
	</form>
</body>
</html>
