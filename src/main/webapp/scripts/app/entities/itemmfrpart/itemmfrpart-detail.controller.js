'use strict';

angular.module('jtraceApp')
    .controller('ItemmfrpartDetailController', function ($scope, $stateParams, Itemmfrpart, Itemmtr) {
        $scope.itemmfrpart = {};
        $scope.load = function (id) {
            Itemmfrpart.get({id: id}, function(result) {
              $scope.itemmfrpart = result;
            });
        };
        $scope.load($stateParams.id);
    });
