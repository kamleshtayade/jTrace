'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('itemmfrpart', {
                parent: 'entity',
                url: '/itemmfrpart',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.itemmfrpart.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/itemmfrpart/itemmfrparts.html',
                        controller: 'ItemmfrpartController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('itemmfrpart');
                        return $translate.refresh();
                    }]
                }
            })
            .state('itemmfrpartDetail', {
                parent: 'entity',
                url: '/itemmfrpart/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.itemmfrpart.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/itemmfrpart/itemmfrpart-detail.html',
                        controller: 'ItemmfrpartDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('itemmfrpart');
                        return $translate.refresh();
                    }]
                }
            });
    });
