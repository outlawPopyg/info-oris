<%@ page import="ru.itis.models.Book" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.util.Map" %>
<%@ page import="ru.itis.models.Entity" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.itis.helpers.ModelUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Update</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">

</head>
<body>
	<% Map<String, Optional<Entity>> map = (Map<String, Optional<Entity>>) request.getServletContext().getAttribute("entityToUpdate"); %>
	<% Entity entity = map.get("entity").isPresent() ? map.get("entity").get() : null; assert entity != null; %>
	<% Map<String, Object> paramsMap = ModelUtil.fieldsAndValuesMap(entity); %>

	<div class="alert alert-secondary" role="alert" style="text-align: center">
		Update an existing book
	</div>
	<form method="post" action="">
		<div style="padding: 10px; width: 500px;" class="mx-auto">
			<table class="table table-striped-columns table-dark">

				<tr>
					<% for (String header : paramsMap.keySet()) { %>
						<th> <%= header %></th>
					<% } %>
				</tr>

				<tr>
					<% for (String header : paramsMap.keySet()) { %>
					<td>
						<input
								type="text" value="<%= paramsMap.get(header) %>"
								name="<%= header %>"
								class="input-group-text"
								<%= header.equals("id") ? "readonly" : "" %>
						/>
						<input type="text" value="<%= entity.getClass().getDeclaredField(header).getType().getSimpleName() %>" name="type" hidden />
						<input type="text" value="<%= entity.getEntityId() %>" name="entityId" hidden />
					</td>
					<% } %>

				</tr>

			</table>
			<button type="submit" class="btn btn-dark">update</button>
		</div>
	</form>
</body>
</html>
