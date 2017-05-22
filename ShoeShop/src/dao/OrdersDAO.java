package dao;

import java.util.List;

import entities.OrderDetailByOrderId;
import entities.OrderDisplay;
import entities.Orders;
import entities.ProductInOrders;

public interface OrdersDAO{
	public boolean insert(Orders order);
	public boolean update(Orders order);
	public boolean delete(int orderId);
	public Orders find(int orderId);
	public Orders findLastOrder();
	public List<OrderDisplay> selectLimit(int start, int end);
	public OrderDetailByOrderId orderDetailByOrderId(int orderId);
	public List<ProductInOrders> listOfProductByOrders(int orderId);
	public int count();
}
