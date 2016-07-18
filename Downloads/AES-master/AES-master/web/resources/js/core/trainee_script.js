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

	var app = angular.module('app', ['ngSanitize', 'ngRoute']);

	app.config(['$routeProvider', function($routeProvider) {
            $routeProvider
                .when('/reset_password', {
                    templateUrl: 'templates/trainee/reset_password.html',
                    controller: 'ResetPasswordController'
                })
                .when('/update_profile', {
                    templateUrl: 'templates/trainee/update_profile.html',
                    controller: 'UpdateProfileController'
                })
                .when('/current_courses', {
                    templateUrl: 'templates/trainee/current_courses.html',
                    controller: 'CurrentCoursesController'
                })
                .when('/past_courses', {
                    templateUrl: 'templates/trainee/past_courses.html',
                    controller: 'PastCoursesController'
                })
                .when('/current_exams', {
                    templateUrl: 'templates/trainee/current_exams.html',
                    controller: 'CurrentExamsController'
                })
                .when('/past_exams', {
                    templateUrl: 'templates/trainee/past_exams.html',
                    controller: 'PastExamsController'
                })
                .when('/assign_trainees', {
                    templateUrl: 'templates/admin/assign_trainees.html',
                    controller: 'AssignTraineesController'
                })
                .when('/unassign_trainees', {
                    templateUrl: 'templates/admin/unassign_trainees.html',
                    controller: 'UnassignTraineesController'
                })
                .otherwise({
                    redirectTo: '/current_courses'
                });
	}]);

	app.controller('DefaultController', ['$scope', function($scope) {
            $scope.navigateTo = function (absoluteAppUrl) {
                Utils.appRedirectTo(absoluteAppUrl);
            };
	}]);

	app.controller('UserHomeController', ['$scope', '$http' ,function($scope, $http) {    
                
	}]);

	app.controller('ResetPasswordController', ['$scope', '$http', function($scope, $http) {
            $scope.getCredentials = function () {
                $http.get('get/logged_user')
                .success(function (data) {
                    var user = jQuery.parseJSON(data.user);
                    $scope.password = user.password;
                    $scope.username = user.userName;
                });
            };
            
            $scope.getCredentials();
                
            $scope.onResetPassword = function () {
                $('#modal-confirm').modal('show');
            };
            $scope.onConfirmResetPassword = function () {                
                $('#modal-confirm').modal('hide');
                $scope.changePassword();
            };  
            
            $scope.resetFields = function () {
                $scope.password = "";
                $scope.newPassword1 = "";
                $scope.newPassword2 = "";
            };
            
            $scope.changePassword = function () {
                var credentials = {
                    username: $scope.username,
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
            };
            
	}]);

	app.controller('UpdateProfileController', ['$scope', '$http' , function($scope, $http) {
                                
                $scope.updateProfileInformation = function () {
                    $http.get('get/logged_user')
                    .success(function (data) {
                        var user = jQuery.parseJSON(data.user);                        
                        $scope.username = user.userName;
                        $scope.name = user.name;
                        $scope.email = user.email;
                    });
                };
                
		$scope.onUpdateProfile = function () {
                    $('#modal-confirm').modal('show');
		};
		$scope.onConfirmUpdateProfile = function () {
                    $('#modal-confirm').modal('hide');
                    $scope.updateProfile();
		};
                
                $scope.resetEntries = function () {
                    $scope.name = "";
                    $scope.email = "";
                };
                
                $scope.updateProfile = function () {
                    
                    var userData = {
                        username: $scope.username,
                        name: $scope.name,
                        email: $scope.email
                    };                    
                    
                    $http.post('put/profile', userData).success(function(data, status, headers, config){
                        if (data.error == ''){                            
                            $('#result-message-modal').html("Profile successfully updated.");
                        } else {
                            $('#result-message-modal').html("Error: " + data.error);
                        }
                    }).error(function(data, status, headers, config){
                        $('#result-message-modal').html("Error: " + data.error);
                    });
                    $('#message-modal').modal('show');
                };
                 
                $scope.updateProfileInformation();
	}]);

	app.controller('CurrentCoursesController', ['$scope', '$http', '$sce', function($scope, $http, $sce) {
                $scope.getCredentials = function () {
                    $http.get('get/logged_user')
                    .success(function (data) {
                        var user = jQuery.parseJSON(data.user);
                        $scope.username = user.userName;
                        $scope.getCurrentCourses(user.userName);
                    });
                };
                
                $scope.trustAsHtml = function(string) {
                    return $sce.trustAsHtml(string);
                };
                
                $scope.getCurrentCourses = function (username) {
                    $http.get('get/detailed_courses/' + username).success(function(response){
                        if (response.error == ""){
                            $scope.currentCourses = jQuery.parseJSON(response.courses);
                        } else {
                            alert (response.error);
                        }
                    }).error(function(response){
                        alert ("Unable to fetch currently assigned courses. AJAX error.");
                    });
                };                                
                
		$scope.showAccordion = function (id) {
			$('.collapse').collapse('hide');
			$(id).collapse('show');
			$scope.activeChapter = 0;
		};

		$scope.showChapter = function (id) {
			$scope.activeChapter = id;
		};

		$scope.activeChapter = 0;
                $scope.getCredentials();
	}]);

	app.controller('PastCoursesController', ['$scope', '$http', '$sce', function($scope, $http, $sce) {
		$scope.getCredentials = function () {
                    $http.get('get/logged_user')
                    .success(function (data) {
                        var user = jQuery.parseJSON(data.user);
                        $scope.username = user.userName;                        
                        $scope.getPastCourses(user.userName);
                    });
                };
                
                $scope.trustAsHtml = function(string) {
                    return $sce.trustAsHtml(string);
                };
                
                $scope.getPastCourses = function (username) {
                    $http.get('get/finished_courses/' + username).success(function(response){
                        if (response.error == ""){
                            $scope.pastCourses = jQuery.parseJSON(response.courses);
                        } else {
                            alert (response.error);
                        }
                    }).error(function(response){
                        alert ("Unable to fetch currently assigned courses. AJAX error.");
                    });
                };
                                
		$scope.showAccordion = function (id) {
			$('.collapse').collapse('hide');
			$(id).collapse('show');
			$scope.activeChapter = 0;
		};

		$scope.showChapter = function (id) {
			$scope.activeChapter = id;
		};

		$scope.activeChapter = 0;
                $scope.getCredentials();
	}]);

	app.controller('CurrentExamsController', ['$window', '$scope', '$http', function($window, $scope, $http) {
		$scope.selectedExam = -1;
                $scope.orderByField = "title";
                $scope.reverseSort = false;
                
                $scope.getCredentials = function () {
                    $http.get('get/logged_user')
                    .success(function (data) {
                        var user = jQuery.parseJSON(data.user);
                        $scope.username = user.userName;  
                        $scope.getCurrentExams(user.userName);
                    });
                };                                
                
                $scope.getCurrentExams = function (userNameValue) {
                    $http.get('get/exam/upcoming/' + userNameValue).success(function(response){
                        if (response.error == ""){
                            $scope.exams = jQuery.parseJSON(response.exams);
                        } else {
                            alert (response.error);
                        }
                    }).error(function(response){
                        alert ("Unable to get exams. Ajax error.");
                    });
                };                                
		
		$scope.takeExam = function (examId) {
                    $scope.selectedExam = examId;                                        
                    $('#modal-confirm').modal('show');
		};

		$scope.onConfirmTakeExam = function () {
                    $window.location.href = "take-exam?id=" + $scope.selectedExam;
                    $('#modal-confirm').modal('hide');
		};
                
                $scope.getCredentials();                
	}]);

	app.controller('PastExamsController', ['$window', '$scope', '$http', function($window, $scope, $http) {
		$scope.selectedExam = -1;
                $scope.orderByField = "dateTaken";
                $scope.reverseSort = "true";
                
                $scope.checkScore = function (score, maxScore, passingRate) {                    
                    var passingScore = maxScore * (passingRate * .01);
                    if (score >= passingScore){
                        return "Passed";
                    } else {
                        return "Failed";
                    }
                };

		$scope.getCredentials = function () {
                    $http.get('get/logged_user')
                    .success(function (data) {
                        var user = jQuery.parseJSON(data.user);
                        $scope.username = user.userName;  
                        $scope.getPastScores(user.userName);
                    });
                };      
                
                $scope.getPastScores = function (userNameValue) {
                    $http.get('get/user/scores/' + userNameValue).success(function(response){
                        if (response.error == ""){
                            $scope.scores = jQuery.parseJSON(response.scores);
                        } else {
                            alert (response.error);
                        }
                    }).error(function(response){
                        alert ("Unable to get exams. Ajax error.");
                    });
                };
                               
		$scope.takeExam = function (examId) {
                    $scope.selectedExam = examId;
                    $('#modal-confirm').modal('show');
		};

		$scope.onConfirmTakeExam = function () {
                    $window.location.href = "take-exam?id=" + $scope.selectedExam;
                    $('#modal-confirm').modal('hide');
		};
                
                $scope.getCredentials();
	}]);
})();
