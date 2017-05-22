<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!@SuppressWarnings("all") %>
<b class="notify">Create New Product</b>
<div class="separator"></div>

<!-- Thông báo message nếu có request -->
${requestScope.message }

<div id="content-center">
	<div style="margin-bottom: 20px;">
		<b>Please enter information correct and legal for all fields to
			create new product.</b>
	</div>
	<form action="controller?action=createNewProduct"
		enctype="multipart/form-data" method="post" accept-charset="UTF-8">
		<table>
			<tr>
				<td>Name:</td>
				<td><input format="text" type="text" name="productName"
					value="${param.productName }${requestScope.productName }" />&nbsp;(<span
					class="star">*</span>) <span style="color: red;">
						${requestScope.rq_productName }${requestScope.invalid_productName }</span>
				</td>
			</tr>
			<tr>
				<td>Publisher:</td>
				<td><input format="text" type="text" name="publisher"
					value="${param.publisher }${requestScope.publisher }" />&nbsp;(<span
					class="star">*</span>) <span style="color: red;">
						${requestScope.rq_publisher }${requestScope.invalid_publisher }</span></td>
			</tr>
			<tr>
				<td>Unit Price:</td>
				<td><input format="text" type="text" name="unitPrice"
					value="${param.unitPrice }${requestScope.unitPrice }" />&nbsp; <strong>(VND)</strong>&nbsp;(<span
					class="star">*</span>) <span style="color: red;">
						${requestScope.rq_unitPrice }</span></td>
			</tr>
			<tr>
				<td>Quantity:</td>
				<td><input format="text" type="text" name="quantity"
					value="${param.quantity }${requestScope.quantity }" />&nbsp;(<span
					class="star">*</span>) <span style="color: red;">
						${requestScope.rq_quantity }</span></td>
			</tr>
			<tr>
				<td valign="top">Description:</td>
				<td><textarea rows="4" cols="35" name="description">${param.description }${requestScope.description }</textarea></td>
			</tr>
			<tr>
				<td valign="top">Image:</td>
				<td><input type="file" name="fileImage"
					value="${param.fileImage }" />(<span class="star">*</span>) <span
					style="color: red;"> ${requestScope.rq_image }</span></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><select name="status">
						<option value="Active">Active</option>
						<option value="Inactive">Inactive</option>
				</select></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input format="btn" type="submit" value="Create" /><br />
					<br /> <b>Note:</b> (<span class="star">*</span>) is required
					fields.</td>
			</tr>
		</table>
	</form>
</div>