'use strict';

angular.module('jtraceApp')
    .controller('ManufacturerController', function ($scope, Manufacturer, ParseLinks) {
        $scope.manufacturers = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Manufacturer.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.manufacturers = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Manufacturer.update($scope.manufacturer,
                function () {
                    $scope.loadAll();
                    $('#saveManufacturerModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Manufacturer.get({id: id}, function(result) {
                $scope.manufacturer = result;
                $('#saveManufacturerModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Manufacturer.get({id: id}, function(result) {
                $scope.manufacturer = result;
                $('#deleteManufacturerConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Manufacturer.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteManufacturerConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.manufacturer = {code: null, name: null, isenabled: null, mfrcat: null, address: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
