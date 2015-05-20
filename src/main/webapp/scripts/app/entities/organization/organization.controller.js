'use strict';

angular.module('jtraceApp')
    .controller('OrganizationController', function ($scope, Organization, ParseLinks) {
        $scope.organizations = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Organization.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.organizations.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.organizations = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Organization.get({id: id}, function(result) {
                $scope.organization = result;
                $('#saveOrganizationModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.organization.id != null) {
                Organization.update($scope.organization,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Organization.save($scope.organization,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Organization.get({id: id}, function(result) {
                $scope.organization = result;
                $('#deleteOrganizationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Organization.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteOrganizationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $('#saveOrganizationModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.organization = {name: null, address_1: null, address_2: null, country: null, city: null, pincode: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
