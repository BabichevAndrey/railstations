<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"    %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="/WEB-INF/stationTag.tld" prefix="st"%>


<l:setlocale/>

<fmt:setBundle basename="messages" />

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
    <script src="html/js/jquery.min.js"></script>
    <script src="html/js/jquery-ui.min.js"></script>

        <link href="html/css/jquery-ui.min.css" rel="stylesheet"/>
        <link href="html/css/chosen.min.css" rel="stylesheet"/>
        <script src="html/js/jquery.min.js"></script>
        <script src="html/js/jquery-ui.min.js"></script>
        <script src="html/js/chosen.jquery.min.js"></script>
</head>
<body>

<div id="dialog-confirm" title="<fmt:message key="station.removefromlist" />">
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

    <%@ include file="search.jspf" %>

    <section class="caption" id="caption_start">

            <!-- Trains list -->


    </section>

    	<section class="listings">
    		<div class="wrapper">

                 <c:if test="${addStationtationMessage != null}">
                     <span class="station_add">   <fmt:message key="${addStationtationMessage}" /> </span>
                     <c:remove var="addStationtationMessage" scope="session"/>
                </c:if>

                <form action="controller" method="post" id="add_station_form">
                    <div class="class="search_fields"">
                        <input type="text" value="" class="float" id="add_station" name="add_station"  placeholder="<fmt:message key="addnew.station" />"  autocomplete="off">

                        <input type="submit" value="<fmt:message key="button.addstation" />" id="add_new_station" name="submit_search"/>
                        <input type="hidden" name="command" value="add_station">
    				</div>
    			</form>

<div class="clear"></div>

<c:if test="${stationList != null}">

                			<div class="properties_list_st">
                				<div class="station_list_row_main">
                                    <div class="station_list station_input">
                                        #
                                    </div>
                                    <div class="station_list station_list_name" id="sort_station_links">

                                      <a href="controller?command=showStations&pagerow=${pagerow}&rows_on_page=${rowsOnPage}&sortby=nameup">
                                        <fmt:message key="stationname.list.up" />
                                      </a>
                                        <br/>
                                       <a href="controller?command=showStations&pagerow=${pagerow}&rows_on_page=${rowsOnPage}&sortby=namedown">
                                          <fmt:message key="stationname.list.down" />
                                       </a>
                                    </div>

                                    <div class="station_list station_list_time ">
                                        <fmt:message key="station.createdtime" />
                                    </div>
                				</div>
                				<div class="clear"></div>

                                <c:forEach items="${stationList}" var="station">



                                    <div class="station_list_row" id="st_row_${station.recordId}">
                                        <div class="station_list station_input">
                                            <input type="button" class="delete_station" id="st_${station.recordId}" value="<fmt:message key="station.delete" />"/>
                                        </div>
                                         <st:stationlistinfo a="${station.stationName}" time="${station.addTime}"/>
                                    </div>
                                <div class="clear"></div>


                                </c:forEach>

                            </div>

                            <form action="controller" method="get" id="pag_form">
                                <input type="hidden" name="command" value="showStations"/>
                                <input type="hidden" name="pagerow" id="pagerow" value="${pagerow}"/>
                                <c:if test="${stationName == null}">
                                    <input type="hidden" name="sortby" id="sortby" value="id"/>
                                </c:if>
                                <c:if test="${stationName != null}">
                                    <input type="hidden" name="sortby" id="sortby" value="${stationName}"/>
                                </c:if>

                                <select class="chosen rows_on_page" id="rows_on_page" name="rows_on_page">
                                    <option value="5" <c:if test="${rowsOnPage == 5}"> selected </c:if>>5</option>
                                    <option value="10" <c:if test="${rowsOnPage == 10}"> selected </c:if>>10</option>
                                    <option value="15" <c:if test="${rowsOnPage == 15}"> selected </c:if>>15</option>
                                    <option value="20" <c:if test="${rowsOnPage == 20}"> selected </c:if>>20</option>
                                </select>

                                <input type="submit" name="page" value="<fmt:message key="station.next" />" id="station_pag_next">
                                <input type="submit" name="page" value="<fmt:message key="station.previous" />" id="station_pag_pre">
                            </form>

                            </c:if>
<div class="clear"></div>









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
