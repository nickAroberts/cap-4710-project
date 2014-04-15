
<html> 

<H1> This is a test. </H1>


<%@ page import ="java.sql.*" %>
<%@ page import = "java.util.Date" %>
<%@ page import = "java.io.File" %>

<%
// creates the Unix Time Step
long orderNum2 = System.currentTimeMillis();

//Date dt=new Date();
//long t=dt.getTime();
//String tm = dt.toString();

// Get infor from Faculty Order Form
String orderNum = request.getParameter("orderNum");
String name = request.getParameter("name");
String email = request.getParameter("email");
String accountNum = request.getParameter("accountNum");
boolean isUrgent = request.getParameter("isUrgent") != null;
boolean isComputer = request.getParameter("isComputer") != null;

String vendor = request.getParameter("vendor");
String comment = request.getParameter("comment");
float costNum = Float.parseFloat( request.getParameter("cost") );
%>

<!-- Output info received from faculty order form to check for accuracy -->

<H3>
This is order number <%=orderNum %> <br/>
Compare to this order num <%= orderNum2  %> <br/>
The name submitted is <%=name %> <br/>
The email submitted is <%=email %> <br/>
The accounNum is <%=accountNum %> <br/>
Is this urgent? <%=isUrgent %> <br/>
Is this a computer <%=isComputer %> <br/>
The vendor name is <%=vendor %> <br/>
Comments: <%=comment %> <br/>
The requested charge is <%= costNum %> <br/>

</H3> 



</html>	
