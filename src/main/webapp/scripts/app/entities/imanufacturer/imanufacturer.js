'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('imanufacturer', {
                parent: 'entity',
                url: '/imanufacturer',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.imanufacturer.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/imanufacturer/imanufacturers.html',
                        controller: 'ImanufacturerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('imanufacturer');
                        return $translate.refresh();
                    }]
                }
            })
            .state('imanufacturerDetail', {
                parent: 'entity',
                url: '/imanufacturer/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.imanufacturer.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/imanufacturer/imanufacturer-detail.html',
                        controller: 'ImanufacturerDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('imanufacturer');
                        return $translate.refresh();
                    }]
                }
            });
    });
