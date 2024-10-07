package com.movie.model;

import java.time.LocalDate;

public class Actor {

	private int id;
	private String fname;	
	private String lname;
	private String gender;
	private LocalDate birthDate;
	
//	public Actor(int id, String fname, String lname, String gender, LocalDate birthDate) {
//		super();
//		this.id = id;
//		this.fname = fname;
//		this.lname = lname;
//		this.gender = gender;
//		this.birthDate = birthDate;
//	}
//
//	public Actor() {
//		super();
//	}
//	
//	public Actor(String fname, String lname, String gender, LocalDate birthDate) {
//		super();
//		this.fname = fname;
//		this.lname = lname;
//		this.gender = gender;
//		this.birthDate = birthDate;
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "Actor [id = " + id + ", name = " + fname + " " + lname + ", gender = " + gender + ", birthDate = "
				+ birthDate + "]";
	}	
	
	
}
