<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <%@include file = "head.jsp"%>
</head>

<body>
    <%@include file = "header.jsp"%>

<c:if test="${user.userAuthentication.role.toString() == 'client'}">

<div class="col-md-2 menu">
        <a href="/profile/addBooking"><fmt:message key="addBoking.page"/></a>
    </div>
<div class="col-md-2">
    </div>

    <div class="col-md-2 menu">
        <a href="/profile/bookings"><fmt:message key="bookings.page"/></a>
    </div>
</c:if>

<c:if test="${user.userAuthentication.role.toString() == 'admin'}">
