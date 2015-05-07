'use strict';

angular.module('jtraceApp')
    .controller('PlantsecController', function ($scope, Plantsec, Plant, ParseLinks) {
        $scope.plantsecs = [];
        $scope.plants = Plant.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Plantsec.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.plantsecs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Plantsec.update($scope.plantsec,
                function () {
                    $scope.loadAll();
                    $('#savePlantsecModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Plantsec.get({id: id}, function(result) {
                $scope.plantsec = result;
                $('#savePlantsecModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Plantsec.get({id: id}, function(result) {
                $scope.plantsec = result;
                $('#deletePlantsecConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Plantsec.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePlantsecConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.plantsec = {name: null, description: null, isenabled: null, capacity: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
