<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!@SuppressWarnings("all")%>
<%@ page import="java.util.List"%>
<%@ page import="entities.User"%>

<b class="notify">Update Orders</b>
<div class="separator"></div>

<%
	// Lấy giá trị từ các request
	List<User> listOfUser = (List<User>) request
			.getAttribute("listOfUser");
	String status = (String) request.getAttribute("status");
	int userId = (Integer) request.getAttribute("userId");
%>
<style>
table.update-orders {
	margin: 20px auto;
}

table.update-orders tr td {
	padding: 20px 40px;
}

table.update-orders tr td div {
	margin-bottom: 20px;
}

table.update-orders tr td:first-child {
	border-right: 1px solid #bbb;
}
</style>
<form action="controller?action=updateOrders" method="post">
	<table class="update-orders">
		<tr>
			<td valign="top">
				<div>
					<b>Choose the shipper</b>
				</div> <select name="user" style="width: 170px;">
					<%
						for (User u : listOfUser) {
					%>
					<option value="<%=u.getUserId()%>"
						<%if (userId == u.getUserId())
					out.println("selected");%>><%=u.getFullName()%></option>
					<%
						}
					%>
			</select>
			</td>
			<td>
				<div>
					<b>Choose the status</b>
				</div>
				<div>
					<input type="radio" name="status" value="Unapproved"
						<%if (status.equals("Unapproved"))
				out.println("checked");%>>
					<span style="color: red;">Unapproved</span>
				</div>
				<div>
					<input type="radio" name="status" value="Approved"
						<%if (status.equals("Approved"))
				out.println("checked");%>>
					<span style="color: orange;">Approved</span>
				</div>
				<div>
					<input type="radio" name="status" value="Delivered"
						<%if (status.equals("Delivered"))
				out.println("checked");%>> <span
						style="color: green;">Delivered</span>
				</div>
			</td>
		</tr>
	</table>
	<div style="text-align: center;">
		<input type="hidden" name="orderId"
			value="<%=request.getAttribute("orderId")%>" /> <input
			type="submit" format="btn" value="Update"
			style="margin-right: -50px;" />
	</div>
</form>
