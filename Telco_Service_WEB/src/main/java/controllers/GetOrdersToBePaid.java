package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.db2.telco.entities.Consumer;
import it.polimi.db2.telco.entities.Order;
import it.polimi.db2.telco.services.OrderService;


@WebServlet("/GetOrdersToBePaid")
public class GetOrdersToBePaid extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name="it.polimi.db2.EJB.services/OrderService")
	private OrderService orderService;   
    
    public GetOrdersToBePaid() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		Consumer c = (Consumer) session.getAttribute("consumer");
		
		if (c == null) {
			String loginpath = request.getServletContext().getContextPath() + "/loginpage.html";
			response.sendRedirect(loginpath);
		}
		
		int consumerId = c.getConsumerId();
		
		List<Order> unpaidOrders = new ArrayList<Order>();
		List<Order> rejectedOrders = new ArrayList<Order>();
		
		rejectedOrders = orderService.getUserRejectedOrders(consumerId);
		unpaidOrders = orderService.getUserUnpaidOrders(consumerId);
		
		unpaidOrders.addAll(rejectedOrders);
		
		//Create the Json and send it to the client
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(unpaidOrders);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
