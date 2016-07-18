<!DOCTYPE html>
<html lang="en">
    <head>
        <title>E-Learning - Login</title>
        <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/resources/css/mycss.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/resources/css/components-md.css" id="style_components" rel="stylesheet" type="text/css"/>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon/alliance.ico"/>        
    </head>
    <body class="page-md login">
        <div class="whole-page">
            <!-- BEGIN LOGO -->
            <div class="logo">
                <a href="index.html">
                <img height="200" width="450" src="${pageContext.request.contextPath}/resources/img/logos/logo_2.png" alt=""/>
                </a>
            </div>
            <!-- END LOGO -->

            <!-- BEGIN LOGIN -->
            <div class="content">
                <!-- BEGIN LOGIN FORM -->

                <form class="login-form" action="${pageContext.request.contextPath}/home" method="post">
                    <br/>
                    <h3 class="form-title">Sign In</h3>
                    <div><p class="error">${error}</p></div>
                    <div class="alert alert-danger display-hide">
                        <button class="close" data-close="alert"></button>
                        <span>
                        Enter any username and password. </span>
                    </div>
                    <div class="form-group">
                        <label class="control-label visible-ie8 visible-ie9">Username</label>
                        <input required class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="Username" name="username"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label visible-ie8 visible-ie9">Password</label>
                        <input required class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="Password" name="password"/>
                    </div>
                    <div class="form-actions">
                        <button type="submit" class="btn btn-success uppercase" style="width: 100%; background-color: #F44336;">Login</button>
                    </div>
                </form>
                <!-- END LOGIN FORM -->
            </div>
        </div>
    <script src="${pageContext.request.contextPath}/resources/js/core/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/core/bootstrap.min.js" type="text/javascript"></script>
    </body>
<!-- END BODY -->
</html>

<style type="text/css">
    .whole-page {
        display: none;
    }
</style>
<script type="text/javascript">
    $(document).ready(function(){
        $('.whole-page').show();
    });
</script>
<noscript>
    <p class="nojs-alert"><i class="fa fa-warning"></i>  Please enable javascript beyond this point.</p>
</noscript>