'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('domline', {
                parent: 'entity',
                url: '/domline',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.domline.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/domline/domlines.html',
                        controller: 'DomlineController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('domline');
                        return $translate.refresh();
                    }]
                }
            })
            .state('domlineDetail', {
                parent: 'entity',
                url: '/domline/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.domline.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/domline/domline-detail.html',
                        controller: 'DomlineDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('domline');
                        return $translate.refresh();
                    }]
                }
            });
    });
