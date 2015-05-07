'use strict';

angular.module('jtraceApp')
    .controller('ItemtypeController', function ($scope, Itemtype, ParseLinks) {
        $scope.itemtypes = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Itemtype.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.itemtypes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Itemtype.update($scope.itemtype,
                function () {
                    $scope.loadAll();
                    $('#saveItemtypeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Itemtype.get({id: id}, function(result) {
                $scope.itemtype = result;
                $('#saveItemtypeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Itemtype.get({id: id}, function(result) {
                $scope.itemtype = result;
                $('#deleteItemtypeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Itemtype.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteItemtypeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.itemtype = {name: null, description: null, isenabled: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
