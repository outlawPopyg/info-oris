<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootswatch@4.5.2/dist/darkly/bootstrap.min.css" integrity="sha384-nNK9n28pDUDDgIiIqZ/MiyO3F4/9vsMtReZK39klb/MtkZI3/LtjSjlmyVPS3KdN" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css" integrity="sha384-xeJqLiuOvjUBq3iGOjvSQSIlwrpqjSHXpduPd6rQpuiM3f5/ijby8pCsnbu5S81n" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@6.2.0/css/fontawesome.min.css" integrity="sha384-z4tVnCr80ZcL0iufVdGQSUzNvJsKjEtqYZjiQrrYKlpGow+btDHDfQWkFjoaz/Zr" crossorigin="anonymous">
	<title>Add post</title>
</head>
<body>
	<jsp:include page="/html/breadcrumb.jsp" />
	<form action="${post != null ? "/posts/update" : ""}" method="post" class="w-50 mx-auto" enctype="multipart/form-data">
		<h2 style="font-family: 'ChineseRocks', sans-serif">Add post</h2>
		<div class="input-group mb-3">
			<span class="input-group-text">Post title</span>
			<input value="${post != null ? post.getTitle() : ""}" required name="title" type="text" class="form-control" aria-label="Server">
		</div>

		<div class="input-group mb-3">
			<span class="input-group-text">Main text</span>
			<textarea cols="10" rows="10" required name="text" class="form-control" aria-label="Main text">${post != null ? post.getText() : ""}</textarea>
		</div>

		<div class="input-group mb-3 mb-3">
			<input name="image" type="file" class="form-control" id="inputGroupFile02">
			<label class="input-group-text" for="inputGroupFile02">Upload image</label>
		</div>
		<input hidden name="postId" value="${post != null ? post.getId() : ""}" type="text">
		<button class="btn btn-outline-secondary">Add post</button>
	</form>
</body>
</html>
