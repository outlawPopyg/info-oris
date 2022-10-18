<%@ page import="models.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% User authUser = (User) request.getSession().getAttribute("authUser"); %>
<% boolean isAdmin = authUser != null && authUser.getRole().equals("admin"); %>
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

