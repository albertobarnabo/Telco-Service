package controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.db2.telco.entities.Employee;
import it.polimi.db2.telco.exceptions.CredentialsException;
import it.polimi.db2.telco.services.EmployeeService;

import javax.persistence.NonUniqueResultException;

@WebServlet(name = "CheckEmployeeLogin", value = "/CheckEmployeeLogin")
@MultipartConfig
public class CheckEmployeeLogin extends HttpServlet {
	
    @EJB(name = "it.polimi.db2.EJB.services/EmployeeService")

    private EmployeeService employeeService;

    private static final long serialVersionUID = 1L;

    public CheckEmployeeLogin() {
        super();
    }

    public void init() throws ServletException {
      
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // obtain and escape params
        String usrn = null;
        String pwd = null;
        try {
            usrn = request.getParameter("username");
            pwd = request.getParameter("pwd");
            

            if (usrn == null || pwd == null || usrn.isEmpty() || pwd.isEmpty()) {
                throw new Exception("Missing or empty credential value");
            }

        } catch (Exception e) {
            // for debugging only e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
            return;
        }

        Employee employee = null;

        try {
            // query db to authenticate for consumer
        	employee = employeeService.checkCredentials(usrn, pwd);
        } catch (CredentialsException | NonUniqueResultException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials");
            return;
        }

        // If the user exists, add info to the session and go to home page, otherwise
        // show login page with error message
        if (employee == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Incorrect credentials");
        } else {
            request.getSession().setAttribute("employee", employee);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(usrn);
        }


    }

    public void destroy() {
    }
}