package dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import utility.HibernateUtil;
import entities.Product;
import dao.ProductDAO;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class ProductDAOImpl implements ProductDAO{
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@Override
	public boolean insert(Product product){
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			session.save(product);
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
	public boolean update(Product newProduct){
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			Product product = (Product) find(newProduct.getProductId());
			product.setProductName(newProduct.getProductName());
			product.setPublisher(newProduct.getPublisher());
			product.setUnitPrice(newProduct.getUnitPrice());
			product.setQuantity(newProduct.getQuantity());
			product.setDescription(newProduct.getDescription());
			product.setImage(newProduct.getImage());
			product.setStatus(newProduct.getStatus());
			session.update(product);
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
	public boolean delete(int productId){
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			session.delete(find(productId));
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
	public Product find(int productId){
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			Product product = (Product) session.get(Product.class, productId);
			session.getTransaction().commit();
			session.close();
			return product;
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return null;
		}
	}

	@Override
	public List<Product> listOfProduct(){
		Session session = sessionFactory.openSession();
		try{
			List list = null;
			session.beginTransaction();
			list = session.createQuery("FROM Product WHERE Status = 'Active'")
					.list();
			session.getTransaction().commit();
			session.close();
			return list;
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
							"SELECT COUNT(*) FROM Product WHERE Status = :Status")
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
							"SELECT COUNT(*) FROM Product WHERE Status = :Status")
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
	public List<Product> selectLimitAll(int start, int end){
		Session session = sessionFactory.openSession();
		try{
			List listOfProduct = null;
			session.beginTransaction();
			listOfProduct = session
					.createQuery(
							"FROM Product WHERE Status = :Status ORDER BY ProductId DESC")
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
	public List<Product> selectLimit(int start, int end){
		Session session = sessionFactory.openSession();
		try{
			List listOfProduct = null;
			session.beginTransaction();
			listOfProduct = session
					.createQuery(
							"FROM Product WHERE Status = :Status AND Quantity > 0 ORDER BY ProductId DESC")
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
	public List<Product> selectLimitInTrash(int start, int end){
		Session session = sessionFactory.openSession();
		try{
			List listOfProduct = null;
			session.beginTransaction();
			listOfProduct = session
					.createQuery("FROM Product WHERE Status = :Status")
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

	@Override
	public List<Product> listOfProductResultSearch(String keyword){
		Session session = sessionFactory.openSession();
		try{
			List listOfProduct = null;
			session.beginTransaction();
			listOfProduct = session.createQuery(
					"FROM Product WHERE ProductName LIKE '%" + keyword + "%'")
					.list();
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
	public List<Product> listOfNewProduct(){
		Session session = sessionFactory.openSession();
		try{
			List listOfProduct = null;
			session.beginTransaction();
			listOfProduct = session
					.createQuery(
							"FROM Product WHERE Status = 'Active' AND Quantity > 0 ORDER BY DatePosted DESC")
					.setMaxResults(5).list();
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
	public List<Product> listOfSellingProduct(){
		Session session = sessionFactory.openSession();
		try{
			List listOfProduct = null;
			session.beginTransaction();
			listOfProduct = session
					.createQuery(
							"FROM Product WHERE Status = 'Active' AND Quantity > 0 AND UnitPrice BETWEEN 1000000 AND 1400000")
					.setMaxResults(5).list();
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
	public List<Product> listOfProductByPublisher(String publisher){
		Session session = sessionFactory.openSession();
		try{
			List listOfProduct = null;
			session.beginTransaction();
			listOfProduct = session
					.createQuery(
							"FROM Product WHERE Publisher = :Publisher AND Status = 'Active'")
					.setParameter("Publisher", publisher).list();
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
	public List<String> listOfPublisher() throws HibernateException{
		Session session = sessionFactory.openSession();
		try{
			List<String> list = new ArrayList<String>();
			session.beginTransaction();
			List<String> l = session.createQuery(
					"SELECT DISTINCT publisher FROM Product").list();
			for(String s : l){
				list.add(s);
			}
			session.getTransaction().commit();
			session.close();
			return list;
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return null;
		}
	}
}
