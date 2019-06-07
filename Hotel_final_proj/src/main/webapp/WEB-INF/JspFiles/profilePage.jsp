<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <%@include file = "head.jsp"%>
</head>

<body>
    <%@include file = "header.jsp"%>

<c:if test="${user.accounts.statusUser.toString() == 'user'}">

<div class="prof">
        <a href="/profile/addBook"><fmt:message key="addBoking.page"/></a>
              </div>

    <div class="prof">
        <a href="/profile/bookings"><fmt:message key="bookings.page"/></a>
    </div>
     <c:choose>
    <c:when test="${success != null}">
       <div class = "suc"> <fmt:message key="message.complete"/></div>
    </c:when>
     </c:choose>
</c:if>

<c:if test="${user.accounts.statusUser.toString() == 'admin'}">
 <div class="prof">
        <a href="/profile/addRoom"><fmt:message key="addRooms.page"/></a>
    </div>
            <div class="prof">
                <a href="/profile/processedBookings"><fmt:message key="booking.processed.page"/></a>
            </div>
            <div class="prof">
            <a href="/allRooms"><fmt:message key="allRooms.page"/></a>
            </div>
     <c:choose>
        <c:when test="${success != null}">
            <fmt:message key="message.complete"/>
        </c:when>
         </c:choose>
            </c:if>


            </body>
            </html>