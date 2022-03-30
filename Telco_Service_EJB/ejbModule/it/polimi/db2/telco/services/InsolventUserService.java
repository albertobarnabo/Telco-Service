package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.InsolventUser;

@Stateless
public class InsolventUserService {
	@PersistenceContext(unitName = "TelcoServiceEJB")
    private EntityManager em;
	
	public List<InsolventUser> getAllInsolvents(){
		return em.createNamedQuery("findAllInsolvents", InsolventUser.class).getResultList();
	}
}
