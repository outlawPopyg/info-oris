<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootswatch@4.5.2/dist/darkly/bootstrap.min.css" integrity="sha384-nNK9n28pDUDDgIiIqZ/MiyO3F4/9vsMtReZK39klb/MtkZI3/LtjSjlmyVPS3KdN" crossorigin="anonymous">
<body>
	<jsp:include page="/html/breadcrumb.jsp" />
	<div>
		<form action="" method="post" class="w-25 p-3 mx-auto">
			<h2 style="font-family: 'ChineseRocks', sans-serif">Registration</h2>
			<div class="input-group mb-3">
				<span class="input-group-text" id="login">Username</span>
				<input required name="login" type="text" class="form-control" aria-label="Sizing example input" aria-describedby="login">
			</div>

			<div class="input-group mb-3">
				<span class="input-group-text" id="password">Password</span>
				<input required name="password" type="password" class="form-control" aria-label="Sizing example input" aria-describedby="password">
			</div>
			<button class="btn btn-outline-secondary">sign up</button>
		</form>
	</div>
</body>
</html>
