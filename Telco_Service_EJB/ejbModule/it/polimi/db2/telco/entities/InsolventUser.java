package it.polimi.db2.telco.entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "insolvent_user")
@NamedQuery(name = "findAllInsolvents", query = "SELECT i FROM InsolventUser i")
public class InsolventUser implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ConsumerID")
	private int consumerId;
	
	private String email;
	private String username;
	
	
	public int getConsumerId() {
		return consumerId;
	}



	public void setConsumerId(int consumerId) {
		this.consumerId = consumerId;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public InsolventUser() {
		super();
	}
   
}
