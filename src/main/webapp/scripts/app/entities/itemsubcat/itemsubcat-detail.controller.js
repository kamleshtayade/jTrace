'use strict';

angular.module('jtraceApp')
    .controller('ItemsubcatDetailController', function ($scope, $stateParams, Itemsubcat, Itemcat, Itemmaster) {
        $scope.itemsubcat = {};
        $scope.load = function (id) {
            Itemsubcat.get({id: id}, function(result) {
              $scope.itemsubcat = result;
            });
        };
        $scope.load($stateParams.id);
    });
