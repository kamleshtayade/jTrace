'use strict';

angular.module('jtraceApp')
    .controller('WorkorderheaderController', function ($scope, Workorderheader) {
        $scope.workorderheaders = [];
        $scope.loadAll = function() {
            Workorderheader.query(function(result) {
               $scope.workorderheaders = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Workorderheader.save($scope.workorderheader,
                function () {
                    $scope.loadAll();
                    $('#saveWorkorderheaderModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Workorderheader.get({id: id}, function(result) {
                $scope.workorderheader = result;
                $('#saveWorkorderheaderModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Workorderheader.get({id: id}, function(result) {
                $scope.workorderheader = result;
                $('#deleteWorkorderheaderConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Workorderheader.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteWorkorderheaderConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.workorderheader = {woNumber: null, kitNumber: null, customer: null, shipToLoc: null, plant: null, plantMfgLine: null, status: null, qty: null, soNumber: null, item: null, asyCode: null, bom: null, id: null};
        };
    });
