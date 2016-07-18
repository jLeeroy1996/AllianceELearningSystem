<%-- 
    Document   : sidebar
    Created on : Sep 6, 2015, 12:40:54 PM
    Author     : Bryan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
            <!-- Sidebar -->
            <nav id="sidebar">
                <!-- Sidebar Scroll Container -->
                <div id="sidebar-scroll">
                    <!-- Sidebar Content -->
                    <div class="sidebar-content">
                        <!-- Side Header -->
                        <div class="side-header side-content bg-white-op">
                            <button class="btn btn-link text-gray pull-right hidden-md hidden-lg" type="button" data-toggle="layout" data-action="sidebar_close">
                                <i class="fa fa-times"></i>
                            </button>
                            <a class="text-white" href="admin">
                                <span class="h4 font-w600 sidebar-mini-hide">E-Learning System</span>
                            </a>
                        </div>
                        <!-- END Side Header -->

                        <!-- Side Content -->
                        <div class="side-content">
                            <ul class="nav-main">
                            	<li class="nav-main-heading"><span class="sidebar-mini-hide">Users</span></li>
                            	<li id="account-nav">
			                         <a class="nav-submenu" data-toggle="nav-submenu"><i class="fa fa-user-plus"></i><span class="sidebar-mini-hide">User Accounts</span></a>
			                         <ul>
			                             <li>
			                                 <a class="side-bar-link" href="#/add_user">Add User</a>
			                             </li>                             
			                             <li>
			                                 <a class="side-bar-link" href="#/manage_users">Manage Users</a>
			                             </li>                                                     
			                         </ul>
			                    </li>
			                    <li class="nav-main-heading"><span class="sidebar-mini-hide">Courses</span></li>
                            	<li id="course-nav">
                                    <a class="nav-submenu" data-toggle="nav-submenu" ><i class="si si-book-open"></i><span class="sidebar-mini-hide">Edit Courses</span></a>
                                    <ul>
                                        <li>
                                            <a class="side-bar-link" href="#/add_course">Add Course</a>
                                        </li>  
                                        <li>
                                            <a class="side-bar-link" href="#/add_chapter">Add Chapter</a>
                                        </li> 
                                        <li>
                                            <a class="side-bar-link" href="#/manage_courses">Manage Courses</a>
                                        </li>
                                        <li>
                                            <a class="side-bar-link" href="#/assign_trainees">Assign Course</a>
                                        </li>
                                        <li>
                                            <a class="side-bar-link" href="#/unassign_trainees">Unassign Course</a>
                                        </li>
                                        <li>
                                            <a class="side-bar-link" href="#/course_details">Course Details</a>
                                        </li>
                                    </ul>
                                </li>
                                <li class="nav-main-heading"><span class="sidebar-mini-hide">Exam</span></li>
                            	<li id="exam-nav">
                                    <a class="nav-submenu" data-toggle="nav-submenu"><i class="si si-note"></i><span class="sidebar-mini-hide">Manage Exams</span></a>
                                    <ul>
                                        <li>
                                            <a class="side-bar-link" href="make-exam">Add Exam</a>
                                        </li>
                                        <li>
                                            <a class="side-bar-link" href="#/manage_exam">Manage Exams</a>
                                        </li>   
                                        <li>
                                            <a class="side-bar-link" href="#/exam_results">Exam Results</a>
                                        </li>                                       
                                    </ul>
                                </li>
                                <li class="nav-main-heading"><span class="sidebar-mini-hide">Miscellaneous</span></li>
	                             <li>
	                                 <a class="side-bar-link" href="#/business_unit"><i class="fa fa-euro"></i><span class="sidebar-mini-hide"> Business Unit</span></a>
	                             </li>                             
	                             <li>
	                                 <a class="side-bar-link" href="#/course_category"><i class="si si-graduation"></i><span class="sidebar-mini-hide">Course Category</span></a>
	                             </li>                                        
                                </ul>
                        </div>
                        <!-- END Side Content -->
                    </div>
                    <!-- Sidebar Content -->
                </div>
                <!-- END Sidebar Scroll Container -->
            </nav>
            <!-- END Sidebar -->