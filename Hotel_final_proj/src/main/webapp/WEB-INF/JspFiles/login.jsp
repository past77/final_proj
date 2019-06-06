
<%--
  Created by IntelliJ IDEA.
  User: ppolozhe
  Date: 5/27/19
  Time: 9:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <%@include file="head.jsp" %>
</head>

<body>
<%@include file="header.jsp" %>
<form class="register" method="POST" action="/login/signin" autocomplete="on">
    <span class="text-primary"><i class="fa fa-envelope-o" aria-hidden="true"></i><fmt:message key="login.page"/> </span>
    <input type="text" name="login" placeholder="example@mail.com" value="${login}" required="required">
    <br>
    <span class="text-primary"><i class="fa fa-unlock-alt" aria-hidden="true"></i><fmt:message key="password"/> </span>
    <input name="password"  placeholder="********" required="required"
           type="password">
    <br>
    <input class="submit-button" type="submit" value=<fmt:message key="sign.in"/>>
</form>

<c:forEach items="${errors}" var="item">
    <p class="text-danger"><fmt:message key="${item}"/></p>
    <br>
</c:forEach>
</body>
</html>
