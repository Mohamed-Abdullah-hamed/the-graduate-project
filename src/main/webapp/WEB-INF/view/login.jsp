<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="/css/login.css" rel="stylesheet">
<title>Login</title>
</head>
<body>

	<div class="parent-container">
	<c:if test="${param.error != null}">
		<span>Invalid Email Or Password</span>
	</c:if>
	<div class="container">
		<h1>Please Login To Continue</h1>
		<br>
		<f:form method="POST" action="/login">
			<label for="email">Email </label>
			<input name="email" id="email">
			<br>
			<label for="pass">Password </label>
			<input type="password" name="password" id="pass">
			<br>
			<input class="submit" type="submit">
		</f:form>
	</div>
	</div>
</body>
</html>