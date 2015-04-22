'use strict';

angular.module('jtraceApp')
    .controller('ItemmasterDetailController', function ($scope, $stateParams, Itemmaster, Itemcat, Itemsubcat) {
        $scope.itemmaster = {};
        $scope.load = function (id) {
            Itemmaster.get({id: id}, function(result) {
              $scope.itemmaster = result;
            });
        };
        $scope.load($stateParams.id);
    });
