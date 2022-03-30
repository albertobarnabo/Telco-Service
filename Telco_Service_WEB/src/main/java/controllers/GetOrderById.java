package controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.db2.telco.entities.Order;
import it.polimi.db2.telco.exceptions.InvalidOrderException;
import it.polimi.db2.telco.services.OrderService;


@WebServlet("/GetOrderById")
public class GetOrderById extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name="it.polimi.db2.EJB.services/OrderService")
	private OrderService orderService;
	
    public GetOrderById() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String o = request.getParameter("orderId");
		
		int orderId = Integer.parseInt(o);
		
		Order order = new Order();
		
		try {
			order = orderService.getOrder(orderId);
		} catch (InvalidOrderException e) {
			e.printStackTrace();
		}
		
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(order);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
