package com.mediscreen.patient.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="patient")
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty(message = "not nullable")
	private String firstName;
	private String lastName;
	@DateTimeFormat(iso=ISO.DATE,pattern ="yyyy-MM-dd")
	private Date birthdate;
	private String gender;
	private String city;
	private String address;
	private Integer postalCode;
	private String phoneNumber;
	
	
	public Patient(String firstName, String lastName, Date birthdate, String gender,String city, String address,Integer postalCode,
			String phoneNumber)
	{
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
	
	public Patient(Integer id,String firstName, String lastName, Date birthdate, String gender,String city, String address,Integer postalCode,
			String phoneNumber)
	{
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.gender = gender;
		this.city = city;
		this.address = address;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
	}
	public Patient(){
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
