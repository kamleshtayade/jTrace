'use strict';

angular.module('jtraceApp')
    .controller('BomheaderController', function ($scope,$controller, Bomheader, Bomline, Itemmtr, ParseLinks,DTOptionsBuilder,DTColumnBuilder,DTColumnDefBuilder,flash) {
        $scope.bomheaders = [];
        $scope.itemmtrs = Itemmtr.query();
        $scope.bomlineEns = Bomline.query();
        $scope.bomlineCrl=$scope.$new();
        $scope.page = 1;
        $scope.dtOptions = DTOptionsBuilder.newOptions().withPaginationType('full_numbers')
                .withBootstrap()            
                .withDisplayLength(10);
        $scope.dtColumns = [
            DTColumnDefBuilder.newColumnDef(0),
            DTColumnDefBuilder.newColumnDef(1),
            DTColumnDefBuilder.newColumnDef(2).withTitle('Actions').notSortable()          
        ];

        $controller('BomlineController',{$scope:$scope.bomlineCrl});
        // console.info($scope.manfactCrl);

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

        /* Start Modified for Quanity and Ref Designator */
        $scope.validate =function() {
            $scope.mismatch = false;
            var qty = $scope.bomline.quantity;
            var rdesig = $scope.bomline.refdesignator.split(",");
            if (qty != rdesig.length) {               
                $scope.mismatch = true;                
                var msg="Reference Designtor and Quantity mismatch, Please review if needed";
                flash.success=msg;
            }
        };         
        /* End Modified for Quanity and Ref Designator */

    });
