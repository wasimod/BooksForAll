// Main application controller
booksForAll.controller('mainController', ['$scope', '$rootScope', '$location', '$http', '$filter', function ($scope, $rootScope, $location, $http, $filter) {
	$rootScope.route = 'login';

	$rootScope.$watch(function () {
		return $rootScope.route;
	}, function (newValue, oldValue) {
		$scope.templateUrl = "app/views/" + newValue + ".html";
		$location.path(newValue);
	});
}])

// Login controller
.controller('loginController', ['$scope', '$rootScope', '$timeout', '$http', function($scope, $rootScope, $timeout, $http) {
	
	$("body").css({
		"background": "url(assets/images/login.jpg)",
		"background-size": "cover"
	});

	$scope.login = function () {
		var user = {
			"userName": $scope.username,
			"password": $scope.password
		};

		$http({
			method: 'POST',
			url: 'login',
			headers: {'Content-Type' : "application/json; charset=utf-8"},
			data: user
		}).then(
			function (success) {
				var data = success.data;
				notifyService.alert({
					"status": data.status,
					"selector": data.notification.selector,
					"message": data.notification.message
				});
				if (data.status == "success") {
					$timeout(function () {
						$rootScope.route = data.route;
						$rootScope.user = data.user;
					}, 2500);
				}
			},
			function (failure) {
				console.log(failure.data);
			}
		);
	};
}])

// Register controller
.controller('registerController', ['$scope', '$rootScope', '$timeout', '$http', '$filter', function($scope, $rootScope, $timeout, $http, $filter) {
	// Scope variables
	$scope.disabled = true;

	// Scope methods
	$scope.checkButton = function () {
		$scope.disabled = 
			$scope.validUsername != "glyphicon glyphicon-ok-circle" || $scope.validNickname != "glyphicon glyphicon-ok-circle"
			||
			$scope.username == "" || $scope.nickname == "" ||	$scope.password == ""
			||
			$scope.username == undefined || $scope.nickname == undefined ||	$scope.password == undefined;
	};

	$scope.register = function () {
		var user = {
			"userName": $scope.username,
			"password": $scope.password,
			"NickName": $scope.nickname,
			"description": $scope.description,
			"photoUrl": $scope.photoUrl !== undefined ? $scope.photoUrl : "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSMyKQ_ZaKgbgQ6PE--NyftpawhbFDuv0lIZAslbH_o5QVS3KY9wHo87AqxyQ",
			"address": $scope.address,
			"email": $scope.email,
			"telephone": $scope.telephone
		};

		$http({
			method: 'POST',
			url: 'register',
			headers: {'Content-Type' : "application/json; charset=utf-8"},
			data: user
		}).then(
			function (success) {
				var data = success.data;
				notifyService.alert({
					"status": data.status,
					"selector": data.notification.selector,
					"message": data.notification.message
				});
				if (data.status == "success") {
					$timeout(function () {
						$(".modal-backdrop").css({display: 'none'});
						$rootScope.route = data.route;
						$rootScope.user = data.user;
					}, 2500);
				}
			},
			function (failure) {
				console.log(failure.data);
			}
		);
	};

	$scope.validateUsername = function () {
		if ($scope.username != "" && $scope.username !== undefined) {
			$http({
				method: 'POST',
				url: 'validate',
				headers: {'Content-Type' : "application/json; charset=utf-8"},
				data: {"userName": $scope.username}
			}).then(
				function (success) {
					$scope.validUsername = "glyphicon glyphicon-" + success.data.valid + "-circle";
					$scope.checkButton();
				},
				function (failure) {
					console.log(failure.data);
				}
			);
		} else {
			$scope.validUsername = "";
		}
	};

	$scope.validateNickname = function () {
		if ($scope.nickname != "" && $scope.nickname !== undefined) {
			$http({
				method: 'POST',
				url: 'validate',
				headers: {'Content-Type' : "application/json; charset=utf-8"},
				data: {"NickName": $scope.nickname}
			}).then(
				function (success) {
					$scope.validNickname = "glyphicon glyphicon-" + success.data.valid + "-circle";
					$scope.checkButton();
				},
				function (failure) {
					console.log(failure.data);
				}
			);
		} else {
			$scope.validNickname = "";
		}
	};
}])

.controller('',[,function(){
	
	
}])
