'use strict';

angular.module('jtraceApp')
    .controller('ManufacturerController', function ($scope, Manufacturer, Itemmfrpart, Supplier) {
        $scope.manufacturers = [];
        $scope.itemmfrparts = Itemmfrpart.query();
        $scope.suppliers = Supplier.query();
        $scope.loadAll = function() {
            Manufacturer.query(function(result) {
               $scope.manufacturers = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Manufacturer.save($scope.manufacturer,
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
            $scope.manufacturer = {code: null, name: null, enabled: null, category: null, contact: null, email: null, phone: null, id: null};
        };
    });
