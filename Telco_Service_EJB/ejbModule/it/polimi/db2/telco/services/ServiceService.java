package it.polimi.db2.telco.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import it.polimi.db2.telco.entities.Service;
import it.polimi.db2.telco.exceptions.InvalidServiceException;

@Stateless
public class ServiceService {
	
	@PersistenceContext(unitName = "TelcoServiceEJB")
    private EntityManager em;
	
	public ServiceService () {}
	

	
	@SuppressWarnings("unchecked")
	public void createService (Service service) throws InvalidServiceException {
		
		List<Service> s = new ArrayList<>();
		
		int minutes, sms, gigabytes;
		float minutesFee, smsFee, gigabytesFee;
		
		minutes = service.getMinutes();
		sms = service.getSms();
		gigabytes = service.getGigabytes();
		
		minutesFee = service.getMinutesFee();
		smsFee = service.getSmsFee();
		gigabytesFee = service.getGigabytesFee();
		
		Query query = em.createQuery("SELECT s FROM Service s");
		
		switch(service.getType()) {
		
		case("fixed_internet"): //Since the controls for mobile and fixed internet is the same, we just handle to the second case 
		
		case("mobile_internet"):
			
			query = em.createQuery("SELECT s from Service s WHERE (s.type=?1 AND s.gigabytes=?2 AND s.gigabytesFee=?3 AND s.minutes=0 AND s.sms=0 AND s.minutesFee=0 AND s.smsFee=0)");
			query.setParameter(1, service.getType());
			query.setParameter(2, gigabytes);
			query.setParameter(3, gigabytesFee);
			break;
		
		case("mobile_phone"):
			
			query = em.createQuery("SELECT s from Service s WHERE (s.type=?1 AND s.gigabytes=0 AND s.gigabytesFee=0 AND s.minutes=?2 AND s.sms=?3 AND s.minutesFee=?4 AND s.smsFee=?5)");
			query.setParameter(1, service.getType());
			query.setParameter(2, minutes);
			query.setParameter(3, sms);
			query.setParameter(4, minutesFee);
			query.setParameter(5, smsFee);
			break;
			
		case("fixed_phone"):
			
			query = em.createQuery("SELECT s from Service s WHERE (s.type=?1 AND s.gigabytes=0 AND s.gigabytesFee=0 AND s.minutes=?2 AND s.sms=0 AND s.minutesFee=?3 AND s.smsFee=0)");
			query.setParameter(1, service.getType());
			query.setParameter(2, minutes);
			query.setParameter(3, minutesFee);
			break;
			
		}
		
		s = query.getResultList();
		if(! s.isEmpty()) {
			throw new InvalidServiceException("The same service already exists!");
		}
		
		em.persist(service);
		em.flush();
		return;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Service> findMobilePhone(){
		
		Query query = em.createQuery("SELECT s FROM Service s WHERE s.type = ?1");
		query.setParameter(1, "mobile phone");
		
		return query.getResultList();	
	}
	
	@SuppressWarnings("unchecked")
	public List<Service> findFixedPhone(){
		
		Query query = em.createQuery("SELECT s FROM Service s WHERE s.type = ?1");
		query.setParameter(1, "fixed phone");
		
		return query.getResultList();	
	}
	
	@SuppressWarnings("unchecked")
	public List<Service> findFixedInternet(){
		
		Query query = em.createQuery("SELECT s FROM Service s WHERE s.type = ?1");
		query.setParameter(1, "fixed internet");
		
		return query.getResultList();	
	}
	
	@SuppressWarnings("unchecked")
	public List<Service> findMobileInternet(){
		
		Query query = em.createQuery("SELECT s FROM Service s WHERE s.type = ?1");
		query.setParameter(1, "mobile internet");
		
		return query.getResultList();	
	}
	
	@SuppressWarnings("unchecked")
	public List<Service> getAllServices(){
		
		Query query = em.createQuery("SELECT s FROM Service s");
		
		return query.getResultList();	
	}
	
}
