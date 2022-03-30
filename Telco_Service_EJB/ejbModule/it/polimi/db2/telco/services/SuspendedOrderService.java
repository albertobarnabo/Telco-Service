package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.SuspendedOrder;

@Stateless
public class SuspendedOrderService {
	@PersistenceContext(unitName = "TelcoServiceEJB")
    private EntityManager em;
	
	public SuspendedOrderService() {}
	
	@SuppressWarnings("unchecked")
	public List<SuspendedOrder> getAll(){
		return em.createQuery("SELECT so FROM SuspendedOrder so").getResultList();
	}
}

