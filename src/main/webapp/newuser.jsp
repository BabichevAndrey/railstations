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
            <h2 class="caption_start"><fmt:message key="newuser.done" /> </h2>

              <a href="login"><fmt:message key="button.login" /></a>


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
