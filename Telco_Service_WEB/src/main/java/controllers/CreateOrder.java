package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.polimi.db2.telco.entities.Consumer;
import it.polimi.db2.telco.exceptions.InvalidOptionalProductException;
import it.polimi.db2.telco.exceptions.NotLoggedUserException;
import it.polimi.db2.telco.exceptions.ServicePackageNotAvailableException;
import it.polimi.db2.telco.services.OrderService;

@WebServlet("/CreateOrder")
@MultipartConfig
public class CreateOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB(name="it.polimi.db2.EJB.services/OrderService")
	private OrderService orderService;
	
    public CreateOrder() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession session = request.getSession();
		int orderId;
		
		Consumer cons = (Consumer) session.getAttribute("consumer");
		if (cons == null) {
			String loginpath = request.getServletContext().getContextPath() + "/loginpage.html";
			response.sendRedirect(loginpath);
		}
		int consumerId = cons.getConsumerId();
		
		String val = request.getParameter("validity");
		String d = request.getParameter("startDate");
		String optProds[] = request.getParameterValues("selectedOptionalProducts");
		String pkg = request.getParameter("packageId");
		
		if(d == null || d.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        	response.getWriter().println("Please Specify a starting subscription date");
			return;
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(d, formatter);
		
		if(val == null  || pkg == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        	response.getWriter().println("One or more of the required parameters is missing!");
			return;
		}
		
		
		
		int validity = 0;
		int packageId = 0;
		
		try {
			packageId = Integer.parseInt(pkg);
			validity = Integer.parseInt(val);
		}catch(NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        	response.getWriter().println("One ore more parameter are wrong");
			return;
		}
		
		List<Integer> optionalProducts = null;
		if(optProds != null) {
			optionalProducts = new ArrayList<Integer>();
			for(int i=0; i < optProds.length; i++) {
				optionalProducts.add(Integer.parseInt(optProds[i]));
			}
		}
		
		try {
			orderId =  orderService.createOrder(date, validity, consumerId, packageId, optionalProducts);
			response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(orderId);
		} catch (NotLoggedUserException | ServicePackageNotAvailableException | InvalidOptionalProductException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        	response.getWriter().println("Something went wrong when creating the order");
			return;
		}
		
	}

}
