package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import it.polimi.db2.telco.entities.AuditingTable;

@Stateless
public class AuditingTableService {
	
	@PersistenceContext(unitName = "TelcoServiceEJB")
	private EntityManager em;
	
	public AuditingTableService() {}
	
	@SuppressWarnings("unchecked")
	public List<AuditingTable> getAuditingTable() {
		
		Query query = em.createQuery("SELECT a from AudtingTable a");
		
		return query.getResultList();
		
	}
}
