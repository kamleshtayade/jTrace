'use strict';

angular.module('jtraceApp')
    .controller('IsupplierController', function ($scope, Isupplier, Imanufacturer, ParseLinks) {
        $scope.isuppliers = [];
        $scope.imanufacturers = Imanufacturer.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Isupplier.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.isuppliers.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.isuppliers = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Isupplier.get({id: id}, function(result) {
                $scope.isupplier = result;
                $('#saveIsupplierModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.isupplier.id != null) {
                Isupplier.update($scope.isupplier,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Isupplier.save($scope.isupplier,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Isupplier.get({id: id}, function(result) {
                $scope.isupplier = result;
                $('#deleteIsupplierConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Isupplier.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteIsupplierConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $('#saveIsupplierModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.isupplier = {code: null, isenabled: null, remark: null, address: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
