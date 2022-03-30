package it.polimi.db2.telco.services;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.telco.entities.Employee;
import it.polimi.db2.telco.exceptions.AlreadyRegisteredUserException;
import it.polimi.db2.telco.exceptions.CredentialsException;
import it.polimi.db2.telco.exceptions.EmptyCredentialsException;

import javax.persistence.NonUniqueResultException;
import java.util.List;

@Stateless
public class EmployeeService {
    @PersistenceContext(unitName = "TelcoServiceEJB")
    private EntityManager em;

    public EmployeeService() {
    }

    public Employee checkCredentials(String username, String password) throws CredentialsException, NonUniqueResultException {
        List<Employee> eList = null;
        try {
            eList = em.createNamedQuery("Employee.checkCredentials", Employee.class).setParameter(1, username).setParameter(2, password)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentals");
        }
        if (eList.isEmpty())
            return null;
        else if (eList.size() == 1)
            return eList.get(0);
        throw new NonUniqueResultException("More than one user registered with same credentials");

    }
    
    public boolean doesUsernameExists(String username) {
    	
		Employee employee = em.find(Employee.class, username);
		    	
		    	if (employee != null)
		    			return true;
		    	else 
		    		return false;
    }
    
    public Employee findEmployeeByUsername (String username) {
    	
    	return em.createNamedQuery("Employee.findByUsername", Employee.class).setParameter(1, username).getSingleResult();
    }
    
    public int createEmployee(String username, String email, String password) throws AlreadyRegisteredUserException, EmptyCredentialsException{
    	
    	if(doesUsernameExists(username)) {
    		throw new AlreadyRegisteredUserException("An Employee with this username already exists!");
    	}
    	
    	if(username.isEmpty() || email.isEmpty() || password.isEmpty() || username == null || email == null || password == null) {
    		throw new EmptyCredentialsException("One or more fields are empty, cannot proceeed to registration");
    	}
    	
    	Employee e = new Employee();
    	
    	e.setEmail(email);
    	e.setUsername(username);
    	e.setPassword(password);
    	
    	em.persist(e);
    	em.flush();
    	return e.getEmployeeId();
    	
    }
}
