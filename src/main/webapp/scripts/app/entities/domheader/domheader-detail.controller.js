'use strict';

angular.module('jtraceApp')
    .controller('DomheaderDetailController', function ($scope, $stateParams, Domheader) {
        $scope.domheader = {};
        $scope.load = function (id) {
            Domheader.get({id: id}, function(result) {
              $scope.domheader = result;
            });
        };
        $scope.load($stateParams.id);
    });
