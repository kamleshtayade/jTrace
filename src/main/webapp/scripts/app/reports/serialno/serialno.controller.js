'use strict';

angular.module('jtraceApp')
    .controller('SerialnoController', function ($scope, Domline, Domheader,Workorderline, Workorderheader, Woheader,Itemctn, ParseLinks) {
        $scope.isCollapsed = true;
        $scope.CurrentDate = new Date();
        $scope.domlines = [];
        $scope.domheaders = Domheader.query();

        $scope.workorderlines = [];
        $scope.itemctns = Itemctn.query();
        $scope.workorderheaders = Workorderheader.query();

        $scope.woheaders=[];

        $scope.page = 1;
        $scope.loadAll = function() {
            Domline.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    //console.log(result[i]);
                    //console.log($scope.showWO(result[i].serialno));
                    //result[i]=angular.merge({},result[i],$scope.showWO(result[i].serialno));
                    //console.log(result[i]);
                    $scope.domlines.push(result[i]);
                    
                   // $scope.domlines.push($scope.showWO(result[i].woNumber));
                                                           
                }
            });
            Workorderline.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.workorderlines.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.domlines = [];
            $scope.workorderlines=[];
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

        var object1 = {
          "key": "abc123def456",
          "message": {
            "subject": "Has a Question",
            "from": "example1@example.com",
            "to": "example2@example.com"
           }
        };

        var object2 = {
          "key": "00700916391",              //Overwrite me
          "message": {                       //Dont overwrite me!
            "subject": "Hey what's up?",     //Overwrite me
            "something": "something new"     //Add me
           }
        };

        //console.log(angular.extend(object1, object2));
        //console.log(angular.merge(object1,object2));
        //console.log(Domline.query());
        //console.log($scope.workorderlines.length);
        //console.log(angular.merge(Domline.query(),Workorderline.query()));
    });
