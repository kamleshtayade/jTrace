'use strict';

angular.module('jtraceApp')
    .controller('PlantmcDetailController', function ($scope, $stateParams, Plantmc, Plantmfgline) {
        $scope.plantmc = {};
        $scope.load = function (id) {
            Plantmc.get({id: id}, function(result) {
              $scope.plantmc = result;
            });
        };
        $scope.load($stateParams.id);
    });
