<%@page import="com.aes.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    response.setHeader("Cache-Control","no-store, must-revalidate");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader("Expires", -1);
    
    User user = (User)session.getAttribute("loggedUser");
    if (user == null){
        response.sendRedirect("redirect.jsp");
    }       
%>


<html lang="en" ng-app="app">
    <head>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon/alliance.ico"/>        
        <title>Trainee - Home</title>
        <!-- BEGIN PAGE STYLES -->
        <jsp:include page="includes/trainee/styles.jsp"/>
        <!-- END PAGE STYLES -->
    </head>
    <body class="page-md page-boxed page-header-fixed page-container-bg-solid page-sidebar-closed-hide-logo" ng-controller="DefaultController">
        <div class="whole-page">
            <!-- BEGIN HEADER -->
            <jsp:include page="includes/trainee/header.jsp"/>
            <!-- END HEADER -->

            <div class="clearfix">
            </div>

            <!-- BEGIN CONTAINER -->
            <div class="container">
                <div class="page-container">

                    <!-- BEGIN SIDEBAR -->
                    <jsp:include page="includes/trainee/sidebar.jsp"/>
                    <!-- END SIDEBAR -->

                    <!-- BEGIN CONTENT -->
                    <div class="page-content-wrapper">
                        <div class="page-content" ng-view>
                        </div>
                    </div>
                    <!-- END CONTENT -->
                </div>

            </div>        
            <!-- BEGIN PAGE LEVEL SCRIPTS -->
                <jsp:include page="includes/trainee/scripts.jsp"/>             
            <!-- END PAGE LEVEL SCRIPTS -->
        </div>        
    </body>    
</html>
<script type="text/javascript">
    $(document).ready(function(){
        $('.whole-page').show();
    });
</script>
<noscript>
    <p class="nojs-alert"><i class="fa fa-warning"></i>  Please enable javascript beyond this point.</p>
</noscript>