'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('itemtype', {
                parent: 'entity',
                url: '/itemtype',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.itemtype.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/itemtype/itemtypes.html',
                        controller: 'ItemtypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('itemtype');
                        return $translate.refresh();
                    }]
                }
            })
            .state('itemtypeDetail', {
                parent: 'entity',
                url: '/itemtype/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.itemtype.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/itemtype/itemtype-detail.html',
                        controller: 'ItemtypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('itemtype');
                        return $translate.refresh();
                    }]
                }
            });
    });
