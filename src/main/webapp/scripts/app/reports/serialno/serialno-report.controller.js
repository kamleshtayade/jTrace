'use strict';

angular.module('jtraceApp')
    .controller('SerialnoReportController', function ($scope, $stateParams, Domline, Domheader,Woheader) {
        $scope.domline = {};
        $scope.woheader={};

        $scope.load = function (id) {
            Domline.get({id: id}, function(result) {
              $scope.domline = result;
              $scope.showWoDetails(result.serialno);
            });
        };

        $scope.showWoDetails = function (id) {
            Woheader.get({itemserial: id}, function(result) {
                $scope.woheader = result;
            });
        };

        $scope.load($stateParams.id);
    });