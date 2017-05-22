package utility;

import entities.Product;

public class ProductInCart{
	private Product	product;
	private int		quantity;

	public ProductInCart(){
		super();
	}

	public ProductInCart(Product product, int quantity){
		super();
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct(){
		return product;
	}

	public void setProduct(Product product){
		this.product = product;
	}

	public int getQuantity(){
		return quantity;
	}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}
	
	public double getTotalPrice(){
		return product.getUnitPrice() * quantity;
	}
	
	@Override
	public boolean equals(Object orther){
		if(this == orther)
			return true;
		if(orther == null)
			return false;
		if(getClass() != orther.getClass())
			return false;
		ProductInCart castOrther = (ProductInCart) orther;
		if(product.getProductId() == 0){
			if(castOrther.getProduct().getProductId() != 0)
				return false;
		}else if(castOrther.getProduct().getProductId() != product.getProductId())
			return false;
		return true;
	}
}
