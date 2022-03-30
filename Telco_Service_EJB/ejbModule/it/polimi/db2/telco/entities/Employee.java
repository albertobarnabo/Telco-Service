package it.polimi.db2.telco.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


import java.io.Serializable;


@Entity
@Table(name = "employee")
@NamedQuery(name = "Employee.checkCredentials", query = "SELECT e FROM Employee e  WHERE e.username = ?1 and e.password = ?2")
@NamedQuery(name = "Employee.findByUsername", query = "SELECT e FROM Employee e WHERE e.username = ?1")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;
    
    @Column(nullable = false)
    private String username;
    
    private String password;
    private String email;
    
    
    public Employee() {}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
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


}
