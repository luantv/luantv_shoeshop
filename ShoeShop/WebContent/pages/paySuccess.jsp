<%!@SuppressWarnings("all") %>
<%@page import="controller.Controller"%>
<%@ page import="entities.Product"%>
<%@ page import="dao.impl.ProductDAOImpl"%>
<%@ page import="java.util.List"%>
<div class="row product">
	<div class="col-md-10">
		<%
			//Lấy danh sách nhà sản xuất để in ra right menu
			List<String> listOfPublisher = (List<String>) request
					.getAttribute("listOfPublisher");
		%>
		<div
			style='margin-top: 10px; font-size: 13px; text-align: center; font-weight: bold;'>Orders sent successfully</div>
		<div style='margin-top: 10px; font-size: 13px; text-align: center;'>
			Thank You for Your purchase, Your order has been sent successfully, we will check and confirm Your order, then we will ship to You as soon as possible, payments after receipt. Once again thank you!
		</div>
		<div class="group-btn">
			<a class="btn" href="forward.jsp">Home page</a>
		</div>
	</div>
	<!-- End show cart -->

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