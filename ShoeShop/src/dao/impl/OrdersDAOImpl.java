package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import utility.HibernateUtil;
import utility.JDBCUtil;
import dao.OrdersDAO;
import entities.OrderDetailByOrderId;
import entities.OrderDisplay;
import entities.Orders;
import entities.ProductInOrders;

public class OrdersDAOImpl implements OrdersDAO{
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	@Override
	public boolean insert(Orders orders){
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			session.save(orders);
			session.getTransaction().commit();
			session.close();
			return true;
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return false;
		}
	}

	@Override
	public boolean update(Orders newOrder){
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			Orders orders = (Orders) find(newOrder.getOrderId());
			orders.setOrderId(newOrder.getOrderId());
			orders.setUserId(newOrder.getUserId());
			orders.setStatus(newOrder.getStatus());
			session.update(orders);
			session.getTransaction().commit();
			session.close();
			return true;
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return false;
		}
	}

	@Override
	public boolean delete(int ordersId){
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			session.delete(find(ordersId));
			session.getTransaction().commit();
			session.close();
			return true;
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return false;
		}
	}

	@Override
	public Orders find(int ordersId){
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			Orders orders = (Orders) session.get(Orders.class, ordersId);
			session.getTransaction().commit();
			session.close();
			return orders;
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return null;
		}
	}

	@Override
	public Orders findLastOrder(){
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			Orders orders = (Orders) session
					.createQuery("FROM Orders ORDER BY OrderId DESC")
					.setMaxResults(1).uniqueResult();
			session.getTransaction().commit();
			session.close();
			return orders;
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return null;
		}
	}

	@Override
	public List<OrderDisplay> selectLimit(int start, int end){
		try{
			List<OrderDisplay> listOfOrders = new ArrayList<OrderDisplay>();
			conn = JDBCUtil.getConnection();
			String sql = "SELECT o.OrderId, u.FullName, o.CustomerName, SUM(od.Quantity * p.UnitPrice), o.DateOfPosted, o.Status FROM Orders o JOIN OrderDetail od ON od.OrderId = o.OrderId JOIN User u ON u.UserId = o.UserId JOIN Product p ON p.ProductId = od.ProductId GROUP BY o.OrderId, u.FullName, o.CustomerName, o.DateOfPosted, o.Status ORDER BY o.OrderId DESC LIMIT ?, ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while(rs.next()){
				OrderDisplay orders = new OrderDisplay(rs.getInt(1),
						rs.getString(2), rs.getString(3), rs.getDouble(4),
						rs.getDate(5), rs.getString(6));
				listOfOrders.add(orders);
			}
			return listOfOrders;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		} finally{
			JDBCUtil.close(conn);
		}
	}
	@Override
	public OrderDetailByOrderId orderDetailByOrderId(int orderId){
		try{
			OrderDetailByOrderId ordersDetail = new OrderDetailByOrderId();
			conn = JDBCUtil.getConnection();
			String sql = "SELECT o.OrderId, u.FullName, o.CustomerName, o.Email, o.PhoneNo, o.IdentityCardNo, o.Description, o.ShippingAddress, o.DateOfPosted, o.Status FROM Orders o JOIN OrderDetail od ON od.OrderId = o.OrderId JOIN User u ON u.UserId = o.UserId WHERE o.OrderId = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, orderId);
			rs = ps.executeQuery();
			if(rs.next()){
				ordersDetail.setOrderId(rs.getInt(1));
				ordersDetail.setUserName(rs.getString(2));
				ordersDetail.setCustomerName(rs.getString(3));
				ordersDetail.setEmail(rs.getString(4));
				ordersDetail.setPhoneNo(rs.getString(5));
				ordersDetail.setIdentityCardNo(rs.getString(6));
				ordersDetail.setDescription(rs.getString(7));
				ordersDetail.setShippingAddress(rs.getString(8));
				ordersDetail.setDateOfPosted(rs.getDate(9));
				ordersDetail.setStatus(rs.getString(10));
			}
			return ordersDetail;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		} finally{
			JDBCUtil.close(conn);
		}
	}

	@Override
	public List<ProductInOrders> listOfProductByOrders(int ordersId){
		try{
			List<ProductInOrders> listOfProduct = new ArrayList<ProductInOrders>();
			conn = JDBCUtil.getConnection();
			String sql = "SELECT p.ProductId, p.ProductName, p.Image, p.UnitPrice, od.Quantity FROM Product p JOIN OrderDetail od ON od.ProductId = p.ProductId WHERE od.OrderId = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, ordersId);
			rs = ps.executeQuery();
			while(rs.next()){
				ProductInOrders product = new ProductInOrders(rs.getInt(1),
						rs.getString(2), rs.getString(3), rs.getDouble(4),
						rs.getInt(5));
				listOfProduct.add(product);
			}
			return listOfProduct;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		} finally{
			JDBCUtil.close(conn);
		}
	}

	@Override
	public int count(){
		Session session = sessionFactory.openSession();
		try{
			int count = 0;
			session.beginTransaction();
			count = ((Long) session.createQuery("SELECT COUNT(*) FROM Orders")
					.uniqueResult()).intValue();
			session.getTransaction().commit();
			session.close();
			return count;
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return 0;
		}
	}
}
