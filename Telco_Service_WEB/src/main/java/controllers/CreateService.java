package controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.db2.telco.entities.Service;
import it.polimi.db2.telco.exceptions.InvalidServiceException;
import it.polimi.db2.telco.services.ServiceService;

@WebServlet("/CreateService")
@MultipartConfig
public class CreateService extends HttpServlet {
	
	@EJB(name="it.polimi.db2.EJB.services/ServiceService")
	private ServiceService serviceService;
	
	private static final long serialVersionUID = 1L;
       
    public CreateService() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String type = null;
		String name = null;
		String minutesParam = null;
		String minutesFeeParam = null;
		String smsParam = null;
		String smsFeeParam = null;
		String gigabytesParam = null;
		String gigabytesFeeParam = null;
		
		
		type = request.getParameter("type");
		name = request.getParameter("name");
		minutesParam = request.getParameter("minutes");
		minutesFeeParam = request.getParameter("minutesFee");
		smsParam = request.getParameter("sms");
		smsFeeParam = request.getParameter("smsFee");
		gigabytesParam = request.getParameter("gigabytes");
		gigabytesFeeParam = request.getParameter("gigabytesFee");
	
	    

        if (type == null || name == null || type.isEmpty() || name.isEmpty() || (minutesParam == null && minutesFeeParam==null && smsParam==null && smsFeeParam==null && gigabytesParam==null && gigabytesFeeParam==null)) {      	
        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        	response.getWriter().println("All parameters are null!");
			return;
        } 
          
		
		int minutes = 0;
		int sms = 0;
		int gigabytes = 0;
		float minutesFee = 0;
		float smsFee = 0;
		float gigabytesFee = 0;
		
		if(minutesParam!=null && !minutesParam.isEmpty())
			try{ 
				minutes = Integer.parseInt(minutesParam);
			}catch(NumberFormatException e) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
	        	response.getWriter().println(e.getMessage());
				return;
			}
		
		if(smsParam!=null && !smsParam.isEmpty())
			try{ 
				sms = Integer.parseInt(smsParam);
			}catch(NumberFormatException e) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
	        	response.getWriter().println(e.getMessage());
				return;
			}
		
		if(gigabytesParam!=null && !gigabytesParam.isEmpty())
			try{ 
				gigabytes = Integer.parseInt(gigabytesParam);
			}catch(NumberFormatException e) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
	        	response.getWriter().println(e.getMessage());
				return;
			}
		
		if(minutesFeeParam!=null && !minutesFeeParam.isEmpty())
			try{ 
				minutesFee = Integer.parseInt(minutesFeeParam);
			}catch(NumberFormatException e) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
	        	response.getWriter().println(e.getMessage());
				return;
			}
		
		if(smsFeeParam!=null && !smsFeeParam.isEmpty())
			try{ 
				smsFee = Integer.parseInt(smsFeeParam);
			}catch(NumberFormatException e) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
	        	response.getWriter().println(e.getMessage());
				return;
			}
		
		if(gigabytesFeeParam!=null && !gigabytesFeeParam.isEmpty())
			try{ 
				gigabytesFee = Integer.parseInt(gigabytesFeeParam);
			}catch(NumberFormatException e) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
	        	response.getWriter().println(e.getMessage());
				return;
			}
		
		
		if(minutes<0 || sms<0 || gigabytes<0 || minutesFee<0 || smsFee<0 || gigabytesFee<0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        	response.getWriter().println("One or more parameters have negative values!");
			return;
		}
		
		Service service = new Service();
		
		service.setType(type);
		service.setName(name);
		service.setGigabytes(gigabytes);
		service.setMinutes(minutes);
		service.setGigabytesFee(gigabytesFee);
		service.setMinutesFee(minutesFee);
		service.setSms(sms);
		service.setSmsFee(smsFee);
		
		try {
			serviceService.createService(service);
		} catch (InvalidServiceException e) {
			response.setStatus(HttpServletResponse.SC_CONFLICT);
        	response.getWriter().println(e.getMessage());
			return;
		}
		
		response.setStatus(HttpServletResponse.SC_OK);
		
	}

}
