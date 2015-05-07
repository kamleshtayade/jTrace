'use strict';

angular.module('jtraceApp')
    .controller('PlantsecDetailController', function ($scope, $stateParams, Plantsec, Plant) {
        $scope.plantsec = {};
        $scope.load = function (id) {
            Plantsec.get({id: id}, function(result) {
              $scope.plantsec = result;
            });
        };
        $scope.load($stateParams.id);
    });
