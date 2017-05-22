package entities;

public class ProductInOrders{
	private int		productId;
	private String	productName;
	private String	image;
	private double	unitPrice;
	private int		quantity;

	public ProductInOrders(){
		super();
	}

	public ProductInOrders(int productId, String productName, String image,
			double unitPrice, int quantity){
		super();
		this.productId = productId;
		this.productName = productName;
		this.image = image;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}

	public int getProductId(){
		return productId;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public String getProductName(){
		return productName;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getImage(){
		return image;
	}

	public void setImage(String image){
		this.image = image;
	}

	public double getUnitPrice(){
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice){
		this.unitPrice = unitPrice;
	}

	public int getQuantity(){
		return quantity;
	}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}
}
