<%@ page import="models.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% User authUser = (User) request.getSession().getAttribute("authUser"); %>
<% boolean isAdmin = authUser != null && authUser.getRole().equals("admin"); %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css"
	  integrity="sha384-xeJqLiuOvjUBq3iGOjvSQSIlwrpqjSHXpduPd6rQpuiM3f5/ijby8pCsnbu5S81n" crossorigin="anonymous">

<style>
	@font-face {
		font-family: 'ChineseRocks';
		src: url("/fonts/chinese rocks rg.otf");
    }
</style>

<nav aria-label="breadcrumb">
	<ol class="breadcrumb">
		<li class="breadcrumb-item"><a href="<c:url value="/reg"/>">Sign up</a></li>
		<li class="breadcrumb-item"><a href="<c:url value="/auth"/>">Log in</a></li>
		<li class="breadcrumb-item"><a href="<c:url value="/home"/>">Home</a></li>
		<li class="breadcrumb-item"><a href="<c:url value="/posts"/>">Posts</a></li>
		<li class="breadcrumb-item"><a href="<c:url value="/posts/add"/>">Add</a></li>
		<% if (isAdmin) { %>
			<li class="breadcrumb-item"><a href="<c:url value="/posts/add/check"/>">Check</a></li>
		<% } %>
	</ol>
</nav>

