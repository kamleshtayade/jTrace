'use strict';

angular.module('jtraceApp')
    .factory('Domline', function ($resource) {
        return $resource('api/domlines/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    if (data.scantime != null) data.scantime = new Date(data.scantime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
