<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootswatch@4.5.2/dist/darkly/bootstrap.min.css" integrity="sha384-nNK9n28pDUDDgIiIqZ/MiyO3F4/9vsMtReZK39klb/MtkZI3/LtjSjlmyVPS3KdN" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css" integrity="sha384-xeJqLiuOvjUBq3iGOjvSQSIlwrpqjSHXpduPd6rQpuiM3f5/ijby8pCsnbu5S81n" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@6.2.0/css/fontawesome.min.css" integrity="sha384-z4tVnCr80ZcL0iufVdGQSUzNvJsKjEtqYZjiQrrYKlpGow+btDHDfQWkFjoaz/Zr" crossorigin="anonymous">
	<script src="${pageContext.request.contextPath}/js/jquery-3.6.1.min.js"></script>
	<title>Home</title>
	<style>
		.home-content {
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			background-color: black;
		}
	</style>
</head>
<body style="background: black">
	<jsp:include page="/html/breadcrumb.jsp" />
	<c:if test="${isAuth}">
		<div class="alert alert-primary" role="alert" style="text-align: center; font-size: 20px;">
			Welcome back, ${authUser.getLogin()}
			<div class="close-button" style="cursor: pointer; display: inline-block; margin-left: 5px;"><i class="bi bi-x-square"></i></div>
		</div>
	</c:if>

	<div class="home-content" style="margin-top: 40px;">
		<h2 style="font-family: 'ChineseRocks', sans-serif; margin-top: 25px;">outlaw portal</h2>
		<img width="800px" src="<c:url value="/img/outlaw_black.jpg"/>" alt="outlaw">
		<p style="font-size: 21px; width: 600px;text-align: center">
			На этом сайте можно узнать много нового про дикий запад конца 19-го века и его обитателей.
			Логотип, который вы видите на экране, нарисовал самолично автор сайта - Ахметшин К.Р.
			На сайте можно читать посты во вкладке Posts, а так же писать их самим. Так же есть возможность загрузить картинку в свою статью.
		</p>
	</div>

</body>
	<script>
        $(".close-button").click(function() {
            $(this).parent().hide("slow");
        });
	</script>
</html>
