<%--
  Created by IntelliJ IDEA.
  User: Sophie
  Date: 05.01.2022
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3> <a href="index.html">home</a> </h3>
<hr>
<h2>meals</h2>
<table border=1 style="width:40%">
    <th>Date</th>
    <th>Discription</th>
    <th>Calories</th>
    <c:forEach  items="${mealTOlist}" var="meal">
        <tr style="color:${meal.excess ? 'green' : 'red'}">
            <td>
            <fmt:parseDate value = "${meal.dateTime}" var = "parsedDateTime" pattern = "yyyy-MM-dd'T'HH:mm" type="both"/>
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}" />
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
