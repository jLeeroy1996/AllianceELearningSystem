
        <!-- Scripts -->   
        <script src="${pageContext.request.contextPath}/resources/js/core/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/jquery.slimscroll.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/jquery.scrollLock.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/jquery.appear.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/jquery.countTo.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/jquery.placeholder.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/js.cookie.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/core/app.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/plugins/slick/slick.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/plugins/chartjs/Chart.min.js"></script>                
        <script src="${pageContext.request.contextPath}/resources/js/plugins/tinymce/bower_components/tinymce-dist/tinymce.min.js"></script>                        
        <script src="${pageContext.request.contextPath}/resources/js/core/angular.js"></script>        
        <script src="${pageContext.request.contextPath}/resources/js/plugins/tinymce/bower_components/angular-ui-tinymce/src/tinymce.js"></script>                
        <script src="${pageContext.request.contextPath}/resources/js/core/angular-route.min.js"></script>        
        <script src="${pageContext.request.contextPath}/resources/js/core/trainer_script.js"></script>                
        <script>
            $(document).ready(function(){
            $(document).on('click', '.side-bar-link', function (e){
                $(".side-bar-link").removeClass("active");
                 var link = $(e.target);
                 link.addClass('active');
            });                
        </script>
