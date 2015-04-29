'use strict';

angular.module('jtraceApp')
    .controller('ItemmfrpartController', function ($scope, Itemmfrpart, Manufacturer, Itemmtr) {
        $scope.itemmfrparts = [];
        $scope.manufacturers = Manufacturer.query();
        $scope.itemmtrs = Itemmtr.query();
        $scope.loadAll = function() {
            Itemmfrpart.query(function(result) {
               $scope.itemmfrparts = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Itemmfrpart.save($scope.itemmfrpart,
                function () {
                    $scope.loadAll();
                    $('#saveItemmfrpartModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Itemmfrpart.get({id: id}, function(result) {
                $scope.itemmfrpart = result;
                $('#saveItemmfrpartModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Itemmfrpart.get({id: id}, function(result) {
                $scope.itemmfrpart = result;
                $('#deleteItemmfrpartConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Itemmfrpart.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteItemmfrpartConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.itemmfrpart = {mfrpart: null, status: null, suppart: null, remarks: null, id: null};
        };
    });
