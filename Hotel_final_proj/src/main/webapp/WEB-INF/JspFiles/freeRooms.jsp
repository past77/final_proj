<%--
  Created by IntelliJ IDEA.
  User: ppolozhe
  Date: 5/28/19
  Time: 6:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" uri="http://ppolozhe" %>

<html>
<head>
    <%@include file="head.jsp" %>
</head>
<body>
<%@include file="header.jsp" %>

<form class="register" name="addBookingForm" method="POST" action="/rooms/find" autocomplete="on">
    <input type="hidden" name="page" value="freeRooms">
    <table align="center">
        <tr>
            <td><fmt:message key="bookings.dateIn"/>*</td>
            <td><input type="date" value="${dateIn}" name="dateIn" required="required"></td>
        </tr>

        <tr>
            <td><fmt:message key="bookings.dateOut"/>*</td>
            <td><input type="date" value="${dateOut}" name="dateOut" required="required"></td>
        </tr>


        <tr>
            <td><fmt:message key="bookings.rooms.type"/>*</td>
            <td>
                <select name="typeRoom" required=required>
                    <option value="Standart" ${typeRoom == 'standart' ? 'selected' : ''}> <fmt:message key="standart"></fmt:message></option>
                    <option value="Lux" ${typeRoom == 'lux' ? 'selected' : ''}><fmt:message key="lux"></fmt:message></option>
                    <option value="Economy" ${typeRoom == 'economy' ? 'selected' : ''}><fmt:message key="economy"></fmt:message></option>
                </select>
            </td>
        </tr>
<input type="submit" class="submit-button" value=<fmt:message key="bookings.find"/>>
</form>
    <div class="register">
        <table align="center">
            <tr>
                <th>
                    <div class="col-md-2">
                        <fmt:message key="rooms.capacity"/>
                    </div>
                </th>
                <th>
                    <div class="col-md-2">
                        <fmt:message key="rooms.price"/>
                    </div>
                </th>
                <th>
                    <div class="col-md-2">
                        <fmt:message key="rooms.number"></fmt:message>
                    </div>
                </th>
                <th>
                    <div class="col-md-2">
                        <fmt:message key="rooms.rooms.type"/>
                    </div>
                </th>
            </tr>
            <c:forEach items="${freeNumbersForBooking}" var="item">
                <tr>
                    <td>
                        <div class="col-md-2">
                            <c:out value="${item.roomCap}"/>
                        </div>
                    </td>
                    <td>
                        <div class="col-md-2">
                            <c:out value="${item.price}"/>
                        </div>
                    </td>
                    <td>
                        <div class="col-md-2">
                            <c:out value="${item.number}"/>
                        </div>
                    </td>
                    <td>
                        <div class="col-md-2">
                            <fmt:message key="${item.roomType.toString()}"></fmt:message>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>


<c:forEach items="${errors}" var="item">
    <p class="text-danger"><fmt:message key="${item}"/></p>
    <br>
</c:forEach>
</body>
</html>