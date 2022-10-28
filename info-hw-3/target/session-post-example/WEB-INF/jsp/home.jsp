<%@ page import="ru.itis.models.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.itis.models.Author" %>
<%@ page import="ru.itis.models.Entity" %>
<%@ page import="ru.itis.helpers.ModelUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
	  integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
<head>
	<title>Home</title>
</head>
<body>
<% List<Entity> entities = (List<Entity>) request.getAttribute("entities"); %>
<% Class<Entity> aClass = (Class<Entity>) request.getAttribute("aClass"); %>
<% List<String> headers = (List<String>) ModelUtil.getFieldsFromEntity(aClass); %>

<div class="alert alert-secondary" role="alert" style="text-align: center">
	<%= aClass.getSimpleName() %> table
</div>
<div style="padding: 10px; width: 500px;" class="mx-auto">

		<table class="table table-striped-columns table-dark" style="display: inline-block">

			<tr>
				<th></th>
				<form action="/<%= aClass.getSimpleName().toLowerCase()%>/sort" method="get">
					<% for (String header : headers) { %>
						<th>
							<button name="sortBy" value="<%= header %>" class="btn btn-secondary"><%= header %></button>
						</th>
					<% } %>
				</form>
				<th></th>
			</tr>

			<form action="/<%= aClass.getSimpleName().toLowerCase() %>" method="post">
			<% for (Entity entity : entities) { %>
			<% List<Object> values = entity.entityAttributes(); %>

				<tr>
					<td>
						<button class="btn btn-outline-secondary" name="toUpdate" value="<%= entity.getEntityId() %>">
							update
						</button>
					</td>
					<% for (Object o : values) { %>
						<td><%= o %></td>
					<% } %>

					<td>
						<label>
							<input name="toDelete" value="<%=entity.getEntityId()%>" type="checkbox" class="form-check-input"/>
						</label>
					</td>
				</tr>
			<% } %>
			<button class="btn btn-dark" style="margin-bottom: 10px">delete</button>
			</form>
		</table>

	<form action="/<%= aClass.getSimpleName().toLowerCase() %>/add" method="get">
		<button value="<%= aClass.getName() %>" name="aClass" class="btn btn-dark">add</button>
	</form>
</div>

</body>
</html>
