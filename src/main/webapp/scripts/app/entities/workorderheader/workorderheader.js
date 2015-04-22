'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('workorderheader', {
                parent: 'entity',
                url: '/workorderheader',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.workorderheader.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/workorderheader/workorderheaders.html',
                        controller: 'WorkorderheaderController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('workorderheader');
                        return $translate.refresh();
                    }]
                }
            })
            .state('workorderheaderDetail', {
                parent: 'entity',
                url: '/workorderheader/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.workorderheader.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/workorderheader/workorderheader-detail.html',
                        controller: 'WorkorderheaderDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('workorderheader');
                        return $translate.refresh();
                    }]
                }
            });
    });
