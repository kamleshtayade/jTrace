'use strict';

angular.module('jtraceApp')
    .controller('ItemmtrController', function ($scope, Itemmtr, Itemcat, Itemsubcat) {
        $scope.itemmtrs = [];
        $scope.itemcats = Itemcat.query();
        $scope.itemsubcats = Itemsubcat.query();
        $scope.loadAll = function() {
            Itemmtr.query(function(result) {
               $scope.itemmtrs = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Itemmtr.update($scope.itemmtr,
                function () {
                    $scope.loadAll();
                    $('#saveItemmtrModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Itemmtr.get({id: id}, function(result) {
                $scope.itemmtr = result;
                $('#saveItemmtrModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Itemmtr.get({id: id}, function(result) {
                $scope.itemmtr = result;
                $('#deleteItemmtrConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Itemmtr.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteItemmtrConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.itemmtr = {code: null, description: null, specification: null, catid: null, subcatid: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });