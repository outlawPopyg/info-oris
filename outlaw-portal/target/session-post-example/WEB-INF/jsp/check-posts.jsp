<%@ page import="util.Util" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootswatch@4.5.2/dist/darkly/bootstrap.min.css"
		  integrity="sha384-nNK9n28pDUDDgIiIqZ/MiyO3F4/9vsMtReZK39klb/MtkZI3/LtjSjlmyVPS3KdN" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css"
		  integrity="sha384-xeJqLiuOvjUBq3iGOjvSQSIlwrpqjSHXpduPd6rQpuiM3f5/ijby8pCsnbu5S81n" crossorigin="anonymous">
	<link rel="stylesheet"
		  href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@6.2.0/css/fontawesome.min.css"
		  integrity="sha384-z4tVnCr80ZcL0iufVdGQSUzNvJsKjEtqYZjiQrrYKlpGow+btDHDfQWkFjoaz/Zr" crossorigin="anonymous">

	<title>Check posts</title>
</head>
<body>
<jsp:include page="/html/breadcrumb.jsp"/>
<c:forEach var="post" items="${posts}">

	<div class="card mx-auto" style="width: 30rem; margin-top: 5rem; margin-bottom: 5rem">
		<c:if test="${post.getImageName() != null}">
			<img src="http://localhost:8080/images/${post.getImageName()}" class="card-img-top" alt="...">
		</c:if>
		<div class="card-body">
			<h5 class="card-title">${post.getTitle()}</h5>

			<c:if test="${!isChecked}">
				<p class="card-text">${post.getText()}</p>
				<form action="" method="post">
					<input name="postId" value="${post.getId()}" type="text" hidden>
					<button name="res" value="accepted" class="btn btn-success" style="margin-right: 5px;">Accept</button>
					<button name="res" value="rejected" class="btn btn-danger">Reject</button>
				</form>
			</c:if>
			<c:if test="${isChecked}">
				<div class="card-forms" style="display: flex">
					<form action="/posts/${post.getId()}" method="get" style="display: inline-block">
						<button class="btn btn-secondary mb-3">Read</button>
					</form>
					<c:if test="${authUser != null && authUser.getId() == post.getUserId()}">
						<form action="/posts/${post.getId()}" method="post">
							<button class="btn btn-outline-secondary mb-3 ml-3">Edit</button>
						</form>
					</c:if>
					<c:if test="${isAdmin}">
						<form action="" method="post">
							<input name="deleteId" value="${post.getId()}" type="text" hidden>
							<button class="btn btn-outline-danger mb-3 ml-3">Delete</button>
						</form>
					</c:if>
				</div>
			</c:if>

			<p class="card-text">
				<small class="text-muted">
					<i class="bi bi-person"></i> ${Util.getAuthorName(post.getUserId())}
				</small>
			</p>
		</div>
	</div>

</c:forEach>
</body>
</html>
