'use strict';

angular.module('jtraceApp')
    .controller('WorkorderlineController', function ($scope, Workorderline) {
        $scope.workorderlines = [];
        $scope.loadAll = function() {
            Workorderline.query(function(result) {
               $scope.workorderlines = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Workorderline.save($scope.workorderline,
                function () {
                    $scope.loadAll();
                    $('#saveWorkorderlineModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Workorderline.get({id: id}, function(result) {
                $scope.workorderline = result;
                $('#saveWorkorderlineModal').modal('show');
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
            $scope.workorderline = {workorderheader: null, bomChildItem: null, attrition: null, requQty: null, issuedQty: null, isCustSupplied: null, itemCtn: null, remarks: null, id: null};
        };
    });
