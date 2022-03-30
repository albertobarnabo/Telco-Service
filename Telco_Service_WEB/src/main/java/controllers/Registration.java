package controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.db2.telco.entities.Consumer;
import it.polimi.db2.telco.services.ConsumerService;



@WebServlet(name = "Registration", value = "/Registration")
@MultipartConfig

public class Registration extends HttpServlet {
	
	@EJB(name = "it.polimi.db2.EJB.services/ConsumerService")
	private ConsumerService consumerService;
	
	private static final long serialVersionUID = 1L;
	
	int consumerId;

    public Registration() {
        super();
    }
    
    public void init() throws ServletException {
        
    }	
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		doPost(request, response);
    }
    
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String usrn = null;
        String pwd = null;
        String email = null;
        
        try {
            usrn = request.getParameter("username");
            pwd = request.getParameter("password");
            email = request.getParameter("email");
            
            

            if (usrn == null || pwd == null || email == null || usrn.isEmpty() || pwd.isEmpty() || email.isEmpty()) {
            	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    			response.getWriter().println("Credentials must be not null");
    			return;
            } 
            
        	} catch (Exception e) {
                // for debugging only e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
                return;
        }
            
            Consumer consumer = null;
            
          
        	  
           if(consumerService.doesUsernameExist(usrn) == true) {
        	   	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	   			response.getWriter().println("There is already a user registered with this username!");
	   			return;            		
           }
           
            
            consumerId = consumerService.createConsumer(usrn, pwd, email);
            consumer = consumerService.findById(consumerId);
            
            System.out.println("New Consumer Registered");
            
            
            request.getSession().setAttribute("consumer", consumer);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(consumer);
                   		
	}
	
	public void destroy() {
    }

}
