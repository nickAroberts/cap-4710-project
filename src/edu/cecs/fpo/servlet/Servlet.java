package edu.cecs.fpo.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "firstServlet", 			// Getting an instance of the Servlet named firstServlet.
        urlPatterns = {"/checkboxes"}, 	// It maps to the next URLs
        loadOnStartup = 1 				// It starts when the application starts
)
public class Servlet extends HttpServlet
{
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
              .append("    </head>\r\n")
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String[] fruits = request.getParameterValues("order");

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.append("<!DOCTYPE html>\r\n")
              .append("<html>\r\n")
              .append("    <head>\r\n")
              .append("        <title>Faculty Purchase Order</title>\r\n")
              .append("    </head>\r\n")
              .append("    <body>\r\n")
              .append("        <h2>You have selected</h2>\r\n");

        if(fruits == null)
            writer.append("        You did not select anything on the list.\r\n");
        else
        {
            writer.append("        <ul>\r\n");
            for(String fruit : fruits)
            {
                writer.append("        <li>").append(fruit).append("</li>\r\n");
            }
            writer.append("        </ul>\r\n");
        }

        writer.append("    </body>\r\n")
              .append("</html>\r\n");
    }
}

