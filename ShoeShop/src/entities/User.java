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
import javax.persistence.UniqueConstraint;

@SuppressWarnings("all")
@Entity
@Table(name = "user", uniqueConstraints = {
		@UniqueConstraint(columnNames = "Email"),
		@UniqueConstraint(columnNames = "IdentityCardNo"),
		@UniqueConstraint(columnNames = "PhoneNo") })
public class User implements java.io.Serializable{
	private Integer	userId;
	private String	fullName;
	private String	address;
	private Date	dateOfBirth;
	private String	email;
	private String	phoneNo;
	private String	identityCardNo;
	private String	status;

	public User(){
	}

	public User(String fullName, String address, String email, String phoneNo,
			String identityCardNo, String status){
		this.fullName = fullName;
		this.address = address;
		this.email = email;
		this.phoneNo = phoneNo;
		this.identityCardNo = identityCardNo;
		this.status = status;
	}

	public User(String fullName, String address, Date dateOfBirth,
			String email, String phoneNo, String identityCardNo, String status){
		this.fullName = fullName;
		this.address = address;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.phoneNo = phoneNo;
		this.identityCardNo = identityCardNo;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "UserId")
	public Integer getUserId(){
		return this.userId;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	@Column(name = "FullName")
	public String getFullName(){
		return this.fullName;
	}

	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	@Column(name = "Address")
	public String getAddress(){
		return this.address;
	}

	public void setAddress(String address){
		this.address = address;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DateOfBirth")
	public Date getDateOfBirth(){
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth){
		this.dateOfBirth = dateOfBirth;
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

	@Column(name = "Status")
	public String getStatus(){
		return this.status;
	}

	public void setStatus(String status){
		this.status = status;
	}

}
