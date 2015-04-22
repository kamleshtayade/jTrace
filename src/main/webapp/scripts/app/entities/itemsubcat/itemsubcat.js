'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('itemsubcat', {
                parent: 'entity',
                url: '/itemsubcat',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.itemsubcat.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/itemsubcat/itemsubcats.html',
                        controller: 'ItemsubcatController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('itemsubcat');
                        return $translate.refresh();
                    }]
                }
            })
            .state('itemsubcatDetail', {
                parent: 'entity',
                url: '/itemsubcat/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.itemsubcat.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/itemsubcat/itemsubcat-detail.html',
                        controller: 'ItemsubcatDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('itemsubcat');
                        return $translate.refresh();
                    }]
                }
            });
    });
