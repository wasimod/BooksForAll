var booksForAll = angular.module('booksForAll', [])
	.config(['$locationProvider', function($locationProvider) {
		$locationProvider.hashPrefix('');
}]);