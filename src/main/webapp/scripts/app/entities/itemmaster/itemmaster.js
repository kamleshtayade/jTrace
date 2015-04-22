'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('itemmaster', {
                parent: 'entity',
                url: '/itemmaster',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.itemmaster.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/itemmaster/itemmasters.html',
                        controller: 'ItemmasterController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('itemmaster');
                        return $translate.refresh();
                    }]
                }
            })
            .state('itemmasterDetail', {
                parent: 'entity',
                url: '/itemmaster/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.itemmaster.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/itemmaster/itemmaster-detail.html',
                        controller: 'ItemmasterDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('itemmaster');
                        return $translate.refresh();
                    }]
                }
            });
    });
