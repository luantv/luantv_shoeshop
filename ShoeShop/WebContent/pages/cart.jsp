<%!@SuppressWarnings("all") %>
<%@page import="controller.Controller"%>
<%@ page import="entities.Product"%>
<%@ page import="dao.impl.ProductDAOImpl"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="utility.NumberUtil"%>
<%@ page import="utility.ProductInCart"%>
<%@ page import="utility.Cart"%>
<script>
	function validInputNumber(id, max, productId){
		var e = document.getElementById(id);
		if(isNaN(e.value) || e.value <= 0 || e.value > max){
			e.value = 1;
		}else if(e.value <= 0 && e.value > max){
			e.value = 1;
		}
		updateQty(productId, id);
	}
	
	function plusValue(id, max, productId){
		var e = document.getElementById(id);
		var i = e.value;
		if(isNaN(e.value) || e.value >= max){
			i = max;
		}else if(e.value <= 0 && e.value > max){
			i = 1;
		}else{
			i++;
		}
		e.value = i;
		updateQty(productId, id);
	}
	
	function minusValue(id, productId){
		var e = document.getElementById(id);
		var i = e.value;
		if(isNaN(e.value) || e.value <= 0){
			i = 1;
		}else if(e.value <= 0){
			i = 1;
		}else if(e.value > 1){
			i--;
		}
		e.value = i;
		updateQty(productId, id)
	}
	
	// Ajax post data update quantity
	function updateQty(productId, id){
		var val = document.getElementById(id);
		var xmlhttp = new XMLHttpRequest();
		var url = "controller";
		var data = "action=updateCart&productId=" + productId + "&qty=" + val.value;
		xmlhttp.open("post", url, true);
		xmlhttp.setRequestHeader("content-type", "application/x-www-form-urlencoded");
		xmlhttp.onreadystatechange = function(){
			if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
				window.location.href = "controller?action=viewCart";
			}
		}
		xmlhttp.send(data);
		return false;
	}
</script>
<div class="row product">
	<div class="col-md-10">
		<%
			//Lấy danh sách nhà sản xuất để in ra right menu
			List<String> listOfPublisher = (List<String>) request
					.getAttribute("listOfPublisher");

			// Lấy đối session cart
			Cart cart = (Cart) session.getAttribute("cart");
			if (cart.count() == 0) {
				out.println("<div style='margin-top: 10px; font-size: 13px; text-align: center; font-weight: bold;'>Your cart empty!</div>");
				out.println("<div style='margin-top: 10px; font-size: 13px; text-align: center;'>Please purchase and return to this page to check, thanks you!</div>");
				out.println("<div class='group-btn'><a class='btn' href='forward.jsp'>Home page</a></div>");
			} else {
		%>
		<table class="table-cart" border="1">
			<tr>
				<td width="30px">No.</td>
				<td width="350px">Product name</td>
				<td width="100px">Unit price</td>
				<td width="100px">Quantity</td>
				<td width="120px">Monetized</td>
				<td></td>
			</tr>
			<%
				int i = 0;
					ArrayList<ProductInCart> list = cart.getCart();
					for (ProductInCart pic : list) {
						Product p = pic.getProduct();
						++i;
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
				<td><span class="sub-plus"><img src="images/minus.png"
						width="10px" height="10px" style="cursor: pointer;"
						onclick="minusValue('qty<%=i%>', <%=p.getProductId()%>)" /></span> <input
					id="qty<%=i%>" type="text" name="quantity"
					value="<%=pic.getQuantity()%>" size="1"
					onchange="validInputNumber(this.id, <%=p.getQuantity()%>, <%=p.getProductId()%>)" />
					<span class="sub-plus"><img src="images/plus.png"
						width="10px" height="10px" style="cursor: pointer;"
						onclick="plusValue('qty<%=i%>', <%=p.getQuantity()%>, <%=p.getProductId()%>)" /></span></td>
				<td><%=NumberUtil.formatMoneyVietnam(pic.getTotalPrice())%></td>
				<td><a
					href="controller?action=removeProductInCart&productId=<%=p.getProductId()%>"
					title="Remove this product from cart"><img
						src="images/close.png" /></a></td>
			</tr>
			<%
				}
			%>
			<tr>
				<td colspan="6">The total amount: <span class="total-amount"><%=NumberUtil.formatMoneyVietnam(cart.totalPrice())%></span></td>
			</tr>
		</table>
		<div class="group-btn">
			<a class="btn" href="forward.jsp">Home page</a> <a class="btn"
				href="forward.jsp">Continue shopping</a> <a class="btn"
				href="controller?action=pagePay">Making payments</a>
		</div>
		<%
			}
		%>
	</div>
	<!-- End show cart -->

	<!-- Show category -->
	<div class="col-md-2" style="height: 100%;">
		<!-- Display all category (pulisher/trademark) -->
		<div class="category">
			<div>All Brands</div>
			<ul>
				<%
					for (String publiser : listOfPublisher) {
				%>
				<li><a
					href="controller?action=findProductByPublisher&name=<%=publiser%>"><%=publiser%></a></li>
				<%
					}
				%>
			</ul>
		</div>

		<!-- Online support -->
		<div class="online-support">
			<div class="title">Online Support</div>
			<img src="images/online-support.png" />
			<div class="phone">HOTLINE: (08).3820.3483</div>
			<div class="phone">Sales 1: 01277.333.567</div>
			<div class="phone">Sales 2: 0983.889.876</div>
		</div>
	</div>
</div>