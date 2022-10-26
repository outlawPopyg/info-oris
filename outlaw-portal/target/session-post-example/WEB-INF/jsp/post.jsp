<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>${post.getTitle()}</title>
</head>
<body>
	<jsp:include page="/html/breadcrumb.jsp"/>
	<div class="w-50 mx-auto">
		<c:if test="${post.getImg() != null}">
			<img src="http://localhost:8080/images/${post.getImageName()}" alt="image" width="800px">
		</c:if>
		<h2>${post.getTitle()}</h2>
		<small class="text-muted" style="font-size: 14px">
			<i class="bi bi-person"></i> ${author}
		</small>
		<hr style="background-color: #444">
		<p>${post.getText()}</p>
	</div>

</body>
</html>
