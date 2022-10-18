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
