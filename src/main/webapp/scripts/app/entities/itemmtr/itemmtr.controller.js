'use strict';

angular.module('jtraceApp')
    .controller('ItemmtrController', function ($scope, Itemmtr, Itemsubcat,Itemcat,DTOptionsBuilder,DTColumnBuilder,DTColumnDefBuilder) {
        $scope.itemmtrs = [];
        $scope.itemsubcats = Itemsubcat.query();
        $scope.itemcats = Itemcat.query();

        $scope.loadAll = function() {
            Itemmtr.query(function(result, headers) {
                $scope.itemmtrs = result;
            });
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

        /** Start: SetCode
            Item Master Code is required 
            its composit number created with combination of 
            Class code , Unique Number and revision 

            0.2.6   : class code - from subcatorgy
                    : Unique Number - Item master entity record id
                    : revision - 01 
        **/

        $scope.setCode = function(){
            var itemid = $scope.itemmtr.id;
            if($scope.itemmtr.id == null){
                itemid = 'XXXX';
            }
            if($scope.itemmtr.itemsubcat.id != null){
                for (var i = 0; i < $scope.itemsubcats.length; i++) {
                    if ($scope.itemsubcats[i].id == $scope.itemmtr.itemsubcat.id) {
                      $scope.itemmtr.code = $scope.itemsubcats[i].classcode+'-'+itemid+'-01';
                    }
                }
            }           
        };
        /* End : SetCode */

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
