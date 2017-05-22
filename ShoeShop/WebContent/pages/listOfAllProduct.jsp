<%!@SuppressWarnings("all") %>
<%@ page import="dao.impl.ProductDAOImpl"%>
<%@ page import="entities.Product"%>
<%@ page import="java.util.List"%>
<%@ page import="utility.NumberUtil"%>
<%
	/*
	Khởi tạo 1 danh sách chứa dữ liệu đã được xử lý từ controller là 1 danh sách Product
	dùng để hiển thị trên table
	 */
	List<Product> listOfAllProduct = (List<Product>) request
			.getAttribute("listOfAllProduct");

	// Nếu danh sách đó rỗng sẽ thông báo rằng Product đang được cập nhật
	if (listOfAllProduct.size() == 0) {
		out.println("<b class='notify'>Product Is Being Updating...</b><div class='separator'></div>");
	} else {
		/* 
		Ngược lại danh sách có product sẽ hiển thị chúng lên table,
		table sẽ có 2 chức năng là xem thông tin product và chức năng chuyển sản phẩm tới thùng rác
		thực tế việc này là update trường Status của sản phẩm đó thành 'Inactive', còn trang hiện tại
		sẽ chỉ hiển thị danh sách các product có trường Status là 'Active'
		 */
%>
<div class="row product">
	<!-- Head title -->
	<div class="head-title">List Of All Products</div>
	<%
		for (Product p : listOfAllProduct) {
	%>
	<div class="show-product col-xs-12 col-sm-6 col-md-4">
		<a
			href="controller?action=productDetail&productId=<%=p.getProductId()%>"
			title="View Detail <%=p.getProductName()%>"><img
			src="images/<%=p.getImage()%>" /></a>
		<div class="name">
			<a
				href="controller?action=productDetail&productId=<%=p.getProductId()%>"><%=p.getProductName()%></a>
		</div>
		<div class="publisher">
			<a
				href="controller?action=findProductByPublisher&name=<%=p.getPublisher()%>"><%=p.getPublisher()%></a>
		</div>
		<div class="price">
			<b><%=NumberUtil.formatMoneyVietnam(p.getUnitPrice())%></b>
		</div>
	</div>
	<%
		}
	%>
	<%
		// Phân trang
			ProductDAOImpl productDaoImpl = new ProductDAOImpl();
			// Tổng số sản phẩm
			int totalProduct = productDaoImpl.count();

			// Số sản phẩm hiển thị trên 1 trang
			int numberProductDispay = 10;

			// Nhận số trang với tổng số sản phẩm
			int numberOfPage;
			if (totalProduct % numberProductDispay == 0) {
				numberOfPage = totalProduct / numberProductDispay;
			} else {
				numberOfPage = (totalProduct / numberProductDispay) + 1;
			}

			int pageIndex = Integer.parseInt(request.getParameter("page"));
			out.println("<div id='pagination' style='clear: both;'>");
			if (pageIndex > 1) {
				int previous = pageIndex - 1;
				out.println("<a href='controller?action=pageListOfAllProduct&page="
						+ previous + "'>Previous</a>");
				if(previous > 1)
				out.println("...");
			}
			if (numberOfPage > 1) {
				for (int i = pageIndex - 2; i <= pageIndex + 2; i++) {
					if (i == pageIndex) {
						out.println("<strong><a href='controller?action=pageListOfAllProduct&page="
								+ i
								+ "' style='color: #EEBB80;'>"
								+ i
								+ "</a></strong>");
					} else {
						if (i > 0 && i < numberOfPage) {
							out.println("<a href='controller?action=pageListOfAllProduct&page="
									+ i + "'>" + i + "</a>");
						}
					}
				}
			}
			if (pageIndex < numberOfPage) {
				int next = pageIndex + 1;
				if(next < numberOfPage)
				out.println("...");
				out.println("<a href='controller?action=pageListOfAllProduct&page="
						+ next + "'>Next</a>");
			}
			out.println("</div>");
	%>
</div>
<%
	}
%>
