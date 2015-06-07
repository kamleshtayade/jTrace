'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('domheader', {
                parent: 'entity',
                url: '/domheader',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.domheader.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/domheader/domheaders.html',
                        controller: 'DomheaderController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('domheader');
                        return $translate.refresh();
                    }]
                }
            })
            .state('domheaderDetail', {
                parent: 'entity',
                url: '/domheader/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.domheader.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/domheader/domheader-detail.html',
                        controller: 'DomheaderDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('domheader');
                        return $translate.refresh();
                    }]
                }
            });
    });
