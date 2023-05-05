<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Profile</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Cairo&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="/css/profile.css">
</head>
<body>
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
							<a href="/cart"><img src="/images/cart.png"></a>
						</div>
					</div>

					<a href="/profile"><div class="profile">${cust.getUser().getEmail().substring(0,2)}</div></a>
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
	<div class="containr">
		<div class="dashboard">
			<div class="Basic-Info">Profile</div>
			<div class="account-sec">Account Security</div>
			<sec:authorize access="hasRole('SELLER')">
			<div class="seller-products">Products</div>
			</sec:authorize>
		</div>
		<h2>Profile Info</h2>
		<div class="profile-container">
			<f:form action="/update" modelAttribute="cust">
				<f:hidden path="address.id" />
				<f:hidden path="id" />
				<label for="cust-name">Name </label>
				<br>
				<f:errors path="name"></f:errors>
				<f:input path="name" id="cust-name" />

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
				<input type="submit" value="Update">
			</f:form>
		</div>
		<div class="sec-container">
			<f:form modelAttribute="userDto" action="/changeInfo">
				<c:if test="${!isValidInfo}">
				<c:out value="${isValidInfo }"></c:out>
				<span>Email or Password Is Incorrect</span>
				</c:if>
			
				<label for="user-email">Current Email </label>
				<br>
				<f:hidden path="id" value="${cust.getUser().id}"/>
				<f:errors path="email"></f:errors>
				<f:input path="email" id="user-email" />
				<br>
				
				<label for="user-pass">Current Password </label>
				<br>
				<f:errors path="password"></f:errors>
				<f:input type="password" path="password" id="user-pass" />
				<br>
				<label for="new-Email">New Email </label>
				<br>
				<f:errors path="newEmail"></f:errors>
				<f:input path="newEmail" id="new-email" />
				<br>
				<label for="new-pass">New Password </label>
				<br>
				<f:errors path="newPassword"></f:errors>
				<f:input type="password" path="newPassword" id="new-pass" />
				<br>
				<label for="re-pass">Re Write New Password </label>
				<br>
				<f:errors path="rePassword"></f:errors>
				<f:input type="password" path="rePassword" id="re-pass" />
				<br>
				<input type="submit" value="Save">
			</f:form>
		</div>
		<sec:authorize access="hasRole('SELLER')">
		<div class="seller-container">
			<p>to Add Products click <span class="add-prd"> here</span></p>
			<c:if test="${items.size() == 0}">

				<div class="empty-cart">You have No Products</div>
			</c:if>
				<c:forEach items="${items}" var="item">
					<div class="card">
						<div class="pro-img">
							<img src="${item.imgPath}" alt="">
						</div>

						<div class="desc">
							<p class="prod-name">${item.name}</p>
							<p class="prod-desc">${item.description}</p>
							<div class="opts">
								<p class="update-prod" proId="${item.id}">Update</p>
								<p class="del-btn" proId="${item.id}">Delete</p>
							</div>
						</div>
						<c:if test="${item.isActive}">
							<div class="price-cont">
								<sup class="bfore-price"> SR </sup> <span class="price">
									${item.price} </span>
							</div>
						</c:if>
					</div>
				</c:forEach>
		
		</div>
		</sec:authorize>
		
	</div>
	<script type="text/javascript" src="/js/profile.js"></script>
</body>
</html>