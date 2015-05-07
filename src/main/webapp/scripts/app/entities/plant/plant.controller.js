'use strict';

angular.module('jtraceApp')
    .controller('PlantController', function ($scope,$modal, Plant, Plantmfgline, ParseLinks) {
        $scope.plants = [];
        $scope.plantmfglines = Plantmfgline.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Plant.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.plants = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Plant.update($scope.plant,
                function () {
                    $scope.loadAll();
                    $('#savePlantModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Plant.get({id: id}, function(result) {
                $scope.plant = result;
                $('#savePlantModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Plant.get({id: id}, function(result) {
                $scope.plant = result;
                $('#deletePlantConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Plant.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePlantConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.plant = {code: null, address: null, isenabled: null, capacity: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        $scope.openModal = function(data) {

          var modalInstance = $modal.open({
            templateUrl: 'scripts/app/entities/plant/modalMain.html',
            controller: 'PlantModalCtrl',
            size: '', // Sizes: 'sm', 'lg'
            resolve: {
              data: function() {
                return data === null ? {} : data;
              }
            }
          });

          modalInstance.result.then(function(result) {
            // Actions based on modal controller result
            if (result) {
              // ...do something on modal close result
            }
          }, function() {
            $log.info('Modal dismissed at: ' + new Date());
          });
        };  

    });

angular.module('jtraceApp')
    .controller('PlantModalCtrl', ['$scope','$modalInstance','data' ,'$log', function ($scope,$modalInstance,data,$log) {
        $scope.data = data;
        
        $scope.steps = [
          { number: 1, name: 'First Step' },
          { number: 2, name: 'Second Step' },
          { number: 3, name: 'Final' }
          ];
        
        $scope.currentStep = angular.copy($scope.steps[0]);
        $scope.preStep = 1;
        
        $scope.cancel = function() {
          $modalInstance.dismiss('cancel');
        };
        
        $scope.nextStep = function() {
          // Perform current step actions and show next step:
          // E.g. save form data
          
          var nextNumber = $scope.currentStep.number;
          if ($scope.steps.length == nextNumber){
            $modalInstance.dismiss('cancel');
          }
          $scope.currentStep = angular.copy($scope.steps[nextNumber]);
        };

        $scope.previousStep = function() {
          // Perform current step actions and show next step:
          // E.g. save form data
          
          var preNumber = $scope.currentStep.number;
          
          $log.debug(preNumber);
          if ($scope.preStep == preNumber){
            $modalInstance.dismiss('cancel');
          }
          $scope.currentStep = angular.copy($scope.steps[preNumber-2]);
        };
        
        $scope.finalStep = function() {
          // Perform current step actions and show next step:
          // E.g. save form data
          
          var nextNumber = 2;
          if ($scope.steps.length == nextNumber){
            $modalInstance.dismiss('cancel');
          }
          $scope.currentStep = angular.copy($scope.steps[nextNumber]);
        }; 

        /*Typeahead*/
        $scope.selected = undefined;
        $scope.states = ['Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California', 'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii', 'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana', 'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota', 'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire', 'New Jersey', 'New Mexico', 'New York', 'North Dakota', 'North Carolina', 'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island', 'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont', 'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'];       
    }]);