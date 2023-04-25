<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>model and view</title>
</head>
<body>
	<%
		out.println("Model: view");
	%>
	<br>
	${objectTest}
	<br>
	${list}
	<br><br>
	<c:forEach var="mylist" items="${list}">
		${mylist}<br>
	</c:forEach>
	<br><br>
	이름 : ${name}

</body>
</html>