package it.polimi.db2.telco.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="package_report")
@NamedQuery(name = "getAllReports", query = "SELECT p FROM PackageReport p")
@NamedQuery(name = "getPackageReport", query = "SELECT p FROM PackageReport p WHERE p.packageId = ?1")

public class PackageReport implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "PackageID")
	private int packageId;
	
	private int totalPurchases;
	
	@Column(name = "Purchases_12m")
	private int purchases12M;
	
	@Column(name = "Purchases_24m")
	private int purchases24M;
	
	@Column(name = "Purchases_36m")
	private int purchases36M;
	
	private float totalRevenue;
	private float totalRevenueWithOptionals;
	private int numberOfOptionalProducts;
	
	
	public PackageReport() {}
	
	public int getPackageId() {
		return packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	public int getTotalPurchases() {
		return totalPurchases;
	}
	public void setTotalPurchases(int totalPurchases) {
		this.totalPurchases = totalPurchases;
	}
	public int getPurchases12M() {
		return purchases12M;
	}
	public void setPurchases12M(int purchases12m) {
		purchases12M = purchases12m;
	}
	public int getPurchases24M() {
		return purchases24M;
	}
	public void setPurchases24M(int purchases24m) {
		purchases24M = purchases24m;
	}
	public int getPurchases36M() {
		return purchases36M;
	}
	public void setPurchases36M(int purchases36m) {
		purchases36M = purchases36m;
	}
	public float getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(float totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	public float getTotalRevenueWithOptionals() {
		return totalRevenueWithOptionals;
	}
	public void setTotalRevenueWithOptionals(float totalRevenueWithOptionals) {
		this.totalRevenueWithOptionals = totalRevenueWithOptionals;
	}
	public int getNumberOfOptionalProducts() {
		return numberOfOptionalProducts;
	}
	public void setNumberOfOptionalProducts(int numberOfOptionalProducts) {
		this.numberOfOptionalProducts = numberOfOptionalProducts;
	}
	public float getAverageNumberOfOptionalProducts() {
		return (float)numberOfOptionalProducts / (float)totalPurchases;
	}
	
	
	
}
