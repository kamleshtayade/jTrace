'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('itemcat', {
                parent: 'entity',
                url: '/itemcat',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.itemcat.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/itemcat/itemcats.html',
                        controller: 'ItemcatController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('itemcat');
                        return $translate.refresh();
                    }]
                }
            })
            .state('itemcatDetail', {
                parent: 'entity',
                url: '/itemcat/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.itemcat.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/itemcat/itemcat-detail.html',
                        controller: 'ItemcatDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('itemcat');
                        return $translate.refresh();
                    }]
                }
            });
    });
