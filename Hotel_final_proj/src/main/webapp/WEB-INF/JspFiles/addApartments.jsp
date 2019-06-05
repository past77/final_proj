<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
<head>
    <%@include file="head.jsp" %>
</head>

<body>
<%@include file="header.jsp" %>

<%--
<form name="loginForm" method="POST" action="login/authorization" autocomplete="on">
--%>
<form class="register" name="addBookingForm" method="POST" action="/profile/addRoom/add" autocomplete="on">

    <table align="center">
        <tr>
            <td><fmt:message key="rooms.capacity"/>*</td>
            <td><input type="number" value="${capacity}" name="capacity" required="required"></td>
        </tr>

        <tr>
            <td><fmt:message key="rooms.price"/>*</td>
            <td><input type="number" value="${price}" name="price" required="required"></td>
        </tr>

        <tr>
            <td><fmt:message key="rooms.number"/>*</td>
            <td><input type="text" value="${number}" name="number" required="required"></td>
        </tr>

        <tr>
            <td><fmt:message key="bookings.rooms.type"/>*</td>
            <td>
                <select name="typeRoom" required=required>
                    <option value="Standart" ${typeRoom == 'standart' ? 'selected' : ''}><fmt:message key="standart"></fmt:message></option>
                    <option value="Lux" ${typeRoom == 'lux' ? 'selected' : ''}><fmt:message key="lux"></fmt:message></option>
                     <option value="Economy" ${typeRoom == 'economy' ? 'selected' : ''}><fmt:message key="economy"></fmt:message></option>
                </select>
            </td>
        </tr>

    </table>

    <input type="submit" value=<fmt:message key="bookings.create"/>>
</form>
<c:forEach items="${errors}" var="item">
    <p class="text-danger"><fmt:message key="${item}"/></p>
    <br>
</c:forEach>

</body>
</html>