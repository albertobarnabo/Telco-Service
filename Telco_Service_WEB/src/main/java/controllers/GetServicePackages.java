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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.db2.telco.entities.ServicePackage;
import it.polimi.db2.telco.services.ServicePackageService;

@WebServlet("/GetServicePackages")
public class GetServicePackages extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name="it.polimi.db2.EJB.services/ServicePackageService")
	private ServicePackageService servicePackageService;
	
    public GetServicePackages() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		List<ServicePackage> servicePackages = new ArrayList<ServicePackage>();
		
		servicePackages = servicePackageService.findAll();
		
		
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(servicePackages);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
