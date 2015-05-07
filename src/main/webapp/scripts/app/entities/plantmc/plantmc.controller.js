'use strict';

angular.module('jtraceApp')
    .controller('PlantmcController', function ($scope, Plantmc, Plantmfgline, ParseLinks) {
        $scope.plantmcs = [];
        $scope.plantmfglines = Plantmfgline.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Plantmc.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.plantmcs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Plantmc.update($scope.plantmc,
                function () {
                    $scope.loadAll();
                    $('#savePlantmcModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Plantmc.get({id: id}, function(result) {
                $scope.plantmc = result;
                $('#savePlantmcModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Plantmc.get({id: id}, function(result) {
                $scope.plantmc = result;
                $('#deletePlantmcConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Plantmc.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePlantmcConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.plantmc = {name: null, description: null, specification: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
