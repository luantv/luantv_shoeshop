package entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("all")
@Embeddable
public class OrderdetailId implements java.io.Serializable{
	private int	orderId;
	private int	productId;
	private int	quantity;

	public OrderdetailId(){
	}

	public OrderdetailId(int orderId, int productId, int quantity){
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
	}

	@Column(name = "OrderId")
	public int getOrderId(){
		return this.orderId;
	}

	public void setOrderId(int orderId){
		this.orderId = orderId;
	}

	@Column(name = "ProductId")
	public int getProductId(){
		return this.productId;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	@Column(name = "Quantity")
	public int getQuantity(){
		return this.quantity;
	}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public boolean equals(Object other){
		if((this == other))
			return true;
		if((other == null))
			return false;
		if(!(other instanceof OrderdetailId))
			return false;
		OrderdetailId castOther = (OrderdetailId) other;

		return (this.getOrderId() == castOther.getOrderId())
				&& (this.getProductId() == castOther.getProductId())
				&& (this.getQuantity() == castOther.getQuantity());
	}

	public int hashCode(){
		int result = 17;

		result = 37 * result + this.getOrderId();
		result = 37 * result + this.getProductId();
		result = 37 * result + this.getQuantity();
		return result;
	}

}
