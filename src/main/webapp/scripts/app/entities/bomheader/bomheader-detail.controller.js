'use strict';

angular.module('jtraceApp')
    .controller('BomheaderDetailController', function ($scope, $stateParams, Bomheader) {
        $scope.bomheader = {};
        $scope.load = function (id) {
            Bomheader.get({id: id}, function(result) {
              $scope.bomheader = result;
            });
        };
        $scope.load($stateParams.id);
    });
