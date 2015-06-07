'use strict';

angular.module('jtraceApp')
    .controller('DomheaderController', function ($scope, Domheader, ParseLinks) {
        $scope.domheaders = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Domheader.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.domheaders.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.domheaders = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Domheader.get({id: id}, function(result) {
                $scope.domheader = result;
                $('#saveDomheaderModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.domheader.id != null) {
                Domheader.update($scope.domheader,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Domheader.save($scope.domheader,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Domheader.get({id: id}, function(result) {
                $scope.domheader = result;
                $('#deleteDomheaderConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Domheader.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteDomheaderConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $('#saveDomheaderModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.domheader = {isAuto: null, cycleTime: null, isMulti: null, panelQty: null, opr: null, shiftsup: null, shift: null, shiftstart: null, shiftend: null, solder: null, machinepk: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
