        
        <script src="${pageContext.request.contextPath}/resources/js/core/jquery-2.1.4.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/jquery-migrate.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/jquery-ui.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/bootstrap.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/jquery.cokie.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/jquery.uniform.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/metronic.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/layout.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/quick-sidebar.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/quick-sidebar.js" type="text/javascript"></script>
        <script>
            jQuery(document).ready(function() {
                Metronic.init(); 
                Layout.init(); 
                QuickSidebar.init(); 
                $(document).on('click', '.sidebar-link', function (e){
                    $(".sidebar-link").removeClass("active");
                     var link = $(e.target);
                     link.addClass('active');
                });
            });
        </script>
        <script src="${pageContext.request.contextPath}/resources/js/core/angular.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/angular-sanitize.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/angular-route.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/trainee_script.js"></script>