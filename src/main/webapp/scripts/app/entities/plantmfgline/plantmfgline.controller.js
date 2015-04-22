'use strict';

angular.module('jtraceApp')
    .controller('PlantmfglineController', function ($scope, Plantmfgline, Plant) {
        $scope.plantmfglines = [];
        $scope.plants = Plant.query();
        $scope.loadAll = function() {
            Plantmfgline.query(function(result) {
               $scope.plantmfglines = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Plantmfgline.save($scope.plantmfgline,
                function () {
                    $scope.loadAll();
                    $('#savePlantmfglineModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Plantmfgline.get({id: id}, function(result) {
                $scope.plantmfgline = result;
                $('#savePlantmfglineModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Plantmfgline.get({id: id}, function(result) {
                $scope.plantmfgline = result;
                $('#deletePlantmfglineConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Plantmfgline.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePlantmfglineConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.plantmfgline = {capacity: null, id: null};
        };
    });
