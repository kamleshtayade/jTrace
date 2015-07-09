'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('serialno', {
                parent: 'report',
                url: '/serialno',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.workorderline.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/reports/serialno/serialno.html',
                        controller: 'SerialnoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('report');
                        return $translate.refresh();
                    }]
                }
            })
            .state('serialnoDetail', {
                parent: 'report',
                url: '/serailno/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.workorderline.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/reports/serialno/serialno-detail.html',
                        controller: 'SerialnoDetailController'
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