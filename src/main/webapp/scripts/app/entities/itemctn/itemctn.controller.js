'use strict';

angular.module('jtraceApp')
    .controller('ItemctnController', function ($scope, Itemctn, Itemmtr,Supplier,Manufacturer,Customer,Itemmfrpart, ParseLinks) {
        $scope.itemctns = [];
    		//$scope.itemctn = {};
    		$scope.id1 = {};
    		$scope.ctnId = "";
        $scope.itemmtrs = Itemmtr.query();
        $scope.suppliers = Supplier.query();
        $scope.manufacturers = Manufacturer.query();
        $scope.customers = Customer.query();
        $scope.itemmfrparts = Itemmfrpart.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Itemctn.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.itemctns = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Itemctn.update($scope.itemctn,
                function () {
                    $scope.loadAll();
                    $('#saveItemctnModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
			debugger;
            Itemctn.get({id: id}, function(result) {
                $scope.itemctn = result;
                $scope.itemctn.ctn = $scope.itemctn.ctn + $scope.itemctn.id;
                $('#saveItemctnModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Itemctn.get({id: id}, function(result) {
                $scope.itemctn = result;
                $('#deleteItemctnConfirmation').modal('show');
            });
        };

      /*start bar code */
        $scope.createCode = function(id) {
          Itemctn.get({
            id: id
          }, function(result) {
            $scope.itemctn = result;
            $('#saveItemctnModal2').modal('show');

            function generateBarcode() {
              var o = $scope.itemctn.id
              var value = o.toString();
              var btype = $("input[name=btype]:checked").val();
              var renderer = $("input[name=renderer]:checked").val();
              var btype1 = "code11";
              var quietZone = false;
              if ($("#quietzone").is(':checked') || $("#quietzone").attr('checked')) {
                quietZone = true;
              }
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
              if ($("#rectangular").is(':checked') || $("#rectangular").attr('checked')) {
                value = {
                  code: value,
                  rect: true
                };
                var checkFlag = false;
              }
              if (renderer == 'canvas') {
                clearCanvas();
                $("#barcodeTarget").hide();
                $("#canvasTarget").show().barcode(value, btype, settings);
              } else {
                $("#canvasTarget").hide();
                $("#barcodeTarget").html("").show().barcode(value, btype, settings);
              }
              if (renderer == 'canvas') {
                clearCanvas();
                $("#barTarget").hide();
                $("#canvasTarget").show().barcode(value, btype1, settings);
              } else {
                $("#canvasTarget").hide();
                $("#barTarget").html("").show().barcode(value, btype1, settings);
              }
              if (!checkFlag) {
                var value = o.toString();
                var btype1 = $("input[name=btype]:checked").val();
                var renderer = $("input[name=renderer]:checked").val();
                // var btype1 = "code11";
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
              }
              if (renderer == 'canvas') {
                clearCanvas();
                $("#barTarget1").hide();
                $("#canvasTarget").show().barcode(value, btype1, settings);
              } else {
                $("#canvasTarget").hide();
                $("#barTarget1").html("").show().barcode(value, btype1, settings);
              }
            }

            function showConfig1D() {
              $('.config .barcode1D').show();
              $('.config .barcode2D').hide();
            }

            function showConfig2D() {
              $('.config .barcode1D').hide();
              $('.config .barcode2D').show();
            }

            function clearCanvas() {
              var canvas = $('#canvasTarget').get(0);
              var ctx = canvas.getContext('2d');
              ctx.lineWidth = 1;
              ctx.lineCap = 'butt';
              ctx.fillStyle = '#FFFFFF';
              ctx.strokeStyle = '#000000';
              ctx.clearRect(0, 0, canvas.width, canvas.height);
              ctx.strokeRect(0, 0, canvas.width, canvas.height);
            }
            $(function() {
              $('input[name=renderer]').click(function() {
                if ($(this).attr('id') == 'canvas') $('#miscCanvas').show();
                else $('#miscCanvas').hide();
              });
              generateBarcode();
            });

            var _gaq = _gaq || [];
            _gaq.push(['_setAccount', 'UA-36251023-1']);
            _gaq.push(['_setDomainName', 'jqueryscript.net']);
            _gaq.push(['_trackPageview']);

            (function() {
              var ga = document.createElement('script');
              ga.type = 'text/javascript';
              ga.async = true;
              ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
              var s = document.getElementsByTagName('script')[0];
              s.parentNode.insertBefore(ga, s);
            })();

          });
        }; 
        /* End BarCode */
        $scope.confirmDelete = function (id) {
            Itemctn.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteItemctnConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.itemctn = {ctn: null, reqdQty: null, recdDt: null, item: null, srNoTo: null, selfLife: null, poQty: null, invoice: null, id: null};
			$scope.itemctn.itemmfrpart = {}
             $scope.itemctn.itemmfrpart.sup = {} 			
            $scope.itemctn.itemmfrpart.sup.id =  null
			 $scope.itemctn.itemmfrpart.itemmtr = {};
			 
            //$scope.itemctn.itemmfrpart.itemmtr = {id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        /*CTN Tokanize*/
        $scope.stateSelected = function() {
          $scope.totalStr = "";
          $scope.manuCode = $scope.itemctn.itemmfrpart.sup.id;
          $scope.codeSelected = "";
          $scope.itemCode = "";
          $scope.itemCode = $scope.itemctn.itemmfrpart.itemmtr.id;

          var itemS = $scope.itemctn.lotCode;
          var itemS2 = $scope.itemctn.dateCode;

          for (var i = 0; i < $scope.suppliers.length; i++) {
            if ($scope.suppliers[i].id == $scope.manuCode) {
              $scope.codeSelected1 = $scope.suppliers[i].code
            }
          }
          if ($scope.manuCode != null) {
            var itemC12 = $scope.codeSelected1.substring(1, 3);
            $scope.totalStr = $scope.totalStr + itemC12
            $scope.itemctn.ctn = $scope.totalStr
          }
          for (var i = 0; i < $scope.itemmtrs.length; i++) {
            if ($scope.itemmtrs[i].id == $scope.itemCode) {
              $scope.codeSelected = $scope.itemmtrs[i].code
            }
          }
          if ($scope.itemCode != null) {
            var itemC11 = $scope.codeSelected.substring(0, 3);
            $scope.totalStr = itemC11 + $scope.totalStr
            $scope.itemctn.ctn = $scope.totalStr
          }
          if (itemS != null) {
            var itemS1 = $scope.itemctn.lotCode.slice($scope.itemctn.lotCode.length - 4, $scope.itemctn.lotCode.length);
            $scope.totalStr = $scope.totalStr + itemS1;
            $scope.itemctn.ctn = $scope.totalStr4
          }
          if (itemS2 != null) {
            var itemS21 = $scope.itemctn.dateCode.slice($scope.itemctn.dateCode.length - 4, $scope.itemctn.dateCode.length);
            $scope.totalStr = $scope.totalStr + itemS21;
            $scope.totalStr3 = itemS1
            $scope.itemctn.ctn = $scope.totalStr
          }

        };

    });
