<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>

<!DOCTYPE html>
<html>
<head>
<title>Cart</title>
<meta charset="utf-8">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Cairo&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="/css/cart.css">
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

			</div>
		</div>

		<div class="products">
			<c:if test="${items.size() == 0}">

				<div class="empty-cart">Your Cart Is Empty</div>
			</c:if>
			<f:form action="/cart/checkout" modelAttribute="checkedCart">
				<div class="total-proceed">
					<div class="sub-total">
						<p>
							Subtotal (<span> ${cart.count} </span>) items :
						</p>
						<sup class="bfore-price"> SR </sup> <span class="price">
							${cart.totalPrice} </span>
					</div>
					<div>
						<input type="submit" value="Proceed To Checkout" class="submit">
					</div>
				</div>

				<c:forEach items="${items}" var="item">
					<div class="card">
						<div class="check-item-cont">
							<!--		<f:checkbox cheacked="true" path="items."  class="check-item" value="${item}"/> -->
							<input type="checkbox" name="chekedItems" class="check-item"
								value="${item.cartItemId}" checked>
						</div>
						<div class="pro-img">
							<img src="${item.product.imgPath}" alt="">
						</div>

						<div class="desc">
							<p class="prod-name">${item.product.name}</p>
							<p class="prod-desc">${item.product.description}</p>
							<div class="opts">
								<div class="qty-select">
									<span>Qty</span> <select class="item-count"
										count="${item.itemCount}"
										prod-id="${item.product.id}"
										item-price="${item.product.price }"></select>
								</div>
								<p class="del-btn">Delete</p>


							</div>
						</div>
						<c:if test="${item.product.isActive}">
							<div class="price-cont">
								<sup class="bfore-price"> SR </sup> <span class="price">
									${item.product.price} </span>
							</div>
						</c:if>
					</div>
				</c:forEach>
			</f:form>
		</div>
	</div>
	<script type="text/javascript" src="/js/cart.js"></script>
</body>
</html>