<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h1>Online Banking System - Payment</h1>
	
	<!-- USER INPUT -->
	<form action="/System2/PaymentServlet2" method="POST">
	
		<label for="account">Account ID: </label>
		<input type="text" name="account"></input>
		<br>
		<label for="pin">PIN: </label>
		<input type="password" name="pin"></input>
		<br>
		<label for="amount">Amount: </label>
		<input type="number" name="amount"></input>

		<br>		
		<input type="submit" value="Submit"></input>
	</form>

</body>
</html>