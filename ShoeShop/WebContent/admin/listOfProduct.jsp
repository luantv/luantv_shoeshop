<%@page import="utility.NumberUtil"%>
<%!@SuppressWarnings("all")%>
<%@ page import="dao.impl.ProductDAOImpl"%>
<%@ page import="entities.Product"%>
<%@ page import="java.util.List"%>

<%
	/*
	Khởi tạo 1 danh sách chứa dữ liệu đã được xử lý từ controller là 1 danh sách Product
	dùng để hiển thị trên table
	 */
	List<Product> listOfProduct = (List<Product>) request
			.getAttribute("listOfProduct");

	// Nếu danh sách đó rỗng sẽ thông báo rằng Product đang được cập nhật
	if (listOfProduct.size() == 0) {
		out.println("<b class='notify'>Product Is Being Updating...</b><div class='separator'></div>");
	} else {
		/* 
		Ngược lại danh sách có product sẽ hiển thị chúng lên table,
		table sẽ có 2 chức năng là xem thông tin product và chức năng chuyển sản phẩm tới thùng rác
		thực tế việc này là update trường Status của sản phẩm đó thành 'Inactive', còn trang hiện tại
		sẽ chỉ hiển thị danh sách các product có trường Status là 'Active'
		 */
%>
<b class="notify">List Of Product Is Active</b>
<div class="separator"></div>

<!-- Thông báo message nếu có request -->
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
			title="Move Product <%=p.getProductName()%> To Trash"
			href="controller?action=moveProductToTrash&productId=<%=p.getProductId()%>"><img
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
		int totalProduct = productDaoImpl.count();

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
			out.println("<a href='controller?action=pageListOfProduct&page="
					+ previous + "'>Previous</a>");
			if (previous > 1)
				out.println("...");
		}
		if (numberOfPage > 1) {
			for (int i = pageIndex - 2; i <= pageIndex + 2; i++) {
				if (i == pageIndex) {
					out.println("<strong><a href='controller?action=pageListOfProduct&page="
							+ i
							+ "' style='color: #EEBB80;'>"
							+ i
							+ "</a></strong>");
				} else {
					if (i > 0 && i < numberOfPage) {
						out.println("<a href='controller?action=pageListOfProduct&page="
								+ i + "'>" + i + "</a>");
					}
				}
			}
		}
		if (pageIndex < numberOfPage) {
			int next = pageIndex + 1;
			if (next < numberOfPage)
				out.println("...");
			out.println("<a href='controller?action=pageListOfProduct&page="
					+ next + "'>Next</a>");
		}
		out.println("</div>");
	}
%>
