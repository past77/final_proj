<%--
  Created by IntelliJ IDEA.
  User: ppolozhe
  Date: 5/28/19
  Time: 1:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%--<c:set var="language" value="--%>
<%--${not empty param.language ? param.language : not empty language ?--%>
<%--language : pageContext.request.locale}" scope="session" />--%>
<%--c:set var="language" scope="session"
       value="${empty sessionScope.locale ? 'en_US' : sessionScope.locale}" />--%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language :
pageContext.request.locale}" scope="session" />

<fmt:setLocale value="${language}" />
<fmt:setBundle basename="lang" />
<link rel="stylesheet" type="text/css" href="/resources/css/style.css">
<title><fmt:message key="hotel"/></title>