<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Add</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
		  integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">

</head>
<body>
<div class="alert alert-secondary" role="alert" style="text-align: center">
	Add a new book
</div>
<% List<String> headers = (List<String>) request.getAttribute("headers"); %>
<div style="padding: 10px; width: 500px;" class="mx-auto">
	<form method="post" action="">
		<table class="table table-striped-columns table-dark">

			<tr>
				<% for (String header : headers) { %>
					<th><%= header %></th>
				<% } %>
			</tr>

			<tr>
				<% for (String header : headers) { %>
					<td>
						<label>
							<input type="text" name="<%= header %>" class="input-group-text"/>
						</label>
					</td>
				<% } %>
			</tr>
		</table>
		<button class="btn btn-dark">add</button>
	</form>
</div>
</body>
</html>
