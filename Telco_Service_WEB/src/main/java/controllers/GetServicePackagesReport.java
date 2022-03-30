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

import it.polimi.db2.telco.entities.PackageReport;
import it.polimi.db2.telco.services.PackageReportService;

@WebServlet("/GetServicePackagesReport")
public class GetServicePackagesReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name="it.polimi.db2.EJB.services/PackageReportService")
	private PackageReportService packageReportService;
	
	
	
    public GetServicePackagesReport() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<PackageReport> reports =  new ArrayList<PackageReport>();
		
		reports = packageReportService.getAll();
		
		
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(reports);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
