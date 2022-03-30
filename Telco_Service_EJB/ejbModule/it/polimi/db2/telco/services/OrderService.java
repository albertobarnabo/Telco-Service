package it.polimi.db2.telco.services;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import it.polimi.db2.telco.entities.Consumer;
import it.polimi.db2.telco.entities.OptionalProduct;
import it.polimi.db2.telco.entities.Order;
import it.polimi.db2.telco.entities.ServicePackage;
import it.polimi.db2.telco.exceptions.InvalidOptionalProductException;
import it.polimi.db2.telco.exceptions.InvalidOrderException;
import it.polimi.db2.telco.exceptions.NotLoggedUserException;
import it.polimi.db2.telco.exceptions.ServicePackageNotAvailableException;

@Stateless
public class OrderService {

	@PersistenceContext(unitName = "TelcoServiceEJB")
    private EntityManager em;
	
	public OrderService() {}
	
	
	
	public int createOrder (LocalDate startSub, int validity, int userId, int servicePackageId, List<Integer> optProdIds) throws NotLoggedUserException, ServicePackageNotAvailableException, InvalidOptionalProductException {
		
		List<OptionalProduct> optionalProducts = new ArrayList<>();
		OptionalProduct op = new OptionalProduct();
		float totalCost = 0;
		
		//Control if the consumer that wants to create the order is actually logged in
		Consumer consumer = new Consumer();
		consumer = em.find(Consumer.class, userId);
		
		if(consumer == null) {
			throw new NotLoggedUserException("The user is not logged");
		}
		
		//Control if the requested service package exists
		ServicePackage servPkg = new ServicePackage();
		servPkg = em.find(ServicePackage.class, servicePackageId);
		
		if(servPkg == null) {
			throw new ServicePackageNotAvailableException("The selected package" + servicePackageId + "is not available or doesn't exist");
		}
		
		
		//Create a new Order
		Order order = new Order();
		
		order.setServicePackage(servPkg);
		if(optProdIds != null) {
			
			for (int prodId : optProdIds) {
				
				op = em.find(OptionalProduct.class, prodId); //Find the corresponding optional product with its ID
				if(op == null) {
					throw new InvalidOptionalProductException("Optional product invalid or not available");
				}
				optionalProducts.add(op);
				totalCost += op.getFee();
			}
			
			//Once all the optional products have been found, we can add them to the order!
			order.setOptionalProducts(optionalProducts);
		}
		
		if(validity == 12)
			totalCost += servPkg.getFee12();
		else if(validity == 24)
			totalCost += servPkg.getFee24();
		else
			totalCost += servPkg.getFee36();
		
		
		order.setConsumer(consumer);
		order.setStartSubscription(startSub);
		order.setValidity(validity);	
		order.setTotalCost(totalCost);
		
		em.persist(order);
		em.flush();
		return order.getOrderId();
	
	}
	
	public int setStatusAsPaid (int orderId) throws InvalidOrderException {
		
		if(orderId == 0)
			throw new InvalidOrderException("Order Id cannot be 0");
		
		Order order = new Order();
		order = em.find(Order.class, orderId);
		
		if(order == null)
			throw new InvalidOrderException("Order id number" + orderId + "doesn't exist");
		
		order.setStatus(2); // 2 = PAYED
		return order.getStatus();
	}
	
	public int setStatusAsRejected(int orderId) throws InvalidOrderException{
		
		if(orderId == 0)
			throw new InvalidOrderException("Order Id cannot be 0");
		
		Order order = new Order();
		order = em.find(Order.class, orderId);
		
		if(order == null)
			throw new InvalidOrderException("Order id number" + orderId + "doesn't exist");
		
		order.setStatus(1); // 1 = PAYMENT REJECTED
		return order.getStatus();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Order> getUserOrders (int consumerId) throws NotLoggedUserException{
		
		Consumer consumer = new Consumer();
		consumer = em.find(Consumer.class, consumerId);
		if(consumer == null) {
			throw new NotLoggedUserException("UserId " + consumerId + " not found");
		}
		
		Query query = em.createQuery("SELECT o FROM Order o, Consumer c WHERE o.consumer=c and c.consumerId=?1");
		
		query.setParameter(1, consumerId);
		
		return query.getResultList();
	}
	
	public Order getOrder(int orderId) throws InvalidOrderException{
		
		Order o = new Order();
		o = em.find(Order.class, orderId);
		
		if(o == null) {
			throw new InvalidOrderException("OrderId invalid or not found");
		}
		else
			return o;	
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Order> getUserRejectedOrders (int userId) {
		
		Query query = em.createQuery("SELECT o FROM Order o, Consumer c WHERE o.consumer=c AND o.status=?1 and c.consumerId=?2");
		
		query.setParameter(1, 1);
		query.setParameter(2, userId);
		
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Order> getUserUnpaidOrders (int userId) {
		
		Query query = em.createQuery("SELECT o FROM Order o, Consumer c WHERE o.consumer=c AND o.status=?1 and c.consumerId=?2");
		
		query.setParameter(1, 0);
		query.setParameter(2, userId);
		
		return query.getResultList();
	}
	

	@SuppressWarnings("unchecked")
	public List<Order> getUserPayedOrders (int userId) {
		
		Query query = em.createQuery("SELECT o FROM Order o, Consumer c WHERE o.consumer=c AND o.status=?1 and c.consumerId=?2");
		
		query.setParameter(1, 2);
		query.setParameter(2, userId);
		
		return query.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Order> getUserOrdersCart (int userId) {
		
		Query query = em.createQuery("SELECT o FROM Order o, Consumer c WHERE o.consumer=c AND o.status=?1 and c.consumerId=?2");
		
		query.setParameter(1, 0);
		query.setParameter(2, userId);
		
		return query.getResultList();
	}
	
	
}
