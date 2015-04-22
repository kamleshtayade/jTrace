'use strict';

angular.module('jtraceApp')
    .controller('SupplierController', function ($scope, Supplier, Manufacturer) {
        $scope.suppliers = [];
        $scope.manufacturers = Manufacturer.query();
        $scope.loadAll = function() {
            Supplier.query(function(result) {
               $scope.suppliers = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Supplier.save($scope.supplier,
                function () {
                    $scope.loadAll();
                    $('#saveSupplierModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Supplier.get({id: id}, function(result) {
                $scope.supplier = result;
                $('#saveSupplierModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Supplier.get({id: id}, function(result) {
                $scope.supplier = result;
                $('#deleteSupplierConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Supplier.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSupplierConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.supplier = {code: null, name: null, enabled: null, contact: null, email: null, phone: null, id: null};
        };
    });
