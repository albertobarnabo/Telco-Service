package it.polimi.db2.telco.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "product_report")
@NamedQuery(name = "getBest", query = "SELECT p FROM OptionalProductReport p WHERE p.totalSales = (SELECT MAX(p1.totalSales) FROM OptionalProductReport p1)")
public class OptionalProductReport implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "OptionalProduct")
	private int optionalProductId;
	
	private String name;
	private float totalSales;
	
	
	public OptionalProductReport() {}
	
	public int getOptionalProductId() {
		return optionalProductId;
	}
	public void setOptionalProductId(int optionalProductId) {
		this.optionalProductId = optionalProductId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(float totalSales) {
		this.totalSales = totalSales;
	}
	
}
