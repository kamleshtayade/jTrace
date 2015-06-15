'use strict';

angular.module('jtraceApp')
    .controller('WorkorderheaderController', function ($scope, Workorderheader,Bomheader, Itemmtr, Plantmfgline, ParseLinks) {
        $scope.workorderheaders = [];
        $scope.itemmtrs = Itemmtr.query();
        $scope.plantmfglines = Plantmfgline.query();
        $scope.bomheaders = Bomheader.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Workorderheader.query({page: $scope.page, per_page: 20}, function(result, headers) {  
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.workorderheaders.push(result[i]);
                }              
              //  $scope.workorderheaders = result;
            });
        };

        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Workorderheader.get({id: id}, function(result) {
                $scope.workorderheader = result;
                $('#saveWorkorderheaderModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.workorderheader.id != null) {
                Workorderheader.update($scope.workorderheader,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Workorderheader.save($scope.workorderheader,
                    function () {
                        $scope.refresh();
                    });
            }
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

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveWorkorderheaderModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.workorderheader = {woNumber: null, kitNumber: null, status: null, qty: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
