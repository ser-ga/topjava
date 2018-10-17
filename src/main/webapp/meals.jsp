<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }

        table td {
            padding-left: 10px;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>
    <hr/>
    <form action="meals" method="post">
        <input type="hidden" name="action" value="filter"/>
        <table>
            <thead>
            <tr>
                <th colspan="2">Дата</th>
                <th colspan="2">Время</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><label for="startDate">от</label></td>
                <td><label for="finishDate">до</label></td>
                <td><label for="startTime">от</label></td>
                <td><label for="finishTime">до</label></td>
            </tr>
            <tr>
                <td><input type="date" name="startDate" id="startDate" value="${param.startDate}"/></td>
                <td><input type="date" name="finishDate" id="finishDate" value="${param.finishDate}"/></td>
                <td><input type="time" name="startTime" id="startTime" value="${param.startTime}"/></td>
                <td><input type="time" name="finishTime" id="finishTime" value="${param.finishTime}"/></td>
                <td>
                    <button type="submit">Отфильтровать</button>
                </td>
            </tr>
            </tbody>
        </table>

    </form>

    <hr/>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <c:if test="${meals.size() == 0}"><p><b>Данные не найдены</b></p></c:if>
</section>
</body>
</html>