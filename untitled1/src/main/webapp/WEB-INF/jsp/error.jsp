<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootswatch@4.5.2/dist/darkly/bootstrap.min.css" integrity="sha384-nNK9n28pDUDDgIiIqZ/MiyO3F4/9vsMtReZK39klb/MtkZI3/LtjSjlmyVPS3KdN" crossorigin="anonymous">
</head>
<body>
	<jsp:include page="/html/breadcrumb.jsp" />
	<div class="alert alert-danger w-50 p-3 mx-auto mt-5" role="alert">
		<h4 class="alert-heading" style="font-family: 'ChineseRocks', sans-serif">Something went wrong!</h4>
		<p>${requestScope.errorMessage}</p>
		<hr>
		<p class="mb-0">С уважением, команда outlaw</p>
	</div>
</body>
</html>
