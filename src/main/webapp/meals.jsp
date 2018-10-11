<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
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
        <th>Edit/Delete</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="item">
    <tr style="color: <c:out value="${not item.isExceed() ? 'green' : 'red'}" />">
            <td>${item.getDate()} ${item.getTime()}</td>
            <td>${item.getDescription()}</td>
            <td>${item.getCalories()}</td>
            <td><a href="meals?id=${item.getId()}&action=edit">edit</a> | <a href="meals?id=${item.getId()}&action=delete">delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<form action="meals?action=new" method="post">
    <table>
        <thead>
        <tr>
            <th><label for="date">Date</label></th>
            <th><label for="time">Time</label></th>
            <th><label for="desc">Description</label></th>
            <th><label for="cal">Calories</label></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><input type="date" name="date" id="date"></td>
            <td><input type="time" name="time" id="time"></td>
            <td><input type="text" name="description" id="desc"></td>
            <td><input type="number" name="calories" id="cal"></td>
        </tr>
        <tr>
            <td></td>
            <td style="text-align: center"><button type="submit">Add meal</button></td>
            <td></td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>