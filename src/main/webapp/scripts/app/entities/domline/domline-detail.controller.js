'use strict';

angular.module('jtraceApp')
    .controller('DomlineDetailController', function ($scope, $stateParams, Domline, Domheader) {
        $scope.domline = {};
        $scope.load = function (id) {
            Domline.get({id: id}, function(result) {
              $scope.domline = result;
            });
        };
        $scope.load($stateParams.id);
    });
