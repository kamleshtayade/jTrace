'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('itemmtr', {
                parent: 'entity',
                url: '/itemmtr',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.itemmtr.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/itemmtr/itemmtrs.html',
                        controller: 'ItemmtrController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('itemmtr');
                        return $translate.refresh();
                    }]
                }
            })
            .state('itemmtrDetail', {
                parent: 'entity',
                url: '/itemmtr/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.itemmtr.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/itemmtr/itemmtr-detail.html',
                        controller: 'ItemmtrDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('itemmtr');
                        return $translate.refresh();
                    }]
                }
            });
    });
