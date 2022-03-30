package controllers;

import java.io.IOException;
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

import it.polimi.db2.telco.entities.Employee;
import it.polimi.db2.telco.exceptions.InvalidOptionalProductException;
import it.polimi.db2.telco.exceptions.InvalidServiceException;
import it.polimi.db2.telco.exceptions.UserNotFoundException;
import it.polimi.db2.telco.services.ServicePackageService;

@WebServlet("/CreateServicePackage")
@MultipartConfig
public class CreateServicePackage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB(name="it.polimi.db2.EJB.services/ServicePackageService")
	private ServicePackageService servicePackageService;
    
    public CreateServicePackage() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		Employee emp = (Employee) session.getAttribute("employee");
		
		if (emp == null) {
			String loginpath = request.getServletContext().getContextPath() + "/loginpage.html";
			response.sendRedirect(loginpath);
		}
		int employeeId = emp.getEmployeeId();
		
		String name = request.getParameter("servpackname");
		String f12 = request.getParameter("fee12");
		String f24 = request.getParameter("fee24");
		String f36 = request.getParameter("fee36");
		String services[] = request.getParameterValues("selectedServices");
		String optProds[] = request.getParameterValues("selectedOptionalProducts");
		//TODO: handle bad requests
		
		if(name == null || name.isEmpty() || (f12==null && f24==null && f36==null) ||services==null || services.length<=0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        	response.getWriter().println("One or more of the required parameters is missing!");
			return;
		}
		
		float fee12=0;
		float fee24=0;
		float fee36=0;
		
		try {
			fee12 = Float.parseFloat(f12);
			fee24 = Float.parseFloat(f24);
			fee36 = Float.parseFloat(f36);
		}catch(NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        	response.getWriter().println(e.getMessage());
			return;
		}
		
		if(fee12<0 || fee24<0 || fee36<0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        	response.getWriter().println("Fees cannot have negative values!");
			return;
		}

		
		List<Integer> optProdIds = new ArrayList<>();
		List<Integer> serviceIds = new ArrayList<>();
		
		if(optProds!=null && optProds.length>0) {
			for(int i=0; i<optProds.length; i++) {
				optProdIds.add(Integer.parseInt(optProds[i]));
			}
		}
		else
			optProdIds=null;
		
		for(int i=0; i<services.length; i++) {
			serviceIds.add(Integer.parseInt(services[i]));
		}
		
		try {
			servicePackageService.createServicePackage(name, fee12, fee24, fee36, employeeId, serviceIds, optProdIds);
		} catch (InvalidServiceException | UserNotFoundException | InvalidOptionalProductException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
