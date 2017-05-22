<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!@SuppressWarnings("all")%>
<%@ page import="java.util.List"%>
<%@ page import="entities.OrderDetailByOrderId"%>
<%@ page import="entities.ProductInOrders"%>
<%@ page import="utility.NumberUtil"%>

<style>
table.detail-orders tr td:first-child {
	font-weight: bold;
}

table.detail-orders tr {
	border-bottom: 1px solid #bbb;
}

table.detail-orders tr td {
	padding: 10px;
}

table.customer tr td {
	border-bottom: 1px solid #fff;
	padding: 5px;
}
</style>

<%
	OrderDetailByOrderId order = (OrderDetailByOrderId) request
			.getAttribute("detailOrders");
	List<ProductInOrders> listOfProduct = (List<ProductInOrders>) request
			.getAttribute("listOfProduct");
%>

<b class="notify">Orders Detail</b>
<div class="separator"></div>

<!-- Thông báo message nếu có request -->
${requestScope.message }

<table class="detail-orders">
	<tr>
		<td>Order Id:</td>
		<td><%=order.getOrderId()%></td>
	</tr>
	<tr>
		<td>User Name:</td>
		<td><%=order.getUserName()%></td>
	</tr>
	<tr>
		<td valign="top">Information Customer:</td>
		<td>
			<table class="customer">
				<tr>
					<td>Customer Name:</td>
					<td><%=order.getCustomerName()%></td>
				</tr>
				<tr>
					<td>Email:</td>
					<td><%=order.getEmail()%></td>
				</tr>
				<tr>
					<td>Phone Number:</td>
					<td><%=order.getPhoneNo()%></td>
				</tr>
				<tr>
					<td>Identity Card Number:</td>
					<td><%=order.getIdentityCardNo()%></td>
				</tr>
				<tr>
					<td>Description:</td>
					<td><%=order.getDescription()%></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>Shipping Address:</td>
		<td><%=order.getShippingAddress()%></td>
	</tr>
	<tr>
		<td>Date Of Posted:</td>
		<td><%=order.getDateOfPosted()%></td>
	</tr>
	<tr>
		<td>Status:</td>
		<td>
			<%
				if (order.getStatus().equals("Unapproved")) {
					out.println("<span style='color: red;'>Unapproved</span>");
				} else if (order.getStatus().equals("Approved")) {
					out.println("<span style='color: orange;'>Approved</span>");
				} else {
					out.println("<span style='color: green;'>Delivered</span>");
				}
			%>
		</td>
	</tr>
	<tr>
		<td valign="top">Product:</td>
		<td>
			<!-- Table product -->
			<table class="table-cart" style="margin: 0;">
				<tr>
					<td width="30px" style="font-weight: normal;">No.</td>
					<td width="350px">Product name</td>
					<td width="100px">Unit price</td>
					<td width="100px">Quantity</td>
					<td width="120px">Monetized</td>
				</tr>
				<%
					int i = 0;
					double t = 0d;
					for (ProductInOrders p : listOfProduct) {
						++i;
						t += (p.getQuantity() * p.getUnitPrice());
				%>
				<tr>
					<td><%=i%></td>
					<td style="text-align: left;"><img class="product"
						src="images/<%=p.getImage()%>" width="80px" height="70px" /> <span
						class="name"><%=p.getProductName()%></span><br />
						<div>
							<span class="product-id">Product Id: </span>
							<%=p.getProductId()%>
						</div></td>
					<td><%=NumberUtil.formatMoneyVietnam(p.getUnitPrice())%></td>
					<td>x<%=p.getQuantity()%></td>
					<td><%=NumberUtil.formatMoneyVietnam(p.getQuantity()
						* p.getUnitPrice())%></td>
				</tr>
				<%
					}
				%>
				<tr>
					<td colspan="5" style="font-weight: normal; font-size: 12px;">The
						total amount: <%=NumberUtil.formatMoneyVietnam(t)%></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<a class="btn"
	href="controller?action=exportToExcel&orderId=<%=order.getOrderId()%>">Export
	to Excel</a>