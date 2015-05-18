'use strict';

angular.module('jtraceApp')
    .controller('BomlineController', function ($scope, $filter,Bomline, Itemmtr,DTOptionsBuilder, DTColumnDefBuilder, ParseLinks) {
        $scope.bomlines = [];
        $scope.itemmtrs = Itemmtr.query();
        $scope.page = 1;
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
        $scope.counter = 0;

        var vm = $scope;
        vm.persons = [];

        vm.dtOptions = DTOptionsBuilder.newOptions()//.withPaginationType('full_numbers');
        .withDataProp('')
            .withOption('processing', true)
            .withOption('serverSide', true)
            .withPaginationType('full_numbers');

        vm.dtColumnDefs = [
            DTColumnDefBuilder.newColumnDef(0),
            DTColumnDefBuilder.newColumnDef(1),
            DTColumnDefBuilder.newColumnDef(2),
            DTColumnDefBuilder.newColumnDef(3)            
        ];
        
        $scope.pullBOM =  function () {          
            var mainArr = $filter('filter')($scope.bomlines,{itemmtr:{id:$scope.bomline.itemmtr.id}});
            //alert("tempARR.."+JSON.stringify(tempArr));
            //var t1 = 
            //alert("persons.."+JSON.stringify($scope.bomliness));
            //var mainArr = JSON.parse(JSON.stringify($scope.bomliness));
            vm.persons = [];//var childArr = null;
            for (var i in mainArr) {
                //alert("mainArr .."+i+"..."+mainArr[i].itemline.code);
                if (i==0) {
                    var childArr = 
                        [{   code:mainArr[i].itemline.code,
                            quantity:mainArr[i].quantity,
                            refdesignator:mainArr[i].refdesignator,
                            physical:mainArr[i].physical
                        }];

                    vm.persons = childArr;
                } else {
                    var childArr = {code:mainArr[i].itemline.code,
                            quantity:mainArr[i].quantity,
                            refdesignator:mainArr[i].refdesignator,
                            physical:mainArr[i].physical
                        };
                    vm.persons.push(childArr);
                }                
            }
            //vm.persons = JSON.parse("");
            //alert("persons.."+JSON.stringify(vm.persons));
            //vm.persons.push(childArr);
            //alert("persons.."+vm.persons);
            
            /*for (var j in vm.persons) {
                alert(".."+vm.persons.code+".."vm.persons.quantity);
            }*/

        //vm.persons = $scope.bomliness;//Bomlines.query();
        //alert("..."+JSON.stringify($scope.bomliness));
        //vm.person2Add = _buildPerson2Add(1);  
        vm.addPerson = addPerson;
        vm.modifyPerson = modifyPerson;
        vm.removePerson = removePerson;
      
        }

        /*function _buildPerson2Add(id) {            
            return {
                code: $scope.bomlines.itemline.id,
                quantity: $scope.bomlines.quantity,
                refdesignator: $scope.bomlines.refdesignator,
                physical:$scope.bomlines.physical
            };
        }*/

        function addPerson(index) {
            //alert("In add person"+JSON.stringify($scope.bomlines)+">>>"+$scope.bomlines.quantity);
            //alert("..."+JSON.stringify(vm.persons));
            var mainArr = $filter('filter')($scope.bomlines,{itemline:{id:$scope.bomline.itemline.id}});
            var bomParent = $scope.bomline.itemmtr.id;
            /*if(mainArr.length>0){
                $scope.duplicate = true;
                alert("BOM Component already present");
                return false;
            } else {*/

            Bomlines.update($scope.bomlines,
                function () {
                    $scope.loadAll();
                    //$scope.bomlines = {lineid: null, quantity: null, refdesignator: null, physical: null, id: null};
                    //$scope.$scope.bomlines.itemmtr.id = bomParent;
                });
            //}
            /*var arr = {
                code: $scope.bomlines.itemline.id,
                quantity: $scope.bomlines.quantity,
                refdesignator: $scope.bomlines.refdesignator,
                physical:$scope.bomlines.physical
            };
            //vm.persons.push(angular.copy(vm.person2Add));
            vm.persons.push(arr);
            //alert("..."+JSON.stringify(vm.persons));
            //vm.person2Add = _buildPerson2Add(1);
            $scope.bomlines.itemline.id = null;
            $scope.bomlines.quantity = null;
            $scope.bomlines.refdesignator = null;
            $scope.bomlines.physical = null;

            */
        }

        function modifyPerson(index) {
            vm.persons.splice(index, 1, angular.copy(vm.person2Add));
            //vm.person2Add = _buildPerson2Add(vm.person2Add.id + 1);
        }

        function removePerson(index) {
            vm.persons.splice(index, 1);
        }

        $scope.create = function () {
            Bomline.update($scope.bomline,
                function () {
                    $scope.reset();
                    $('#saveBomlineModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Bomline.get({id: id}, function(result) {
                $scope.bomline = result;
                $('#saveBomlineModal').modal('show');
            });
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

        $scope.clear = function () {
            $scope.bomline = {lineid: null, quantity: null, refdesignator: null, physical: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
