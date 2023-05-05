<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/css/add-product.css">
<title>Update Product</title>
</head>
<body>

	<div class="form-container">
		<h1>Update a Product</h1>
		<div class="line"></div>
		<br>
		<f:form method="POST" modelAttribute="product" action="/products/update/validate"
			enctype="multipart/form-data">
			<f:hidden path="id"/>
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
			<f:errors path="category"></f:errors>
			<br>
			<p>Unit Numbers</p>
			<f:input type="number" path="unitNumbers" />
			<f:errors path="unitNumbers"></f:errors>
			<br>
			<p>is Active</p>
			<f:radiobutton value="true" path="isActive" />
			<p>present</p>
			<f:radiobutton vaue="false" path="isActive" />
			<p>not found</p>
			<br>
			<p>Image</p>
			<input name="file" type="file" accept="image/*" />
			<f:hidden path="imgPath"/>
			<br>
			<p>Description</p>
			<f:hidden path="seller"/>
			<f:textarea cols="30" rows="5" path="description" />
			<br>
			<br>
			<input type="submit" value="Update">
			<br>
			<br>
		</f:form>
	</div>
</body>

















</html>