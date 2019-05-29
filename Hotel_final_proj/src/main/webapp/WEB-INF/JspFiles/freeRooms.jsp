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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <%@include file="head.jsp" %>
</head>
<body>
<%@include file="header.jsp" %>

<form class="register" name="addBookingForm" method="POST" action="/apartments/find" autocomplete="on">
    <input type="hidden" name="page" value="freeNumbers">
    <table align="center">
        <tr>
            <td><fmt:message key="bookings.dateIn"/>*</td>
            <td><input type="date" value="${dateIn}" name="dateIn" required="required"></td>
        </tr>

        <tr>
            <td><fmt:message key="bookings.dateIn"/>*</td>
            <td><input type="date" value="${dateIn}" name="dateIn" required="required"></td>
        </tr>


        <tr>
            <td><fmt:message key="bookings.rooms.type"/>*</td>
            <td>
                <select name="apartmentsType" required=required>
                    <option value="Standart" ${apartmentsType == 'standart' ? 'selected' : ''}> <fmt:message key="standart"></fmt:message></option>
                    <option value="Suite" ${apartmentsType == 'suite' ? 'selected' : ''}><fmt:message key="suite"></fmt:message></option>
                </select>
            </td>
        </tr>

    </table>
</body>
</html>
