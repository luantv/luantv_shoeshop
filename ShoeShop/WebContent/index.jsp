<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!@SuppressWarnings("all") %>
<%@ page import="controller.Controller"%>
<%@ page import="entities.Product"%>
<%@ page import="utility.ProductInCart"%>
<%@ page import="utility.Cart"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Include CSS Bootstrap -->
<link href="css/bootstrap.css" rel="stylesheet" />
<link href="css/bootstrap.min.css" rel="stylesheet" />
<link href="css/style.css" rel="stylesheet" />

<!-- Include JS  Bootstrap-->
<script src="js/jquery-2.1.4.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script>
	$(document).ready(function() {

	});
</script>
</head>
<body>
	<div class="container">
		<!-- Logo -->
		<div class="row">
			<div class="col-md-6 col-sm-6">
				<a href="forward.jsp"><img src="images/logo.jpg" width="50%" /></a>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-6 cart-and-search">
				<div style="float: right;">
					<!-- Form search product -->
					<form action="controller?action=productSearch" method="post">
						<input type="text" name="q" class="input-search"
							placeholder="Search..." /> <input type="submit"
							style="display: none;" />
					</form>
					<!-- End form search product -->
				</div>
				<!-- Get cart -->
				<%
					Cart cart = (Cart) session.getAttribute("cart");
				%>
				<div style="float: right; margin-top: 7px;">
					<span class="cart"> <a href="controller?action=viewCart"><img
							src="images/icon-cart.png" /> Cart (<%=cart.count()%>)</a>
					</span>
				</div>
			</div>
		</div>

		<!-- Slide Show -->
		<jsp:include page="pages/slideshow.jsp"></jsp:include>

		<!-- Product -->
		<%
			if (Controller.displayAs.equalsIgnoreCase("pageListOfAllProduct")) {
		%>
		<!-- New Products -->
		<jsp:include page="pages/listOfNewProduct.jsp"></jsp:include>
		<!-- End New Products -->

		<!-- Selling Products -->
		<jsp:include page="pages/listOfSellingProduct.jsp"></jsp:include>
		<!-- End Selling Products -->

		<jsp:include page="pages/listOfAllProduct.jsp"></jsp:include>
		<%
			} else if (Controller.displayAs
					.equalsIgnoreCase("pageListOfProductSearch")) {
		%>
		<jsp:include page="pages/listOfProductResultSearch.jsp"></jsp:include>
		<%
			} else if (Controller.displayAs
					.equalsIgnoreCase("pageListOfProductByPublisher")) {
		%>
		<jsp:include page="pages/listOfProductByPublisher.jsp"></jsp:include>
		<%
			} else if (Controller.displayAs
					.equalsIgnoreCase("pageProductDetail")) {
		%>
		<jsp:include page="pages/productDetail.jsp"></jsp:include>
		<%
			} else if (Controller.displayAs.equalsIgnoreCase("pageViewCart")) {
		%>
		<jsp:include page="pages/cart.jsp"></jsp:include>
		<%
			} else if (Controller.displayAs.equalsIgnoreCase("pagePay")) {
		%>
		<jsp:include page="pages/pay.jsp"></jsp:include>
		<%
			} else if (Controller.displayAs.equalsIgnoreCase("pagePaySuccess")) {
		%>
		<jsp:include page="pages/paySuccess.jsp"></jsp:include>
		<%
			}
		%>
		<!-- End Product -->

		<div class="row">
			<div class="separator col-md-12 col-sm-12 col-xs-12"></div>
		</div>

		<!-- Footer -->
		<div class="row product footer">
			<div class="col-xs-12 col-sm-12 col-md-4">
				<div class="title">SHOE SHOP ONLINE</div>
				<div>Address: 193, Thuong Tin district, Hanoi city.</div>
				<div>Phone No: 0168.616.4232</div>
				<div>
					Hotline: <b>(08).3820.3483</b>
				</div>
				<div>
					Email: <b>shoeshopvn@gmail.com</b>
				</div>
				<div>Website: www.shoeshop.com</div>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-4">
				<div class="title">
					<a href="">ABOUT US</a>
				</div>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-4">
				<div class="copyright">shoeshop.com © 2015 - 2016</div>
			</div>
		</div>
		<!-- End Footer -->
	</div>
</body>

</html>
