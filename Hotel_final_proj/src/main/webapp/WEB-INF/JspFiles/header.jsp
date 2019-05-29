
<%--
  Created by IntelliJ IDEA.
  User: ppolozhe
  Date: 5/28/19
  Time: 2:40 AM
  To change this template use File | Settings | File Templates.
--%>
<div class="menu container-fluid">
    <div class="row">


        <div class="col-md-2 menu">
            <a href="/" class="lnk"><fmt:message key="main.page"/></a>
        </div>

        <div class="col-md-2 menu">
            <a href="/rooms" class="lnk"><fmt:message key="freeRooms.page"/></a>
        </div>


        <c:choose>
            <c:when test="${user == null}">

                <div class="col-md-2 menu">
                    <a href="/login"><fmt:message key="login.page"/></a>
                </div>

                <div class="col-md-2 menu">
                    <a href="/registration"><fmt:message key="registration.page"/></a>
                </div>

            </c:when>


            <c:when test="${user != null}">
                <div class="col-md-2 menu">
                    <a href="/profile"><fmt:message key="profile.page"/></a>
                </div>

                <div class="col-md-2 menu">
                    <p class="text">
                        <fmt:message key="header.logged"/>
                        <c:out value="${user.accounts.login}"/>
                    </p>
                </div>

                <div class="col-md-2 menu">
                    <a href="/logout"><fmt:message key="logout"/></a>
                </div>

            </c:when>

        </c:choose>


        <form method="get">
            <div class="col-md-2 menu">
                <div class="dropdown">
                    <button class="btn dropdown-toggle language-btn" type="button" data-toggle="dropdown"> Language
                    </button>
                    <button class="btn dropdown-toggle language-btn" data-toggle="dropdown"><span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a href="?language=en_US">English</a></li>
                        <li><a href="?language=uk_UA">Ukrainian</a></li>
                        <li><a href="?language=ru_RU">Russian</a></li>
                    </ul>
                </div>
            </div>
        </form>

    </div>

    <div class="row ">

        <div class="col-md-4">
        </div>

        <div class="col-md-4 ">
        </div> </div> </div>