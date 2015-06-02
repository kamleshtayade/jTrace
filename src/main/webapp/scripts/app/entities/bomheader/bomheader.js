'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bomheader', {
                parent: 'entity',
                url: '/bomheader',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.bomheader.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bomheader/bomheaders.html',
                        controller: 'BomheaderController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bomheader');
                        return $translate.refresh();
                    }]
                }
            })
            .state('bomheaderDetail', {
                parent: 'entity',
                url: '/bomheader/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.bomheader.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bomheader/bomheader-detail.html',
                        controller: 'BomheaderDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bomheader');
                        return $translate.refresh();
                    }]
                }
            });
    });
