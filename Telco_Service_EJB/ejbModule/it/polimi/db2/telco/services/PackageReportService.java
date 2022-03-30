package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.PackageReport;
import it.polimi.db2.telco.exceptions.MissingReportException;

@Stateless
public class PackageReportService {
	
	@PersistenceContext(unitName = "TelcoServiceEJB")
    private EntityManager em;
	
	public PackageReportService() {}
	
	public List<PackageReport> getAll(){
		return em.createNamedQuery("getAllReports", PackageReport.class).getResultList();
	}
	
	
	public PackageReport getReportByPackage(int packageId) throws MissingReportException {
		
		PackageReport p = new PackageReport();
		
		p = em.createNamedQuery("getPackageReport", PackageReport.class).setParameter(1, packageId).getSingleResult();
		
		if(p == null) {
			throw new MissingReportException("No info about package number "+packageId+" are available");
		}
		
		return p;
		
	}
}
