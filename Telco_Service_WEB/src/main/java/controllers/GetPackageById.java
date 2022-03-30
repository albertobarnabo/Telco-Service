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

import it.polimi.db2.telco.entities.ServicePackage;
import it.polimi.db2.telco.services.ServicePackageService;

@WebServlet("/GetPackageById")
public class GetPackageById extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name="it.polimi.db2.EJB.services/ServicePackageService")
	private ServicePackageService servicePackageService;
	
    public GetPackageById() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServicePackage servicePackage = new ServicePackage();
		
		int pckgId = 0;
		
		String id = request.getParameter("packageId");
		
		if(id == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    	response.getWriter().println("Incorrect PackageId");
			return;
		}
		
		try {
			pckgId = Integer.parseInt(id);
		}catch(NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        	response.getWriter().println("Incorrect PackageId");
			return;
		}
		
		
		servicePackage = servicePackageService.getPackageById(pckgId);
		
		
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(servicePackage);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
