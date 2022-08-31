<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"    %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>
<l:setlocale/>

<fmt:setBundle basename="messages" />

<!DOCTYPE html>
<html lang="en" xmlns:c="http://www.w3.org/1999/XSL/Transform" xmlns:jsp="http://www.w3.org/2001/XInclude">
<head>
    <title>Main rail trips</title>
    <meta charset="utf-8">
    <meta name="description" content="Life - is trip"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0" />

    <link rel="stylesheet" type="text/css" href="html/css/reset.css">
    <link rel="stylesheet" type="text/css" href="html/css/responsive.css">
    <link href="html/style.css" rel="stylesheet" type="text/css">

     <script type="text/javascript" src="html/js/jquery.js"></script>
     <script type="text/javascript" src="html/js/main.js"></script>
     <script type="text/javascript" src="html/js/ajaxservlet.js"></script>

     <link href="html/css/jquery-ui.min.css" rel="stylesheet"/>
     <script src="html/js/jquery.min.js"></script>
     <script src="html/js/jquery-ui.min.js"></script>
</head>
<body>

<section class="hero">
    <header>
        <div class="wrapper">
            <a href="#"><img src="img/logo.png" class="logo" alt="" titl=""/></a>
            <nav>
                   <div class="lang_uk_en">
                      <a href="language?lang=en">EN</a> |
                      <a href="language?lang=uk">UK</a>
                   </div>
                   <c:if test="${currentUser.login != null}">
                   <ul class="user_info">
                      <fmt:message key="hiuser" />, ${currentUser.login}!
                   </ul>
                    <c:if test="${currentUser.roleId == 1}">
                        <ul>
                            <li><a href="controller?command=showStations&pagerow=0&rows_on_page=5&sortby=id"><fmt:message key="editstationslist" /></a></li>
                            <li><a href="addnewway"><fmt:message key="createway" /></a></li>
                            <li><a href="controller"><fmt:message key="findyourroute" /></a></li>
                        </ul>
                    </c:if>
                   <ul class="logout_list">
                      <jsp:include page="logout.jsp" />
                   </ul>
                   </c:if>
                   <c:if test="${currentUser.login == null}">
                      <a href="login" class="login_btn"><fmt:message key="button.login" /></a>
                   </c:if>
            </nav>
        </div>
    </header><!--  end header section  -->

    <section class="search">
        <div class="wrapper">
            <form action="#" method="post">
                <input disabled type="text" id="search" name="search" placeholder="<fmt:message key="start.search.placeholder" />"  autocomplete="off"/>
                <input type="submit" id="submit_search" name="submit_search"/>
            </form>
            <a href="#" class="advanced_search_icon" id="advanced_search_btn"></a>
        </div>

        <div class="advanced_search" style="display:block;">
            <div class="wrapper">
                <span class="arrow"></span>
                <form action="controller" method="post">

                    <div class="search_fields">
                        <input type="date" value="<%= new java.util.Date() %>" class="float" id="check_in_date" name="check_in_date" placeholder="Check In Date"  autocomplete="off">

                        <hr class="field_sep float"/>

                        <input type="date" class="float" id="check_out_date" name="check_out_date" placeholder="Check Out Date"  autocomplete="off">
                    </div>
                    <div class="search_fields">
                        <input type="text"  class="float" id="start_station" name="start_station" placeholder="<fmt:message key="start.station" />"  autocomplete="off">

                        <hr class="field_sep float"/>

                        <input type="text" class="float" id="final_station" name="final_station" placeholder="<fmt:message key="finish.station" />"  autocomplete="off">
                    </div>
                    <input type="submit" value="<fmt:message key="search.find" />" id="submit_search_find" name="submit_search"/>
                    <input type="hidden" name="command" value="find_route">
                </form>
            </div>
        </div><!--  end advanced search section  -->
    </section><!--  end search section  -->

    <section class="caption">

    </section>
</section><!--  end hero section  -->

<footer>
    <div class="wrapper footer">

    </div>

    <div class="copyrights wrapper">
    </div>
</footer><!--  end footer  -->

</body>
</html>