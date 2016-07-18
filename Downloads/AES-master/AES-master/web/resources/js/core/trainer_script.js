(function() {
	'use strict';       
        
        var Utils = {
            appRedirectTo: function (absoluteAppUrl) {
                window.location = window.location.href.split('#')[0] + '#' + absoluteAppUrl;
            },
            redirectTo: function (absoluteUrl) {
                window.location = window.location.origin + absoluteAppUrl;
            }
	};
        
        var app = angular.module('ELearningApp', ['ui.tinymce', 'ngRoute']);
        
        app.directive('fileModel', ['$parse', function ($parse) {
            return {
                restrict: 'A',
                link: function(scope, element, attrs) {
                    var model = $parse(attrs.fileModel);
                    var modelSetter = model.assign;

                    element.bind('change', function(){
                        scope.$apply(function(){
                            modelSetter(scope, element[0].files[0]);
                        });
                    });
                }
            };
        }]);
    
        app.service('fileUpload', ['$http', function ($http) {
            this.uploadFileToUrl = function(file, uploadUrl, id, admin, $scope){
                var fd = new FormData();
                fd.append('file', file);
                $http.post(uploadUrl, fd, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                })
                .success(function(response){
                    if (response.error.length == 0) {
                        var data = {
                            "id": id.toString(),
                            "admin": admin,
                            "path": response.path,
                            "name": response.name
                        };
                        $http.post('post/presentation', data).success(function(response){
                            if (response.error == ""){
                                $('#file-input').val("");
                                $scope.getPresentations($scope.presentationChapter);
                                $scope.modalSuccessMessage = "Upload success!";
                            } else {
                                $scope.modalErrorMessage = response.error;
                            }
                        }).error(function(response){
                            $scope.modalErrorMessage = ("Unable to upload file. AJAX error.");
                        });
                    } else {
                        $scope.modalErrorMessage = (response.error);
                    }
                })
                .error(function(response){
                });
            };
        }]);
    
        app.config( ['$routeProvider', function($routeProvider) {
            $routeProvider
                .when('/add_user', {
                    templateUrl: 'templates/trainer/add_user.html',
                    controller: 'AddUserController'
                })                
                .when('/manage_users', {
                    templateUrl: 'templates/trainer/manage_users.html',
                    controller: 'ManageUserController'
                })
                .when('/add_course', {
                    templateUrl: 'templates/trainer/add_course.html', 
                    controller: 'AddCourseController'
                })
                .when('/manage_courses', {
                    templateUrl: 'templates/trainer/manage_courses.html',
                    controller: 'ManageCourseController'
                })
                .when('/manage_exam', {
                    templateUrl: 'templates/trainer/manage_exam.html',
                    controller: 'ManageExamController'
                })
                .when('/exam_results', {
                    templateUrl: 'templates/trainer/exam_results.html',
                    controller: 'ExamResultsController'
            
                })
                .when('/business_unit', {
                    templateUrl: 'templates/trainer/business_unit.html',
                    controller: 'ManageBusinessUnitController'
                })
                .when('/course_category', {
                    templateUrl: 'templates/trainer/course_category.html',
                    controller: 'ManageCategoryController'
                })
                .when('/change_password', {
                    templateUrl: 'templates/trainer/change_password.html',
                    controller: 'ChangePasswordController'
                })
                .when('/update_profile', {
                    templateUrl: 'templates/trainer/update_profile.html',
                    controller: 'UpdateProfileController'
                })
                .when('/assign_trainees', {
                    templateUrl: 'templates/trainer/assign_trainees.html',
                    controller: 'AssignTraineesController'
                })
                .when('/unassign_trainees', {
                    templateUrl: 'templates/trainer/unassign_trainees.html',
                    controller: 'UnassignTraineesController'
                })                
                .when('/course_details/', {
                    templateUrl: 'templates/trainer/course_details.html',
                    controller: 'CourseDetailsController'
                })
                .when('/add_chapter', {
                    templateUrl: 'templates/trainer/add_chapter.html',
                    controller: 'AddChapterController'
                })
                .otherwise({
                    redirectTo: '/manage_courses'
                });
        }]);
    
        app.controller('DefaultController', ['$scope', function($scope) {
            $scope.navigateTo = function (absoluteAppUrl) {
                Utils.appRedirectTo(absoluteAppUrl);                                
            };
	}]);
    
        app.controller('AddChapterController', ['$scope', '$routeParams', '$http', function($scope, $routeParams, $http) { 
            
            $scope.tinymceOptions = {
                plugins: 'print textcolor',
                toolbar: "undo redo styleselect formatselect fontselect fontsizeselect forecolor backcolor bullist numlist",
                height : 500
            };
            
            $scope.getCredentials = function () {
                $http.get('get/logged_user')
                .success(function (data) {
                    var user = jQuery.parseJSON(data.user);
                    $scope.admin = user.userName;
                }).error(function(response){
                    alert("AJAX Error");
                });
            };
            
            $scope.clearMessages = function () {
                $scope.successMessage = "";
                $scope.errorMessage = "";
            };
            
            $scope.confirmAddChapter = function () {
                $scope.clearMessages();
                $('#confirm-add-chapter').modal('show');
            };
            
            $scope.addChapter = function () {
                $scope.clearMessages();
                if ($scope.courseSelected != 0) {
                    var data = {                        
                        "number": $scope.number.toString(),
                        "title" : $scope.title,
                        "content": $scope.content,
                        "course": $scope.courseSelected.toString(),
                        "admin": $scope.admin
                    }; 
                    $http.post('post/chapter', data).success(function(response){
                        if (response.error == ""){
                            $scope.successMessage = ('Chapter added successfully.');
                            window.location.href = "trainer#/course_details?id=" + data.course;
                        } else {
                            $scope.errorMessage = (response.error);
                        }
                    }).error(function(response){
                        $scope.errorMessage = ("Unable to add chapter. AJAX error.");
                    });
                } else {
                    $scope.errorMessage = ("Please enter course.");
                }
                $('#confirm-add-chapter').modal('hide'); 
            };
            
            $scope.getCourses = function () {
                $http.get('get/courses').success(function(response){
                       $scope.courses = jQuery.parseJSON(response.courses);                       
                    }).error(function(response){
                        alert("AJAX Error");
                });
            };
            
            $scope.courseSelected = 0;
            
            $scope.init = function () {                 
                $scope.getCourses();
                $scope.getCredentials();
                if (typeof $routeParams.id !== 'undefined'){                    
                    $scope.courseSelected = $routeParams.id;         
                }
            };
            
            $scope.init();
                        
	}]);
    
    
        app.controller('CourseDetailsController', ['$scope', '$routeParams', '$http', 'fileUpload', function($scope, $routeParams, $http, fileUpload) {                                    
            
            $scope.orderByField = 'number';
            $scope.reverseSort = false;
            
            $scope.orderByField2 = 'dateCreated';
            $scope.reverseSort2 = false; 
            
            $scope.goToAddExam = function () {
                window.location.href = 'make-exam';
            };
                
            $scope.tinymceOptions = {
                plugins: 'print textcolor',
                toolbar: "undo redo styleselect formatselect fontselect fontsizeselect forecolor backcolor bullist numlist",
                height : 500
            };
            
            $scope.editExam = function (id){
                window.location.href = "edit-exam?id=" + id;
            };
            
            $scope.getCredentials = function () {
                $http.get('get/logged_user')
                .success(function (data) {
                    var user = jQuery.parseJSON(data.user);
                    $scope.admin = user.userName;
                }).error(function(response){
                    alert("AJAX Error");
                });
            };
            
            $scope.confirmDeleteExam = function (id) {
                $scope.clearMessages();
                $scope.examForDelete = id;
                $('#confirm-delete-exam').modal('show');
            };
            
            $scope.deleteExam = function () {
                $scope.clearMessages();
                $http.post('delete/exam', {"id": $scope.examForDelete.toString()}).success(function(response){
                    if (response.error == ""){
                        $scope.successMessage = ("Exam deleted successfully.");
                        $scope.getCourse();
                    } else {
                        $scope.errorMessage = (response.error);
                    }
                }).error(function(response){
                    $scope.errorMessage =  ("Unable to delete exam. Ajax error.");
                });
                $('#confirm-delete-exam').modal('hide');
            };
            
                
            $scope.getCourse = function () {
                $http.get('get/course/' + $scope.courseSelected).success(function(response){
                    $scope.course = jQuery.parseJSON(response.course);
                    $scope.title = $scope.course.courseTitle;
                    $scope.description = $scope.course.description;
                    $scope.categorySelected = $scope.course.courseCategory.id.toString();
                    $scope.trainerSelected = $scope.course.user.userName;
                    $scope.getChapters($scope.course.id);
                }).error(function(response){
                    alert("AJAX Error");
                });
                $http.get('get/course/exams/'+$scope.courseSelected).success(function(response){
                    $scope.exams = jQuery.parseJSON(response.exams);
                }).error(function(response){
                    alert("AJAX Error");    
                }) ;
            };
            
            $scope.confirmUploadFile = function () {
                $scope.clearMessages();
                $('#view-presentations-modal').modal('hide');
                $('#confirm-upload-presentation').modal('show');
            };
            
            $scope.uploadFile = function(){
                $scope.clearMessages();
                var file = $scope.myFile;
                var uploadUrl = "upload/file";
                fileUpload.uploadFileToUrl(file, uploadUrl, $scope.presentationChapter, $scope.admin, $scope);
                $scope.getPresentations($scope.presentationChapter);
                $scope.viewPresentationModal();
            };
            
            $scope.viewChapter = function (id) {
                $scope.clearMessages();
                $scope.chapterId = id;
                $scope.getPresentations(id);
                $http.get('get/chapter/' + id).success(function(response){
                    if (response.error == ""){                        
                        $('#update-chapter-modal').modal('show');                        
                        var chapter = $.parseJSON(response.chapter);                        
                        $scope.chapterSelected = {
                                number:chapter.number,
                                title:chapter.title,
                                content:chapter.content
                            };
                        tinymce.activeEditor.setContent(chapter.content); 
                    } else {
                        $scope.errorMessage = (response.error);
                    }
                }).error(function(response){
                     $scope.errorMessage = ("Unable to fetch chapter. AJAX error.");
                });
            };
            
            $scope.confirmUpdateChapter = function () {
                $scope.clearMessages();
                $('#update-chapter-modal').modal('hide');
                $('#confirm-update-chapter').modal('show');
            };
            
            $scope.showUpdateChapterModal = function () {
                $scope.clearMessages();
                $('#confirm-update-chapter').modal('hide');
                $('#update-chapter-modal').modal('show');
            };
            
            $scope.updateChapter = function () {       
                $scope.clearMessages();
                var data = {    
                    "id": $scope.chapterId.toString(),
                    "number": $scope.chapterSelected.number.toString(),
                    "title" : $scope.chapterSelected.title,
                    "content": $scope.chapterSelected.content,
                    "course": $scope.course.id.toString(),
                    "admin": $scope.admin
                }; 
                $http.post('put/chapter', data).success(function(response){
                    if (response.error == ""){                            
                        $scope.getCourse();
                        $scope.successMessage = ('Chapter was successfully updated.');
                    } else {
                        $scope.errorMessage = (response.error);
                    }
                }).error(function(response){
                    $scope.errorMessage = ("Unable to update chapter. AJAX error.");
                });
                $('#confirm-update-chapter').modal('hide');
                $('#update-chapter-modal').modal('hide');
            };
            
            $scope.getCourses = function () {
                $http.get('get/courses').success(function(response){
                       $scope.courses = jQuery.parseJSON(response.courses);                       
                    }).error(function(response){
                        alert("AJAX Error");
                });
            };
            
            $scope.getTrainers = function () {
                $http.get('get/trainers').success(function(response){
                    if (response.error == ""){
                        $scope.trainers = jQuery.parseJSON(response.trainers);
                    } else {
                        alert (response.error);
                    }
                }).error(function(response){
                    alert ("Unable to fetch trainers. AJAX error.");
                });                                
            };
            
            $scope.getCategories = function () {
                $http.get('get/categories')
                    .success(function(response){
                        if (response.error == ""){
                            $scope.categories = jQuery.parseJSON(response.categories);
                        } else {
                            alert(response.error);
                        }
                    })
                    .error(function(response){
                        alert("Unable to fetch categories. AJAX error.");
                    });
            };
            
            $scope.clearMessages = function () {
                $scope.errorMessage = '';
                $scope.successMessage = '';
                $scope.modalErrorMessage = '';
                $scope.modalSuccessMessage = '';
            };
            
            $scope.showUpdateCourseModal = function (id) {
                $scope.clearMessages();
                $('#update-course-modal').modal('show');                
                $scope.courseId = id.toString();
                $scope.getCourse();
            };
            
            $scope.confirmUpdateCourse = function () {
                $scope.clearMessages();
                $('#update-course-modal').modal('hide');
                $('#confirm-update-course').modal('show');
            };
            
            $scope.showUpdateCourseModal2 = function () {
                $('#update-course-modal').modal('show');
                $('#confirm-update-course').modal('hide');
            };
            
            $scope.updateCourse = function () { 
                $scope.clearMessages();
                var data = {                    
                    id: $scope.courseId.toString(),
                    title: $scope.title,
                    description: $scope.description,
                    category: $scope.categorySelected,
                    trainer: $scope.trainerSelected,
                    admin: $scope.admin
                };
                data = JSON.stringify(data);
                $http.post('put/course', data).success(function(response){
                    if (response.error == ""){
                        $scope.getCourses();
                        $scope.getCourse();
                        $scope.successMessage = ("Course was successfully updated.");
                    } else {
                        $scope.errorMessage = (response.error);
                    }
                }).error(function(response){
                    $scope.errorMessage = ('Unable to update user. AJAX error.');
                });
                $('#update-course-modal').modal('hide');
                $('#confirm-update-course').modal('hide');
            };
            
            $scope.getChapters = function (chapterId) {
                $http.get('get/chapters/' + chapterId).success(function(response){
                       $scope.chapters = jQuery.parseJSON(response.chapters);                       
                    }).error(function(response){
                        alert("AJAX Error");
                });
            };
            
            $scope.getPresentations = function (id) {
                $http.get('get/chapter/presentations/' + id).success(function(response){
                    if (response.error == ""){
                        $scope.presentations = jQuery.parseJSON(response.presentations);
                    } else {
                        alert (response.error);
                    }
                }).error(function(response){
                    alert ("Unable to fetch presentations. AJAX error.");
                });
            };
            
            $scope.confirmDeleteChapter = function (id) {
                $scope.clearMessages();
                $scope.chapterForDelete = id;
                $('#confirm-delete-chapter').modal('show');
            };
            
            $scope.deleteChapter = function () {
                $scope.clearMessages();
                $http.post('delete/chapter', {"id": $scope.chapterForDelete.toString()}).success(function(response){
                    if (response.error == ""){
                        $scope.getCourse();
                        $scope.successMessage = ('Chapter was successfully deleted.');
                    } else {
                        $scope.errorMessage = (response.error);
                    }
                }).error(function(response){
                    $scope.errorMessage = ("Unable to delete chapter. AJAX error.");
                });
                $('#confirm-delete-chapter').modal('hide');
            };
            
            $scope.goToAddChapter = function (id) {
                window.location.href = "trainer#/add_chapter?id=" + id;
            };
            
            $scope.courseSelected = 0;
            
            $scope.viewPresentations = function (id) {       
                $scope.clearMessages();
                $('#view-presentations-modal').modal('show');
                $scope.getPresentations(id);
                $scope.presentationChapter = id;
            }; 
            
            $scope.confirmDeletePresentation = function (id){
                $scope.presentationForDelete = id;
                $scope.clearMessages();
                $('#view-presentations-modal').modal('hide');
                $('#confirm-delete-presentation').modal('show');
            };
            
            $scope.viewPresentationModal = function () {
                $scope.clearMessages();
                $('#confirm-delete-presentation').modal('hide');
                $('#confirm-upload-presentation').modal('hide');
                $('#view-presentations-modal').modal('show');
            };
            
            $scope.deletePresentation = function (id) {
                $scope.clearMessages();
                $http.post('delete/presentation', {"id": $scope.presentationForDelete.toString()}).success(function(response){
                    if (response.error == ""){
                        $scope.getPresentations($scope.presentationChapter);
                        $scope.modalSuccessMessage = ("File deleted successfully.");
                    } else {
                        $scope.modalErrorMessage = (response.error);
                    }
                }).error(function(response){
                    $scope.modalErrorMessage = ("Unable to delete file. AJAX error.");
                });
                $('#confirm-delete-presentation').modal('hide');
                $('#view-presentations-modal').modal('show');
            };
            
            $scope.init = function () {                 
                $scope.getCourses();
                $scope.getTrainers();
                $scope.getCategories();
                $scope.getCredentials();
                if (typeof $routeParams.id !== 'undefined'){                    
                    $scope.courseSelected = $routeParams.id;
                    $scope.getCourse();                    
                }
            };
            
            $scope.init();            
	}]);
    
        app.controller('AssignTraineesController', ['$scope', '$http', function($scope, $http) {
            $scope.orderByField = 'name';
            $scope.reverseSort = false;
                
            $scope.getCredentials = function () {
                $http.get('get/logged_user')
                .success(function (data) {
                    var user = jQuery.parseJSON(data.user);
                    $scope.admin_username = user.userName;
                }).error(function(response){
                    alert("AJAX Error");
                });
            };
            
            $scope.clearMessages = function () {
                $scope.successMessage = "";
                $scope.errorMessage = "";
            };
            
            $scope.getAllUsers = function () {
                $http.get('get/users')
                    .success(function(response){
                       $scope.traineesMain = jQuery.parseJSON(response.users);
                    }).error(function(response){
                        alert("AJAX Error");
                    });
            };                        
            
            $scope.getCourses = function () {
                $http.get('get/courses').success(function(response){
                    if (response.error == ""){
                        $scope.courses = jQuery.parseJSON(response.courses);                            
                    } else {
                        alert(response.error);
                    }
                }).error(function(response){
                    alert("AJAX Error");
                });
            };
            
            $scope.updateTrainees = function (){
                $http.get('get/course/trainees/' + $scope.courseSelected).success(function(response){
                    if (response.error == ""){
                        var trainees = jQuery.parseJSON(response.trainees);
                        $scope.getUnassigned($scope.traineesMain, trainees);
                    } else {
                        alert (response.error);
                    }
                }).error(function(response){
                    alert ('Unable to retrieve data. Ajax error.');
                });
            };
            
            $scope.getUnassigned = function (allTrainees, assignedTrainees) {        		
                var newList = [];        		
                for (var i = 0; i < allTrainees.length; i++) {
                    var flag = true;
                    for (var j = 0; j < assignedTrainees.length; j++){        				        		
                        if(allTrainees[i].userName == assignedTrainees[j].userName){
                            flag = false;
                            break;
                        }        				
                    }        		
                    if (flag){
                        newList.push(allTrainees[i]);
                    }
                }
                $scope.trainees = newList;
                $scope.mainUnassigned = newList;
            };
            
            $scope.selectAllUnassigned = function () {
                if ($('input#selectAllUnassigned').prop('checked')){
                    $('input.trainee').prop('checked', true);
                } else {
                    $('input.trainee').prop('checked', false);
                }
            };
            
            $scope.searchTrainees = function () {        		
                $scope.search($scope.searchName, $scope.mainUnassigned);
            };
            
            $scope.search = function searchStringInArray (name, trainees) {        		
                var newList = [];        		
                for (var i = 0; i < trainees.length; i++) {
                    if (trainees[i].name.match(new RegExp(name, "i"))){
                        newList.push(trainees[i]);
                    }
                }
                $scope.trainees = newList;
            };
            
            $scope.confirmAssignTrainees = function () {
                $scope.clearMessages();
                $('#confirm-assign-trainees').modal('show');
            };
            
            $scope.assignTrainees = function () {
                $scope.clearMessages();
                $('#assign-trainees-btn').attr('disabled', 'disabled');
                var trainees = $('#unassigned-trainees-table').find('input[type="checkbox"]:checked').not('#selectAllUnassigned').map(function() {		
                    return $(this).val();
                }).get();			
                var courseId = $scope.courseSelected;
                if (courseId == '' || (typeof courseId === "undefined")){
                    $scope.errorMessage = ('Please choose a course first.');                        
                } else if (trainees.length) {  
                    var data = {'trainees': JSON.stringify(trainees), 'course': courseId.toString(), 'admin': $scope.admin_username};
                    $http.post('assign/batch/course', data)
                        .success(function(response){
                            if (response.error == "") {
                                $scope.successMessage = ("Trainees added successfully.");                                    
                                $scope.updateTrainees();
                            } else {
                                $scope.errorMessage = (response.error);
                            }                                
                            $('#assign-trainees-btn').prop('disabled', false);
                        }).error(function(response){
                            $scope.errorMessage = ("Unable to add trainees. Ajax error.");
                            $('#assign-trainees-btn').prop('disabled', false);
                        });                            
                } else {
                    $scope.errorMessage = ('Please select a student first.');
                    $('#assign-trainees-btn').prop('disabled', false);
                }
                $('#assign-trainees-btn').prop('disabled', false); 
                $('#confirm-assign-trainees').modal('hide');
            };
                   
            $scope.getCredentials();    
            $scope.getCourses();
            $scope.getAllUsers();
            
	}]);
    
        app.controller('UnassignTraineesController', ['$scope', '$http', function($scope, $http) {
            $scope.orderByField = 'name';
            $scope.reverseSort = false;
                
            $scope.getCredentials = function () {
                $http.get('get/logged_user')
                .success(function (data) {
                    var user = jQuery.parseJSON(data.user);
                    $scope.admin_username = user.userName;
                }).error(function(response){
                    alert("AJAX Error");
                });
            };
            
            $scope.getAllUsers = function () {
                $http.get('get/users')
                    .success(function(response){
                       $scope.traineesMain = jQuery.parseJSON(response.users);
                    }).error(function(response){
                        alert("AJAX Error");
                    });
            };                        
            
            $scope.getCourses = function () {
                $http.get('get/courses').success(function(response){
                    if (response.error == ""){
                        $scope.courses = jQuery.parseJSON(response.courses);                            
                    } else {
                        alert(response.error);
                    }
                }).error(function(response){
                    alert("AJAX Error");
                });
            };
            
            $scope.updateTrainees = function (){
                $http.get('get/course/trainees/' + $scope.courseSelected).success(function(response){
                    if (response.error == ""){
                        var trainees = jQuery.parseJSON(response.trainees);
                        $scope.trainees = trainees;
                        $scope.mainTrainees = trainees;
                    } else {
                        alert (response.error);
                    }
                }).error(function(response){
                    alert ('Unable to retrieve data. Ajax error.');
                });
            };                        
            
            $scope.selectAllUnassigned = function () {
                if ($('input#selectAllUnassigned').prop('checked')){
                    $('input.trainee').prop('checked', true);
                } else {
                    $('input.trainee').prop('checked', false);
                }
            };
            
            $scope.searchTrainees = function () {        		
                $scope.search($scope.searchName, $scope.mainTrainees);
            };
            
            $scope.search = function searchStringInArray (name, trainees) {        		
                var newList = [];        		
                for (var i = 0; i < trainees.length; i++) {
                    if (trainees[i].name.match(new RegExp(name, "i"))){
                        newList.push(trainees[i]);
                    }
                }
                $scope.trainees = newList;
            };
            
            $scope.clearMessages = function () {
                $scope.successMessage = "";
                $scope.errorMessage = "";
            };
            
            $scope.confirmUnassignTrainees = function (){
                $scope.clearMessages();
                $('#confirm-unassign-trainees').modal('show');
            };
            
            $scope.unassignTrainees = function () {   
                $scope.clearMessages();
                var trainees = $('#unassigned-trainees-table').find('input[type="checkbox"]:checked').not('#selectAllUnassigned').map(function() {		
                    return $(this).val();
                }).get();			
                var courseId = $scope.courseSelected;
                if (courseId == '' || (typeof courseId === "undefined")){
                    $scope.errorMessage = ('Please choose a course first.');
                } else if (trainees.length) {  
                    var data = {'trainees': JSON.stringify(trainees), 'course': courseId.toString(), 'admin': $scope.admin_username};
                    $http.post('unassign/batch/course', data)
                        .success(function(response){
                            if (response.error == "") {
                                $scope.successMessage = ("Trainees removed successfully.");
                                $scope.updateTrainees();
                            } else {
                                $scope.errorMessage = (response.error);
                            }
                        }).error(function(response){
                            $scope.errorMessage = ("Unable to remove trainees. Ajax error.");
                        });
                } else {
                    $scope.errorMessage = ('Please select a student first.');
                }
                $('#confirm-unassign-trainees').modal('hide');
            };
                   
            $scope.getCredentials();    
            $scope.getCourses();
            $scope.getAllUsers();
            
	}]);


        app.controller('AddUserController', ['$scope', '$http' ,function($scope, $http) {
            $scope.getCredentials = function () {
                $http.get('get/logged_user')
                .success(function (data) {
                    var user = jQuery.parseJSON(data.user);
                    $scope.admin_username = user.userName;
                }).error(function(response){
                    alert("AJAX Error");
                });
            };
            
            $scope.getBusinessUnits = function () {
                $http.get('get/bus')
                    .success(function(response){
                        if (response.error == ""){
                            $scope.bus = jQuery.parseJSON(response.bus);
                        } else {
                            alert(response.error);
                        }
                    }).error(function(response){
                        alert("AJAX Error");
                    });
            }
            
            $scope.getUserTypes = function () {
                $http.get('get/user_types')
                    .success(function(response){
                        if (response.error==""){
                            $scope.types = jQuery.parseJSON(response.types);
                        } else {
                            alert(response.error);
                        }
                    }).error(function(response){
                        alert("AJAX Error");
                    });
            };
            
            $scope.confirmAddUser = function (){
                $('#confirm-add-user').modal('show');
            };
            
            $scope.addUser = function () {
                $scope.successMessage = "";                   
                if ((typeof $scope.buSelected === 'undefined') || $scope.buSelected == ''){
                    $scope.errorMessage = "Please select a business unit.";
                } else if ((typeof $scope.typeSelected === 'undefined') || $scope.typeSelected == '') {
                     $scope.errorMessage = "Please select a user type.";
                } else {                        
                    var userData = {
                        username: $scope.username,
                        name: $scope.name,
                        email: $scope.email,
                        position: $scope.position,
                        bu: $scope.buSelected,
                        type: $scope.typeSelected,
                        admin: $scope.admin_username
                    };   

                    $http.post('post/user', userData)
                        .success(function(response){
                            var error = jQuery.parseJSON(response.error);
                            if (error.length == 0){
                                $scope.successMessage = "User successfully added!";
                                $scope.errorMessage = "";
                                $('.form-control').val("");
                                $scope.buSelected = "";
                                $scope.typeSelected = "";
                            } else {
                                $scope.successMessage = "";
                                var msg = "";                                    
                                $.each(jQuery.parseJSON(response.error), function(index, message){
                                    msg += message + "\n";
                                });
                                $scope.errorMessage = msg;                                    
                            }
                            $('#confirm-add-user').modal('hide');
                        }).error(function(response){
                            $scope.errorMessage = 'Ajax Error. Please check entries.';
                        });
                    }
            }; 
            $scope.getBusinessUnits();
            $scope.getCredentials();  
            $scope.getUserTypes();
	}]);
    
        app.controller('ManageUserController', ['$scope', '$http', function($scope, $http) {
            $scope.orderByField = 'name';
            $scope.reverseSort = false;
            
            $scope.getCredentials = function () {
                $http.get('get/logged_user')
                .success(function (data) {
                    var user = jQuery.parseJSON(data.user);
                    $scope.admin_username = user.userName;
                }).error(function(response){
                    alert("AJAX Error");
                });
            };
            
            $scope.confirmDeleteUser = function (user) {
                $scope.userForDelete = user;
                $('#confirm-delete-user').modal('show');
            };
            
            $scope.confirmResetPassword = function (user) {
                $scope.userForResetPassword = user;
                $('#confirm-reset-pw').modal('show');
            };
            
            $scope.searchUser = function () {   
                $scope.clearMessages();
                var name = $scope.searchName;                
                if (!(/\S/.test(name))) {
                    name = "12345";
                }                 
                $http.get('user/search/' + name).success(function(response){
                    if (response.error == ""){
                        $scope.users = jQuery.parseJSON(response.users);
                    } else {
                        alert (response.error);
                    }
                }).error(function(response){
                    alert (response.toString());
                });
            };            
          
            $scope.getUser = function (usernameValue) {
                $http.get('get/user/' + usernameValue)
                    .success(function(response){
                        if (response.error == ""){
                            var user = jQuery.parseJSON(response.user);
                            $scope.username = user.userName;                            
                            $scope.name = user.name;
                            $scope.email = user.email;
                            $scope.position = user.position;
                            $scope.buSelected = user.businessUnit.id.toString();
                            $scope.typeSelected = user.userType.id.toString();
                        } else {
                            alert(response.error);
                        }
                    }).error(function(response){
                        alert("AJAX Error");
                    });
            };
            
            $scope.confirmUpdateUser = function () {
                $('#update-user-modal').modal('hide');
                $('#confirm-update-user').modal('show');
            };
            
            $scope.showUpdateUserModal = function () {
                $('#confirm-update-user').modal('hide');
                $('#update-user-modal').modal('show');
            };
            
            $scope.resetTable = function () {
                $http.get('get/users')
                    .success(function(response){
                       $scope.usersMain = jQuery.parseJSON(response.users);
                       $scope.users = jQuery.parseJSON(response.users);
                    }).error(function(response){
                        alert("AJAX Error");
                    });
            };
            
            $scope.updateUser = function () {
                $scope.clearMessages();
                var userData = {
                        username: $scope.username,
                        name: $scope.name,
                        email: $scope.email,
                        position: $scope.position,
                        bu: $scope.buSelected,
                        type: $scope.typeSelected,
                        admin: $scope.admin_username
                    };

                $http.post('put/user', userData)
                    .success(function(response){
                        var error = jQuery.parseJSON(response.error);
                        if (error.length == 0){
                            $scope.resetTable();
                            $scope.successMessage = "User was successfully updated.";
                        } else {
                            var msg  = "";
                            for (var i = 0; i < error.length; i++){
                                msg += error[i] + "\n";
                            }
                             $scope.errorMessage = msg;
                        }
                    }).error(function(){
                        $scope.errorMessage = "AJAX Error";
                    });
                $('#confirm-update-user').modal('hide');
            };
            
            $scope.viewAssignCourseModal = function (usernameValue) {
                $scope.username = usernameValue;
                $('#assign-course-modal').modal('show');
                $scope.updateCoursesTable();  
                $scope.updateAssignedTable();  
                $scope.clearMessages();
            };
            
            $scope.updateCoursesTable = function () {
                $http.get('get/courses')
                    .success(function(response){
                        if (response.error == ""){
                            $scope.courses = jQuery.parseJSON(response.courses);                            
                        } else {
                            alert(response.error);
                        }
                    }).error(function(response){
                        alert("AJAX Error");
                    });
            };
            
            $scope.isFinished = function (flag){
                if (flag)
                    return "Active";
                else
                    return "Finished";
            };
            
            $scope.updateAssignedTable = function () {
                $http.get('get/user/courses/' + $scope.username)
                    .success(function(response){
                        if (response.error == ""){
                            $scope.takens = [];
                            $scope.takens = jQuery.parseJSON(response.courses);
                        } else {
                            alert(response.error);
                        }
                    }).error(function(response){
                        alert("AJAX Error");
                    });
            };
            
            $scope.confirmAssignCourse = function (){
                $scope.clearMessages();
                $('#assign-course-modal').modal('hide');
                $('#confirm-assign-course').modal('show');
            };
            
            $scope.cancelAssignCourse = function (){
                $scope.clearMessages();
                $('#confirm-assign-course').modal('hide');
                $('#assign-course-modal').modal('show');
            };
            
            $scope.assignCourse = function () {
                if (typeof $scope.courseSelected === 'undefined'){
                    $scope.modalErrorMessage = "Please choose a course to assign.";
                } else {    
                    var data = { 
                        username : $scope.username,
                        courseId : $scope.courseSelected.toString(),
                        admin : $scope.admin_username
                    };
                    
                    $http.post('assign/course', data)
                        .success(function(response){
                            if (response.error == ""){                                
                                $scope.modalSuccessMessage = "User was assigned successfully!";
                                $scope.updateAssignedTable();
                            } else {
                                $scope.modalErrorMessage = "Error assigning course. " + response.error;
                            }
                        })
                        .error(function(response){
                           $scope.modalErrorMessage = "AJAX Error";
                        });
                }
                $scope.cancelAssignCourse(); 
            };
            
            $scope.confirmUnassignCourse = function (idValue){
                $('#assign-course-modal').modal('hide');
                $('#confirm-unassign-course').modal('show');
                $scope.courseToUnassign = idValue;
            };
            
            $scope.cancelUnassignCourse = function (){
                $scope.clearMessages();
                $('#confirm-unassign-course').modal('hide');
                $('#assign-course-modal').modal('show');
            };
            
            $scope.unassignCourse = function () {
                $scope.clearMessages();
                var data = {"assignmentId" : $scope.courseToUnassign.toString()};
                $http.post('delete/assigned_course', data)
                .success(function(response){
                    if (response.error == "") {
                        $scope.modalSuccessMessage = "Course assignment was successfully deleted.";
                        $scope.updateAssignedTable();
                    } else {
                         $scope.modalErrorMessage = "Error in deleting course assignment. " + response.error;
                    }
                })
                .error(function(){
                    $scope.modalErrorMessage = "Error in deleting course assignment. AJAX error.";
                });
                $scope.cancelUnassignCourse();
            };
            
            $scope.clearMessages = function () {
                $scope.errorMessage = "";
                $scope.successMessage = "";
                $scope.modalErrorMessage = "";
                $scope.modalSuccessMessage = "";
            };
            
            $scope.deleteUser = function () {
                $scope.clearMessages();
                $http.post('delete/user',{"username": $scope.userForDelete})
                    .success(function(response){
                        if (response.error == ""){
                            $scope.successMessage = "User was deleted successfully";
                            $scope.resetTable();
                        } else {
                             $scope.errorMessage = response.error;
                        }
                    }).error(function(response){
                        $scope.errorMessage = "AJAX Error";
                    });
                    $('#confirm-delete-user').modal('hide');
            };
            
            $scope.resetPassword = function () {
                $scope.clearMessages();
                $http.post('password/reset',{"username": $scope.userForResetPassword})
                      .success(function(response){
                          if (response.error == ""){
                              $scope.successMessage = "Password was changed successfully.";
                              $scope.resetTable();
                          } else {
                              $scope.errorMessage = response.error;
                          }
                      }).error(function(response){
                           $scope.errorMessage = "AJAX Error";
                      });
                      $('#confirm-reset-pw').modal('hide');
            };
            
            $scope.getBusinessUnits = function () {
                $http.get('get/bus')
                    .success(function(response){
                        if (response.error == ""){
                            $scope.bus = jQuery.parseJSON(response.bus);
                        } else {
                            alert(response.error);
                        }
                    }).error(function(response){
                        alert("AJAX Error");
                    });
            }
            
            $scope.getUserTypes = function () {
                $http.get('get/user_types')
                    .success(function(response){
                        if (response.error==""){
                            $scope.types = jQuery.parseJSON(response.types);
                        } else {
                            alert(response.error);
                        }
                    }).error(function(response){
                        alert("AJAX Error");
                    });
            };
            
            $scope.showUserProfile = function (usernameValue) {
                $('div#update-user-modal').modal('show');
                $scope.getUser(usernameValue);
            };
            
            $scope.getBusinessUnits();
            $scope.getUserTypes();
            $scope.getCredentials();
	}]);
                
        app.controller('AddCourseController', ['$scope', '$http', function($scope, $http) {
            
            $scope.getCredentials = function () {
                $http.get('get/logged_user')
                .success(function (data) {
                    var user = jQuery.parseJSON(data.user);
                    $scope.admin= user.userName;
                }).error(function(response){
                    alert("AJAX Error");
                });
            };
                
            $scope.getTrainers = function () {
                $http.get('get/trainers').success(function(response){
                    if (response.error == ""){
                        $scope.trainers = jQuery.parseJSON(response.trainers);
                    } else {
                        alert (response.error);
                    }
                }).error(function(response){
                    alert ("Unable to fetch trainers. AJAX error.");
                });                                
            };
            
            
            $scope.getCategories = function () {
                $http.get('get/categories')
                    .success(function(response){
                        if (response.error == ""){
                            $scope.categories = jQuery.parseJSON(response.categories);
                        } else {
                            alert(response.error);
                        }
                    })
                    .error(function(response){
                        alert("Unable to fetch categories. AJAX error.");
                    });
            };
            
            $scope.clearMessages = function () {
                $scope.successMessage = "";
                $scope.errorMessage = "";
            };
            
            $scope.confirmAddCourse = function () {
                $('#confirm-add-course').modal('show');
                $scope.clearMessages();
            };
            
            $scope.addCourse = function () {
                $scope.clearMessages();
                    if ((typeof $scope.categorySelected === 'undefined') || $scope.categorySelected == ""){
                        $scope.errorMessage = ("Please enter a category.");
                    } else if ((typeof $scope.trainerSelected === 'undefined') || $scope.trainerSelected == ""){
                        $scope.errorMessage =  ("Please choose a trainer.");
                    } else {
                        var data = {
                                "admin": $scope.admin,
                                "title": $scope.title,
                                "description": $scope.description,
                                "trainer": $scope.trainerSelected,
                                "categoryId": $scope.categorySelected.toString()
                            };
                            
                        $http.post('post/course', data).success(function(response){
                            if (response.error == ""){
                                $scope.trainerSelected = "";
                                $scope.categorySelected = "";
                                $(".form-control").val("");
                                $scope.successMessage = ('Course updated successfully.');
                                window.location.href = "trainer#/";
                            } else {
                                $scope.errorMessage = (response.error);
                            }
                        }).error(function(response){
                            $scope.errorMessage =  ("Unable to add course. AJAX error.");
                        });
                    }
                 $('#confirm-add-course').modal('hide');    
            };
            
            $scope.getCategories();
            $scope.getTrainers();
            $scope.getCredentials();
	}]);
    
        app.controller('ManageCourseController', ['$scope', '$http', '$window', function($scope, $http, $window) {            
            $scope.orderByField = 'dateEdited';
            $scope.reverseSort = true;
            
            $scope.clearMessages = function () {
                $scope.errorMessage = "";
                $scope.successMessage = "";
            };
                
            $scope.getCredentials = function () {
                $http.get('get/logged_user')
                .success(function (data) {
                    var user = jQuery.parseJSON(data.user);
                    $scope.admin = user.userName;
                }).error(function(response){
                    alert("AJAX Error");
                });
            };
            
            $scope.goToAddCourse = function () {
                window.location.href = "trainer#/add_course";
            };
            
            $scope.viewCourse = function (id) {
                window.location.href = "trainer#/course_details?id=" + id;
            };
            
            $scope.getTrainers = function () {
                $http.get('get/trainers').success(function(response){
                    if (response.error == ""){
                        $scope.trainers = jQuery.parseJSON(response.trainers);
                    } else {
                        alert (response.error);
                    }
                }).error(function(response){
                    alert ("Unable to fetch trainers. AJAX error.");
                });                                
            };
            
            $scope.getCategories = function () {
                $http.get('get/categories')
                    .success(function(response){
                        if (response.error == ""){
                            $scope.categories = jQuery.parseJSON(response.categories);
                        } else {
                            alert(response.error);
                        }
                    })
                    .error(function(response){
                        alert("Unable to fetch categories. AJAX error.");
                    });
            };
                
            $scope.getCourses = function (){
                $http.get('get/courses').success(function(response){
                    if (response.error == ""){
                        $scope.courses = jQuery.parseJSON(response.courses);
                        $scope.coursesMain = jQuery.parseJSON(response.courses);
                    } else {
                        alert (response.error);
                    }
                }).error(function(response){
                    alert ("Unable to fetch courses. AJAX Error.");
                });
            };
            
            $scope.searchCourses = function () {                                
                $scope.search($scope.searchName, $scope.coursesMain);                
            };
            
            $scope.search = function (name, courses) {        		
                var newCourses = [];        		
                for (var i = 0; i < courses.length; i++) {
                    if (courses[i].courseTitle.match(new RegExp(name, "i"))){
                            newCourses.push(courses[i]);
                    }
                }
                $scope.courses = newCourses;
            };
            
            $scope.confirmDeleteCourse = function (id) {
                $scope.clearMessages();
                $scope.courseForDelete = id;
                $('#confirm-delete-course').modal('show');
            };
            
            $scope.deleteCourse = function () {
                $scope.clearMessages();
                $http.post('delete/course', {"id": $scope.courseForDelete.toString()}).success(function(response){
                    if (response.error == ""){
                        $scope.successMessage = ("Course successfully deleted");
                        $scope.getCourses();
                    } else {
                        $scope.errorMessage = (response.error);
                    }
                }).error(function(response){
                    $scope.errorMessage = ("Unable to delete course. AJAX error.");
                });
                $('#confirm-delete-course').modal('hide');
            };
            
            $scope.getCourse = function (id) {                
                $http.get('get/course/' + id).success(function(response){
                    if (response.error == ""){
                        var course = jQuery.parseJSON(response.course);
                        $scope.title = course.courseTitle;
                        $scope.description = course.description;
                        $scope.categorySelected = course.courseCategory.id.toString();
                        $scope.trainerSelected = course.user.userName;
                    } else {
                        alert (response.error);
                    }
                }).error(function(response){
                    alert ("Unable to fetch course. AJAX error.");
                });
            };
            
            $scope.confirmUpdateCourse = function (){
                $scope.clearMessages();
                $('#confirm-update-course').modal('show');
                $('#update-course-modal').modal('hide');
            };
            
            $scope.showUpdateCourseModal = function () {
                $scope.clearMessages();
                $('#confirm-update-course').modal('hide');
                $('#update-course-modal').modal('show');
            };
            
            $scope.updateCourse = function () {  
                $scope.clearMessages();
                var data = {                    
                    id: $scope.courseId.toString(),
                    title: $scope.title,
                    description: $scope.description,
                    category: $scope.categorySelected,
                    trainer: $scope.trainerSelected,
                    admin: $scope.admin
                };
                data = JSON.stringify(data);
                $http.post('put/course', data).success(function(response){
                    if (response.error == ""){
                        $scope.getCourses();
                        $scope.successMessage = ("Course was successfully updated.");
                    } else {
                        $scope.errorMessage = (response.error);
                    }
                }).error(function(response){
                    $scope.errorMessage = ('Unable to update user. AJAX error.');
                });
                $('#confirm-update-course').modal('hide');
                $('#update-course-modal').modal('hide');
            };
            
            $scope.showUpdateModal = function (id) {
                $scope.clearMessages();
                $('#update-course-modal').modal('show');
                $scope.getCourse(id);
                $scope.courseId = id.toString();
            };
            
            $scope.init = function (){
                $scope.getCourses();
                $scope.getCredentials();
                $scope.getTrainers();
                $scope.getCategories();  
            };
            
            $scope.init();
	}]);
    
        app.controller('ManageExamController', ['$scope', '$http', function($scope, $http) {
            $scope.orderByField = 'title';
            $scope.reverseSort = false;
            
            $scope.goToAddExam = function () {
                window.location.href = "make-exam";
            };
            
            $scope.goToEditExam = function (id){
                window.location.href = "edit-exam?id=" + id.toString();
            };
            
            $scope.getCredentials = function () {
                $http.get('get/logged_user')
                .success(function (data) {
                    var user = jQuery.parseJSON(data.user);
                    $scope.admin_username = user.userName;
                }).error(function(response){
                    alert("AJAX Error");
                });
            };
            
            $scope.getExams = function (id){
                $http.get('get/course/exams/' + id).success(function(response){
                    if (response.error == ""){
                        $scope.exams = jQuery.parseJSON(response.exams);
                    } else {
                        alert (response.error);
                    }
                }).error(function(response){
                    alert ("Unable to fetch exams. Ajax error.");
                });
            }; 
            
            $scope.updateExamTable = function () {
                $scope.getExams($scope.courseSelected);
            };
            
            $scope.getCourses = function () {
                $http.get('get/courses')
                    .success(function(response){
                        if (response.error == ""){
                            $scope.courses = jQuery.parseJSON(response.courses);                            
                        } else {
                            alert(response.error);
                        }
                    }).error(function(response){
                        alert("AJAX Error");
                    });
            };
            
            $scope.clearMessages = function (){
                $scope.errorMessage = "";
                $scope.successMessage = "";
            };
            
            $scope.confirmDeleteExam = function (id) {
                $scope.clearMessages();
                $scope.examForDelete = id;
                $('#confirm-delete-exam').modal('show');
            };
            
            $scope.deleteExam = function () {
                $scope.clearMessages();
                $http.post('delete/exam', {"id": $scope.examForDelete.toString()}).success(function(response){
                    if (response.error == ""){
                        $scope.successMessage = ("Exam deleted successfully.");
                        $scope.updateExamTable();
                    } else {
                        $scope.errorMessage = (response.error);
                    }
                }).error(function(response){
                    $scope.errorMessage =  ("Unable to delete exam. Ajax error.");
                });
                $('#confirm-delete-exam').modal('hide');
            };            
            $scope.getCredentials();
            $scope.getCourses();
	}]);
    
        app.controller('ExamResultsController', ['$scope', '$http', function($scope, $http) {
            
            $scope.orderByField = 'title';
            $scope.reverseSort = false;
            
            $scope.getCredentials = function () {
                $http.get('get/logged_user')
                .success(function (data) {
                    var user = jQuery.parseJSON(data.user);
                    $scope.admin_username = user.userName;
                }).error(function(response){
                    alert("AJAX Error");
                });
            };
            
            $scope.updateExams = function () {                 
                $http.get('get/course/exams/' + $scope.courseSelected).success(function(response){
                    if (response.error == ""){
                        $scope.scores = [];
                        $scope.examSelected = null;
                        $scope.exams = jQuery.parseJSON(response.exams);
                    } else {
                        alert (response.error);
                    }
                }).error(function(response){
                    alert ("Unable to fetch exams. Ajax error.");
                });                
            };
            
            $scope.updateScoresTable = function () {
                $http.get('get/exam/scores/' + $scope.examSelected).success(function(response){
                    if (response.error == ""){
                        $scope.scoresMain = jQuery.parseJSON(response.scores);
                        $scope.scores = jQuery.parseJSON(response.scores);                        
                    } else {
                        alert (response.error);
                    }
                }).error(function(response){
                    alert ("Unable to fetch scores. Ajax error.");
                });
            };
            
            $scope.searchUser = function () {  
                $scope.search($scope.searchName, $scope.scoresMain);                
            };
            
            $scope.search = function (name, scores) {        		
                var newScores = [];        		
                for (var i = 0; i < scores.length; i++) {                    
                    if (scores[i].user.name.match(new RegExp(name, "i"))){
                        newScores.push(scores[i]);
                    }
                }
                $scope.scores = newScores;
            };
            
            $scope.clearMessages = function () {
                $scope.errorMessage = "";
                $scope.successMessage = "";
            };
            
            $scope.confirmDeleteScore = function (id){
                $scope.clearMessages();
                $scope.scoreForDelete = id;
                $('#confirm-delete-score').modal('show');
            };
            
            $scope.deleteScore = function () {
                $scope.clearMessages();
                $http.post('delete/score', {"id": $scope.scoreForDelete.toString()}).success(function(response){
                    if (response.error == ""){
                        $scope.successMessage = ("Score successfully deleted.");
                        $scope.updateScoresTable();
                    } else {
                        $scope.errorMessage = (response.error);
                    }
                }).error(function(response){
                    $scope.errorMessage = ("Unable to delete the score. Ajax error.");
                });
                $('#confirm-delete-score').modal('hide');
            };
                
            $scope.getCourses = function () {
                $http.get('get/courses').success(function(response){
                    if (response.error == ""){
                        $scope.courses = jQuery.parseJSON(response.courses);                            
                    } else {
                        alert(response.error);
                    }
                }).error(function(response){
                    alert("AJAX Error");
                });
            };
                        
            $scope.getCourses();
            
	}]);
    
        app.controller('ManageBusinessUnitController', ['$scope', '$http', function($scope, $http) {
            $scope.getCredentials = function () {
                $http.get('get/logged_user')
                .success(function (data) {
                    var user = jQuery.parseJSON(data.user);
                    $scope.admin_username = user.userName;
                }).error(function(response){
                    alert("AJAX Error");
                });
            };                      
                                
            $scope.updateBuTable = function () {
                $http.get('get/bus')
                    .success(function(response){
                        if (response.error == ""){
                            $scope.bus = jQuery.parseJSON(response.bus);
                        } else {
                            alert(response.error);
                        }
                    })
                    .error(function(response){
                        alert("Unabled to fetch business units. AJAX error.");
                    });
            };
            
            $scope.clearMessages = function (){
                $scope.errorMessage = "";
                $scope.successMessage = "";
            };
            
            $scope.confirmUpdateBu = function (id){
                $scope.clearMessages();
                $scope.buForUpdate = id;
                $('#confirm-update-bu').modal('show');
            };
            
            $scope.updateBu = function () {
                $scope.clearMessages();
                var buName = $('#' + $scope.buForUpdate.toString()).val();
                if (!(buName == "")){
                    var buName = $('#' + $scope.buForUpdate.toString()).val();
                    var buData = {
                        "id": $scope.buForUpdate.toString(),
                        "name": buName,
                        "admin": $scope.admin_username
                    };
                    $http.post('put/bu', buData)
                        .success(function(response){
                            if (response.error == ""){
                                $scope.successMessage = "Updated business unit successfully.";
                            } else {
                                $scope.errorMessage = response.error;
                            }
                        })
                        .error(function(response){
                            $scope.errorMessage = "Error updating the business unit. Cause: AJAX Error";
                        });   
                } else {
                    $scope.errorMessage = "Please enter a value. Do not leave it blank.";
                }
                $('#confirm-update-bu').modal('hide');
            };
            $scope.confirmAddBusinessUnit = function (){
                $scope.clearMessages();
                $('#confirm-add-bu').modal('show');
            };
            
            $scope.addBusinessUnit = function () {
                var data = {
                    bu : $scope.newBu,
                    admin : $scope.admin_username
                };
                $http.post('post/bu', data)
                    .success(function(response){
                        if (response.error == ""){
                            $scope.updateBuTable();
                            $scope.newBu = "";
                            $scope.successMessage = ("Added successfully");
                        } else {
                            $scope.errorMessage = ("Unable to add business unit.\n" + response.error);
                        }
                    })
                    .error(function(response){
                        $scope.errorMessage = ("Unable to add business unit. AJAX Error");
                    });
                $('#confirm-add-bu').modal('hide');     
            };
            
            $scope.confirmDeleteBu = function (id){
                $scope.clearMessages();
                $scope.buForDelete = id;
                $('#confirm-delete-bu').modal('show');
            };
            
            $scope.deleteBu = function () {
                $http.post('delete/bu', {"id": $scope.buForDelete.toString()})
                    .success(function(response){
                        if (response.error==""){
                            $scope.successMessage = "Deleted business unit sucessfully!";
                            $scope.updateBuTable();
                        } else {
                            $scope.errorMessage = "Unable to delete business unit:. \n" + response.error;
                        }
                    })
                    .error(function(response){
                         $scope.errorMessage = "Unable to delete business unit. AJAX error.";
                    });
                    $('#confirm-delete-bu').modal('hide');
            };
            $scope.updateBuTable();
            $scope.getCredentials();
	}]);
    
        app.controller('ManageCategoryController', ['$scope', '$http', function($scope, $http) {
            $scope.getCredentials = function () {
                $http.get('get/logged_user')
                .success(function (data) {
                    var user = jQuery.parseJSON(data.user);
                    $scope.admin_username = user.userName;
                }).error(function(response){
                    alert("AJAX Error");
                });
            };    
            
            $scope.updateCategoryTable = function () {
                $http.get('get/categories')
                    .success(function(response){
                        if (response.error == ""){
                            $scope.categories = jQuery.parseJSON(response.categories);
                        } else {
                            alert(response.error);
                        }
                    })
                    .error(function(response){
                        alert("Unabled to fetch categories. AJAX error.");
                    });
            };
            
            $scope.clearMessages = function () {
                $scope.errorMessage = "";
                $scope.successMessage = "";
            };
            
            $scope.confirmUpdateCategory = function (id) {
                $scope.clearMessages();
                $scope.categoryForUpdate = id;
                $('#confirm-update-category').modal('show');
            };
            
            $scope.updateCategory= function () {
                var newCategoryName = $('#' +  $scope.categoryForUpdate.toString()).val();
                if (newCategoryName == "") {
                    $scope.errorMessage = ("Please enter a value. Do not leave it blank.");
                } else { 
                    var categoryData = {
                        "id":  $scope.categoryForUpdate.toString(),
                        "name": newCategoryName,
                        "admin": $scope.admin_username
                    };
                    $http.post('put/category', categoryData)
                        .success(function(response){
                            if (response.error == ""){
                                $scope.successMessage = ("Updated category successfully.");
                            } else {
                                $scope.errorMessage =  (response.error);
                            }
                        })
                        .error(function(response){
                            $scope.errorMessage =  ("Error updating the category. Cause: AJAX Error");
                        }); 
                }
                $('#confirm-update-category').modal('hide');
            };
            
            $scope.confirmAddCategory = function (){
                $scope.clearMessages();
                $('#confirm-add-category').modal('show');
            };
            
            $scope.addCategory = function () {                                   
                var data = {
                    category : $scope.newCategory,
                    admin : $scope.admin_username
                };

                $http.post('post/category', data)
                    .success(function(response){
                        if (response.error == ""){
                            $scope.updateCategoryTable();   
                            $scope.newCategory = "";
                            $scope.successMessage = ("Added successfully");
                        } else {
                            $scope.errorMessage = ("Unable to add new category.\n" + response.error);
                        }
                    })
                    .error(function(response){
                        $scope.errorMessage = ("Unable to add new category. AJAX Error");
                    });
                $('#confirm-add-category').modal('hide');
            };
            
            $scope.confirmDeleteCategory = function (id) {
                $scope.clearMessages();
                $scope.categoryForDelete = id;
                $('#confirm-delete-category').modal('show');
            };
            
            $scope.deleteCategory = function () {
                $scope.clearMessages();
                $http.post('delete/category', {"id": $scope.categoryForDelete.toString()})
                    .success(function(response){
                        if (response.error==""){
                            $scope.successMessage = ("Deleted category sucessfully!");
                            $scope.updateCategoryTable(); 
                        } else {
                            $scope.errorMessage = ("Unable to delete category:. \n" + response.error);
                        }
                    })
                    .error(function(response){
                        $scope.errorMessage = ("Unable to delete category. AJAX error.");
                    });   
                $('#confirm-delete-category').modal('hide');
            };
            $scope.updateCategoryTable();
            $scope.getCredentials();
	}]);
    
        app.controller('ChangePasswordController', ['$scope', '$http', function($scope, $http) {
            $scope.getCredentials = function () {
                $http.get('get/logged_user')
                .success(function (data) {
                    var user = jQuery.parseJSON(data.user);
                    $scope.admin = user.userName;
                }).error(function(response){
                    alert("AJAX Error");
                });
            }; 
            
            $scope.resetFields = function () {
                $scope.password = "";
                $scope.newPassword1 = "";
                $scope.newPassword2 = "";
            };
            
            $scope.changePassword = function () {
                if (confirm('Are you sure you want to change your password?')) {
                var credentials = {
                    username: $scope.admin,
                    password: $scope.password,
                    newPassword1: $scope.newPassword1,
                    newPassword2: $scope.newPassword2
                };
                
                $http.post('put/user/password', credentials)
                    .success(function(response){
                        var errors = response.error;
                        if (errors.length == 0){
                            alert('Successfully changed password.');
                            $scope.resetFields();
                        } else {
                            var msg = "";
                            $.each(errors, function(index, error) {
                                msg+=error + "\n";
                            });
                            alert (msg);
                            $scope.resetFields();
                        }                    
                    }).error(function(response){
                        alert(reponse);
                        $scope.resetFields();
                    });
                }
            };              
            $scope.getCredentials();     
	}]);
    
        app.controller('UpdateProfileController', ['$scope', '$http', function($scope, $http) {
            $scope.getProfile = function () {
                $http.get('get/logged_user')
                .success(function (response) {
                    var user = jQuery.parseJSON(response.user);
                    $scope.username = user.userName;
                    $scope.getUser(user.userName);                    
                }).error(function(response){
                    alert("AJAX Error");
                });                
            };
            
             $scope.getUser = function (usernameValue) {
                $http.get('get/user/' + usernameValue)
                    .success(function(response){                        
                        if (response.error == ""){
                            var user = jQuery.parseJSON(response.user);
                            $scope.username = user.userName;                            
                            $scope.name = user.name;
                            $scope.email = user.email;
                            $scope.position = user.position;
                            $scope.buSelected = user.businessUnit.id.toString();
                            $scope.typeSelected = user.userType.id.toString();
                        } else {
                            alert(response.error);
                        }
                    }).error(function(response){
                        alert("AJAX Error");
                    });
            };
            
            $scope.getBusinessUnits = function () {
                $http.get('get/bus')
                    .success(function(response){
                        if (response.error == ""){
                            $scope.bus = jQuery.parseJSON(response.bus);
                        } else {
                            alert(response.error);
                        }
                    }).error(function(response){
                        alert("AJAX Error");
                    });
            }
            
            $scope.getUserTypes = function () {
                $http.get('get/user_types')
                    .success(function(response){
                        if (response.error==""){
                            $scope.types = jQuery.parseJSON(response.types);
                        } else {
                            alert(response.error);
                        }
                    }).error(function(response){
                        alert("AJAX Error");
                    });
            };
            
            $scope.updateProfile = function () {
                $scope.successMessage = "";
                $scope.errorMessage = "";
                if (confirm("Are you sure you want to update your profile?")){                    
                    if (typeof $scope.buSelected === 'undefined'){
                        alert("Please select a business unit.");
                    } else if (typeof $scope.typeSelected === 'undefined') {
                        alert("Please select a user type.");
                    } else {                        
                        var userData = {
                            username: $scope.username,
                            name: $scope.name,
                            email: $scope.email,
                            position: $scope.position,
                            bu: $scope.buSelected.toString(),
                            type: $scope.typeSelected.toString(),
                            admin: $scope.username
                        };   
                        
                        $http.post('put/user', userData)
                            .success(function(response){
                                var error = jQuery.parseJSON(response.error);
                                if (error.length == 0){
                                    $scope.successMessage = "User successfully updated!";
                                    $scope.errorMessage = "";
                                } else {
                                    $scope.successMessage = "";
                                    var msg = "";                                    
                                    $.each(jQuery.parseJSON(response.error), function(index, message){
                                        msg += message + "\n";
                                    });
                                    alert (msg);
                                }                            
                            }).error(function(response){
                                $scope.errorMessage = "AJAX Error";
                            });
                    }
                }
            }; 
                                   
            $scope.getProfile();     
            $scope.getUserTypes();
            $scope.getBusinessUnits();
	}]);
})();