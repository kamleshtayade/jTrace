'use strict';

angular.module('jtraceApp')
    .controller('ItemctnDetailController', function ($scope, $stateParams, Itemctn, Itemmtr) {
        $scope.itemctn = {};
        $scope.load = function (id) {
            Itemctn.get({id: id}, function(result) {
              $scope.itemctn = result;
            });
        };
        $scope.load($stateParams.id);
    });
