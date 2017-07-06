<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h2>Hello World!</h2>
hello:    ${test}  


<body>
	<table border="1">
		<tr>
		<td>id</td>
		<td>name</td>
		<td>createTime</td>
		</tr>
		
			<c:forEach var="item" items="${lists}">
					<tr>
					<td>${item.id}</td>
					<td>${item.name}</td>
					
					<td>${item.createTime}</td>
			</tr>
			</c:forEach>
			
		
	</table>

</body>
</html>
