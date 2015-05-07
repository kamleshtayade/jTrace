'use strict';

angular.module('jtraceApp')
    .factory('Plantmc', function ($resource) {
        return $resource('api/plantmcs/:id', {}, {
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
