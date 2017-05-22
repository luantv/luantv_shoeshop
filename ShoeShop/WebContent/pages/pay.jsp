<%!@SuppressWarnings("all") %>
<%@ page import="entities.Product"%>
<%@ page import="dao.impl.ProductDAOImpl"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="utility.NumberUtil"%>
<%@ page import="utility.ProductInCart"%>
<%@ page import="utility.Cart"%>
<%@ page import="utility.City"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	function validForm() {
		var valid = true;
		var regexName = "[a-zA-Z ]+";
		var regexPhone = "0[0-9]{9,10}";
		var regexDistrict = "[a-zA-Z /-]+";
		var regexEmail = "[a-zA-Z_0-9.]{4,}@[a-zA-Z_0-9]+[.][a-zA-Z_0-9]{2,4}";
		var regexIdCard = "[0-9]{9,12}";

		// Full name
		if ($("fullname").value == null || $("fullname").value == "") {
			$("err_fullname").innerHTML = "Please enter your full name";
			valid = false;
		} else if ($("fullname").value.match(regexName)) {
			$("err_fullname").innerHTML = "";
			valid = true;
		} else {
			$("err_fullname").innerHTML = "Name invalid";
			valid = false;
		}

		// Phone
		if ($("phoneno").value == null || $("phoneno").value == "") {
			$("err_phoneno").innerHTML = "Please enter your phone number";
			valid = false;
		} else if ($("phoneno").value.match(regexPhone)) {
			$("err_phoneno").innerHTML = "";
			valid = true;
		} else {
			$("err_phoneno").innerHTML = "Phone invalid";
			valid = false;
		}

		// City
		if ($("city").value == null || $("city").value == "") {
			$("err_city").innerHTML = "Please choose the city";
			valid = false;
		} else {
			$("err_city").innerHTML = "";
			valid = true;
		}

		// District
		if ($("district").value == null || $("district").value == "") {
			$("err_district").innerHTML = "Please enter district";
			valid = false;
		} else if ($("district").value.match(regexDistrict)) {
			$("err_district").innerHTML = "";
			valid = true;
		} else {
			$("err_district").innerHTML = "District invalid";
			valid = false;
		}

		// Email
		if ($("email").value == null || $("email").value == "") {
			$("err_email").innerHTML = "Please enter your email address";
			valid = false;
		} else if ($("email").value.match(regexEmail)) {
			$("err_email").innerHTML = "";
			valid = true;
		} else {
			$("err_email").innerHTML = "Email address invalid";
			valid = false;
		}

		// Identity card numer
		if ($("identitycardno").value == null
				|| $("identitycardno").value == "") {
			$("err_identitycardno").innerHTML = "Please enter your identity card number";
			valid = false;
		} else if ($("identitycardno").value.match(regexIdCard)) {
			$("err_identitycardno").innerHTML = "";
			valid = true;
		} else {
			$("err_identitycardno").innerHTML = "Card number invalid (9 - 12 number)";
			valid = false;
		}

		// Address
		if ($("address").value == null || $("address").value == "") {
			$("err_address").innerHTML = "Please enter your address/shipping address";
			valid = false;
		} else {
			$("err_address").innerHTML = "";
			valid = true;
		}

		return valid;
		// Nếu valid == true post các giá trị qua controller bằng Ajax
		/* if (valid) {
			var xmlhttp = new XMLHttpRequest();
			var address = $("address").value + "/" + $("district").value + "/"
					+ $("city").value;
			var url = "controller";
			var data = "action=orderConfirm&name=" + $("fullname").value
					+ "&phoneno=" + $("phoneno").value;
			xmlhttp.open("post", url, true);
			xmlhttp.setRequestHeader("content-type", "application/x-www-form-urlencoded");
			xmlhttp.onreadystatechange = function(){
				if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
					alert("Send data success");
				}else{
					alert("Send data not success");
				}
			}
			xmlhttp.send(data);
		} */
	}
	function $(id) {
		return document.getElementById(id);
	}
</script>
<%
	//Lấy danh sách nhà sản xuất để in ra right menu
	List<String> listOfPublisher = (List<String>) request
			.getAttribute("listOfPublisher");

	// Lấy đối session cart
	Cart cart = (Cart) session.getAttribute("cart");
%>
<div class="row product">
	<div class="col-md-10">
		<!-- Sub container -->
		<div class="title-content">Customer information</div>
		<form action="controller" method="post" onsubmit="return validForm()">
			<div class="sub-container">
				<input type="hidden" name="action" value="orderConfirm" />
				<div class="item-form">
					<div>
						Full name: <span class="star">*</span>
					</div>
					<input id="fullname" type="text" name="fullname" value="" />
					<!-- Notify error -->
					<div style="color: #BD2A2A; margin-top: 5px;">
						<span id="err_fullname"> <!-- content here -->

						</span>
					</div>
				</div>
				<div class="item-form">
					<div>
						Phone number: <span class="star">*</span>
					</div>
					<input id="phoneno" type="text" name="phoneno" value="" />
					<!-- Notify error -->
					<div style="color: #BD2A2A; margin-top: 5px;">
						<span id="err_phoneno"> <!-- content here -->

						</span>
					</div>
				</div>
				<div class="item-form">
					<div>
						Province/city: <span class="star">*</span>
					</div>
					<select id="city" name="city">
						<option value="">Select province/city</option>
						<%
							for (String city : City.getCity()) {
						%>
						<option value="<%=city%>"><%=city%></option>
						<%
							}
						%>
					</select>
					<!-- Notify error -->
					<div style="color: #BD2A2A; margin-top: 5px;">
						<span id="err_city"> <!-- content here -->

						</span>
					</div>
				</div>
				<div class="item-form" style="clear: both;">
					<div>
						District: <span class="star">*</span>
					</div>
					<input id="district" type="text" name="district" value="" />
					<!-- Notify error -->
					<div style="color: #BD2A2A; margin-top: 5px;">
						<span id="err_district"> <!-- content here -->

						</span>
					</div>
				</div>
				<div class="item-form">
					<div>
						Email: <span class="star">*</span>
					</div>
					<input id="email" type="text" name="email" value="" />
					<!-- Notify error -->
					<div style="color: #BD2A2A; margin-top: 5px;">
						<span id="err_email"> <!-- content here -->

						</span>
					</div>
				</div>
				<div class="item-form">
					<div>
						Identity card number: <span class="star">*</span>
					</div>
					<input id="identitycardno" type="text" name="identitycardno"
						value="" />
					<!-- Notify error -->
					<div style="color: #BD2A2A; margin-top: 5px;">
						<span id="err_identitycardno"> <!-- content here -->

						</span>
					</div>
				</div>
				<div class="item-form" style="clear: both;">
					<div>
						Address: <span class="star">*</span>
					</div>
					<input id="address" type="text" name="address" value="" />
					<div style="color: #BD2A2A; font-weight: normal; margin-top: 3px;">(House
						number, street, ward/commune)</div>
					<!-- Notify error -->
					<div style="color: #BD2A2A; margin-top: 5px;">
						<span id="err_address"> <!-- content here -->

						</span>
					</div>
				</div>
				<div
					style="font-weight: normal; font-style: italic; color: #555; font-size: 12px; text-align: left; margin: 20px 20px; clear: both;">
					Please enter the details for order to be transferred quickly and
					accurately to customer.</div>
			</div>

			<!-- Sub container -->
			<div class="title-content">Payments methods</div>
			<div class="sub-container">
				<div
					style="font-weight: normal; color: #555; font-size: 12px; text-align: left; margin: 20px 20px; clear: both;">
					Payments after receipt.</div>
			</div>

			<!-- Sub container -->
			<div class="title-content">Recheck orders</div>
			<div class="sub-container">
				<div
					style="font-weight: bold; color: #555; font-size: 12px; text-align: left; margin: 20px 20px; clear: both; text-transform: uppercase; border-bottom: 1px solid #bbb; padding-bottom: 10px;">
					Order information</div>

				<!-- Table orders -->
				<table class="table-pay" border="1">
					<tr>
						<td width="20px">No.</td>
						<td width="50px;">Image</td>
						<td>Product name</td>
						<td>Unit price</td>
						<td>Quantity</td>
						<td>Monetized</td>
					</tr>
					<!-- Display product in cart -->
					<%
						ArrayList<ProductInCart> list = (ArrayList<ProductInCart>) cart
								.getCart();
						int i = 0;
						for (ProductInCart pic : list) {
							Product p = pic.getProduct();
							++i;
					%>
					<tr>
						<td><%=i%></td>
						<td><img class="product" src="images/<%=p.getImage()%>"
							width="60px" height="60px" /></td>
						<td style="text-align: left;"><%=p.getProductName()%></td>
						<td><%=NumberUtil.formatMoneyVietnam(p.getUnitPrice())%></td>
						<td>x<%=pic.getQuantity()%></td>
						<td><%=NumberUtil.formatMoneyVietnam(pic.getTotalPrice())%></td>
					</tr>
					<%
						}
					%>
					<tr>
						<td colspan="6" style="font-weight: normal; font-size: 12px;">The
							total amount: <%=NumberUtil.formatMoneyVietnam(cart.totalPrice())%></td>
					</tr>
				</table>
				<!-- End table orders -->

				<!-- Additional information -->
				<div class="item-form1" style="margin-bottom: 10px;">
					<div>Additional information:</div>
					<textarea id="description" cols="35" rows="4" name="description"></textarea>
				</div>
			</div>

			<div class="group-btn">
				<a class="btn" href="forward.jsp">Home page</a> <a class="btn"
					href="controller?action=viewCart">Cart</a>
				<button class="btn" type="submit">Order confirmation</button>
			</div>
		</form>
		<div style="margin-bottom: 10px; color: #BD2A2A; font-size: 13px;">*
			Required information</div>
	</div>

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