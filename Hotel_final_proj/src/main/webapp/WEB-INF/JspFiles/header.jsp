
<%--
  Created by IntelliJ IDEA.
  User: ppolozhe
  Date: 5/28/19
  Time: 2:40 AM
  To change this template use File | Settings | File Templates.
--%>
<div class="menu container-fluid">
    <div class="row">
<ul class = "ule">

          <li>  <a href="/" class="lnk"><fmt:message key="main.page"/></a></li>
           <li> <a href="/rooms" class="lnk"><fmt:message key="freeRooms.page"/></a></li>

        <c:choose>
            <c:when test="${user == null}">

                   <li> <a href="/login"><fmt:message key="login.page"/></a><li>
                  <li>  <a href="/registration"><fmt:message key="registration.page"/></a><li>
            </c:when>
        <c:when test="${user != null}">

                  <li>  <a href="/profile"><fmt:message key="profile.page"/></a> </li>

                       <div class = "logged">
                    <li>    <fmt:message key="header.logged"/> </li>
                        <c:out value="${user.accounts.login}"/>
                        </div>
                        </ul>

                        <ul class="dropdown-menu ule2">
                 <li>  <a href="/logout"><fmt:message key="logout"/></a></li>
            </c:when>
        </c:choose>

    <form method="get">
                <li><a href=""><fmt:message key="language"/></a>
<ul>
                        <li><a href="?language=en_US">En</a></li>
                        <li><a href="?language=uk_UA">Ua</a></li>
                        <li><a href="?language=ru_RU">Ru</a></li>
                    </ul>

                    </li>

        </form>
</ul>
    </div>

    <div class="row ">

        <div class="col-md-4">
        </div>

        <div class="col-md-4 ">
        </div> </div> </div>