<%@ page contentType="text/html/javascript" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
</head>
<body>

	<%
// Obtainning the field values
		String username = request.getParameter("un");
		String password = request.getParameter("pw"); 
		
		if((username.equals("root") && password.equals("admin"))){
			session.setAttribute("un", username);
			response.sendRedirect("Home.jsp");
		}
		
		else 
		{
			response.sendRedirect("LoginPage.jsp");
			
		}
	%>
</body>
</html>