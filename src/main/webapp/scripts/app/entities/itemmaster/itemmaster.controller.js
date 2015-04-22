'use strict';

angular.module('jtraceApp')
    .controller('ItemmasterController', function ($scope, Itemmaster, Itemcat, Itemsubcat) {
        $scope.itemmasters = [];
        $scope.itemcats = Itemcat.query();
        $scope.itemsubcats = Itemsubcat.query();
        $scope.loadAll = function() {
            Itemmaster.query(function(result) {
               $scope.itemmasters = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Itemmaster.save($scope.itemmaster,
                function () {
                    $scope.loadAll();
                    $('#saveItemmasterModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Itemmaster.get({id: id}, function(result) {
                $scope.itemmaster = result;
                $('#saveItemmasterModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Itemmaster.get({id: id}, function(result) {
                $scope.itemmaster = result;
                $('#deleteItemmasterConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Itemmaster.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteItemmasterConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.itemmaster = {itemCode: null, description: null, attributes: null, id: null};
        };
    });
