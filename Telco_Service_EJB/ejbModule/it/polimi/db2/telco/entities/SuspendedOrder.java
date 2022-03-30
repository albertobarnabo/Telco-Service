package it.polimi.db2.telco.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "suspended_order")
public class SuspendedOrder implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="OrderID")
	private int orderId;
	
	@Column(name="ServicePackage")
	private int servicePackage;
	
	@Column(name="ConsumerID")
	private int consumerId;
	
	private float totalCost;
	private LocalDateTime creationDate;
	private int validity;
	private int status;
	private LocalDateTime paymentDate;
	private LocalDate startSubscription;
	
	
	public int getOrderId() {
		return orderId;
	}


	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public int getServicePackage() {
		return servicePackage;
	}


	public void setServicePackage(int servicePackage) {
		this.servicePackage = servicePackage;
	}


	public int getConsumerId() {
		return consumerId;
	}


	public void setConsumerId(int consumerId) {
		this.consumerId = consumerId;
	}


	public float getTotalCost() {
		return totalCost;
	}


	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}


	public LocalDateTime getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}


	public int getValidity() {
		return validity;
	}


	public void setValidity(int validity) {
		this.validity = validity;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}


	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}


	public LocalDate getStartSubscription() {
		return startSubscription;
	}


	public void setStartSubscription(LocalDate startSubscription) {
		this.startSubscription = startSubscription;
	}


	public SuspendedOrder() {
		super();
	}
   
}
