// Restful call service
booksForAll.service('restService', ['$http', '$q', 'dataSharingService', function($http, $q, dataSharingService) {
	
	return {
		// Perform an Asynchronous callback with the specified URL
		call: function (method, url, input) {
			$http({
				method: method,
				url: url,
				headers: {
					'Content-Type' : "application/json; charset=utf-8"
				},
				data: input
			}).then(
				function (success) {
					dataSharingService.set("status", success.data.status !== undefined ? success.data.status : dataSharingService.get("status"));
					dataSharingService.set("route", success.data.route !== undefined ? success.data.route : dataSharingService.get("route"));
					dataSharingService.set("notification", success.data.notification !== undefined ? success.data.notification : dataSharingService.get("notification"));
					dataSharingService.set("user", success.data.user !== undefined ? success.data.user : dataSharingService.get("user"));
					dataSharingService.set("users", success.data.users !== undefined ? success.data.users : dataSharingService.get("users"));
			}, function (failure) {
				console.log("An unknown error has occured while trying to retrieve data from server...");
			});
		}
	};
}])

// Authentication system service
.service('authService', ['restService', function(restService) {
	
	var data = {};

	return {
		// Log the passed user in
		login: function (user) {
			restService.call('POST', 'login/auth', user);
		},
		// Log the passed user out
		logout: function (user) {
			restService.call('POST', 'logout', user);
		}
	}
}])
// Registeration system service
.service('registerService', ['restService', function(restService) {
	return {
		// Register the passed user to the system
		register: function (user) {
			restService.call('POST', 'register', user);
		},
		// Get users with the provided username (or) nickname
		getUsers: function () {
			restService.call('POST', 'getUsers', {});
		}
	}
}])
// Notification system service
.service('notifyService', ['$rootScope', '$timeout', 'dataSharingService', function ($rootScope, $timeout, dataSharingService) {
	return {
		alert: function (notification) {
			$(notification.selector).addClass("alert alert-" + notification.status).html(notification.message);
			$timeout(function () {
				$(notification.selector).removeClass("alert alert-" + notification.status).html("");
			}, 2500);
		}
	}
}])

// Service for data sharing over all scopes 
.service('dataSharingService', [function () {

	var data = {
			"route": "login"
	};

	return {
		get: function (key) {
			return data[key];
		},

		set: function (key, value) {
			data[key] = value;
		},
		
		log: function () {
			console.log(data);
		}
	}
}])