package utility;

import java.util.ArrayList;
import java.util.List;

import entities.Product;

@SuppressWarnings("all")
public class Cart{
	private ArrayList<ProductInCart> cart;

	public Cart(){
		cart = new ArrayList<ProductInCart>();
	}

	public ArrayList<ProductInCart> getCart(){
		return cart;
	}

	public void addProduct(ProductInCart p){
		if(cart.contains(p)){
			ProductInCart product = cart.get(cart.indexOf(p));
			product.setQuantity(product.getQuantity() + p.getQuantity());
		} else{
			cart.add(p);
		}
	}

	public void updateProduct(ProductInCart p){
		if(cart.contains(p)){
			ProductInCart product = cart.get(cart.indexOf(p));
			product.setQuantity(product.getQuantity() + p.getQuantity());
		}
	}

	public boolean removeProduct(ProductInCart product){
		if(!cart.contains(product))
			return false;
		cart.remove(product);
		return true;
	}

	public int count(){
		return cart.size();
	}

	public double totalPrice(){
		double t = 0d;
		for(ProductInCart p : cart){
			t += p.getTotalPrice();
		}
		return t;
	}
}
