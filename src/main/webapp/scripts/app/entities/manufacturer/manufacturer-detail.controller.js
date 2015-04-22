'use strict';

angular.module('jtraceApp')
    .controller('ManufacturerDetailController', function ($scope, $stateParams, Manufacturer, Itemmfrpart, Supplier) {
        $scope.manufacturer = {};
        $scope.load = function (id) {
            Manufacturer.get({id: id}, function(result) {
              $scope.manufacturer = result;
            });
        };
        $scope.load($stateParams.id);
    });
