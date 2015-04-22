'use strict';

angular.module('jtraceApp')
    .factory('Itemmaster', function ($resource) {
        return $resource('api/itemmasters/:id', {}, {
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
