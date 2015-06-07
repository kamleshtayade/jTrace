'use strict';

angular.module('jtraceApp')
    .controller('MainController', function ($scope, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
        $scope.alerts = [
		    { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' },
		    { type: 'success', msg: 'Well done! You successfully read this important alert message.' }
		];
		$scope.closeAlert = function(index) {
    		$scope.alerts.splice(index, 1);
  		};
    });
