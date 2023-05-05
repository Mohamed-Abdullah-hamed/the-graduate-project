<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/css/sign-up.css">
<title>SignUp</title>
</head>
<body>
	<div class="containr">
	<h2>SignUP</h2>
	<div class="form-container">
		<f:form action="/validate" modelAttribute="cust" autocomplete="off">
			<label for="cust-name">Name </label>
			<br>
			<f:errors path="name"></f:errors>
			<f:input path="name" id="cust-name" />
			<br>
			<label for="user-email">Email </label>
			
			<br>
			<f:errors path="user.email"></f:errors>
			<f:input path="user.email" id="user-email" />
			<br>
			<label for="user-pass">Password </label>
			<br>
			<f:errors path="user.password"></f:errors>
			<f:input path="user.password" id="user-pass" type="password"/>
			<br>
			<label for="cust-city">City </label>
			<br>
			<f:errors path="address.city"></f:errors>
			<f:input path="address.city" id="cust-city" />
			<br>
			<label for="cust-district">District </label>
			<br>
			<f:errors path="address.district"></f:errors>
			<f:input path="address.district" id="cust-district" />
			<br>
			<label for="cust-street">Street </label>
			<br>
			<f:errors path="address.street"></f:errors>
			<f:input path="address.street" id="cust-street" />
			<br>
			<label for="cust-zCode">Zip Code </label>
			<br>
			<f:errors path="address.zipCode"></f:errors>
			<f:input path="address.zipCode" id="cust-zCode" />
			<br>
			<label for="user-type">Which Type You Will Be ? </label>
			<br>
			<f:radiobutton path="userType"  value="Seller" cssStyle="width:3%;" id="user-type"/> Seller
			<f:radiobutton path="userType"  value="Customer" cssStyle="width:3%;margin-left: 115px" id="user-type"/> Customer
			<br>
			<input type="submit" value="Save" id="submit">
		</f:form>
	</div>
	</div>
	<script type="text/javascript" src="/js/sign-up.js"></script>
</body>
</html>