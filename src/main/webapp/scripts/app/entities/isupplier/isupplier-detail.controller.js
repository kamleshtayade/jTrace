'use strict';

angular.module('jtraceApp')
    .controller('IsupplierDetailController', function ($scope, $stateParams, Isupplier, Imanufacturer) {
        $scope.isupplier = {};
        $scope.load = function (id) {
            Isupplier.get({id: id}, function(result) {
              $scope.isupplier = result;
            });
        };
        $scope.load($stateParams.id);
    });
