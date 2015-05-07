'use strict';

angular.module('jtraceApp')
    .factory('Itemcat', function ($resource) {
        return $resource('api/itemcats/:id', {}, {
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
