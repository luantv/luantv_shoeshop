<%!@SuppressWarnings("all") %>
<%@ page import="entities.Product"%>
<%@ page import="dao.impl.ProductDAOImpl"%>
<%@ page import="java.util.List"%>
<%@ page import="utility.NumberUtil"%>
<%
	// Lấy ra danh sách product theo publisher
	ProductDAOImpl productDaoImpl = new ProductDAOImpl();
	List<Product> listOfProductByPublisher = (List<Product>) request
			.getAttribute("listOfProductByPublisher");
	String keyword = String.valueOf(request.getAttribute("name"));
	if (listOfProductByPublisher.size() == 0) {
%>
<div class="row product">
	<!-- Head title -->
	<div class="head-title">
		No product in publisher
		<%=keyword%>
	</div>
</div>
<%
	} else {
%>
<div class="row product">
	<!-- Head title -->
	<div class="head-title">
		List of product by publisher
		<%=keyword%>
	</div>
	<%
		for (Product p : listOfProductByPublisher) {
	%>
	<!-- Content here -->
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
</div>
<%
	}
%>