 'use strict';
 angular.module('jtraceApp').controller('ManufacturerController',ManufacturerController);
 function ManufacturerController($scope,$resource,Manufacturer,ParseLinks,DTOptionsBuilder,DTColumnBuilder,DTColumnDefBuilder) {
      $scope.manufacturers=[];
      $scope.dtOptions = DTOptionsBuilder.newOptions().withPaginationType('full_numbers')
            .withBootstrap()            
            .withDisplayLength(10);
      $scope.dtColumns = [
        DTColumnDefBuilder.newColumnDef(0),
        DTColumnDefBuilder.newColumnDef(1),
        DTColumnDefBuilder.newColumnDef(2),
        DTColumnDefBuilder.newColumnDef(3),
        DTColumnDefBuilder.newColumnDef(4),
        DTColumnDefBuilder.newColumnDef(5).withTitle('Actions').notSortable()
       
    ];
    $scope.loadAll = function() {
           $scope.manufacturers= Manufacturer.query(); 
    };
    $scope.loadAll();
    $scope.create = function () {
            Manufacturer.update($scope.manufacturer,function () {
                    $scope.manufacturers= Manufacturer.query(); 
                    $('#saveManufacturerModal').modal('hide');
                     $scope.clear(); 
                });
        };
    $scope.update = function (id) {
            Manufacturer.get({id: id}, function(result) {
                $scope.manufacturer = result;
                $('#saveManufacturerModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Manufacturer.get({id: id}, function(result) {
                $scope.manufacturer = result;
                $('#deleteManufacturerConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Manufacturer.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteManufacturerConfirmation').modal('hide');
                    $scope.clear();
                });
        };
        
    $scope.clear = function () {
            $scope.manufacturer = {code: null, name: null, isenabled: null, mfrcat: null, address: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
   
}