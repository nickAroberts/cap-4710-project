package edu.cecs.fpo.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.cecs.fpo.database.management.AbstractDatabaseManager;
import edu.cecs.fpo.database.tables.AbstractTableEntry;
import edu.cecs.fpo.database.tables.Order;
import edu.cecs.fpo.database.tables.User;
import edu.cecs.fpo.server.ServerImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "UserLoginServlet", 			
        urlPatterns = {"/UserLoginServlet"}, 	
        loadOnStartup = 1 				
)
public class UserLoginServlet extends HttpServlet
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
    	
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
			User user = ServerImpl.login(username, password);
			
			if(user == null){
				throw new NullPointerException();
			}
			
			List<AbstractTableEntry> orderEntries = AbstractDatabaseManager.selectAllRowsInTable(Order.class);
			List<Order> orders = new ArrayList<Order>();
			for(AbstractTableEntry entry : orderEntries){
				if(entry instanceof Order){
					orders.add((Order) entry);
				}
			}
			
			List<AbstractTableEntry> purchaserEntries = AbstractDatabaseManager.selectAllRowsInTable(User.class);
			List<User> purchasers = new ArrayList<User>();
			for(AbstractTableEntry entry : purchaserEntries){
				if(entry instanceof User && ((User) entry).getRole().equals("purchaser")){
					purchasers.add((User) entry);
				}
			}
			
			List<AbstractTableEntry> userEntries = AbstractDatabaseManager.selectAllRowsInTable(User.class);
			List<User> users = new ArrayList<User>();
			for(AbstractTableEntry entry : userEntries){
				if(entry instanceof User){
					users.add((User) entry);
				}
			}
			
			if(user.getRole().equals("purchaser")){
				PrintWriter writer = response.getWriter();
			        writer.append("<!DOCTYPE html>\r\n")
			              .append("<html>\r\n")
			              .append("    <head>\r\n")
			              .append("        <title>Purchaser Interface</title>\r\n")
			              .append("    </head>\r\n")
			              .append("    <body>\r\n")
			              .append("		Welcome Purchaser.")
			              .append("    </body>\r\n")
			              .append("			<form name = \"PurchaseRequest\" Method = \"Post\" Action = \"PurchaserUpdateDatabaseServlet\"")
			              .append("				onClick = \"getTimeStamp()\" onSubmit = \"return testValidEntry()\" >")
			              .append("				<fieldset>");			                          
			        
			    for(int i = 0; i < orders.size(); i++){
			    	
			    	writer.append("<div>");
			    	
			    	for(int j=0; j < orders.get(i).getColumnNames().length; j++){	    	  
			    		  writer.append("<input type = \"text\" id = \"" + orders.get(i).getColumnNames()[j] + "_" + i + "\" name = \"user_" + i + "\" value = \"" +  orders.get(i).getValue(j) + "\" required/>");
			    	}
			    	
			    	writer.append("</div><br>");
			    }
			    
			    writer.append("<div class='container'>")	
		    			.append("<input type=\"submit\" name=\"submit\" value=\"Submit\"/><br/>")
		    			.append("</div>")
		    			.append("</fieldset>")
		    			.append("</html>\r\n");
			        
			    return;
			}
			
			else if(user.getRole().equals("accountant")){
				PrintWriter writer = response.getWriter();
		        writer.append("<!DOCTYPE html>\r\n")
		              .append("<html>\r\n")
		              .append("    <head>\r\n")
		              .append("        <title>Accountant Interface</title>\r\n")
		              .append("    </head>\r\n")
		              .append("    <body>\r\n")
		              .append("		Welcome Accountant.")
		              .append("    </body>\r\n")
		              .append("			<form name = \"PurchaseRequest\" Method = \"Post\" Action = \"PurchaserUpdateDatabaseServlet\"")
		              .append("				onClick = \"getTimeStamp()\" onSubmit = \"return testValidEntry()\" >")
		              .append("				<fieldset>");			                          
		        
		        for(int i = 0; i < purchasers.size(); i++){
			    	
			    	writer.append("<div>");
			    	
			    	for(int j=0; j < purchasers.get(i).getColumnNames().length; j++){	    	  
			    		  writer.append("<input type = \"text\" id = \"" + purchasers.get(i).getColumnNames()[j] + "_" + i + "\" name = \"user_" + i + "\" value = \"" +  purchasers.get(i).getValue(j) + "\" required/>");
			    	}
			    	
			    	writer.append("</div><br>");
			    } 
		        
		        writer.append("<br>");
			        
			    for(int i = 0; i < orders.size(); i++){
			    	
			    	writer.append("<div>");
			    	
			    	for(int j=0; j < orders.get(i).getColumnNames().length; j++){	    	  
			    		  writer.append("<input type = \"text\" id = \"" + orders.get(i).getColumnNames()[j] + "_" + i + "\" name = \"user_" + i + "\" value = \"" +  orders.get(i).getValue(j) + "\" required/>");
			    	}
			    	
			    	writer.append("</div><br>");
			    }
			    
			    writer.append("<div class='container'>")	
		    			.append("<input type=\"submit\" name=\"submit\" value=\"Submit\"/><br/>")
		    			.append("</div>")
		    			.append("</fieldset>")
		    			.append("</html>\r\n");
			        
			    return;
			} else if(user.getRole() == "admin"){
				PrintWriter writer = response.getWriter();
		        writer.append("<!DOCTYPE html>\r\n")
		              .append("<html>\r\n")
		              .append("    <head>\r\n")
		              .append("        <title>Admin Interface</title>\r\n")
		              .append("    </head>\r\n")
		              .append("    <body>\r\n")
		              .append("		Welcome Admin.")
		              .append("    </body>\r\n")
		              .append("			<form name = \"PurchaseRequest\" Method = \"Post\" Action = \"PurchaserUpdateDatabaseServlet\"")
		              .append("				onClick = \"getTimeStamp()\" onSubmit = \"return testValidEntry()\" >")
		              .append("				<fieldset>");			                          
		        
	        for(int i = 0; i < users.size(); i++){
		    	
		    	writer.append("<div>");
		    	
		    	for(int j=0; j < users.get(i).getColumnNames().length; j++){	    	  
		    		  writer.append("<input type = \"text\" id = \"" + users.get(i).getColumnNames()[j] + "_" + i + "\" name = \"user_" + i + "\" value = \"" +  users.get(i).getValue(j) + "\" required/>");
		    	}
		    	
		    	writer.append("</div><br>");
		    } 
	        
	        writer.append("<br>");
		        
		    for(int i = 0; i < orders.size(); i++){
		    	
		    	writer.append("<div>");
		    	
		    	for(int j=0; j < orders.get(i).getColumnNames().length; j++){	    	  
		    		  writer.append("<input type = \"text\" id = \"" + orders.get(i).getColumnNames()[j] + "_" + i + "\" name = \"user_" + i + "\" value = \"" +  orders.get(i).getValue(j) + "\" required/>");
		    	}
		    	
		    	writer.append("</div><br>");
		    }
		    
		    writer.append("<div class='container'>")	
	    			.append("<input type=\"submit\" name=\"submit\" value=\"Submit\"/><br/>")
	    			.append("</div>")
	    			.append("</fieldset>")
	    			.append("</html>\r\n");
		        
		    return;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        PrintWriter writer = response.getWriter();
        writer.append("<!DOCTYPE html>\r\n")
              .append("<html>\r\n")
              .append("    <head>\r\n")
              .append("        <title>Faculty Purchase Order</title>\r\n")
              .append("    </head>\r\n")
              .append("    <body>\r\n")
              .append("		You aren't a valid user.")
              .append("    </body>\r\n")
              .append("</html>\r\n");
    }	
}

