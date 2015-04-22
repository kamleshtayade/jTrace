'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('workorderline', {
                parent: 'entity',
                url: '/workorderline',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.workorderline.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/workorderline/workorderlines.html',
                        controller: 'WorkorderlineController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('workorderline');
                        return $translate.refresh();
                    }]
                }
            })
            .state('workorderlineDetail', {
                parent: 'entity',
                url: '/workorderline/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.workorderline.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/workorderline/workorderline-detail.html',
                        controller: 'WorkorderlineDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('workorderline');
                        return $translate.refresh();
                    }]
                }
            });
    });
