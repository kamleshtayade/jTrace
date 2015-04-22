'use strict';

angular.module('jtraceApp')
    .controller('ItemcatController', function ($scope, Itemcat, Itemsubcat, Itemmaster) {
        $scope.itemcats = [];
        $scope.itemsubcats = Itemsubcat.query();
        $scope.itemmasters = Itemmaster.query();
        $scope.loadAll = function() {
            Itemcat.query(function(result) {
               $scope.itemcats = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Itemcat.save($scope.itemcat,
                function () {
                    $scope.loadAll();
                    $('#saveItemcatModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Itemcat.get({id: id}, function(result) {
                $scope.itemcat = result;
                $('#saveItemcatModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Itemcat.get({id: id}, function(result) {
                $scope.itemcat = result;
                $('#deleteItemcatConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Itemcat.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteItemcatConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.itemcat = {name: null, description: null, enabled: null, id: null};
        };
    });
