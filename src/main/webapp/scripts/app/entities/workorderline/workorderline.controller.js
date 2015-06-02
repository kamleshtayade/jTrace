'use strict';

angular.module('jtraceApp')
    .controller('WorkorderlineController', function ($scope, Workorderline, Workorderheader, Itemctn, ParseLinks) {
        $scope.workorderlines = [];
        $scope.itemctns = Itemctn.query();
        $scope.workorderheaders = Workorderheader.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Workorderline.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.workorderlines = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Workorderline.update($scope.workorderline,
                function () {
                    $scope.loadAll();
                    $('#saveWorkorderlineModal').collapse('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Workorderline.get({id: id}, function(result) {
                $scope.workorderline = result;
                $('#saveWorkorderlineModal').collapse('show');
            });
        };

        $scope.delete = function (id) {
            Workorderline.get({id: id}, function(result) {
                $scope.workorderline = result;
                $('#deleteWorkorderlineConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Workorderline.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteWorkorderlineConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.workorderline = {bomChildItem: null, attrition: null, requQty: null, issuedQty: null, isCustSupplied: null, remark: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
            $('#saveWorkorderlineModal').collapse('hide');
        };
    });
