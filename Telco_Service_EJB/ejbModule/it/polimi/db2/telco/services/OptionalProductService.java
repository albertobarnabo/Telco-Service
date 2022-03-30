package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import it.polimi.db2.telco.entities.OptionalProduct;
import it.polimi.db2.telco.exceptions.DuplicatedOptionalProductException;

@Stateless
public class OptionalProductService {

	@PersistenceContext(unitName = "TelcoServiceEJB")
	private EntityManager em;
	
	public OptionalProductService() {}
	

	@SuppressWarnings("unchecked")
	public List<OptionalProduct> getAllOptionalProducts(){
		
		Query query = em.createQuery("SELECT o FROM OptionalProduct o", OptionalProduct.class);
		
		return query.getResultList();
	}
	
	//TODO: Can we actually pass a optional product?
	public void createOptionalProduct (OptionalProduct o) throws DuplicatedOptionalProductException {
		
		Query query = em.createQuery("SELECT op FROM OptionalProduct op WHERE op.name=?1 AND op.fee=?2", OptionalProduct.class);
		
		query.setParameter(1, o.getName());
		query.setParameter(2, o.getFee());
		
		if(!query.getResultList().isEmpty()) {
			throw new DuplicatedOptionalProductException("Optional Product" + o.getName() + "already exists");
		}
		
		em.persist(o);
		em.flush();
		return;
		
	}
	
}
