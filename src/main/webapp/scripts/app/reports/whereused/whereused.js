 'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('whereused', {
                parent: 'report',
                url: '/whereused',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.whereused.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/reports/whereused/whereused.html',
                        controller: 'WhereusedController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('report');
                        return $translate.refresh();
                    }]
                }
            })
            .state('whereusedDetail', {
                parent: 'report',
                url: '/whereused/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.whereused.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/reports/whereused/whereused-detail.html',
                        controller: 'WhereusedDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('report');
                        return $translate.refresh();
                    }]
                }
            });
    });
