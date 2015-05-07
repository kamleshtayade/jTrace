'use strict';

angular.module('jtraceApp')
    .controller('ItemcatDetailController', function ($scope, $stateParams, Itemcat, Itemsubcat) {
        $scope.itemcat = {};
        $scope.load = function (id) {
            Itemcat.get({id: id}, function(result) {
              $scope.itemcat = result;
            });
        };
        $scope.load($stateParams.id);
    });
