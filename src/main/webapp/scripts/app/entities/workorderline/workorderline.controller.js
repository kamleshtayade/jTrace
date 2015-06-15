'use strict';

angular.module('jtraceApp')
    .controller('WorkorderlineController', function ($scope,$filter, Workorderline, Workorderheader, Itemctn, ParseLinks) {
        $scope.workorderlines = [];
        $scope.itemctns = Itemctn.query();
        $scope.workorderheaders = Workorderheader.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Workorderline.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.workorderlines.push(result[i]);
                }
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

        /* First In First Out function*/
        $scope.fifoCheck = function (ctnID) {
            //alert("...."+ctnID);
            /*var selectedCTN = $filter('filter')($scope.itemctns,{id:ctnID});
            alert(selectedCTN.id);*/
            var availableCTNs = $filter('filter')($scope.itemctns,{itemmfrpart:{itemmtr:{code:$scope.workorderline.itemmtr.code}}});
            //alert(JSON.stringify(availableCTNs));
            for (var i = 0; i < availableCTNs.length; i++) {
                //alert(availableCTNs[i].id);
                if(availableCTNs[i].id < ctnID) {
                    alert("Pls use "+availableCTNs[i].ctn+" component CTN based on FIFO");
                    break;
                }
            }
            //alert(JSON.stringify(availableCTNs));
            
            //var availableCTNs = $filter('filter')($scope.itemctns,{itemmfrpart:{itemmtr:{code:masterItem}}});
            //alert(JSON.stringify(availableCTNs));
        };
    });
