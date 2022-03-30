package it.polimi.db2.telco.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "auditing_table")
public class AuditingTable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "UserID")
	private int userId;
	
	private String username;
	private Float amount;
	
	@Temporal(TemporalType.DATE)
	private Date lastRejection;
	
	public AuditingTable() {}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Date getLastRejection() {
		return lastRejection;
	}

	public void setLastRejection(Date lastRejection) {
		this.lastRejection = lastRejection;
	}

	
	
	

}
