'use strict';

angular.module('jtraceApp')
    .controller('SerialnoController', function ($scope, Domline, Domheader, ParseLinks) {
        $scope.domlines = [];
        $scope.domheaders = Domheader.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Domline.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.domlines.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.domlines = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Domline.get({id: id}, function(result) {
                $scope.domline = result;
                $('#saveDomlineModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.domline.id != null) {
                Domline.update($scope.domline,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Domline.save($scope.domline,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Domline.get({id: id}, function(result) {
                $scope.domline = result;
                $('#deleteDomlineConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Domline.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteDomlineConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $('#saveDomlineModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.domline = {serialno: null, scantime: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
