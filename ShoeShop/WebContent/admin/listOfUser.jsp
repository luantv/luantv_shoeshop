<%!@SuppressWarnings("all") %>
<%@ page import="dao.impl.UserDAOImpl"%>
<%@ page import="entities.User"%>
<%@ page import="java.util.List"%>

<%
	/*
	Khởi tạo 1 danh sách chứa dữ liệu đã được xử lý từ controller là 1 danh sách User
	dùng để hiển thị trên table
	 */
	List<User> listOfUser = (List<User>) request
			.getAttribute("listOfUser");

	// Nếu danh sách đó rỗng sẽ thông báo rằng User đang được cập nhật
	if (listOfUser.size() == 0) {
		out.println("<b class='notify'>User Is Being Updating...</b><div class='separator'></div>");
	} else {
		/* 
		Ngược lại danh sách có user sẽ hiển thị chúng lên table,
		table sẽ có 2 chức năng là xem thông tin user và chức năng chuyển sản phẩm tới thùng rác
		thực tế việc này là update trường Status của sản phẩm đó thành 'Inactive', còn trang hiện tại
		sẽ chỉ hiển thị danh sách các user có trường Status là 'Active'
		 */
%>
<b class="notify">List Of User Is Active</b>
<div class="separator"></div>

<!-- Thông báo message nếu có request -->
${requestScope.message }

<table class="list-of-user">
	<tr>
		<th>No.</th>
		<th width="155px">Full Name</th>
		<th>Address</th>
		<th>Date Of Birth</th>
		<th>Email</th>
		<th>Phone No.</th>
		<th>Identity Card No.</th>
		<th>Status</th>
		<th>Function</th>
	</tr>
	<%
		int no = (Integer) request.getAttribute("no");
			for (User u : listOfUser) {
				++no;
	%>

	<tr>
		<td><%=no%></td>
		<td><%=u.getFullName()%></td>
		<td><%=u.getAddress()%></td>
		<td><%=u.getDateOfBirth()%></td>
		<td><%=u.getEmail()%></td>
		<td><%=u.getPhoneNo()%></td>
		<td><%=u.getIdentityCardNo()%></td>
		<td><span style='color: green;'><%=u.getStatus()%></span></td>
		<td><a title="Move User <%=u.getFullName()%> To Trash"
			href="controller?action=moveUserToTrash&userId=<%=u.getUserId()%>"><img
				src="images/trash.png" width="16px" height="16px" /></a></td>
	</tr>
	<%
		}
	%>
</table>
<%
	// Phân trang
		UserDAOImpl userDaoImpl = new UserDAOImpl();
		// Tổng số nhân viên chuyển phát
		int totalUser = userDaoImpl.count();

		// Số nhân viên hiển thị trên 1 trang
		int numberUserDispay = 5;

		// Nhận số trang với tổng số nhân viên
		int numberOfPage;
		if (totalUser % numberUserDispay == 0) {
			numberOfPage = totalUser / numberUserDispay;
		} else {
			numberOfPage = (totalUser / numberUserDispay) + 1;
		}

		int pageIndex = Integer.parseInt(request.getParameter("page"));
		out.println("<div id='pagination'>");
		if (pageIndex > 1) {
			int previous = pageIndex - 1;
			out.println("<a href='controller?action=pageListOfUser&page="
					+ previous + "'>Previous</a>");
			if(previous > 1)
			out.println("...");
		}
		if (numberOfPage > 1) {
			for (int i = pageIndex - 2; i <= pageIndex + 2; i++) {
				if (i == pageIndex) {
					out.println("<strong><a href='controller?action=pageListOfUser&page="
							+ i
							+ "' style='color: #EEBB80;'>"
							+ i
							+ "</a></strong>");
				} else {
					if (i > 0 && i < numberOfPage) {
						out.println("<a href='controller?action=pageListOfUser&page="
								+ i + "'>" + i + "</a>");
					}
				}
			}
		}
		if (pageIndex < numberOfPage) {
			int next = pageIndex + 1;
			if(next < numberOfPage)
			out.println("...");
			out.println("<a href='controller?action=pageListOfUser&page="
					+ next + "'>Next</a>");
		}
		out.println("</div>");
	}
%>