'use strict';

angular.module('jtraceApp')
    .controller('ItemctnController', function ($scope, Itemctn, Itemmtr, ParseLinks) {
        $scope.itemctns = [];
        $scope.itemmtrs = Itemmtr.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Itemctn.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.itemctns = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Itemctn.update($scope.itemctn,
                function () {
                    $scope.loadAll();
                    $('#saveItemctnModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Itemctn.get({id: id}, function(result) {
                $scope.itemctn = result;
                $('#saveItemctnModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Itemctn.get({id: id}, function(result) {
                $scope.itemctn = result;
                $('#deleteItemctnConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Itemctn.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteItemctnConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.itemctn = {ctn: null, reqdQty: null, recdDt: null, item: null, srNoTo: null, selfLife: null, poQty: null, invoice: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
