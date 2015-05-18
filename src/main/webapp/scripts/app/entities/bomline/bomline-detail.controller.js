'use strict';

angular.module('jtraceApp')
    .controller('BomlineDetailController', function ($scope, $stateParams, Bomline, Itemmtr) {
        $scope.bomline = {};
        $scope.load = function (id) {
            Bomline.get({id: id}, function(result) {
              $scope.bomline = result;
            });
        };
        $scope.load($stateParams.id);
    });
