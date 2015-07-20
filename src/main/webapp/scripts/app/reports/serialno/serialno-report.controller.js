'use strict';

angular.module('jtraceApp')
    .controller('SerialnoReportController', function ($scope, $stateParams, Rdomline, Domheader,Rwoheader,Rbomline,ParseLinks) {
        $scope.domline = {};
        $scope.woheader={};
        $scope.bomlines=[];
        $scope.page = 1;

        $scope.load = function (id) {
            Rdomline.get({id: id}, function(result) {
              $scope.domline = result;
              $scope.showWoDetails(result.serialno);
            });
        };

        $scope.showWoDetails = function (id) {
            Rwoheader.get({id: id}, function(result) {
                $scope.woheader = result;
                $scope.showBomlines(result.itemmtr.id);
            });
        };

        $scope.showBomlines = function (id) {
            Rbomline.query({id: id,page: $scope.page, per_page: 20}, function(result,headers) {
                $scope.links = ParseLinks.parse(headers('link'));
              for (var i = 0; i < result.length; i++) {
                    $scope.bomlines.push(result[i]);
                }
            });
        };

        $scope.load($stateParams.id);
    });
