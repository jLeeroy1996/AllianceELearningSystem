
<%@page import="com.aes.model.User"%>
<div class="page-header md-shadow-z-1-i navbar navbar-fixed-top">
            <!-- BEGIN HEADER INNER -->
            <div class="page-header-inner container">
                <!-- BEGIN LOGO -->
                <div class="page-logo">
                    <a href="trainee">
                        <h3 class="logo-default white-text">ALLIANCE</h3>
                    </a>
                    <div class="menu-toggler sidebar-toggler">
                    </div>
                </div>
                <!-- END LOGO -->
                <!-- BEGIN RESPONSIVE MENU TOGGLER -->
                <a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">
                </a>
                <!-- END RESPONSIVE MENU TOGGLER -->
                <!-- BEGIN PAGE TOP -->
                <div class="page-top">
                    <div class="top-menu">
                        <ul class="nav navbar-nav pull-right">          
                            <!-- BEGIN USER LOGIN DROPDOWN -->
                            <li class="dropdown dropdown-user">
                                <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                                    <span class="username username-hide-on-mobile">${loggedUser.getName()}</span>
                                    <i class="fa fa-angle-down"></i>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-default">
                                <%
                                    User user = (User)session.getAttribute("loggedUser");
                                    if (user.getUserType().getUserType().equals("admin")){
                                %>
                                    <li>
                                        <a href="admin">
                                        <i class="si si-shuffle"></i> Switch to Admin</a>
                                    </li>
                                <%
                                    } else if (user.getUserType().getUserType().equals("trainer")) {
                                %>
                                    <li>
                                        <a href="trainer">
                                        <i class="si si-shuffle"></i> Switch to Trainer</a>
                                    </li>                                
                                <%
                                    }
                                %>
                                    <li>
                                        <a href="logout"><i class="si si-logout"></i> Logout</a>
                                    </li>
                                </ul>
                            </li>                            
                            <!-- END USER LOGIN DROPDOWN -->
                        </ul>
                    </div>
                    <!-- END TOP NAVIGATION MENU -->
                </div>
                <!-- END PAGE TOP -->
            </div>
            <!-- END HEADER INNER -->
        </div>