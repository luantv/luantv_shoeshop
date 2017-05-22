package dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.User;
import utility.HibernateUtil;
import dao.UserDAO;

@SuppressWarnings("all")
public class UserDAOImpl implements UserDAO{
	private SessionFactory	sessionFactory	= HibernateUtil.getSessionFactory();

	@Override
	public boolean insert(User user){
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			session.save(user);
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
	public boolean update(User newUser){
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			User user = (User) find(newUser.getUserId());
			user.setFullName(newUser.getFullName());
			user.setAddress(newUser.getAddress());
			user.setDateOfBirth(newUser.getDateOfBirth());
			user.setEmail(newUser.getEmail());
			user.setPhoneNo(newUser.getPhoneNo());
			user.setIdentityCardNo(newUser.getIdentityCardNo());
			user.setStatus(newUser.getStatus());
			session.update(user);
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
	public boolean delete(int userId){
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			session.delete(find(userId));
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
	public User find(int userId){
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			User user = (User) session.get(User.class, userId);
			session.getTransaction().commit();
			session.close();
			return user;
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return null;
		}
	}

	@Override
	public int count(){
		Session session = sessionFactory.openSession();
		try{
			int count = 0;
			session.beginTransaction();
			count = ((Long) session
					.createQuery(
							"SELECT COUNT(*) FROM User WHERE Status = :Status")
					.setParameter("Status", "Active").uniqueResult())
					.intValue();
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

	@Override
	public int countInactive(){
		Session session = sessionFactory.openSession();
		try{
			int count = 0;
			session.beginTransaction();
			count = ((Long) session
					.createQuery(
							"SELECT COUNT(*) FROM User WHERE Status = :Status")
					.setParameter("Status", "Inactive").uniqueResult())
					.intValue();
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

	@Override
	public List<User> listOfUser(){
		Session session = sessionFactory.openSession();
		try{
			List listOfUser = null;
			session.beginTransaction();
			listOfUser = session
					.createQuery("FROM User WHERE Status = :Status")
					.setParameter("Status", "Active").list();
			session.getTransaction().commit();
			session.close();
			return listOfUser;
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return null;
		}
	}

	@Override
	public List<User> selectLimit(int start, int end){
		Session session = sessionFactory.openSession();
		try{
			List listOfProduct = null;
			session.beginTransaction();
			listOfProduct = session
					.createQuery("FROM User WHERE Status = :Status")
					.setParameter("Status", "Active").setFirstResult(start)
					.setMaxResults(end).list();
			session.getTransaction().commit();
			session.close();
			return listOfProduct;
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return null;
		}
	}

	@Override
	public List<User> selectLimitInTrash(int start, int end){
		Session session = sessionFactory.openSession();
		try{
			List listOfProduct = null;
			session.beginTransaction();
			listOfProduct = session
					.createQuery("FROM User WHERE Status = :Status")
					.setParameter("Status", "Inactive").setFirstResult(start)
					.setMaxResults(end).list();
			session.getTransaction().commit();
			session.close();
			return listOfProduct;
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return null;
		}
	}
}
