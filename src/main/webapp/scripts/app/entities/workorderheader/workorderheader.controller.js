'use strict';

angular.module('jtraceApp')
    .controller('WorkorderheaderController', function ($scope,$interval, Workorderheader,Bomheader, Itemmtr, Plantmfgline, ParseLinks) {
        $scope.workorderheaders = [];
        $scope.itemmtrs = Itemmtr.query();
        $scope.plantmfglines = Plantmfgline.query();
        $scope.bomheaders = Bomheader.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Workorderheader.query({page: $scope.page, per_page: 20}, function(result, headers) {  
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.workorderheaders.push(result[i]);
                }              
              //  $scope.workorderheaders = result;
            });
        };

        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Workorderheader.get({id: id}, function(result) {
                $scope.workorderheader = result;
                $('#saveWorkorderheaderModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.workorderheader.id != null) {
                Workorderheader.update($scope.workorderheader,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Workorderheader.save($scope.workorderheader,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Workorderheader.get({id: id}, function(result) {
                $scope.workorderheader = result;
                $('#deleteWorkorderheaderConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Workorderheader.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteWorkorderheaderConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveWorkorderheaderModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.workorderheader = {woNumber: null, kitNumber: null, status: null, qty: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
        /* Barcode */
        //$scope.barVal2 = ['201524PDr0001','201524PDr0002','201524PDr0003'];
       // $scope.barVal2 = [];
       //alert($scope.workorderheader.itemserial);
        /*  Start : BarCode Generation */
        $scope.createCode = function(id) {
          Workorderheader.get({id: id}, function(result) { 
                $scope.barVal2 = [];           
                $scope.workorderheader = result;
                var barVal = $scope.workorderheader.itemserial;
                $scope.barVal2= barVal.split(",");
                $('#printWorkorderheaderModal').modal('show');

               // $interval(generateBarcode(), 500, [30]);

           // function generateBarcode() {
                //$scope.barVal2 = id.split(",");   

                var btype = "datamatrix";
                var renderer = "css";
                   // var btype1 = "code11";
                    var quietZone = false;
                   if ($("#quietzone").is(':checked') || $("#quietzone").attr('checked')) {
                        quietZone = true;
                    }
                var checkFlag = false;    
                var settings = {
                    output: renderer,
                    bgColor: $("#bgColor").val(),
                    color: $("#color").val(),
                    barWidth: $("#barWidth").val(),
                    barHeight: $("#barHeight").val(),
                    moduleSize: $("#moduleSize").val(),
                    posX: $("#posX").val(),
                    posY: $("#posY").val(),
                    addQuietZone: $("#quietZoneSize").val()
                };            

                for (var i = 0; i < $scope.barVal2.length; i++) {
                    var barCodeValue = $scope.barVal2[i];
                    var value = barCodeValue.toString();

                    value = {code:value, rect: true};
                    
                    var x = $scope.barVal2[i];
                    var barCod = document.createElement('div')
                    var currentDiv2 = document.getElementById('barCodw')
                    barCod.setAttribute('id',x)
                    currentDiv2.appendChild(barCod);

                    $("#" + x).html("").show().barcode(value, btype, settings);

                   // $("#bcTarget"+x).barcode(value,btype,settings);

                }                
                
                //$scope.flag = true;

                
               // $( "#first" ).slideUp( 300 ).fadeIn( 400 );
               // $( "#second" ).slideUp( 300 ).delay( 5000 ).fadeIn( 400 );
            //};
            /* end : generateBarcode*/

            /*  Start: child checkboxes seletion dependency on parent*/
            $(function() {
                $scope.dynamicDivs1 = [];
                $scope.dynamicDivs12 = '';
                $scope.dynamicDivs14 = '';
                for (var i= 0; i < $scope.barVal2.length; i++) {
                    $scope.dynamicDivs1.push($scope.barVal2[i] + '1');
                    $scope.dynamicDivs12 = $scope.dynamicDivs12 + $scope.barVal2[i] + '1' + ' ' + '&&' + ' ';
                    $scope.dynamicDivs14 = $scope.dynamicDivs14 + 'dynamicDivs144' + ' ' + '&&' + ' ';
                }
                $scope.dynamicDivs14.substring(0, $scope.dynamicDivs14.length - 4);
                $("#selectAll").on('change', function() {
                    for (var i = 0; i < $scope.dynamicDivs1.length; i++) {
                        $("#" + $scope.dynamicDivs1[i]).click(function() {
                            if (!($("#" + $scope.dynamicDivs1[i]).is(':checked'))) {
                                $("#selectAll").attr('checked', false);
                            }
                        });
                    }
                });               
             // generateBarcode();
            });
            /* End */

          });
        }; 
              
            /*End*/





            

        /* Start: Print selected Barcodes */
        $scope.printDivDynamic = function(divName) {
            var s = ""
            var printContents = [];
            for (var i = 0; i < $scope.barVal2.length; i++) {
                var selectAllCodes = $scope.dynamicDivs1[i]
                var x = $scope.barVal2[i]
                if ($("#" + selectAllCodes).is(':checked')) {
                    var s1 = document.getElementById(x).innerHTML;
                    printContents.push(s1);
                }
            }
            if ($("#selectAll").is(':checked')) {
                var printContents1 = document.getElementById(divName).innerHTML;
                var originalContents1 = document.body.innerHTML;
                document.body.innerHTML = printContents1;
                window.print();
                document.body.innerHTML = originalContents1;
            } else {
                var originalContents = document.body.innerHTML;
                document.body.innerHTML = printContents;
                window.print();
                document.body.innerHTML = originalContents;
                $(document).ready(function() {
                    $('#printWorkorderheaderModal').hide('modal');
                    $(".modal-backdrop").removeClass("fade in").addClass("fade out");
                });
            }
        };
               /* End */


        $scope.clearAll = function() {
                
                    /* for(var i = 0 ; i<$scope.dynamicDivs1.length ; i++){

                    if($("#"+$scope.dynamicDivs1[i]).is(':checked')){
                    $("#"+$scope.dynamicDivs1[i]).attr('checked', false) 
                    } 

                    } */
                $scope.barVal2 = ['33566', '7877888', '45345', '564', '54544', '7664767', '555598', 't76ttt', 'dsgfds676', 'hu5555', 'ewwr56656'];
                for (var i = 0; i < $scope.barVal2.length; i++) {
                    var barCodeValue = $scope.barVal2[i]
                    var value = barCodeValue.toString();
                    var btype = "datamatrix";
                    var renderer = "css";
                    var btype1 = "code11";
                    //var quietZone = false;

                    value = {
                        code: value,
                        rect: true
                    };
                    var checkFlag = false;

                    var settings = {
                        output: renderer,
                        bgColor: $("#bgColor").val(),
                        color: $("#color").val(),
                        barWidth: $("#barWidth").val(),
                        barHeight: $("#barHeight").val(),
                        moduleSize: $("#moduleSize").val(),
                        posX: $("#posX").val(),
                        posY: $("#posY").val(),
                        addQuietZone: $("#quietZoneSize").val()
                    };
                    var x = $scope.barVal2[i]
                    
                        //var barCod = document.createElement('div')
                    var currentDiv2 = document.getElementById('barCodw');
                    
                    var barCod = document.getElementById(x);
                        //barCod.setAttribute('id',x)
                    currentDiv2.removeChild(barCod);
                    

                    //$("#"+x).html("").show().barcode(value, btype, settings);
                }
            };


    });
