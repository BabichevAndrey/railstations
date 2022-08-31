<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"    %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.net.*" %>

<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>
<l:setlocale/>

<fmt:setBundle basename="messages" />


<%

    Cookie addWayTrainId = new Cookie("add_way_train_id", request.getParameter("add_way_train_id"));

    addWayTrainId.setMaxAge(60*60*24);


    response.addCookie( addWayTrainId );


%>


<c:choose>
    <c:when test="${currentUser.roleId != 1}">
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
                      Hi, ${currentUser.login}!
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

             <c:if test="${addWayMessage != null}">
                <span class="station_add">   <fmt:message key="${addWayMessage}" /> </span>
                <c:remove var="addWayMessage" scope="session"/>
             </c:if>

            <form action="controller" method="get" id="add_new_way_form">
                 <ul class="properties_list" id="add_new_way">
                				<li class="add_new_way_li">
                                    <div class="train_list add_new_way number_train">
                                    <fmt:message key="addway.trainenumber" />
                                    </div>
                                    <div class="train_list add_new_way time_start_train">
                                    <fmt:message key="addway.deptime" />
                                    </div>
                                    <div class="train_list add_new_way arrive_train">
                                    <fmt:message key="addway.arrivaltime" />
                                    </div>
                                    <div class="train_list add_new_way start_station_train">
                                    <fmt:message key="addway.depstation" />
                                    </div>
                                    <div class="train_list add_new_way final_station_train">
                                    <fmt:message key="addway.arrivalstation" />
                                    </div>
                                    <div class="train_list add_new_way cost_station_train">
                                    <fmt:message key="addway.ticketprice" />
                                    </div>
                                    <div class="train_list add_new_way free_places_train">
                                    <fmt:message key="addway.freetickets" />
                                    </div>
                				</li>
                                 <li class="add_new_way_li">
                                        <div class="train_list add_new_way number_train_list">
                                            <input type="text" value="${route.traineNumber}" class="float" id="add_way_train_id" name="add_way_train_id" placeholder="Number" autocomplete="off">
                                        </div>
                                        <div class="train_list add_new_way time_start_train_list">
                                            <input type="datetime-local" value="${route.dateStart}" class="float" id="add_way_dep_time" name="add_way_dep_time" placeholder="" autocomplete="off">
                                        </div>
                                        <div class="train_list add_new_way arrive_train_list">
                                             <input type="datetime-local" value="${route.dateArrival}" class="float" id="add_way_arrival_time" name="add_way_arrival_time" placeholder="" autocomplete="off">
                                        </div>
                                        <div class="train_list add_new_way start_station_train_list">
                                            <select class="chosen" id="add_way_dep_station" name="add_way_dep_station">
                                                <c:forEach items="${stationList}" var="station">
                                                     <c:choose>
                                                         <c:when test="${station.recordId == route.stationIdStart}">
                                                             <option value="${station.recordId}" selected>${station.stationName}</option>
                                                         </c:when>
                                                         <c:otherwise>
                                                            <option value="${station.recordId}">${station.stationName}</option>
                                                         </c:otherwise>
                                                     </c:choose>
                                                      </c:forEach>
                                            </select>
                                        </div>
                                        <div class="train_list add_new_way final_station_train_list">
                                            <select class="chosen" id="add_way_arrival_station" name="add_way_arrival_station">
                                                <c:forEach items="${stationList}" var="station">
                                                     <c:choose>
                                                         <c:when test="${station.recordId == route.stationIdArrival}">
                                                             <option value="${station.recordId}" selected>${station.stationName}</option>
                                                         </c:when>
                                                         <c:otherwise>
                                                            <option value="${station.recordId}">${station.stationName}</option>
                                                         </c:otherwise>
                                                     </c:choose>

                                                </c:forEach>
                                            </select>

                                        </div>
                                        <div class="train_list add_new_way cost_station_train_list">
                                             <input type="text" value="${route.routePrice}" class="float" id="add_way_ticket_price" name="add_way_ticket_price" placeholder="Number" autocomplete="off">
                                        </div>
                                        <div class="train_list add_new_way free_places_train_list">
                                              <input type="text" value="${route.freePlaces}" class="float" id="add_way_ticket_free" name="add_way_ticket_free" placeholder="Number" autocomplete="off">
                                        </div>
                                 </li>
                            </ul>




                    <div class="class="search_fields"">
                        <input type="submit" value="<fmt:message key="updateroute" />" id="add_new_way_submit" name="submit_search"/>
                        <input type="hidden" name="command" value="edit_route">
                        <input type="hidden" name="route_id" value="${route.id}">
                        <input type="hidden" name="edit_way" value="true">
    				</div>
    			</form>

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
