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
            <td><a href="meals?id=${item.getId()}&action=update">edit</a> | <a
                    href="meals?id=${item.getId()}&action=delete">delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<form action="meals?action=${meal==null ? 'new' : 'update'}" method="post">
    <table>
        <thead>
        <tr>
            ${meal==null ? '' : '<th><label for="mealId">id</label></th>'}
            <th><label for="datetime">Date / Time</label></th>
            <th><label for="desc">Description</label></th>
            <th><label for="cal">Calories</label></th>
        </tr>
        </thead>
        <tbody>

        <c:if test="${meal==null}">
            <tr>
                <input type="hidden" name="mealId" id="mealId" value="0" required>
                <td><input type="datetime-local" name="datetime" id="datetime" value="" required></td>
                <td><input type="text" name="description" id="desc" value="" required></td>
                <td><input type="number" name="calories" id="cal" value="" required></td>
                <td>
                    <button type="submit">Add meal</button>
                </td>
            </tr>
        </c:if>
        <c:if test="${meal!=null}">
            <tr>
                <td><input type="hidden" name="mealId" id="mealId" value="${meal.getId()}" required>${meal.getId()}</td>
                <td><input type="datetime-local" name="datetime" id="datetime" value="${meal.getDateTime()}" required>
                </td>
                <td><input type="text" name="description" id="desc" value="${meal.getDescription()}" required></td>
                <td><input type="number" name="calories" id="cal" value="${meal.getCalories()}" required></td>
                <td>
                    <button type="submit">Update</button>
                </td>
            </tr>
        </c:if>
        </tbody>
    </table>
</form>
</body>
</html>