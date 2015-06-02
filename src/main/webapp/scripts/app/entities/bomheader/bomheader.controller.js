'use strict';

angular.module('jtraceApp')
    .controller('BomheaderController', function ($scope,$controller, Bomheader, ParseLinks,DTOptionsBuilder,DTColumnBuilder,DTColumnDefBuilder) {
        $scope.bomheaders = [];
        $scope.bomlineCrl=$scope.$new();
        $scope.page = 1;
        $scope.dtOptions = DTOptionsBuilder.newOptions().withPaginationType('full_numbers').withDisplayLength(10);

        $controller('BomlineController',{$scope:$scope.bomlineCrl});
         console.info($scope.manfactCrl);

        $scope.loadAll = function() {
            Bomheader.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.bomheaders.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.bomheaders = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Bomheader.get({id: id}, function(result) {
                $scope.bomheader = result;
                $('#saveBomheaderModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.bomheader.id != null) {
                Bomheader.update($scope.bomheader,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Bomheader.save($scope.bomheader,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Bomheader.get({id: id}, function(result) {
                $scope.bomheader = result;
                $('#deleteBomheaderConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Bomheader.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteBomheaderConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $('#saveBomheaderModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.bomheader = {code: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
