package edu.cecs.fpo.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.cecs.fpo.server.ServerImpl;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
		description = "LoginServlet",
        urlPatterns = { "/LoginServlet" }, 
        
        loadOnStartup = 1 				// It starts when the application starts
)
public class LoginServlet extends HttpServlet
{
	

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
    	// Gets the relevant info from the form - Faculty Order Form
    	String orderNum = request.getParameter("orderNum");
    	String name = request.getParameter("name");
    	String email = request.getParameter("email");
    	String accountNum = request.getParameter("accountNum");
    	boolean isUrgent = request.getParameter("isUrgent") != null;
    	boolean isComputer = request.getParameter("isComputer") != null;

    	String vendor = request.getParameter("vendor");
    	String comment = request.getParameter("comment");
    	float costNum = Float.parseFloat( request.getParameter("cost") );
    	String filename = request.getParameter("filename");
    	
    	
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        
        // create confirmation order page, and retrieve values from form
        writer.append("<!DOCTYPE html>\r\n")
              .append("<html>\r\n")
              .append("    <head>\r\n")
              .append("        <title>Faculty Purchase Order</title>\r\n")
              .append("    </head>\r\n")
              .append("    <body>\r\n")
              .append(" <H3>  The following order has been created, and an email confirmation will be delivered shortly:<br/> </H3>")
              
              .append("Confirmation: ")
              .append("	 This is order number " + orderNum + " <br/>")
        		
              .append(" The name submitted is " + name + " <br/>")
              .append(" The email submitted is " + email + " <br/>")
              .append("The accounNum is " + accountNum + " <br/>")
              .append("Is this urgent? " + isUrgent + " <br/>")
              .append(" Is this a computer " + isComputer + " <br/>")
              .append("The vendor name is " + vendor + "<br/>")
              .append("Comments: " + comment + "<br/>")
              .append("The requested charge is " + costNum + " <br/>")
              .append(" submitting file: " + filename + " <br/>")
              .append("<br/>  <br/>")
              .append(" <a href = 'FacultyOrderForm.html'>Go back to order form</a>")
        	  .append("    </body>\r\n")
              .append("</html>\r\n");
        
        // call ServerImpl with form values to load into database
        try {
			ServerImpl.createAndVerifyPurchaseOrder(name, email, accountNum, isUrgent, isComputer, vendor, comment, costNum, "file");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}

