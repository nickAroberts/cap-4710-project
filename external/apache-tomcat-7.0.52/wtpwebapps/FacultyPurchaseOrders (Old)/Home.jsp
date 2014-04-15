<%@ page contentType="text/html" pageEncoding="UTF-8" errorPage="Error.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
</head>
<body>
	<br><br><br><br><br>
	<center>
		<h2>
			<%
			String a = session.getAttribute("un").toString();
			out.println("Hello " + a);
			%>
		</h2>
		<br>
		<br>
		<br><br><br><br><br>
		<a href = "Logout.jsp">Logout</a>
	</center>
</body>
</html>