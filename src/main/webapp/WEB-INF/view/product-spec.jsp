<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<!DOCTYPE html>
<html>
<head>
<title>Product Details</title>
<meta charset="utf-8">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Cairo&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="/css/prod-details.css">

</head>

<body>
	<div class="container">

		<div class="header">
			<a href="/products/all" class="app-name"><p>AZ Shop</p></a>
			<div class="search-container">
				<div>
					<input type="search" class="search">
					<button class="btn-srch">search</button>
				</div>
				<div class="products-result"></div>
			</div>
			<sec:authorize access="isAuthenticated()">

				<sec:authorize access="hasRole('ADMIN')">

					<div class="admin-cont">

						<a href="/profile"><div class="profile">ADMIN</div></a> <a
							class="admin-add-prods" href="/products/add">Add Products</a> <a
							class="log-out" href="/logout">LogOut</a>
					</div>
				</sec:authorize>

				<sec:authorize access="!hasRole('ADMIN')">

					<div class="cart-profile">
						<div class="header-cart">
							<div class="shop-cart">
								<p class="items-price">${cart.getTotalPrice()}</p>
								<p class="no-items">${cart.getCount()}</p>
							</div>
							<div class="cart-img">
								<img src="/images/cart.png">
							</div>
						</div>

						<a href="/profile"><div class="profile">${user}</div></a> <a
							class="log-out" href="/logout">LogOut</a>
					</div>
				</sec:authorize>


			</sec:authorize>

			<sec:authorize access="!isAuthenticated()">
				<div class="login-container">
					<a href="/login" class="login">Login </a> <a href="/signUp"
						class="signup">SignUp</a>
				</div>
			</sec:authorize>
		</div>
		<div class="details-info">

			<div class="details">
				<div class="img-cont">
					<img src="${product.imgPath}" alt="">
				</div>
				<div class="spec">
					<div>
						<p class="name">${product.name}</p>
						<p class="desc">${product.description}</p>
					<input type="hidden" class="hidden-id" id-value="${product.id}"> 
					</div>
					<f:form modelAttribute="item" action="/cart/addItem" method="POST">
						<div class="pro-info">
							<p class="available">In Stock</p>
							<p class="unavailable">Currently Unavailable</p>
							<div class="price-cont">
								<sup class="bfore-price"> SR </sup> <span class="price">
									${product.price} </span>
							</div>

							<span>QTY</span>
							<f:select path="itemCount" class="qty"
								item-count="${item.itemCount}">
							</f:select>

						</div>
						<div>
							<input type="submit" class="cart" value="Add To Cart"> <input
								type="submit" class="buy" value="Buy Now">
						</div>
					 	<input type="hidden" name="prodId" value="${product.id} ">  

					</f:form>
				</div>

			</div>
		</div>
	</div>
	<script src="/js/prod-details.js" type="text/javascript">
		
	</script>
</body>

</html>