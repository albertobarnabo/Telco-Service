package it.polimi.db2.telco.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "orders")
@NamedQuery(name = "Order.getOrdersByConsumerId", query = "SELECT o FROM Order o, Consumer c WHERE o.consumer = c and c.consumerId = ?1")
public class Order implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	
	@ManyToOne
	@JoinColumn(name = "ServicePackage")
	private ServicePackage servicePackage;
	
	@ManyToOne
	@JoinColumn(name = "ConsumerID")
	@JsonBackReference
	private Consumer consumer;
	
	private float totalCost;
	
	private LocalDateTime creationDate;
	
	private int validity;
	
	private int status;
	
	private LocalDateTime paymentDate;
	
	private LocalDate startSubscription;
	
	
	@ManyToMany
	@JoinTable(name = "chosen",
		joinColumns = @JoinColumn(name = "OrderID"),
		inverseJoinColumns = @JoinColumn(name = "OptionalProductID")
	)
	private List<OptionalProduct> optionalProducts;

	//Constructor
	public Order() {}
	
	//Getters and Setters
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public ServicePackage getServicePackage() {
		return servicePackage;
	}

	public void setServicePackage(ServicePackage servicePackage) {
		this.servicePackage = servicePackage;
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
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

	public List<OptionalProduct> getOptionalProducts() {
		return optionalProducts;
	}

	public void setOptionalProducts(List<OptionalProduct> optionalProducts) {
		this.optionalProducts = optionalProducts;
	}

	public int getValidity() {
		return validity;
	}

	public void setValidity(int validity) {
		this.validity = validity;
	}

	

	
	
}
