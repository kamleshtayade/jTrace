'use strict';

angular.module('jtraceApp')
    .factory('Workorderline', function ($resource) {
        return $resource('api/workorderlines/:id', {}, {
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
