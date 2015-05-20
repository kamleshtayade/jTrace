'use strict';

angular.module('jtraceApp')
    .controller('OrganizationDetailController', function ($scope, $stateParams, Organization) {
        $scope.organization = {};
        $scope.load = function (id) {
            Organization.get({id: id}, function(result) {
              $scope.organization = result;
            });
        };
        $scope.load($stateParams.id);
    });
