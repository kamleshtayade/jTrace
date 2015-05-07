'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('plantsec', {
                parent: 'entity',
                url: '/plantsec',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.plantsec.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plantsec/plantsecs.html',
                        controller: 'PlantsecController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('plantsec');
                        return $translate.refresh();
                    }]
                }
            })
            .state('plantsecDetail', {
                parent: 'entity',
                url: '/plantsec/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.plantsec.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plantsec/plantsec-detail.html',
                        controller: 'PlantsecDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('plantsec');
                        return $translate.refresh();
                    }]
                }
            });
    });
