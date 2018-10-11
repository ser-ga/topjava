<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<style>
    table th, table td {
        padding: 10px;
    }
</style>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table border="1">
    <thead>
    <tr>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="item">
    <tr style="color: <c:out value="${not item.isExceed() ? 'green' : 'red'}" />">
            <td>${fn:replace(item.getDateTime(), 'T', ' ')}</td>
            <td>${item.getDescription()}</td>
            <td>${item.getCalories()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>