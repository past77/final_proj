<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
<head>
    <%@include file="head.jsp" %>
</head>

<body>
<%@include file="header.jsp" %>


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
        <c:forEach items="${rooms}" var="item">

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
                <td>
                    <div class="col-md-2">
                        <form action="/profile/rooms/delete" method="post">
                          <input type="hidden" name="delete" value="${item.id}"/>
                          <button class="submit-button" type="submit"><fmt:message key="rooms.delete"/></button>
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>


</body>
</html>