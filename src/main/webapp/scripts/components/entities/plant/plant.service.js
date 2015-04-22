'use strict';

angular.module('jtraceApp')
    .factory('Plant', function ($resource) {
        return $resource('api/plants/:id', {}, {
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
