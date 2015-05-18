'use strict';

angular.module('jtraceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bomline', {
                parent: 'entity',
                url: '/bomline',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.bomline.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bomline/bomlines.html',
                        controller: 'BomlineController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bomline');
                        return $translate.refresh();
                    }]
                }
            })
            .state('bomlineDetail', {
                parent: 'entity',
                url: '/bomline/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jtraceApp.bomline.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bomline/bomline-detail.html',
                        controller: 'BomlineDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bomline');
                        return $translate.refresh();
                    }]
                }
            });
    });
