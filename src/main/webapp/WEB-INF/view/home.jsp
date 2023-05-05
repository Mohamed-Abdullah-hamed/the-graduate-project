<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>AZ-Shop</title>
<meta charset="utf-8">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Cairo&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="/css/all-products.css">

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
			
			<a href="/profile"><div class="profile">ADMIN</div></a>
			<a class="admin-add-prods" href="/products/add">Add Products</a>
			<a class="log-out" href="/logout">LogOut</a>
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

					<a href="/profile"><div class="profile">${user}</div></a>
					<a class="log-out" href="/logout">LogOut</a>
				</div>
			</sec:authorize>
			
				
			</sec:authorize>

			<sec:authorize access="!isAuthenticated()">
  					<div class="login-container">
  					<a href="/login" class="login">Login </a>
  					<a href="/signUp" class="signup">SignUp</a>		
  					</div>
			</sec:authorize>
			
		</div>
		<div class="categories">
			<a href="/products/all"><p>All Products</p></a>
			<c:forEach items="${categories}" var="categ">
				<a href="/products/category/${categ.getKey()}"><p>${categ.getValue()}</p></a>
			</c:forEach>
		</div>
		<div class="products">
			<c:if test="${products.size() == 0}">

				<div style="font-size: 30px; font-weight: bold;">Sorry, there
					is No match Items</div>
			</c:if>
			<c:forEach items="${products}" var="prod">

				<sec:authorize access="!hasRole('ADMIN')">
				<div class="card">

					<div class="pro-img">
						<img src="${prod.imgPath}" alt="">
					</div>

					<div class="desc">
						<p class="prod-name">${prod.name}</p>
						<p class="prod-desc">${prod.description}</p>
						<c:if test="${prod.isActive}">
							<div class="price-cont">
								<sup class="bfore-price"> SR </sup> <span class="price">
									${prod.price} </span>
							</div>
						</c:if>

					</div>
					<a href="/products/id/${prod.id}" class="prod-url">
						<div class="show-info">
							<p>Show More Info</p>
							<i class="fas fa-long-arrow-alt-right"></i>
						</div>
					</a>
				</div>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ADMIN')">
				<div class="card">
					<a class="rmv-prd" href="/products/delete/${prod.id}">
					<div class="remove-prod">
					<div class="bef"></div>
					<div class="aft"></div>
					</div>
					</a>
					<div class="pro-img">
						<img src="${prod.imgPath}" alt="">
					</div>

					<div class="desc">
						<p class="prod-name">${prod.name}</p>
						<p class="prod-desc">${prod.description}</p>
						<c:if test="${prod.isActive}">
							<div class="price-cont">
								<sup class="bfore-price"> SR </sup> <span class="price">
									${prod.price} </span>
							</div>
						</c:if>

					</div>
					<a href="/products/update/${prod.id}" class="prod-url">
						<div class="show-info">
							<p>Update</p>
							<i class="fas fa-long-arrow-alt-right"></i>
						</div>
					</a>
				</div>
				</sec:authorize>

			</c:forEach>

		</div>
	</div>
	<script src="https://kit.fontawesome.com/0d91f27fc0.js"
		crossorigin="anonymous"></script>
	<script src="/js/home.js" type="text/javascript"></script>

</body>

</html>
