<%@ page import="models.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="listeners.SessionCreatedListener" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Cart</title>
</head>
<% List<Product> activeOrders = SessionCreatedListener.getActiveOrders(); %>
<% int i = -1; %>
<body>
	<h2>Active orders: <%= SessionCreatedListener.ACTIVE_COUNT%></h2>
	<% for (Product product : activeOrders) { %>
		<% i++; %>
		<hr>
			<li>
				<div>name: <%= product.getName() %></div>
				<div>price: <%= product.getPrice() %></div>
				<% if (SessionCreatedListener.IS_AUTH) { %>
					<form action="" method="post">
						<button name="ready" type="submit" value="<%= i %>">ready</button>
					</form>
				<% } %>
			</li>
		<hr>
	<% } %>
</body>
</html>
