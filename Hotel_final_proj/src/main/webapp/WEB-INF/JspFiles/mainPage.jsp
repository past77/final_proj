<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
  Created by IntelliJ IDEA.
  User: ppolozhe
  Date: 5/27/19
  Time: 10:02 PM
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <%@include file="head.jsp" %>
</head>
<body>
<%@include file="header.jsp" %>
<div class="row ">
    <p class="welcome-title"><fmt:message key="greetings"/></p>
</div>

 <div class="footer">
 <div class = "foot">Copyright Paul Polozhevets
 </div></div>

</body>
</html>
