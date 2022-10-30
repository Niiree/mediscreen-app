package com.mediscreen.clientui.beans;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class PatientBean {

	@Id
	private Integer id;
	@NotEmpty(message = "FirstName is mandatory")
	private String firstName;
	@NotEmpty(message = "LastName is mandatory")
	private String lastName;
	@NotNull(message = "Birthdate is mandatory")
	@DateTimeFormat(iso=ISO.DATE,pattern ="yyyy-MM-dd")
	private Date birthdate;
	@NotEmpty(message = "Gender is mandatory")
	private String gender;
	@NotEmpty(message = "City is mandatory")
	private String city;
	@NotEmpty(message = "Address is mandatory")
	private String address;
	@NotNull(message = "Postal code is mandatory")
	private Integer postalCode;
	@NotEmpty(message = "Phone Number is mandatory")
	private String phoneNumber;
	
	
	public PatientBean(	@NotEmpty String firstName, 	@NotEmpty String lastName, 	@NotEmpty Date birthdate, 	@NotEmpty String gender,	@NotEmpty String city, 	@NotEmpty String address,	@NotEmpty Integer postalCode,
			@NotEmpty	String phoneNumber){
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.gender = gender;
		this.city = city;
		this.address = address;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
	}
	
	
	public PatientBean(){
		super();
	}

	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}