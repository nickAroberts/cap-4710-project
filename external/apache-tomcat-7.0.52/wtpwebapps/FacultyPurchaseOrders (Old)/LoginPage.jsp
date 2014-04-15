
<!-- The name attributes are used to retrieve the values of respective fields in other pages -->

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
<center>
	<h1>Login Page</h1>
</center>

<h2>Signup Details</h2>

<form  action=LoginCheck.jsp  method="post">
	<table cellpadding="5" cellspacing="5">
		<tr>
			<td>Please enter your username:</td>
			<td><input type="text"  name="un"></td>
		</tr>
		
		<tr>
			<td>Please enter your password:</td>
			<td><input type="password"  name="pw"></td>
		</tr>
		
		<tr>
			<td><input type="submit" value="Submit" style="width:100px; height:25px"></td>
		</tr>
	</table>
</form>
</body>
</html>