'use strict';

angular.module('jtraceApp')
    .controller('ImanufacturerDetailController', function ($scope, $stateParams, Imanufacturer, Isupplier) {
        $scope.imanufacturer = {};
        $scope.load = function (id) {
            Imanufacturer.get({id: id}, function(result) {
              $scope.imanufacturer = result;
            });
        };
        $scope.load($stateParams.id);
    });
