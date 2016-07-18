
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
    if (user == null || !user.getUserType().getUserType().equals("admin")){
        response.sendRedirect("redirect.jsp");
    }       
%>
<html class="no-focus">
    <head>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon/alliance.ico"/>        
        <title>Admin - Home</title>        
        <jsp:include page="includes/admin/styles.jsp"/>                 
    </head>
    <body ng-app="ELearningApp">
    	<!-- Start Page Container -->        
        <div id="page-container" class="sidebar-l sidebar-o side-scroll header-navbar-fixed whole-page">            
           
            <jsp:include page="includes/admin/sidebar.jsp"/> 
            <jsp:include page="includes/admin/header.jsp"/>                  

            <!-- Main Container -->
            <main id="main-container">                                   
                <div class="row">                    
                </div>
                    <div>
                        <div ng-view></div>   
                    </div>                
            </main>           
            <!-- END Main Container -->

            <jsp:include page="includes/admin/footer.jsp"/> 

        </div>
        <!-- END Page Container -->        

        <jsp:include page="includes/admin/scripts.jsp"/>                                              
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
