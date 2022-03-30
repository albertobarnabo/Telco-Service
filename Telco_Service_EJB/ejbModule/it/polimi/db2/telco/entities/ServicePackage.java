package it.polimi.db2.telco.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "service_package")
public class ServicePackage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PackageID")
	private int packageId;
	
	private String name;
	private float fee12;
	private float fee24;
	private float fee36;
	
	@ManyToOne
	@JoinColumn(name = "EmployeeID")
	private Employee employee;
	
	@ManyToMany
	@JoinTable(name = "comprises",
			joinColumns = @JoinColumn(name = "PackageID"),
			inverseJoinColumns = @JoinColumn(name = "ServiceID")
	)
	
	private List<Service> services;
	
	@ManyToMany
	@JoinTable(name = "inclusion",
			joinColumns = @JoinColumn(name = "PackageID"),
			inverseJoinColumns = @JoinColumn(name = "OptionalProductID")
	)

	private List<OptionalProduct> optionalProducts;
	
	public ServicePackage() {}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getFee12() {
		return fee12;
	}

	public void setFee12(float fee12) {
		this.fee12 = fee12;
	}

	public float getFee24() {
		return fee24;
	}

	public void setFee24(float fee24) {
		this.fee24 = fee24;
	}

	public float getFee36() {
		return fee36;
	}

	public void setFee36(float fee36) {
		this.fee36 = fee36;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public List<OptionalProduct> getOptionalProducts() {
		return optionalProducts;
	}

	public void setOptionalProducts(List<OptionalProduct> optionalProducts) {
		this.optionalProducts = optionalProducts;
	}
	
	
	
}
