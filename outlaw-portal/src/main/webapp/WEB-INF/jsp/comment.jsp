<%@ page import="services.UserService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Comment</title>
</head>
<body>
	<jsp:include page="/html/breadcrumb.jsp"/>
	<h2 style="text-align: center; margin-top: 2rem; margin-bottom: 2rem">Comments</h2>
	<ul class="list-group w-50 mx-auto">
		<c:forEach var="comment" items="${comments}">
			<li class="list-group-item" style="margin-bottom: 1rem;">
				<b>${UserService.getUserNameByPostId(comment.getUserId())}</b>: ${comment.getText()}
			</li>
		</c:forEach>
	</ul>

	<c:if test="${isAuth}">
		<div class="w-50 mx-auto" style="margin-top: 5rem;">
			<form action="" method="post">
				<div class="input-group mb-3 mb-3">
					<input autocomplete="off" name="comment" type="text" class="form-control" id="inputGroupFile02">
					<label class="input-group-text" for="inputGroupFile02">Your comment</label>
				</div>
				<button class="btn btn-success">Send</button>
			</form>
		</div>
	</c:if>


</body>
</html>
