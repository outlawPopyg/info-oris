<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Error</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
</head>
<body>
	<div class="alert alert-danger" role="alert">
		<h4 class="alert-heading">Something went wrong!</h4>
		<p><%= request.getAttribute("errorMessage") %></p>
		<hr>
		<p class="mb-0">Не отчаивайтесь, у вас все получится (нет)</p>
	</div>

</body>
</html>
