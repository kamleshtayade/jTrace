'use strict';

angular.module('jtraceApp')
    .controller('WhereusedController', function ($scope, Bomline,Bomheader, Itemmtr, Itemctn,Itemmfrpart, ParseLinks,flash) {
        $scope.isCollapsed = true;
        $scope.CurrentDate = new Date();
        $scope.bomlines = [];
        $scope.itemmtrs = Itemmtr.query();
        $scope.itemctns = Itemctn.query();
        $scope.bomheaders = Bomheader.query();
        $scope.itemmfrparts = Itemmfrpart.query();
        $scope.page = 1;

        Bomheader.query(function(data) {
        $scope.prperties = data;
        console.log("Data Count "+data.length);
        });

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
        /* Start Modified for Quanity and Ref Designator */
        $scope.validate =function() {
            $scope.mismatch = false;
            var qty = $scope.bomline.quantity;
            var rdesig = $scope.bomline.refdesignator.split(",");
            if (qty != rdesig.length) {
                $scope.mismatch = true;                
                var msg="Reference Designtor and Quantity mismatch, Please review if needed";
                flash.info=msg;
            }
        };            
        /* End Modified for Quanity and Ref Designator */
        /* Chart */
        $scope.labels = ["Download Sales", "In-Store Sales", "Mail-Order Sales"];
        $scope.data = [300, 500, 100];
        $scope.colours=['#27ae60','#e67e22','#2c3e50'];
    });
