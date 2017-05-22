package entities;

import java.util.Date;

public class OrderDetailByOrderId{
	private int		orderId;
	private String	userName;
	private String	customerName;
	private String	email;
	private String	phoneNo;
	private String	identityCardNo;
	private String	description;
	private String	shippingAddress;
	private Date	dateOfPosted;
	private String	status;

	public OrderDetailByOrderId(){
		super();
	}

	public OrderDetailByOrderId(int orderId, String userName,
			String customerName, String email, String phoneNo,
			String identityCardNo, String description, String shippingAddress,
			Date dateOfPosted, String status){
		super();
		this.orderId = orderId;
		this.userName = userName;
		this.customerName = customerName;
		this.email = email;
		this.phoneNo = phoneNo;
		this.identityCardNo = identityCardNo;
		this.description = description;
		this.shippingAddress = shippingAddress;
		this.dateOfPosted = dateOfPosted;
		this.status = status;
	}

	public int getOrderId(){
		return orderId;
	}

	public void setOrderId(int orderId){
		this.orderId = orderId;
	}

	public String getUserName(){
		return userName;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getCustomerName(){
		return customerName;
	}

	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getPhoneNo(){
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo){
		this.phoneNo = phoneNo;
	}

	public String getIdentityCardNo(){
		return identityCardNo;
	}

	public void setIdentityCardNo(String identityCardNo){
		this.identityCardNo = identityCardNo;
	}

	public String getDescription(){
		return description;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getShippingAddress(){
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress){
		this.shippingAddress = shippingAddress;
	}

	public Date getDateOfPosted(){
		return dateOfPosted;
	}

	public void setDateOfPosted(Date dateOfPosted){
		this.dateOfPosted = dateOfPosted;
	}

	public String getStatus(){
		return status;
	}

	public void setStatus(String status){
		this.status = status;
	}
}
