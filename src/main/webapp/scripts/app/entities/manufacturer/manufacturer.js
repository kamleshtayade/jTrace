'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('manufacturer', {
                parent: 'entity',
                url: '/manufacturer',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.manufacturer.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/manufacturer/manufacturers.html',
                        controller: 'ManufacturerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('manufacturer');
                        return $translate.refresh();
                    }]
                }
            })
            .state('manufacturerDetail', {
                parent: 'entity',
                url: '/manufacturer/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.manufacturer.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/manufacturer/manufacturer-detail.html',
                        controller: 'ManufacturerDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('manufacturer');
                        return $translate.refresh();
                    }]
                }
            });
    });
