'use strict';

angular.module('jtraceApp')
    .controller('PlantController', function ($scope, Plant, Plantmfgline) {
        $scope.plants = [];
        $scope.plantmfglines = Plantmfgline.query();
        $scope.loadAll = function() {
            Plant.query(function(result) {
               $scope.plants = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Plant.save($scope.plant,
                function () {
                    $scope.loadAll();
                    $('#savePlantModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Plant.get({id: id}, function(result) {
                $scope.plant = result;
                $('#savePlantModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Plant.get({id: id}, function(result) {
                $scope.plant = result;
                $('#deletePlantConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Plant.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePlantConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.plant = {code: null, location: null, address: null, isActive: null, capacity: null, id: null};
        };
    });
