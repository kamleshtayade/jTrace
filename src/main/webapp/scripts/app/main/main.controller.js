'use strict';

angular.module('jtraceApp')
    .controller('MainController', function ($scope, Principal,Bomheader,Itemmtr,Workorderheader,Imanufacturer) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
        $scope.alerts = [
		    { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' },
		    { type: 'success', msg: 'Well done! You successfully read this important alert message.' }
		];
		$scope.closeAlert = function(index) {
    		$scope.alerts.splice(index, 1);
  		};

        /*Statistics*/
        Itemmtr.query(function(data){
            $scope.itemmtrcount = data.length;
        });
        Bomheader.query(function(data) {
            $scope.bomheadercount = data.length;
        });
        Workorderheader.query(function(data){
            $scope.wocount = data.length;
        });
        Imanufacturer.query(function(data){
            $scope.mfrcount = data.length;
        });

        /*End - Statistics */
        // Area Chart
        $scope.labelsLine = ["January", "February", "March", "April", "May", "June", "July"];
        $scope.seriesLine = ['Series A', 'Series B'];
        $scope.dataLine = [
            [65, 59, 80, 81, 56, 55, 40],
            [28, 48, 40, 19, 86, 27, 90]
        ];
        $scope.colours=['#27ae60','#e67e22','#2c3e50'];
        $scope.onClickLine = function (points, evt) {
            console.log(points, evt);
        };

        // Bar Chart
        $scope.labelsCbar = ['2006', '2007', '2008', '2009', '2010', '2011', '2012'];
        $scope.series = ['Series A', 'Series B'];

        $scope.dataCbar = [
            [65, 59, 80, 81, 56, 55, 40],
            [28, 48, 40, 19, 86, 27, 90]
         ];

        // Donut chart
        
        $scope.labels = ["Running Lines", "Assemblies", "Operators"];
        $scope.data = [12, 30, 20];
    });


angular.module('jtraceApp')
    .controller('DataTablesCtrl', function ($scope) {
        $scope.labels = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];
        $scope.data = [
          [65, 59, 80, 81, 56, 55, 40],
          [28, 48, 40, 19, 86, 27, 90]
        ];
        $scope.colours = [
          { // grey
            fillColor: 'rgba(148,159,177,0.2)',
            strokeColor: 'rgba(148,159,177,1)',
            pointColor: 'rgba(148,159,177,1)',
            pointStrokeColor: '#fff',
            pointHighlightFill: '#fff',
            pointHighlightStroke: 'rgba(148,159,177,0.8)'
          },
          { // dark grey
            fillColor: 'rgba(77,83,96,0.2)',
            strokeColor: 'rgba(77,83,96,1)',
            pointColor: 'rgba(77,83,96,1)',
            pointStrokeColor: '#fff',
            pointHighlightFill: '#fff',
            pointHighlightStroke: 'rgba(77,83,96,1)'
          }
        ];
        $scope.randomize = function () {
          $scope.data = $scope.data.map(function (data) {
            return data.map(function (y) {
              y = y + Math.random() * 20 - 10;
              return parseInt(y < 0 ? 0 : y > 100 ? 100 : y);
            });
          });
        };
    });

