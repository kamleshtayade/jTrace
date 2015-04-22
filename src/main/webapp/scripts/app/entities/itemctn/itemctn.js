'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('itemctn', {
                parent: 'entity',
                url: '/itemctn',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.itemctn.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/itemctn/itemctns.html',
                        controller: 'ItemctnController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('itemctn');
                        return $translate.refresh();
                    }]
                }
            })
            .state('itemctnDetail', {
                parent: 'entity',
                url: '/itemctn/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.itemctn.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/itemctn/itemctn-detail.html',
                        controller: 'ItemctnDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('itemctn');
                        return $translate.refresh();
                    }]
                }
            });
    });
