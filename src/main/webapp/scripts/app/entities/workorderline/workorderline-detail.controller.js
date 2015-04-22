'use strict';

angular.module('jtraceApp')
    .controller('WorkorderlineDetailController', function ($scope, $stateParams, Workorderline) {
        $scope.workorderline = {};
        $scope.load = function (id) {
            Workorderline.get({id: id}, function(result) {
              $scope.workorderline = result;
            });
        };
        $scope.load($stateParams.id);
    });
