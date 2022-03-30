package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.OptionalProductReport;

@Stateless
public class OptionalProductReportService {
	@PersistenceContext(unitName = "TelcoServiceEJB")
    private EntityManager em;
	
	public OptionalProductReportService() {}
	
	public List<OptionalProductReport> getBestValue(){
		return em.createNamedQuery("getBest", OptionalProductReport.class).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<OptionalProductReport> getAll() {
		return em.createQuery("SELECT o FROM OptionalProductReport o").getResultList();
	}
}
