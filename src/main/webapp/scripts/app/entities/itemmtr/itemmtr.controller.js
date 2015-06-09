'use strict';

angular.module('jtraceApp')
    .controller('ItemmtrController', function ($scope, Itemmtr, Itemsubcat,Itemcat,DTOptionsBuilder,DTColumnBuilder,DTColumnDefBuilder) {
        $scope.itemmtrs = [];
        $scope.itemsubcats = Itemsubcat.query();
        $scope.itemcats = Itemcat.query();

		$scope.newSubCat1 =[];
		$scope.subCatValue = {};
		/* Start : populate the dropdown values for add more specification*/ 
        $scope.loadSpec = function() {
            Itemsubcat.query(function(result, headers) {
            var itemmtrs1 = [];
            var newSubCat = [];
            var newSubCat2 = {};
            var newSub = {};

            $scope.p = "";
            $scope.p = $scope.itemmtr.itemsubcat.id;
            itemmtrs1 = result;
                for(var i = 0 ; i<itemmtrs1.length; i++){
                    if(itemmtrs1[i].id == $scope.p ){
                        itemmtrs1[0] = {
                            spec1 : [{name: 'Tollerance'}, {name:'value'}, {name:'FilmRegister'}]
                        };
                        itemmtrs1[1] = {
                            spec1 : [{name: 'Voltage'}, {name:'Value'}, {name:'Wattage'}]
                        };
                        itemmtrs1[2] = {
                            spec1 : [{name: 'Voltage'}, {name:'Value'}, {name:'CE'}]
                        };
                        itemmtrs1[3] = {
                            spec1 : [{name: 'Value'}, {name:'wattage'}, {name:'Voltage'}]
                        };
                        itemmtrs1[4] = {
                            spec1 : [{name: 'wire'}, {name:'color'}, {name:'gage'}]
                        };

                        $scope.newSubCat1 = itemmtrs1[i].spec1;
                    }
                }
            });
        };

        /* End*/
        /* Start : populate the data in specification textfield */ 
        $scope.showSpecification = function() {
            $scope.dropValue2 = "";
            $scope.itemmtr.specification = ""; 

            for(var i = 0 ;i<$scope.choices.length ; i++){
                $scope.dropValue = $scope.choices[i].itemsubcat.name.name;
                $scope.dropValue1 = $scope.choices[i].name;

                //alert($scope.dropValue);
                if(($scope.dropValue1 != undefined) && (i>0)){
                    $scope.itemmtr.specification = $scope.itemmtr.specification + ',' + '' + $scope.dropValue1 + '' ;
                }else if($scope.dropValue1 != undefined && (i == 0)){
                    $scope.itemmtr.specification = $scope.itemmtr.specification + $scope.dropValue1 ;
                }
            }
        };
        /* End*/ 

        $scope.loadAll = function() {
            Itemmtr.query(function(result, headers) {
                $scope.itemmtrs = result;
            });
        };
        
        $scope.loadAll();

        $scope.create = function () {
			$scope.removeChoice();
            Itemmtr.update($scope.itemmtr,
                function () {
                    $scope.loadAll();
                    $('#saveItemmtrModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
			$scope.removeChoice();
            Itemmtr.get({id: id}, function(result) {
				Itemsubcat.query(function(result, headers) {
            var itemmtrs1 = [];
            $scope.p = "";
            $scope.p = $scope.itemmtr.itemsubcat.id 

            itemmtrs1 = result;
            var newSubCat = [];


            var newSubCat2 = {};
            var newSub = {};


            for(var i = 0 ; i<itemmtrs1.length; i++){
                if(itemmtrs1[i].id == $scope.p ){
                    itemmtrs1[0] = {
                        spec1 : [{name: 'Tollerance'}, {name:'value'}, {name:'FilmRegister'}]
                    };
                    itemmtrs1[1] = {
                        spec1 : [{name: 'Voltage'}, {name:'Value'}, {name:'Wattage'}]
                    };
                    itemmtrs1[2] = {
                        spec1 : [{name: 'Voltage'}, {name:'Value'}, {name:'CE'}]
                    };
                    itemmtrs1[3] = {
                        spec1 : [{name: 'Value'}, {name:'Wattage'}, {name:'Voltage'}]
                    };
                    itemmtrs1[4] = {
                        spec1 : [{name: 'wire'}, {name:'color'}, {name:'gage'}]
                    };

                    $scope.newSubCat1 = itemmtrs1[i].spec1;
                    }
                }
            });

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
		$scope.clearData = function () {
			$scope.itemmtr.specification = "";
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
