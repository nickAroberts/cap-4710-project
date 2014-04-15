package edu.cecs.fpo.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.cecs.fpo.database.management.AbstractDatabaseManager;
import edu.cecs.fpo.database.tables.AbstractTableEntry;
import edu.cecs.fpo.database.tables.Order;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "UserLoginServlet", 			
        urlPatterns = {"/UserLoginServlet"}, 	
        loadOnStartup = 1 				
)
public class PurchaserUpdateDatabaseServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
    	
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        
        int i = 0;
        Order sampleOrder = new Order();
        
        while(request.getParameter(sampleOrder.getColumnNames()[0] + "_" + i) != null){
        	Order order = new Order(
        			Integer.parseInt(request.getParameter(sampleOrder.getColumnNames()[0] + "_" + i)),
        			Integer.parseInt(request.getParameter(sampleOrder.getColumnNames()[1] + "_" + i)), 
        			Date.valueOf(request.getParameter(sampleOrder.getColumnNames()[2] + "_" + i)),
        			Date.valueOf(request.getParameter(sampleOrder.getColumnNames()[3] + "_" + i)), 
        			Date.valueOf(request.getParameter(sampleOrder.getColumnNames()[4] + "_" + i)), 
        			Date.valueOf(request.getParameter(sampleOrder.getColumnNames()[5] + "_" + i)),
        			request.getParameter(sampleOrder.getColumnNames()[6] + "_" + i), 
        			Integer.parseInt(request.getParameter(sampleOrder.getColumnNames()[7] + "_" + i)) == 1 ? true : false, 
        			Integer.parseInt(request.getParameter(sampleOrder.getColumnNames()[8] + "_" + i)) == 1 ? true : false, 
        			request.getParameter(sampleOrder.getColumnNames()[9] + "_" + i),
        			request.getParameter(sampleOrder.getColumnNames()[10] + "_" + i), 
        			request.getParameter(sampleOrder.getColumnNames()[11] + "_" + i), 
        			request.getParameter(sampleOrder.getColumnNames()[12] + "_" + i), 
        			request.getParameter(sampleOrder.getColumnNames()[13] + "_" + i), 
        			request.getParameter(sampleOrder.getColumnNames()[14] + "_" + i),
        			Float.parseFloat(request.getParameter(sampleOrder.getColumnNames()[11] + "_" + i)), 
        			request.getParameter(sampleOrder.getColumnNames()[15] + "_" + i), 
        			request.getParameter(sampleOrder.getColumnNames()[16] + "_" + i), 
        			request.getParameter(sampleOrder.getColumnNames()[17] + "_" + i));
        	
        	System.out.println(order.toSQLRepresentation());
        	
        	AbstractDatabaseManager.insertRow(order);
        	
        	i++;
        }
        
        try {
			
			List<AbstractTableEntry> entries = AbstractDatabaseManager.selectAllRowsInTable(Order.class);
			List<Order> orders = new ArrayList<Order>();
			for(AbstractTableEntry entry : entries){
				if(entry instanceof Order){
					orders.add((Order) entry);
				}
			}
			
				PrintWriter writer = response.getWriter();
			        writer.append("<!DOCTYPE html>\r\n")
			              .append("<html>\r\n")
			              .append("    <head>\r\n")
			              .append("        <title>Faculty Purchase Order</title>\r\n")
			              .append("    </head>\r\n")
			              .append("    <body>\r\n")
			              .append("		Welcome Purchaser.")
			              .append("    </body>\r\n")
			              .append("			<form name = \"PurchaseRequest\" Method = \"Post\" Action = \"UserLoginServlet\"")
			              .append("				onClick = \"getTimeStamp()\" onSubmit = \"return testValidEntry()\" >")
			              .append("				<fieldset>")
			              .append("				</fieldset>");				             
			        
			    for(int k = 0; k < orders.size(); k++){
			    	
			    	writer.append("<div>");
			    	
			    	for(int j = 0; j < orders.get(k).getColumnNames().length; j++){	    	  
			    		  writer.append("<input type = \"text\" id = \"" + orders.get(k).getColumnNames()[j] + "_" + k + "\" name = \"user_" + k + "\" value = \"" +  orders.get(k).getValue(j) + "\" required/>");
			    	}
			    	
			    	writer.append("</div><br>");
			    }
			    
			    writer.append("<div class='container'>")	
		    			.append("<input type=\"submit\" name=\"submit\" value=\"Submit\"/><br/>")
		    			.append("</div>")
		    			.append("</html>\r\n");
			        
			    return;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }	
}

