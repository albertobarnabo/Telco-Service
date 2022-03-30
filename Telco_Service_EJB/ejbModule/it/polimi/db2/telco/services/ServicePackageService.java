package it.polimi.db2.telco.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import it.polimi.db2.telco.entities.Employee;
import it.polimi.db2.telco.entities.OptionalProduct;
import it.polimi.db2.telco.entities.Service;
import it.polimi.db2.telco.entities.ServicePackage;
import it.polimi.db2.telco.exceptions.InvalidOptionalProductException;
import it.polimi.db2.telco.exceptions.InvalidServiceException;
import it.polimi.db2.telco.exceptions.UserNotFoundException;

@Stateless
public class ServicePackageService {
	
	@PersistenceContext(unitName = "TelcoServiceEJB")
    private EntityManager em;
	
	public ServicePackageService () {}
	
	@SuppressWarnings("unchecked")
	public List<ServicePackage> findAll(){
		
		Query q = em.createQuery("SELECT s FROM ServicePackage s");
		
		return q.getResultList();
		
	}
	
	public int createServicePackage (String name, float fee12, float fee24, float fee36, int employeeId, List<Integer> serviceIds, List<Integer> optionalProductIds) throws InvalidServiceException, UserNotFoundException, InvalidOptionalProductException{
		
		List<Service> services = new ArrayList<>();
		List<OptionalProduct> optionalProducts = new ArrayList<>();
		
		
		//Find all the services corresponding to the serviceIds and adds them to the created ServicePackage
		Service service = new Service();
		for(int serviceId : serviceIds) {
			
			service = em.find(Service.class, serviceId);
			
			if(service == null)
				throw new InvalidServiceException("The service with serviceId" + serviceId + "doesn't exist");
			services.add(service);
		}
		
		//Finds the employee with the given employeeId
		Employee emp = new Employee();
		emp = em.find(Employee.class, employeeId);
		
		if(emp == null)
			throw new UserNotFoundException("Employee not found");
		
		//Finds the corresponding Optional Products
		OptionalProduct op = new OptionalProduct();
		if(optionalProductIds != null) {
			for(int optProd : optionalProductIds) {
				op = em.find(OptionalProduct.class, optProd);
				if(op == null) {
					throw new InvalidOptionalProductException("Optional product not found");
				}
				optionalProducts.add(op);
			}
		}
		
		//Populate the new Service package with the obtained values
		ServicePackage servicePackage = new ServicePackage();
		
		servicePackage.setName(name);
		servicePackage.setFee12(fee12);
		servicePackage.setFee24(fee24);
		servicePackage.setFee36(fee36);
		servicePackage.setEmployee(emp);
		servicePackage.setOptionalProducts(optionalProducts);
		servicePackage.setServices(services);
		
		em.persist(servicePackage);
		em.flush();
		
		return servicePackage.getPackageId();
	}
	
	public ServicePackage getPackageById (int id) {
		
		ServicePackage s = em.find(ServicePackage.class, id);
		
		return s;
		
	}
	
}
