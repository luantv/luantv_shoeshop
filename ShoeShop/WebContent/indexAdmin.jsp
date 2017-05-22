<%@page import="controller.Controller"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!@SuppressWarnings("all") %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
<!-- JavaScript -->
<script>
	// Function close message
	function closeMsg() {
		var notifyMessage = document.getElementById("notify-msg-success");
		var notifyMessage1 = document.getElementById("notify-msg-error");
		notifyMessage.style.display = "none";
		notifyMessage1.style.display = "none";
	}
</script>

<!-- My CSS -->
<style type="text/css">
</style>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>ShoeShop Admin</title>

<!-- My CSS -->
<link href="css/style-admin-page.css" rel="stylesheet" />

<!-- Bootstrap Core CSS -->
<link href="bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<!-- Timeline CSS -->
<link href="dist/css/timeline.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Morris Charts CSS -->
<link href="bower_components/morrisjs/morris.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

	<!-- Messages -->
	<!--
<div id="notify-message">
	<div class="title">Notify</div>
	<span class="close-notify" onclick="closeNotify()">x</span>
	<div class="separate"></div>
	<span class="content-notify">Update successfully!</span>
	<input type="button" format="btn" class="btn-close" value="Close" onclick="closeNotify()"/>
</div>
<div id="overlay"></div>
-->

	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="controller?action=pageHome">ShoeShop
				Administrator</a>
		</div>
		<!-- /.navbar-header -->

		<ul class="nav navbar-top-links navbar-right">
			<span
				style="padding: 15px 20px; position: absolute; right: 10px; color: #555;">Hello
				${sessionScope.username } | <a href="controller?action=logout">Logout</a>
			</span>
		</ul>
		<!-- /.navbar-top-links -->

		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse">
				<ul class="nav" id="side-menu">

					<!-- Form Search -->
					<li class="sidebar-search">
						<div class="input-group custom-search-form search">
							<form action="controller?action=pageListOfProductSearch"
								method="post">
								<input type="text" class="box-input-search" name="q"
									placeholder="Search Product..." size="9" />
								<button class="btn-submit" type="submit">
									<i class="fa fa-search"></i>
								</button>
							</form>
						</div> <!-- /input-group -->
					</li>
					<!-- End Form Search -->

					<li class="bold"><a href="controller?action=pageHome"><i
							class="fa fa-dashboard fa-fw"></i> Dashboard</a></li>
					<li class="bold"><a href="#"> Product</a>
						<ul class="nav nav-second-level">
							<li><a href="controller?action=pageListOfProduct&page=1">List
									Of Product</a></li>
							<li><a href="controller?action=pageCreateNewProduct">Create
									New Product</a></li>
						</ul> <!-- /.nav-second-level --></li>
					<li class="bold"><a href="controller?action=pageListOfOrders&page=1">List Of Orders</a></li>
					<li class="bold"><a
						href="controller?action=pageListOfUser&page=1">List Of Users</a></li>
					<li class="bold"><a href="#">Trash</a>
						<ul class="nav nav-second-level">
							<li><a
								href="controller?action=pageListOfProductInTrash&page=1">List
									Of Product</a></li>
							<li><a href="controller?action=pageListOfUserInTrash&page=1">List
									Of User</a></li>
						</ul> <!-- /.nav-second-level --></li>
				</ul>
			</div>
			<!-- /.sidebar-collapse -->
		</div>
		<!-- /.navbar-static-side --> </nav>

		<!-- #page-wrapper -->
		<div id="page-wrapper">
			<!-- Hiển thị nội dung trang theo action được điều hướng từ controller -->
			<%
				if (Controller.displayAs.equalsIgnoreCase("pageHome")) {
			%>
			<jsp:include page="admin/home.jsp"></jsp:include>
			<%
				} else if (Controller.displayAs
						.equalsIgnoreCase("pageCreateNewProduct")) {
			%>
			<jsp:include page="admin/createNewProduct.jsp"></jsp:include>
			<%
				} else if (Controller.displayAs
						.equalsIgnoreCase("pageListOfProduct")) {
			%>
			<jsp:include page="admin/listOfProduct.jsp"></jsp:include>
			<%
				} else if (Controller.displayAs
						.equalsIgnoreCase("pageDetailProduct")) {
			%>
			<jsp:include page="admin/detailProduct.jsp"></jsp:include>
			<%
				} else if (Controller.displayAs
						.equalsIgnoreCase("pageListOfProductInTrash")) {
			%>
			<jsp:include page="admin/listOfProductInTrash.jsp"></jsp:include>
			<%
				} else if (Controller.displayAs
						.equalsIgnoreCase("pageListOfProductSearch")) {
			%>
			<jsp:include page="admin/listOfProductResultSearch.jsp"></jsp:include>
			<%
				} else if (Controller.displayAs.equalsIgnoreCase("pageListOfUser")) {
			%>
			<jsp:include page="admin/listOfUser.jsp"></jsp:include>
			<%
				} else if (Controller.displayAs
						.equalsIgnoreCase("pageListOfUserInTrash")) {
			%>
			<jsp:include page="admin/listOfUserInTrash.jsp"></jsp:include>
			<%
				} else if (Controller.displayAs
						.equalsIgnoreCase("pageListOfOrders")) {
			%>
			<jsp:include page="admin/listOfOrders.jsp"></jsp:include>
			<%
				} else if (Controller.displayAs
						.equalsIgnoreCase("pageViewDetailOrders")) {
			%>
			<jsp:include page="admin/detailOrders.jsp"></jsp:include>
			<%
				} else if (Controller.displayAs
						.equalsIgnoreCase("pageUpdateOrders")) {
			%>
			<jsp:include page="admin/updateOrders.jsp"></jsp:include>
			<%
				}
			%>

		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Morris Charts JavaScript -->
	<script src="bower_components/raphael/raphael-min.js"></script>
	<script src="bower_components/morrisjs/morris.min.js"></script>
	<script src="js/morris-data.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="dist/js/sb-admin-2.js"></script>

</body>

</html>