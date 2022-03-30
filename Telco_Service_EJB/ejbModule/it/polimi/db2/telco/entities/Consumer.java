package it.polimi.db2.telco.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "consumer")
@NamedQuery(name = "Consumer.checkCredentials", query = "SELECT c FROM Consumer c  WHERE c.username = ?1 and c.password = ?2")
@NamedQuery(name = "Consumer.findByUsername", query = "SELECT c FROM Consumer c WHERE c.username = ?1")
public class Consumer implements Serializable {
	
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int consumerId;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String email;
    
    private int numRejPay;
    private int lastRejOrder;
    private int insolvent;
    
    
    //Getters and Setters
	public int getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(int consumerId) {
		this.consumerId = consumerId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getNumRejPay() {
		return numRejPay;
	}

	public void setNumRejPay(int numRejPay) {
		this.numRejPay = numRejPay;
	}

	public int getLastRejOrder() {
		return lastRejOrder;
	}

	public void setLastRejOrder(int lastRejOrder) {
		this.lastRejOrder = lastRejOrder;
	}

	public int isInsolvent() {
		return insolvent;
	}

	public void setInsolvent(int insolvent) {
		this.insolvent = insolvent;
	}

	
	public Consumer() {
		
	}
	
	public Consumer(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}
    
    
  
    
    
    

}
