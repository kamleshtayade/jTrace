'use strict';

angular.module('jtraceApp')
    .controller('PlantDetailController', function ($scope, $stateParams, Plant, Plantmfgline) {
        $scope.plant = {};
        $scope.load = function (id) {
            Plant.get({id: id}, function(result) {
              $scope.plant = result;
            });
        };
        $scope.load($stateParams.id);
    });
