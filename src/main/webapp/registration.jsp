<%@ page contentType="text/html; charset=UTF-8" %>
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
                <a href="login" class="login_btn"><fmt:message key="button.login" /></a>
            </nav>
        </div>
    </header><!--  end header section  -->

    <section class="caption" id="caption_start">

        <div class="start_form">
            <h2 class="caption_start"><fmt:message key="registration.input.info" /> </h2>

             <c:if test="${errorMessage != null}">
                <span class="error_message">   <fmt:message key="${errorMessage}" /> </span>
                <c:remove var="errorMessage" scope="session"/>
             </c:if>

            <form action="controller" method="post" class="registation_form">
                <input type="hidden" name="command" value="registration">
                  <%  if( request.getSession().getAttribute("login_reg") == null){ %>
                     <input name="login" value="" placeholder="<fmt:message key="button.login" />"><br/>
                  <%  } else {  %>
                     <input name="login" value="<%= request.getSession().getAttribute("login_reg")  %>" placeholder="<fmt:message key="button.login" />"><br/>
                  <%  }   %>

                   <%  if( request.getSession().getAttribute("name_reg") == null){ %>
                      <input type="text" name="name" value="" placeholder="<fmt:message key="registration.name" />"><br/>
                   <%  } else {  %>
                   <input type="text" name="name" value="<%= request.getSession().getAttribute("name_reg")  %>" placeholder="<fmt:message key="registration.name" />"><br/>
                   <%  }   %>

                   <%  if( request.getSession().getAttribute("secondName_reg") == null){ %>
                       <input type="text" name="second_name" value="" placeholder="<fmt:message key="registration.secondname" />"><br/>
                   <%  } else {  %>
                   <input type="text" name="second_name" value="<%= request.getSession().getAttribute("secondName_reg")  %>" placeholder="<fmt:message key="registration.secondname" />"><br/>
                   <%  }   %>

                   <%  if( request.getSession().getAttribute("email_reg") == null){ %>
                       <input type="text" name="email" value="" placeholder="<fmt:message key="registration.email" />"><br/>
                   <%  } else {  %>
                        <input type="text" name="email" value="<%= request.getSession().getAttribute("email_reg")  %>" placeholder="<fmt:message key="registration.email" />"><br/>
                   <%  }   %>

                   <%  if( request.getSession().getAttribute("password1_reg") == null){ %>
                       <input type="password" name="password1" value="" placeholder="<fmt:message key="registration.password" />"><br/>
                   <%  } else {  %>
                        <input type="password" name="password1" value="<%= request.getSession().getAttribute("password1_reg")  %>" placeholder="<fmt:message key="registration.password" />"><br/>
                   <%  }   %>

                   <%  if( request.getSession().getAttribute("password2_reg") == null){ %>
                       <input type="password" name="password2" value="" placeholder="<fmt:message key="registration.password" />"><br/>
                   <%  } else {  %>
                        <input type="password" name="password2" value="<%= request.getSession().getAttribute("password2_reg")  %>" placeholder="<fmt:message key="registration.password" />"><br/>
                   <%  }   %>

                <input type="text" name="captcha" value="" placeholder="<fmt:message key="password.captcha" />"><br/>
                <img src="captcha-image.jpg"><br/>
                <input type="submit" value="<fmt:message key="registration.in" />" id="sublog"><br/>
            </form>
        </div>
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
