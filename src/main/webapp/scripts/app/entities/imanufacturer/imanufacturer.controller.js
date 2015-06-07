'use strict';

angular.module('jtraceApp')
    .controller('ImanufacturerController', function ($scope, Imanufacturer, Isupplier, ParseLinks) {
        $scope.imanufacturers = [];
        $scope.isuppliers = Isupplier.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Imanufacturer.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.imanufacturers.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.imanufacturers = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Imanufacturer.get({id: id}, function(result) {
                $scope.imanufacturer = result;
                $('#saveImanufacturerModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.imanufacturer.id != null) {
                Imanufacturer.update($scope.imanufacturer,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Imanufacturer.save($scope.imanufacturer,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Imanufacturer.get({id: id}, function(result) {
                $scope.imanufacturer = result;
                $('#deleteImanufacturerConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Imanufacturer.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteImanufacturerConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $('#saveImanufacturerModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.imanufacturer = {name: null, isenabled: null, code: null, mfrcat: null, address: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
