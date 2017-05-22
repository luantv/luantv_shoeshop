package dao;

import entities.Orderdetail;

public interface OrderdetailDAO{
	public boolean insert(Orderdetail orderDetail);
	public boolean update(Orderdetail orderDetail);
	public boolean delete(int orderId);
	public Orderdetail find(int orderId);
}
