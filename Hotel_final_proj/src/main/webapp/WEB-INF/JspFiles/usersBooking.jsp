<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>




<html>
<head>
    <%@include file="head.jsp" %>
</head>

<body>
<%@include file="header.jsp" %>

<%--
<form name="loginForm" method="POST" action="login/authorization" autocomplete="on">
--%>
<c:forEach items="${bookings}" var="item">

    <table class="register mytable" align="center">
        <tr>
            <th>
                <div class="col-md-2">
                    <fmt:message key="bookings.dateIn"/>
                </div>
            </th>
            <th>
                <div class="col-md-2">
                    <fmt:message key="bookings.dateOut"/>
                </div>
            </th>
            <th>
                <div class="col-md-2">
                    <fmt:message key="bookings.rooms.type"/>
                </div>
            </th>
            <th>
                <div class="col-md-2">
                    <fmt:message key="bookings.status"/>
                </div>
            </th>

            <c:if test="${item.status.toString() == 'confirmed'}">
                <th>
                    <div class="col-md-2">
                        <fmt:message key="bill.price"/>
                    </div>
                </th>
            </c:if>
        </tr>
        <tr>
            <th>
                <div class="col-md-2">
                <tags:localDate date="${item.dateIn}"/>
                </div>
            </th>
            <th>
                <div class="col-md-2">
                    <tags:localDate date="${item.dateOut}"/>
                </div>
            </th>

            <th>
                <div class="col-md-2">
                    <fmt:message key="${item.typeRoom.toString()}"/>
                </div>
            </th>
            <th>
                <div class="col-md-2">
                    <fmt:message key="${item.status.toString()}"/>
                </div>
            </th>

            <c:if test="${item.status.toString() == 'confirmed'}">
                <td>
                    <div class="col-md-2">
                        <c:out value="${item.bill.price}"/>
                    </div>
                </td>
            </c:if>
            <td>
                <div class="col-md-2">
                    <form action="/profile/bookings/delete" method="post">
                        <input type="hidden" name="delete" value="${item.id}"/>
                        <button class="submit-button" type="submit"><fmt:message key="booking.delete"/></button>
                    </form>
                </div>
            </td>
        </tr>
    </table>


</c:forEach>


</body>
</html>