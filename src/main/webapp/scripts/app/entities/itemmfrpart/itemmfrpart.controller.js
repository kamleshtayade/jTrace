'use strict';

angular.module('jtraceApp')
    .controller('ItemmfrpartController', function ($scope, Itemmfrpart, Itemmtr, ParseLinks) {
        $scope.itemmfrparts = [];
        $scope.itemmtrs = Itemmtr.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Itemmfrpart.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.itemmfrparts = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Itemmfrpart.update($scope.itemmfrpart,
                function () {
                    $scope.loadAll();
                    $('#saveItemmfrpartModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Itemmfrpart.get({id: id}, function(result) {
                $scope.itemmfrpart = result;
                $('#saveItemmfrpartModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Itemmfrpart.get({id: id}, function(result) {
                $scope.itemmfrpart = result;
                $('#deleteItemmfrpartConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Itemmfrpart.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteItemmfrpartConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.itemmfrpart = {mfrpart: null, status: null, supplier: null, remark: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
