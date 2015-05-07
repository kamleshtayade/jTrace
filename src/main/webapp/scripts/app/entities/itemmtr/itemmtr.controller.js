'use strict';

angular.module('jtraceApp')
    .controller('ItemmtrController', function ($scope, Itemmtr, Itemsubcat,Itemcat, ParseLinks) {
        $scope.itemmtrs = [];
        $scope.itemsubcats = Itemsubcat.query();
        $scope.itemcats = Itemcat.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Itemmtr.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.itemmtrs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Itemmtr.update($scope.itemmtr,
                function () {
                    $scope.loadAll();
                    $('#saveItemmtrModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Itemmtr.get({id: id}, function(result) {
                $scope.itemmtr = result;
                $('#saveItemmtrModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Itemmtr.get({id: id}, function(result) {
                $scope.itemmtr = result;
                $('#deleteItemmtrConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Itemmtr.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteItemmtrConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.itemmtr = {code: null, description: null, specification: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        /* Specification */
        $scope.choices = [];
        $scope.colors =  [
              {name:'Resistors '},
              {name:'Capacitors'},
              {name:'Diode'},
              {name:'Inductor'},
              {name:'Wire'}
                ];
  
        $scope.addNewChoice = function() {
            var newItemNo = $scope.choices.length+1;
            $scope.choices.push({'id':'choice'+newItemNo});
        };
    
        $scope.removeChoice = function() {
            var lastItem = $scope.choices.length-1;
            $scope.choices.splice(lastItem);
        };        
    });
