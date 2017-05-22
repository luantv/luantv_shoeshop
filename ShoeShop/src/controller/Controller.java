package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import dao.impl.OrderdetailDAOImpl;
import dao.impl.OrdersDAOImpl;
import dao.impl.ProductDAOImpl;
import dao.impl.UserDAOImpl;
import utility.Cart;
import utility.NumberUtil;
import utility.ProductInCart;
import entities.OrderDetailByOrderId;
import entities.Orders;
import entities.Orderdetail;
import entities.OrderdetailId;
import entities.Product;
import entities.ProductInOrders;
import entities.User;

@SuppressWarnings("all")
@WebServlet("/controller")
public class Controller extends HttpServlet{
	private static final long serialVersionUID	= 1L;
	public static String displayAs = "pageHome";

	private String productName = null;
	private String publisher = null;
	private String description = null;
	private String image = null;
	private String status = null;
	private int quantity = 0;
	private float unitPrice	= 0f;
	private Date datePosted = new Date();

	private ProductDAOImpl productDaoImpl = new ProductDAOImpl();
	private UserDAOImpl userDaoImpl = new UserDAOImpl();
	private OrdersDAOImpl orderDaoImpl = new OrdersDAOImpl();
	private OrderdetailDAOImpl orderDetailDaoImpl = new OrderdetailDAOImpl();

	// Khá»Ÿi táº¡o 1 list product lÆ°u trá»¯ cĂ¡c product trong cart
	private Cart				cart				= new Cart();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller(){
		super();
		// TODO Auto-generated constructor stub
	}

	// HĂ m xá»­ lĂ½ nháº­n method get hoáº·c post, hĂ m nĂ y Ä‘Æ°á»£c gá»�i trong cáº£ hĂ m
	// doGet() láº«n doPost()
	protected void progressRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String action = request.getParameter("action");

		/**
		 * --------------------------------------------------------------------
		 * START REDIRECT PAGES
		 * --------------------------------------------------------------------
		 */

		// Redirect page home
		if(action.equalsIgnoreCase("pageHome")){
			displayAs = "pageHome";
			request.getRequestDispatcher("indexAdmin.jsp").forward(request,
					response);
		}

		// Redirect page createNewProduct.jsp
		if(action.equalsIgnoreCase("pageCreateNewProduct")){
			displayAs = "pageCreateNewProduct";
			request.setAttribute("message", "");
			request.getRequestDispatcher("indexAdmin.jsp").forward(request,
					response);
		}

		// Redirect page listOfProduct.jsp
		if(action.equalsIgnoreCase("pageListOfProduct")){
			displayAs = "pageListOfProduct";
			int start = 0, end = 5;
			int page = Integer.parseInt(request.getParameter("page"));
			if(page > 0){
				start = (page - 1) * 5;
			}
			request.removeAttribute("listOfProduct");
			request.setAttribute("listOfProduct",
					productDaoImpl.selectLimitAll(start, end));
			request.setAttribute("no", start);
			request.getRequestDispatcher("indexAdmin.jsp").forward(request,
					response);
		}

		// Redirect listOfAllProduct.jsp Trang hiá»ƒn thá»‹ sáº£n pháº©m trĂªn trang
		// index
		// cho khĂ¡ch hĂ ng
		if(action.equalsIgnoreCase("pageListOfAllProduct")){
			displayAs = "pageListOfAllProduct";
			// Khá»Ÿi táº¡o session cho khĂ¡c hĂ ng vá»›i sá»‘ sáº£n pháº©m báº±ng 0
			request.getSession().setAttribute("cart", cart);
			request.getSession().setMaxInactiveInterval(60 * 60);

			int start = 0, end = 10;
			int page = Integer.parseInt(request.getParameter("page"));
			if(page > 0){
				start = (page - 1) * 10;
			}
			request.removeAttribute("listOfAllProduct");
			request.setAttribute("listOfAllProduct",
					productDaoImpl.selectLimit(start, end));
			request.setAttribute("no", start);
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
		}

		// Redirect page cart.jsp
		if(action.equalsIgnoreCase("viewCart")){
			displayAs = "pageViewCart";
			request.getSession().setAttribute("cart", cart);
			request.getSession().setMaxInactiveInterval(60 * 60);
			// Gá»­i danh sĂ¡ch publisher
			request.setAttribute("listOfPublisher",
					productDaoImpl.listOfPublisher());
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
		}

		// Redirect page pay.jsp
		if(action.equalsIgnoreCase("pagePay")){
			displayAs = "pagePay";
			request.getSession().setAttribute("cart", cart);
			request.getSession().setMaxInactiveInterval(60 * 60);
			// Gá»­i danh sĂ¡ch publisher
			request.setAttribute("listOfPublisher",
					productDaoImpl.listOfPublisher());
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
		}

		// Redirect page detailProduct.jsp
		if(action.equalsIgnoreCase("pageDetailProduct")){
			displayAs = "pageDetailProduct";
			request.removeAttribute("detailProduct");
			int productId = Integer.valueOf(request.getParameter("productId"));
			// Láº¥y thĂ´ng tin Ä‘á»‘i tÆ°á»£ng product cĂ³ id = productId á»Ÿ trĂªn
			Product product = productDaoImpl.find(productId);
			request.setAttribute("detailProduct", product);
			request.getRequestDispatcher("indexAdmin.jsp").forward(request,
					response);
		}

		// Redirect page listOfProductInTrash.jsp
		if(action.equalsIgnoreCase("pageListOfProductInTrash")){
			displayAs = "pageListOfProductInTrash";
			int start = 0, end = 5;
			int page = Integer.parseInt(request.getParameter("page"));
			if(page > 0){
				start = (page - 1) * 5;
			}
			request.removeAttribute("listOfProductInTrash");
			request.setAttribute("listOfProductInTrash",
					productDaoImpl.selectLimitInTrash(start, end));
			request.setAttribute("no", start);
			request.getRequestDispatcher("indexAdmin.jsp").forward(request,
					response);
		}

		// Redirect page listOfUser.jsp
		if(action.equalsIgnoreCase("pageListOfUser")){
			displayAs = "pageListOfUser";
			int start = 0, end = 5;
			int page = Integer.parseInt(request.getParameter("page"));
			if(page > 0){
				start = (page - 1) * 5;
			}
			request.removeAttribute("listOfUser");
			request.setAttribute("listOfUser",
					userDaoImpl.selectLimit(start, end));
			request.setAttribute("no", start);
			request.getRequestDispatcher("indexAdmin.jsp").forward(request,
					response);
		}

		// Redirect page listOfUserInTrash.jsp
		if(action.equalsIgnoreCase("pageListOfUserInTrash")){
			displayAs = "pageListOfUserInTrash";
			int start = 0, end = 5;
			int page = Integer.parseInt(request.getParameter("page"));
			if(page > 0){
				start = (page - 1) * 5;
			}
			request.removeAttribute("listOfUserInTrash");
			request.setAttribute("listOfUserInTrash",
					userDaoImpl.selectLimitInTrash(start, end));
			request.setAttribute("no", start);
			request.getRequestDispatcher("indexAdmin.jsp").forward(request,
					response);
		}

		// Redirect page listOfOrders.jsp
		if(action.equalsIgnoreCase("pageListOfOrders")){
			displayAs = "pageListOfOrders";
			int start = 0, end = 5;
			int page = Integer.parseInt(request.getParameter("page"));
			if(page > 0){
				start = (page - 1) * 5;
			}
			request.removeAttribute("listOfOrders");
			request.setAttribute("listOfOrders",
					orderDaoImpl.selectLimit(start, end));
			request.setAttribute("no", start);
			request.getRequestDispatcher("indexAdmin.jsp").forward(request,
					response);
		}

		// Redirect page updateOrders.jsp
		if(action.equalsIgnoreCase("pageUpdateOrders")){
			displayAs = "pageUpdateOrders";
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			String status = request.getParameter("status");

			// láº¥y UserId theo mĂ£ sáº£n Ä‘Æ¡n hĂ ng
			int userId = orderDaoImpl.find(orderId).getUserId();

			request.setAttribute("userId", userId);
			request.setAttribute("status", status);
			request.setAttribute("orderId", orderId);
			request.setAttribute("listOfUser", userDaoImpl.listOfUser());
			request.getRequestDispatcher("indexAdmin.jsp").forward(request,
					response);
		}
		/**
		 * --------------------------------------------------------------------
		 * END REDIRECT PAGES
		 * --------------------------------------------------------------------
		 */

		/**
		 * --------------------------------------------------------------------
		 * START CART HANDLE
		 * --------------------------------------------------------------------
		 */

		// ThĂªm sáº£n pháº©m vĂ o giá»� hĂ ng
		if(action.equalsIgnoreCase("addToCart")){
			int productId = Integer.parseInt(request.getParameter("productId"));
			Product p = productDaoImpl.find(productId);
			ProductInCart product = new ProductInCart(p, 1);
			cart.addProduct(product);
			request.getSession().setAttribute("cart", cart);
			request.getSession().setMaxInactiveInterval(60 * 60);
			request.getRequestDispatcher(
					"controller?action=productDetail&productId=" + productId)
					.forward(request, response);
		}

		// XĂ³a sáº£n pháº©m trong cart
		if(action.equalsIgnoreCase("removeProductInCart")){
			int productId = Integer.parseInt(request.getParameter("productId"));
			Product p = productDaoImpl.find(productId);
			ProductInCart product = new ProductInCart(p, 0);
			cart = (Cart) request.getSession().getAttribute("cart");
			cart.removeProduct(product);
			request.getSession().setAttribute("cart", cart);
			request.getSession().setMaxInactiveInterval(60 * 60);
			request.getRequestDispatcher("controller?action=viewCart").forward(
					request, response);
		}

		// Cáº­p nháº­t sá»‘ lÆ°á»£ng cho cart
		if(action.equalsIgnoreCase("updateCart")){
			int productId = Integer.parseInt(request.getParameter("productId"));
			int qty = Integer.parseInt(request.getParameter("qty"));

			int quantity = 0;

			ArrayList<ProductInCart> list = (ArrayList<ProductInCart>) cart
					.getCart();
			for(ProductInCart pic : list){
				if(pic.getProduct().getProductId() == productId){
					quantity = qty - pic.getQuantity();
					break;
				}
			}

			Product p = productDaoImpl.find(productId);
			ProductInCart product = new ProductInCart(p, quantity);

			cart.updateProduct(product);
			request.getSession().setAttribute("cart", cart);
			request.getSession().setMaxInactiveInterval(60 * 60);
			request.getRequestDispatcher("controller?action=viewCart").forward(
					request, response);
		}

		// Mua ngay, thĂªm sáº£n pháº©m vĂ o giá»� hĂ ng vĂ  chuyá»ƒn tá»›i trang tiáº¿n hĂ nh
		// thanh toĂ¡n
		if(action.equalsIgnoreCase("buyNow")){
			int productId = Integer.parseInt(request.getParameter("productId"));
			Product p = productDaoImpl.find(productId);
			ProductInCart product = new ProductInCart(p, 1);
			cart.addProduct(product);
			request.getSession().setAttribute("cart", cart);
			request.getSession().setMaxInactiveInterval(60 * 60);
			request.getRequestDispatcher("controller?action=pagePay").forward(
					request, response);
		}

		// XĂ¡c nháº­n Ä‘Æ¡n hĂ ng
		if(action.equalsIgnoreCase("orderConfirm")){
			String name = String.valueOf(request.getParameter("fullname"));
			String phoneno = String.valueOf(request.getParameter("phoneno"));
			String address = String.valueOf(request.getParameter("address"));
			String city = String.valueOf(request.getParameter("city"));
			String district = String.valueOf(request.getParameter("district"));
			String email = String.valueOf(request.getParameter("email"));
			String idcardno = String.valueOf(request
					.getParameter("identitycardno"));
			String description = String.valueOf(request
					.getParameter("description"));

			String addr = address + "/" + district + "/" + city;

			// Khá»Ÿi táº¡o Ä‘á»‘i tÆ°á»£ng order
			Orders order = new Orders(name, email, phoneno, idcardno,
					description, new Date(), addr, 1, "Unapproved");

			// Insert vĂ o database
			boolean inserted = orderDaoImpl.insert(order);

			if(inserted){
				// Náº¿u thĂªm order thĂ nh cĂ´ng, thĂ¬ thĂªm thĂ´ng tin cho orderdetail
				int orderId = orderDaoImpl.findLastOrder().getOrderId();
				cart = (Cart) request.getSession().getAttribute("cart");
				List<ProductInCart> listPic = cart.getCart();
				for(ProductInCart pic : listPic){
					// ThĂªm cĂ¡c product trong cart vĂ o báº£ng orderdetail, máº·c
					// Ä‘á»‹nh mĂ£ nhĂ¢n viĂªn chuyá»ƒn phĂ¡t sáº½ lĂ  1 vĂ  nháº¥t Ä‘á»‹nh admin
					// sáº½ Ä‘á»•i nhĂ¢n viĂªn chuyá»ƒn phĂ¡t hay khĂ´ng thĂ¬ thá»±c hiá»‡n bĂªn
					// trang quáº£n trá»‹
					OrderdetailId orderDetailId = new OrderdetailId(orderId,
							pic.getProduct().getProductId(), pic.getQuantity());
					Orderdetail orderDetail = new Orderdetail(orderDetailId);
					orderDetailDaoImpl.insert(orderDetail);

					// Cáº­p nháº­t láº¡i sá»‘ lÆ°á»£ng cho sáº£n pháº©m Ä‘Ă³ vĂ¬ khĂ¡ch hĂ ng Ä‘Ă£
					// mua má»™t sá»‘ lÆ°á»£ng nháº¥t Ä‘á»‹nh
					Product product = productDaoImpl.find(pic.getProduct().getProductId());
					product.setQuantity(product.getQuantity() - pic.getQuantity());
					productDaoImpl.update(product);
				}
				cart = new Cart();
				request.getSession().setAttribute("cart", cart);
				displayAs = "pagePaySuccess";
				request.setAttribute("listOfPublisher",
						productDaoImpl.listOfPublisher());
			}

			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
		}

		/**
		 * --------------------------------------------------------------------
		 * END CART HANDLE
		 * --------------------------------------------------------------------
		 */

		/**
		 * --------------------------------------------------------------------
		 * START LOGIN HANDLE
		 * --------------------------------------------------------------------
		 */

		// Authentication admin login
		if(action.equalsIgnoreCase("login")){
			/*
			 * Trong project nĂ y dĂ¹ng táº¡m 1 tĂ i khoáº£n Ä‘á»ƒ cĂ³ thá»ƒ thá»ƒ hiá»‡n Ä‘Æ°á»£c
			 * tĂ­nh báº£o máº­t trong trang quáº£n lĂ½, vĂ¬ cÅ©ng lĂ m Ä‘Æ¡n giáº£n nĂªn sáº½
			 * khĂ´ng cĂ³ md5 password, dĂ¹ng tĂ i khoáº£n máº·c Ä‘á»‹nh nhÆ° sau
			 * 
			 * @username: admin
			 * 
			 * @password: admin
			 * 
			 * Ä‘á»ƒ chá»©ng thá»±c ngÆ°á»�i quáº£n trá»‹
			 */
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			if(username.equals("") || username.equals(null)
					|| password.equals("") || password.equals(null)){
				// Táº¡o request errorAuthen gá»­i chuá»—i thĂ´ng bĂ¡o lá»—i vá»� trang
				// login.jsp
				request.setAttribute("errorAuthen",
						"Please enter username and password fields");

				// Chuyá»ƒn hÆ°á»›ng vá»� trang login.jsp
				request.getRequestDispatcher("login.jsp").forward(request,
						response);
			} else{
				String regex = "^[a-zA-Z]+$";
				if(!username.matches(regex) || !password.matches(regex)){
					// Táº¡o request errorAuthen gá»­i chuá»—i thĂ´ng bĂ¡o lá»—i vá»� trang
					// login.jsp
					request.setAttribute("errorAuthen",
							"Username or password are not correct");

					// Chuyá»ƒn hÆ°á»›ng vá»� trang login.jsp
					request.getRequestDispatcher("login.jsp").forward(request,
							response);
				} else{
					if(username.equals("admin") && password.equals("admin")){
						// TĂ i khoáº£n há»£p lá»‡, chuyá»ƒn hÆ°á»›ng tá»›i trang home.jsp
						// (trang quáº£n lĂ½ táº¡i Ä‘Ă¢y)
						request.getSession().setAttribute("username", username);
						request.getRequestDispatcher(
								"controller?action=pageHome").forward(request,
								response);
					} else{
						// TĂ i khoáº£n khĂ´ng chĂ­nh xĂ¡c
						// Táº¡o request errorAuthen gá»­i chuá»—i thĂ´ng bĂ¡o lá»—i vá»�
						// trang login.jsp
						request.setAttribute("errorAuthen",
								"Username or password are not correct");

						// Chuyá»ƒn hÆ°á»›ng vá»� trang login.jsp
						request.getRequestDispatcher("login.jsp").forward(
								request, response);
					}
				}
			}
		}

		// Logout
		if(action.equalsIgnoreCase("logout")){
			request.getSession().removeAttribute("username");
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
		}

		/**
		 * --------------------------------------------------------------------
		 * END LOGIN HANDLE
		 * --------------------------------------------------------------------
		 */

		/**
		 * --------------------------------------------------------------------
		 * START PRODUCT HANDLE
		 * --------------------------------------------------------------------
		 */

		// Insert product
		if(action.equalsIgnoreCase("createNewProduct")){
			// Thá»±c hiá»‡n upload file áº£nh, pháº§n xá»­ lĂ½ gĂ¡n giĂ¡ trá»‹ náº±m trong hĂ m
			// upload()
			upload(request);

			// Check sum
			boolean valid = true;
			String regex = "^[a-zA-Z_0-9.\\s]+$";

			// Product name
			if(productName.equals("") || productName.equals(null)){
				request.setAttribute("rq_productName",
						"Please enter product name!");
				valid = false;
			} else{
				if(!productName.matches(regex)){
					request.setAttribute("invalid_productName",
							"Product name wrong format!");
					valid = false;
				}
			}

			// Publisher
			if(publisher.equals("") || publisher.equals(null)){
				request.setAttribute("rq_publisher", "Please enter publisher!");
				valid = false;
			} else{
				if(!publisher.matches(regex)){
					request.setAttribute("invalid_publisher",
							"Publisher wrong format!");
					valid = false;
				}
			}

			// Unit price
			if(unitPrice == 0 || unitPrice == 0.0){
				request.setAttribute("rq_unitPrice",
						"Unit price must be a number large than zero!");
				valid = false;
			}

			// Quantity
			if(quantity == 0){
				request.setAttribute("rq_quantity",
						"Quantity must be a number large than zero!");
				valid = false;
			}

			if(valid){
				// Láº¥y tĂªn áº£nh dĂ¹ng Ä‘á»ƒ insert vĂ o database
				image = String.valueOf(request.getAttribute("imageName"));

				// Khá»Ÿi táº¡o Ä‘á»‘i tÆ°á»£ng product chá»©a cĂ¡c giĂ¡ trá»‹ tháº­t nháº­p tá»« bĂªn
				// form
				// dĂ¹ng Ä‘á»ƒ insert vĂ o database
				Product product = new Product(productName, publisher,
						unitPrice, quantity, description, image, datePosted,
						status);

				// Gá»�i hĂ m insert << model
				boolean inserted = productDaoImpl.insert(product);
				if(inserted){
					// ThĂ´ng bĂ¡o thĂªm má»›i thĂ nh cĂ´ng
					request.setAttribute(
							"message",
							"<div id='notify-msg-success'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Insert new product to database successfully!</span></div>");
				} else{
					// ThĂ´ng bĂ¡o thĂªm má»›i tháº¥t báº¡i
					request.setAttribute(
							"message",
							"<div id='notify-msg-error'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Insert new product to database not successfully!</span></div>");
				}
				request.getRequestDispatcher("indexAdmin.jsp").forward(request,
						response);
			} else{
				// Re setup all attribute
				setAttribute(request);
				request.getRequestDispatcher(
						"controller?action=pageCreateNewProduct").forward(
						request, response);
			}
		}
		// End insert

		// Update product
		if(action.equalsIgnoreCase("updateDetailProduct")){
			// Thá»±c hiá»‡n upload file áº£nh, pháº§n xá»­ lĂ½ gĂ¡n giĂ¡ trá»‹ náº±m trong hĂ m
			request.setAttribute("imageName", "");

			// upload()
			upload(request);

			// Check sum
			boolean valid = true;
			String regex = "^[a-zA-Z_0-9.\\s]+$";

			// Product name
			if(productName.equals("") || productName.equals(null)){
				request.setAttribute("rq_productName",
						"Please enter product name!");
				valid = false;
			} else{
				if(!productName.matches(regex)){
					request.setAttribute("invalid_productName",
							"Product name wrong format!");
					valid = false;
				}
			}

			// Publisher
			if(publisher.equals("") || publisher.equals(null)){
				request.setAttribute("rq_publisher", "Please enter publisher!");
				valid = false;
			} else{
				if(!publisher.matches(regex)){
					request.setAttribute("invalid_publisher",
							"Publisher wrong format!");
					valid = false;
				}
			}

			// Unit price
			if(unitPrice == 0 || unitPrice == 0.0){
				request.setAttribute("rq_unitPrice",
						"Unit price must be a number large than zero!");
				valid = false;
			}

			// Quantity
			if(quantity == 0){
				request.setAttribute("rq_quantity",
						"Quantity must be a number large than zero!");
				valid = false;
			}

			if(valid){
				request.setAttribute("hasErrors", "");
				// Láº¥y tĂªn áº£nh dĂ¹ng Ä‘á»ƒ update vĂ o database
				image = String.valueOf(request.getAttribute("imageName"));
				int productId = Integer.valueOf(request
						.getParameter("productId"));
				Product productTemp = productDaoImpl.find(productId);

				if(!image.equals("")){
					// Náº¿u chá»�n áº£nh má»›i, sáº½ upload áº£nh má»›i lĂªn

					// Khá»Ÿi táº¡o Ä‘á»‘i tÆ°á»£ng product chá»©a cĂ¡c giĂ¡ trá»‹ tháº­t nháº­p tá»«
					// bĂªn
					// form dĂ¹ng Ä‘á»ƒ insert vĂ o database
					Product product = new Product();
					product.setProductId(productId);
					product.setProductName(productName);
					product.setPublisher(publisher);
					product.setUnitPrice(unitPrice);
					product.setQuantity(quantity);
					product.setDescription(description);
					product.setImage(image);
					product.setStatus(status);

					// Gá»�i hĂ m update << model
					boolean updated = productDaoImpl.update(product);
					if(updated){
						request.setAttribute(
								"message",
								"<div id='notify-msg-success'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Update information product to database successfully!</span></div>");
					} else{
						request.setAttribute(
								"message",
								"<div id='notify-msg-error'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Update information product to database not successfully!</span></div>");
					}
				} else{
					// NgÆ°á»£c láº¡i láº¥y Ä‘á»‘i tÆ°á»£ng productTemp Ä‘á»ƒ láº¥y tĂªn áº£nh cÅ©

					// Khá»Ÿi táº¡o Ä‘á»‘i tÆ°á»£ng product chá»©a cĂ¡c giĂ¡ trá»‹ tháº­t nháº­p tá»«
					// bĂªn
					// form dĂ¹ng Ä‘á»ƒ insert vĂ o database
					Product product = new Product();
					product.setProductId(productId);
					product.setProductName(productName);
					product.setPublisher(publisher);
					product.setUnitPrice(unitPrice);
					product.setQuantity(quantity);
					product.setDescription(description);
					// Náº¿u khĂ´ng upload áº£nh má»›i, thĂ¬ váº«n giá»¯ tĂªn áº£nh cÅ©
					product.setImage(productTemp.getImage());
					product.setStatus(status);

					// Gá»�i hĂ m update << model
					boolean updated = productDaoImpl.update(product);
					if(updated){
						request.setAttribute(
								"message",
								"<div id='notify-msg-success'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Update information product to database successfully!</span></div>");
					} else{
						request.setAttribute(
								"message",
								"<div id='notify-msg-error'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Update information product to database not successfully!</span></div>");
					}
				}
				if(status.equalsIgnoreCase("Active")){
					request.getRequestDispatcher(
							"controller?action=pageListOfProduct&page=1")
							.forward(request, response);
				} else{
					request.getRequestDispatcher(
							"controller?action=pageListOfProductInTrash&page=1")
							.forward(request, response);
				}
			} else{
				// Re setup all attribute
				request.setAttribute("hasErrors", "error");
				setAttribute(request);
				request.getRequestDispatcher(
						"controller?action=pageDetailProduct&productId="
								+ request.getParameter("productId")).forward(
						request, response);
			}
		}
		// End update product

		// Move product to trash (Cáº­p nháº­t tráº¡ng thĂ¡i thĂ nh Inactive)
		if(action.equalsIgnoreCase("moveProductToTrash")){
			int productId = Integer.valueOf(request.getParameter("productId"));
			Product productTemp = productDaoImpl.find(productId);
			productTemp.setProductId(productId);
			productTemp.setStatus("Inactive");
			// Gá»�i hĂ m update << model
			boolean moved = productDaoImpl.update(productTemp);
			if(moved){
				// ThĂ´ng bĂ¡o chuyá»ƒn product vĂ o thĂ¹ng rĂ¡c thĂ nh cĂ´ng
				request.setAttribute(
						"message",
						"<div id='notify-msg-success'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Move "
								+ productTemp.getProductName()
								+ " to trash successfully!</span></div>");
			} else{
				// ThĂ´ng bĂ¡o chuyá»ƒn product vĂ o thĂ¹ng rĂ¡c tháº¥t báº¡i
				request.setAttribute(
						"message",
						"<div id='notify-msg-error'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Move "
								+ productTemp.getProductName()
								+ " to trash not successfully!</span></div>");
			}
			request.getRequestDispatcher(
					"controller?action=pageListOfProduct&page=1").forward(
					request, response);
		}

		// XĂ³a vÄ©nh viá»…n product khá»�i database
		if(action.equalsIgnoreCase("deletePermanentlyProduct")){
			int productId = Integer.valueOf(request.getParameter("productId"));
			Product product = productDaoImpl.find(productId);
			// Gá»�i hĂ m delete << model
			boolean deleted = productDaoImpl.delete(productId);
			if(deleted){
				// ThĂ´ng bĂ¡o xĂ³a product thĂ nh cĂ´ng
				request.setAttribute(
						"message",
						"<div id='notify-msg-success'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Deleted "
								+ product.getProductName()
								+ " successfully!</span></div>");
			} else{
				// ThĂ´ng bĂ¡o xĂ³a product khĂ´ng thĂ nh cĂ´ng
				request.setAttribute(
						"message",
						"<div id='notify-msg-error'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Deleted  "
								+ product.getProductName()
								+ "  not successfully!</span></div>");
			}
			request.getRequestDispatcher(
					"controller?action=pageListOfProductInTrash&page=1")
					.forward(request, response);
		}

		// TĂ¬m kiáº¿m theo tĂªn product
		if(action.equalsIgnoreCase("pageListOfProductSearch")){
			displayAs = "pageListOfProductSearch";
			String keyword = request.getParameter("q");
			if(keyword.equals("")){
				// Náº¿u tá»« khĂ³a tĂ¬m kiáº¿m rá»—ng thĂ¬ tráº£ vá»� trang home.jsp
				request.getRequestDispatcher("controller?action=pageHome")
						.forward(request, response);
			} else{
				// Tá»« khĂ³a tĂ¬m kiáº¿m khĂ¡c rá»—ng, tráº£ vá»� trang káº¿t quáº£ vá»›i 1
				// attribute lĂ  danh sĂ¡ch tĂ¬m kiáº¿m
				request.setAttribute("q", keyword);
				request.setAttribute("listOfProductResultSearch",
						productDaoImpl.listOfProductResultSearch(keyword));
				request.getRequestDispatcher("indexAdmin.jsp").forward(request,
						response);
			}
		}

		/**
		 * --------------------------------------------------------------------
		 * END PRODUCT HANDLE
		 * --------------------------------------------------------------------
		 */

		/**
		 * --------------------------------------------------------------------
		 * START ORDERS HANDLE
		 * --------------------------------------------------------------------
		 */
		// Xem chi tiáº¿t Ä‘Æ¡n hĂ ng
		if(action.equalsIgnoreCase("viewDetailOrders")){
			displayAs = "pageViewDetailOrders";
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			request.setAttribute("detailOrders",
					orderDaoImpl.orderDetailByOrderId(orderId));
			request.setAttribute("listOfProduct",
					orderDaoImpl.listOfProductByOrders(orderId));
			request.getRequestDispatcher("indexAdmin.jsp").forward(request,
					response);
		}

		// Cáº­p nháº­t user vĂ  status Ä‘Æ¡n hĂ ng
		if(action.equalsIgnoreCase("updateOrders")){
			int userId = Integer.parseInt(request.getParameter("user"));
			String status = request.getParameter("status");
			int orderId = Integer.parseInt(request.getParameter("orderId"));

			Orders orders = new Orders();
			orders.setOrderId(orderId);
			orders.setUserId(userId);
			orders.setStatus(status);

			boolean updated = orderDaoImpl.update(orders);
			if(updated){
				// ThĂ´ng bĂ¡o chuyá»ƒn cáº­p nháº­t Ä‘Æ¡n hĂ ng thĂ nh cĂ´ng
				request.setAttribute(
						"message",
						"<div id='notify-msg-success'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Update orders "
								+ orderId + " successfully!</span></div>");
			} else{
				// ThĂ´ng bĂ¡o cáº­p nháº­t Ä‘Æ¡n hĂ ng tháº¥t báº¡i
				request.setAttribute(
						"message",
						"<div id='notify-msg-error'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Update orders "
								+ orderId + " not successfully!</span></div>");
			}
			request.getRequestDispatcher(
					"controller?action=pageListOfOrders&page=1").forward(
					request, response);
		}

		// XĂ³a vÄ©nh viáº¿n order, sáº½ xĂ³a cĂ¡c orderdetail trÆ°á»›c sau Ä‘Ă³ xĂ³a order
		// theo orderId
		if(action.equalsIgnoreCase("deletePermanentlyOrders")){
			int orderId = Integer.parseInt(request.getParameter("orderId"));

			boolean deleted = orderDetailDaoImpl.delete(orderId);
			if(deleted){
				orderDaoImpl.delete(orderId);
				// ThĂ´ng bĂ¡o xĂ³a Ä‘Æ¡n hĂ ng thĂ nh cĂ´ng
				request.setAttribute(
						"message",
						"<div id='notify-msg-success'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Delete permanently orders "
								+ orderId + " successfully!</span></div>");
			} else{
				// ThĂ´ng bĂ¡o xĂ³a Ä‘Æ¡n hĂ ng tháº¥t báº¡i
				request.setAttribute(
						"message",
						"<div id='notify-msg-error'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Delete permanently orders "
								+ orderId + " not successfully!</span></div>");
			}
			request.getRequestDispatcher(
					"controller?action=pageListOfOrders&page=1").forward(
					request, response);
		}

		// Export order to excel file
		if(action.equals("exportToExcel")){
			int orderId = Integer.parseInt(request.getParameter("orderId"));

			OrderDetailByOrderId order = orderDaoImpl
					.orderDetailByOrderId(orderId);

			List<ProductInOrders> listOfProduct = orderDaoImpl
					.listOfProductByOrders(orderId);

			// Create workbook
			Workbook wb = new HSSFWorkbook();
			Sheet sheet = wb.createSheet("Orders Detail");

			// Style
			CellStyle style = wb.createCellStyle();

			Font font = wb.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);

			Row headerRow = sheet.createRow(0);
			Cell cellHeaderRow = headerRow.createCell(0);
			cellHeaderRow.setCellValue("Orders Detail");
			cellHeaderRow.setCellStyle(style);

			// Space
			Row spaceRow = sheet.createRow(1);

			// OrderId
			Row orderIdRow = sheet.createRow(2);
			Cell orderIdCell = orderIdRow.createCell(0);
			orderIdCell.setCellValue("Order Id: ");
			orderIdCell.setCellStyle(style);
			orderIdRow.createCell(1).setCellValue(order.getOrderId() + "");

			// UserName
			Row userNameRow = sheet.createRow(3);
			Cell userNameCell = userNameRow.createCell(0);
			userNameCell.setCellValue("User Name: ");
			userNameCell.setCellStyle(style);
			userNameRow.createCell(1).setCellValue(order.getUserName());

			// Information of Customer
			Row inforCustomerRow = sheet.createRow(4);
			Cell inforCell = inforCustomerRow.createCell(0);
			inforCell.setCellValue("Information Customer: ");
			inforCell.setCellStyle(style);

			// CustomerName
			Row customerNameRow = sheet.createRow(5);
			customerNameRow.createCell(0).setCellValue("");
			Cell customerNameCell = customerNameRow.createCell(1);
			customerNameCell.setCellValue("Customer Name:");
			customerNameCell.setCellStyle(style);
			customerNameRow.createCell(2).setCellValue(order.getCustomerName());

			// Email
			Row emailRow = sheet.createRow(6);
			emailRow.createCell(0).setCellValue("");
			Cell emailCell = emailRow.createCell(1);
			emailCell.setCellValue("Email:");
			emailCell.setCellStyle(style);
			emailRow.createCell(2).setCellValue(order.getEmail());

			// PhoneNo
			Row phoneNoRow = sheet.createRow(7);
			phoneNoRow.createCell(0).setCellValue("");
			Cell phoneNoCell = phoneNoRow.createCell(1);
			phoneNoCell.setCellValue("Phone Number:");
			phoneNoCell.setCellStyle(style);
			phoneNoRow.createCell(2).setCellValue(order.getPhoneNo());

			// IdentityCardNo
			Row identityCardNoRow = sheet.createRow(8);
			identityCardNoRow.createCell(0).setCellValue("");
			Cell idCardCell = identityCardNoRow.createCell(1);
			idCardCell.setCellValue("Identity Card Number:");
			idCardCell.setCellStyle(style);
			identityCardNoRow.createCell(2).setCellValue(
					order.getIdentityCardNo());

			// Description
			Row descriptionRow = sheet.createRow(9);
			descriptionRow.createCell(0).setCellValue("");
			Cell descriptionCell = descriptionRow.createCell(1);
			descriptionCell.setCellValue("Description:");
			descriptionCell.setCellStyle(style);
			descriptionRow.createCell(2).setCellValue(order.getDescription());

			// ShippingAddress
			Row shippingAddressRow = sheet.createRow(10);
			Cell shipAddrCell = shippingAddressRow.createCell(0);
			shipAddrCell.setCellValue("Shipping Address: ");
			shipAddrCell.setCellStyle(style);
			shippingAddressRow.createCell(1).setCellValue(
					order.getShippingAddress());

			// DateOfPosted
			Row dateOfPostedRow = sheet.createRow(11);
			Cell dateOfPostedCell = dateOfPostedRow.createCell(0);
			dateOfPostedCell.setCellValue("Date Of Posted: ");
			dateOfPostedCell.setCellStyle(style);
			dateOfPostedRow.createCell(1).setCellValue(
					order.getDateOfPosted().toString());

			// Status
			Row statusRow = sheet.createRow(12);
			Cell statusCell = statusRow.createCell(0);
			statusCell.setCellValue("Status: ");
			statusCell.setCellStyle(style);
			statusRow.createCell(1).setCellValue(order.getStatus());

			// Product
			Row productRow = sheet.createRow(13);
			Cell productCell = productRow.createCell(0);
			productCell.setCellValue("Product: ");
			productCell.setCellStyle(style);

			// Product header
			Row productHeaderRow = sheet.createRow(14);
			productHeaderRow.createCell(0).setCellValue("");
			Cell noCell = productHeaderRow.createCell(1);
			noCell.setCellValue("No.");
			noCell.setCellStyle(style);
			Cell productNameCell = productHeaderRow.createCell(2);
			productNameCell.setCellValue("Product Name");
			productNameCell.setCellStyle(style);
			Cell unitPriceCell = productHeaderRow.createCell(3);
			unitPriceCell.setCellValue("Unit Price");
			unitPriceCell.setCellStyle(style);
			Cell qtyCell = productHeaderRow.createCell(4);
			qtyCell.setCellValue("Quantity");
			qtyCell.setCellStyle(style);
			Cell monetizedCell = productHeaderRow.createCell(5);
			monetizedCell.setCellValue("Monetized");
			monetizedCell.setCellStyle(style);

			// Write list of product
			int i = 14, no = 0;
			double t = 0d;
			for(ProductInOrders p : listOfProduct){
				++i;
				no++;
				t += (p.getUnitPrice() * p.getQuantity());
				// Description
				Row itemProductRow = sheet.createRow(i);
				itemProductRow.createCell(0).setCellValue("");
				itemProductRow.createCell(1).setCellValue(no + "");
				itemProductRow.createCell(2).setCellValue(p.getProductName());
				itemProductRow.createCell(3).setCellValue(
						NumberUtil.formatMoneyVietnam(p.getUnitPrice()));
				itemProductRow.createCell(4).setCellValue(p.getQuantity() + "");
				itemProductRow.createCell(5).setCellValue(
						NumberUtil.formatMoneyVietnam((p.getUnitPrice() * p
								.getQuantity())));
			}

			// Space
			Row spaceRow1 = sheet.createRow(i + 1);

			// TotalPrice
			Row totalPriceRow = sheet.createRow(i + 2);
			Cell totalPriceCell = totalPriceRow.createCell(0);
			totalPriceCell.setCellValue("The total amount: ");
			totalPriceCell.setCellStyle(style);
			totalPriceRow.createCell(1).setCellValue(
					NumberUtil.formatMoneyVietnam(t));

			String exportDir = "";

			/*
			 * Khá»Ÿi táº¡o 1 thÆ° má»¥c lÆ°u file export orders to excel file, cĂ³ thá»ƒ
			 * thay Ä‘á»•i thÆ° má»¥c nĂ y báº±ng cĂ¡ch má»Ÿ file \WEB-INF\web.xml vĂ  thay
			 * Ä‘á»•i giĂ¡ trá»‹ tháº» <param-value>
			 */
			File path = new File(request.getServletContext().getInitParameter(
					"orders-export"));

			if(!path.exists()){
				path.mkdir();
				exportDir = path.getAbsolutePath();
			} else{
				exportDir = path.getAbsolutePath();
			}

			FileOutputStream fos = new FileOutputStream(exportDir
					+ "/orders_export_" + order.getOrderId() + ".xls");
			// Write
			wb.write(fos);
			fos.close();
			// ThĂ´ng bĂ¡o chuyá»ƒn export excel file thĂ nh cĂ´ng vĂ  chuyá»ƒn trang
			request.setAttribute(
					"message",
					"<div id='notify-msg-success'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Export orders to excel file successfully, please check on folder follow path: "
							+ exportDir + "</span></div>");
			request.getRequestDispatcher(
					"controller?action=viewDetailOrders&orderId=" + orderId)
					.forward(request, response);
		}
		/**
		 * --------------------------------------------------------------------
		 * END ORDERS HANDLE
		 * --------------------------------------------------------------------
		 */

		/**
		 * --------------------------------------------------------------------
		 * START USER HANDLE
		 * --------------------------------------------------------------------
		 */

		// Move user to trash (Cáº­p nháº­t tráº¡ng thĂ¡i thĂ nh Inactive)
		if(action.equalsIgnoreCase("moveUserToTrash")){
			int userId = Integer.valueOf(request.getParameter("userId"));
			User userTemp = userDaoImpl.find(userId);
			userTemp.setUserId(userId);
			userTemp.setStatus("Inactive");
			// Gá»�i hĂ m update << model
			boolean moved = userDaoImpl.update(userTemp);
			if(moved){
				// ThĂ´ng bĂ¡o chuyá»ƒn user vĂ o thĂ¹ng rĂ¡c thĂ nh cĂ´ng
				request.setAttribute(
						"message",
						"<div id='notify-msg-success'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Move "
								+ userTemp.getFullName()
								+ " to trash successfully!</span></div>");
			} else{
				// ThĂ´ng bĂ¡o chuyá»ƒn user vĂ o thĂ¹ng rĂ¡c tháº¥t báº¡i
				request.setAttribute(
						"message",
						"<div id='notify-msg-error'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Move "
								+ userTemp.getFullName()
								+ " to trash not successfully!</span></div>");
			}
			request.getRequestDispatcher(
					"controller?action=pageListOfUser&page=1").forward(request,
					response);
		}

		// Restore user from trash (Cáº­p nháº­t láº¡i tráº¡ng thĂ¡i thĂ nh 'Active')
		if(action.equalsIgnoreCase("restoreUserFromTrash")){
			int userId = Integer.valueOf(request.getParameter("userId"));
			User userTemp = userDaoImpl.find(userId);
			userTemp.setUserId(userId);
			userTemp.setStatus("Active");
			// Gá»�i hĂ m update << model
			boolean restored = userDaoImpl.update(userTemp);
			if(restored){
				// ThĂ´ng bĂ¡o chuyá»ƒn product vĂ o thĂ¹ng rĂ¡c thĂ nh cĂ´ng
				request.setAttribute(
						"message",
						"<div id='notify-msg-success'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Restore User "
								+ userTemp.getFullName()
								+ " from trash successfully!</span></div>");
			} else{
				// ThĂ´ng bĂ¡o chuyá»ƒn product vĂ o thĂ¹ng rĂ¡c tháº¥t báº¡i
				request.setAttribute(
						"message",
						"<div id='notify-msg-error'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Restore User "
								+ userTemp.getFullName()
								+ " from trash not successfully!</span></div>");
			}
			request.getRequestDispatcher(
					"controller?action=pageListOfUser&page=1").forward(request,
					response);
		}

		// XĂ³a vÄ©nh viá»…n user khá»�i database
		if(action.equalsIgnoreCase("deletePermanentlyUser")){
			int userId = Integer.valueOf(request.getParameter("userId"));
			User user = userDaoImpl.find(userId);
			// Gá»�i hĂ m delete << model
			boolean deleted = userDaoImpl.delete(userId);
			if(deleted){
				// ThĂ´ng bĂ¡o xĂ³a user thĂ nh cĂ´ng
				request.setAttribute(
						"message",
						"<div id='notify-msg-success'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Deleted "
								+ user.getFullName()
								+ " successfully!</span></div>");
			} else{
				// ThĂ´ng bĂ¡o xĂ³a user khĂ´ng thĂ nh cĂ´ng
				request.setAttribute(
						"message",
						"<div id='notify-msg-error'><span class='close-notify' onclick='closeMsg()'>x</span><span class='content-msg'>Deleted  "
								+ user.getFullName()
								+ "  not successfully!</span></div>");
			}
			request.getRequestDispatcher(
					"controller?action=pageListOfUserInTrash&page=1").forward(
					request, response);
		}

		/**
		 * --------------------------------------------------------------------
		 * END USER HANDLE
		 * --------------------------------------------------------------------
		 */

		/**
		 * --------------------------------------------------------------------
		 * START CUSTOMER HANDLE
		 * --------------------------------------------------------------------
		 */

		// TĂ¬m kiáº¿m product cho trang index dĂ¹ng cho khĂ¡ch hĂ ng
		if(action.equalsIgnoreCase("productSearch")){
			displayAs = "pageListOfProductSearch";
			String keyword = request.getParameter("q");
			if(keyword.equals("")){
				// Náº¿u tá»« khĂ³a tĂ¬m kiáº¿m rá»—ng thĂ¬ tráº£ vá»� trang home.jsp
				request.getRequestDispatcher("forward.jsp").forward(request,
						response);
			} else{
				// Tá»« khĂ³a tĂ¬m kiáº¿m khĂ¡c rá»—ng, tráº£ vá»� trang káº¿t quáº£ vá»›i 1
				// attribute lĂ  danh sĂ¡ch tĂ¬m kiáº¿m
				request.setAttribute("q", keyword);
				request.setAttribute("listOfProductResultSearch",
						productDaoImpl.listOfProductResultSearch(keyword));
				request.getRequestDispatcher("index.jsp").forward(request,
						response);
			}
		}

		// Danh sĂ¡ch product theo nhĂ  sáº£n xuáº¥t (publisher)
		if(action.equalsIgnoreCase("findProductByPublisher")){
			displayAs = "pageListOfProductByPublisher";
			String keyword = request.getParameter("name");
			if(keyword.equals("")){
				// Náº¿u tá»« khĂ³a tĂ¬m kiáº¿m rá»—ng thĂ¬ tráº£ vá»� trang home.jsp
				request.getRequestDispatcher("forward.jsp").forward(request,
						response);
			} else{
				// Tá»« khĂ³a tĂ¬m kiáº¿m khĂ¡c rá»—ng, tráº£ vá»� trang káº¿t quáº£ vá»›i 1
				// attribute lĂ  danh sĂ¡ch tĂ¬m kiáº¿m
				request.setAttribute("name", keyword);
				request.setAttribute("listOfProductByPublisher",
						productDaoImpl.listOfProductByPublisher(keyword));
				request.getRequestDispatcher("index.jsp").forward(request,
						response);
			}
		}

		// Trang chi tiáº¿t sáº£n pháº©m cho khĂ¡ch hĂ ng
		if(action.equalsIgnoreCase("productDetail")){
			displayAs = "pageProductDetail";
			int productId = Integer.parseInt(request.getParameter("productId"));
			Product product = productDaoImpl.find(productId);

			// Gá»­i request chi tiáº¿t product
			request.setAttribute("productDetail", product);

			// Gá»­i danh sĂ¡ch sáº£n pháº©m liĂªn quan
			request.setAttribute("listOfRelatedProduct", productDaoImpl
					.listOfProductByPublisher(product.getPublisher()));

			// Gá»­i danh sĂ¡ch publisher
			request.setAttribute("listOfPublisher",
					productDaoImpl.listOfPublisher());

			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
		}

	}

	/**
	 * ------------------------------------------------------------------------
	 * END CUSTOMER HANDLE
	 * ------------------------------------------------------------------------
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		progressRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		progressRequest(request, response);
	}

	// Set attribute
	private void setAttribute(HttpServletRequest request){
		request.setAttribute("productName", productName);
		request.setAttribute("publisher", publisher);
		request.setAttribute("unitPrice", unitPrice);
		request.setAttribute("quantity", quantity);
		request.setAttribute("description", description);
		request.setAttribute("rq_image", "Please choose a picture to upload!");
	}

	// PhÆ°Æ¡ng thá»©c upload file
	private boolean upload(HttpServletRequest request){
		try{
			String uploadDir = "";
			boolean success = true;
			/*
			 * Khá»Ÿi táº¡o 1 thÆ° má»¥c lÆ°u file táº£i lĂªn, cĂ³ thá»ƒ thay Ä‘á»•i thÆ° má»¥c nĂ y
			 * báº±ng cĂ¡ch má»Ÿ file \WEB-INF\web.xml vĂ  thay Ä‘á»•i giĂ¡ trá»‹ tháº»
			 * <param-value>
			 */
			File path = new File(getServletContext().getInitParameter(
					"upload-file"));

			// Kiá»ƒm tra náº¿u thÆ° má»¥c chá»©a chÆ°a tá»“n táº¡i thĂ¬ táº¡o má»›i vĂ  gĂ¡n path
			if(!path.exists()){
				path.mkdir();
				uploadDir = path.getAbsolutePath();
			} else{
				uploadDir = path.getAbsolutePath();
			}

			if(ServletFileUpload.isMultipartContent(request)){
				try{
					List<FileItem> multiparts = new ServletFileUpload(
							new DiskFileItemFactory()).parseRequest(request);

					for(FileItem item : multiparts){
						if(!item.isFormField()){
							String name = new File(item.getName()).getName();
							item.write(new File(uploadDir + File.separator
									+ name));
							request.setAttribute("imageName", name);
						} else{
							// VĂ¬ khĂ´ng sá»­ dá»¥ng Ä‘Æ°á»£c request.getParameter() Ä‘á»ƒ
							// láº¥y giĂ¡ trá»‹ vĂ¬ form sá»­ dá»¥ng multipart/form-data,
							// thay vĂ o Ä‘Ă³ ta gĂ¡n giĂ¡ trá»‹ cá»§a cĂ¡c trÆ°á»�ng cáº§n láº¥y
							// táº¡i Ä‘Ă¢y
							if(item.getFieldName().equals("productName")){
								productName = item.getString();
							}
							if(item.getFieldName().equals("publisher")){
								publisher = item.getString();
							}
							if(item.getFieldName().equals("unitPrice")){
								unitPrice = Float.valueOf(item.getString());
							}
							if(item.getFieldName().equals("quantity")){
								quantity = Integer.valueOf(item.getString());
							}
							if(item.getFieldName().equals("description")){
								description = item.getString();
							}
							if(item.getFieldName().equals("fileImage")){
								image = item.getString();
							}
							if(item.getFieldName().equals("status")){
								status = item.getString();
							}
						}
					}
					// File uploaded successfully
				} catch(Exception e){
					e.printStackTrace();
				}
			} else{
				success = false;
				request.setAttribute("message", "Could not upload!");
			}

			return success;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

}
