booksForAll.controller('mainController', ['$scope', '$rootScope', '$location', '$http', '$filter', function () {
	$rootScope.route = 'login';

	$rootScope.$watch(function () {
		return $rootScope.route;
	}, function (newValue, oldValue) {
		$scope.templateUrl = "app/views/" + newValue + ".html";
		$location.path(newValue);
	});
}])