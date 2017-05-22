package dao;

import java.util.List;

import entities.Product;

public interface ProductDAO{
	public boolean insert(Product product);
	public boolean update(Product product);
	public boolean delete(int productId);
	public Product find(int productId);
	public List<Product> listOfProduct();
	public int count();
	public int countInactive();
	public List<Product> selectLimitAll(int start, int end);
	public List<Product> selectLimit(int start, int end);
	public List<Product> selectLimitInTrash(int start, int end);
	public List<Product> listOfProductResultSearch(String keyword);
	public List<Product> listOfNewProduct();
	public List<Product> listOfSellingProduct();
	public List<Product> listOfProductByPublisher(String publisher);
	public List<String> listOfPublisher();
}
