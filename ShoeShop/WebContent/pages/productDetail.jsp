<%!@SuppressWarnings("all")%>
<%@page import="controller.Controller"%>
<%@ page import="entities.Product"%>
<%@ page import="dao.impl.ProductDAOImpl"%>
<%@ page import="java.util.List"%>
<%@ page import="utility.NumberUtil"%>
<%
	// Lấy đối tượng product
	Product p = (Product) request.getAttribute("productDetail");

	// Lấy danh sách product liên quan
	List<Product> listOfRelatedProduct = (List<Product>) request
			.getAttribute("listOfRelatedProduct");

	// Lấy danh sách nhà sản xuất để in ra right menu
	List<String> listOfPublisher = (List<String>) request
			.getAttribute("listOfPublisher");
%>
<div class="row product">
	<div class="col-md-10">
		<!-- Product detail -->
		<div class="row product detail-product">
			<div class="col-xs-12 col-sm-4 col-md-4">
				<!-- Display image detail of product -->
				<img src="images/<%=p.getImage()%>" />
			</div>
			<div class="col-xs-12 col-sm-8 col-md-8">
				<div class="name"><%=p.getProductName()%></div>
				<div class="product-id">
					<b style="color: #1094CF;">Product Id</b>: <span
						class="font-regular"><%=p.getProductId()%></span>
				</div>
				<div class="publisher">
					Trademark: <span class="font-regular"><%=p.getPublisher()%></span>
				</div>
				<div class="price">
					Unit Price: <b style="color: #BD2A2A;"><%=NumberUtil.formatMoneyVietnam(p.getUnitPrice())%></b>
				</div>
				<div class="description">
					Description: <span class="font-regular"><%=p.getDescription()%></span>
				</div>
				<div class="color">
					Color: <span class="font-regular">Like picture</span>
				</div>
				<div class="status">
					Remaining quantity: <span class="font-regular"><%=p.getQuantity()%></span>
				</div>
				<div>
					<!-- Thêm vào giỏ hàng và chuyển tới trang tiến hành thanh toán -->
					<div>
						<button class="btn">
							<a
								href="controller?action=buyNow&productId=<%=p.getProductId()%>">Buy
								now</a>
						</button>
						<span class="font-regular">or</span>
					</div>

					<!-- Thêm product vào giỏ hàng -->
					<div>
						<button class="btn">
							<a
								href="controller?action=addToCart&productId=<%=p.getProductId()%>">Add
								to cart</a>
						</button>
					</div>
				</div>
			</div>
		</div>
		<!-- End Product detail -->

		<!-- Related Products -->
		<div class="row product">
			<!-- Head title -->
			<div class="head-title">Related Products</div>
			<%
				for (Product pRelated : listOfRelatedProduct) {
			%>
			<!-- Content here -->
			<div class="relate-product col-xs-12 col-sm-6 col-md-4">
				<a
					href="controller?action=productDetail&productId=<%=pRelated.getProductId()%>"
					title="View Detail <%=pRelated.getProductName()%>"><img
					src="images/<%=pRelated.getImage()%>" /></a>
				<div class="name">
					<a
						href="controller?action=productDetail&productId=<%=pRelated.getProductId()%>"><%=pRelated.getProductName()%></a>
				</div>
				<div class="publisher">
					<a
						href="controller?action=findProductByPublisher&name=<%=pRelated.getPublisher()%>"><%=pRelated.getPublisher()%></a>
				</div>
				<div class="price">
					<b><%=NumberUtil.formatMoneyVietnam(pRelated.getUnitPrice())%></b>
				</div>
			</div>
			<%
				}
			%>
		</div>
		<!-- End Related Products -->
	</div>

	<!-- Show category -->
	<div class="col-md-2" style="height: 100%;">
		<!-- Display all category (pulisher/trademark) -->
		<div class="category">
			<div>All Brands</div>
			<ul>
				<%
					for (String publiser : listOfPublisher) {
				%>
				<li><a
					href="controller?action=findProductByPublisher&name=<%=publiser%>"><%=publiser%></a></li>
				<%
					}
				%>
			</ul>
		</div>

		<!-- Online support -->
		<div class="online-support">
			<div class="title">Online Support</div>
			<img src="images/online-support.png" />
			<div class="phone">HOTLINE: (08).3820.3483</div>
			<div class="phone">Sales 1: 01277.333.567</div>
			<div class="phone">Sales 2: 0983.889.876</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="separator col-md-12 col-sm-12 col-xs-12"></div>
</div>