package entities;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("all")
@Entity
@Table(name = "orderdetail")
public class Orderdetail implements java.io.Serializable{
	private OrderdetailId id;

	public Orderdetail(){
	}

	public Orderdetail(OrderdetailId id){
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "orderId", column = @Column(name = "OrderId", nullable = false)),
			@AttributeOverride(name = "productId", column = @Column(name = "ProductId", nullable = false)),
			@AttributeOverride(name = "quantity", column = @Column(name = "Quantity", nullable = false)) })
	public OrderdetailId getId(){
		return this.id;
	}

	public void setId(OrderdetailId id){
		this.id = id;
	}

}
