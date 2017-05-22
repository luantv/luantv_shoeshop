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
			.getAttribute("listOfUserInTrash");

	if (listOfUser.size() == 0) {
		// Nếu danh sách trên không có user nào thì thông báo
		out.println("<b class='notify'>No User In Trash</b><div class='separator'></div>");
	} else {
		// Nếu có product trong danh sách thì hiển thị, ở trang này chức năng xóa sẽ xóa vĩnh viễn user đó khỏi database
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
		<th>Identity Cart No.</th>
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
		<td><span style='color: red;'><%=u.getStatus()%></span></td>
		<td><a title="Restore User <%=u.getFullName()%>" href="controller?action=restoreUserFromTrash&userId=<%=u.getUserId()%>"><img
				src="images/restore-trash.png" width="32px" height="32px" /></a> <a
			title="Delete Permanently User <%=u.getFullName()%>"
			href="controller?action=deletePermanentlyUser&userId=<%=u.getUserId()%>"
			onclick="return confirm('You sure you want to permanently delete <%=u.getFullName()%>?')"><img
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
		int totalUser = userDaoImpl.countInactive();

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
			if(next > numberOfPage)
			out.println("...");
			out.println("<a href='controller?action=pageListOfUser&page="
					+ next + "'>Next</a>");
		}
		out.println("</div>");
	}
%>