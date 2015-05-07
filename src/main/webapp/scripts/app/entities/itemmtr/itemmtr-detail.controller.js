'use strict';

angular.module('jtraceApp')
    .controller('ItemmtrDetailController', function ($scope, $stateParams, Itemmtr, Itemsubcat) {
        $scope.itemmtr = {};
        $scope.load = function (id) {
            Itemmtr.get({id: id}, function(result) {
              $scope.itemmtr = result;
            });
        };
        $scope.load($stateParams.id);
    });
