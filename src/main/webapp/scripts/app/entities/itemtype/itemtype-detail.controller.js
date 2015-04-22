'use strict';

angular.module('jtraceApp')
    .controller('ItemtypeDetailController', function ($scope, $stateParams, Itemtype) {
        $scope.itemtype = {};
        $scope.load = function (id) {
            Itemtype.get({id: id}, function(result) {
              $scope.itemtype = result;
            });
        };
        $scope.load($stateParams.id);
    });
