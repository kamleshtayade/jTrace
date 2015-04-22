'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('plantmfgline', {
                parent: 'entity',
                url: '/plantmfgline',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.plantmfgline.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plantmfgline/plantmfglines.html',
                        controller: 'PlantmfglineController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('plantmfgline');
                        return $translate.refresh();
                    }]
                }
            })
            .state('plantmfglineDetail', {
                parent: 'entity',
                url: '/plantmfgline/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.plantmfgline.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plantmfgline/plantmfgline-detail.html',
                        controller: 'PlantmfglineDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('plantmfgline');
                        return $translate.refresh();
                    }]
                }
            });
    });
