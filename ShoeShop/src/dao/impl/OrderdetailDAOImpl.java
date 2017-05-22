package dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import utility.HibernateUtil;
import dao.OrderdetailDAO;
import entities.Orderdetail;
import entities.OrderdetailId;

@SuppressWarnings("all")
public class OrderdetailDAOImpl implements OrderdetailDAO{
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@Override
	public boolean insert(Orderdetail orderDetail){
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			session.save(orderDetail);
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
	public boolean update(Orderdetail newOrderdetail){
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			Orderdetail orderDetail = (Orderdetail) find(newOrderdetail.getId()
					.getOrderId());
			OrderdetailId orderTemp = newOrderdetail.getId();
			orderDetail.setId(orderTemp);
			session.update(orderDetail);
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
	public boolean delete(int orderId){
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			Query query = session.createQuery(
					"DELETE FROM Orderdetail WHERE OrderId = :OrderId")
					.setParameter("OrderId", orderId);
			int deleted = query.executeUpdate();
			session.getTransaction().commit();
			session.close();
			if(deleted > 0)
				return true;
			else
				return false;
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return false;
		}
	}

	@Override
	public Orderdetail find(int orderId){
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			Orderdetail order = null;
			Query query = session.createQuery(
					"FROM Orderdetail WHERE OrderId = :OrderId").setParameter(
					"OrderId", orderId);
			order = (Orderdetail) query.list();
			session.getTransaction().commit();
			session.close();
			return order;
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return null;
		}
	}
}
