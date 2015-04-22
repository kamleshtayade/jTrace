'use strict';

angular.module('jtraceApp')
    .controller('ItemtypeController', function ($scope, Itemtype) {
        $scope.itemtypes = [];
        $scope.loadAll = function() {
            Itemtype.query(function(result) {
               $scope.itemtypes = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Itemtype.save($scope.itemtype,
                function () {
                    $scope.loadAll();
                    $('#saveItemtypeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Itemtype.get({id: id}, function(result) {
                $scope.itemtype = result;
                $('#saveItemtypeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Itemtype.get({id: id}, function(result) {
                $scope.itemtype = result;
                $('#deleteItemtypeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Itemtype.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteItemtypeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.itemtype = {name: null, description: null, enabled: null, id: null};
        };
    });
