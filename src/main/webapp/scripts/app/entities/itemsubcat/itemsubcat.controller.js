'use strict';

angular.module('jtraceApp')
    .controller('ItemsubcatController', function ($scope, Itemsubcat, Itemcat) {
        $scope.itemsubcats = [];
        $scope.itemcats = Itemcat.query();
        $scope.loadAll = function() {
            Itemsubcat.query(function(result) {
               $scope.itemsubcats = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Itemsubcat.update($scope.itemsubcat,
                function () {
                    $scope.loadAll();
                    $('#saveItemsubcatModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Itemsubcat.get({id: id}, function(result) {
                $scope.itemsubcat = result;
                $('#saveItemsubcatModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Itemsubcat.get({id: id}, function(result) {
                $scope.itemsubcat = result;
                $('#deleteItemsubcatConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Itemsubcat.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteItemsubcatConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.itemsubcat = {name: null, description: null, enabled: null, classcode: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
