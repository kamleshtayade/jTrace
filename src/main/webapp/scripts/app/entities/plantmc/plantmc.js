'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('plantmc', {
                parent: 'entity',
                url: '/plantmc',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.plantmc.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plantmc/plantmcs.html',
                        controller: 'PlantmcController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('plantmc');
                        return $translate.refresh();
                    }]
                }
            })
            .state('plantmcDetail', {
                parent: 'entity',
                url: '/plantmc/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.plantmc.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plantmc/plantmc-detail.html',
                        controller: 'PlantmcDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('plantmc');
                        return $translate.refresh();
                    }]
                }
            });
    });
