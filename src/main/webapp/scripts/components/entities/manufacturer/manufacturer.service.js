'use strict';

angular.module('jtraceApp')
    .factory('Manufacturer', function ($resource) {
        return $resource('api/manufacturers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
