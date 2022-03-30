package controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.db2.telco.entities.OptionalProduct;
import it.polimi.db2.telco.exceptions.DuplicatedOptionalProductException;
import it.polimi.db2.telco.services.OptionalProductService;


@WebServlet("/CreateOptionalProduct")
@MultipartConfig
public class CreateOptionalProduct extends HttpServlet {
	
	@EJB(name = "it.polimi.db2.EJB.services/OptionalProductService")
	private OptionalProductService optionalProductService;
	
	private static final long serialVersionUID = 1L;
       
	
    public CreateOptionalProduct() {
        super();
    }
    
    public void init() throws ServletException {}	

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = null;
		String feeParam = null;
		
		 try {
			 name = request.getParameter("optproductname");
			 feeParam = request.getParameter("optproductfee"); 
	            
	       
            if (name == null || feeParam == null || name.isEmpty() || feeParam.isEmpty()) {
            	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    			response.getWriter().println("Both fields are mandatory!");
    			return;
            } 
            
            
	            
	      } catch (Exception e) {
	                // for debugging only e.printStackTrace();
	                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "One or more input is null");
	                return;
	      }
		 
		float fee = 0;
		 try{ 
			 fee = Float.parseFloat(feeParam);
			}catch(NumberFormatException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        	response.getWriter().println("The fee must be a number!");
				return;
			}
		 
		 
		 if (fee<0) {
         	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
 			response.getWriter().println("A fee cannot be negative!");
 			return;
         } 
		 
		 
		 OptionalProduct optionalProduct = new OptionalProduct();
		 optionalProduct.setName(name);
		 optionalProduct.setFee(fee);
		 
		 try {
			optionalProductService.createOptionalProduct(optionalProduct);
		} catch (DuplicatedOptionalProductException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
		}
		 
		response.setStatus(HttpServletResponse.SC_OK);
		 
		 
		 
		
	}
	
	public void destroy() {}
}
