package controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.polimi.db2.telco.entities.Consumer;
import it.polimi.db2.telco.exceptions.InvalidOrderException;
import it.polimi.db2.telco.exceptions.UserNotFoundException;
import it.polimi.db2.telco.services.ConsumerService;
import it.polimi.db2.telco.services.OrderService;


@WebServlet("/PerformPayment")
public class PerformPayment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name="it.polimi.db2.EJB.services/OrderService")
	private OrderService orderService; 
	
	@EJB(name="it.polimi.db2.EJB.services/ConsumerService")
	private ConsumerService consumerService; 
    
    public PerformPayment() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String o = request.getParameter("orderId");
		int orderId = Integer.parseInt(o);
		String result = null;
		
		HttpSession session = request.getSession();
		
		Consumer c = (Consumer) session.getAttribute("consumer");
		
		if (c == null) {
			String loginpath = request.getServletContext().getContextPath() + "/loginpage.html";
			response.sendRedirect(loginpath);
		}
		
		int consumerId = c.getConsumerId();
		
		double i = Math.random();
		
		if(i<0) {
			result = "accepted";
			try {
				orderService.setStatusAsPaid(orderId);
			} catch (InvalidOrderException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Order");
	            return;
			}
			
			
		}
		else {
			result = "denied";
			try {
				orderService.setStatusAsRejected(orderId);
			} catch (InvalidOrderException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Order");
	            return;
			}

			try {
				consumerService.incrementRejPayments(consumerId);
			} catch (UserNotFoundException e) {
				e.printStackTrace();
			}
			
			
			
			
		}
		System.out.println(result);
		response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(result);
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
