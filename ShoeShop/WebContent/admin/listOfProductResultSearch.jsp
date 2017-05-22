<%!@SuppressWarnings("all")%>
<%@ page import="java.util.List"%>
<%@ page import="entities.Product"%>
<%@ page import="utility.NumberUtil"%>
<%
	// Khởi tạo 1 đối tượng lưu trữ danh sách kết quả các product khi search
	List<Product> listOfProduct = (List<Product>) request
			.getAttribute("listOfProductResultSearch");
	if (listOfProduct.size() == 0) {
		// Nếu không có product nào trong danh sách thì thông báo
		out.println("<b class='notify'>No Result Search With Keyword '"
				+ request.getAttribute("q")
				+ "'</b><div class='separator'></div>");
	} else {
		// Nếu có product trong danh sách search thì hiển thị ra
%>
<b class="notify">Result Search With Keyword '${requestScope.q }'
	Is: <%=listOfProduct.size()%></b>
<div class="separator"></div>
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
		int no = 0;
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
			<%if (p.getStatus().equalsIgnoreCase("Active")) {%>
			title="Move Product <%=p.getProductName()%> To Trash"
			href="controller?action=moveProductToTrash&productId=<%=p.getProductId()%>"
			<%} else {%>
			title="Delete Permanently Product <%=p.getProductName()%>"
			href="controller?action=deletePermanentlyProduct&productId=<%=p.getProductId()%>"
			onclick="return confirm('You sure you want to permanently delete <%=p.getProductName()%>?')"
			<%}%>><img src="images/trash.png" width="16px" height="16px" /></a></td>
	</tr>
	<%
		}
	%>
</table>
<%
	}
%>