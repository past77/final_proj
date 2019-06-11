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
        <span><i aria-hidden="true"></i><fmt:message key="login.page"/> </span>
        <input type="text" placeholder="example@mail.com" name="login"  required="required">
    </div>
    <div>
        <span><i class="forminput"  aria-hidden="true"></i><fmt:message key="password"/> </span>
        <input type="password"  name="password" placeholder="********" required="required">
    </div>


    <div>
        <span><i class="forminput" aria-hidden="true"></i><fmt:message key="registration.name"/> </span>
        <input type="text" placeholder="<fmt:message key="registration.name"/>" name="name"
                required="required">
    </div>
    <div>
        <span><i class="forminput"  aria-hidden="true"></i><fmt:message key="registration.surname"/> </span>
        <input type="text" placeholder="<fmt:message key="registration.surname"/>" name="surname" value="${surname}"
               required="required">
    </div>

    <div>
        <span><i class="forminput"  aria-hidden="true"></i><fmt:message key="registration.phone"/></span>
        <input type="text" placeholder="+380_ _ _ _ _ _ _ _" name="phone" value="${phone}" required="required">
    </div>
 <div>
    <input class="submit-button" type="submit" value=<fmt:message key="sign.up"/>
    </div>
</form>

    <c:choose>
    <c:when test="${success != null}">
        <fmt:message key="message.complete"/>
    </c:when>
     </c:choose>

<c:forEach items="${errors}" var="item">
    <p class="text-danger"><fmt:message key="${item}"/></p>
    <br>
</c:forEach>


</body>
</html>