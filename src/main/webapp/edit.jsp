<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit meal</title>
</head>
<body>
<style>
    table th, table td {
        padding: 10px;
    }
</style>
<h3><a href="index.html">Home</a></h3>
<h2>Edit meal</h2>
<form action="meals?action=update" method="post">
    <table>
        <thead>
        <tr>
            <th>id</th>
            <th><label for="date">Date</label></th>
            <th><label for="time">Time</label></th>
            <th><label for="desc">Description</label></th>
            <th><label for="cal">Calories</label></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${meal.getId()}<input type="hidden" name="mealId" value="${meal.getId()}"></td>
            <td><input type="date" name="date" id="date" value="${meal.getDate()}"></td>
            <td><input type="time" name="time" id="time" value="${meal.getTime()}"></td>
            <td><input type="text" name="description" id="desc" value="${meal.getDescription()}"></td>
            <td><input type="number" name="calories" id="cal" value="${meal.getCalories()}"></td>
        </tr>
        <tr>
            <td></td>
            <td style="text-align: center"><button type="submit">Apply</button></td>
            <td></td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>