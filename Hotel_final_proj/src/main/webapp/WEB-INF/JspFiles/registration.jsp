<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
<head>
    <%@include file="head.jsp" %>
</head>

<body>
<%@include file="header.jsp" %>

<form class="register" method="POST" action="/registration/signup" autocomplete="on">
    <div>
        <span><i class="fa fa-envelope-o" aria-hidden="true"></i>*</span>
        <input type="text" placeholder="example@mail.com" name="login" value="${login}" required="required">
    </div>
    <div>
        <span><i class="fa fa-unlock-alt" aria-hidden="true"></i>*</span>
        <input type="password"  name="password" required="required">
    </div>


    <div>
        <span><i class="fa fa-caret-right" aria-hidden="true"></i>*</span>
        <input type="text" placeholder="<fmt:message key="registration.name"/>" name="name"
               value="${name}" required="required">
    </div>
    <div>
        <span><i class="fa fa-caret-right" aria-hidden="true"></i>*</span>
        <input type="text" placeholder="<fmt:message key="registration.surname"/>" name="surame" value="${surame}"
               required="required">
    </div>

    <div>
        <span><i class="fa fa-mobile" aria-hidden="true"></i>*</span>
        <input type="text" placeholder="+380_ _ _ _ _ _ _ _" name="phone" value="${phone}" required="required">
    </div>

    <input class="submit-button" type="submit" value=<fmt:message key="sign.up"/>>
</form>

<c:forEach items="${errors}" var="item">
    <p class="text-danger"><fmt:message key="${item}"/></p>
    <br>
</c:forEach>


</body>
</html>