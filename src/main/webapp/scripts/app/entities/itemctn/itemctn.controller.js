'use strict';

angular.module('jtraceApp')
    .controller('ItemctnController', function ($scope, Itemctn) {
        $scope.itemctns = [];
        $scope.loadAll = function() {
            Itemctn.query(function(result) {
               $scope.itemctns = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Itemctn.save($scope.itemctn,
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
            $scope.itemctn = {ctn: null, srNoFrom: null, reqdQty: null, recdDt: null, item: null, srNoTo: null, selfLife: null, poQty: null, invoice: null, manufaturer: null, mfgPartNo: null, supplier: null, dateCode: null, lotCode: null, id: null};
        };
    });
