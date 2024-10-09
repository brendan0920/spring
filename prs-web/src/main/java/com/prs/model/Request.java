package com.prs.model;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class Request {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "UserId")
	private User user;
	private String requestNumber;
	private String description;
	private String justification;
	private LocalDate dateNeeded;
	private String deliveryMode;
	private String status;
	private double total;
	private Date submittedDate;
	private String reasonForRejection;
	
	 private String maxReqNbr;
	 
	 public String getMaxReqNbr() {
        return maxReqNbr;
    }

    public void setMaxReqNbr(String maxReqNbr) {
        this.maxReqNbr = maxReqNbr;
    }
	    
	    
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getRequestNumber() {
		return requestNumber;
	}
	
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getJustification() {
		return justification;
	}
	
	public void setJustification(String justification) {
		this.justification = justification;
	}
	
	public LocalDate getDateNeeded() {
		return dateNeeded;
	}
	
	public void setDateNeeded(LocalDate dateNeeded) {
		this.dateNeeded = dateNeeded;
	}
	
	public String getDeliveryMode() {
		return deliveryMode;
	}
	
	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public double getTotal() {
		return total;
	}
	
	public void setTotal(double total) {
		this.total = total;
	}
	
	public Date getSubmittedDate() {
		return submittedDate;
	}
	
	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}
	
	public String getReasonForRejection() {
		return reasonForRejection;
	}
	
	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}
	
	
}
