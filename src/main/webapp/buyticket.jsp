<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"    %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.net.*" %>

<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>
<l:setlocale/>

<fmt:setBundle basename="messages" />




<c:choose>
    <c:when test="${(currentUser.roleId != 1) and (currentUser.roleId != 2)}">
        <jsp:forward page="/error.jsp"></jsp:forward>
    </c:when>
    <c:otherwise>

<!DOCTYPE html>
<html lang="en">
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
    <link href="html/css/chosen.min.css" rel="stylesheet"/>
    <script src="html/js/jquery.min.js"></script>
    <script src="html/js/jquery-ui.min.js"></script>
    <script src="html/js/chosen.jquery.min.js"></script>

</head>
<body>

<div id="dialog-confirm" title="Remove station?">
</div>

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
                      <a href="login" class="login_btn">Login</a>
                   </c:if>
            </nav>
        </div>
    </header><!--  end header section  -->

    <section class="caption" id="caption_start">

            <!-- Trains list -->


    </section>

    	<section class="listings">
    		<div class="wrapper">




             <c:if test="${ticketStatus != null}">
                <span class="station_add">   <fmt:message key="${ticketStatus}" /> </span>
                <c:remove var="ticketStatus" scope="session"/>
             </c:if>

            <div class="buy_ticket_form">
                <div class="train_list_ticket add_new_way number_train_list">
                    <strong><fmt:message key="addway.trainenumber" /></strong><br/><br/> ${route.traineNumber}
                </div>
                <div class="train_list_ticket add_new_way number_train_list">
                    <strong><fmt:message key="addway.deptime" /></strong><br/><br/> ${route.dateStart}
                </div>
                <div class="train_list_ticket add_new_way number_train_list">
                    <strong><fmt:message key="addway.arrivaltime" /></strong><br/><br/> ${route.dateArrival}
                </div>
                <div class="train_list_ticket add_new_way number_train_list">
                    <strong><fmt:message key="addway.depstation" /></strong><br/></br>
                        <c:forEach items="${stationList}" var="station">
                           <c:choose>
                              <c:when test="${station.recordId == route.stationIdStart}">
                                <span class="station_name"> ${station.stationName}</span>
                              </c:when>
                              <c:otherwise>
                              </c:otherwise>
                           </c:choose>
                        </c:forEach>
                </div>
                <div class="train_list_ticket add_new_way number_train_list">
                     <strong><fmt:message key="addway.arrivalstation" /></strong><br/><br/>
                     <c:forEach items="${stationList}" var="station">
                        <c:choose>
                           <c:when test="${station.recordId == route.stationIdArrival}">
                              <span class="station_name">${station.stationName}</span>
                           </c:when>
                           <c:otherwise>
                           </c:otherwise>
                        </c:choose>
                     </c:forEach>
                </div>
                <div class="train_list_ticket add_new_way number_train_list">
                    <strong><fmt:message key="addway.ticketprice" /></strong><br/><br/> ${route.routePrice}
                </div>
                <c:if test="${buy_way == true}">
                    <div class="train_list_ticket add_new_way number_train_list">
                        <strong class="green_span"><fmt:message key="ticketcode" /> ${ticketCode} </strong>
                    </div>
                </c:if>

            </div>



                <c:if test="${buy_way != true}">
                <form action="controller" method="post" id="add_new_way_form">
                    <div class="class="search_fields">
                        <input type="submit" value="<fmt:message key="buyticket" />" id="add_new_way_submit" name="submit_search"/>
                        <input type="hidden" name="command" value="ticket">
                        <input type="hidden" name="route_id" value="${route.id}">
    				</div>
    			</form>
    			</c:if>

    		</div>
    	</section>	<!--  end listing section  -->

</section><!--  end hero section  -->



<footer>
    <div class="wrapper footer">

    </div>

    <div class="copyrights wrapper">
    </div>
</footer><!--  end footer  -->

</body>
</html>


    </c:otherwise>
</c:choose>
