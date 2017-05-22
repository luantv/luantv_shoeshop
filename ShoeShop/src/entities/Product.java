package entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("all")
@Entity
@Table(name = "product")
public class Product implements java.io.Serializable{

	private Integer	productId;
	private String	productName;
	private String	publisher;
	private float	unitPrice;
	private int		quantity;
	private String	description;
	private String	image;
	private Date	datePosted;
	private String	status;

	public Product(){
	}

	public Product(String productName, String publisher, float unitPrice,
			int quantity, String image, Date datePosted, String status){
		this.productName = productName;
		this.publisher = publisher;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.image = image;
		this.datePosted = datePosted;
		this.status = status;
	}

	public Product(String productName, String publisher, float unitPrice,
			int quantity, String description, String image, Date datePosted,
			String status){
		this.productName = productName;
		this.publisher = publisher;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.description = description;
		this.image = image;
		this.datePosted = datePosted;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ProductId")
	public Integer getProductId(){
		return this.productId;
	}

	public void setProductId(Integer productId){
		this.productId = productId;
	}

	@Column(name = "ProductName")
	public String getProductName(){
		return this.productName;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	@Column(name = "Publisher")
	public String getPublisher(){
		return this.publisher;
	}

	public void setPublisher(String publisher){
		this.publisher = publisher;
	}

	@Column(name = "UnitPrice")
	public float getUnitPrice(){
		return this.unitPrice;
	}

	public void setUnitPrice(float unitPrice){
		this.unitPrice = unitPrice;
	}

	@Column(name = "Quantity")
	public int getQuantity(){
		return this.quantity;
	}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	@Column(name = "Description")
	public String getDescription(){
		return this.description;
	}

	public void setDescription(String description){
		this.description = description;
	}

	@Column(name = "Image")
	public String getImage(){
		return this.image;
	}

	public void setImage(String image){
		this.image = image;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DatePosted")
	public Date getDatePosted(){
		return this.datePosted;
	}

	public void setDatePosted(Date datePosted){
		this.datePosted = datePosted;
	}

	@Column(name = "Status")
	public String getStatus(){
		return this.status;
	}

	public void setStatus(String status){
		this.status = status;
	}

}
