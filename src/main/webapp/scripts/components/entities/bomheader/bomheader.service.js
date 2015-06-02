'use strict';

angular.module('jtraceApp')
    .factory('Bomheader', function ($resource) {
        return $resource('api/bomheaders/:id', {}, {
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
