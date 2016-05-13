package com.hmitchang.memberservice.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Member {
	
	private long id;
	private String firstName; 
	private String lastName;
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dateOfBirth;
	private int postalCode;
	
	public Member(){
		this.id = 0;
		this.firstName="";
		this.lastName="";
		this.dateOfBirth = null;
		this.postalCode=0;
	}
	
	public Member(long id, String firstName,String lastName, Date dateOfBirth, int postalCode){
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.postalCode = postalCode;
	}
	
	public long getId(){
		return this.id;
	}
	
	public void setId(long id){
		this.id = id;
	}
		
	public String getFirstName(){
		return this.firstName;
	}
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public String getLastName(){
		return this.lastName;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public Date getDateOfBirth(){
		return this.dateOfBirth;
	}
	
	public void setDateOfBirth(Date dateOfBirth){
		this.dateOfBirth = dateOfBirth;
	}
	
	public int getPostalCode(){
		return this.postalCode;
	}
	
	public void setPostalCode(int postalCode){
		this.postalCode = postalCode;
	}

}
