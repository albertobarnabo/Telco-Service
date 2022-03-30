package it.polimi.db2.telco.services;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import it.polimi.db2.telco.entities.Consumer;
import it.polimi.db2.telco.entities.Order;
import it.polimi.db2.telco.exceptions.CredentialsException;
import it.polimi.db2.telco.exceptions.UserNotFoundException;

import javax.persistence.NonUniqueResultException;
import java.util.List;

@Stateless
public class ConsumerService {
    @PersistenceContext(unitName = "TelcoServiceEJB")
    private EntityManager em;

    public ConsumerService() {
    }

    public Consumer findConsumerByUsername(String username) {
    	return (Consumer) em.createNamedQuery("Consumer.findByUsername, Consumer.class").setParameter(1, username).getSingleResult();
    }
    
    public boolean doesUsernameExist(String username) {
    	
    	Query query = em.createQuery("SELECT count (c) from Consumer c WHERE c.username=?1");
    	
    	query.setParameter(1, username);
    	
    	Long i = (Long) query.getSingleResult();
    	return((i.equals(0L)) ? false : true);
    	
    }
    
    public Consumer checkCredentials(String username, String password) throws CredentialsException, NonUniqueResultException {
    	
        List<Consumer> cList = null;
        try {
            cList = em.createNamedQuery("Consumer.checkCredentials", Consumer.class).setParameter(1, username).setParameter(2, password)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentals");
        }
        if (cList.isEmpty())
            return null;
        else if (cList.size() == 1)
            return cList.get(0);
        throw new NonUniqueResultException("More than one user registered with same credentials");

    }
    
    public int createConsumer(String username, String password, String email){
    	
    	Consumer c = new Consumer();
    	
    	c.setEmail(email);
    	c.setUsername(username);
    	c.setPassword(password);
    	
    	em.persist(c);
    	em.flush();
    	return c.getConsumerId();
    	
    }
    
    public List<Order> getConsumerOrders (int consumerId) throws UserNotFoundException {
    	
    	Consumer consumer = new Consumer();
    	
    	consumer = em.find(Consumer.class, consumerId);
    	
    	if(consumer == null) {
    		throw new UserNotFoundException("Consumer" + consumerId + "not found");
    	}
    	
    	List<Order> ordersList = em.createNamedQuery("Order.getOrdersByConsumerId", Order.class).getResultList();
    	
    	return ordersList;
    }
    
    public boolean getInsolvent (int consumerId) throws UserNotFoundException{
    	
    	Consumer c = em.find(Consumer.class, consumerId);
    	
    	if(c == null)
    		throw new UserNotFoundException("Consumer not found");
    	
    	Query query = em.createQuery("SELECT c.insolvent from Consumer c where c.consumerId=?1");
    	
    	query.setParameter(1, consumerId);
    	return (boolean) query.getSingleResult();
    	
    }
    
    @SuppressWarnings("unchecked")
	public List<Consumer> getAllInsovents(){
    	
    	Query query = em.createQuery("SELECT c.email, c.username FROM Consumer c WHERE c.insolvent = 1");
    	
    	return query.getResultList();
    	
    }
    
    public Consumer findById (int consumerId) {
    	
    	Consumer c = em.find(Consumer.class, consumerId);
    	return c;
    	
    }
    
    public void incrementRejPayments(int consumerId) throws UserNotFoundException {
    	
    	Consumer c = new Consumer();
    	c = em.find(Consumer.class, consumerId);
    	
    	if (c==null)
    		throw new UserNotFoundException("The user was not found!");
    	
    	int i;
    	i = c.getNumRejPay();
    	i += 1;
    	c.setNumRejPay(i);
    	
    	return;
    	
    }
    
    
}
