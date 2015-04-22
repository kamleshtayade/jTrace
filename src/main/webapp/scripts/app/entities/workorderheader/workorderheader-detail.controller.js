'use strict';

angular.module('jtraceApp')
    .controller('WorkorderheaderDetailController', function ($scope, $stateParams, Workorderheader) {
        $scope.workorderheader = {};
        $scope.load = function (id) {
            Workorderheader.get({id: id}, function(result) {
              $scope.workorderheader = result;
            });
        };
        $scope.load($stateParams.id);
    });
