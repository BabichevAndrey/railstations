<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"    %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="l" tagdir="/WEB-INF/tags" %>
<l:setlocale/>

<fmt:setBundle basename="messages" />

<link href="resources/style.css" rel="stylesheet">


<form action="controller" method="post">
    <input type="hidden" name="command" value="logout">
    <input type="submit" value="<fmt:message key="logoutuser" />" id="sublog"><br/>
</form>