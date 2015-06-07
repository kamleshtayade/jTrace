'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('isupplier', {
                parent: 'entity',
                url: '/isupplier',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.isupplier.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/isupplier/isuppliers.html',
                        controller: 'IsupplierController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('isupplier');
                        return $translate.refresh();
                    }]
                }
            })
            .state('isupplierDetail', {
                parent: 'entity',
                url: '/isupplier/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.isupplier.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/isupplier/isupplier-detail.html',
                        controller: 'IsupplierDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('isupplier');
                        return $translate.refresh();
                    }]
                }
            });
    });
