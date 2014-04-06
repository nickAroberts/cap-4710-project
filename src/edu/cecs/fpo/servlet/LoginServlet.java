package edu.cecs.fpo.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
		description = "LoginServlet",
        urlPatterns = { "/LoginServlet" }, 
        
        loadOnStartup = 1 				// It starts when the application starts
)
public class LoginServlet extends HttpServlet
{
	/*
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
    	// It sets the content type of the response to text/html and the chatacter encoding to UTF-8
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.append("<!DOCTYPE html>\r\n")
              .append("<html>\r\n")
              .append("    <head>\r\n")
              .append("        <title>Faculty Purchase Order</title>\r\n")
              .append("    </head>\r\n");
              
              .append("    <body>\r\n")
              .append("        <form action=\"checkboxes\" method=\"POST\">\r\n")
              .append("Please select what would like to do:<br/>\r\n")
              .append("<input type=\"checkbox\" name=\"order\" value=\"Call Nick\"/>")
              .append(" Call Nick<br/>\r\n")
              .append("<input type=\"checkbox\" name=\"order\" value=\"Call Jeremy\"/>")
              .append(" Call Jermy<br/>\r\n")
              .append("<input type=\"checkbox\" name=\"order\" value=\"Call Amanda\"/>")
              .append(" Call Amanda<br/>\r\n")
              .append("<input type=\"submit\" value=\"Submit\"/>\r\n")
              .append("        </form>")
              .append("    </body>\r\n")
              .append("</html>\r\n");
              
    }
    */

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
    	
    	
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.append("<!DOCTYPE html>\r\n")
              .append("<html>\r\n")
              .append("    <head>\r\n")
              .append("        <title>Faculty Purchase Order</title>\r\n")
              .append("    </head>\r\n")
              .append("    <body>\r\n")
              .append("	 This is order number " + orderNum + " <br/>")
        		
        .append(" The name submitted is " + name + " <br/>")
        .append(" The email submitted is " + email + " <br/>")
        .append("The accounNum is " + accountNum + " <br/>")
        .append("Is this urgent? " + isUrgent + " <br/>")
        .append(" Is this a computer " + isComputer + " <br/>")
        .append("The vendor name is " + vendor + "<br/>")
        .append("Comments: " + comment + "<br/>")
        .append("The requested charge is " + costNum + " <br/>")
        
        .append(" <H1>  Whoo HOO !!! </H1>")

        	  .append("    </body>\r\n")
              .append("</html>\r\n");
    }
    
}

