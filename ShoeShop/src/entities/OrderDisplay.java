package entities;

import java.util.Date;

public class OrderDisplay{
	private int		orderId;
	private String	userName;
	private String	customerName;
	private double	totalPrice;
	private Date	dateOfPosted;
	private String	status;

	public OrderDisplay(){
		super();
	}

	public OrderDisplay(int orderId, String userName, String customerName,
			double totalPrice, Date dateOfPosted, String status){
		super();
		this.orderId = orderId;
		this.userName = userName;
		this.customerName = customerName;
		this.totalPrice = totalPrice;
		this.dateOfPosted = dateOfPosted;
		this.status = status;
	}

	public int getOrderId(){
		return orderId;
	}

	public double getTotalPrice(){
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice){
		this.totalPrice = totalPrice;
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
