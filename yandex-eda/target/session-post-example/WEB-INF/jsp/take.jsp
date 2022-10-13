<%@ page import="models.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="listeners.SessionCreatedListener" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Выдача заказов</title>
</head>
<% List<Product> list = SessionCreatedListener.TAKE; %>
<body>
	<h1>Забирайте</h1>
	<% for (Product product : list) { %>
		<li>
			<div><%= product.getName()%></div>
			<div><%= product.getPrice()%></div>
		</li>
	<%}%>
</body>
</html>
