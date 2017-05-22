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
@Table(name = "orders")
public class Orders implements java.io.Serializable{
	private Integer	orderId;
	private String	customerName;
	private String	email;
	private String	phoneNo;
	private String	identityCardNo;
	private String	description;
	private Date	dateOfPosted;
	private String	shippingAddress;
	private int		userId;
	private String	status;

	public Orders(){
	}

	public Orders(String customerName, String email, String phoneNo,
			String identityCardNo, Date dateOfPosted, String shippingAddress,
			int userId, String status){
		this.customerName = customerName;
		this.email = email;
		this.phoneNo = phoneNo;
		this.identityCardNo = identityCardNo;
		this.dateOfPosted = dateOfPosted;
		this.shippingAddress = shippingAddress;
		this.userId = userId;
		this.status = status;
	}

	public Orders(String customerName, String email, String phoneNo,
			String identityCardNo, String description, Date dateOfPosted,
			String shippingAddress, int userId, String status){
		this.customerName = customerName;
		this.email = email;
		this.phoneNo = phoneNo;
		this.identityCardNo = identityCardNo;
		this.description = description;
		this.dateOfPosted = dateOfPosted;
		this.shippingAddress = shippingAddress;
		this.userId = userId;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "OrderId")
	public Integer getOrderId(){
		return this.orderId;
	}

	public void setOrderId(Integer orderId){
		this.orderId = orderId;
	}

	@Column(name = "CustomerName")
	public String getCustomerName(){
		return this.customerName;
	}

	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}

	@Column(name = "Email")
	public String getEmail(){
		return this.email;
	}

	public void setEmail(String email){
		this.email = email;
	}

	@Column(name = "PhoneNo")
	public String getPhoneNo(){
		return this.phoneNo;
	}

	public void setPhoneNo(String phoneNo){
		this.phoneNo = phoneNo;
	}

	@Column(name = "IdentityCardNo")
	public String getIdentityCardNo(){
		return this.identityCardNo;
	}

	public void setIdentityCardNo(String identityCardNo){
		this.identityCardNo = identityCardNo;
	}

	@Column(name = "Description")
	public String getDescription(){
		return this.description;
	}

	public void setDescription(String description){
		this.description = description;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DateOfPosted")
	public Date getDateOfPosted(){
		return this.dateOfPosted;
	}

	public void setDateOfPosted(Date dateOfPosted){
		this.dateOfPosted = dateOfPosted;
	}

	@Column(name = "ShippingAddress")
	public String getShippingAddress(){
		return this.shippingAddress;
	}

	public void setShippingAddress(String shippingAddress){
		this.shippingAddress = shippingAddress;
	}

	@Column(name = "UserId")
	public int getUserId(){
		return this.userId;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	@Column(name = "Status")
	public String getStatus(){
		return this.status;
	}

	public void setStatus(String status){
		this.status = status;
	}

}
