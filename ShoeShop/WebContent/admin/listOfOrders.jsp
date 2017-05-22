<%@ page import="dao.impl.OrdersDAOImpl"%>
<%@ page import="entities.OrderDisplay"%>
<%@ page import="utility.NumberUtil"%>
<%@ page import="java.util.List"%>
<%!@SuppressWarnings("all")%>
<%
	/*
	 Khởi tạo 1 danh sách chứa dữ liệu đã được xử lý từ controller là 1 danh sách User
	 dùng để hiển thị trên table
	 */
	List<OrderDisplay> listOfOrders = (List<OrderDisplay>) request
			.getAttribute("listOfOrders");

	//Nếu danh sách đó rỗng sẽ thông báo rằng danh sách đơn hàng rỗng
	if (listOfOrders.size() == 0) {
		out.println("<b class='notify'>Orders Is Empty</b><div class='separator'></div>");
	} else {
		/*
		Ngược lại hiển thị tất cả các đơn hàng
		 */
%>
<b class="notify">List Of Orders</b>
<div class="separator"></div>

<!-- Thông báo message nếu có request -->
${requestScope.message }

<table class="list-of-user">
	<tr>
		<th>No.</th>
		<th>Order Id</th>
		<th>User Name</th>
		<th>Customer Name</th>
		<th>Total Price</th>
		<th>Date Of Posted</th>
		<th>Status</th>
		<th width="100px">Function</th>
	</tr>
	<%
		int no = (Integer) request.getAttribute("no");
			for (OrderDisplay o : listOfOrders) {
				++no;
	%>

	<tr>
		<td><%=no%></td>
		<td><%=o.getOrderId()%></td>
		<td><%=o.getUserName()%></td>
		<td><%=o.getCustomerName()%></td>
		<td><%=NumberUtil.formatMoneyVietnam(o.getTotalPrice())%></td>
		<td><%=o.getDateOfPosted()%></td>
		<td>
			<%
				if (o.getStatus().equals("Unapproved")) {
							out.println("<span style='color: red;'>Unapproved</span>");
						} else if (o.getStatus().equals("Approved")) {
							out.println("<span style='color: orange;'>Approved</span>");
						} else {
							out.println("<span style='color: green;'>Delivered</span>");
						}
			%>
		</td>
		<td><a title="View Detail This Orders"
			href="controller?action=viewDetailOrders&orderId=<%=o.getOrderId()%>"><img
				src="images/infor.png" width="16px" height="16px" /></a>&nbsp; <a
			title="Update User & Status Of This Orders"
			href="controller?action=pageUpdateOrders&orderId=<%=o.getOrderId()%>&status=<%=o.getStatus()%>"><img
				src="images/settings.png" /></a>&nbsp; <a
			title="Delete Permanently Orders <%=o.getOrderId()%>"
			href="controller?action=deletePermanentlyOrders&orderId=<%=o.getOrderId()%>"
			onclick="return confirm('You sure you want to permanently delete orders <%=o.getOrderId()%>?')"><img
				src="images/trash.png" width="16px" height="16px" /></a></td>
	</tr>
	<%
		}
	%>
</table>
<%
	// Phân trang
		OrdersDAOImpl orderDaoImpl = new OrdersDAOImpl();
		// Tổng số đơn hàng trong database
		int totalOrder = orderDaoImpl.count();

		// Số đơn hàng hiển thị trên 1 trang
		int numberOrderDispay = 5;

		// Nhận số trang với tổng số đơn hàng
		int numberOfPage;
		if (totalOrder % numberOrderDispay == 0) {
			numberOfPage = totalOrder / numberOrderDispay;
		} else {
			numberOfPage = (totalOrder / numberOrderDispay) + 1;
		}

		int pageIndex = Integer.parseInt(request.getParameter("page"));
		out.println("<div id='pagination'>");
		if (pageIndex > 1) {
			int previous = pageIndex - 1;
			out.println("<a href='controller?action=pageListOfOrders&page="
					+ previous + "'>Previous</a>");
			if (previous > 1)
				out.println("...");
		}
		if (numberOfPage > 1) {
			for (int i = pageIndex - 2; i <= pageIndex + 2; i++) {
				if (i == pageIndex) {
					out.println("<strong><a href='controller?action=pageListOfOrders&page="
							+ i
							+ "' style='color: #EEBB80;'>"
							+ i
							+ "</a></strong>");
				} else {
					if (i > 0 && i < numberOfPage) {
						out.println("<a href='controller?action=pageListOfOrders&page="
								+ i + "'>" + i + "</a>");
					}
				}
			}
		}
		if (pageIndex < numberOfPage) {
			int next = pageIndex + 1;
			if (next < numberOfPage)
				out.println("...");
			out.println("<a href='controller?action=pageListOfOrders&page="
					+ next + "'>Next</a>");
		}
		out.println("</div>");
	}
%>