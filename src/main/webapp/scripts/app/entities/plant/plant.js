'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('plant', {
                parent: 'entity',
                url: '/plant',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.plant.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plant/plants.html',
                        controller: 'PlantController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('plant');
                        return $translate.refresh();
                    }]
                }
            })
            .state('plantDetail', {
                parent: 'entity',
                url: '/plant/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.plant.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plant/plant-detail.html',
                        controller: 'PlantDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('plant');
                        return $translate.refresh();
                    }]
                }
            });
    });
