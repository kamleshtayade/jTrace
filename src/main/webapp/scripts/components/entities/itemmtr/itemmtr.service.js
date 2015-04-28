'use strict';

angular.module('jtraceApp')
    .factory('Itemmtr', function ($resource) {
        return $resource('api/itemmtrs/:id', {}, {
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
