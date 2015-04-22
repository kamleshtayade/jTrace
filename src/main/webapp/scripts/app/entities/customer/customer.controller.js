'use strict';

angular.module('jtraceApp')
    .controller('CustomerController', function ($scope, Customer) {
        $scope.customers = [];
        $scope.loadAll = function() {
            Customer.query(function(result) {
               $scope.customers = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Customer.save($scope.customer,
                function () {
                    $scope.loadAll();
                    $('#saveCustomerModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Customer.get({id: id}, function(result) {
                $scope.customer = result;
                $('#saveCustomerModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Customer.get({id: id}, function(result) {
                $scope.customer = result;
                $('#deleteCustomerConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Customer.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCustomerConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.customer = {code: null, name: null, billToAddress: null, shipToAddress: null, contactName: null, email: null, phone: null, id: null};
        };
    });
