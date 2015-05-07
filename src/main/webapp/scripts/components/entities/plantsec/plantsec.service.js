'use strict';

angular.module('jtraceApp')
    .factory('Plantsec', function ($resource) {
        return $resource('api/plantsecs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
