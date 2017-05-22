<%!@SuppressWarnings("all")%>
<%@ page import="dao.impl.ProductDAOImpl"%>
<%@ page import="entities.Product"%>
<%@ page import="java.util.List"%>
<%@ page import="utility.NumberUtil"%>

<%
	/*
	Khởi tạo 1 đối tượng danh sách product để lưu trữ danh sách product có Status là 'Inactive'
	và hiển thị chúng
	 */
	List<Product> listOfProduct = (List<Product>) request
			.getAttribute("listOfProductInTrash");
	if (listOfProduct.size() == 0) {
		// Nếu danh sách trên không có product nào thì thông báo
		out.println("<b class='notify'>No Product In Trash</b><div class='separator'></div>");
	} else {
		// Nếu có product trong danh sách thì hiển thị, ở trang này chức năng xóa sẽ xóa vĩnh viễn product đó khỏi database
%>
<b class='notify'>List Of Product Is Inactive</b>
<div class="separator"></div>
${requestScope.message }
<table id="show">
	<tr>
		<th>No.</th>
		<th>Product Id</th>
		<th>Product Name</th>
		<th>Publisher</th>
		<th>Unit Price</th>
		<th>Image</th>
		<th>Date Posted</th>
		<th>Function</th>
	</tr>
	<%
		int no = (Integer) request.getAttribute("no");
			for (Product p : listOfProduct) {
				++no;
	%>

	<tr>
		<td><%=no%></td>
		<td><%=p.getProductId()%></td>
		<td><%=p.getProductName()%></td>
		<td><%=p.getPublisher()%></td>
		<td><%=NumberUtil.formatMoneyVietnam(p.getUnitPrice())%></td>
		<td><img src="images/<%=p.getImage()%>" width="80px"
			height="50px" /></td>
		<td><%=p.getDatePosted()%></td>
		<td><a title="View Detail Product <%=p.getProductName()%>"
			href="controller?action=pageDetailProduct&productId=<%=p.getProductId()%>"><img
				src="images/infor.png" width="16px" height="16px" /></a>
			&nbsp;&nbsp;&nbsp; <a
			title="Delete Permanently Product <%=p.getProductName()%>"
			href="controller?action=deletePermanentlyProduct&productId=<%=p.getProductId()%>"
			onclick="return confirm('You sure you want to permanently delete <%=p.getProductName()%>?')"><img
				src="images/trash.png" width="16px" height="16px" /></a></td>
	</tr>
	<%
		}
	%>
</table>
<%
	// Phân trang
		ProductDAOImpl productDaoImpl = new ProductDAOImpl();
		// Tổng số sản phẩm
		int totalProduct = productDaoImpl.countInactive();

		// Số sản phẩm hiển thị trên 1 trang
		int numberProductDispay = 5;

		// Nhận số trang với tổng số sản phẩm
		int numberOfPage;
		if (totalProduct % numberProductDispay == 0) {
			numberOfPage = totalProduct / numberProductDispay;
		} else {
			numberOfPage = (totalProduct / numberProductDispay) + 1;
		}

		int pageIndex = Integer.parseInt(request.getParameter("page"));
		out.println("<div id='pagination'>");
		if (pageIndex > 1) {
			int previous = pageIndex - 1;
			out.println("<a href='controller?action=pageListOfProductInTrash&page="
					+ previous + "'>Previous</a>");
			if (previous > 1)
				out.println("...");
		}
		if (numberOfPage > 1) {
			for (int i = pageIndex - 2; i <= pageIndex + 2; i++) {
				if (i == pageIndex) {
					out.println("<strong><a href='controller?action=pageListOfProductInTrash&page="
							+ i
							+ "' style='color: #EEBB80;'>"
							+ i
							+ "</a></strong>");
				} else {
					if (i > 0 && i < numberOfPage) {
						out.println("<a href='controller?action=pageListOfProductInTrash&page="
								+ i + "'>" + i + "</a>");
					}
				}
			}
		}
		if (pageIndex < numberOfPage) {
			int next = pageIndex + 1;
			if (next < numberOfPage)
				out.println("...");
			out.println("<a href='controller?action=pageListOfProductInTrash&page="
					+ next + "'>Next</a>");
		}
		out.println("</div>");
	}
%>