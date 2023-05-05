<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/css/add-product.css">
<title>Add Product</title>
</head>
<body>

	<div class="form-container">
		<h1>Submit a Product</h1>
		<div class="line"></div>
		<br>
		<f:form method="POST" modelAttribute="product" action="validate" enctype="multipart/form-data">
			<p>Name</p>
			<f:input path="name" /> 
			<br>
			<f:errors path="name"></f:errors>
			<br>
			<p>Price</p>
			<f:input path="price" />
			<br>
			<f:errors path="price"></f:errors>
			<br>
			<p>Category</p>
			<f:select items="${categories}" path="category" class="category"></f:select>
			<br>
			<p>Unit Numbers</p>
			<f:input type="number" path="unitNumbers" />
			<br>
			<p>is Active</p>
			<f:radiobutton value="true" path="isActive" />
			<p>present</p>
			<f:radiobutton vaue="false" path="isActive" />
			<p>not found</p>
			<br>
			<p>Image</p>
			<input name="file" type="file" accept="image/*"/>
			<br>
			<f:hidden path="seller"/>
			<p>Description</p>

			<f:textarea cols="30" rows="5" path="description" />
			<br>
			<br>
			<input type="submit" value="submit">
			<br>
			<br>
		</f:form>
	</div>
</body>

















</html>