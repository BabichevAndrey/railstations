<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"    %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>
<l:setlocale/>

<fmt:setBundle basename="messages" />


<!DOCTYPE html>
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
<div id="dialog-confirm" title="<fmt:message key="routeremove" />">
</div>

<div id="dialog-confirm-done" title="">
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
                      <a href="login" class="login_btn"><fmt:message key="button.login" /></a>
                   </c:if>
            </nav>
        </div>
    </header><!--  end header section  -->



    <section class="caption" id="caption_start">

            <!-- Trains list -->


    </section>

    	<section class="listings">
    		<div class="wrapper">

    		<c:if test="${routeList != null}">

    			<ul class="properties_list">
    				<li>
                        <c:choose>
                          <c:when test="${currentUser.roleId == 1}">
                             <div class="train_list id_train">
                                #
                             </div>
                            </c:when>
                            <c:otherwise>
                               <div class="train_list id_train_list">
                               </div>
                            </c:otherwise>
                        </c:choose>
                        <div class="train_list number_train">
                        <fmt:message key="addway.trainenumber" />
                        </div>
                        <div class="train_list time_start_train">
                        <fmt:message key="addway.deptime" />
                        </div>
                        <div class="train_list arrive_train">
                        <fmt:message key="addway.arrivaltime" />
                        </div>
                        <div class="train_list trip_time">
                        <fmt:message key="triptime" />
                        </div>
                        <div class="train_list start_station_train">
                        <fmt:message key="addway.depstation" />
                        </div>
                        <div class="train_list final_station_train">
                        <fmt:message key="addway.arrivalstation" />
                        </div>
                        <div class="train_list cost_station_train">
                        <fmt:message key="addway.ticketprice" />
                        </div>
                        <div class="train_list free_places_train">
                        <fmt:message key="addway.freetickets" />
                        </div>

                        <c:if test="${currentUser != null}">
                            <div class="train_list buy_ticket_train">
                              <fmt:message key="buyticket" />
                            </div>
                        </c:if>
    				</li>


                    <c:forEach items="${routeList}" var="routes">
                        <li id="way_row_${routes.id}">

							<c:choose>
                                <c:when test="${currentUser.roleId == 1}">
                                    <div class="station_list station_input train_list id_train_list">
                                        <input type="button" class="delete_way" id="way_id_${routes.id}" value="<fmt:message key="station.delete" />"/>
                                        <a href="controller?command=edit_route&route_id=${routes.id}">
                                            <input type="button" class="edit_way" id="trip_id_${routes.id}" value="<fmt:message key="editroute" />"/>
                                        </a>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="train_list id_train_list">

                                    </div>
                                </c:otherwise>
                            </c:choose>



                            <div class="train_list number_train_list">
                                 ${routes.traineNumber}
                            </div>
                            <div class="train_list time_start_train_list">
                                ${routes.dateStart}
                            </div>
                            <div class="train_list arrive_train_list">
                                ${routes.dateArrival}
                            </div>
                            <div class="train_list trip_time">
                                ${routes.tripTime}
                            </div>
                            <div class="train_list start_station_train_list">
                                ${routes.stationStartName}
                            </div>
                            <div class="train_list final_station_train_list">
                                ${routes.stationArrivalName}
                            </div>
                            <div class="train_list cost_station_train_list">
                                ${routes.routePrice}
                            </div>
                            <div class="train_list free_places_train_list">
                                ${routes.freePlaces}
                            </div>
                            <c:if test="${currentUser != null}">
                                <div class="train_list buy_ticket_train_list">
                                     <a href = "controller?command=buy_ticket&route_id=${routes.id}"><fmt:message key="buyticket" /></a>
                                </div>
                            </c:if>

                        </li>
                    </c:forEach>


                </ul>
                </c:if>

    			<div class="more_listing">

    			</div>
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
