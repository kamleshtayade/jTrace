'use strict';

angular.module('jtraceApp')
    .controller('PlantmfglineController', function ($scope, Plantmfgline, Plant, Plantsec, ParseLinks) {
        $scope.plantmfglines = [];
        $scope.plantsecs = Plantsec.query();
        $scope.plants = Plant.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Plantmfgline.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.plantmfglines = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Plantmfgline.update($scope.plantmfgline,
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
            $scope.plantmfgline = {name: null, capacity: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
