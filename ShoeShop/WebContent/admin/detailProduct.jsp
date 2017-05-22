<%!@SuppressWarnings("all") %>
<%@ page import="entities.Product"%>
<b class="notify">Information Detail Of Product</b>
<div class="separator"></div>
<%
	/*
	Khởi tạo đối tượng product nhận request từ controller gửi 1 đối tượng lưu trữ thông tin đã tìm được product theo productId
	dùng để lấy giá trị gán xuống form
	*/
	Product product = (Product) request.getAttribute("detailProduct");

	/*
	Khai báo biến hasErrors để nhận request từ controller gửi sang, nếu bằng rỗng hoặc null thì không có lỗi
	còn ngược lại giá trị của nó sẽ là 'error', nếu có lỗi sẽ có thông báo lỗi dưới form
	*/
	String hasErrors = String
			.valueOf(request.getAttribute("hasErrors"));
%>
<div id="content-center">
	<div style="margin-bottom: 20px;">
		<b>Please enter information correct and legal for all fields to
			update this product.</b>
	</div>
	<form
		action="controller?action=updateDetailProduct&productId=<%=request.getParameter("productId")%>"
		enctype="multipart/form-data" method="post">
		<table>
			<tr>
				<td>Name:</td>
				<td><input format="text" type="text" name="productName"
					value="<%if (hasErrors.equals("error"))
				out.println(request.getAttribute("productName"));
			else
				out.println(product.getProductName());%>" />
					&nbsp;(<span class="star">*</span>) <span style="color: red;">${requestScope.rq_productName }${requestScope.invalid_productName }</span>
				</td>
			</tr>
			<tr>
				<td>Publisher:</td>
				<td><input format="text" type="text" name="publisher"
					value="<%if (hasErrors.equals("error"))
				out.println(request.getAttribute("publisher"));
			else
				out.println(product.getPublisher());%>" />
					&nbsp;(<span class="star">*</span>) <span style="color: red;">${requestScope.rq_publisher }${requestScope.invalid_publisher }</span>
				</td>
			</tr>
			<tr>
				<td>Unit Price:</td>
				<td><input format="text" type="text" name="unitPrice"
					value="<%if (hasErrors.equals("error"))
				out.println(request.getAttribute("unitPrice"));
			else
				out.println(product.getUnitPrice());%>" />&nbsp;
					<strong>(VND)</strong>&nbsp;(<span class="star">*</span>) <span style="color: red;">${requestScope.rq_unitPrice }</span></td>
			</tr>
			<tr>
				<td>Quantity:</td>
				<td><input format="text" type="text" name="quantity"
					value="<%if (hasErrors.equals("error"))
				out.println(request.getAttribute("quantity"));
			else
				out.println(product.getQuantity());%>" />&nbsp;(<span
					class="star">*</span>) <span style="color: red;">${requestScope.rq_quantity }</span></td>
			</tr>
			<tr>
				<td valign="top">Description:</td>
				<td><textarea rows="4" cols="35" name="description"><%
							if (hasErrors.equals("error"))
								out.println(request.getAttribute("description"));
							else
								out.println(product.getDescription());
						%></textarea></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><select name="status">
						<option value="Active"
							<%if (product.getStatus().equalsIgnoreCase("Active"))
				out.println("selected");%>>Active</option>
						<option value="Inactive"
							<%if (product.getStatus().equalsIgnoreCase("Inactive"))
				out.println("selected");%>>Inactive</option>
				</select></td>
			</tr>
			<tr>
				<td valign="top">Image:</td>
				<td><input type="file" name="fileImage" />&nbsp;(Select a new
					picture)&nbsp;<img src="images/<%=product.getImage()%>"
					width="80px" height="50px" /></td>
			</tr>

			<tr>
				<td>&nbsp;</td>
				<td><input format="btn" type="submit" value="Update" /><br />
					<br /> <b>Note:</b> (<span class="star">*</span>) is required
					fields.</td>
			</tr>
		</table>
	</form>
</div>