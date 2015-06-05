'use strict';

angular.module('jtraceApp')
    .controller('BomlineController', function ($scope, Bomline, Bomheader, Itemmtr, Itemctn, ParseLinks,DTOptionsBuilder,DTColumnBuilder,DTColumnDefBuilder) {
        $scope.bomlines = [];
        $scope.itemmtrs = Itemmtr.query();
        $scope.itemctns = Itemctn.query();
        $scope.bomheader = Bomheader.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Bomline.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.bomlines.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.bomlines = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Bomline.get({id: id}, function(result) {
                $scope.bomline = result;
                $('#saveBomlineModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.bomline.id != null) {
                Bomline.update($scope.bomline,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Bomline.save($scope.bomline,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Bomline.get({id: id}, function(result) {
                $scope.bomline = result;
                $('#deleteBomlineConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Bomline.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteBomlineConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $('#saveBomlineModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.bomline = {lineid: null, quantity: null, refdesignator: null, physical: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
