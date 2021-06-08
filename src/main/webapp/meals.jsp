<%--
  Created by IntelliJ IDEA.
  User: Sophie
  Date: 08.06.2021
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>
    <table style="width:40%">
        <tr style="text-align: left">
            <th>Date</th>
            <th>Description</th>
            <th>Callories</th>
        </tr>

        <c:forEach items="${meals}" var="meal">
            <tr style="color:
            <c:if test="${meal.excess}">red</c:if>
            <c:if test="${!meal.excess}">green</c:if>"
            >

                <td>
                    <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${ parsedDateTime }"/>

                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
            </tr>
        </c:forEach>


    </table>
</body>
</html>
