<%-- 
    Document   : header
    Created on : Sep 6, 2015, 12:42:23 PM
    Author     : Bryan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
            <!-- Header -->
            <header id="header-navbar" class="content-mini content-mini-full">
                <!-- Header Navigation Right -->
                <ul class="nav-header pull-right">
                    <li>
                        <div class="btn-group">
                            <button class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" type="button">
                                ${loggedUser.getName()}&nbsp;&nbsp;&nbsp;
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-right">                            	
                            	<li class="dropdown-header">My Profile</li>
                                <li>
                                    <a tabindex="-1" href="#/update_profile">
                                        <i class="si si-settings pull-right"></i>Edit Profile
                                    </a>
                                </li>
                                 <li>
                                    <a tabindex="-1" href="#/change_password">
                                        <i class="si si-action-undo pull-right"></i>Change Password
                                    </a>
                                </li>
                                <li>
                                    <a tabindex="-1" href="trainee">
                                        <i class="si si-user pull-right"></i>Switch to Trainee
                                    </a>
                                </li>
                                <li class="divider"></li>
                                <li class="dropdown-header">Actions</li>
                                <li>
                                    <a tabindex="-1" href="logout">
                                        <i class="si si-logout pull-right"></i>Logout
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </li>
                </ul>
                <!-- END Header Navigation Right -->

                <!-- Header Navigation Left -->
                <ul class="nav-header pull-left">
                    <li class="hidden-md hidden-lg">
	                     <button class="btn btn-default" data-toggle="layout" data-action="sidebar_toggle" type="button">
	                         <i class="fa fa-navicon"></i>
	                     </button>
	                 </li>
                    <li class="hidden-xs hidden-sm">
                        <button class="btn btn-default" data-toggle="layout" data-action="sidebar_mini_toggle" type="button">
                            <i class="fa fa-ellipsis-v"></i>
                        </button>
                    </li>
                </ul>
                <!-- END Header Navigation Left -->
            </header>
            <!-- END Header -->
